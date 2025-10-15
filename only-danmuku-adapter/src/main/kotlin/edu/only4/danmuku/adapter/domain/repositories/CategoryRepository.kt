package edu.only4.danmuku.adapter.domain.repositories

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.domain.repo.AbstractJpaRepository
import com.only4.cap4k.ddd.domain.repo.querydsl.AbstractQuerydslRepository

import edu.only4.danmuku.domain.aggregates.category.Category

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * Repository for Category aggregate
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Repository
interface CategoryRepository : JpaRepository<Category, Long>, JpaSpecificationExecutor<Category>,
    QuerydslPredicateExecutor<Category> {

    @Component
    @Aggregate(aggregate = "Category", name = "CategoryRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class CategoryJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<Category>,
        jpaRepository: JpaRepository<Category, Long>
    ) : AbstractJpaRepository<Category, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )
    @Component
    @Aggregate(aggregate = "Category", name = "CategoryQuerydslRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class CategoryQuerydslRepositoryAdapter(
        querydslPredicateExecutor: QuerydslPredicateExecutor<Category>
    ) : AbstractQuerydslRepository<Category>(
        querydslPredicateExecutor
    )
}
