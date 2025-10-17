package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 获取视频统计信息接口载荷
 */
object UCenterGetVideoCountInfo {

    class Request

    data class Response(
        /** 审核通过数 */
        var auditPassCount: Int? = null,
        /** 审核失败数 */
        var auditFailCount: Int? = null,
        /** 进行中数量 */
        var inProgress: Int? = null
    )
}
