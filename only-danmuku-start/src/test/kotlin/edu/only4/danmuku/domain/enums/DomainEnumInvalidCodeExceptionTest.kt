package edu.only4.danmuku.domain.enums

import com.only.engine.error.CommonErrors
import com.only.engine.exception.RequestException
import com.only.engine.exception.SystemException
import edu.only4.danmuku.domain.aggregates.video_post.enums.EncryptMethod
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class DomainEnumInvalidCodeExceptionTest {

    @Test
    fun `request-side invalid enum code should throw request exception`() {
        val ex = assertThrows(RequestException::class.java) {
            EncryptMethod.valueOf(999)
        }
        assertEquals(CommonErrors.PARAM_INVALID, ex.errorCode)
    }

    @Test
    fun `db-side invalid enum code should throw system exception`() {
        val ex = assertThrows(SystemException::class.java) {
            EncryptMethod.Converter().convertToEntityAttribute(999)
        }
        assertEquals(CommonErrors.SYSTEM_ERROR, ex.errorCode)
    }
}
