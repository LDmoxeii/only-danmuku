package edu.only4.danmuku.adapter.application.queries.video_draft

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.application.queries._share.model.*
import edu.only4.danmuku.application.queries.video_draft.GetUserVideoPostQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.`eq?`
import org.babyfish.jimmer.sql.kt.ast.expression.`ilike?`
import org.babyfish.jimmer.sql.kt.ast.expression.`valueNotIn?`
import org.springframework.stereotype.Service

/**
 * 获取用户视频草稿列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetUserVideoPostsQryHandler(
    private val sqlClient: KSqlClient,
) : PageQuery<GetUserVideoPostQry.Request, GetUserVideoPostQry.Response> {

    override fun exec(request: GetUserVideoPostQry.Request): PageData<GetUserVideoPostQry.Response> {

        val pageResult =
            sqlClient.createQuery(VideoPost::class) {
                where(table.customerId eq request.userId)
                where(table.status `eq?` request.status)
                where(table.videoName `ilike?` request.videoNameFuzzy)

                where(table.status `valueNotIn?` request.excludeStatusArray)

                select(table.fetchBy {
                    allScalarFields()
                    video {
                        allScalarFields()
                    }

                })
            }.fetchPage(request.pageNum - 1, request.pageSize)

        return PageData.create(
            pageNum = request.pageNum,
            pageSize = request.pageSize,
            list = pageResult.rows.map { post ->
                GetUserVideoPostQry.Response(
                    videoPostId = post.id,
                    videoId = post.video?.id,
                    videoCover = post.videoCover,
                    videoName = post.videoName,
                    duration = post.duration,
                    createTime = post.createTime ?: 0L,
                    lastUpdateTime = post.updateTime,
                    status = post.status,
                    interaction = post.interaction,
                    playCount = post.video?.playCount ?: 0,
                    likeCount = post.video?.likeCount ?: 0,
                    danmuCount = post.video?.danmukuCount ?: 0,
                    commentCount = post.video?.commentCount ?: 0,
                    coinCount = post.video?.coinCount ?: 0,
                    collectCount = post.video?.collectCount ?: 0
                )
            },
            totalCount = pageResult.totalRowCount
        )
    }
}
