package edu.only4.danmuku.adapter.portal.api

import edu.only4.danmuku.adapter.portal.api.payload.UserActionDo
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 用户行为控制器
 */
@RestController
@RequestMapping("/userAction")
@Validated
class UserActionController {

    /**
     * 执行用户行为(点赞/收藏/投币)
     */
    @PostMapping("/doAction")
    fun userActionDo(@RequestBody @Validated request: UserActionDo.Request): UserActionDo.Response {
        // TODO: 实现执行用户行为(点赞/收藏/投币)逻辑
        return UserActionDo.Response()
    }

}
