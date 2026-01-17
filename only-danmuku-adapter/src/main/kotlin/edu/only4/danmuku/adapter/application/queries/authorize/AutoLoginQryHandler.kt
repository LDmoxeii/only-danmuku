package edu.only4.danmuku.adapter.application.queries.authorize

import cn.dev33.satoken.stp.StpUtil
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries.authorize.AutoLoginQry
import edu.only4.danmuku.domain._share.meta.customer_profile.SCustomerProfile
import org.springframework.stereotype.Service

/**
 * 自动登录
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/17
 */
@Service
class AutoLoginQryHandler : Query<AutoLoginQry.Request, AutoLoginQry.Response> {

    override fun exec(request: AutoLoginQry.Request): AutoLoginQry.Response {
        if (!LoginHelper.isLogin()) return AutoLoginQry.Response()
        return AutoLoginQry.Response(
            userId = LoginHelper.getUserId()!!,
            nickName = LoginHelper.getUserInfo()!!.username,
            avatar = LoginHelper.getUserInfo()!!.extra[SCustomerProfile.props.avatar] as String,
            expireAt = StpUtil.getTokenTimeout(),
            token = StpUtil.getTokenValue()
        )
    }
}
