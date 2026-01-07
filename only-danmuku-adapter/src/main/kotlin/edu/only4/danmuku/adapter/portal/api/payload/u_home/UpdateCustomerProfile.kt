package edu.only4.danmuku.adapter.portal.api.payload.u_home

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

object UpdateCustomerProfile {


    data class Request(
        @param:NotEmpty @param:Size(max = 20) val nickName: String,
        @param:NotEmpty @param:Size(max = 100) val avatar: String,
        val sex: Int, val birthday: String?,
        @param:Size(max = 150) val school: String?,
        @param:Size(max = 80) val personIntroduction: String?,
        @param:Size(max = 300) val noticeInfo: String?,
    )
}
