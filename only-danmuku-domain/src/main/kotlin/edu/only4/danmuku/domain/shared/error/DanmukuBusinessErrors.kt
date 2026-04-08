package edu.only4.danmuku.domain.shared.error

import com.only.engine.error.BusinessErrorCode

object DanmukuBusinessErrors {
    object RESOURCE_NOT_FOUND : BusinessErrorCode(41000, "RESOURCE_NOT_FOUND", "资源不存在")

    object OPERATION_FORBIDDEN : BusinessErrorCode(41001, "OPERATION_FORBIDDEN", "操作不允许")

    object STATE_INVALID : BusinessErrorCode(41002, "STATE_INVALID", "状态非法")

    object COIN_AMOUNT_INVALID : BusinessErrorCode(41003, "COIN_AMOUNT_INVALID", "硬币数量非法")

    object COIN_BALANCE_INSUFFICIENT : BusinessErrorCode(41004, "COIN_BALANCE_INSUFFICIENT", "硬币余额不足")
}
