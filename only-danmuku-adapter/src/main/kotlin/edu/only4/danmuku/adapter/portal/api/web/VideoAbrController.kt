package edu.only4.danmuku.adapter.portal.api.web

import com.only.engine.exception.KnownException
import com.only.engine.web.annotation.IgnoreResultWrapper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.video_transcode.FrontGetAbrMaster
import edu.only4.danmuku.adapter.portal.api.payload.video_transcode.FrontGetAbrPlaylist
import edu.only4.danmuku.adapter.portal.api.payload.video_transcode.FrontGetAbrSegment
import edu.only4.danmuku.adapter.portal.api.payload.video_transcode.FrontListAbrVariants
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.application.queries.video_transcode.GetVideoAbrMasterQry
import edu.only4.danmuku.application.queries.video_transcode.GetVideoPostIdByFileIdQry
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
@RequestMapping("/api/video/abr")
class VideoAbrController(
    private val fileProps: FileAppProperties,
) {

    @IgnoreResultWrapper
    @PostMapping("/master")
    fun master(@RequestBody request: FrontGetAbrMaster.Request): ResponseEntity<FileSystemResource> {
        val post = Mediator.queries.send(GetVideoPostIdByFileIdQry.Request(fileId = request.fileId))
        val master = Mediator.queries.send(GetVideoAbrMasterQry.Request(fileId = post.filePostId))
        if (master.status != "SUCCESS") throw KnownException("转码未完成: ${master.status}")
        val masterFile = File(buildAbsolutePath(post.filePath, "master.m3u8"))
        return asResource(masterFile, MediaType.valueOf("application/vnd.apple.mpegurl"))
    }

    @PostMapping("/variants")
    fun variants(@RequestBody request: FrontListAbrVariants.Request): FrontListAbrVariants.Response {
        val post = Mediator.queries.send(GetVideoPostIdByFileIdQry.Request(fileId = request.fileId))
        val resp = Mediator.queries.send(
            ListVideoAbrVariantsQry.Request(fileId = post.filePostId)
        ).firstOrNull() ?: return FrontListAbrVariants.Response(emptyList(), "[]")
        return FrontListAbrVariants.Response(
            qualities = resp.qualities,
            variantJson = resp.variantJson
        )
    }

    @IgnoreResultWrapper
    @PostMapping("/playlist")
    fun playlist(@RequestBody request: FrontGetAbrPlaylist.Request): ResponseEntity<FileSystemResource> {
        val post = Mediator.queries.send(GetVideoPostIdByFileIdQry.Request(fileId = request.fileId))
        val base = post.filePath ?: throw KnownException("filePath 为空")
        val playlistFile = File(buildAbsolutePath(base, "${request.quality}/index.m3u8"))
        return asResource(playlistFile, MediaType.valueOf("application/vnd.apple.mpegurl"))
    }

    @IgnoreResultWrapper
    @PostMapping("/segment")
    fun segment(@RequestBody request: FrontGetAbrSegment.Request): ResponseEntity<FileSystemResource> {
        val post = Mediator.queries.send(GetVideoPostIdByFileIdQry.Request(fileId = request.fileId))
        val base = post.filePath ?: throw KnownException("filePath 为空")
        val segmentFile = File(buildAbsolutePath(base, "${request.quality}/${request.ts}"))
        return asResource(segmentFile, MediaType.valueOf("video/mp2t"))
    }

    private fun buildAbsolutePath(relativeBase: String?, suffix: String): String {
        val base = relativeBase ?: throw KnownException("filePath 为空")
        val normalizedSuffix = suffix.removePrefix("/")
        return fileProps.projectFolder + Constants.FILE_FOLDER + base.trimEnd('/') + "/" + normalizedSuffix
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
