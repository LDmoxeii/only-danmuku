package com.edu.only4.danmuku.adapter.domain.repositories

import com.edu.only4.danmuku.domain.aggregates.video_file_draft.VideoFileDraft
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
interface VideoFileDraftRepository : JpaRepository<VideoFileDraft, Long>, JpaSpecificationExecutor<VideoFileDraft>, 
	QuerydslPredicateExecutor<VideoFileDraft> {

    @Component
    @Aggregate(aggregate = "VideoFileDraft", name = "VideoFileDraftRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoFileDraftJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<VideoFileDraft>,
        jpaRepository: JpaRepository<VideoFileDraft, Long>
    ) : AbstractJpaRepository<VideoFileDraft, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )

    @Component
    @Aggregate(aggregate = "VideoFileDraft", name = "VideoFileDraftQuerydslRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoFileDraftQuerydslRepositoryAdapter(
        querydslPredicateExecutor: QuerydslPredicateExecutor<VideoFileDraft>
    ) : AbstractQuerydslRepository<VideoFileDraft>(
        querydslPredicateExecutor
    )
}