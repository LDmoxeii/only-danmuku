package edu.only4.danmuku.adapter.portal.api.web

import cn.dev33.satoken.annotation.SaIgnore
import cn.dev33.satoken.stp.StpUtil
import com.only.engine.exception.KnownException
import com.only.engine.json.misc.JsonUtils
import com.only.engine.web.annotation.IgnoreResultWrapper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.video_encrypt.FrontGetEncKey
import edu.only4.danmuku.adapter.portal.api.payload.video_encrypt.FrontGetEncMaster
import edu.only4.danmuku.adapter.portal.api.payload.video_encrypt.FrontGetEncSegment
import edu.only4.danmuku.adapter.portal.api.payload.video_encrypt.FrontGetEncVariantPlaylist
import edu.only4.danmuku.adapter.portal.api.payload.video_encrypt.FrontIssueEncToken
import edu.only4.danmuku.adapter.portal.api.payload.video_encrypt.FrontListEncQualities
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.application.commands.video_encrypt.ConsumeVideoHlsKeyTokenCmd
import edu.only4.danmuku.application.commands.video_encrypt.IssueVideoHlsKeyTokenCmd
import edu.only4.danmuku.application.queries.video_encrypt.GetLatestVideoHlsKeyQry
import edu.only4.danmuku.application.queries.video_encrypt.GetVideoEncryptStatusQry
import edu.only4.danmuku.application.queries.video_encrypt.ListVideoQualityAuthQry
import edu.only4.danmuku.application.queries.video_transcode.GetVideoPostIdByFileIdQry
import edu.only4.danmuku.application.queries.video_transcode.ListVideoAbrVariantsQry
import edu.only4.danmuku.domain.aggregates.video_hls_quality_auth.enums.QualityAuthPolicy
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.File
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

