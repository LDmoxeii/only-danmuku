package edu.only4.danmuku.adapter.domain.repositories

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.domain.repo.AbstractJpaRepository
import edu.only4.danmuku.domain.aggregates.video_audit_trace.VideoAuditTrace
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * Repository for VideoAuditTrace aggregate
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 */
@Repository
interface VideoAuditTraceRepository : JpaRepository<VideoAuditTrace, Long>, JpaSpecificationExecutor<VideoAuditTrace> {

    @Component
    @Aggregate(aggregate = "VideoAuditTrace", name = "VideoAuditTraceRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoAuditTraceJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<VideoAuditTrace>,
        jpaRepository: JpaRepository<VideoAuditTrace, Long>
    ) : AbstractJpaRepository<VideoAuditTrace, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )
}
