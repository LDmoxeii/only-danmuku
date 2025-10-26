package edu.only4.danmuku.adapter.application.queries.user

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.dto.User.UserAccountInfo
import edu.only4.danmuku.application.queries._share.model.email
import edu.only4.danmuku.application.queries.user.GetAccountInfoByEmailQry
import edu.only4.danmuku.domain.aggregates.user.enums.UserType
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 根据邮箱获取账户信息查询处理器
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/19
 */
@Service
class GetAccountInfoByEmailQryHandler(
    private val sqlClient: KSqlClient
) : Query<GetAccountInfoByEmailQry.Request, GetAccountInfoByEmailQry.Response> {

    override fun exec(request: GetAccountInfoByEmailQry.Request): GetAccountInfoByEmailQry.Response {
        // 使用 Jimmer DTO 查询用户账户信息
        val userAccountInfo = sqlClient.findOneOrNull(UserAccountInfo::class) {
            where(table.email eq request.email)
        } ?: throw IllegalArgumentException("用户不存在: ${request.email}")

        // 转换为查询响应
        return GetAccountInfoByEmailQry.Response(
            id = userAccountInfo.id,
            nickName = userAccountInfo.nickName,
            email = userAccountInfo.email,
            password = userAccountInfo.password,
            type = UserType.valueOf(userAccountInfo.type)
        )
    }
}
