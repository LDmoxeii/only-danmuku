package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.*

/**
 * Jimmer 视频弹幕实体
 * 用于 CQRS 读模型查询
 */
@Table(name = "video_danmuku")
@Entity
interface JVideoDanmuku {

    /**
     * ID
     */
    @Id
    val id: Long

    /**
     * 唯一ID
     */
    val fileId: Long

    /**
     * 发布时间
     */
    val postTime: Long?

    /**
     * 内容
     */
    val text: String?

    /**
     * 展示位置
     */
    val mode: Byte?

    /**
     * 颜色
     */
    val color: String?

    /**
     * 展示时间(秒)
     */
    val time: Int?

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

    /**
     * 关联视频信息（使用真实外键约束，关联非空）
     */
    @ManyToOne
    @JoinColumn(name = "video_id")
    val video: JVideo

    /**
     * 通过关联访问视频ID
     */
    @IdView("video")
    val videoId: Long

    /**
     * 关联用户信息（使用真实外键约束，关联非空）
     */
    @ManyToOne
    @JoinColumn(name = "customer_id")
    val customer: JCustomerProfile

    /**
     * 通过关联访问用户ID
     */
    @IdView("customer")
    val customerId: Long
}
