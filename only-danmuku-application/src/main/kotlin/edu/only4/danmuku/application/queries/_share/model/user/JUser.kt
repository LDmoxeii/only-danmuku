package edu.only4.danmuku.application.queries._share.model.user

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.Table

/**
 * Jimmer 用户账号实体
 * 用于 CQRS 读模型查询
 */
@Table(name = "user")
@Entity
interface JUser {

    /**
     * ID
     */
    @Id
    val id: Long

    /**
     * 账号类型 (0:未知 1:系统管理员)
     */
    val type: Byte

    /**
     * 邮箱
     */
    val email: String

    /**
     * 密码
     */
    val password: String

    /**
     * 加入时间
     */
    val joinTime: Long

    /**
     * 最后登录时间
     */
    val lastLoginTime: Long?

    /**
     * 最后登录IP
     */
    val lastLoginIp: String?

    /**
     * 状态 (0:禁用 1:正常)
     */
    val status: Byte

    /**
     * 关联ID
     */
    val relatedId: Int?

    /**
     * 创建人ID
     */
    val createUserId: Long?

    /**
     * 创建人名称
     */
    val createBy: String?

    /**
     * 创建时间
     */
    val createTime: Long?

    /**
     * 更新人ID
     */
    val updateUserId: Long?

    /**
     * 更新人名称
     */
    val updateBy: String?

    /**
     * 更新时间
     */
    val updateTime: Long?

    /**
     * 逻辑删除标识
     */
    @LogicalDeleted("true")
    val deleted: Boolean
}
