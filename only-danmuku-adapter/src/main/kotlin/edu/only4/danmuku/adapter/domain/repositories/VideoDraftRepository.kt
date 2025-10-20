package edu.only4.danmuku.adapter.domain.repositories

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.domain.repo.AbstractJpaRepository
import com.only4.cap4k.ddd.domain.repo.querydsl.AbstractQuerydslRepository

import edu.only4.danmuku.domain.aggregates.video_draft.VideoDraft

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * Repository for VideoDraft aggregate
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/20
 */
@Repository
interface VideoDraftRepository : JpaRepository<VideoDraft, Long>, JpaSpecificationExecutor<VideoDraft>,
    QuerydslPredicateExecutor<VideoDraft> {

    @Component
    @Aggregate(aggregate = "VideoDraft", name = "VideoDraftRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoDraftJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<VideoDraft>,
        jpaRepository: JpaRepository<VideoDraft, Long>
    ) : AbstractJpaRepository<VideoDraft, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )
    @Component
    @Aggregate(aggregate = "VideoDraft", name = "VideoDraftQuerydslRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoDraftQuerydslRepositoryAdapter(
        querydslPredicateExecutor: QuerydslPredicateExecutor<VideoDraft>
    ) : AbstractQuerydslRepository<VideoDraft>(
        querydslPredicateExecutor
    )
}
