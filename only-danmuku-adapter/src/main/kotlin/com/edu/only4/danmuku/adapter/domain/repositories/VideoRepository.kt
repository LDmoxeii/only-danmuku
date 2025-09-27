package com.edu.only4.danmuku.adapter.domain.repositories

import com.edu.only4.danmuku.domain.aggregates.video.Video
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
interface VideoRepository : JpaRepository<Video, Long>, JpaSpecificationExecutor<Video>, 
	QuerydslPredicateExecutor<Video> {

    @Component
    @Aggregate(aggregate = "Video", name = "VideoRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<Video>,
        jpaRepository: JpaRepository<Video, Long>
    ) : AbstractJpaRepository<Video, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )

    @Component
    @Aggregate(aggregate = "Video", name = "VideoQuerydslRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoQuerydslRepositoryAdapter(
        querydslPredicateExecutor: QuerydslPredicateExecutor<Video>
    ) : AbstractQuerydslRepository<Video>(
        querydslPredicateExecutor
    )
}