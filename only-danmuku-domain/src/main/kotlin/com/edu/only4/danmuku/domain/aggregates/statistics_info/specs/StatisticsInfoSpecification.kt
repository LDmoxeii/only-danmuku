package com.edu.only4.danmuku.domain.aggregates.statistics_info.specs

import com.edu.only4.danmuku.domain.aggregates.statistics_info.StatisticsInfo
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
@Aggregate(aggregate = "StatisticsInfo", name = "StatisticsInfoSpecification", type = Aggregate.TYPE_SPECIFICATION, description = "")
class StatisticsInfoSpecification : Specification<StatisticsInfo> {

    override fun specify(entity: StatisticsInfo): Result {
        return Result.pass()
    }

}
