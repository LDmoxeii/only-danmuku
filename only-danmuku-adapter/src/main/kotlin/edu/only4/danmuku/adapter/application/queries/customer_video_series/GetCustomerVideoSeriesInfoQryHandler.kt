package edu.only4.danmuku.adapter.application.queries.customer_video_series

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.dto.CustomerVideoSeries.CustomerVideoSeriesDetail
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries.customer_video_series.GetCustomerVideoSeriesInfoQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 获取用户视频系列信息
 */
@Service
class GetCustomerVideoSeriesInfoQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetCustomerVideoSeriesInfoQry.Request, GetCustomerVideoSeriesInfoQry.Response> {

    override fun exec(request: GetCustomerVideoSeriesInfoQry.Request): GetCustomerVideoSeriesInfoQry.Response {
        // 查询系列详情，携带系列视频列表
        val series = sqlClient.findOne(CustomerVideoSeriesDetail::class) {
            where(table.id eq request.seriesId)
        }

        return GetCustomerVideoSeriesInfoQry.Response(
            seriesId = series.id,
            userId = series.customerId,
            seriesName = series.seriesName,
            seriesDescription = series.seriesDescription,
            sort = series.sort,
            createTime = series.createTime ?: 0L,
            updateTime = series.createTime, // 暂无独立updateTime字段
            videoList = series.seriesVideos.map { seriesVideo ->
                GetCustomerVideoSeriesInfoQry.VideoItem(
                    videoId = seriesVideo.video.id,
                    videoCover = seriesVideo.video.videoCover,
                    videoName = seriesVideo.video.videoName,
                    playCount = seriesVideo.video.playCount,
                    sort = seriesVideo.sort,
                    createTime = seriesVideo.video.createTime!!
                )
            }
        )
    }
}

