package edu.only4.danmuku.adapter.application.distributed.clients.video_encrypt

import com.only.engine.error.CommonErrors
import com.only.engine.exception.RequestException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.lang.reflect.InvocationTargetException

class VideoEncryptHexValidationExceptionTest {

    @Test
    fun `single variant handler should reject odd-length hex with request exception`() {
        val ex = assertThrows(RequestException::class.java) {
            invokeHexToBytes(EncryptHlsVariantWithKeyCliHandler(), "ABC")
        }

        assertEquals(CommonErrors.PARAM_INVALID, ex.errorCode)
        assertEquals("keyPlainHex", ex.message)
    }

    @Test
    fun `abr handler should reject odd-length hex with request exception`() {
        val ex = assertThrows(RequestException::class.java) {
            invokeHexToBytes(EncryptHlsWithQualityKeysCliHandler(), "ABC")
        }

        assertEquals(CommonErrors.PARAM_INVALID, ex.errorCode)
        assertEquals("keyPlainHex", ex.message)
    }

    private fun invokeHexToBytes(target: Any, hex: String) {
        val method = target.javaClass.getDeclaredMethod("hexToBytes", String::class.java)
        method.isAccessible = true
        try {
            method.invoke(target, hex)
        } catch (ex: InvocationTargetException) {
            throw (ex.targetException ?: ex)
        }
    }
}
