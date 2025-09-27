package com.edu.only4.danmuku.adapter.domain.repositories

import com.edu.only4.danmuku.domain.aggregates.customer_action.CustomerAction
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
interface CustomerActionRepository : JpaRepository<CustomerAction, Long>, JpaSpecificationExecutor<CustomerAction>, 
	QuerydslPredicateExecutor<CustomerAction> {

    @Component
    @Aggregate(aggregate = "CustomerAction", name = "CustomerActionRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class CustomerActionJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<CustomerAction>,
        jpaRepository: JpaRepository<CustomerAction, Long>
    ) : AbstractJpaRepository<CustomerAction, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )

    @Component
    @Aggregate(aggregate = "CustomerAction", name = "CustomerActionQuerydslRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class CustomerActionQuerydslRepositoryAdapter(
        querydslPredicateExecutor: QuerydslPredicateExecutor<CustomerAction>
    ) : AbstractQuerydslRepository<CustomerAction>(
        querydslPredicateExecutor
    )
}