package edu.only4.danmuku.adapter.application.queries.customer_profile

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.CustomerFocus
import edu.only4.danmuku.application.queries._share.model.CustomerProfile
import edu.only4.danmuku.application.queries._share.model.customerId
import edu.only4.danmuku.application.queries._share.model.fetchBy
import edu.only4.danmuku.application.queries._share.model.focusCustomerId
import edu.only4.danmuku.application.queries._share.model.userId
import edu.only4.danmuku.application.queries.customer_profile.GetCustomerProfileQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.count
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 获取用户信息
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetCustomerProfileQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetCustomerProfileQry.Request, GetCustomerProfileQry.Response> {

    override fun exec(request: GetCustomerProfileQry.Request): GetCustomerProfileQry.Response {

        val profile = sqlClient.createQuery(CustomerProfile::class) {
            where(table.userId eq request.customerId)
            select(table.fetchBy {
                allScalarFields()
                user()
            })
        }.fetchOneOrNull() ?: throw KnownException("用户档案不存在: customerId=${request.customerId}")

        val fansCount = sqlClient.createQuery(CustomerFocus::class) {
            where(table.focusCustomerId eq request.customerId)
            select(count(table.customerId))
        }.fetchOne()

        val focusCount = sqlClient.createQuery(CustomerFocus::class) {
            where(table.customerId eq request.customerId)
            select(count(table.focusCustomerId))
        }.fetchOne()

        return GetCustomerProfileQry.Response(
            customerId = profile.userId,
            nickName = profile.nickName,
            avatar = profile.avatar,
            sex = profile.sex,
            birthday = profile.birthday,
            school = profile.school,
            personIntroduction = profile.personIntroduction,
            noticeInfo = profile.noticeInfo,
            theme = profile.theme,
            currentCoinCount = profile.currentCoinCount,
            fansCount = fansCount,
            focusCount = focusCount
        )
    }
}
