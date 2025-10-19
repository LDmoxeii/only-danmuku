package edu.only4.danmuku.adapter.application.queries.video_play_history

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.application.queries._share.draft.video_play_history.PlayHistoryPageItem
import edu.only4.danmuku.application.queries._share.model.video_play_history.JVideoPlayHistory
import edu.only4.danmuku.application.queries._share.model.video_play_history.createTime
import edu.only4.danmuku.application.queries._share.model.video_play_history.customerId
import edu.only4.danmuku.application.queries.video_play_history.GetUserPlayHistoryQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.desc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 获取用户播放历史
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetUserPlayHistoryQryHandler(
    private val sqlClient: KSqlClient,
) : PageQuery<GetUserPlayHistoryQry.Request, GetUserPlayHistoryQry.Response> {

    override fun exec(request: GetUserPlayHistoryQry.Request): PageData<GetUserPlayHistoryQry.Response> {
        // 使用 Jimmer 分页查询播放历史
        val pageResult = sqlClient.createQuery(JVideoPlayHistory::class) {
            // 按用户ID过滤
            where(table.customerId eq request.customerId)

            // 按播放时间倒序排列（最新的在前）
            orderBy(table.createTime.desc())

            // DTO 投影
            select(table.fetch(PlayHistoryPageItem::class))
        }.fetchPage(request.pageNum - 1, request.pageSize)

        // 转换为查询响应
        val responses = pageResult.rows.map { history ->
            GetUserPlayHistoryQry.Response(
                historyId = history.id,
                customerId = history.customerId,
                videoId = history.videoId,
                videoName = history.video.videoName,
                videoCover = history.video.videoCover,
                fileIndex = history.fileIndex,
                playTime = history.createTime ?: 0L
            )
        }

        return PageData.create(
            pageNum = request.pageNum,
            pageSize = request.pageSize,
            list = responses,
            totalCount = pageResult.totalRowCount
        )
    }
}
