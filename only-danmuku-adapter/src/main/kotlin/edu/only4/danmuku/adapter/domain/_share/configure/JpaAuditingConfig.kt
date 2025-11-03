package edu.only4.danmuku.adapter.domain._share.configure

import com.only.engine.satoken.utils.LoginHelper
import edu.only4.danmuku.domain._share.audit.AuditSupport
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.time.Instant
import java.util.*

@Configuration
@EnableJpaAuditing
class JpaAuditingConfig {

    @Bean
    fun auditorAware(): AuditorAware<Long> = AuditorAware {
        Optional.ofNullable(LoginHelper.getUserId())
    }

    // Provide a DateTimeProvider (Instant). We still persist seconds ourselves to Long fields.
    @Bean
    fun epochInstantProvider(): DateTimeProvider = DateTimeProvider { Optional.of(Instant.now()) }

    @PostConstruct
    fun wireAuditSupport() {
        // Register converters so @CreatedDate/@LastModifiedDate can write Long epoch seconds.
        AuditingLongConverters.registerWithDefaultConversionService()
        // Bridge to domain-side helper for name/ID usage in AuditedEntity
        AuditSupport.register(object : AuditSupport.Provider {
            override fun currentUserId(): Long? = LoginHelper.getUserId()
            override fun currentUserName(): String? = LoginHelper.getUserInfo()?.username
        })
    }
}
