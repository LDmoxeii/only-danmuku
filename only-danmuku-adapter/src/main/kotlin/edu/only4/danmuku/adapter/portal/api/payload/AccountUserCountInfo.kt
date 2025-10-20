package edu.only4.danmuku.adapter.portal.api.payload

object AccountUserCountInfo {

    class Request

    data class Response(

        val fansCount: Int,

        val currentCoinCount: Int,

        val focusCount: Int
    )
}
