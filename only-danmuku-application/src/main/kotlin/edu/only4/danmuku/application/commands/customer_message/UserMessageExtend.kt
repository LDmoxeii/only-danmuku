package edu.only4.danmuku.application.commands.customer_message

/**
 * 用户消息扩展信息（与旧版 Java UserMessageExtendDto 对齐）
 */
data class UserMessageExtend(
    val messageContent: String? = null,
    val messageContentReply: String? = null,
    val auditStatus: Int? = null,
) {
    /**
     * 轻量级 JSON 序列化，避免额外依赖；按需转义
     */
    fun toJson(): String {
        fun esc(src: String?): String? {
            if (src == null) return null
            val e = src.replace("\\", "\\\\").replace("\"", "\\\"")
            return "\"$e\""
        }
        val parts = mutableListOf<String>()
        esc(messageContent)?.let { parts.add("\"messageContent\":$it") }
        esc(messageContentReply)?.let { parts.add("\"messageContentReply\":$it") }
        auditStatus?.let { parts.add("\"auditStatus\":$it") }
        return "{" + parts.joinToString(",") + "}"
    }
}

