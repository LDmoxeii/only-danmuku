package edu.only4.danmuku.application.commands.customer_video_series

import com.only.engine.error.CommonErrors
import com.only.engine.exception.RequestException
import edu.only4.danmuku.application.commands.customer_video_series.CreateCustomerVideoSeriesCmd
import edu.only4.danmuku.application.commands.customer_video_series.UpdateCustomerVideoSeriesVideosCmd
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.lang.reflect.InvocationTargetException

class CustomerVideoSeriesVideoIdsValidationExceptionTest {

    @Test
    fun `create command should throw request exception for malformed video ids`() {
        val ex = assertThrows(RequestException::class.java) {
            CreateCustomerVideoSeriesCmd.Handler().exec(
                CreateCustomerVideoSeriesCmd.Request(
                    userId = 1L,
                    seriesName = "series",
                    videoIds = "abc,2",
                ),
            )
        }
        assertEquals(CommonErrors.PARAM_INVALID, ex.errorCode)
    }

    @Test
    fun `update command parser should throw request exception for malformed video ids`() {
        val method = UpdateCustomerVideoSeriesVideosCmd.Handler::class.java.getDeclaredMethod(
            "parseVideoIds",
            String::class.java,
        )
        method.isAccessible = true
        val handler = UpdateCustomerVideoSeriesVideosCmd.Handler()

        val wrapped = assertThrows(InvocationTargetException::class.java) {
            method.invoke(handler, "1,invalid")
        }
        val cause = assertInstanceOf(RequestException::class.java, wrapped.cause)
        assertEquals(CommonErrors.PARAM_INVALID, cause.errorCode)
    }
}
