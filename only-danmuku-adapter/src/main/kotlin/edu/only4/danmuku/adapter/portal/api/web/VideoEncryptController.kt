package edu.only4.danmuku.adapter.portal.api.web

import cn.dev33.satoken.annotation.SaIgnore
import cn.dev33.satoken.stp.StpUtil
import com.only.engine.exception.KnownException
import com.only.engine.web.annotation.IgnoreResultWrapper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.video_encrypt.FrontGetEncKey
import edu.only4.danmuku.adapter.portal.api.payload.video_encrypt.FrontGetEncMaster
import edu.only4.danmuku.adapter.portal.api.payload.video_encrypt.FrontGetEncSegment
import edu.only4.danmuku.adapter.portal.api.payload.video_encrypt.FrontGetEncVariantPlaylist
import edu.only4.danmuku.adapter.portal.api.payload.video_encrypt.FrontIssueEncToken
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.application.commands.video_encrypt.ConsumeVideoHlsKeyTokenCmd
import edu.only4.danmuku.application.commands.video_encrypt.IssueVideoHlsKeyTokenCmd
import edu.only4.danmuku.application.queries.video_encrypt.GetLatestVideoHlsKeyQry
import edu.only4.danmuku.application.queries.video_encrypt.GetVideoEncryptStatusQry
import edu.only4.danmuku.application.queries.video_encrypt.ListVideoQualityAuthQry
import edu.only4.danmuku.application.queries.video_transcode.GetVideoPostIdByFileIdQry
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
        val masterFile = File(buildEncPath(status.encryptedMasterPath))
        if (!masterFile.exists()) {
            throw KnownException("master 文件不存在")
        }
        val content = masterFile.readText()
        val replaced = content.replace("__TOKEN__", token)
        return ResponseEntity.ok()
            .contentType(MediaType.valueOf("application/vnd.apple.mpegurl"))
            .header(HttpHeaders.CONTENT_LENGTH, replaced.toByteArray().size.toString())
            .body(replaced)
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
        val content = playlistFile.readText().replace("__TOKEN__", token)
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

    @PostMapping("/key")
    fun key(@RequestBody req: FrontGetEncKey.Request): FrontGetEncKey.Response {
        val resp = Mediator.commands.send(
            ConsumeVideoHlsKeyTokenCmd.Request(
                token = req.token,
                keyId = req.keyId,
                quality = req.quality
            )
        )
        if (!resp.valid) throw KnownException(resp.failReason ?: "token 无效")
        return FrontGetEncKey.Response(
            keyBytes = resp.keyPlainHex ?: ""
        )
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

    private fun computeAllowedQualities(filePostId: Long): String? {
        val policies = Mediator.queries.send(
            ListVideoQualityAuthQry.Request(videoFilePostId = filePostId, videoFileId = null)
        ).firstOrNull()?.policiesJson

        if (policies.isNullOrBlank()) return null
        val allowLogin = StpUtil.isLogin()
        val allowed = com.only.engine.json.misc.JsonUtils.parseArray(policies, PolicyPayload::class.java)
            .filter {
                when (it.authPolicy) {
                    1 -> true
                    2 -> allowLogin
                    else -> allowLogin // 付费/自定义此处先要求登录
                }
            }
            .map { it.quality }
        return if (allowed.isEmpty()) null else com.only.engine.json.misc.JsonUtils.toJsonString(allowed)
    }

    data class PolicyPayload(
        val quality: String,
        val authPolicy: Int
    )
}
