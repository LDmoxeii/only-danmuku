package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

/**
 * Jimmer 视频评论实体
 * 用于 CQRS 读模型查询
 */
@Table(name = "video_comment")
@Entity
interface VideoComment : BaseEntity {

    @OneToOne
    @JoinColumn(name = "video_owner_id")
    val videoOwner: User

    /**
     * 关联视频信息（使用真实外键约束，关联非空）
     */
    @OneToOne
    @JoinColumn(name = "video_id")
    val video: Video

    /**
     * 关联评论用户信息（使用真实外键约束，关联非空）
     */
    @OneToOne
    @JoinColumn(name = "customer_id")
    val customer: User

    /**
     * 关联回复用户信息（可选，使用真实外键约束）
     */
    @OneToOne
    @JoinColumn(name = "reply_customer_id")
    val replyCustomer: User?

    /**
     * 父级评论ID
     */
    val parentId: Long

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
}
