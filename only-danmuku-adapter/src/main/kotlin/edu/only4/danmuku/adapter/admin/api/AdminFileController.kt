package edu.only4.danmuku.adapter.admin.api

import edu.only4.danmuku.adapter.admin.api.payload.AdminFileGetResource
import edu.only4.danmuku.adapter.admin.api.payload.AdminFileGetVideoResource
import edu.only4.danmuku.adapter.admin.api.payload.AdminFileGetVideoResourceTs
import edu.only4.danmuku.adapter.admin.api.payload.AdminFileUploadImage
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
    fun adminFileUploadImage(@RequestBody @Validated request: AdminFileUploadImage.Request): AdminFileUploadImage.Response {
        // TODO: 实现上传图片逻辑
        return AdminFileUploadImage.Response()
    }

    /**
     * 获取资源文件
     */
    @GetMapping("/getResource")
    fun adminFileGetResource(@RequestParam sourceName: String = null): AdminFileGetResource.Response {
        // TODO: 实现获取资源文件逻辑
        return AdminFileGetResource.Response()
    }

    /**
     * 获取视频资源(m3u8)
     */
    @GetMapping("/videoResource/{fileId}")
    fun adminFileGetVideoResource(@PathVariable fileId: String = null): AdminFileGetVideoResource.Response {
        // TODO: 实现获取视频资源(m3u8)逻辑
        return AdminFileGetVideoResource.Response()
    }

    /**
     * 获取视频TS分片
     */
    @GetMapping("/videoResource/{fileId}/{ts}")
    fun adminFileGetVideoResourceTs(
        @PathVariable fileId: String = null,
        @PathVariable ts: String = null
    ): AdminFileGetVideoResourceTs.Response {
        // TODO: 实现获取视频TS分片逻辑
        return AdminFileGetVideoResourceTs.Response()
    }

}
