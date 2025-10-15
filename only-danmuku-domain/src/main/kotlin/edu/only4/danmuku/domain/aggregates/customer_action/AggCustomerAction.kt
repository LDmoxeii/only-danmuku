package edu.only4.danmuku.domain.aggregates.customer_action

import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

import edu.only4.danmuku.domain.aggregates.customer_action.factory.CustomerActionFactory

/**
 * CustomerAction聚合封装
 * 用户行为 点赞、评论;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
class AggCustomerAction(
    payload: CustomerActionFactory.Payload? = null,
) : Aggregate.Default<CustomerAction>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) :
        com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggCustomerAction, Long>(key)

}
