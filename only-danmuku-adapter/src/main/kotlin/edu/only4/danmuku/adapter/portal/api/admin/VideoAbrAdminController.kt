package edu.only4.danmuku.adapter.portal.api.admin

import com.only.engine.exception.KnownException
import com.only.engine.web.annotation.IgnoreResultWrapper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.video_transcode.AdminListAbrVariants
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.application.queries.video_transcode.GetVideoAbrMasterQry
import edu.only4.danmuku.application.queries.video_transcode.GetVideoFilePostPathQry
import edu.only4.danmuku.application.queries.video_transcode.ListVideoAbrVariantsQry
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.File

@RestController
@RequestMapping("/admin/video/abr")
class VideoAbrAdminController(
    private val fileProps: FileAppProperties,
) {

    @IgnoreResultWrapper
    @GetMapping("/videoResource/{fileId}/master.m3u8")
    fun master(@PathVariable fileId: Long): ResponseEntity<FileSystemResource> {
        val filePath = Mediator.queries.send(GetVideoFilePostPathQry.Request(filePostId = fileId)).filePath
            ?: throw KnownException("filePath 为空")
        val master = Mediator.queries.send(GetVideoAbrMasterQry.Request(fileId = fileId))
        if (master.status != "SUCCESS") throw KnownException("转码未完成: ${master.status}")
        val masterFile = File(buildAbsolutePath(filePath, "master.m3u8"))
        return asResource(masterFile, MediaType.valueOf("application/vnd.apple.mpegurl"))
    }

    @PostMapping("/variants")
    fun variants(@RequestBody request: AdminListAbrVariants.Request): AdminListAbrVariants.Response {
        val resp = Mediator.queries.send(
            ListVideoAbrVariantsQry.Request(fileId = request.fileId)
        ).firstOrNull() ?: return AdminListAbrVariants.Response(emptyList(), "[]")
        return AdminListAbrVariants.Response(resp.qualities, resp.variantJson)
    }

    @IgnoreResultWrapper
    @GetMapping("/videoResource/{fileId}/{quality}/index.m3u8")
    fun playlist(
        @PathVariable fileId: Long,
        @PathVariable quality: String
    ): ResponseEntity<FileSystemResource> {
        val base = Mediator.queries.send(GetVideoFilePostPathQry.Request(filePostId = fileId)).filePath
            ?: throw KnownException("filePath 为空")
        val playlistFile = File(buildAbsolutePath(base, "$quality/index.m3u8"))
        return asResource(playlistFile, MediaType.valueOf("application/vnd.apple.mpegurl"))
    }

    @IgnoreResultWrapper
    @GetMapping("/videoResource/{fileId}/{quality}/{ts}")
    fun segment(
        @PathVariable fileId: Long,
        @PathVariable quality: String,
        @PathVariable ts: String
    ): ResponseEntity<FileSystemResource> {
        val base = Mediator.queries.send(GetVideoFilePostPathQry.Request(filePostId = fileId)).filePath
            ?: throw KnownException("filePath 为空")
        val segmentFile = File(buildAbsolutePath(base, "$quality/$ts"))
        return asResource(segmentFile, MediaType.valueOf("video/mp2t"))
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
