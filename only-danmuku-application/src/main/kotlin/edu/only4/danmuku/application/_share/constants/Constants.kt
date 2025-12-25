package edu.only4.danmuku.application._share.constants

object Constants {
    /**
     * 文件夹路径常量
     */
    const val FILE_FOLDER_TEMP: String = "temp/" // 临时文件夹路径
    const val FILE_FOLDER: String = "file/" // 文件存储路径
    const val FILE_COVER: String = "cover/" // 封面图片存储路径

    /**
     * 文件大小常量
     */
    val MB_SIZE: Long = 1024 * 1024L // 1MB的字节大小

    /**
     * 文件名常量
     */
    const val M3U8_NAME: String = "index.m3u8" // M3U8格式视频文件名

    /**
     * 修改昵称所需金币数量
     */
    const val UPDATE_NICK_NAME_COIN: Int = 5 // 修改昵称所需金币数量

    /**
     * 视频编码常量
     */
    const val VIDEO_CODE_HEVC: String = "hevc" // 视频编码格式：HEVC
}
