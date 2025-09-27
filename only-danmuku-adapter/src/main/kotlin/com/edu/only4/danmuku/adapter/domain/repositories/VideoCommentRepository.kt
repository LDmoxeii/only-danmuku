package com.edu.only4.danmuku.adapter.domain.repositories

import com.edu.only4.danmuku.domain.aggregates.video_comment.VideoComment
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.domain.repo.AbstractJpaRepository
import com.only4.cap4k.ddd.domain.repo.querydsl.AbstractQuerydslRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Component

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @date 2025/09/27
 */
interface VideoCommentRepository : JpaRepository<VideoComment, Long>, JpaSpecificationExecutor<VideoComment>, 
	QuerydslPredicateExecutor<VideoComment> {

    @Component
    @Aggregate(aggregate = "VideoComment", name = "VideoCommentRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoCommentJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<VideoComment>,
        jpaRepository: JpaRepository<VideoComment, Long>
    ) : AbstractJpaRepository<VideoComment, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )

    @Component
    @Aggregate(aggregate = "VideoComment", name = "VideoCommentQuerydslRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoCommentQuerydslRepositoryAdapter(
        querydslPredicateExecutor: QuerydslPredicateExecutor<VideoComment>
    ) : AbstractQuerydslRepository<VideoComment>(
        querydslPredicateExecutor
    )
}