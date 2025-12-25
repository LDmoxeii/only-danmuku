package edu.only4.danmuku.application._share.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * 文件存储相关配置
 *
 * prefix: app.file
 * - showFFmpegLog: 是否输出 FFmpeg 日志
 */
@ConfigurationProperties(prefix = "app.file")
class FileAppProperties {
    var showFFmpegLog: Boolean = true
}
