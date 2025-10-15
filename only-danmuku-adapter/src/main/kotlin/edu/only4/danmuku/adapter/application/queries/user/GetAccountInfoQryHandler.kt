package edu.only4.danmuku.adapter.application.queries.user

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.user.GetAccountInfoQry

import org.springframework.stereotype.Service

/**
 * 获取用户信息
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetAccountInfoQryHandler(
) : Query<GetAccountInfoQry.Request, GetAccountInfoQry.Response> {

    override fun exec(request: GetAccountInfoQry.Request): GetAccountInfoQry.Response {

        return GetAccountInfoQry.Response(

        )
    }
}
