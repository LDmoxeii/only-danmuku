package edu.only4.danmuku.domain._share.audit

import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import jakarta.persistence.Transient
import java.time.Instant
import java.lang.reflect.Field

@MappedSuperclass
open class AuditedEntity {

    // Field name hooks for customization in rare cases
    @Transient
    protected open val fieldCreateUserId: String = "createUserId"

    @Transient
    protected open val fieldCreateBy: String = "createBy"

    @Transient
    protected open val fieldCreateTime: String = "createTime"

    @Transient
    protected open val fieldUpdateUserId: String = "updateUserId"

    @Transient
    protected open val fieldUpdateBy: String = "updateBy"

    @Transient
    protected open val fieldUpdateTime: String = "updateTime"

    @PrePersist
    fun __auditPrePersist() {
        val nowSec = Instant.now().epochSecond
        val uid = AuditSupport.currentUserId()
        val uname = AuditSupport.currentUserName()
        setIfNull(fieldCreateUserId, uid)
        setIfNull(fieldCreateBy, uname)
        setIfNull(fieldCreateTime, nowSec)
        setIfNull(fieldUpdateUserId, uid)
        setIfNull(fieldUpdateBy, uname)
        setIfNull(fieldUpdateTime, nowSec)
    }

    @PreUpdate
    fun __auditPreUpdate() {
        val nowSec = Instant.now().epochSecond
        setAlways(fieldUpdateUserId, AuditSupport.currentUserId())
        setAlways(fieldUpdateBy, AuditSupport.currentUserName())
        setAlways(fieldUpdateTime, nowSec)
    }

    private fun setIfNull(name: String, value: Any?) {
        val field = findField(name) ?: return
        val cur = field.get(this)
        if (cur == null) field.set(this, value)
    }

    private fun setAlways(name: String, value: Any?) {
        val field = findField(name) ?: return
        field.set(this, value)
    }

    private fun findField(name: String): Field? {
        var cls: Class<*>? = this.javaClass
        while (cls != null && cls != Any::class.java) {
            try {
                val f = cls.getDeclaredField(name)
                f.isAccessible = true
                return f
            } catch (_: NoSuchFieldException) {
                cls = cls.superclass
            }
        }
        return null
    }

}
