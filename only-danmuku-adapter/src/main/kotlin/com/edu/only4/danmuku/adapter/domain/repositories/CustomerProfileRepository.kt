package com.edu.only4.danmuku.adapter.domain.repositories

import com.edu.only4.danmuku.domain.aggregates.customer_profile.CustomerProfile
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
interface CustomerProfileRepository : JpaRepository<CustomerProfile, Long>, JpaSpecificationExecutor<CustomerProfile>, 
	QuerydslPredicateExecutor<CustomerProfile> {

    @Component
    @Aggregate(aggregate = "CustomerProfile", name = "CustomerProfileRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class CustomerProfileJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<CustomerProfile>,
        jpaRepository: JpaRepository<CustomerProfile, Long>
    ) : AbstractJpaRepository<CustomerProfile, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )

    @Component
    @Aggregate(aggregate = "CustomerProfile", name = "CustomerProfileQuerydslRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class CustomerProfileQuerydslRepositoryAdapter(
        querydslPredicateExecutor: QuerydslPredicateExecutor<CustomerProfile>
    ) : AbstractQuerydslRepository<CustomerProfile>(
        querydslPredicateExecutor
    )
}