package edu.only4.danmuku.domain.aggregates.video

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.DomainEventSupervisorSupport.events

import edu.only4.danmuku.domain.aggregates.video.enums.PostType
import edu.only4.danmuku.domain.aggregates.video.enums.RecommendType
import edu.only4.danmuku.domain.aggregates.video.events.VideoRecommendedDomainEvent

import jakarta.persistence.*

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 视频信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/10/25
 */
@Aggregate(aggregate = "Video", name = "Video", root = true, type = Aggregate.TYPE_ENTITY, description = "视频信息，")
@Entity
@Table(name = "`video`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `video` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class Video (
    // 【字段映射开始】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    /**
     * ID
     * bigint
     */
    @Id
    @GeneratedValue(generator = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator")
    @GenericGenerator(name = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator", strategy = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator")
    @Column(name = "`id`", insertable = false, updatable = false)
    var id: Long = 0L,

    /**
     * 用户ID
     * bigint
     */
    @Column(name = "`customer_id`")
    var customerId: Long = 0L,

    /**
     * 视频封面
     * varchar(50)
     */
    @Column(name = "`video_cover`")
    var videoCover: String = "",

    /**
     * 视频名称
     * varchar(100)
     */
    @Column(name = "`video_name`")
    var videoName: String = "",

    /**
     * 父级分类ID
     * bigint
     */
    @Column(name = "`p_category_id`")
    var pCategoryId: Long = 0L,

    /**
     * 分类ID
     * bigint
     */
    @Column(name = "`category_id`")
    var categoryId: Long? = null,

    /**
     * 投稿类型
     * 0:UNKNOW:未知类型
     * 1:ORIGINAL:自制作
     * 2:REPOST:转载
     * tinyint
     */
    @Convert(converter = PostType.Converter::class)
    @Column(name = "`post_type`")
    var postType: PostType = PostType.valueOf(0),

    /**
     * 原资源说明
     * varchar(200)
     */
    @Column(name = "`origin_info`")
    var originInfo: String? = null,

    /**
     * 标签
     * varchar(300)
     */
    @Column(name = "`tags`")
    var tags: String? = null,

    /**
     * 简介
     * varchar(2000)
     */
    @Column(name = "`introduction`")
    var introduction: String? = null,

    /**
     * 互动设置
     * varchar(5)
     */
    @Column(name = "`interaction`")
    var interaction: String? = null,

    /**
     * 持续时间（秒）
     * int
     */
    @Column(name = "`duration`")
    var duration: Int? = 0,

    /**
     * 播放数量
     * int
     */
    @Column(name = "`play_count`")
    var playCount: Int? = 0,

    /**
     * 点赞数量
     * int
     */
    @Column(name = "`like_count`")
    var likeCount: Int? = 0,

    /**
     * 弹幕数量
     * int
     */
    @Column(name = "`danmuku_count`")
    var danmukuCount: Int? = 0,

    /**
     * 评论数量
     * int
     */
    @Column(name = "`comment_count`")
    var commentCount: Int? = 0,

    /**
     * 投币数量
     * int
     */
    @Column(name = "`coin_count`")
    var coinCount: Int? = 0,

    /**
     * 收藏数量
     * int
     */
    @Column(name = "`collect_count`")
    var collectCount: Int? = 0,

    /**
     * 推荐状态
     * 0:UNKNOW:未知状态
     * 1:NOT_RECOMMEND:未推荐
     * 2:RECOMMEND:已推荐
     * tinyint(1)
     */
    @Convert(converter = RecommendType.Converter::class)
    @Column(name = "`recommend_type`")
    var recommendType: RecommendType = RecommendType.valueOf(0),

    /**
     * 最后播放时间
     * datetime
     */
    @Column(name = "`last_play_time`")
    var lastPlayTime: java.time.LocalDateTime? = null,

    /**
     * 创建人ID
     * bigint
     */
    @Column(name = "`create_user_id`")
    var createUserId: Long? = null,

    /**
     * 创建人名称
     * varchar(32)
     */
    @Column(name = "`create_by`")
    var createBy: String? = null,

    /**
     * 创建时间
     * bigint
     */
    @Column(name = "`create_time`")
    var createTime: Long? = null,

    /**
     * 更新人ID
     * bigint
     */
    @Column(name = "`update_user_id`")
    var updateUserId: Long? = null,

    /**
     * 更新人名称
     * varchar(32)
     */
    @Column(name = "`update_by`")
    var updateBy: String? = null,

    /**
     * 更新时间
     * bigint
     */
    @Column(name = "`update_time`")
    var updateTime: Long? = null,

    /**
     * 删除标识 0：未删除 id：已删除
     * bigint
     */
    @Column(name = "`deleted`")
    var deleted: Long = 0L,
) {
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "`video_id`", nullable = false)
    var videoFiles: MutableList<VideoFile> = mutableListOf()

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】

    /** 标记为推荐视频 */
    fun recommend() {
        if (this.recommendType == RecommendType.RECOMMEND) return
        this.recommendType = RecommendType.RECOMMEND
        events().attach(this) { VideoRecommendedDomainEvent(entity = this) }
    }

    /** 取消推荐 */
    fun unrecommend() {
        if (this.recommendType == RecommendType.NOT_RECOMMEND) return
        this.recommendType = RecommendType.NOT_RECOMMEND
    }

    /** 修改互动设置 */
    fun changeInteraction(newInteraction: String?) {
        this.interaction = newInteraction
    }

    fun applyStatisticsDelta(
        playCountDelta: Int? = null,
        likeCountDelta: Int? = null,
        danmukuCountDelta: Int? = null,
        commentCountDelta: Int? = null,
        coinCountDelta: Int? = null,
        collectCountDelta: Int? = null,
    ) {
        fun Int?.applyDelta(delta: Int?): Int? {
            if (delta == null || delta == 0) return this
            val updated = (this ?: 0) + delta
            return maxOf(updated, 0)
        }

        playCount = playCount.applyDelta(playCountDelta)
        likeCount = likeCount.applyDelta(likeCountDelta)
        danmukuCount = danmukuCount.applyDelta(danmukuCountDelta)
        commentCount = commentCount.applyDelta(commentCountDelta)
        coinCount = coinCount.applyDelta(coinCountDelta)
        collectCount = collectCount.applyDelta(collectCountDelta)
    }

    // 【行为方法结束】
}
