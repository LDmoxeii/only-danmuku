package com.edu.only4.danmuku.adapter.domain.repositories

import com.edu.only4.danmuku.domain.aggregates.customer_message.CustomerMessage
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
interface CustomerMessageRepository : JpaRepository<CustomerMessage, Long>, JpaSpecificationExecutor<CustomerMessage>, 
	QuerydslPredicateExecutor<CustomerMessage> {

    @Component
    @Aggregate(aggregate = "CustomerMessage", name = "CustomerMessageRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class CustomerMessageJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<CustomerMessage>,
        jpaRepository: JpaRepository<CustomerMessage, Long>
    ) : AbstractJpaRepository<CustomerMessage, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )

    @Component
    @Aggregate(aggregate = "CustomerMessage", name = "CustomerMessageQuerydslRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class CustomerMessageQuerydslRepositoryAdapter(
        querydslPredicateExecutor: QuerydslPredicateExecutor<CustomerMessage>
    ) : AbstractQuerydslRepository<CustomerMessage>(
        querydslPredicateExecutor
    )
}