package edu.only4.danmuku.adapter.portal.api.web

import cn.dev33.satoken.annotation.SaIgnore
import com.only.engine.exception.KnownException
import com.only.engine.web.annotation.IgnoreResultWrapper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.video_abr.GetVideoVariants
import edu.only4.danmuku.application.distributed.clients.oss.ReadObjectAsTextCli
import edu.only4.danmuku.application.queries.video_storage.GetVideoHlsResourceUrlQry
import edu.only4.danmuku.application.queries.video_transcode.GetVideoAbrMasterQry
import edu.only4.danmuku.application.queries.video_transcode.GetVideoPostIdByFileIdQry
import edu.only4.danmuku.application.queries.video_transcode.ListVideoAbrVariantsQry
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@SaIgnore
@RestController
@RequestMapping("/video/abr")
class VideoAbrController {

    @IgnoreResultWrapper
    @GetMapping("/videoResource/{fileId}/master.m3u8")
    fun master(@PathVariable fileId: Long): ResponseEntity<String> {
        val post = Mediator.queries.send(GetVideoPostIdByFileIdQry.Request(fileId = fileId))
        val master = Mediator.queries.send(GetVideoAbrMasterQry.Request(fileId = post.filePostId))
        if (master.status != "SUCCESS") throw KnownException("转码未完成: ${master.status}")
        val base = post.filePath ?: throw KnownException("filePath 为空: $fileId")
        val objectKey = base.trimEnd('/') + "/master.m3u8"
        val content = Mediator.requests.send(
            ReadObjectAsTextCli.Request(objectKey)
        ).content
        return ResponseEntity.ok()
            .contentType(MediaType.valueOf("application/vnd.apple.mpegurl"))
            .header(HttpHeaders.CONTENT_LENGTH, content.toByteArray().size.toString())
            .body(content)
    }

    @PostMapping("/variants")
    fun variants(@RequestBody request: GetVideoVariants.Request): GetVideoVariants.Response {
        val post = Mediator.queries.send(GetVideoPostIdByFileIdQry.Request(fileId = request.fileId))
        val resp = Mediator.queries.send(
            ListVideoAbrVariantsQry.Request(fileId = post.filePostId)
        ).firstOrNull() ?: return GetVideoVariants.Response(emptyList(), "[]")
        return GetVideoVariants.Response(
            qualities = resp.qualities,
            variantJson = resp.variantJson
        )
    }

    @IgnoreResultWrapper
    @GetMapping("/videoResource/{fileId}/{quality}/index.m3u8")
    fun playlist(@PathVariable fileId: Long, @PathVariable quality: String): ResponseEntity<String> {
        val post = Mediator.queries.send(GetVideoPostIdByFileIdQry.Request(fileId = fileId))
        val base = post.filePath ?: throw KnownException("filePath 为空: $fileId")
        val objectKey = base.trimEnd('/') + "/$quality/index.m3u8"
        val content = Mediator.requests.send(
            ReadObjectAsTextCli.Request(objectKey)
        ).content
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
        val url = Mediator.queries.send(
            GetVideoHlsResourceUrlQry.Request(
                videoFileId = fileId,
                relativePath = "$quality/$ts"
            )
        ).url
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create(url))
            .build()
    }
}
