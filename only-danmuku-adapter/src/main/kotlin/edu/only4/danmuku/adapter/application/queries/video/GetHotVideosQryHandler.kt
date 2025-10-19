package edu.only4.danmuku.adapter.application.queries.video

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.application.queries._share.draft.video.VideoListItem
import edu.only4.danmuku.application.queries._share.model.video.JVideo
import edu.only4.danmuku.application.queries._share.model.video.likeCount
import edu.only4.danmuku.application.queries._share.model.video.playCount
import edu.only4.danmuku.application.queries.video.GetHotVideosQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.desc
import org.springframework.stereotype.Service

/**
 * 获取热门视频列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetHotVideosQryHandler(
    private val sqlClient: KSqlClient,
) : PageQuery<GetHotVideosQry.Request, GetHotVideosQry.Response> {

    override fun exec(request: GetHotVideosQry.Request): PageData<GetHotVideosQry.Response> {
        // 查询热门视频列表 (按播放数和点赞数排序)
        // 注意: 这里暂时忽略 lastPlayHour 参数，因为需要播放历史表来实现精确过滤
        // 简化实现：直接按播放数降序返回热门视频
        val pageResult = sqlClient.createQuery(JVideo::class) {
            // 按播放数降序，点赞数作为次要排序
            orderBy(table.playCount.desc(), table.likeCount.desc())
            // DTO投影
            select(table.fetch(VideoListItem::class))
        }.fetchPage(request.pageNum - 1, request.pageSize)

        // 转换为响应格式
        val responseList = pageResult.rows.map { video ->
            GetHotVideosQry.Response(
                videoId = video.id,
                videoCover = video.videoCover,
                videoName = video.videoName,
                userId = video.customerId,
                nickName = video.customer.nickName,
                avatar = video.customer.avatar,
                playCount = video.playCount,
                likeCount = video.likeCount,
                createTime = video.createTime ?: 0L
            )
        }

        return PageData.create(
            pageNum = request.pageNum,
            pageSize = request.pageSize,
            list = responseList,
            totalCount = pageResult.totalRowCount
        )
    }
}
