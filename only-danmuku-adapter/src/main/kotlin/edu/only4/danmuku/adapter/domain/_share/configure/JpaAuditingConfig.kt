package edu.only4.danmuku.adapter.domain._share.configure

import com.only.engine.satoken.utils.LoginHelper
import edu.only4.danmuku.domain._share.audit.AuditSupport
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration

@Configuration
class JpaAuditingConfig {

    @PostConstruct
    fun wireAuditSupport() {
        // Bridge to domain-side helper for name/ID usage in AuditedEntity
        AuditSupport.register(object : AuditSupport.Provider {
            override fun currentUserId(): Long? = LoginHelper.getUserId()
            override fun currentUserName(): String? = LoginHelper.getUserInfo()?.username
        })
    }
}
