package edu.only4.danmuku.application.queries._share.model

import edu.only4.danmuku.domain.aggregates.user.enums.UserType
import edu.only4.danmuku.domain.aggregates.video_audit_trace.enums.AuditStatus
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "video_audit_trace")
interface VideoAuditTrace : BaseEntity {

    @Column(name = "video_post_id")
    val videoPostId: Long

    @Column(name = "audit_status")
    val auditStatus: AuditStatus

    @Column(name = "reviewer_id")
    val reviewerId: Long?

    @Column(name = "reviewer_type")
    val reviewerType: UserType

    @Column(name = "reason")
    val reason: String?

    @Column(name = "occur_time")
    val occurTime: Long
}
