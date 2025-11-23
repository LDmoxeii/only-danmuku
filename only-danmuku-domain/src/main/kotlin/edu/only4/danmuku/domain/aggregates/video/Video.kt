package edu.only4.danmuku.domain.aggregates.video

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.DomainEventSupervisorSupport.events

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity
import edu.only4.danmuku.domain.aggregates.video.enums.PostType
import edu.only4.danmuku.domain.aggregates.video.enums.RecommendType
import edu.only4.danmuku.domain.aggregates.video.events.VideoBasicsSyncedDomainEvent
import edu.only4.danmuku.domain.aggregates.video.events.VideoCoinCountDeltaAppliedDomainEvent
import edu.only4.danmuku.domain.aggregates.video.events.VideoCollectCountDeltaAppliedDomainEvent
import edu.only4.danmuku.domain.aggregates.video.events.VideoCommentCountDeltaAppliedDomainEvent
import edu.only4.danmuku.domain.aggregates.video.events.VideoCreatedDomainEvent
import edu.only4.danmuku.domain.aggregates.video.events.VideoDanmukuCountDeltaAppliedDomainEvent
import edu.only4.danmuku.domain.aggregates.video.events.VideoDeletedDomainEvent
import edu.only4.danmuku.domain.aggregates.video.events.VideoInteractionChangedDomainEvent
import edu.only4.danmuku.domain.aggregates.video.events.VideoLastPlayTimeAttachedDomainEvent
import edu.only4.danmuku.domain.aggregates.video.events.VideoLikeCountDeltaAppliedDomainEvent
import edu.only4.danmuku.domain.aggregates.video.events.VideoPlayCountDeltaAppliedDomainEvent
import edu.only4.danmuku.domain.aggregates.video.events.VideoRecommendedDomainEvent
import edu.only4.danmuku.domain.aggregates.video.events.VideoStatisticsDeltaAppliedDomainEvent
import edu.only4.danmuku.domain.aggregates.video.events.VideoUnrecommendedDomainEvent
import edu.only4.danmuku.domain.aggregates.video_post.VideoPost

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
 * @date 2025/11/04
 */
