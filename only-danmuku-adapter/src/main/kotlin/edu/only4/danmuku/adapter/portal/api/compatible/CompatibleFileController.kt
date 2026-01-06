package edu.only4.danmuku.adapter.portal.api.compatible

import cn.dev33.satoken.annotation.SaIgnore
import com.only.engine.satoken.utils.LoginHelper
import com.only.engine.web.annotation.IgnoreResultWrapper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.FileUploadVideo
import edu.only4.danmuku.application.commands.file_upload_session.CreateUploadSessionCmd
import edu.only4.danmuku.application.commands.file_upload_session.DeleteUploadSessionCmd
import edu.only4.danmuku.application.commands.file_upload_session.UploadVideoChunkCmd
import edu.only4.danmuku.application.distributed.clients.file_storage.UploadImageResourceCli
import edu.only4.danmuku.application.distributed.clients.file_upload_session.UploadVideoChunkStorageCli
import edu.only4.danmuku.application.queries.file_storage.GetResourceAccessUrlQry
import edu.only4.danmuku.application.queries.video_transcode.GetUploadSessionTempPathQry
import jakarta.validation.constraints.NotEmpty
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.net.URI

/**
 * 文件操作控制器 - 处理文件上传、资源获取等操作
 */
@RestController
@RequestMapping("/file")
@Validated
class CompatibleFileController {

    @SaIgnore
    @IgnoreResultWrapper
    @GetMapping("/getResource")
    fun getResource(
        @NotEmpty sourceName: String
    ): ResponseEntity<Unit> {
        val result = Mediator.queries.send(
            GetResourceAccessUrlQry.Request(resourceKey = sourceName)
        )
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create(result.url))
            .build()
    }

    /**
     * 预上传视频
     */
    @PostMapping("/preUploadVideo")
    fun filePreUploadVideo(
        @NotEmpty fileName: String,
        chunks: Int,
    ): Long {
        val currentUserId = LoginHelper.getUserId()!!
        val result = Mediator.commands.send(
            CreateUploadSessionCmd.Request(
                customerId = currentUserId,
                fileName = fileName,
                chunks = chunks
            )
        )
        return result.uploadId
    }

    /**
     * 上传视频分片
     */
    @PostMapping("/uploadVideo")
    fun fileUploadVideo(
        @RequestPart("chunkFile") chunkFile: MultipartFile,
        @RequestParam("chunkIndex") chunkIndex: Int,
        @RequestParam("uploadId") uploadId: Long,
    ): FileUploadVideo.Response {
        val currentUserId = LoginHelper.getUserId()!!
        val tempPath = Mediator.queries.send(
            GetUploadSessionTempPathQry.Request(uploadId = uploadId)
        ).tempPath
        val storageResp = Mediator.requests.send(
            UploadVideoChunkStorageCli.Request(
                tempPath = tempPath,
                chunkIndex = chunkIndex,
                chunkFile = chunkFile
            )
        )
        Mediator.commands.send(
            UploadVideoChunkCmd.Request(
                customerId = currentUserId,
                uploadId = uploadId,
                chunkIndex = chunkIndex,
                chunkSize = storageResp.size
            )
        )
        return FileUploadVideo.Response()
    }

    /**
     * 删除上传中的视频
     */
    @PostMapping("/delUploadVideo")
    fun fileDelUploadVideo(
        uploadId: Long,
    ) {
        val currentUserId = LoginHelper.getUserId()!!
        Mediator.commands.send(
            DeleteUploadSessionCmd.Request(
                customerId = currentUserId,
                uploadId = uploadId
            )
        )
    }

    @PostMapping("/uploadImage")
    fun uploadImage(
        file: MultipartFile,
        createThumbnail: Boolean,
    ): String {
        val resp = Mediator.requests.send(
            UploadImageResourceCli.Request(
                file = file,
                createThumbnail = createThumbnail,
                bizType = "COVER"
            )
        )
        return resp.resourceKey
    }

}
