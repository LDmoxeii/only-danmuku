package edu.only4.danmuku.adapter.portal.api.web

import cn.dev33.satoken.annotation.SaIgnore
import cn.dev33.satoken.stp.StpUtil
import com.only.engine.exception.KnownException
import com.only.engine.json.misc.JsonUtils
import com.only.engine.oss.factory.OssFactory
import com.only.engine.web.annotation.IgnoreResultWrapper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.video_encrypt.FrontIssueEncToken
import edu.only4.danmuku.adapter.portal.api.payload.video_encrypt.FrontListEncQualities
import edu.only4.danmuku.application.commands.video_encrypt.ConsumeVideoHlsKeyTokenCmd
import edu.only4.danmuku.application.commands.video_encrypt.IssueVideoHlsKeyTokenCmd
import edu.only4.danmuku.application.queries.file_storage.GetResourceAccessUrlQry
import edu.only4.danmuku.application.queries.video_encrypt.GetLatestVideoHlsKeyVersionQry
import edu.only4.danmuku.application.queries.video_encrypt.GetVideoEncryptStatusQry
import edu.only4.danmuku.application.queries.video_encrypt.ListVideoQualityAuthQry
import edu.only4.danmuku.application.queries.video.GetVideoFileContextByIdQry
import edu.only4.danmuku.application.queries.video.ListVideoFileVariantsQry
import edu.only4.danmuku.domain.aggregates.video_quality_policy.enums.QualityAuthPolicy
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.nio.charset.StandardCharsets

@SaIgnore
@RestController
@RequestMapping("/video/enc")
class VideoEncryptController {

    @PostMapping("/token")
    fun issueToken(@RequestBody req: FrontIssueEncToken.Request): FrontIssueEncToken.Response {
        val context = resolveFileContext(req.fileId)
        val latestKey = Mediator.queries.send(
            GetLatestVideoHlsKeyVersionQry.Request(
                videoPostId = context.videoPostId,
                fileIndex = context.fileIndex
            )
        )
        val keyVersion = latestKey.keyVersion ?: throw KnownException("未找到可用密钥版本")

        val allowedQualities = computeAllowedQualities(req.fileId)

        val resp = Mediator.commands.send(
            IssueVideoHlsKeyTokenCmd.Request(
                videoPostId = context.videoPostId,
                fileIndex = context.fileIndex,
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
        val policies = loadQualityPolicies(req.fileId)
        val allowLogin = StpUtil.isLogin()
        val items = if (policies.isEmpty()) {
            loadAbrQualities(req.fileId).map { quality ->
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
        val allowedQualities = resolveAllowedQualities(fileId)
        val masterKey = status.encryptedMasterPath ?: throw KnownException("缺少加密路径")
        val content = readObjectAsText(masterKey)
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
        val objectKey = "$base/$quality/index.m3u8"
        val content = runCatching { readObjectAsText(objectKey) }
            .getOrElse { return ResponseEntity.status(HttpStatus.NOT_FOUND).build() }
        val replaced = content.replace("__TOKEN__", token)
        return ResponseEntity.ok()
            .contentType(MediaType.valueOf("application/vnd.apple.mpegurl"))
            .header(HttpHeaders.CONTENT_LENGTH, replaced.toByteArray().size.toString())
            .body(replaced)
    }

    @IgnoreResultWrapper
    @GetMapping("/videoResource/{fileId}/{quality}/{ts}")
    fun segment(
        @PathVariable fileId: Long,
        @PathVariable quality: String,
        @PathVariable ts: String
    ): ResponseEntity<Void> {
        val status = encryptStatus(fileId)
        val base = status.encryptedMasterPath?.substringBeforeLast("/master.m3u8")
            ?: throw KnownException("缺少加密目录")
        val objectKey = "$base/$quality/$ts"
        val url = Mediator.queries.send(
            GetResourceAccessUrlQry.Request(resourceKey = objectKey)
        ).url
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create(url))
            .build()
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
        val context = resolveFileContext(videoFileId)
        return Mediator.queries.send(
            GetVideoEncryptStatusQry.Request(
                videoPostId = context.videoPostId,
                fileIndex = context.fileIndex
            )
        )
    }

    private fun hexToBytes(hex: String): ByteArray {
        if (hex.length % 2 != 0) throw KnownException("key hex 长度异常")
        return hex.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
    }

    private fun loadQualityPolicies(videoFileId: Long): List<PolicyPayload> {
        val policiesJson = Mediator.queries.send(
            ListVideoQualityAuthQry.Request(videoFilePostId = null, videoFileId = videoFileId)
        ).firstOrNull()?.policiesJson
        if (policiesJson.isNullOrBlank()) return emptyList()
        return JsonUtils.parseArray(policiesJson, PolicyPayload::class.java)
    }

    private fun loadAbrQualities(videoFileId: Long): List<String> {
        return Mediator.queries.send(ListVideoFileVariantsQry.Request(fileId = videoFileId))
            .firstOrNull()
            ?.qualities
            ?: emptyList()
    }

    private fun resolveFileContext(videoFileId: Long): GetVideoFileContextByIdQry.Response {
        return Mediator.queries.send(GetVideoFileContextByIdQry.Request(fileId = videoFileId))
    }

    private fun resolveAllowedQualities(videoFileId: Long): List<String> {
        val policies = loadQualityPolicies(videoFileId)
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

    private fun readObjectAsText(objectKey: String): String {
        return OssFactory.instance().getObjectContent(objectKey)
            .bufferedReader(StandardCharsets.UTF_8)
            .use { it.readText() }
    }

    private fun playlistAllowed(playlist: String, allowedQualities: Set<String>): Boolean {
        val normalized = playlist.substringBefore("?").trim()
        return allowedQualities.any { quality ->
            normalized == quality ||
                normalized.startsWith("$quality/") ||
                normalized.contains("/$quality/")
        }
    }

    private fun computeAllowedQualities(videoFileId: Long): String? {
        val policies = loadQualityPolicies(videoFileId)
        if (policies.isEmpty()) return null
        val allowLogin = StpUtil.isLogin()
        val allowed = policies.filter {
            isPlayable(resolvePolicy(it.authPolicy), allowLogin)
        }.map { it.quality }
        if (allowed.isEmpty()) {
            throw KnownException("无可用清晰度")
        }
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
