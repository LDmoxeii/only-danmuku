package edu.only4.danmuku.application.queries.file_upload_session

import com.only4.cap4k.ddd.core.application.RequestParam
import jakarta.validation.constraints.NotNull

/**
 * 根据会话ID获取上传会话（VideoFileUploadSession）
 */
object GetUploadSessionByIdQry {

    data class Request(
        /** 用户ID（用于权限校验） */
        @field:NotNull(message = "用户ID不能为空")
        val userId: Long,
        /** 上传会话ID */
        @field:NotNull(message = "上传会话ID不能为空")
        val uploadId: Long,
    ) : RequestParam<Response>

    data class Response(
        val exists: Boolean,
        val id: Long?,
        val customerId: Long?,
        val fileName: String?,
        val chunks: Int?,
        val chunkIndex: Int?,
        val fileSize: Long?,
        val tempPath: String?,
        val status: Int?,
        val createTime: Long?,
        val updateTime: Long?,
        val expiresAt: Long?,
    )
}

