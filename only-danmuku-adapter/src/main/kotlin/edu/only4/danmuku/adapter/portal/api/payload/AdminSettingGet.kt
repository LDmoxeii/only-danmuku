package edu.only4.danmuku.adapter.portal.api.payload

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import edu.only4.danmuku.application._share.config.properties.SysSettingProperties
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * 获取系统设置接口载荷
 */
object AdminSettingGet {

    class Request

    /**
     * 系统设置数据传输对象（DTO），用于封装系统相关的配置参数
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Response(
        /**
         * 用户注册时赠送的金币数量，默认值为10
         */
        var registerCoinCount: Int = 10,

        /**
         * 用户发布视频时消耗的金币数量，默认值为5
         */
        var postVideoCoinCount: Int = 5,

        /**
         * 单个视频允许的最大大小（单位：MB），默认值为10MB
         * 该参数用于限制用户上传的每个视频的最大容量，以确保服务器存储稳定性与传输效率
         */
        var videoSize: Int = 10,

        /**
         * 单个视频的最大分片数量，默认值为10
         */
        var videoPCount: Int = 10,

        /**
         * 用户最多可以发布的视频数量，默认值为10
         */
        var videoCount: Int = 10,

        /**
         * 用户最多可以发表的评论数量，默认值为20
         */
        var commentCount: Int = 20,

        /**
         * 用户最多可以发送的弹幕数量，默认值为20
         */
        var danmuCount: Int = 20,
    )

    @Mapper(componentModel = "default")
    interface Converter {
        fun fromApp(props: SysSettingProperties): Response

        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