@Aggregate(aggregate = "Video", name = "Video", root = true, type = Aggregate.TYPE_ENTITY, description = "视频信息，")
@Entity
@Table(name = "`video`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `video` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class Video(
    id: Long = 0L,
    videoPostId: Long = 0L,
    customerId: Long = 0L,
    videoCover: String = "",
    videoName: String = "",
    pCategoryId: Long = 0L,
    categoryId: Long? = null,
    postType: PostType = PostType.valueOf(0),
    originInfo: String? = null,
    tags: String? = null,
    introduction: String? = null,
    interaction: String? = null,
    duration: Int = 0,
    playCount: Int = 0,
    likeCount: Int = 0,
    danmukuCount: Int = 0,
    commentCount: Int = 0,
    coinCount: Int = 0,
    collectCount: Int = 0,
    recommendType: RecommendType = RecommendType.valueOf(0),
    lastPlayTime: Long? = null,
) : AuditedFieldsEntity() {
    // 【字段映射开始】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "`video_id`", nullable = false)
    var videoFiles: MutableList<VideoFile> = mutableListOf()

    /**
     * ID
     * bigint
     */
    @Id
    @GeneratedValue(generator = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator")
    @GenericGenerator(name = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator", strategy = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator")
    @Column(name = "`id`", insertable = false, updatable = false)
    var id: Long = id
        internal set

    /**
     * 视频草稿ID
     * bigint
     */
    @Column(name = "`video_post_id`")
    var videoPostId: Long = videoPostId
        internal set

    /**
     * 用户ID
     * bigint
     */
    @Column(name = "`customer_id`")
    var customerId: Long = customerId
        internal set

    /**
     * 视频封面
     * varchar(50)
     */
    @Column(name = "`video_cover`")
    var videoCover: String = videoCover
        internal set

    /**
     * 视频名称
     * varchar(100)
     */
    @Column(name = "`video_name`")
    var videoName: String = videoName
        internal set

    /**
     * 父级分类ID
     * bigint
     */
    @Column(name = "`p_category_id`")
    var pCategoryId: Long = pCategoryId
        internal set

    /**
     * 分类ID
     * bigint
     */
    @Column(name = "`category_id`")
    var categoryId: Long? = categoryId
        internal set

    /**
     * 投稿类型
     * 0:UNKNOW:未知类型
     * 1:ORIGINAL:自制作
     * 2:REPOST:转载
     * tinyint
     */
    @Convert(converter = PostType.Converter::class)
    @Column(name = "`post_type`")
    var postType: PostType = postType
        internal set

    /**
     * 原资源说明
     * varchar(200)
     */
    @Column(name = "`origin_info`")
    var originInfo: String? = originInfo
        internal set

    /**
     * 标签
     * varchar(300)
     */
    @Column(name = "`tags`")
    var tags: String? = tags
        internal set

    /**
     * 简介
     * varchar(2000)
     */
    @Column(name = "`introduction`")
    var introduction: String? = introduction
        internal set

    /**
     * 互动设置
     * varchar(5)
     */
    @Column(name = "`interaction`")
    var interaction: String? = interaction
        internal set

    /**
     * 持续时间（秒）
     * int
     */
    @Column(name = "`duration`")
    var duration: Int = duration
        internal set

    /**
     * 播放数量
     * int
     */
    @Column(name = "`play_count`")
    var playCount: Int = playCount
        internal set

    /**
     * 点赞数量
     * int
     */
    @Column(name = "`like_count`")
    var likeCount: Int = likeCount
        internal set

    /**
     * 弹幕数量
     * int
     */
    @Column(name = "`danmuku_count`")
    var danmukuCount: Int = danmukuCount
        internal set

    /**
     * 评论数量
     * int
     */
    @Column(name = "`comment_count`")
    var commentCount: Int = commentCount
        internal set

    /**
     * 投币数量
     * int
     */
    @Column(name = "`coin_count`")
    var coinCount: Int = coinCount
        internal set

    /**
     * 收藏数量
     * int
     */
    @Column(name = "`collect_count`")
    var collectCount: Int = collectCount
        internal set

    /**
     * 推荐状态
     * 0:UNKNOW:未知状态
     * 1:NOT_RECOMMEND:未推荐
     * 2:RECOMMEND:已推荐
     * tinyint(1)
     */
    @Convert(converter = RecommendType.Converter::class)
    @Column(name = "`recommend_type`")
    var recommendType: RecommendType = recommendType
        internal set

    /**
     * 最后播放时间
     * bigint
     */
    @Column(name = "`last_play_time`")
    var lastPlayTime: Long? = lastPlayTime
        internal set

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    fun onCreate() {
        events().attach(this) { VideoCreatedDomainEvent(entity = this) }
    }

    fun onDelete() {
        events().attach(this) { VideoDeletedDomainEvent(entity = this) }
    }

    /** 切换推荐状态 */
    fun toggleRecommend() {
        if (this.recommendType == RecommendType.RECOMMEND) {
            // 当前是推荐状态，切换为未推荐
            this.recommendType = RecommendType.NOT_RECOMMEND
            events().attach(this) { VideoUnrecommendedDomainEvent(entity = this) }
        } else {
            // 当前是未推荐状态，切换为推荐
            this.recommendType = RecommendType.RECOMMEND
            events().attach(this) { VideoRecommendedDomainEvent(entity = this) }
        }
    }

    /** 修改互动设置 */
    fun changeInteraction(newInteraction: String?) {
        this.interaction = newInteraction

        events().attach(this) { VideoInteractionChangedDomainEvent(this) }
    }

    private fun calculateDelta(current: Int, delta: Int): Pair<Int, Int> {
        if (delta == 0) return current to 0
        val updated = (current + delta).coerceAtLeast(0)
        return updated to (updated - current)
    }

    fun applyPlayCountDelta(delta: Int): Int {
        val (updated, appliedDelta) = calculateDelta(playCount, delta)
        if (appliedDelta == 0) return 0
        playCount = updated
        events().attach(this) { VideoPlayCountDeltaAppliedDomainEvent(this, appliedDelta) }
        return appliedDelta
    }

    fun applyLikeCountDelta(delta: Int): Int {
        val (updated, appliedDelta) = calculateDelta(likeCount, delta)
        if (appliedDelta == 0) return 0
        likeCount = updated
        events().attach(this) { VideoLikeCountDeltaAppliedDomainEvent(this, appliedDelta) }
        return appliedDelta
    }

    fun applyDanmukuCountDelta(delta: Int): Int {
        val (updated, appliedDelta) = calculateDelta(danmukuCount, delta)
        if (appliedDelta == 0) return 0
        danmukuCount = updated
        events().attach(this) { VideoDanmukuCountDeltaAppliedDomainEvent(this, appliedDelta) }
        return appliedDelta
    }

    fun applyCommentCountDelta(delta: Int): Int {
        val (updated, appliedDelta) = calculateDelta(commentCount, delta)
        if (appliedDelta == 0) return 0
        commentCount = updated
        events().attach(this) { VideoCommentCountDeltaAppliedDomainEvent(this, appliedDelta) }
        return appliedDelta
    }

    fun applyCoinCountDelta(delta: Int): Int {
        val (updated, appliedDelta) = calculateDelta(coinCount, delta)
        if (appliedDelta == 0) return 0
        coinCount = updated
        events().attach(this) { VideoCoinCountDeltaAppliedDomainEvent(this, appliedDelta) }
        return appliedDelta
    }

    fun applyCollectCountDelta(delta: Int): Int {
        val (updated, appliedDelta) = calculateDelta(collectCount, delta)
        if (appliedDelta == 0) return 0
        collectCount = updated
        events().attach(this) { VideoCollectCountDeltaAppliedDomainEvent(this, appliedDelta) }
        return appliedDelta
    }

    fun applyPlayStatisticsDelta(delta: Int): Int {
        return applyPlayCountDelta(delta)
    }

    fun applyStatisticsDelta(
        playCountDelta: Int = 0,
        likeCountDelta: Int = 0,
        danmukuCountDelta: Int = 0,
        commentCountDelta: Int = 0,
        coinCountDelta: Int = 0,
        collectCountDelta: Int = 0,
    ) {
        var changed = false
        changed = applyPlayCountDelta(playCountDelta) != 0 || changed
        changed = applyLikeCountDelta(likeCountDelta) != 0 || changed
        changed = applyDanmukuCountDelta(danmukuCountDelta) != 0 || changed
        changed = applyCommentCountDelta(commentCountDelta) != 0 || changed
        changed = applyCoinCountDelta(coinCountDelta) != 0 || changed
        changed = applyCollectCountDelta(collectCountDelta) != 0 || changed

        if (changed) {
            events().attach(this) { VideoStatisticsDeltaAppliedDomainEvent(entity = this) }
        }
    }

    fun attachLastPlayTime(toEpochSecond: Long) {
        this.lastPlayTime = toEpochSecond

        events().attach(this) { VideoLastPlayTimeAttachedDomainEvent(entity = this) }
    }

    /**
     * 使用基础数据同步到成品视频（无须暴露 VideoPost 聚合给外部）。
     */
    fun syncFromBasics(
        videoPostId: Long,
        customerId: Long,
        videoCover: String,
        videoName: String,
        parentCategoryId: Long,
        categoryId: Long?,
        postType: Int,
        originInfo: String?,
        tags: String?,
        introduction: String?,
        interaction: String?,
        duration: Int,
        files: List<SyncFileArgs>,
    ) {
        // 基础信息
        this.videoPostId = videoPostId
        this.customerId = customerId
        this.videoCover = videoCover
        this.videoName = videoName
        this.pCategoryId = parentCategoryId
        this.categoryId = categoryId
        this.postType = PostType.valueOf(postType)
        this.originInfo = originInfo
        this.tags = tags
        this.introduction = introduction
        this.interaction = interaction
        this.duration = duration

        // 分P重建
        this.videoFiles.clear()
        files.sortedBy { it.fileIndex }.forEach { f ->
            this.videoFiles.add(
                VideoFile(
                    customerId = f.customerId,
                    videoFilePostId = f.videoFilePostId,
                    fileName = f.fileName,
                    fileIndex = f.fileIndex,
                    fileSize = f.fileSize,
                    filePath = f.filePath,
                    duration = f.duration,
                )
            )
        }

        events().attach(this) { VideoBasicsSyncedDomainEvent(entity = this) }
    }

    // 【行为方法结束】

    data class SyncFileArgs(
        val videoFilePostId: Long,
        val customerId: Long,
        val fileName: String?,
        val fileIndex: Int,
        val fileSize: Long?,
        val filePath: String?,
        val duration: Int?,
    )
}
