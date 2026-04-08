package edu.only4.danmuku.adapter.portal.api

import com.only.engine.error.AuthErrors
import com.only.engine.error.CommonErrors
import com.only.engine.exception.AuthenticationException
import com.only.engine.exception.AuthorizationException
import com.only.engine.exception.BusinessException
import com.only.engine.exception.DependencyException
import com.only.engine.exception.RateLimitException
import com.only.engine.exception.SystemException
import com.only.engine.web.advice.GlobalExceptionHandlerAdvice
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

class HybridExceptionProtocolIntegrationTest {

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(TestController())
            .setControllerAdvice(GlobalExceptionHandlerAdvice())
            .build()
    }

    @Test
    fun `business failure should return http 200 and code 40240`() {
        mockMvc.perform(get("/exception/business"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value(40240))
    }

    @Test
    fun `authentication failure should return http 401 and code 40100`() {
        mockMvc.perform(get("/exception/authentication"))
            .andExpect(status().isUnauthorized)
            .andExpect(jsonPath("$.code").value(40100))
    }

    @Test
    fun `authorization failure should return http 403 and code 40300`() {
        mockMvc.perform(get("/exception/authorization"))
            .andExpect(status().isForbidden)
            .andExpect(jsonPath("$.code").value(40300))
    }

    @Test
    fun `rate limit failure should return http 429 and code 40501`() {
        mockMvc.perform(get("/exception/rate-limit"))
            .andExpect(status().isTooManyRequests)
            .andExpect(jsonPath("$.code").value(40501))
    }

    @Test
    fun `system failure should return http 500 and code 50000`() {
        mockMvc.perform(get("/exception/system"))
            .andExpect(status().isInternalServerError)
            .andExpect(jsonPath("$.code").value(50000))
    }

    @Test
    fun `dependency failure should return http 503 and code 60000`() {
        mockMvc.perform(get("/exception/dependency"))
            .andExpect(status().isServiceUnavailable)
            .andExpect(jsonPath("$.code").value(60000))
    }

    @RestController
    private class TestController {

        @GetMapping("/exception/business")
        fun business(): String = throw BusinessException(AuthErrors.CAPTCHA_INVALID, "验证码错误")

        @GetMapping("/exception/authentication")
        fun authentication(): String = throw AuthenticationException(AuthErrors.LOGIN_REQUIRED)

        @GetMapping("/exception/authorization")
        fun authorization(): String = throw AuthorizationException(AuthErrors.ACCESS_DENIED)

        @GetMapping("/exception/rate-limit")
        fun rateLimit(): String = throw RateLimitException(CommonErrors.REQUEST_RATE_LIMITED)

        @GetMapping("/exception/system")
        fun system(): String = throw SystemException(CommonErrors.SYSTEM_ERROR, cause = IllegalStateException("boom"))

        @GetMapping("/exception/dependency")
        fun dependency(): String = throw DependencyException(CommonErrors.DEPENDENCY_ERROR, "依赖服务异常")
    }
}
