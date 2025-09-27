package com.edu.only4.danmuku.domain.aggregates.category_info

import com.edu.only4.danmuku.domain.aggregates.category_info.factory.CategoryInfoFactory
import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

class AggCategoryInfo (
payload: CategoryInfoFactory.Payload? = null,
) : Aggregate.Default<CategoryInfo>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) : com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggCategoryInfo, Long > (key)
}
