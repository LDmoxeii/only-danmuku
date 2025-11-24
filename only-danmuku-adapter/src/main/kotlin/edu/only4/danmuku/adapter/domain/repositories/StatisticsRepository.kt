package edu.only4.danmuku.adapter.domain.repositories

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.domain.repo.AbstractJpaRepository

import edu.only4.danmuku.domain.aggregates.statistics.Statistics

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * Repository for Statistics aggregate
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 */
@Repository
interface StatisticsRepository : JpaRepository<Statistics, Long>, JpaSpecificationExecutor<Statistics> {

    @Component
    @Aggregate(aggregate = "Statistics", name = "StatisticsRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class StatisticsJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<Statistics>,
        jpaRepository: JpaRepository<Statistics, Long>
    ) : AbstractJpaRepository<Statistics, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )
}
