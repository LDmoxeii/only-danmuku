package edu.only4.danmuku.adapter.portal.api

import edu.only4.danmuku.adapter.portal.api.payload.AdminInteractDelComment
import edu.only4.danmuku.adapter.portal.api.payload.AdminInteractDelDanmu
import edu.only4.danmuku.adapter.portal.api.payload.AdminInteractLoadComment
import edu.only4.danmuku.adapter.portal.api.payload.AdminInteractLoadDanmu
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 管理员互动管理控制器 - 处理弹幕和评论管理
 */
@RestController
@RequestMapping("/admin/interact")
@Validated
class AdminInteractController {

    /**
     * 加载弹幕列表(分页)
     */
    @PostMapping("/loadDanmu")
    fun adminInteractLoadDanmu(@RequestBody request: AdminInteractLoadDanmu.Request): AdminInteractLoadDanmu.Response {
        // TODO: 实现加载弹幕列表(分页)逻辑
        return AdminInteractLoadDanmu.Response()
    }

    /**
     * 删除弹幕
     */
    @PostMapping("/delDanmu")
    fun adminInteractDelDanmu(@RequestBody @Validated request: AdminInteractDelDanmu.Request): AdminInteractDelDanmu.Response {
        // TODO: 实现删除弹幕逻辑
        return AdminInteractDelDanmu.Response()
    }

    /**
     * 加载评论列表(分页)
     */
    @PostMapping("/loadComment")
    fun adminInteractLoadComment(@RequestBody request: AdminInteractLoadComment.Request): AdminInteractLoadComment.Response {
        // TODO: 实现加载评论列表(分页)逻辑
        return AdminInteractLoadComment.Response()
    }

    /**
     * 删除评论
     */
    @PostMapping("/delComment")
    fun adminInteractDelComment(@RequestBody @Validated request: AdminInteractDelComment.Request): AdminInteractDelComment.Response {
        // TODO: 实现删除评论逻辑
        return AdminInteractDelComment.Response()
    }

}
