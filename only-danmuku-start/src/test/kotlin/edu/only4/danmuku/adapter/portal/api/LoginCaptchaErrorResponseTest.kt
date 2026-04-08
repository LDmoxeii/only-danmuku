package edu.only4.danmuku.adapter.portal.api

import com.only.engine.web.advice.GlobalExceptionHandlerAdvice
import com.only4.cap4k.ddd.core.application.RequestSupervisor
import com.only4.cap4k.ddd.core.application.RequestSupervisorSupport
import edu.only4.danmuku.adapter.portal.api.admin.AdminAccountController
import edu.only4.danmuku.adapter.portal.api.web.AccountController
import edu.only4.danmuku.application.distributed.clients.CaptchaValidCli
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginCaptchaErrorResponseTest {

    private val requestSupervisor: RequestSupervisor = mockk()
    private lateinit var mockMvc: MockMvc

    @BeforeAll
    fun init() {
        RequestSupervisorSupport.instance = requestSupervisor
        mockMvc = MockMvcBuilders
            .standaloneSetup(AccountController(), AdminAccountController())
            .setControllerAdvice(GlobalExceptionHandlerAdvice())
            .build()
    }

    @BeforeEach
    fun setUp() {
        clearMocks(requestSupervisor)
        every { requestSupervisor.send(any<CaptchaValidCli.Request>()) } returns
            CaptchaValidCli.Response(result = false)
    }

    @Test
    fun `should return captcha error for web login when captcha validation fails`() {
        mockMvc.perform(
            post("/account/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "email": "user@example.com",
                      "password": "admin123",
                      "checkCodeKey": "captcha-key",
                      "checkCode": "1"
                    }
                    """.trimIndent(),
                ),
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value(40240))
            .andExpect(jsonPath("$.message").value("验证码错误"))
    }

    @Test
    fun `should return captcha error for register when captcha validation fails`() {
        mockMvc.perform(
            post("/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "email": "user@example.com",
                      "nickName": "tester",
                      "registerPassword": "admin1234",
                      "checkCodeKey": "captcha-key",
                      "checkCode": "1"
                    }
                    """.trimIndent(),
                ),
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value(40240))
            .andExpect(jsonPath("$.message").value("验证码错误"))
    }

    @Test
    fun `should return captcha error for admin login when captcha validation fails`() {
        mockMvc.perform(
            post("/admin/account/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "account": "admin@example.com",
                      "password": "admin123",
                      "checkCodeKey": "captcha-key",
                      "checkCode": "1"
                    }
                    """.trimIndent(),
                ),
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value(40240))
            .andExpect(jsonPath("$.message").value("验证码错误"))
    }
}
