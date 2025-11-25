package edu.only4.danmuku.adapter.portal.api.admin

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.video_transcode.AdminGetAbrMaster
import edu.only4.danmuku.adapter.portal.api.payload.video_transcode.AdminGetAbrPlaylist
import edu.only4.danmuku.adapter.portal.api.payload.video_transcode.AdminGetAbrSegment
import edu.only4.danmuku.adapter.portal.api.payload.video_transcode.AdminListAbrVariants
import edu.only4.danmuku.application.queries.video_transcode.GetVideoAbrMasterQry
import edu.only4.danmuku.application.queries.video_transcode.GetVideoFilePostPathQry
import edu.only4.danmuku.application.queries.video_transcode.ListVideoAbrVariantsQry
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/video/abr")
class VideoAbrAdminController {

    @PostMapping("/master")
    fun master(@RequestBody request: AdminGetAbrMaster.Request): AdminGetAbrMaster.Response {
        val filePath = Mediator.queries.send(GetVideoFilePostPathQry.Request(filePostId = request.filePostId)).filePath
            ?: throw KnownException("filePath 为空")
        val master = Mediator.queries.send(GetVideoAbrMasterQry.Request(fileId = request.filePostId))
        val masterPath = "$filePath/master.m3u8"
        return AdminGetAbrMaster.Response(status = master.status, masterPath = masterPath)
    }

    @PostMapping("/variants")
    fun variants(@RequestBody request: AdminListAbrVariants.Request): AdminListAbrVariants.Response {
        val filePath = Mediator.queries.send(GetVideoFilePostPathQry.Request(filePostId = request.filePostId)).filePath
            ?: throw KnownException("filePath 为空")
        val resp = Mediator.queries.send(
            ListVideoAbrVariantsQry.Request(fileId = request.filePostId)
        ).firstOrNull() ?: return AdminListAbrVariants.Response(emptyList(), "[]")
        return AdminListAbrVariants.Response(resp.qualities, resp.variantJson)
    }

    @PostMapping("/playlist")
    fun playlist(@RequestBody request: AdminGetAbrPlaylist.Request): AdminGetAbrPlaylist.Response {
        val base = Mediator.queries.send(GetVideoFilePostPathQry.Request(filePostId = request.filePostId)).filePath
            ?: throw KnownException("filePath 为空")
        val playlistPath = "$base/${request.quality}/index.m3u8"
        return AdminGetAbrPlaylist.Response(playlistPath = playlistPath)
    }

    @PostMapping("/segment")
    fun segment(@RequestBody request: AdminGetAbrSegment.Request): AdminGetAbrSegment.Response {
        val base = Mediator.queries.send(GetVideoFilePostPathQry.Request(filePostId = request.filePostId)).filePath
            ?: throw KnownException("filePath 为空")
        val segmentPath = "$base/${request.quality}/${request.ts}"
        return AdminGetAbrSegment.Response(segmentPath = segmentPath)
    }
}
