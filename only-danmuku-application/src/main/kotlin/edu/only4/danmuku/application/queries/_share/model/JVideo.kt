package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.Table

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
     * 用户ID
     */
    val customerId: Long

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
     * 逻辑删除标识
     */
    @LogicalDeleted("true")
    val deleted: Boolean
}
