package edu.only4.danmuku.adapter.portal.api.payload.admin_user

object ChangeStatus {

    data class Request(
        val userId: Long,
        val status: Int
    )
}
