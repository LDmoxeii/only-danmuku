package edu.only4.danmuku.adapter.application.queries.customer_video_series

import com.only4.cap4k.ddd.core.application.query.ListQuery
import edu.only4.danmuku.application.queries._share.model.customerId
import edu.only4.danmuku.application.queries._share.model.dto.CustomerVideoSeries.CustomerVideoSeriesDetail
import edu.only4.danmuku.application.queries.customer_video_series.GetCustomerVideoSeriesVideoQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 获取用户视频系列及关联视频
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetCustomerVideoSeriesVideoQryHandler(
    private val sqlClient: KSqlClient,
) : ListQuery<GetCustomerVideoSeriesVideoQry.Request, GetCustomerVideoSeriesVideoQry.Response> {

    override fun exec(request: GetCustomerVideoSeriesVideoQry.Request): List<GetCustomerVideoSeriesVideoQry.Response> {
        // 查询用户的所有系列及其视频
        val seriesList = sqlClient.findAll(CustomerVideoSeriesDetail::class) {
            where(table.customerId eq request.userId)
        }

        // 转换为响应格式
        return seriesList.map { series ->
            GetCustomerVideoSeriesVideoQry.Response(
                seriesId = series.id,
                seriesName = series.seriesName,
                seriesDescription = series.seriesDescription,
                sort = series.sort,
                videoList = series.seriesVideos.map { seriesVideo ->
                    GetCustomerVideoSeriesVideoQry.VideoItem(
                        videoId = seriesVideo.video.id,
                        videoCover = seriesVideo.video.videoCover,
                        videoName = seriesVideo.video.videoName,
                        playCount = seriesVideo.video.playCount,
                        sort = seriesVideo.sort
                    )
                }
            )
        }
    }
}
