package edu.only4.danmuku.application.distributed.clients

import com.only4.cap4k.ddd.core.application.RequestParam

object RefreshLoginSessionCli {

    data class Request(
        val userId: Long,
        val nickName: String? = null,
        val avatar: String? = null,
    ) : RequestParam<Unit>
}

