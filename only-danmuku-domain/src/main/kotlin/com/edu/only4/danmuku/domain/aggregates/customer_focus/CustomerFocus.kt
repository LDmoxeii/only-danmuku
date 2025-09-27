package com.edu.only4.danmuku.domain.aggregates.customer_focus

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 用户关注;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/09/27
 */
@Aggregate(aggregate = "CustomerFocus", name = "CustomerFocus", root = true, type = Aggregate.TYPE_ENTITY, description = "用户关注;")
@Entity
@Table(name = "`customer_focus`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `customer_focus` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class CustomerFocus (
    // 【字段映射开始】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    /**
     * ID
     * bigint
     */
    @Id
    @GeneratedValue(generator = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator")
    @GenericGenerator(name = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator", strategy = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator")
    @Column(name = "`id`", insertable = false, updatable = false)
    var id: Long = 0L,

    /**
     * 用户ID
     * varchar(10)
     */
    @Column(name = "`customer_id`")
    var customerId: String = "",

    /**
     * 用户ID
     * varchar(10)
     */
    @Column(name = "`focus_customer_id`")
    var focusCustomerId: String = "",

    /**
     * 删除标识 0：未删除 id：已删除
     * tinyint(1)
     */
    @Column(name = "`deleted`")
    var deleted: Boolean = false,
) {

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】



    // 【行为方法结束】

}
