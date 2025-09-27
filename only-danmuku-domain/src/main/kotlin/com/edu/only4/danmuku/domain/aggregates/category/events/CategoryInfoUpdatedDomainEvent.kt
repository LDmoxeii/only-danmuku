package com.edu.only4.danmuku.domain.aggregates.category.events

import com.edu.only4.danmuku.domain.aggregates.category.Category
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

@DomainEvent(persist = false)
@Aggregate(aggregate = "Category", name = "CategoryInfoUpdatedDomainEvent", type = Aggregate.TYPE_DOMAIN_EVENT, description = "")
class CategoryInfoUpdatedDomainEvent(val entity: Category)
