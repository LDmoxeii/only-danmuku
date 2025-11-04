package edu.only4.danmuku.adapter.application.queries.video

import com.only4.cap4k.ddd.core.application.query.ListQuery
import edu.only4.danmuku.application.queries._share.model.dto.Video.VideoListItem
import edu.only4.danmuku.application.queries._share.model.playCount
import edu.only4.danmuku.application.queries._share.model.recommendType
import edu.only4.danmuku.application.queries.video.GetRecommendVideosQry
import edu.only4.danmuku.domain.aggregates.video.enums.RecommendType
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.desc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 获取推荐视频列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetRecommendVideosQryHandler(
    private val sqlClient: KSqlClient,
) : ListQuery<GetRecommendVideosQry.Request, GetRecommendVideosQry.Response> {

    override fun exec(request: GetRecommendVideosQry.Request): List<GetRecommendVideosQry.Response> {
        // 查询推荐视频列表 (recommendType = 2 表示已推荐)
        val videoList = sqlClient.findAll(VideoListItem::class) {
            where(table.recommendType eq RecommendType.RECOMMEND)
            orderBy(table.playCount.desc())  // 按播放数降序
        }

        // 转换为响应格式
        return videoList.map { video ->
            GetRecommendVideosQry.Response(
                videoId = video.id,
                videoCover = video.videoCover,
                videoName = video.videoName,
                userId = video.customer.id,
                nickName = video.customer.relation!!.nickName,
                avatar = video.customer.relation!!.nickName,
                playCount = video.playCount,
                likeCount = video.likeCount,
                createTime = video.createTime ?: 0L
            )
        }
    }
}
