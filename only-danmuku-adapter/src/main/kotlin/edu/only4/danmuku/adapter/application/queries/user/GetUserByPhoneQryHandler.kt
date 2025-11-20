package edu.only4.danmuku.adapter.application.queries.user

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.user.GetUserByPhoneQry

import org.springframework.stereotype.Service
import org.babyfish.jimmer.sql.kt.KSqlClient

/**
 * 通过手机号获取对应的用户账号信息
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/20
 */
@Service
class GetUserByPhoneQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetUserByPhoneQry.Request, GetUserByPhoneQry.Response> {

    override fun exec(request: GetUserByPhoneQry.Request): GetUserByPhoneQry.Response {

        return GetUserByPhoneQry.Response(

        )
    }
}
