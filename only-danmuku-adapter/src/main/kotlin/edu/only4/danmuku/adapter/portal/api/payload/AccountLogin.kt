package edu.only4.danmuku.adapter.portal.api.payload

object AccountLogin {

    data class Response(

        var userId: Long,

        var nickName: String,

        var avatar: String?,

        var expireAt: Long?,

        var token: String,
    )
}
