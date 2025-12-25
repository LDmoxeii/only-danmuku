package edu.only4.danmuku.adapter.portal.api.admin

import cn.dev33.satoken.annotation.SaIgnore
import com.only.engine.exception.KnownException
import com.only.engine.oss.factory.OssFactory
import com.only.engine.web.annotation.IgnoreResultWrapper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.video_encrypt.AdminGetEncKey
import edu.only4.danmuku.adapter.portal.api.payload.video_encrypt.AdminIssueEncToken
import edu.only4.danmuku.adapter.portal.api.payload.video_encrypt.AdminRotateEncKey
import edu.only4.danmuku.application.commands.video_encrypt.ConsumeVideoHlsKeyTokenCmd
import edu.only4.danmuku.application.commands.video_encrypt.IssueVideoHlsKeyTokenCmd
import edu.only4.danmuku.application.commands.video_encrypt.RotateVideoHlsKeyBatchCmd
import edu.only4.danmuku.application.queries.file_storage.GetResourceAccessUrlQry
import edu.only4.danmuku.application.queries.video_encrypt.GetLatestVideoHlsKeyVersionQry
import edu.only4.danmuku.application.queries.video_encrypt.GetVideoEncryptStatusQry
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
@RequestMapping("/admin/video/enc")
class VideoEncryptAdminController {

    private val ossClient = OssFactory.instance()

    @PostMapping("/token")
    fun issueToken(@RequestBody req: AdminIssueEncToken.Request): AdminIssueEncToken.Response {
        val latestKey = Mediator.queries.send(
            GetLatestVideoHlsKeyVersionQry.Request(
                videoFilePostId = req.filePostId,
                videoFileId = null
            )
        )
        val keyVersion = latestKey.keyVersion ?: throw KnownException("未找到可用密钥版本")

        val resp = Mediator.commands.send(
            IssueVideoHlsKeyTokenCmd.Request(
                videoFilePostId = req.filePostId,
                videoFileId = null,
                keyVersion = keyVersion,
                audience = null,
                allowedQualities = null
            )
        )
        return AdminIssueEncToken.Response(
            token = resp.token,
            expireAt = resp.expireAt,
            allowedQualities = resp.allowedQualities
        )
    }

    @PostMapping("/rotateKey")
    fun rotateKey(@RequestBody req: AdminRotateEncKey.Request): AdminRotateEncKey.Response {
        val resp = Mediator.commands.send(
            RotateVideoHlsKeyBatchCmd.Request(
                videoFilePostId = req.filePostId,
                reason = req.reason
            )
        )
        return AdminRotateEncKey.Response(
            newKeyVersion = resp.newKeyVersion,
            failReason = resp.failReason
        )
    }

    @IgnoreResultWrapper
    @GetMapping("/videoResource/{fileId}/master.m3u8")
    fun master(@PathVariable fileId: Long, @RequestParam token: String): ResponseEntity<String> {
        val status = Mediator.queries.send(
            GetVideoEncryptStatusQry.Request(videoFilePostId = fileId, videoFileId = null)
        )
        if (status.encryptStatus != "ENCRYPTED") throw KnownException("加密未完成: ${status.encryptStatus}")
        val path = status.encryptedMasterPath ?: throw KnownException("缺少加密路径")
        val content = readObjectAsText(path).replace("__TOKEN__", token)
        return ResponseEntity.ok()
            .contentType(MediaType.valueOf("application/vnd.apple.mpegurl"))
            .header(HttpHeaders.CONTENT_LENGTH, content.toByteArray().size.toString())
            .body(content)
    }

    @IgnoreResultWrapper
    @GetMapping("/videoResource/{fileId}/{quality}/index.m3u8")
    fun playlist(
        @PathVariable fileId: Long,
        @PathVariable quality: String,
        @RequestParam token: String
    ): ResponseEntity<String> {
        val status = Mediator.queries.send(
            GetVideoEncryptStatusQry.Request(videoFilePostId = fileId, videoFileId = null)
        )
        val base = status.encryptedMasterPath?.substringBeforeLast("/master.m3u8")
            ?: throw KnownException("缺少加密目录")
        val objectKey = "$base/$quality/index.m3u8"
        val content = runCatching { readObjectAsText(objectKey) }
            .getOrElse { return ResponseEntity.status(HttpStatus.NOT_FOUND).build() }
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
    ): ResponseEntity<Void> {
        val status = Mediator.queries.send(
            GetVideoEncryptStatusQry.Request(videoFilePostId = fileId, videoFileId = null)
        )
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

    @PostMapping("/key")
    fun key(@RequestBody req: AdminGetEncKey.Request): AdminGetEncKey.Response {
        val resp = Mediator.commands.send(
            ConsumeVideoHlsKeyTokenCmd.Request(
                token = req.token,
                keyId = req.keyId,
                quality = req.quality
            )
        )
        if (!resp.valid) throw KnownException(resp.failReason ?: "token 无效")
        return AdminGetEncKey.Response(keyBytes = resp.keyPlainHex ?: "")
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

    private fun hexToBytes(hex: String): ByteArray {
        if (hex.length % 2 != 0) throw KnownException("key hex 长度异常")
        return hex.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
    }

    private fun readObjectAsText(objectKey: String): String {
        return ossClient.getObjectContent(objectKey)
            .bufferedReader(StandardCharsets.UTF_8)
            .use { it.readText() }
    }
}
