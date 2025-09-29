package com.edu.only4.danmuku.domain.aggregates.customer_profile.specs

import com.edu.only4.danmuku.domain.aggregates.customer_profile.CustomerProfile
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
@Aggregate(aggregate = "CustomerProfile", name = "CustomerProfileSpecification", type = Aggregate.TYPE_SPECIFICATION, description = "")
class CustomerProfileSpecification : Specification<CustomerProfile> {

    override fun specify(entity: CustomerProfile): Result {
        return Result.pass()
    }

}
