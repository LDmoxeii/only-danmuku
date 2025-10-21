package edu.only4.danmuku.adapter.domain.repositories

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.domain.repo.AbstractJpaRepository

import edu.only4.danmuku.domain.aggregates.video_file_draft.VideoFileDraft

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * Repository for VideoFileDraft aggregate
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Repository
interface VideoFileDraftRepository : JpaRepository<VideoFileDraft, Long>, JpaSpecificationExecutor<VideoFileDraft> {

    @Component
    @Aggregate(aggregate = "VideoFileDraft", name = "VideoFileDraftRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoFileDraftJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<VideoFileDraft>,
        jpaRepository: JpaRepository<VideoFileDraft, Long>
    ) : AbstractJpaRepository<VideoFileDraft, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )
}
