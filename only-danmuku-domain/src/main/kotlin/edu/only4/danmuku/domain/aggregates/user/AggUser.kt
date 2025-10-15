package edu.only4.danmuku.domain.aggregates.user

import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

import edu.only4.danmuku.domain.aggregates.user.factory.UserFactory

/**
 * User聚合封装
 * 帐号;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
class AggUser(
    payload: UserFactory.Payload? = null,
) : Aggregate.Default<User>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) :
        com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggUser, Long>(key)

}
