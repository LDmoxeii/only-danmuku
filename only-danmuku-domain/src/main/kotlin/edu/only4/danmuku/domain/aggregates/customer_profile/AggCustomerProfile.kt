package edu.only4.danmuku.domain.aggregates.customer_profile

import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

import edu.only4.danmuku.domain.aggregates.customer_profile.factory.CustomerProfileFactory

/**
 * CustomerProfile聚合封装
 * 用户信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
class AggCustomerProfile(
    payload: CustomerProfileFactory.Payload? = null,
) : Aggregate.Default<CustomerProfile>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) :
        com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggCustomerProfile, Long>(key)

}
