package edu.only4.danmuku.domain.aggregates.customer_profile

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.DomainEventSupervisorSupport.events

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity
import edu.only4.danmuku.domain.aggregates.customer_profile.enums.SexType
import edu.only4.danmuku.domain.aggregates.customer_profile.enums.ThemeType
import edu.only4.danmuku.domain.aggregates.customer_profile.events.CustomerAvatarUpdatedDomainEvent
import edu.only4.danmuku.domain.aggregates.customer_profile.events.CustomerNicknameUpdatedDomainEvent
import edu.only4.danmuku.domain.aggregates.customer_profile.events.CustomerProfileCoinsRewardedDomainEvent
import edu.only4.danmuku.domain.aggregates.customer_profile.events.CustomerProfileCreatedDomainEvent
import edu.only4.danmuku.domain.aggregates.customer_profile.events.CustomerProfileRewardCoinsReclaimedDomainEvent
import edu.only4.danmuku.domain.aggregates.customer_profile.events.CustomerProfileUpdatedDomainEvent

import jakarta.persistence.*
import jakarta.persistence.Table

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
 * @date 2025/11/04
 */
@Aggregate(aggregate = "CustomerProfile", name = "CustomerProfile", root = true, type = Aggregate.TYPE_ENTITY, description = "用户信息，")
@Entity
@Table(name = "`customer_profile`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `customer_profile` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class CustomerProfile(
    id: Long = 0L,
    userId: Long = 0L,
    nickName: String = "",
    avatar: String? = null,
    email: String = "",
    sex: SexType = SexType.valueOf(0),
    birthday: String? = null,
    school: String? = null,
    personIntroduction: String? = null,
    noticeInfo: String? = null,
    totalCoinCount: Int = 0,
    currentCoinCount: Int = 0,
    theme: ThemeType = ThemeType.valueOf(0),
) : AuditedFieldsEntity() {
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
     * bigint
     */
    @Column(name = "`user_id`")
    var userId: Long = userId
        internal set

    /**
     * 昵称
     * varchar(20)
     */
    @Column(name = "`nick_name`")
    var nickName: String = nickName
        internal set

    /**
     * 头像
     * varchar(100)
     */
    @Column(name = "`avatar`")
    var avatar: String? = avatar
        internal set

    /**
     * 邮箱
     * varchar(150)
     */
    @Column(name = "`email`")
    var email: String = email
        internal set

    /**
     * 性别
     * 0:UNKNOWN:未知
     * 1:FEMALE:女
     * 2:MALE:男
     * tinyint(1)
     */
    @Convert(converter = SexType.Converter::class)
    @Column(name = "`sex`")
    var sex: SexType = sex
        internal set

    /**
     * 出生日期
     * varchar(10)
     */
    @Column(name = "`birthday`")
    var birthday: String? = birthday
        internal set

    /**
     * 学校
     * varchar(150)
     */
    @Column(name = "`school`")
    var school: String? = school
        internal set

    /**
     * 个人简介
     * varchar(200)
     */
    @Column(name = "`person_introduction`")
    var personIntroduction: String? = personIntroduction
        internal set

    /**
     * 空间公告
     * varchar(300)
     */
    @Column(name = "`notice_info`")
    var noticeInfo: String? = noticeInfo
        internal set

    /**
     * 硬币总数量
     * int
     */
    @Column(name = "`total_coin_count`")
    var totalCoinCount: Int = totalCoinCount
        internal set

    /**
     * 当前硬币数
     * int
     */
    @Column(name = "`current_coin_count`")
    var currentCoinCount: Int = currentCoinCount
        internal set

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
    var theme: ThemeType = theme
        internal set

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
    }

    /** 更新用户资料信息 */
    fun rewardCoins(amount: Int) {
        require(amount > 0) { "奖励数量必须大于0" }
        this.totalCoinCount += amount
        this.currentCoinCount += amount

        events().attach(this) { CustomerProfileCoinsRewardedDomainEvent(this, amount) }
    }

    /** 在删除视频后回收对应奖励硬币（可能超出） */
    fun reclaimRewardCoins(amount: Int) {
        require(amount >= 0) { "扣款数量不得小于0" }
        if (amount == 0) return
        val deduction = amount.coerceAtLeast(this.currentCoinCount)
        this.currentCoinCount -= deduction
        this.totalCoinCount = (this.totalCoinCount - deduction).coerceAtLeast(0)

        events().attach(this) { CustomerProfileRewardCoinsReclaimedDomainEvent(this, deduction) }
    }


    /** 支出硬币（如修改昵称消耗） */
    fun spendCoins(amount: Int) {
        require(amount > 0) { "扣款数量必须大于0" }
        require(this.currentCoinCount >= amount) { "硬币余额不足" }
        this.currentCoinCount -= amount
        // totalCoinCount 保持累计不变
    }


    fun updateProfileInfo(
        nickName: String? = null,
        avatar: String? = null,
        sex: SexType? = null,
        birthday: String? = null,
        school: String? = null,
        personIntroduction: String? = null,
        noticeInfo: String? = null,
        theme: ThemeType? = null,
    ) {
        nickName?.let {
            if (it != this.nickName) {
                this.nickName = it
                events().attach(this) { CustomerNicknameUpdatedDomainEvent(this) }
            }
        }
        avatar?.let {
            if (it != this.avatar) {
                this.avatar = it
                events().attach(this) { CustomerAvatarUpdatedDomainEvent(this) }
            }
        }
        sex?.let { this.sex = it }
        birthday?.let { this.birthday = it }
        school?.let { this.school = it }
        personIntroduction?.let { this.personIntroduction = it }
        noticeInfo?.let { this.noticeInfo = it }
        theme?.let { this.theme = it }

        events().attach(this) { CustomerProfileUpdatedDomainEvent(this) }
    }

    // 【行为方法结束】
}
