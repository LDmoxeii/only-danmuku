package edu.only4.danmuku.adapter.portal.api.admin

import com.only.engine.exception.KnownException
import com.only.engine.web.annotation.IgnoreResultWrapper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.video_transcode.AdminGetAbrMaster
import edu.only4.danmuku.adapter.portal.api.payload.video_transcode.AdminGetAbrPlaylist
import edu.only4.danmuku.adapter.portal.api.payload.video_transcode.AdminGetAbrSegment
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
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File

@RestController
@RequestMapping("/api/admin/video/abr")
class VideoAbrAdminController(
    private val fileProps: FileAppProperties,
) {

    @IgnoreResultWrapper
    @PostMapping("/master")
    fun master(@RequestBody request: AdminGetAbrMaster.Request): ResponseEntity<FileSystemResource> {
        val filePath = Mediator.queries.send(GetVideoFilePostPathQry.Request(filePostId = request.filePostId)).filePath
            ?: throw KnownException("filePath 为空")
        val master = Mediator.queries.send(GetVideoAbrMasterQry.Request(fileId = request.filePostId))
        if (master.status != "SUCCESS") throw KnownException("转码未完成: ${master.status}")
        val masterFile = File(buildAbsolutePath(filePath, "master.m3u8"))
        return asResource(masterFile, MediaType.valueOf("application/vnd.apple.mpegurl"))
    }

    @PostMapping("/variants")
    fun variants(@RequestBody request: AdminListAbrVariants.Request): AdminListAbrVariants.Response {
        val resp = Mediator.queries.send(
            ListVideoAbrVariantsQry.Request(fileId = request.filePostId)
        ).firstOrNull() ?: return AdminListAbrVariants.Response(emptyList(), "[]")
        return AdminListAbrVariants.Response(resp.qualities, resp.variantJson)
    }

    @IgnoreResultWrapper
    @PostMapping("/playlist")
    fun playlist(@RequestBody request: AdminGetAbrPlaylist.Request): ResponseEntity<FileSystemResource> {
        val base = Mediator.queries.send(GetVideoFilePostPathQry.Request(filePostId = request.filePostId)).filePath
            ?: throw KnownException("filePath 为空")
        val playlistFile = File(buildAbsolutePath(base, "${request.quality}/index.m3u8"))
        return asResource(playlistFile, MediaType.valueOf("application/vnd.apple.mpegurl"))
    }

    @IgnoreResultWrapper
    @PostMapping("/segment")
    fun segment(@RequestBody request: AdminGetAbrSegment.Request): ResponseEntity<FileSystemResource> {
        val base = Mediator.queries.send(GetVideoFilePostPathQry.Request(filePostId = request.filePostId)).filePath
            ?: throw KnownException("filePath 为空")
        val segmentFile = File(buildAbsolutePath(base, "${request.quality}/${request.ts}"))
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
