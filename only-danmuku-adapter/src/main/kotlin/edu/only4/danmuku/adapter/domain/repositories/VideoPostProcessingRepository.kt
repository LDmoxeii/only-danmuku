package edu.only4.danmuku.adapter.domain.repositories

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.domain.repo.AbstractJpaRepository

import edu.only4.danmuku.domain.aggregates.video_post_processing.VideoPostProcessing

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * Repository for VideoPostProcessing aggregate
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 */
@Repository
interface VideoPostProcessingRepository : JpaRepository<VideoPostProcessing, Long>, JpaSpecificationExecutor<VideoPostProcessing> {

    @Component
    @Aggregate(aggregate = "VideoPostProcessing", name = "VideoPostProcessingRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoPostProcessingJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<VideoPostProcessing>,
        jpaRepository: JpaRepository<VideoPostProcessing, Long>
    ) : AbstractJpaRepository<VideoPostProcessing, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )
}
