package edu.only4.danmuku.domain.shared.error

import com.only.engine.error.BusinessErrorCode

object DanmukuBusinessErrors {
    object RESOURCE_NOT_FOUND : BusinessErrorCode(41000, "RESOURCE_NOT_FOUND", "资源不存在")

    object OPERATION_FORBIDDEN : BusinessErrorCode(41001, "OPERATION_FORBIDDEN", "操作不允许")

    object STATE_INVALID : BusinessErrorCode(41002, "STATE_INVALID", "状态非法")
}
