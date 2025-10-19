package edu.only4.danmuku.adapter.application.queries.customer_focus

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.application.queries._share.draft.customer_focus.CustomerFocusSimple
import edu.only4.danmuku.application.queries._share.draft.customer_profile.CustomerProfileSimple
import edu.only4.danmuku.application.queries._share.model.customer_focus.customerId
import edu.only4.danmuku.application.queries._share.model.customer_focus.focusCustomerId
import edu.only4.danmuku.application.queries._share.model.customer_profile.userId
import edu.only4.danmuku.application.queries.customer_focus.GetFansListQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.stereotype.Service

/**
 * 获取粉丝列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetFansListQryHandler(
    private val sqlClient: KSqlClient,
) : PageQuery<GetFansListQry.Request, GetFansListQry.Response> {

    override fun exec(request: GetFansListQry.Request): PageData<GetFansListQry.Response> {

        // 查询当前用户的粉丝列表（谁关注了我）
        val pageResult =
            sqlClient.createQuery(edu.only4.danmuku.application.queries._share.model.customer_focus.JCustomerFocus::class) {
                where(table.focusCustomerId eq request.userId)
                select(table.fetch(CustomerFocusSimple::class))
            }.fetchPage(request.pageNum - 1, request.pageSize)

        // 查询粉丝用户的档案信息
        val fansUserIds = pageResult.rows.map { it.customerId }
        val userProfiles = if (fansUserIds.isNotEmpty()) {
            sqlClient.findAll(CustomerProfileSimple::class) {
                where(table.userId valueIn fansUserIds)
            }
        } else {
            emptyList()
        }

        // 构建用户档案Map
        val profileMap = userProfiles.associateBy { it.userId }

        // 检查当前用户是否反向关注了这些粉丝
        val myFocusList = sqlClient.findAll(CustomerFocusSimple::class) {
            where(table.customerId eq request.userId)
        }
        val myFocusUserIds = myFocusList.map { it.focusCustomerId }.toSet()

        return PageData.create(
            pageNum = request.pageNum,
            pageSize = request.pageSize,
            list = pageResult.rows.mapNotNull { focus ->
                val profile = profileMap[focus.customerId]
                profile?.let {
                    // 统计该用户的粉丝数
                    val fansCount = sqlClient.findAll(CustomerFocusSimple::class) {
                        where(table.focusCustomerId eq focus.customerId)
                    }.size

                    GetFansListQry.Response(
                        userId = focus.customerId,
                        nickName = it.nickName,
                        avatar = it.avatar,
                        fansCount = fansCount,
                        haveFocus = myFocusUserIds.contains(focus.customerId)  // 是否反向关注
                    )
                }
            },
            totalCount = pageResult.totalRowCount
        )
    }
}
