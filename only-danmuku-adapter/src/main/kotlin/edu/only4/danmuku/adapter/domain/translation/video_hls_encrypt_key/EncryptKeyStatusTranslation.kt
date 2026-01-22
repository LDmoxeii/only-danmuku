package edu.only4.danmuku.adapter.domain.translation.video_hls_encrypt_key

import com.only.engine.translation.annotation.TranslationType
import com.only.engine.translation.core.BatchTranslationInterface
import com.only.engine.translation.core.TranslationInterface
import edu.only4.danmuku.adapter.domain.translation.video_hls_encrypt_key.EncryptKeyStatusTranslation.Companion.ENCRYPT_KEY_STATUS_CODE_TO_DESC
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.enums.EncryptKeyStatus
import org.springframework.stereotype.Component

/**
 * 由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手动修改本文件，后续生成会覆盖当前文件
 */
@TranslationType(type = ENCRYPT_KEY_STATUS_CODE_TO_DESC)
@Component
class EncryptKeyStatusTranslation :
    TranslationInterface<String>, BatchTranslationInterface<String> {

    companion object {
        const val ENCRYPT_KEY_STATUS_CODE_TO_DESC = "encrypt_key_status_code_to_desc"
    }

    override fun translation(key: Any, other: String): String? {
        val code = when (key) {
            is Number -> key.toInt()
            is String -> key.toIntOrNull()
            else -> null
        } ?: return null
        return EncryptKeyStatus.valueOfOrNull(code)?.desc
    }

    override fun translationBatch(keys: Collection<Any>, other: String): Map<Any, String?> {
        if (keys.isEmpty()) return emptyMap()

        val keyToCode: Map<Any, Int?> = keys.associateWith { k ->
            when (k) {
                is Number -> k.toInt()
                is String -> k.toIntOrNull()
                else -> null
            }
        }

        val codes: Set<Int> = keyToCode.values.filterNotNull().toSet()

        val codeToDesc: Map<Int, String?> = codes.associateWith { c ->
            EncryptKeyStatus.valueOfOrNull(c)?.desc
        }

        return keyToCode.mapValues { (_, c) -> c?.let { codeToDesc[it] } }
    }
}
