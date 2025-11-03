package edu.only4.danmuku.adapter.application.queries.video_play_history

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.application.queries._share.model.VideoPlayHistory
import edu.only4.danmuku.application.queries._share.model.customerId
import edu.only4.danmuku.application.queries._share.model.fetchBy
import edu.only4.danmuku.application.queries._share.model.updateTime
import edu.only4.danmuku.application.queries.video_play_history.GetUserPlayHistoryQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.desc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 获取用户播放历史记录
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/30
 */
@Service
class GetUserPlayHistoryQryHandler(
    private val sqlClient: KSqlClient,
) : PageQuery<GetUserPlayHistoryQry.Request, GetUserPlayHistoryQry.HistoryItem> {

    override fun exec(request: GetUserPlayHistoryQry.Request): PageData<GetUserPlayHistoryQry.HistoryItem> {
        // 分页查询用户播放历史，按最近更新时间倒序
        val pageResult = sqlClient.createQuery(VideoPlayHistory::class) {
            where(table.customerId eq request.customerId)
            orderBy(table.updateTime.desc())
            select(
                table.fetchBy {
                    // 基本标量字段（包含 id, createTime, updateTime, fileIndex 等）
                    allScalarFields()
                    // 关联视频信息（id、封面、标题等）
                    video {
                        allScalarFields()
                    }
                    customerId()
                }
            )
        }.fetchPage(request.pageNum - 1, request.pageSize)

        val list = pageResult.rows.map { item ->
            GetUserPlayHistoryQry.HistoryItem(
                historyId = item.id,
                customerId = item.customerId,
                videoId = item.video?.id,
                videoName = item.video?.videoName,
                videoCover = item.video?.videoCover,
                fileIndex = item.fileIndex,
                // 若无更新时间则回退到创建时间
                playTime = item.updateTime ?: item.createTime ?: 0L
            )
        }

        return PageData.create(
            pageNum = request.pageNum,
            pageSize = request.pageSize,
            list = list,
            totalCount = pageResult.totalRowCount
        )
    }
}
