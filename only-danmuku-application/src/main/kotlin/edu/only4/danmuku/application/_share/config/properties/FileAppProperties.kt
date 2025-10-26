package edu.only4.danmuku.application._share.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * 文件存储相关配置
 *
 * prefix: app.file
 * - projectFolder: 项目根目录
 * - fileFolder: 文件存储目录
 * - fileCover: 封面图片目录
 * - thumbnailWidth: 缩略图宽度（像素）
 */
@ConfigurationProperties(prefix = "app.file")
class FileAppProperties {
    var projectFolder: String = "C:/Users/LD_moxeii/Documents/code/only-workspace/only-danmuku/"

    var showFFmpegLog: Boolean = true
}
