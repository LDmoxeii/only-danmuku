package edu.only4.danmuku.domain.aggregates.user_login_log

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity
import edu.only4.danmuku.domain.aggregates.user.enums.UserType
import edu.only4.danmuku.domain.aggregates.user_login_log.enums.LoginResult
import edu.only4.danmuku.domain.aggregates.user_login_log.enums.LoginType

import jakarta.persistence.*

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 用户登录日志;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/11/20
 */
@Aggregate(aggregate = "UserLoginLog", name = "UserLoginLog", root = true, type = Aggregate.TYPE_ENTITY, description = "用户登录日志，")
@Entity
@Table(name = "`user_login_log`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `user_login_log` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class UserLoginLog(
    id: Long = 0L,
    userId: Long? = null,
    userType: UserType = UserType.valueOf(0),
    loginName: String = "",
    loginType: LoginType = LoginType.valueOf(0),
    result: LoginResult = LoginResult.valueOf(0),
    ip: String = "",
    userAgent: String? = null,
    reason: String? = null,
    occurTime: Long = 0L,
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
     * 用户ID，可为空（未知用户）
     * bigint
     */
    @Column(name = "`user_id`")
    var userId: Long? = userId
        internal set

    /**
     * 用户类型
     * 0:UNKNOW:未知类型
     * 1:SYS_USER:系统管理员
     * tinyint(1)
     */
    @Convert(converter = UserType.Converter::class)
    @Column(name = "`user_type`")
    var userType: UserType = userType
        internal set

    /**
     * 登录名（邮箱或手机号）
     * varchar(150)
     */
    @Column(name = "`login_name`")
    var loginName: String = loginName
        internal set

    /**
     * 登录类型
     * 0:UNKNOW:未知登录类型
     * 1:PASSWORD:密码登录
     * 2:SMS_CODE:短信验证码登录
     * 3:LOGOUT:退出登录
     * tinyint(1)
     */
    @Convert(converter = LoginType.Converter::class)
    @Column(name = "`login_type`")
    var loginType: LoginType = loginType
        internal set

    /**
     * 登录结果
     * 0:UNKNOW:未知结果
     * 1:SUCCESS:成功
     * 2:FAILURE:失败
     * tinyint(1)
     */
    @Convert(converter = LoginResult.Converter::class)
    @Column(name = "`result`")
    var result: LoginResult = result
        internal set

    /**
     * 登录IP
     * varchar(45)
     */
    @Column(name = "`ip`")
    var ip: String = ip
        internal set

    /**
     * User-Agent
     * varchar(255)
     */
    @Column(name = "`user_agent`")
    var userAgent: String? = userAgent
        internal set

    /**
     * 失败原因描述
     * varchar(200)
     */
    @Column(name = "`reason`")
    var reason: String? = reason
        internal set

    /**
     * 发生时间（秒级或毫秒级时间戳）
     * bigint
     */
    @Column(name = "`occur_time`")
    var occurTime: Long = occurTime
        internal set

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动
}
