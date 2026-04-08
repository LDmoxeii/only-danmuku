package edu.only4.danmuku.adapter.application.queries.user

import com.only.engine.error.CommonErrors
import com.only.engine.exception.AppException
import com.only.engine.exception.BusinessException
import com.only.engine.exception.DependencyException
import com.only.engine.exception.RequestException
import com.only.engine.exception.SystemException
import edu.only4.danmuku.domain.shared.error.DanmukuBusinessErrors
import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.*
import edu.only4.danmuku.application.queries.user.GetUserByPhoneQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

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
        val row = sqlClient.createQuery(User::class) {
            where(table.phone eq request.phone)
            select(
                table.id,
                table.nickName,
                table.type
            )
        }.fetchOneOrNull() ?: throw BusinessException(DanmukuBusinessErrors.RESOURCE_NOT_FOUND, "用户不存在: ${request.phone}")

        val (id, nickName, type) = row
        return GetUserByPhoneQry.Response(
            userId = id,
            nickName = nickName,
            type = type
        )
    }
}
