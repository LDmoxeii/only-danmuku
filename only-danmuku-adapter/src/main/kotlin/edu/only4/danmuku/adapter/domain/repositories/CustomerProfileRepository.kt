package edu.only4.danmuku.adapter.domain.repositories

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.domain.repo.AbstractJpaRepository

import edu.only4.danmuku.domain.aggregates.customer_profile.CustomerProfile

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * Repository for CustomerProfile aggregate
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/30
 */
@Repository
interface CustomerProfileRepository : JpaRepository<CustomerProfile, Long>, JpaSpecificationExecutor<CustomerProfile> {

    @Component
    @Aggregate(aggregate = "CustomerProfile", name = "CustomerProfileRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class CustomerProfileJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<CustomerProfile>,
        jpaRepository: JpaRepository<CustomerProfile, Long>
    ) : AbstractJpaRepository<CustomerProfile, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )
}
