package edu.only4.danmuku.adapter.portal.api

import edu.only4.danmuku.adapter.portal.api.payload.*
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * 文件操作控制器 - 处理文件上传、资源获取等操作
 */
@RestController
@RequestMapping("/file/v2")
@Validated
class FileController {

    /**
     * 获取资源文件
     */
    @GetMapping("/getResource")
    fun fileGetResource(sourceName: String = ""): FileGetResource.Response {
        // TODO: 实现获取资源文件逻辑
        return FileGetResource.Response()
    }

    /**
     * 预上传视频
     */
    @PostMapping("/preUploadVideo")
    fun filePreUploadVideo(@RequestBody @Validated request: FilePreUploadVideo.Request): FilePreUploadVideo.Response {
        // TODO: 实现预上传视频逻辑
        return FilePreUploadVideo.Response()
    }

    /**
     * 上传视频分片
     */
    @PostMapping("/uploadVideo")
    fun fileUploadVideo(@RequestBody @Validated request: FileUploadVideo.Request): FileUploadVideo.Response {
        // TODO: 实现上传视频分片逻辑
        return FileUploadVideo.Response()
    }

    /**
     * 删除上传中的视频
     */
    @PostMapping("/delUploadVideo")
    fun fileDelUploadVideo(@RequestBody @Validated request: FileDelUploadVideo.Request): FileDelUploadVideo.Response {
        // TODO: 实现删除上传中的视频逻辑
        return FileDelUploadVideo.Response()
    }

    /**
     * 上传图片
     */
    @PostMapping("/uploadImage")
    fun fileUploadImage(@RequestBody @Validated request: FileUploadImage.Request): FileUploadImage.Response {
        // TODO: 实现上传图片逻辑
        return FileUploadImage.Response()
    }

    /**
     * 获取视频资源(m3u8)
     */
    @GetMapping("/videoResource/{fileId}")
    fun fileGetVideoResource(@PathVariable fileId: String = ""): FileGetVideoResource.Response {
        // TODO: 实现获取视频资源(m3u8)逻辑
        return FileGetVideoResource.Response()
    }

    /**
     * 获取视频TS分片
     */
    @GetMapping("/videoResource/{fileId}/{ts}")
    fun fileGetVideoResourceTs(
        @PathVariable fileId: String = "",
        @PathVariable ts: String = ""
    ): FileGetVideoResourceTs.Response {
        // TODO: 实现获取视频TS分片逻辑
        return FileGetVideoResourceTs.Response()
    }

}
