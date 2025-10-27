package edu.only4.danmuku.domain.aggregates.user

import cn.hutool.crypto.digest.BCrypt
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.DomainEventSupervisorSupport.events
import edu.only4.danmuku.domain.aggregates.user.enums.UserType
import edu.only4.danmuku.domain.aggregates.user.events.AccountDisabledDomainEvent
import edu.only4.danmuku.domain.aggregates.user.events.AccountEnabledDomainEvent
import edu.only4.danmuku.domain.aggregates.user.events.UserCreatedDomainEvent
import jakarta.persistence.*
import jakarta.persistence.Table
import org.hibernate.annotations.*

/**
 * 帐号;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Aggregate(aggregate = "User", name = "User", root = true, type = Aggregate.TYPE_ENTITY, description = "帐号，")
@Entity
@Table(name = "`user`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `user` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class User (
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
     * 帐号类型
     * 0:UNKNOW:未知类型
     * 1:SYS_USER:系统管理员
     * tinyint(1)
     */
    @Convert(converter = UserType.Converter::class)
    @Column(name = "`type`")
    var type: UserType = UserType.valueOf(0),

    /**
     * 昵称
     * varchar(20)
     */
    @Column(name = "`nick_name`")
    var nickName: String = "",

    /**
     * 邮箱
     * varchar(150)
     */
    @Column(name = "`email`")
    var email: String = "",

    /**
     * 密码
     * varchar(50)
     */
    @Column(name = "`password`")
    var password: String = "",

    /**
     * 加入时间
     * bigint
     */
    @Column(name = "`join_time`")
    var joinTime: Long = 0L,

    /**
     * 最后登录时间
     * bigint
     */
    @Column(name = "`last_login_time`")
    var lastLoginTime: Long? = null,

    /**
     * 最后登录IP
     * varchar(15)
     */
    @Column(name = "`last_login_ip`")
    var lastLoginIp: String? = null,

    /**
     * 0:禁用 1:正常
     * tinyint(1)
     */
    @Column(name = "`status`")
    var status: Boolean = true,

    /**
     * 关联ID; 用户、管理员 = 0
     * bigint
     */
    @Column(name = "`related_id`")
    var relatedId: Long? = null,

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
     * bigint
     */
    @Column(name = "`deleted`")
    var deleted: Long = 0L,
) {

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动
    companion object {
        fun isPasswordCorrect(password: String, input: String): Boolean {
            BCrypt.hashpw(input)
            return password == input
        }
    }

    // 【行为方法开始】

    fun onCreate() {
        // 密码加密
//        this.password = BCrypt.hashpw(this.password)
        events().attach(this) { UserCreatedDomainEvent(entity = this) }
    }

    /** 切换账号状态 */
    fun changeStatus(newStatus: Boolean) {
        if (this.status == newStatus) return
        this.status = newStatus
        if (newStatus) {
            events().attach(this) { AccountEnabledDomainEvent(entity = this) }
        } else {
            events().attach(this) { AccountDisabledDomainEvent(entity = this) }
        }
    }

    fun updateLoginInfo(loginTime: Long, loginIp: String) {
        this.lastLoginTime = loginTime
        this.lastLoginIp = loginIp
    }

    // 【行为方法结束】
}
