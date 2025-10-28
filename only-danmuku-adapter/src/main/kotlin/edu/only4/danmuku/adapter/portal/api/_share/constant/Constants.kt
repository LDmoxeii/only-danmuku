package edu.only4.danmuku.adapter.portal.api._share.constant

object Constants {

    /**
     * Redis键前缀
     */
    const val REDIS_KEY_PREFIX: String = "only-danmuku:" // Redis键通用前缀

    /**
     * Redis键：验证码
     */
    const val REDIS_KEY_CHECK_CODE: String = REDIS_KEY_PREFIX + "check-code:"

    /**
     * Redis键：Web端Token
     */
    const val REDIS_KEY_TOKEN_WEB: String = REDIS_KEY_PREFIX + "token:web:" // Redis键：Web端Token

    /**
     * Redis键：Admin端Token
     */
    const val REDIS_KEY_TOKEN_ADMIN: String = REDIS_KEY_PREFIX + "token:admin:" // Redis键：Admin端Token

    /**
     * 系统设置相关Redis键
     */
    const val REDIS_KEY_SYS_SETTING: String = REDIS_KEY_PREFIX + "sysSetting:" // Redis键：系统设置

    /**
     * 视频搜索次数相关Redis键
     */
    val REDIS_KEY_VIDEO_SEARCH_COUNT: String = REDIS_KEY_PREFIX + "video:search:" // Redis键：视频搜索次数

    /**
     * 视频在线相关 Redis 键
     */
    const val REDIS_KEY_VIDEO_PLAY_COUNT_ONLINE_PREFIX: String = REDIS_KEY_PREFIX + "video:play:online:"
    const val REDIS_KEY_VIDEO_PLAY_COUNT_ONLINE: String = REDIS_KEY_VIDEO_PLAY_COUNT_ONLINE_PREFIX + "count:%s"
    const val REDIS_KEY_VIDEO_PLAY_COUNT_USER_PREFIX: String = "user:"
    const val REDIS_KEY_VIDEO_PLAY_COUNT_USER: String = REDIS_KEY_VIDEO_PLAY_COUNT_ONLINE_PREFIX + REDIS_KEY_VIDEO_PLAY_COUNT_USER_PREFIX + "%s:%s"

    /**
     * 视频在线心跳 TTL（秒）
     */
    const val VIDEO_PLAY_ONLINE_DEVICE_TTL_SEC: Long = 8
    const val VIDEO_PLAY_ONLINE_COUNT_TTL_SEC: Long = 10
}
