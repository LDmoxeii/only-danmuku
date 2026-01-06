package edu.only4.danmuku.adapter.portal.api.admin

import cn.dev33.satoken.annotation.SaIgnore
import com.only.engine.exception.KnownException
import com.only.engine.oss.factory.OssFactory
import com.only.engine.web.annotation.IgnoreResultWrapper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.video_transcode.AdminListAbrVariants
import edu.only4.danmuku.application.queries.file_storage.GetResourceAccessUrlQry
import edu.only4.danmuku.application.queries.video_transcode.GetVideoAbrMasterQry
import edu.only4.danmuku.application.queries.video_transcode.GetVideoFilePostPathQry
import edu.only4.danmuku.application.queries.video_transcode.ListVideoAbrVariantsQry
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.nio.charset.StandardCharsets

@SaIgnore
@RestController
@RequestMapping("/admin/video/abr")
class VideoAbrAdminController {

    @IgnoreResultWrapper
    @GetMapping("/videoResource/{fileId}/master.m3u8")
    fun master(@PathVariable fileId: Long): ResponseEntity<String> {
        val outputPrefix = Mediator.queries.send(GetVideoFilePostPathQry.Request(filePostId = fileId)).filePath
            ?: throw KnownException("播放路径为空")
        val master = Mediator.queries.send(GetVideoAbrMasterQry.Request(fileId = fileId))
        if (master.status != "SUCCESS") throw KnownException("转码未完成: ${master.status}")
        val objectKey = outputPrefix.trimEnd('/') + "/master.m3u8"
        val content = readObjectAsText(objectKey)
        return ResponseEntity.ok()
            .contentType(MediaType.valueOf("application/vnd.apple.mpegurl"))
            .header(HttpHeaders.CONTENT_LENGTH, content.toByteArray().size.toString())
            .body(content)
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
    ): ResponseEntity<String> {
        val outputPrefix = Mediator.queries.send(GetVideoFilePostPathQry.Request(filePostId = fileId)).filePath
            ?: throw KnownException("播放路径为空")
        val objectKey = outputPrefix.trimEnd('/') + "/$quality/index.m3u8"
        val content = readObjectAsText(objectKey)
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
        val outputPrefix = Mediator.queries.send(GetVideoFilePostPathQry.Request(filePostId = fileId)).filePath
            ?: throw KnownException("播放路径为空")
        val url = resolveUrl(outputPrefix, "$quality/$ts")
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create(url))
            .build()
    }

    private fun resolveUrl(base: String, suffix: String): String {
        val objectKey = base.trimEnd('/') + "/" + suffix.removePrefix("/")
        return Mediator.queries.send(
            GetResourceAccessUrlQry.Request(resourceKey = objectKey)
        ).url
    }

    private fun readObjectAsText(objectKey: String): String {
        return OssFactory.instance().getObjectContent(objectKey)
            .bufferedReader(StandardCharsets.UTF_8)
            .use { it.readText() }
    }
}
