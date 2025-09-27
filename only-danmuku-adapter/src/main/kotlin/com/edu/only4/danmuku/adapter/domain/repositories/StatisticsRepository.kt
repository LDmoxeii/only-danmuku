package com.edu.only4.danmuku.adapter.domain.repositories

import com.edu.only4.danmuku.domain.aggregates.statistics.Statistics
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
interface StatisticsRepository : JpaRepository<Statistics, Long>, JpaSpecificationExecutor<Statistics>, 
	QuerydslPredicateExecutor<Statistics> {

    @Component
    @Aggregate(aggregate = "Statistics", name = "StatisticsRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class StatisticsJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<Statistics>,
        jpaRepository: JpaRepository<Statistics, Long>
    ) : AbstractJpaRepository<Statistics, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )

    @Component
    @Aggregate(aggregate = "Statistics", name = "StatisticsQuerydslRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class StatisticsQuerydslRepositoryAdapter(
        querydslPredicateExecutor: QuerydslPredicateExecutor<Statistics>
    ) : AbstractQuerydslRepository<Statistics>(
        querydslPredicateExecutor
    )
}