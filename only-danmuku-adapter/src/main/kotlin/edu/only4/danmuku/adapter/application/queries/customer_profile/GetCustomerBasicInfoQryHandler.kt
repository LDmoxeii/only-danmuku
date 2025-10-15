package edu.only4.danmuku.adapter.application.queries.customer_profile

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.customer_profile.GetCustomerBasicInfoQry

import org.springframework.stereotype.Service

/**
 * 获取用户基本信息
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetCustomerBasicInfoQryHandler(
) : Query<GetCustomerBasicInfoQry.Request, GetCustomerBasicInfoQry.Response> {

    override fun exec(request: GetCustomerBasicInfoQry.Request): GetCustomerBasicInfoQry.Response {

        return GetCustomerBasicInfoQry.Response(

        )
    }
}
