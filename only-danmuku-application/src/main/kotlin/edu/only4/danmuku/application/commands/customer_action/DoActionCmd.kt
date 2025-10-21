package edu.only4.danmuku.application.commands.customer_action

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.customer_profile.SCustomerProfile
import edu.only4.danmuku.domain._share.meta.video.SVideo
import edu.only4.danmuku.domain._share.meta.video_comment.SVideoComment
import edu.only4.danmuku.domain.aggregates.customer_action.enums.ActionType
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 执行用户行为（点赞/收藏/投币/评论赞踩）
 */
object DoActionCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val video = Mediator.repositories.findFirst(
                SVideo.predicateById(request.videoId),
                persist = false
            ).getOrNull() ?: throw KnownException("视频不存在：${request.videoId}")

            val type = ActionType.valueOf(request.actionType)

            when (type) {
                ActionType.LIKE_VIDEO -> {
                    video.likeCount = (video.likeCount ?: 0) + 1
                }

                ActionType.FAVORITE_VIDEO -> {
                    video.collectCount = (video.collectCount ?: 0) + 1
                }

                ActionType.COIN_VIDEO -> {
                    val coins = if (request.actionCount > 0) request.actionCount else 1
                    val profile = Mediator.repositories.findFirst(
                        SCustomerProfile.predicate { it.userId eq request.userId },
                        persist = false
                    ).getOrNull() ?: throw KnownException("用户资料不存在：${request.userId}")
                    if (profile.currentCoinCount < coins) {
                        throw KnownException("硬币不足")
                    }
                    profile.currentCoinCount = profile.currentCoinCount - coins
                    video.coinCount = (video.coinCount ?: 0) + coins
                }

                ActionType.LIKE_COMMENT -> {
                    val cid = request.commentId ?: throw KnownException("缺少评论ID")
                    val comment = Mediator.repositories.findFirst(
                        SVideoComment.predicateById(cid),
                        persist = false
                    ).getOrNull() ?: throw KnownException("评论不存在：$cid")
                    comment.likeCount = (comment.likeCount ?: 0) + 1
                }

                ActionType.HATE_COMMENT -> {
                    val cid = request.commentId ?: throw KnownException("缺少评论ID")
                    val comment = Mediator.repositories.findFirst(
                        SVideoComment.predicateById(cid),
                        persist = false
                    ).getOrNull() ?: throw KnownException("评论不存在：$cid")
                    comment.hateCount = (comment.hateCount ?: 0) + 1
                }

                else -> throw KnownException("不支持的行为类型: ${request.actionType}")
            }

            Mediator.uow.save()
            return Response()
        }
    }

    class Request(
        /** 用户ID */
        val userId: Long,
        /** 视频ID */
        val videoId: Long,
        /** 行为类型 (使用 ActionType.code) */
        val actionType: Int,
        /** 行为次数 (1或2，用于投币) */
        val actionCount: Int = 1,
        /** 评论ID (可选，针对评论的行为) */
        val commentId: Long? = null,
    ) : RequestParam<Response>

    class Response
}
