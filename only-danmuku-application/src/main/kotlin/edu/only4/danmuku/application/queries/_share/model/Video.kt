package edu.only4.danmuku.application.queries._share.model

import edu.only4.danmuku.application.queries._share.model.VideoPost
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table
import java.time.LocalDateTime

/**
 * 视频信息;@Spe;@Fac;
 */
@Entity
@Table(name = "video")
interface Video : BaseEntity {

    @OneToOne
    @JoinColumn(name = "video_draft_id")
    val videoPost: VideoPost

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

    /**
     * 播放数量
     */
    @Column(name = "play_count")
    val playCount: String?

    /**
     * 点赞数量
     */
    @Column(name = "like_count")
    val likeCount: String?

    /**
     * 弹幕数量
     */
    @Column(name = "danmuku_count")
    val danmukuCount: String?

    /**
     * 评论数量
     */
    @Column(name = "comment_count")
    val commentCount: String?

    /**
     * 投币数量
     */
    @Column(name = "coin_count")
    val coinCount: String?

    /**
     * 收藏数量
     */
    @Column(name = "collect_count")
    val collectCount: String?

    /**
     * 推荐状态 @E=0:UNKNOW:未知状态|1:NOT_RECOMMEND:未推荐|2:RECOMMEND:已推荐;@T=RecommendType
     */
    @Column(name = "recommend_type")
    val recommendType: String

    /**
     * 最后播放时间
     */
    @Column(name = "last_play_time")
    val lastPlayTime: LocalDateTime?
}
