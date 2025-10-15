package edu.only4.danmuku.adapter.application.queries.customer_video_series

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.customer_video_series.GetCustomerVideoSeriesVideoQry

import org.springframework.stereotype.Service

/**
 * 获取用户视频系列视频
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetCustomerVideoSeriesVideoQryHandler(
) : Query<GetCustomerVideoSeriesVideoQry.Request, GetCustomerVideoSeriesVideoQry.Response> {

    override fun exec(request: GetCustomerVideoSeriesVideoQry.Request): GetCustomerVideoSeriesVideoQry.Response {

        return GetCustomerVideoSeriesVideoQry.Response(

        )
    }
}
