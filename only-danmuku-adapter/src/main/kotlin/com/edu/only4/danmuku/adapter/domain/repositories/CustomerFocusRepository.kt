package com.edu.only4.danmuku.adapter.domain.repositories

import com.edu.only4.danmuku.domain.aggregates.customer_focus.CustomerFocus
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
interface CustomerFocusRepository : JpaRepository<CustomerFocus, Long>, JpaSpecificationExecutor<CustomerFocus>, 
	QuerydslPredicateExecutor<CustomerFocus> {

    @Component
    @Aggregate(aggregate = "CustomerFocus", name = "CustomerFocusRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class CustomerFocusJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<CustomerFocus>,
        jpaRepository: JpaRepository<CustomerFocus, Long>
    ) : AbstractJpaRepository<CustomerFocus, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )

    @Component
    @Aggregate(aggregate = "CustomerFocus", name = "CustomerFocusQuerydslRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class CustomerFocusQuerydslRepositoryAdapter(
        querydslPredicateExecutor: QuerydslPredicateExecutor<CustomerFocus>
    ) : AbstractQuerydslRepository<CustomerFocus>(
        querydslPredicateExecutor
    )
}