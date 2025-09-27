package com.edu.only4.danmuku.domain.aggregates.category_info.specs

import com.edu.only4.danmuku.domain.aggregates.category_info.CategoryInfo
import com.only4.cap4k.ddd.core.domain.aggregate.Specification
import com.only4.cap4k.ddd.core.domain.aggregate.Specification.Result
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import org.springframework.stereotype.Service

/**
 * 
 *
 * @author cap4k-ddd-codegen
 * @date 2025/09/27
 */
@Service
@Aggregate(aggregate = "CategoryInfo", name = "CategoryInfoSpecification", type = Aggregate.TYPE_SPECIFICATION, description = "")
class CategoryInfoSpecification : Specification<CategoryInfo> {

    override fun specify(entity: CategoryInfo): Result {
        return Result.pass()
    }

}
