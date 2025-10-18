package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.Table

/**
 * Jimmer 用户信息实体
 * 用于 CQRS 读模型查询
 */
@Table(name = "customer_profile")
@Entity
interface JCustomerProfile {

    /**
     * ID
     */
    @Id
    val id: Long

    /**
     * 昵称
     */
    val nickName: String

    /**
     * 头像
     */
    val avatar: String?

    /**
     * 邮箱
     */
    val email: String

    /**
     * 性别
     */
    val sex: Byte

    /**
     * 逻辑删除标识
     */
    @LogicalDeleted("true")
    val deleted: Boolean
}
