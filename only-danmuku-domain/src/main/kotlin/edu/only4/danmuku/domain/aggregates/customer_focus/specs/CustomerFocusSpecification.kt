package edu.only4.danmuku.domain.aggregates.customer_focus

import com.only4.cap4k.ddd.core.domain.aggregate.Specification
import com.only4.cap4k.ddd.core.domain.aggregate.Specification.Result
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import org.springframework.stereotype.Service

/**
 * 用户关注;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义规约方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
@Aggregate(
    aggregate = "CustomerFocus",
    name = "CustomerFocusSpecification",
    type = Aggregate.TYPE_SPECIFICATION,
    description = ""
)
class CustomerFocusSpecification : Specification<CustomerFocus> {

    override fun specify(entity: CustomerFocus): Result {
        return Result.pass()
    }

}
