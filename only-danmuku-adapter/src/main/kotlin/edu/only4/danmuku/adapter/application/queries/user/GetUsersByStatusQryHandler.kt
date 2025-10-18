package edu.only4.danmuku.adapter.application.queries.user

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.application.queries._share.draft.ProfileWithUser
import edu.only4.danmuku.application.queries._share.model.*
import edu.only4.danmuku.application.queries.user.GetUsersByStatusQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.desc
import org.babyfish.jimmer.sql.kt.ast.expression.`eq?`
import org.babyfish.jimmer.sql.kt.ast.expression.`ilike?`
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
    private val sqlClient: KSqlClient,
) : PageQuery<GetUsersByStatusQry.Request, GetUsersByStatusQry.UserItem> {

    override fun exec(request: GetUsersByStatusQry.Request): PageData<GetUsersByStatusQry.UserItem> {
        // 使用 Jimmer 查询用户档案，关联用户表
        val pageResult = sqlClient.createQuery(JCustomerProfile::class) {
            // 昵称模糊查询
            where(table.nickName `ilike?` request.nickNameFuzzy)
            // 状态过滤
            where(table.user.status `eq?` request.status?.toByte())
            // 按加入时间倒序
            orderBy(table.user.joinTime.desc())
            // DTO 投影
            select(table.fetch(ProfileWithUser::class))
        }.fetchPage(request.pageNum - 1, request.pageSize)

        // 转换为查询响应
        val responseList = pageResult.rows.map { profile ->
            GetUsersByStatusQry.UserItem(
                userId = profile.user.id,
                email = profile.user.email,
                nickName = profile.nickName,
                avatar = profile.avatar,
                sex = profile.sex.toInt(),
                birthday = profile.birthday,
                school = profile.school,
                personalSignature = profile.personIntroduction,
                status = profile.user.status.toInt(),
                joinTime = profile.user.joinTime,
                lastLoginTime = profile.user.lastLoginTime,
                currentCoinCount = profile.currentCoinCount,
                theme = profile.theme.toInt()
            )
        }

        // 返回分页结果
        return PageData.create(
            pageNum = request.pageNum,
            pageSize = request.pageSize,
            list = responseList,
            totalCount = pageResult.totalRowCount
        )
    }
}
