package com.edu.only4.danmuku.domain.aggregates.statistics.specs

import com.edu.only4.danmuku.domain.aggregates.statistics.Statistics
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
@Aggregate(aggregate = "Statistics", name = "StatisticsSpecification", type = Aggregate.TYPE_SPECIFICATION, description = "")
class StatisticsSpecification : Specification<Statistics> {

    override fun specify(entity: Statistics): Result {
        return Result.pass()
    }

}