@SaIgnore
@RestController
@RequestMapping("/video/enc")
class VideoEncryptController(
    private val fileProps: FileAppProperties,
) {

    @PostMapping("/token")
    fun issueToken(@RequestBody req: FrontIssueEncToken.Request): FrontIssueEncToken.Response {
        val post = Mediator.queries.send(GetVideoPostIdByFileIdQry.Request(fileId = req.fileId))
        val latestKey = Mediator.queries.send(
            GetLatestVideoHlsKeyQry.Request(videoFilePostId = post.filePostId)
        )
        val keyId = latestKey.keyId ?: throw KnownException("未找到可用密钥")
        val keyVersion = latestKey.keyVersion ?: throw KnownException("未找到可用密钥版本")

        val allowedQualities = computeAllowedQualities(post.filePostId)

        val resp = Mediator.commands.send(
            IssueVideoHlsKeyTokenCmd.Request(
                videoFilePostId = post.filePostId,
                videoFileId = req.fileId,
                keyId = keyId,
                keyVersion = keyVersion,
                audience = StpUtil.getLoginIdDefaultNull()?.toString(),
                allowedQualities = allowedQualities
            )
        )
        return FrontIssueEncToken.Response(
            token = resp.token,
            expireAt = resp.expireAt,
            allowedQualities = resp.allowedQualities
        )
    }

    @PostMapping("/qualities")
    fun qualities(@RequestBody req: FrontListEncQualities.Request): FrontListEncQualities.Response {
        val post = Mediator.queries.send(GetVideoPostIdByFileIdQry.Request(fileId = req.fileId))
        val policies = loadQualityPolicies(post.filePostId)
        val allowLogin = StpUtil.isLogin()
        val items = if (policies.isEmpty()) {
            loadAbrQualities(post.filePostId).map { quality ->
                FrontListEncQualities.QualityItem(
                    quality = quality,
                    authPolicy = QualityAuthPolicy.PUBLIC.code,
                    playable = true
                )
            }
        } else {
            sortPolicies(policies).map { payload ->
                val policy = resolvePolicy(payload.authPolicy)
                FrontListEncQualities.QualityItem(
                    quality = payload.quality,
                    authPolicy = policy.code,
                    playable = isPlayable(policy, allowLogin)
                )
            }
        }
        return FrontListEncQualities.Response(items)
    }

    @IgnoreResultWrapper
    @GetMapping("/videoResource/{fileId}/master.m3u8")
    fun master(
        @PathVariable fileId: Long,
        @RequestParam token: String
    ): ResponseEntity<String> {
        val status = encryptStatus(fileId)
        if (status.encryptStatus != "ENCRYPTED") {
            throw KnownException("加密未完成: ${status.encryptStatus}")
        }
        val post = Mediator.queries.send(GetVideoPostIdByFileIdQry.Request(fileId = fileId))
        val allowedQualities = resolveAllowedQualities(post.filePostId)
        val masterFile = File(buildEncPath(status.encryptedMasterPath))
        if (!masterFile.exists()) {
            throw KnownException("master 文件不存在")
        }
        val content = masterFile.readText()
        val filtered = if (allowedQualities.isEmpty()) content else {
            filterMasterByAllowedQualities(content, allowedQualities.toSet())
        }
        val replaced = filtered.replace("__TOKEN__", token)
        val withToken = attachTokenToVariantPlaylists(replaced, token)
        return ResponseEntity.ok()
            .contentType(MediaType.valueOf("application/vnd.apple.mpegurl"))
            .header(HttpHeaders.CONTENT_LENGTH, withToken.toByteArray().size.toString())
            .body(withToken)
    }

    @IgnoreResultWrapper
    @GetMapping("/videoResource/{fileId}/{quality}/index.m3u8")
    fun playlist(
        @PathVariable fileId: Long,
        @PathVariable quality: String,
        @RequestParam token: String
    ): ResponseEntity<String> {
        val status = encryptStatus(fileId)
        val base = status.encryptedMasterPath?.substringBeforeLast("/master.m3u8")
            ?: throw KnownException("缺少加密目录")
        val playlistFile = File(buildEncPath("$base/$quality/index.m3u8"))
        if (!playlistFile.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
        val content = playlistFile.readText()
            .replace("__TOKEN__", token)
            .replace("/api/video/enc/key?keyId=", "/api/video/enc/key?quality=$quality&keyId=")
            .replace("/video/enc/key?keyId=", "/api/video/enc/key?quality=$quality&keyId=")
        return ResponseEntity.ok()
            .contentType(MediaType.valueOf("application/vnd.apple.mpegurl"))
            .header(HttpHeaders.CONTENT_LENGTH, content.toByteArray().size.toString())
            .body(content)
    }

    @IgnoreResultWrapper
    @GetMapping("/videoResource/{fileId}/{quality}/{ts}")
    fun segment(
        @PathVariable fileId: Long,
        @PathVariable quality: String,
        @PathVariable ts: String
    ): ResponseEntity<FileSystemResource> {
        val status = encryptStatus(fileId)
        val base = status.encryptedMasterPath?.substringBeforeLast("/master.m3u8")
            ?: throw KnownException("缺少加密目录")
        val segmentFile = File(buildEncPath("$base/$quality/$ts"))
        return asResource(segmentFile, MediaType.valueOf("video/mp2t"))
    }

    @IgnoreResultWrapper
    @GetMapping("/key")
    fun keyByGet(
        @RequestParam token: String,
        @RequestParam keyId: String,
        @RequestParam(required = false) quality: String?
    ): ResponseEntity<ByteArrayResource> {
        val resp = Mediator.commands.send(
            ConsumeVideoHlsKeyTokenCmd.Request(
                token = token,
                keyId = keyId,
                quality = quality ?: ""
            )
        )
        if (!resp.valid) throw KnownException(resp.failReason ?: "token 无效")
        val keyHex = resp.keyPlainHex ?: throw KnownException("key 为空")
        val keyBytes = hexToBytes(keyHex)
        val resource = ByteArrayResource(keyBytes)
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(HttpHeaders.CONTENT_LENGTH, keyBytes.size.toString())
            .body(resource)
    }

    private fun encryptStatus(videoFileId: Long): GetVideoEncryptStatusQry.Response {
        val post = Mediator.queries.send(GetVideoPostIdByFileIdQry.Request(fileId = videoFileId))
        return Mediator.queries.send(
            GetVideoEncryptStatusQry.Request(
                videoFilePostId = post.filePostId,
                videoFileId = videoFileId
            )
        )
    }

    private fun buildEncPath(relative: String?): String {
        val rel = relative ?: throw KnownException("缺少路径")
        return fileProps.projectFolder + Constants.FILE_FOLDER + rel.removePrefix("/")
    }

    private fun hexToBytes(hex: String): ByteArray {
        if (hex.length % 2 != 0) throw KnownException("key hex 长度异常")
        return hex.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
    }

    private fun asResource(file: File, mediaType: MediaType): ResponseEntity<FileSystemResource> {
        if (!file.exists() || !file.isFile) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
        val resource = FileSystemResource(file)
        return ResponseEntity.ok()
            .contentType(mediaType)
            .header(HttpHeaders.CONTENT_LENGTH, file.length().toString())
            .body(resource)
    }

    private fun loadQualityPolicies(filePostId: Long): List<PolicyPayload> {
        val policiesJson = Mediator.queries.send(
            ListVideoQualityAuthQry.Request(videoFilePostId = filePostId, videoFileId = null)
        ).firstOrNull()?.policiesJson
        if (policiesJson.isNullOrBlank()) return emptyList()
        return JsonUtils.parseArray(policiesJson, PolicyPayload::class.java)
    }

    private fun loadAbrQualities(filePostId: Long): List<String> {
        return Mediator.queries.send(ListVideoAbrVariantsQry.Request(fileId = filePostId))
            .firstOrNull()
            ?.qualities
            ?: emptyList()
    }

    private fun resolveAllowedQualities(filePostId: Long): List<String> {
        val policies = loadQualityPolicies(filePostId)
        if (policies.isEmpty()) return emptyList()
        val allowLogin = StpUtil.isLogin()
        return policies.filter {
            isPlayable(resolvePolicy(it.authPolicy), allowLogin)
        }.map { it.quality }
    }

    private fun resolvePolicy(code: Int?): QualityAuthPolicy {
        return QualityAuthPolicy.valueOfOrNull(code) ?: QualityAuthPolicy.UNKNOW
    }

    private fun isPlayable(policy: QualityAuthPolicy, allowLogin: Boolean): Boolean {
        return when (policy) {
            QualityAuthPolicy.PUBLIC -> true
            QualityAuthPolicy.LOGIN -> allowLogin
            QualityAuthPolicy.PAID -> allowLogin
            QualityAuthPolicy.CUSTOM -> allowLogin
            else -> allowLogin
        }
    }

    private fun sortPolicies(policies: List<PolicyPayload>): List<PolicyPayload> {
        return policies.sortedWith(
            compareByDescending<PolicyPayload> { qualityScore(it.quality) }
                .thenBy { it.quality }
        )
    }

    private fun qualityScore(quality: String): Int {
        val number = QUALITY_NUMBER_REGEX.find(quality)?.groupValues?.getOrNull(1)?.toIntOrNull()
        return number ?: Int.MIN_VALUE
    }

    private fun filterMasterByAllowedQualities(content: String, allowedQualities: Set<String>): String {
        if (allowedQualities.isEmpty()) return content
        val lines = content.lines()
        val builder = StringBuilder()
        var i = 0
        while (i < lines.size) {
            val line = lines[i]
            if (line.startsWith("#EXT-X-STREAM-INF")) {
                val next = lines.getOrNull(i + 1)
                if (next != null && playlistAllowed(next.trim(), allowedQualities)) {
                    builder.appendLine(line)
                    builder.appendLine(next.trim())
                }
                i += 2
                continue
            }
            builder.appendLine(line)
            i += 1
        }
        return builder.toString()
    }

    private fun attachTokenToVariantPlaylists(content: String, token: String): String {
        val lines = content.lines()
        val builder = StringBuilder()
        lines.forEach { line ->
            val trimmed = line.trim()
            if (trimmed.isBlank() || trimmed.startsWith("#")) {
                builder.appendLine(line)
                return@forEach
            }
            if (trimmed.contains("token=")) {
                builder.appendLine(trimmed)
                return@forEach
            }
            val separator = if (trimmed.contains("?")) "&" else "?"
            builder.appendLine(trimmed + separator + "token=" + token)
        }
        return builder.toString()
    }

    private fun playlistAllowed(playlist: String, allowedQualities: Set<String>): Boolean {
        val normalized = playlist.substringBefore("?").trim()
        return allowedQualities.any { quality ->
            normalized == quality ||
                normalized.startsWith("$quality/") ||
                normalized.contains("/$quality/")
        }
    }

    private fun computeAllowedQualities(filePostId: Long): String? {
        val allowed = resolveAllowedQualities(filePostId)
        if (allowed.isEmpty()) return null
        return JsonUtils.toJsonString(allowed)
    }

    data class PolicyPayload(
        val quality: String,
        val authPolicy: Int
    )

    companion object {
        private val QUALITY_NUMBER_REGEX = Regex("(\\d+)")
    }
}
