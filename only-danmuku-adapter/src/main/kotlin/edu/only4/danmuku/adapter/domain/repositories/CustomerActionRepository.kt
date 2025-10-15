package edu.only4.danmuku.adapter.domain.repositories

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.domain.repo.AbstractJpaRepository
import com.only4.cap4k.ddd.domain.repo.querydsl.AbstractQuerydslRepository

import edu.only4.danmuku.domain.aggregates.customer_action.CustomerAction

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * Repository for CustomerAction aggregate
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Repository
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
