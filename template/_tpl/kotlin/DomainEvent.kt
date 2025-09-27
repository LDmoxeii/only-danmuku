package ${basePackage}.domain.aggregates${package}.events

import ${basePackage}.domain.aggregates${package}.${Entity}
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

@DomainEvent(persist = ${persist})
@Aggregate(aggregate = "${Aggregate}", name = "${DomainEvent}", type = Aggregate.TYPE_DOMAIN_EVENT, description = "")
class ${DomainEvent}(val entity: ${Entity})
