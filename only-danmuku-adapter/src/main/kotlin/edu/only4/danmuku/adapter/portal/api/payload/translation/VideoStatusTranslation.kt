package edu.only4.danmuku.adapter.portal.api.payload.translation

import com.only.engine.translation.annotation.TranslationType
import com.only.engine.translation.core.BatchTranslationInterface
import com.only.engine.translation.core.TranslationInterface
import edu.only4.danmuku.adapter.portal.api.payload.translation.VideoStatusTranslation.Companion.VIDEO_STATUS_CODE_TO_DESC
import edu.only4.danmuku.domain.aggregates.video_post.enums.VideoStatus
import org.springframework.stereotype.Component

@TranslationType(type = VIDEO_STATUS_CODE_TO_DESC)
@Component
class VideoStatusTranslation :
    TranslationInterface<String>, BatchTranslationInterface<String> {

    companion object {
        const val VIDEO_STATUS_CODE_TO_DESC = "video_status_code_to_desc"
    }

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

    override fun translationBatch(keys: Collection<Any>, other: String): Map<Any, String?> {
        if (keys.isEmpty()) return emptyMap()

        // Map each original key to an Int code (or null if not convertible)
        val keyToCode: Map<Any, Int?> = keys.associateWith { k ->
            when (k) {
                is Number -> k.toInt()
                is String -> k.toIntOrNull()
                else -> null
            }
        }

        // Unique valid codes
        val codes: Set<Int> = keyToCode.values.filterNotNull().toSet()

        // Build code -> desc map
        val codeToDesc: Map<Int, String?> = codes.associateWith { c ->
            try {
                VideoStatus.valueOf(c).desc
            } catch (_: Exception) {
                null
            }
        }

        // Return mapping for all input keys (including non-convertible -> null)
        return keyToCode.mapValues { (_, c) -> c?.let { codeToDesc[it] } }
    }
}
