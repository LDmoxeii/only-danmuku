package edu.only4.danmuku.adapter.domain.repositories

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.domain.repo.AbstractJpaRepository

import edu.only4.danmuku.domain.aggregates.customer_video_series.CustomerVideoSeries

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * Repository for CustomerVideoSeries aggregate
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/29
 */
@Repository
interface CustomerVideoSeriesRepository : JpaRepository<CustomerVideoSeries, Long>, JpaSpecificationExecutor<CustomerVideoSeries> {

    @Component
    @Aggregate(aggregate = "CustomerVideoSeries", name = "CustomerVideoSeriesRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class CustomerVideoSeriesJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<CustomerVideoSeries>,
        jpaRepository: JpaRepository<CustomerVideoSeries, Long>
    ) : AbstractJpaRepository<CustomerVideoSeries, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )
}
