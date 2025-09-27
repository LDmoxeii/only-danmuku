package com.edu.only4.danmuku.domain.aggregates.customer_video_series.specs

import com.edu.only4.danmuku.domain.aggregates.customer_video_series.CustomerVideoSeries
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
@Aggregate(aggregate = "CustomerVideoSeries", name = "CustomerVideoSeriesSpecification", type = Aggregate.TYPE_SPECIFICATION, description = "")
class CustomerVideoSeriesSpecification : Specification<CustomerVideoSeries> {

    override fun specify(entity: CustomerVideoSeries): Result {
        return Result.pass()
    }

}
