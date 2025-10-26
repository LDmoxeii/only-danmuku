package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

/**
 * 视频信息;@Spe;@Fac;
 */
@Entity
@Table(name = "video_post")
interface VideoPost : BaseEntity {

    @OneToOne
    @JoinColumn(name = "customer_id")
    val customer: User

    /**
     * 视频封面
     */
    @Column(name = "video_cover")
    val videoCover: String

    /**
     * 视频名称
     */
    @Column(name = "video_name")
    val videoName: String

    /**
     * 视频状态 @E=0:UNKNOW:未知状态|1:TRANSCODING:转码中|2:TRANSCODE_FAILED:转码失败|3:PENDING_REVIEW:待审核|4:REVIEW_PASSED:审核成功|5:REVIEW_FAILED:审核失败;@T=VideoStatus
     */
    @Column(name = "status")
    val status: String

    /**
     * 投稿类型 @E=0:UNKNOW:未知类型|1:ORIGINAL:自制作|2:REPOST:转载;@T=PostType
     */
    @Column(name = "post_type")
    val postType: Int

    /**
     * 原资源说明
     */
    @Column(name = "origin_info")
    val originInfo: String?

    /**
     * 标签
     */
    @Column(name = "tags")
    val tags: String?

    /**
     * 简介
     */
    @Column(name = "introduction")
    val introduction: String?

    /**
     * 互动设置
     */
    @Column(name = "interaction")
    val interaction: String?

    /**
     * 持续时间（秒）
     */
    @Column(name = "duration")
    val duration: String?
}
