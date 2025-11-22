package edu.only4.danmuku.domain._share.audit

import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import jakarta.persistence.EntityListeners
import jakarta.persistence.Transient
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import java.lang.reflect.Field

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
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
        val nowSec = System.currentTimeMillis() / 1000
        val hasCreatedDate = hasAnnotation(fieldCreateTime, CreatedDate::class.java)
        val hasLastModifiedDate = hasAnnotation(fieldUpdateTime, LastModifiedDate::class.java)

        if (!hasCreatedDate) {
            setIfNull(fieldCreateTime, nowSec)
        }
        if (!hasLastModifiedDate) {
            setIfNull(fieldUpdateTime, (getFieldValue(fieldCreateTime) as Long?) ?: nowSec)
        }

        val uid = AuditSupport.currentUserId()
        val uname = AuditSupport.currentUserName()
        val hasCreatedBy = hasAnnotation(fieldCreateUserId, CreatedBy::class.java)
        val hasLastModifiedBy = hasAnnotation(fieldUpdateUserId, LastModifiedBy::class.java)

        if (!hasCreatedBy) setIfNull(fieldCreateUserId, uid)
        setIfNull(fieldCreateBy, uname)

        if (!hasLastModifiedBy) setAlways(fieldUpdateUserId, uid)
        setAlways(fieldUpdateBy, uname)
    }

    @PreUpdate
    fun __auditPreUpdate() {
        val nowSec = System.currentTimeMillis() / 1000
        val hasLastModifiedDate = hasAnnotation(fieldUpdateTime, LastModifiedDate::class.java)
        if (!hasLastModifiedDate) {
            setAlways(fieldUpdateTime, nowSec)
        }
        val hasLastModifiedBy = hasAnnotation(fieldUpdateUserId, LastModifiedBy::class.java)
        if (!hasLastModifiedBy) setAlways(fieldUpdateUserId, AuditSupport.currentUserId())
        setAlways(fieldUpdateBy, AuditSupport.currentUserName())
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

    private fun getFieldValue(name: String): Any? = findField(name)?.get(this)

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

    private fun hasAnnotation(name: String, ann: Class<out Annotation>): Boolean {
        val field = findField(name) ?: return false
        return field.isAnnotationPresent(ann)
    }
}
