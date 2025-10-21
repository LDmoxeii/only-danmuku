package edu.only4.danmuku.application.queries._share.model.video_draft

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.Table

/**
 * Jimmer 视频草稿实体
 * 用于 CQRS 读模型查询
 */
@Table(name = "video_draft")
@Entity
interface JVideoDraft {

    /**
     * ID
     */
    @Id
    val id: Long

    /**
     * 视频ID
     */
    val videoId: Long

    /**
     * 视频封面
     */
    val videoCover: String

    /**
     * 视频名称
     */
    val videoName: String

    /**
     * 用户ID
     */
    val customerId: Long

    /**
     * 父级分类ID
     */
    val pCategoryId: Long

    /**
     * 分类ID
     */
    val categoryId: Long?

    /**
     * 视频状态
     * 0:UNKNOW:未知状态
     * 1:TRANSCODING:转码中
     * 2:TRANSCODE_FAILED:转码失败
     * 3:PENDING_REVIEW:待审核
     * 4:REVIEW_PASSED:审核成功
     * 5:REVIEW_FAILED:审核失败
     */
    val status: Byte

    /**
     * 投稿类型
     * 0:UNKNOW:未知类型
     * 1:ORIGINAL:自制作
     * 2:REPOST:转载
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
     * 创建时间
     */
    val createTime: Long?

    /**
     * 更新时间
     */
    val updateTime: Long?

    /**
     * 逻辑删除标识（0：未删除，非0：已删除）
     */
    @LogicalDeleted
    val deleted: Long
}
