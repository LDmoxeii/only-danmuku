package edu.only4.danmuku.domain.aggregates.user_abnormal_operation_log

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity
import edu.only4.danmuku.domain.aggregates.user.enums.UserType
import edu.only4.danmuku.domain.aggregates.user_abnormal_operation_log.enums.AbnormalOpType

import jakarta.persistence.*

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 用户异常操作日志;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/11/24
 */
@Aggregate(aggregate = "UserAbnormalOperationLog", name = "UserAbnormalOperationLog", root = true, type = Aggregate.TYPE_ENTITY, description = "用户异常操作日志，")
@Entity
@Table(name = "`user_abnormal_operation_log`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `user_abnormal_operation_log` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class UserAbnormalOperationLog(
    id: Long = 0L,
    userId: Long = 0L,
    userType: UserType = UserType.valueOf(0),
    opType: AbnormalOpType = AbnormalOpType.valueOf(0),
    ip: String = "",
    occurTime: Long = 0L,
    description: String? = null,
    extra: String? = null,
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
     * 异常类型
     * 0:UNKNOW:未知异常
     * 1:PASSWORD_FAIL_TOO_MANY_TIMES:密码失败次数过多
     * tinyint(1)
     */
    @Convert(converter = AbnormalOpType.Converter::class)
    @Column(name = "`op_type`")
    var opType: AbnormalOpType = opType
        internal set

    /**
     * 触发IP
     * varchar(45)
     */
    @Column(name = "`ip`")
    var ip: String = ip
        internal set

    /**
     * 触发时间
     * bigint
     */
    @Column(name = "`occur_time`")
    var occurTime: Long = occurTime
        internal set

    /**
     * 异常描述
     * varchar(200)
     */
    @Column(name = "`description`")
    var description: String? = description
        internal set

    /**
     * 扩展JSON，如关联登录日志ID列表等
     * text
     */
    @Column(name = "`extra`")
    var extra: String? = extra
        internal set

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动
}
