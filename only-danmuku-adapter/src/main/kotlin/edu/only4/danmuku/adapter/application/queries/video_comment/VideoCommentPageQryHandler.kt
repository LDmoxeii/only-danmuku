package edu.only4.danmuku.adapter.application.queries.video_comment

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.application.queries._share.draft.video_comment.CommentPageItem
import edu.only4.danmuku.application.queries._share.model.video.videoName
import edu.only4.danmuku.application.queries._share.model.video_comment.*
import edu.only4.danmuku.application.queries.video_comment.VideoCommentPageQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.*
import org.springframework.stereotype.Service

/**
 * 评论分页
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class VideoCommentPageQryHandler(
    private val sqlClient: KSqlClient,
) : PageQuery<VideoCommentPageQry.Request, VideoCommentPageQry.Response> {

    override fun exec(request: VideoCommentPageQry.Request): PageData<VideoCommentPageQry.Response> {
        // 使用 Jimmer 查询评论分页数据，使用 DTO 投影
        val pageResult = sqlClient.createQuery(JVideoComment::class) {
            // 视频ID精确查询（可选）
            where(table.videoId `eq?` request.videoId)
            // 视频名称模糊查询（可选）
            where(table.video.videoName `ilike?` request.videoNameFuzzy)
            // 按发布时间倒序排列
            orderBy(table.postTime.desc())
            // DTO 投影
            select(table.fetch(CommentPageItem::class))
        }.fetchPage(request.pageNum - 1, request.pageSize)

        // 将 DTO 转换为查询响应
        val responseList = pageResult.rows.map { item ->
            toResponse(item, loadChildren = true)
        }

        // 返回分页结果
        return PageData.create(
            pageNum = request.pageNum,
            pageSize = request.pageSize,
            list = responseList,
            totalCount = pageResult.totalRowCount
        )
    }

    /**
     * 将 DTO 转换为查询响应，支持递归加载子评论
     */
    private fun toResponse(item: CommentPageItem, loadChildren: Boolean): VideoCommentPageQry.Response {
        // 查询子评论
        val children = if (loadChildren) {
            loadChildComments(item.id)
        } else {
            emptyList()
        }

        return VideoCommentPageQry.Response(
            commentId = item.id,
            parentCommentId = item.parentId,
            videoId = item.videoId,
            videoUserId = item.video.customerId,
            videoName = item.video.videoName,
            videoCover = item.video.videoCover,
            content = item.content,
            imgPath = item.imgPath,
            customerId = item.customerId,
            customerNickname = item.customer.nickName,
            customerAvatar = item.customer.avatar,
            replyCustomerId = item.replyCustomer?.id,
            replyCustomerNickname = item.replyCustomer?.nickName,
            postTime = item.postTime,
            likeCount = item.likeCount,
            hateCount = item.hateCount,
            topType = item.topType,
            childrenCount = children.size,
            children = children.ifEmpty { null }
        )
    }

    /**
     * 递归加载子评论
     */
    private fun loadChildComments(parentCommentId: Long): List<VideoCommentPageQry.Response> {
        // 查询子评论列表
        val childComments = sqlClient.createQuery(JVideoComment::class) {
            where(table.parentId eq parentCommentId)
            orderBy(table.postTime.asc()) // 子评论按时间正序
            select(table.fetch(CommentPageItem::class))
        }.execute()

        // 递归转换子评论
        return childComments.map { child ->
            toResponse(child, loadChildren = true) // 递归加载
        }
    }
}
