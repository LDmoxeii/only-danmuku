package edu.only4.danmuku.domain.aggregates.category.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.category.Category

import org.springframework.stereotype.Service

/**
 * 分类信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
@Aggregate(
    aggregate = "Category",
    name = "CategoryFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class CategoryFactory : AggregateFactory<CategoryFactory.Payload, Category> {

    override fun create(payload: Payload): Category {
        return Category(

        )
    }

     @Aggregate(
        aggregate = "Category",
        name = "CategoryPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
        val name: String
    ) : AggregatePayload<Category>

}
