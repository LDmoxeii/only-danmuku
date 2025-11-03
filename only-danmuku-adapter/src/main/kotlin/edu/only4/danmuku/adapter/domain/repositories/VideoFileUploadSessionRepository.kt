package edu.only4.danmuku.adapter.domain.repositories

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.domain.repo.AbstractJpaRepository

import edu.only4.danmuku.domain.aggregates.video_file_upload_session.VideoFileUploadSession

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * Repository for VideoFileUploadSession aggregate
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/03
 */
@Repository
interface VideoFileUploadSessionRepository : JpaRepository<VideoFileUploadSession, Long>, JpaSpecificationExecutor<VideoFileUploadSession> {

    @Component
    @Aggregate(aggregate = "VideoFileUploadSession", name = "VideoFileUploadSessionRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoFileUploadSessionJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<VideoFileUploadSession>,
        jpaRepository: JpaRepository<VideoFileUploadSession, Long>
    ) : AbstractJpaRepository<VideoFileUploadSession, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )
}
