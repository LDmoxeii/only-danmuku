package edu.only4.danmuku.adapter.portal.api.web

import cn.dev33.satoken.annotation.SaIgnore
import com.only.engine.exception.KnownException
import com.only.engine.web.annotation.IgnoreResultWrapper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.video_transcode.FrontListAbrVariants
import edu.only4.danmuku.application.queries.video_transcode.GetVideoAbrMasterQry
import edu.only4.danmuku.application.queries.video_transcode.GetVideoPostIdByFileIdQry
import edu.only4.danmuku.application.queries.video_transcode.ListVideoAbrVariantsQry
import edu.only4.danmuku.application.queries.video_storage.GetVideoHlsResourceUrlQry
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@SaIgnore
@RestController
@RequestMapping("/video/abr")
class VideoAbrController {

    @IgnoreResultWrapper
    @GetMapping("/videoResource/{fileId}/master.m3u8")
    fun master(@PathVariable fileId: Long): ResponseEntity<Void> {
        val post = Mediator.queries.send(GetVideoPostIdByFileIdQry.Request(fileId = fileId))
        val master = Mediator.queries.send(GetVideoAbrMasterQry.Request(fileId = post.filePostId))
        if (master.status != "SUCCESS") throw KnownException("转码未完成: ${master.status}")
        val url = Mediator.queries.send(
            GetVideoHlsResourceUrlQry.Request(
                videoFileId = fileId,
                relativePath = "master.m3u8"
            )
        ).url
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create(url))
            .build()
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
    @GetMapping("/videoResource/{fileId}/{quality}/index.m3u8")
    fun playlist(@PathVariable fileId: Long, @PathVariable quality: String): ResponseEntity<Void> {
        val url = Mediator.queries.send(
            GetVideoHlsResourceUrlQry.Request(
                videoFileId = fileId,
                relativePath = "$quality/index.m3u8"
            )
        ).url
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create(url))
            .build()
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
