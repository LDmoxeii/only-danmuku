package edu.only4.danmuku.application.queries.user

import com.only4.cap4k.ddd.core.application.query.PageQueryParam

/**
 * 按状态获取用户列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetUsersByStatusQry {

    data class Request(
        /** 昵称模糊查询 */
        val nickNameFuzzy: String? = null,
        /** 用户状态: 0-禁用 1-正常 */
        val status: Int? = null
    ) : PageQueryParam<Response>()

    data class Response(
        /** 用户ID */
        val userId: Long,
        /** 邮箱 */
        val email: String? = null,
        /** 昵称 */
        val nickName: String? = null,
        /** 头像 */
        val avatar: String? = null,
        /** 性别: 0-女 1-男 */
        val sex: Int? = null,
        /** 生日 */
        val birthday: String? = null,
        /** 学校 */
        val school: String? = null,
        /** 个性签名 */
        val personalSignature: String? = null,
        /** 用户状态: 0-禁用 1-正常 */
        val status: Int? = null,
        /** 注册时间 */
        val joinTime: Long,
        /** 最后登录时间 */
        val lastLoginTime: Long? = null,
        /** 当前硬币数 */
        val currentCoinCount: Int? = null,
        /** 主题 */
        val theme: Int? = null
    )
}
