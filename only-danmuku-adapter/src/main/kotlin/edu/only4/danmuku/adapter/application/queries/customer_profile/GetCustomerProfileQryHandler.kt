package edu.only4.danmuku.adapter.application.queries.customer_profile

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.draft.customer_focus.CustomerFocusSimple
import edu.only4.danmuku.application.queries._share.draft.customer_profile.CustomerProfileSimple
import edu.only4.danmuku.application.queries._share.model.customer_focus.customerId
import edu.only4.danmuku.application.queries._share.model.customer_focus.focusCustomerId
import edu.only4.danmuku.application.queries._share.model.customer_profile.userId
import edu.only4.danmuku.application.queries.customer_profile.GetCustomerProfileQry
import org.babyfish.jimmer.sql.kt.KSqlClient
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

        // 查询用户档案信息
        val profiles = sqlClient.findAll(CustomerProfileSimple::class) {
            where(table.userId eq request.customerId)
        }

        if (profiles.isEmpty()) {
            throw IllegalArgumentException("用户档案不存在: customerId=${request.customerId}")
        }

        val profile = profiles.first()

        // 统计粉丝数（有多少人关注了该用户）
        val fansCount = sqlClient.findAll(CustomerFocusSimple::class) {
            where(table.focusCustomerId eq request.customerId)
        }.size

        // 统计关注数（该用户关注了多少人）
        val focusCount = sqlClient.findAll(CustomerFocusSimple::class) {
            where(table.customerId eq request.customerId)
        }.size

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
