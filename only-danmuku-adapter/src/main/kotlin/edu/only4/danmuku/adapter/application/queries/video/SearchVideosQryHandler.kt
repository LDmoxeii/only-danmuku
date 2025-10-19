package edu.only4.danmuku.adapter.application.queries.video

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.application.queries._share.draft.video.VideoSearchItem
import edu.only4.danmuku.application.queries._share.model.video.*
import edu.only4.danmuku.application.queries.video.SearchVideosQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.desc
import org.babyfish.jimmer.sql.kt.ast.expression.`eq?`
import org.babyfish.jimmer.sql.kt.ast.expression.`ilike?`
import org.springframework.stereotype.Service

/**
 * 搜索视频
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class SearchVideosQryHandler(
    private val sqlClient: KSqlClient,
) : PageQuery<SearchVideosQry.Request, SearchVideosQry.Response> {

    override fun exec(request: SearchVideosQry.Request): PageData<SearchVideosQry.Response> {
        // 使用 Jimmer 查询视频列表，关联用户档案表
        val pageResult = sqlClient.createQuery(JVideo::class) {
            // 用户ID过滤
            where(table.customerId `eq?` request.userId)
            // 视频名称模糊查询
            where(table.videoName `ilike?` request.videoNameFuzzy)
            // 状态过滤 (这里过滤的是推荐状态)
            where(table.recommendType `eq?` request.status?.toByte())
            // 按创建时间倒序
            orderBy(table.createTime.desc())
            // DTO 投影
            select(table.fetch(VideoSearchItem::class))
        }.fetchPage(request.pageNum - 1, request.pageSize)

        // 转换为查询响应
        val responseList = pageResult.rows.map { video ->
            SearchVideosQry.Response(
                videoId = video.id,
                videoCover = video.videoCover,
                videoName = video.videoName,
                userId = video.customerId,
                nickName = video.customer.nickName,
                avatar = video.customer.avatar,
                duration = video.duration,
                status = video.recommendType.toInt(),
                createTime = video.createTime ?: 0L,
                lastUpdateTime = video.updateTime,
                playCount = video.playCount,
                likeCount = video.likeCount,
                danmuCount = video.danmukuCount,
                commentCount = video.commentCount,
                coinCount = video.coinCount,
                collectCount = video.collectCount,
                recommendType = video.recommendType.toInt()
            )
        }

        // 返回分页结果
        return PageData.create(
            pageNum = request.pageNum,
            pageSize = request.pageSize,
            list = responseList,
            totalCount = pageResult.totalRowCount
        )
    }
}
