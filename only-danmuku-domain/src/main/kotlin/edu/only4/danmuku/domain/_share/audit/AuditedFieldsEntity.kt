package edu.only4.danmuku.domain._share.audit

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass

/**
 * MappedSuperclass that declares common auditing fields so concrete entities
 * don't need to repeat them. Fields are Long epoch seconds for time, and include
 * both ID and name for created/updated users.
 */
@MappedSuperclass
open class AuditedFieldsEntity : AuditedEntity() {

    // 创建人ID（由 AuditorAware<Long> 提供）
    @Column(name = "`create_user_id`", updatable = false)
    open var createUserId: Long? = null

    // 创建人名称（标准审计不提供，基类回调填充）
    @Column(name = "`create_by`", length = 32)
    open var createBy: String? = null

    // 创建时间（Long 秒级时间戳；通过转换器支持 @CreatedDate -> Long）
    @Column(name = "`create_time`", updatable = false)
    open var createTime: Long? = null

    // 更新人ID（由 AuditorAware<Long> 提供）
    @Column(name = "`update_user_id`")
    open var updateUserId: Long? = null

    // 更新人名称（标准审计不提供，基类回调填充）
    @Column(name = "`update_by`", length = 32)
    open var updateBy: String? = null

    // 更新时间（Long 秒级时间戳；通过转换器支持 @LastModifiedDate -> Long）
    @Column(name = "`update_time`")
    open var updateTime: Long? = null
}

