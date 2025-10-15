package edu.only4.danmuku.domain.aggregates.category

import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

import edu.only4.danmuku.domain.aggregates.category.factory.CategoryFactory

/**
 * Category聚合封装
 * 分类信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
class AggCategory(
    payload: CategoryFactory.Payload? = null,
) : Aggregate.Default<Category>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) :
        com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggCategory, Long>(key)

}
