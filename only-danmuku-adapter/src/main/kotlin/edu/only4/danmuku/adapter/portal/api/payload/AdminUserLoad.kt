package edu.only4.danmuku.adapter.portal.api.payload

import com.only4.cap4k.ddd.core.share.PageParam
import java.time.LocalDateTime

/**
 * 加载用户列表(分页)接口载荷
 */
object AdminUserLoad {

    class Request(
        /** 昵称模糊查询 */
        val nickNameFuzzy: String? = null,
        /** 用户状态 */
        val status: Int? = null
    ) : PageParam()

    /**
     * 用户项
     */
    data class UserItem(
        /** 用户ID */
        var userId: String? = null,
        /** 邮箱 */
        var email: String? = null,
        /** 昵称 */
        var nickName: String? = null,
        /** 头像 */
        var avatar: String? = null,
        /** 性别: 0-女 1-男 */
        var sex: Int? = null,
        /** 生日 */
        var birthday: String? = null,
        /** 学校 */
        var school: String? = null,
        /** 个性签名 */
        var personalSignature: String? = null,
        /** 用户状态: 0-禁用 1-正常 */
        var status: Int? = null,
        /** 注册时间 */
        var joinTime: LocalDateTime? = null,
        /** 最后登录时间 */
        var lastLoginTime: LocalDateTime? = null,
        /** 当前硬币数 */
        var currentCoinCount: Int? = null,
        /** 主题 */
        var theme: Int? = null
    )
}
