package edu.only4.danmuku.adapter.portal.api

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.AdminFileGetResource
import edu.only4.danmuku.adapter.portal.api.payload.AdminFileGetVideoResource
import edu.only4.danmuku.adapter.portal.api.payload.AdminFileGetVideoResourceTs
import edu.only4.danmuku.adapter.portal.api.payload.AdminFileUploadImage
import edu.only4.danmuku.application.commands.file.SaveImageCmd
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * 管理员文件管理控制器
 */
@RestController
@RequestMapping("/admin/file")
@Validated
class AdminFileController {

    /**
     * 上传图片
     */
    @PostMapping("/uploadImage")
    fun adminFileUploadImage(@ModelAttribute @Validated request: AdminFileUploadImage.Request): AdminFileUploadImage.Response {
        val result = Mediator.commands.send(
            SaveImageCmd.Request(
                file = request.file,
                createThumbnail = request.createThumbnail
            )
        )
        return AdminFileUploadImage.Response(filePath = result.filePath)
    }

    /**
     * 获取资源文件
     */
    @GetMapping("/getResource")
    fun adminFileGetResource(@RequestParam sourceName: String = ""): AdminFileGetResource.Response {
        // TODO: 实现获取资源文件逻辑
        return AdminFileGetResource.Response()
    }

    /**
     * 获取视频资源(m3u8)
     */
    @GetMapping("/videoResource/{fileId}")
    fun adminFileGetVideoResource(@PathVariable fileId: String = ""): AdminFileGetVideoResource.Response {
        // TODO: 实现获取视频资源(m3u8)逻辑
        return AdminFileGetVideoResource.Response()
    }

    /**
     * 获取视频TS分片
     */
    @GetMapping("/videoResource/{fileId}/{ts}")
    fun adminFileGetVideoResourceTs(
        @PathVariable fileId: String = "",
        @PathVariable ts: String = ""
    ): AdminFileGetVideoResourceTs.Response {
        // TODO: 实现获取视频TS分片逻辑
        return AdminFileGetVideoResourceTs.Response()
    }

}
