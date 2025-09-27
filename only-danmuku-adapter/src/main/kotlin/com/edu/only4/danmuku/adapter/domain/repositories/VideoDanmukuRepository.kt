package com.edu.only4.danmuku.adapter.domain.repositories

import com.edu.only4.danmuku.domain.aggregates.video_danmuku.VideoDanmuku
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
interface VideoDanmukuRepository : JpaRepository<VideoDanmuku, Long>, JpaSpecificationExecutor<VideoDanmuku>, 
	QuerydslPredicateExecutor<VideoDanmuku> {

    @Component
    @Aggregate(aggregate = "VideoDanmuku", name = "VideoDanmukuRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoDanmukuJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<VideoDanmuku>,
        jpaRepository: JpaRepository<VideoDanmuku, Long>
    ) : AbstractJpaRepository<VideoDanmuku, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )

    @Component
    @Aggregate(aggregate = "VideoDanmuku", name = "VideoDanmukuQuerydslRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoDanmukuQuerydslRepositoryAdapter(
        querydslPredicateExecutor: QuerydslPredicateExecutor<VideoDanmuku>
    ) : AbstractQuerydslRepository<VideoDanmuku>(
        querydslPredicateExecutor
    )
}