package edu.only4.danmuku.domain.aggregates.video_post

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.DomainEventSupervisorSupport.events

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity
import edu.only4.danmuku.domain.aggregates.video_post.enums.PostType
import edu.only4.danmuku.domain.aggregates.video_post.enums.VideoStatus
import edu.only4.danmuku.domain.aggregates.video_post.events.*

import jakarta.persistence.*
import jakarta.persistence.Table

import org.hibernate.annotations.*

/**
 * 视频信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
@Aggregate(aggregate = "VideoPost", name = "VideoPost", root = true, type = Aggregate.TYPE_ENTITY, description = "视频信息，")
@Entity
@Table(name = "`video_post`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `video_post` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class VideoPost(
    id: Long = 0L,
    videoCover: String = "",
    videoName: String = "",
    customerId: Long = 0L,
    pCategoryId: Long = 0L,
    categoryId: Long? = null,
    status: VideoStatus = VideoStatus.valueOf(0),
    postType: PostType = PostType.valueOf(0),
    originInfo: String? = null,
    tags: String? = null,
    introduction: String? = null,
    interaction: String? = null,
    duration: Int = 0,
) : AuditedFieldsEntity() {
    // 【字段映射开始】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "`video_post_id`", nullable = false)
    var videoFilePosts: MutableList<VideoFilePost> = mutableListOf()

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
     * 用户ID
     * bigint
     */
    @Column(name = "`customer_id`")
    var customerId: Long = customerId
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
     * 视频状态
     * 0:UNKNOW:未知状态
     * 1:TRANSCODING:转码中
     * 2:TRANSCODE_FAILED:转码失败
     * 3:PENDING_REVIEW:待审核
     * 4:REVIEW_PASSED:审核成功
     * 5:REVIEW_FAILED:审核失败
     * tinyint(1)
     */
    @Convert(converter = VideoStatus.Converter::class)
    @Column(name = "`status`")
    var status: VideoStatus = status
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

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】

    fun onCreate() {
        events().attach(this) { VideoDraftCreatedDomainEvent(this) }
    }

    fun onDelete() {
        events().attach(this) { VideoPostDeletedDomainEvent(this) }
    }

    fun onUpdate() {
    }

    /** 审核通过 */
    fun reviewPass() {
        if (this.status == VideoStatus.REVIEW_PASSED) return
        this.status = VideoStatus.REVIEW_PASSED
        events().attach(this) { VideoAuditPassedDomainEvent(entity = this) }
    }

    /** 审核失败 */
    fun reviewFail() {
        if (this.status == VideoStatus.REVIEW_FAILED) return
        this.status = VideoStatus.REVIEW_FAILED
        events().attach(this) { VideoAuditFailedDomainEvent(entity = this) }
    }

    /** 标记为待审核 */
    fun markPendingReview() {
        this.status = VideoStatus.PENDING_REVIEW
    }

    /** 标记为转码中 */
    fun markTranscoding() {
        this.status = VideoStatus.TRANSCODING
    }

    /** 标记为转码失败 */
    fun markTranscodeFailed() {
        this.status = VideoStatus.TRANSCODE_FAILED
    }

    /**
     * 更新视频总时长
     *
     * @param duration 总时长(秒)
     */
    fun updateDuration(duration: Int) {
        this.duration = duration
    }

    /**
     * 应用基础信息变更（仅对非空字段生效）
     * @return 是否发生了基础信息变化
     */
    fun applyBasicInfo(
        videoName: String? = null,
        videoCover: String? = null,
        pCategoryId: Long? = null,
        categoryId: Long? = null,
        postType: PostType? = null,
        originInfo: String? = null,
        tags: String? = null,
        introduction: String? = null,
        interaction: String? = null,
    ): Boolean {
        var changed = false
        videoName?.let {
            if (this.videoName != it) {
                this.videoName = it; changed = true
            }
        }
        videoCover?.let {
            if (this.videoCover != it) {
                this.videoCover = it; changed = true
            }
        }
        pCategoryId?.let {
            if (this.pCategoryId != it) {
                this.pCategoryId = it; changed = true
            }
        }
        categoryId?.let {
            if (this.categoryId != it) {
                this.categoryId = it; changed = true
            }
        }
        postType?.let {
            if (this.postType != it) {
                this.postType = it; changed = true
            }
        }
        originInfo?.let {
            if (this.originInfo != it) {
                this.originInfo = it; changed = true
            }
        }
        tags?.let {
            if (this.tags != it) {
                this.tags = it; changed = true
            }
        }
        introduction?.let {
            if (this.introduction != it) {
                this.introduction = it; changed = true
            }
        }
        interaction?.let {
            if (this.interaction != it) {
                this.interaction = it; changed = true
            }
        }
        return changed
    }

    fun changeInteraction(interaction: String?) {
        if (this.interaction == interaction) return
        this.interaction = interaction
        events().attach(this) { VideoPostInteractionChangedDomainEvent(this) }
    }

    // 【行为方法结束】

    // 【语义化编辑方法开始】

    /**
     * 上传占位信息（与 video_file_post 解耦后仅用于初始化总时长）
     */
    data class UploadSpec(
        val uploadId: Long,
        val fileIndex: Int = 0,
        val fileName: String = "",
        val fileSize: Long = 0,
        val duration: Int = 0,
    )
}
