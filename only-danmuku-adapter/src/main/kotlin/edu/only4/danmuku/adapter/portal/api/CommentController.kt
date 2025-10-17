package edu.only4.danmuku.adapter.portal.api

import edu.only4.danmuku.adapter.portal.api.payload.*
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 视频评论控制器
 */
@RestController
@RequestMapping("/comment")
@Validated
class CommentController {

    /**
     * 加载评论列表
     */
    @PostMapping("/loadComment")
    fun commentLoad(@RequestBody @Validated request: CommentLoad.Request): CommentLoad.Response {
        // TODO: 实现加载评论列表逻辑
        return CommentLoad.Response()
    }

    /**
     * 发表评论
     */
    @PostMapping("/postComment")
    fun commentPost(@RequestBody @Validated request: CommentPost.Request): CommentPost.Response {
        // TODO: 实现发表评论逻辑
        return CommentPost.Response()
    }

    /**
     * 用户删除评论
     */
    @PostMapping("/userDelComment")
    fun commentUserDel(@RequestBody @Validated request: CommentUserDel.Request): CommentUserDel.Response {
        // TODO: 实现用户删除评论逻辑
        return CommentUserDel.Response()
    }

    /**
     * 置顶评论
     */
    @PostMapping("/topComment")
    fun commentTop(@RequestBody @Validated request: CommentTop.Request): CommentTop.Response {
        // TODO: 实现置顶评论逻辑
        return CommentTop.Response()
    }

    /**
     * 取消置顶评论
     */
    @PostMapping("/cancelTopComment")
    fun commentCancelTop(@RequestBody @Validated request: CommentCancelTop.Request): CommentCancelTop.Response {
        // TODO: 实现取消置顶评论逻辑
        return CommentCancelTop.Response()
    }

}
