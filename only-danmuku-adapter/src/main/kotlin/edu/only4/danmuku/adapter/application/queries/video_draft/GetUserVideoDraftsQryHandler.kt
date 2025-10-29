package edu.only4.danmuku.adapter.application.queries.video_draft

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.application.queries._share.model.*
import edu.only4.danmuku.application.queries.video_draft.GetUserVideoDraftsQry
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
class GetUserVideoDraftsQryHandler(
    private val sqlClient: KSqlClient,
) : PageQuery<GetUserVideoDraftsQry.Request, GetUserVideoDraftsQry.Response> {

    override fun exec(request: GetUserVideoDraftsQry.Request): PageData<GetUserVideoDraftsQry.Response> {

        // 查询用户视频草稿列表
        val pageResult =
            sqlClient.createQuery(VideoPost::class) {
                where(table.customerId eq request.userId)
                where(table.status `eq?` request.status)
                where(table.videoName `ilike?` request.videoNameFuzzy)

                // 如果有排除状态数组，则排除这些状态
                where(table.status `valueNotIn?` request.excludeStatusArray)

                select(table.fetchBy {
                    allScalarFields()
                    video {
                        allScalarFields()
                    }

                })
            }.fetchPage(request.pageNum - 1, request.pageSize)

        // 转换为响应格式
        return PageData.create(
            pageNum = request.pageNum,
            pageSize = request.pageSize,
            list = pageResult.rows.map { draft ->
                GetUserVideoDraftsQry.Response(
                    videoId = draft.id,
                    videoCover = draft.videoCover,
                    videoName = draft.videoName,
                    duration = draft.duration,
                    createTime = draft.createTime ?: 0L,
                    lastUpdateTime = draft.updateTime,
                    status = draft.status,
                    interaction = draft.interaction,
                    playCount = draft.video?.playCount ?: 0,
                    likeCount = draft.video?.likeCount ?: 0,
                    danmuCount = draft.video?.danmukuCount ?: 0,
                    commentCount = draft.video?.commentCount ?: 0,
                    coinCount = draft.video?.coinCount ?: 0,
                    collectCount = draft.video?.collectCount ?: 0
                )
            },
            totalCount = pageResult.totalRowCount
        )
    }
}
