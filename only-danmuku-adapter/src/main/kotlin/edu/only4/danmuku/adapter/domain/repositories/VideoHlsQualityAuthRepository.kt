package edu.only4.danmuku.adapter.domain.repositories

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.domain.repo.AbstractJpaRepository

import edu.only4.danmuku.domain.aggregates.video_hls_quality_auth.VideoHlsQualityAuth

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * Repository for VideoHlsQualityAuth aggregate
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 */
@Repository
interface VideoHlsQualityAuthRepository : JpaRepository<VideoHlsQualityAuth, Long>, JpaSpecificationExecutor<VideoHlsQualityAuth> {

    @Component
    @Aggregate(aggregate = "VideoHlsQualityAuth", name = "VideoHlsQualityAuthRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoHlsQualityAuthJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<VideoHlsQualityAuth>,
        jpaRepository: JpaRepository<VideoHlsQualityAuth, Long>
    ) : AbstractJpaRepository<VideoHlsQualityAuth, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )
}
