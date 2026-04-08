package edu.only4.danmuku.domain.customer_profile

import com.only.engine.exception.BusinessException
import edu.only4.danmuku.domain.aggregates.customer_profile.CustomerProfile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class CustomerProfileExceptionTest {

    @Test
    fun `transfer should throw business exception when amount is not positive`() {
        val sender = CustomerProfile(currentCoinCount = 10)
        val receiver = CustomerProfile(currentCoinCount = 0)

        val ex = assertThrows(BusinessException::class.java) {
            sender.transferCoin(receiver, 0)
        }

        assertEquals(41003, ex.errorCode.code)
        assertEquals("转账金额必须大于0", ex.message)
    }

    @Test
    fun `spend should throw business exception when balance is insufficient`() {
        val profile = CustomerProfile(currentCoinCount = 1)

        val ex = assertThrows(BusinessException::class.java) {
            profile.spendCoins(2)
        }

        assertEquals(41004, ex.errorCode.code)
        assertEquals("硬币余额不足", ex.message)
    }
}
