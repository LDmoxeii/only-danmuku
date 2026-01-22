package edu.only4.danmuku.domain.aggregates.video_audit_trace

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.DomainEventSupervisorSupport.events
import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity
import edu.only4.danmuku.domain.aggregates.user.enums.UserType
import edu.only4.danmuku.domain.aggregates.video_audit_trace.enums.AuditStatus
import edu.only4.danmuku.domain.aggregates.video_audit_trace.events.VideoAuditTraceRecordedDomainEvent
import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 视频审核追溯记录;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2026/01/22
 */
@Aggregate(aggregate = "VideoAuditTrace", name = "VideoAuditTrace", root = true, type = Aggregate.TYPE_ENTITY, description = "视频审核追溯记录，")
@Entity
@Table(name = "`video_audit_trace`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `video_audit_trace` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class VideoAuditTrace(
    id: Long = 0L,
    videoPostId: Long = 0L,
    auditStatus: AuditStatus = AuditStatus.valueOf(0),
    reviewerId: Long? = null,
    reviewerType: UserType = UserType.valueOf(0),
    reason: String? = null,
    occurTime: Long = 0L,
) : AuditedFieldsEntity() {
    // 【字段映射开始】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    /**
     * ID
     * bigint
     */
    @Id
    @GeneratedValue(generator = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator")
    @GenericGenerator(name = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator", strategy = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator")
    @Column(name = "`id`", insertable = false, updatable = false)
    var id: Long = id
        internal set

    /**
     * 视频稿件ID
     * bigint
     */
    @Column(name = "`video_post_id`")
    var videoPostId: Long = videoPostId
        internal set

    /**
     * 审核状态
     * 0:UNKNOW:未知
     * 1:PASSED:审核通过
     * 2:FAILED:审核不通过
     * tinyint(1)
     */
    @Convert(converter = AuditStatus.Converter::class)
    @Column(name = "`audit_status`")
    var auditStatus: AuditStatus = auditStatus
        internal set

    /**
     * 审核人ID，可为空
     * bigint
     */
    @Column(name = "`reviewer_id`")
    var reviewerId: Long? = reviewerId
        internal set

    /**
     * 审核人类型
     * 0:UNKNOW:未知类型
     * 1:SYS_USER:系统管理员
     * tinyint(1)
     */
    @Convert(converter = UserType.Converter::class)
    @Column(name = "`reviewer_type`")
    var reviewerType: UserType = reviewerType
        internal set

    /**
     * 审核备注/失败原因
     * varchar(255)
     */
    @Column(name = "`reason`")
    var reason: String? = reason
        internal set

    /**
     * 审核发生时间
     * bigint
     */
    @Column(name = "`occur_time`")
    var occurTime: Long = occurTime
        internal set

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    fun onCreate() {
        events().attach(this) { VideoAuditTraceRecordedDomainEvent(this) }
    }
}
