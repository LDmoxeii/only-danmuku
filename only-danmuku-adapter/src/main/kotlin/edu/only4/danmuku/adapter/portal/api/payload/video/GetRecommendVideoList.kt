package edu.only4.danmuku.adapter.portal.api.payload.video

import edu.only4.danmuku.application.queries.video.GetVideoPageQry
import jakarta.validation.constraints.NotEmpty
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * 获取推荐视频(基于关键词)接口载荷
 */
object GetRecommendVideoList {

    /**
     * 请求参数
     */
    data class Request(
        /** 关键词 */
        @field:NotEmpty(message = "关键词不能为空")
        val keyword: String = "",
        /** 当前视频ID(排除) */
        val videoId: Long
    )

    /**
     * 响应结果 - 推荐视频列表
     */
    data class Response(
        /** 推荐视频列表 */
        var list: List<Item>? = null
    )

    data class Item(
        var videoId: Long,
        var videoCover: String?,
        var videoName: String?,
        var userId: Long?,
        var createTime: Long,
        var lastUpdateTime: Long?,
        var parentCategoryId: Long,
        var categoryId: Long?,
        var postType: Int,
        var originInfo: String?,
        var tags: String?,
        var introduction: String?,
        var duration: Int,
        val status: Int,
        var playCount: Int,
        var likeCount: Int,
        var danmuCount: Int,
        var commentCount: Int,
        var coinCount: Int,
        var collectCount: Int,
        var recommendType: Int,
        var lastPlayTime: Long?,
        var nickName: String? = null,
        var avatar: String? = null,
        var categoryFullName: String?,
    )

    @Mapper(componentModel = "default")
    interface Converter {
        fun fromApp(resp: GetVideoPageQry.Response): Item

        companion object { val INSTANCE: Converter = Mappers.getMapper(Converter::class.java) }
    }
}
