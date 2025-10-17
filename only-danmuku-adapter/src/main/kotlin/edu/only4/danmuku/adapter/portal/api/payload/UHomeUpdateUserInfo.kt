package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

/**
 * 更新用户信息接口载荷
 */
object UHomeUpdateUserInfo {

    data class Request(
        /** 昵称 */

        @field:NotEmpty(message = "昵称不能为空")
        @field:Size(max = 20, message = "长度不能超过20个字符")
        val nickName: String = "",
        /** 头像 */

        @field:NotEmpty(message = "头像不能为空")
        @field:Size(max = 100, message = "长度不能超过100个字符")
        val avatar: String = "",
        /** 性别 */

        @field:NotEmpty(message = "性别不能为空")
        val sex: Int = 0,
        /** 生日 */
        val birthday: String? = null,
        /** 学校 */

        @field:Size(max = 150, message = "长度不能超过150个字符")
        val school: String? = null,
        /** 个人简介 */

        @field:Size(max = 80, message = "长度不能超过80个字符")
        val personIntroduction: String? = null,
        /** 空间公告 */

        @field:Size(max = 300, message = "长度不能超过300个字符")
        val noticeInfo: String? = null
    )

    class Response()
}
