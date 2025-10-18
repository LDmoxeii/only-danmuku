package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.*

/**
 * Jimmer 视频评论实体
 * 用于 CQRS 读模型查询
 */
@Table(name = "video_comment")
@Entity
interface JVideoComment {

    /**
     * 评论ID
     */
    @Id
    val id: Long

    /**
     * 父级评论ID
     */
    val parentId: Long

    /**
     * 视频所有者ID
     */
    val videoOwnerId: Long

    /**
     * 回复内容
     */
    val content: String?

    /**
     * 图片路径
     */
    val imgPath: String?

    /**
     * 置顶类型 (0:未置顶 1:置顶)
     */
    val topType: Byte?

    /**
     * 发布时间
     */
    val postTime: Long

    /**
     * 喜欢数量
     */
    val likeCount: Int?

    /**
     * 讨厌数量
     */
    val hateCount: Int?

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
     * 关联评论用户信息（使用真实外键约束，关联非空）
     */
    @ManyToOne
    @JoinColumn(name = "customer_id")
    val customer: JCustomerProfile

    /**
     * 通过关联访问用户ID
     */
    @IdView("customer")
    val customerId: Long

    /**
     * 关联回复用户信息（可选，使用真实外键约束）
     */
    @ManyToOne
    @JoinColumn(name = "reply_customer_id")
    val replyCustomer: JCustomerProfile?

    /**
     * 通过关联访问回复用户ID
     */
    @IdView("replyCustomer")
    val replyCustomerIdView: Long?
}
