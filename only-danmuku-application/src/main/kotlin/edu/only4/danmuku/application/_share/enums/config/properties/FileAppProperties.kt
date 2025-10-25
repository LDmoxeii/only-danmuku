package edu.only4.danmuku.application._share.enums.config.properties

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
    var projectFolder: String = "/data/app"
    var fileFolder: String = "file"
    var fileCover: String = "cover/"
    var thumbnailWidth: Int = 200
}
