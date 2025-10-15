package edu.only4.danmuku.domain.aggregates.customer_message

import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

import edu.only4.danmuku.domain.aggregates.customer_message.factory.CustomerMessageFactory

/**
 * CustomerMessage聚合封装
 * 用户消息表;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
class AggCustomerMessage(
    payload: CustomerMessageFactory.Payload? = null,
) : Aggregate.Default<CustomerMessage>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) :
        com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggCustomerMessage, Long>(key)

}
