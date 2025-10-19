package edu.only4.danmuku.application.queries._share.model.video

import edu.only4.danmuku.application.queries._share.model.customer_profile.JCustomerProfile
import org.babyfish.jimmer.sql.*

/**
 * Jimmer 视频实体
 * 用于 CQRS 读模型查询
 */
@Table(name = "video")
@Entity
interface JVideo {

    /**
     * ID
     */
    @Id
    val id: Long

    /**
     * 视频封面
     */
    val videoCover: String

    /**
     * 视频名称
     */
    val videoName: String

    /**
     * 父级分类ID
     */
    val pCategoryId: Long

    /**
     * 分类ID
     */
    val categoryId: Long?

    /**
     * 投稿类型
     */
    val postType: Byte

    /**
     * 原资源说明
     */
    val originInfo: String?

    /**
     * 标签
     */
    val tags: String?

    /**
     * 简介
     */
    val introduction: String?

    /**
     * 互动设置
     */
    val interaction: String?

    /**
     * 持续时间（秒）
     */
    val duration: Int?

    /**
     * 播放数量
     */
    val playCount: Int?

    /**
     * 点赞数量
     */
    val likeCount: Int?

    /**
     * 弹幕数量
     */
    val danmukuCount: Int?

    /**
     * 评论数量
     */
    val commentCount: Int?

    /**
     * 投币数量
     */
    val coinCount: Int?

    /**
     * 收藏数量
     */
    val collectCount: Int?

    /**
     * 推荐类型
     */
    val recommendType: Byte

    /**
     * 创建时间
     */
    val createTime: Long?

    /**
     * 更新时间
     */
    val updateTime: Long?

    /**
     * 关联的用户档案
     */
    @ManyToOne
    @JoinColumn(name = "customer_id")
    val customer: JCustomerProfile

    /**
     * 用户ID
     */
    @IdView("customer")
    val customerId: Long

    /**
     * 逻辑删除标识
     */
    @LogicalDeleted("true")
    val deleted: Boolean
}
