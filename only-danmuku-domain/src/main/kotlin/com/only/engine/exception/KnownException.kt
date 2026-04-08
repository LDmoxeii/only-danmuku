package com.only.engine.exception

import com.only.engine.error.CommonErrors
import com.only.engine.error.ErrorCategory

class KnownException @JvmOverloads constructor(
    message: String = CommonErrors.BUSINESS_ERROR.message,
    cause: Throwable? = null,
) : AppException(
    errorCode = CommonErrors.BUSINESS_ERROR,
    expectedCategory = ErrorCategory.BUSINESS,
    message = message,
    cause = cause,
) {

    companion object {
        @JvmStatic
        fun illegalArgument(message: String): KnownException = KnownException(message)

        @JvmStatic
        fun systemError(message: String): SystemException = SystemException(CommonErrors.SYSTEM_ERROR, message)

        @JvmStatic
        fun systemError(cause: Throwable): SystemException =
            SystemException(CommonErrors.SYSTEM_ERROR, CommonErrors.SYSTEM_ERROR.message, cause = cause)
    }
}
