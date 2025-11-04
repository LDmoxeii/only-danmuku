package edu.only4.danmuku.adapter.domain.repositories

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.domain.repo.AbstractJpaRepository

import edu.only4.danmuku.domain.aggregates.video_comment.VideoComment

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * Repository for VideoComment aggregate
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/04
 */
@Repository
interface VideoCommentRepository : JpaRepository<VideoComment, Long>, JpaSpecificationExecutor<VideoComment> {

    @Component
    @Aggregate(aggregate = "VideoComment", name = "VideoCommentRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoCommentJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<VideoComment>,
        jpaRepository: JpaRepository<VideoComment, Long>
    ) : AbstractJpaRepository<VideoComment, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )
}
