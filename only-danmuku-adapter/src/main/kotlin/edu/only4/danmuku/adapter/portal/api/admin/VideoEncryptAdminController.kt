package edu.only4.danmuku.adapter.portal.api.admin

import cn.dev33.satoken.annotation.SaIgnore
import com.only.engine.exception.KnownException
import com.only.engine.web.annotation.IgnoreResultWrapper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.video_encrypt.AdminGetEncKey
import edu.only4.danmuku.adapter.portal.api.payload.video_encrypt.AdminGetEncMaster
import edu.only4.danmuku.adapter.portal.api.payload.video_encrypt.AdminGetEncSegment
import edu.only4.danmuku.adapter.portal.api.payload.video_encrypt.AdminGetEncVariantPlaylist
import edu.only4.danmuku.adapter.portal.api.payload.video_encrypt.AdminIssueEncToken
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.application.commands.video_encrypt.ConsumeVideoHlsKeyTokenCmd
import edu.only4.danmuku.application.commands.video_encrypt.IssueVideoHlsKeyTokenCmd
import edu.only4.danmuku.application.queries.video_encrypt.GetLatestVideoHlsKeyQry
import edu.only4.danmuku.application.queries.video_encrypt.GetVideoEncryptStatusQry
import edu.only4.danmuku.application.queries.video_transcode.GetVideoFilePostPathQry
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.File

@SaIgnore
@RestController
@RequestMapping("/admin/video/enc")
class VideoEncryptAdminController(
    private val fileProps: FileAppProperties,
) {

    @PostMapping("/token")
    fun issueToken(@RequestBody req: AdminIssueEncToken.Request): AdminIssueEncToken.Response {
        val latestKey = Mediator.queries.send(
            GetLatestVideoHlsKeyQry.Request(videoFilePostId = req.filePostId, videoFileId = null)
        )
        val keyId = latestKey.keyId ?: throw KnownException("未找到可用密钥")
        val keyVersion = latestKey.keyVersion ?: throw KnownException("未找到可用密钥版本")

        val resp = Mediator.commands.send(
            IssueVideoHlsKeyTokenCmd.Request(
                videoFilePostId = req.filePostId,
                videoFileId = null,
                keyId = keyId,
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

    @IgnoreResultWrapper
    @GetMapping("/videoResource/{fileId}/master.m3u8")
    fun master(@PathVariable fileId: Long, @RequestParam token: String): ResponseEntity<String> {
        val status = Mediator.queries.send(
            GetVideoEncryptStatusQry.Request(videoFilePostId = fileId, videoFileId = null)
        )
        if (status.encryptStatus != "ENCRYPTED") throw KnownException("加密未完成: ${status.encryptStatus}")
        val path = status.encryptedMasterPath ?: throw KnownException("缺少加密路径")
        val masterFile = File(buildEncPath(path))
        val content = masterFile.readText().replace("__TOKEN__", token)
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
        val playlistFile = File(buildEncPath("$base/$quality/index.m3u8"))
        if (!playlistFile.exists()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
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
        val path = Mediator.queries.send(GetVideoFilePostPathQry.Request(filePostId = fileId)).filePath
            ?: throw KnownException("filePath 为空")
        val segmentFile = File(buildAbsolutePath(path, "enc/$quality/$ts"))
        return asResource(segmentFile, MediaType.valueOf("video/mp2t"))
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

    private fun buildEncPath(relative: String): String {
        return fileProps.projectFolder + Constants.FILE_FOLDER + relative.removePrefix("/")
    }

    private fun buildAbsolutePath(relativeBase: String, suffix: String): String {
        val normalizedSuffix = suffix.removePrefix("/")
        return fileProps.projectFolder + Constants.FILE_FOLDER + relativeBase.trimEnd('/') + "/" + normalizedSuffix
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
}
