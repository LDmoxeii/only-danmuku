package edu.only4.danmuku.adapter.domain.repositories

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.domain.repo.AbstractJpaRepository

import edu.only4.danmuku.domain.aggregates.video_quality_policy.VideoQualityPolicy

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * Repository for VideoQualityPolicy aggregate
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 */
@Repository
interface VideoQualityPolicyRepository : JpaRepository<VideoQualityPolicy, Long>, JpaSpecificationExecutor<VideoQualityPolicy> {

    @Component
    @Aggregate(aggregate = "VideoQualityPolicy", name = "VideoQualityPolicyRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoQualityPolicyJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<VideoQualityPolicy>,
        jpaRepository: JpaRepository<VideoQualityPolicy, Long>
    ) : AbstractJpaRepository<VideoQualityPolicy, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )
}
