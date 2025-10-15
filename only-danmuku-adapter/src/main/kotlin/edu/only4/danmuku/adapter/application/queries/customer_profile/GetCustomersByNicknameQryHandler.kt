package edu.only4.danmuku.adapter.application.queries.customer_profile

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.customer_profile.GetCustomersByNicknameQry

import org.springframework.stereotype.Service

/**
 * 根据昵称搜索用户
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetCustomersByNicknameQryHandler(
) : Query<GetCustomersByNicknameQry.Request, GetCustomersByNicknameQry.Response> {

    override fun exec(request: GetCustomersByNicknameQry.Request): GetCustomersByNicknameQry.Response {

        return GetCustomersByNicknameQry.Response(

        )
    }
}
