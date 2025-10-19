package edu.only4.danmuku.application.queries._share.model.customer_focus

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.Table

/**
 * Jimmer 用户关注实体
 * 用于 CQRS 读模型查询
 */
@Table(name = "customer_focus")
@Entity
interface JCustomerFocus {

    /**
     * ID
     */
    @Id
    val id: Long

    /**
     * 用户ID
     */
    val customerId: Long

    /**
     * 关注的用户ID
     */
    val focusCustomerId: Long

    /**
     * 创建时间
     */
    val createTime: Long?

    /**
     * 逻辑删除标识
     */
    @LogicalDeleted("true")
    val deleted: Boolean
}
