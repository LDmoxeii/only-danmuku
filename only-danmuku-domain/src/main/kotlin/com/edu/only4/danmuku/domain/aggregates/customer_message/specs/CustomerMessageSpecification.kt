package com.edu.only4.danmuku.domain.aggregates.customer_message.specs

import com.edu.only4.danmuku.domain.aggregates.customer_message.CustomerMessage
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
@Aggregate(aggregate = "CustomerMessage", name = "CustomerMessageSpecification", type = Aggregate.TYPE_SPECIFICATION, description = "")
class CustomerMessageSpecification : Specification<CustomerMessage> {

    override fun specify(entity: CustomerMessage): Result {
        return Result.pass()
    }

}
