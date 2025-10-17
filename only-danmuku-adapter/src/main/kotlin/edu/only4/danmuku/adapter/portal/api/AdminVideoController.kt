package edu.only4.danmuku.adapter.portal.api

import edu.only4.danmuku.adapter.portal.api.payload.*
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 管理员视频管理控制器
 */
@RestController
@RequestMapping("/admin/videoInfo")
@Validated
class AdminVideoController {

    /**
     * 加载视频列表(分页)
     */
    @PostMapping("/loadVideoList")
    fun adminVideoLoadList(@RequestBody request: AdminVideoLoadList.Request): AdminVideoLoadList.Response {
        // TODO: 实现加载视频列表(分页)逻辑
        return AdminVideoLoadList.Response()
    }

    /**
     * 推荐视频
     */
    @PostMapping("/recommendVideo")
    fun adminVideoRecommend(@RequestBody @Validated request: AdminVideoRecommend.Request): AdminVideoRecommend.Response {
        // TODO: 实现推荐视频逻辑
        return AdminVideoRecommend.Response()
    }

    /**
     * 审核视频
     */
    @PostMapping("/auditVideo")
    fun adminVideoAudit(@RequestBody @Validated request: AdminVideoAudit.Request): AdminVideoAudit.Response {
        // TODO: 实现审核视频逻辑
        return AdminVideoAudit.Response()
    }

    /**
     * 删除视频
     */
    @PostMapping("/deleteVideo")
    fun adminVideoDelete(@RequestBody @Validated request: AdminVideoDelete.Request): AdminVideoDelete.Response {
        // TODO: 实现删除视频逻辑
        return AdminVideoDelete.Response()
    }

    /**
     * 加载视频分片列表
     */
    @PostMapping("/loadVideoPList")
    fun adminVideoLoadPList(@RequestBody @Validated request: AdminVideoLoadPList.Request): AdminVideoLoadPList.Response {
        // TODO: 实现加载视频分片列表逻辑
        return AdminVideoLoadPList.Response()
    }

}
