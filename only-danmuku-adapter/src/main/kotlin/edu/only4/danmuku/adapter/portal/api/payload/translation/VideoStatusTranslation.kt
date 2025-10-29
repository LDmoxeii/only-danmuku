package edu.only4.danmuku.adapter.portal.api.payload.translation

import com.only.engine.translation.annotation.TranslationType
import com.only.engine.translation.core.TranslationInterface
import edu.only4.danmuku.domain.aggregates.video_post.enums.VideoStatus
import org.springframework.stereotype.Component

@TranslationType(type = "video_status_code_to_desc")
@Component
class VideoStatusTranslation : TranslationInterface<String> {
    override fun translation(key: Any, other: String): String? {
        val code = when (key) {
            is Number -> key.toInt()
            is String -> key.toIntOrNull()
            else -> null
        } ?: return null
        return try {
            VideoStatus.valueOf(code).desc
        } catch (_: Exception) {
            null
        }
    }
}

