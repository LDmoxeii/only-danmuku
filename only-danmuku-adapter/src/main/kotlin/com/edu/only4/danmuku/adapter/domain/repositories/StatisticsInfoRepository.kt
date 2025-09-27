package com.edu.only4.danmuku.adapter.domain.repositories

import com.edu.only4.danmuku.domain.aggregates.statistics_info.StatisticsInfo
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
interface StatisticsInfoRepository : JpaRepository<StatisticsInfo, Long>, JpaSpecificationExecutor<StatisticsInfo>, 
	QuerydslPredicateExecutor<StatisticsInfo> {

    @Component
    @Aggregate(aggregate = "StatisticsInfo", name = "StatisticsInfoRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class StatisticsInfoJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<StatisticsInfo>,
        jpaRepository: JpaRepository<StatisticsInfo, Long>
    ) : AbstractJpaRepository<StatisticsInfo, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )

    @Component
    @Aggregate(aggregate = "StatisticsInfo", name = "StatisticsInfoQuerydslRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class StatisticsInfoQuerydslRepositoryAdapter(
        querydslPredicateExecutor: QuerydslPredicateExecutor<StatisticsInfo>
    ) : AbstractQuerydslRepository<StatisticsInfo>(
        querydslPredicateExecutor
    )
}