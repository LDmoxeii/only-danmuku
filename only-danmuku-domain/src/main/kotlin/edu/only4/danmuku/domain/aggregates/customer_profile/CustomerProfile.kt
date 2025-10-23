package edu.only4.danmuku.domain.aggregates.customer_profile

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.DomainEventSupervisorSupport.events

import edu.only4.danmuku.domain.aggregates.customer_profile.enums.SexType
import edu.only4.danmuku.domain.aggregates.customer_profile.enums.ThemeType
import edu.only4.danmuku.domain.aggregates.customer_profile.events.CustomerProfileCreatedDomainEvent

import jakarta.persistence.*

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 用户信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Aggregate(aggregate = "CustomerProfile", name = "CustomerProfile", root = true, type = Aggregate.TYPE_ENTITY, description = "用户信息，")
@Entity
@Table(name = "`customer_profile`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `customer_profile` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class CustomerProfile (
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
     * bigint
     */
    @Column(name = "`user_id`")
    var userId: Long = 0L,

    /**
     * 昵称
     * varchar(20)
     */
    @Column(name = "`nick_name`")
    var nickName: String = "",

    /**
     * 头像
     * varchar(100)
     */
    @Column(name = "`avatar`")
    var avatar: String? = null,

    /**
     * 邮箱
     * varchar(150)
     */
    @Column(name = "`email`")
    var email: String = "",

    /**
     * 性别
     * 0:UNKNOWN:未知
     * 1:FEMALE:女
     * 2:MALE:男
     * tinyint(1)
     */
    @Convert(converter = SexType.Converter::class)
    @Column(name = "`sex`")
    var sex: SexType = SexType.valueOf(0),

    /**
     * 出生日期
     * varchar(10)
     */
    @Column(name = "`birthday`")
    var birthday: String? = null,

    /**
     * 学校
     * varchar(150)
     */
    @Column(name = "`school`")
    var school: String? = null,

    /**
     * 个人简介
     * varchar(200)
     */
    @Column(name = "`person_introduction`")
    var personIntroduction: String? = null,

    /**
     * 空间公告
     * varchar(300)
     */
    @Column(name = "`notice_info`")
    var noticeInfo: String? = null,

    /**
     * 硬币总数量
     * int
     */
    @Column(name = "`total_coin_count`")
    var totalCoinCount: Int = 0,

    /**
     * 当前硬币数
     * int
     */
    @Column(name = "`current_coin_count`")
    var currentCoinCount: Int = 0,

    /**
     * 主题
     * 0:UNKNOW:未知主题
     * 1:LIGHT:浅色主题
     * 2:DARK:深色主题
     * 3:SYSTEM:跟随系统
     * tinyint(1)
     */
    @Convert(converter = ThemeType.Converter::class)
    @Column(name = "`theme`")
    var theme: ThemeType = ThemeType.valueOf(0),

    /**
     * 创建人ID
     * bigint
     */
    @Column(name = "`create_user_id`")
    var createUserId: Long? = null,

    /**
     * 创建人名称
     * varchar(32)
     */
    @Column(name = "`create_by`")
    var createBy: String? = null,

    /**
     * 创建时间
     * bigint
     */
    @Column(name = "`create_time`")
    var createTime: Long? = null,

    /**
     * 更新人ID
     * bigint
     */
    @Column(name = "`update_user_id`")
    var updateUserId: Long? = null,

    /**
     * 更新人名称
     * varchar(32)
     */
    @Column(name = "`update_by`")
    var updateBy: String? = null,

    /**
     * 更新时间
     * bigint
     */
    @Column(name = "`update_time`")
    var updateTime: Long? = null,

    /**
     * 删除标识 0：未删除 id：已删除
     * tinyint(1)
     */
    @Column(name = "`deleted`")
    var deleted: Boolean = false,
) {

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】

    fun onCreate() {
        events().attach(this) { CustomerProfileCreatedDomainEvent(this) }
    }

    /** 硬币转账：从当前用户转给目标用户 */
    fun transferCoin(toProfile: CustomerProfile, amount: Int) {
        require(amount > 0) { "转账金额必须大于0" }
        require(this.currentCoinCount >= amount) { "硬币余额不足" }
        this.currentCoinCount -= amount
        toProfile.currentCoinCount += amount
        this.totalCoinCount = this.totalCoinCount // 占位，保持生成器格式
    }

    // 【行为方法结束】
}
