package edu.only4.danmuku.domain.aggregates.customer_focus

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.DomainEventSupervisorSupport.events

import edu.only4.danmuku.domain.aggregates.customer_focus.events.UserFocusedDomainEvent
import edu.only4.danmuku.domain.aggregates.customer_focus.events.UserUnfocusedDomainEvent

import jakarta.persistence.*
import jakarta.persistence.Table

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
 * @date 2025/10/30
 */
@Aggregate(aggregate = "CustomerFocus", name = "CustomerFocus", root = true, type = Aggregate.TYPE_ENTITY, description = "用户关注，")
@Entity
@Table(name = "`customer_focus`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `customer_focus` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class CustomerFocus(
    id: Long = 0L,
    customerId: String = "",
    focusCustomerId: String = "",
    createUserId: Long? = null,
    createBy: String? = null,
    createTime: Long? = null,
    updateUserId: Long? = null,
    updateBy: String? = null,
    updateTime: Long? = null,
    deleted: Long = 0L
) {
    // 【字段映射开始】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    /**
     * ID
     * bigint
     */
    @Id
    @GeneratedValue(generator = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator")
    @GenericGenerator(name = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator", strategy = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator")
    @Column(name = "`id`", insertable = false, updatable = false)
    var id: Long = id
        internal set

    /**
     * 用户ID
     * varchar(10)
     */
    @Column(name = "`customer_id`")
    var customerId: String = customerId
        internal set

    /**
     * 用户ID
     * varchar(10)
     */
    @Column(name = "`focus_customer_id`")
    var focusCustomerId: String = focusCustomerId
        internal set

    /**
     * 创建人ID
     * bigint
     */
    @Column(name = "`create_user_id`")
    var createUserId: Long? = createUserId
        internal set

    /**
     * 创建人名称
     * varchar(32)
     */
    @Column(name = "`create_by`")
    var createBy: String? = createBy
        internal set

    /**
     * 创建时间
     * bigint
     */
    @Column(name = "`create_time`")
    var createTime: Long? = createTime
        internal set

    /**
     * 更新人ID
     * bigint
     */
    @Column(name = "`update_user_id`")
    var updateUserId: Long? = updateUserId
        internal set

    /**
     * 更新人名称
     * varchar(32)
     */
    @Column(name = "`update_by`")
    var updateBy: String? = updateBy
        internal set

    /**
     * 更新时间
     * bigint
     */
    @Column(name = "`update_time`")
    var updateTime: Long? = updateTime
        internal set

    /**
     * 删除标识 0：未删除 id：已删除
     * bigint
     */
    @Column(name = "`deleted`")
    var deleted: Long = deleted
        internal set

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】

    fun onCreate() {
        events().attach(this) { UserFocusedDomainEvent(this) }
    }

    fun onDelete() {
        events().attach(this) { UserUnfocusedDomainEvent(this) }
    }

    // 【行为方法结束】
}
