package edu.only4.danmuku.application.queries._share.model

import edu.only4.danmuku.domain.aggregates.video.enums.RecommendType
import edu.only4.danmuku.domain.aggregates.video_post.enums.PostType
import org.babyfish.jimmer.sql.*

/**
 * 视频信息;@Spe;@Fac;
 */
@Entity
@Table(name = "video")
interface Video : BaseEntity {

    @IdView
    val customerId: Long

    @IdView
    val parentCategoryId: Long

    @IdView
    val categoryId: Long?

    @OneToOne
    @JoinColumn(name = "video_post_id")
    val videoPost: VideoPost

    @OneToOne
    @JoinColumn(name = "customer_id")
    val customer: User

    @OneToOne
    @JoinColumn(name = "p_category_id")
    val parentCategory: Category

    @OneToOne
    @JoinColumn(name = "category_id")
    val category: Category?

    @Column(name = "video_cover")
    val videoCover: String

    @Column(name = "video_name")
    val videoName: String

    /**
     * 投稿类型 @E=0:UNKNOW:未知类型|1:ORIGINAL:自制作|2:REPOST:转载;@T=PostType
     */
    @Column(name = "post_type")
    val postType: PostType

    @Column(name = "origin_info")
    val originInfo: String?

    @Column(name = "tags")
    val tags: String?

    @Column(name = "introduction")
    val introduction: String?

    @Column(name = "interaction")
    val interaction: String?

    @Column(name = "duration")
    val duration: Int

    @Column(name = "play_count")
    val playCount: Int

    @Column(name = "like_count")
    val likeCount: Int

    @Column(name = "danmuku_count")
    val danmukuCount: Int

    @Column(name = "comment_count")
    val commentCount: Int

    @Column(name = "coin_count")
    val coinCount: Int

    @Column(name = "collect_count")
    val collectCount: Int

    /**
     * 推荐状态 @E=0:UNKNOW:未知状态|1:NOT_RECOMMEND:未推荐|2:RECOMMEND:已推荐;@T=RecommendType
     */
    @Column(name = "recommend_type")
    val recommendType: RecommendType

    @Column(name = "last_play_time")
    val lastPlayTime: Long?
}
