package edu.only4.danmuku.application._share.enums.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * 视频处理相关配置
 *
 * prefix: app.video
 * - tempFolder: 分片临时目录
 * - targetFolder: 转码/发布目标目录
 * - tsSegmentSeconds: TS 分片时长（秒）
 */
@ConfigurationProperties(prefix = "app.video")
class VideoAppProperties {
    var tempFolder: String = "/tmp/video/temp"
    var targetFolder: String = "/data/video"
    var tsSegmentSeconds: Int = 10
}

