package edu.only4.danmuku.application._share.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "edu.only.danmuku")
class SysSettingProperties {
    /**
     * 用户注册时赠送的金币数量，默认值为10。
     */
    var registerCoinCount: Int = 10

    /**
     * 用户发布视频时消耗的金币数量，默认值为5。
     */
    var postVideoCoinCount: Int = 5

    /**
     * 单个视频允许的最大大小（单位：MB），默认值为10MB。
     * 该参数用于限制用户上传的每个视频的最大容量，以确保服务器存储稳定性与传输效率。
     */
    var videoSize: Int = 10

    /**
     * 单个视频的最大分片数量，默认值为10。
     */
    var videoPCount: Int = 10

    /**
     * 用户最多可以发布的视频数量，默认值为10。
     */
    var videoCount: Int = 10

    /**
     * 用户最多可以发表的评论数量，默认值为20。
     */
    var commentCount: Int = 20

    /**
     * 用户最多可以发送的弹幕数量，默认值为20。
     */
    var danmuCount: Int = 20
}
