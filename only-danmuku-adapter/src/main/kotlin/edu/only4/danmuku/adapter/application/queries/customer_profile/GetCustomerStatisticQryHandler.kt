package edu.only4.danmuku.adapter.application.queries.customer_profile

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.customer_profile.GetCustomerStatisticQry

import org.springframework.stereotype.Service

/**
 * 获取用户统计信息
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetCustomerStatisticQryHandler(
) : Query<GetCustomerStatisticQry.Request, GetCustomerStatisticQry.Response> {

    override fun exec(request: GetCustomerStatisticQry.Request): GetCustomerStatisticQry.Response {

        return GetCustomerStatisticQry.Response(

        )
    }
}
