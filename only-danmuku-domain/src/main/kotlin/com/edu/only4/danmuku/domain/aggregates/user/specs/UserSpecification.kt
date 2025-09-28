package com.edu.only4.danmuku.domain.aggregates.user.specs

import com.edu.only4.danmuku.domain.aggregates.user.User
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
@Aggregate(aggregate = "User", name = "UserSpecification", type = Aggregate.TYPE_SPECIFICATION, description = "")
class UserSpecification : Specification<User> {

    override fun specify(entity: User): Result {
        return Result.pass()
    }

}
