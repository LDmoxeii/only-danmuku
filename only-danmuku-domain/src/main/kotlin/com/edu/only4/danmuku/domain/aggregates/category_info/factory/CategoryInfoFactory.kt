package com.edu.only4.danmuku.domain.aggregates.category_info.factory

import com.edu.only4.danmuku.domain.aggregates.category_info.CategoryInfo
import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import org.springframework.stereotype.Service

@Service
@Aggregate(aggregate = "CategoryInfo", name = "CategoryInfoFactory", type = Aggregate.TYPE_FACTORY, description = "")
class CategoryInfoFactory : AggregateFactory<CategoryInfoFactory.Payload, CategoryInfo> {
    override fun create(payload: Payload): CategoryInfo {
        return CategoryInfo(

        )
    }

    @Aggregate(aggregate = "CategoryInfo", name = "CategoryInfoPayload", type = Aggregate.TYPE_FACTORY_PAYLOAD, description = "")
    class Payload(

    ) : AggregatePayload<CategoryInfo>
}
