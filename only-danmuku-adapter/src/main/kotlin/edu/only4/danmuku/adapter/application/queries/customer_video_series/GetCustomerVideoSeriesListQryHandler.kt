package edu.only4.danmuku.adapter.application.queries.customer_video_series

import com.only4.cap4k.ddd.core.application.query.ListQuery

import edu.only4.danmuku.application.queries.customer_video_series.GetCustomerVideoSeriesListQry

import org.springframework.stereotype.Service

/**
 * 获取用户视频系列
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetCustomerVideoSeriesListQryHandler(
) : ListQuery<GetCustomerVideoSeriesListQry.Request, GetCustomerVideoSeriesListQry.Response> {

    override fun exec(request: GetCustomerVideoSeriesListQry.Request): List<GetCustomerVideoSeriesListQry.Response> {

        return listOf()

    }
}
