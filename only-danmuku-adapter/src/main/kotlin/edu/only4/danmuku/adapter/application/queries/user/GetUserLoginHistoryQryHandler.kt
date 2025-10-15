package edu.only4.danmuku.adapter.application.queries.user

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.user.GetUserLoginHistoryQry

import org.springframework.stereotype.Service

/**
 * 获取用户登录历史
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetUserLoginHistoryQryHandler(
) : Query<GetUserLoginHistoryQry.Request, GetUserLoginHistoryQry.Response> {

    override fun exec(request: GetUserLoginHistoryQry.Request): GetUserLoginHistoryQry.Response {

        return GetUserLoginHistoryQry.Response(

        )
    }
}
