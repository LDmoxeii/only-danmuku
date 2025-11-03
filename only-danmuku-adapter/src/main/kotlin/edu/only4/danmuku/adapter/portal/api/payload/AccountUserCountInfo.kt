package edu.only4.danmuku.adapter.portal.api.payload

object AccountUserCountInfo {
    data class Response(
        val fansCount: Long,
        val currentCoinCount: Int,
        val focusCount: Long
    )
}
