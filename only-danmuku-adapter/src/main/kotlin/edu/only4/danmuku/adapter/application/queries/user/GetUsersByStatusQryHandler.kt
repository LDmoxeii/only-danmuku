package edu.only4.danmuku.adapter.application.queries.user

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.user.GetUsersByStatusQry

import org.springframework.stereotype.Service

/**
 * 按状态获取用户列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetUsersByStatusQryHandler(
) : Query<GetUsersByStatusQry.Request, GetUsersByStatusQry.Response> {

    override fun exec(request: GetUsersByStatusQry.Request): GetUsersByStatusQry.Response {

        return GetUsersByStatusQry.Response(

        )
    }
}
