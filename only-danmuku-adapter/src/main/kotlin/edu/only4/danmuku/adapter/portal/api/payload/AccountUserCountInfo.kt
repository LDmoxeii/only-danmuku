package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 获取用户统计信息接口载荷
 *
 * @see edu.only4.danmuku.adapter.portal.api.AccountController.getUserCountInfo
 */
object AccountUserCountInfo {

    /**
     * 获取用户统计信息请求参数
     */
    class Request {
        // 无请求参数，通过Token获取当前用户信息
    }

    /**
     * 获取用户统计信息响应结果
     */
    data class Response(
        /**
         * 粉丝数
         */
        var fansCount: Int? = null,

        /**
         * 关注数
         */
        var focusCount: Int? = null,

        /**
         * 获赞数
         */
        var likeCount: Int? = null,

        /**
         * 播放数
         */
        var playCount: Int? = null,

        /**
         * 收藏数
         */
        var collectCount: Int? = null,

        /**
         * 投币数
         */
        var coinCount: Int? = null
    )
}
