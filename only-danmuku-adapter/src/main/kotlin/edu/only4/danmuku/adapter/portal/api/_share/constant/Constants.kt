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
}
