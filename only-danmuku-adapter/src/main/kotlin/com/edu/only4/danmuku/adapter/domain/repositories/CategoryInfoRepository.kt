package com.edu.only4.danmuku.adapter.domain.repositories

import com.edu.only4.danmuku.domain.aggregates.category_info.CategoryInfo
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
interface CategoryInfoRepository : JpaRepository<CategoryInfo, Long>, JpaSpecificationExecutor<CategoryInfo>, 
	QuerydslPredicateExecutor<CategoryInfo> {

    @Component
    @Aggregate(aggregate = "CategoryInfo", name = "CategoryInfoRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class CategoryInfoJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<CategoryInfo>,
        jpaRepository: JpaRepository<CategoryInfo, Long>
    ) : AbstractJpaRepository<CategoryInfo, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )

    @Component
    @Aggregate(aggregate = "CategoryInfo", name = "CategoryInfoQuerydslRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class CategoryInfoQuerydslRepositoryAdapter(
        querydslPredicateExecutor: QuerydslPredicateExecutor<CategoryInfo>
    ) : AbstractQuerydslRepository<CategoryInfo>(
        querydslPredicateExecutor
    )
}