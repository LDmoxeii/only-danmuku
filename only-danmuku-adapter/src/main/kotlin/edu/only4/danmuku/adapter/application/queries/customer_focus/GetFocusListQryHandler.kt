package edu.only4.danmuku.adapter.application.queries.customer_focus

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.application.queries._share.draft.customer_focus.CustomerFocusSimple
import edu.only4.danmuku.application.queries._share.draft.customer_profile.CustomerProfileSimple
import edu.only4.danmuku.application.queries._share.model.customer_focus.customerId
import edu.only4.danmuku.application.queries._share.model.customer_focus.focusCustomerId
import edu.only4.danmuku.application.queries._share.model.customer_profile.userId
import edu.only4.danmuku.application.queries.customer_focus.GetFocusListQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.stereotype.Service

/**
 * 获取关注列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetFocusListQryHandler(
    private val sqlClient: KSqlClient,
) : PageQuery<GetFocusListQry.Request, GetFocusListQry.Response> {

    override fun exec(request: GetFocusListQry.Request): PageData<GetFocusListQry.Response> {

        // 查询当前用户的关注列表
        val pageResult =
            sqlClient.createQuery(edu.only4.danmuku.application.queries._share.model.customer_focus.JCustomerFocus::class) {
                where(table.customerId eq request.userId)
                select(table.fetch(CustomerFocusSimple::class))
            }.fetchPage(request.pageNum - 1, request.pageSize)

        // 查询关注用户的档案信息
        val focusUserIds = pageResult.rows.map { it.focusCustomerId }
        val userProfiles = if (focusUserIds.isNotEmpty()) {
            sqlClient.findAll(CustomerProfileSimple::class) {
                where(table.userId valueIn focusUserIds)
            }
        } else {
            emptyList()
        }

        // 构建用户档案Map
        val profileMap = userProfiles.associateBy { it.userId }

        // 检查当前用户是否关注了这些人（在关注列表中，默认都是已关注）
        return PageData.create(
            pageNum = request.pageNum,
            pageSize = request.pageSize,
            list = pageResult.rows.mapNotNull { focus ->
                val profile = profileMap[focus.focusCustomerId]
                profile?.let {
                    // 统计该用户的粉丝数
                    val fansCount = sqlClient.findAll(CustomerFocusSimple::class) {
                        where(table.focusCustomerId eq focus.focusCustomerId)
                    }.size

                    GetFocusListQry.Response(
                        focusUserId = focus.focusCustomerId,
                        nickName = it.nickName,
                        avatar = it.avatar,
                        fansCount = fansCount,
                        haveFocus = true  // 已关注
                    )
                }
            },
            totalCount = pageResult.totalRowCount
        )
    }
}
