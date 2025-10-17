package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 获取推荐视频(基于关键词)接口载荷
 *
 * @see edu.only4.danmuku.adapter.portal.api.VideoController.videoGetRecommend
 */
object VideoGetRecommend {

    /**
     * 请求参数
     */
    data class Request(
        /**
         * 关键词
         */

        @field:NotEmpty(message = "关键词不能为空")
        val keyword: String = null,
        /**
         * 当前视频ID(排除)
         */

        @field:NotEmpty(message = "当前视频ID(排除)不能为空")
        val videoId: String = null
    )

    /**
     * 响应结果
     */
    data class Response(
        /**
         * 推荐视频列表
         */
        var list: List<Any>? = null
    )
}
