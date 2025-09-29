package com.edu.only4.danmuku.domain.aggregates.category.specs

import com.edu.only4.danmuku.domain.aggregates.category.Category
import com.only4.cap4k.ddd.core.domain.aggregate.Specification
import com.only4.cap4k.ddd.core.domain.aggregate.Specification.Result
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import org.springframework.stereotype.Service

/**
 * @author cap4k-ddd-codegen
 * @date 2025/09/27
 */
@Service
@Aggregate(aggregate = "Category", name = "CategorySpecification", type = Aggregate.TYPE_SPECIFICATION, description = "")
class CategorySpecification : Specification<Category> {

    override fun specify(entity: Category): Result {
        return Result.pass()
    }

}
