package edu.only4.danmuku.adapter.portal.api.payload

import edu.only4.danmuku.application._share.config.properties.SysSettingProperties
import jakarta.validation.constraints.Min
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * 保存系统设置接口载荷
 */
object AdminSettingSave {

    data class Request(
        /**
         * 用户注册时赠送的金币数量
         */
        @field:Min(value = 0, message = "注册赠送金币数量不能小于0")
        val registerCoinCount: Int,

        /**
         * 用户发布视频时消耗的金币数量
         */
        @field:Min(value = 0, message = "发布视频消耗金币数量不能小于0")
        val postVideoCoinCount: Int,

        /**
         * 单个视频允许的最大大小（单位：MB）
         */
        @field:Min(value = 1, message = "视频最大大小不能小于1MB")
        val videoSize: Int,

        /**
         * 单个视频的最大分片数量
         */
        @field:Min(value = 1, message = "视频最大分片数量不能小于1")
        val videoPCount: Int,

        /**
         * 用户最多可以发布的视频数量
         */
        @field:Min(value = 1, message = "用户最大视频数量不能小于1")
        val videoCount: Int,

        /**
         * 用户最多可以发表的评论数量
         */
        @field:Min(value = 1, message = "用户最大评论数量不能小于1")
        val commentCount: Int,

        /**
         * 用户最多可以发送的弹幕数量
         */
        @field:Min(value = 1, message = "用户最大弹幕数量不能小于1")
        val danmuCount: Int,
    )

    class Response

    @Mapper(componentModel = "default")
    interface Converter {
        fun toApp(request: Request): SysSettingProperties

        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
