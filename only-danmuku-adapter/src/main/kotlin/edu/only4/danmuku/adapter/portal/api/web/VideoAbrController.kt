package edu.only4.danmuku.adapter.portal.api.web

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.video_transcode.FrontGetAbrMaster
import edu.only4.danmuku.adapter.portal.api.payload.video_transcode.FrontGetAbrPlaylist
import edu.only4.danmuku.adapter.portal.api.payload.video_transcode.FrontGetAbrSegment
import edu.only4.danmuku.adapter.portal.api.payload.video_transcode.FrontListAbrVariants
import edu.only4.danmuku.application.queries.video_transcode.GetVideoAbrMasterQry
import edu.only4.danmuku.application.queries.video_transcode.GetVideoPostIdByFileIdQry
import edu.only4.danmuku.application.queries.video_transcode.ListVideoAbrVariantsQry
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/video/abr")
class VideoAbrController {

    @PostMapping("/master")
    fun master(@RequestBody request: FrontGetAbrMaster.Request): FrontGetAbrMaster.Response {
        val post = Mediator.queries.send(GetVideoPostIdByFileIdQry.Request(fileId = request.fileId))
        val master = Mediator.queries.send(GetVideoAbrMasterQry.Request(fileId = post.filePostId))
        val masterPath = post.filePath?.let { "$it/master.m3u8" }
        return FrontGetAbrMaster.Response(status = master.status, masterPath = masterPath)
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

    @PostMapping("/playlist")
    fun playlist(@RequestBody request: FrontGetAbrPlaylist.Request): FrontGetAbrPlaylist.Response {
        val post = Mediator.queries.send(GetVideoPostIdByFileIdQry.Request(fileId = request.fileId))
        val base = post.filePath ?: throw KnownException("filePath 为空")
        val playlistPath = "$base/${request.quality}/index.m3u8"
        return FrontGetAbrPlaylist.Response(playlistPath = playlistPath)
    }

    @PostMapping("/segment")
    fun segment(@RequestBody request: FrontGetAbrSegment.Request): FrontGetAbrSegment.Response {
        val post = Mediator.queries.send(GetVideoPostIdByFileIdQry.Request(fileId = request.fileId))
        val base = post.filePath ?: throw KnownException("filePath 为空")
        val segmentPath = "$base/${request.quality}/${request.ts}"
        return FrontGetAbrSegment.Response(segmentPath = segmentPath)
    }
}
