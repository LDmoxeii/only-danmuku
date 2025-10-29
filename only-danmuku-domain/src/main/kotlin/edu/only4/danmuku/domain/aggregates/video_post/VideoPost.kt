package edu.only4.danmuku.domain.aggregates.video_post

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.DomainEventSupervisorSupport.events
import edu.only4.danmuku.domain.aggregates.video.enums.PostType
import edu.only4.danmuku.domain.aggregates.video_post.enums.TransferResult
import edu.only4.danmuku.domain.aggregates.video_post.enums.UpdateType
import edu.only4.danmuku.domain.aggregates.video_post.enums.VideoStatus
import edu.only4.danmuku.domain.aggregates.video_post.events.VideoAuditFailedDomainEvent
import edu.only4.danmuku.domain.aggregates.video_post.events.VideoAuditPassedDomainEvent
import jakarta.persistence.*
import jakarta.persistence.CascadeType
import jakarta.persistence.Table
import org.hibernate.annotations.*

/**
 * 视频信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/10/26
 */
@Aggregate(
    aggregate = "VideoPost",
    name = "VideoPost",
    root = true,
    type = Aggregate.TYPE_ENTITY,
    description = "视频信息，"
)
@Entity
@Table(name = "`video_post`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `video_post` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class VideoPost(
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
     * 用户ID
     * bigint
     */
    @Column(name = "`customer_id`")
    var customerId: Long = 0L,

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
    var status: VideoStatus = VideoStatus.valueOf(0),

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
    var duration: Int? = null,

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
    var videoFilePosts: MutableList<VideoFilePost> = mutableListOf()

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】

    /** 审核通过 */
    fun reviewPass() {
        if (this.status == VideoStatus.REVIEW_PASSED) return
        this.status = VideoStatus.REVIEW_PASSED
        this.videoFilePosts.forEach{ it.updateType(UpdateType.NO_UPDATE)}
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
     * 检查是否正在转码中
     */
    fun isTranscoding(): Boolean {
        return this.status == VideoStatus.TRANSCODING
    }

    /**
     * 检查转码是否失败
     */
    fun isTranscodeFailed(): Boolean {
        return this.status == VideoStatus.TRANSCODE_FAILED
    }

    /**
     * 检查是否待审核
     */
    fun isPendingReview(): Boolean {
        return this.status == VideoStatus.PENDING_REVIEW
    }

    /**
     * 检查是否审核通过
     */
    fun isReviewPassed(): Boolean {
        return this.status == VideoStatus.REVIEW_PASSED
    }

    // 【行为方法结束】

    // 【语义化编辑方法开始】

    data class FileEditSpec(
        val fileId: Long? = null,
        val uploadId: Long? = null,
        val fileIndex: Int,
        val fileName: String,
        val fileSize: Long? = null,
        val duration: Int? = null,
    )

    data class FileEditOutcome(
        val hasNewFiles: Boolean,
        val hasRemovedFiles: Boolean,
        val hasFileMetaChange: Boolean,
        val totalDuration: Int,
    )

    /**
     * 初次构建所需的上传规格（来自上传中心的文件占位信息）
     */
    data class UploadSpec(
        val uploadId: Long,
        val fileIndex: Int,
        val fileName: String,
        val fileSize: Long = 0,
        val duration: Int = 0,
    )

    data class BuildResult(
        val fileDrafts: List<VideoFilePost>,
        val totalDuration: Int,
    )

    companion object {
        /**
         * 根据上传记录构建草稿文件列表，并计算总时长
         */
        fun buildFromUploads(
            customerId: Long,
            uploads: List<UploadSpec>,
        ): BuildResult {
            if (uploads.isEmpty()) {
                return BuildResult(emptyList(), 0)
            }
            val seenUploadIds = mutableSetOf<Long>()
            var totalDuration = 0
            val sorted = uploads.sortedBy { it.fileIndex }
            val fileDrafts = sorted.mapIndexed { index, upload ->
                if (!seenUploadIds.add(upload.uploadId)) {
                    throw IllegalArgumentException("Duplicate uploadId: ${'$'}{upload.uploadId}")
                }
                totalDuration += upload.duration
                VideoFilePost(
                    uploadId = upload.uploadId,
                    customerId = customerId,
                    fileIndex = index + 1,
                    fileName = upload.fileName,
                    fileSize = upload.fileSize,
                    updateType = UpdateType.HAS_UPDATE,
                    transferResult = TransferResult.TRANSCODING,
                    duration = upload.duration
                )
            }
            return BuildResult(fileDrafts = fileDrafts, totalDuration = totalDuration)
        }
    }

    /**
     * 初始化草稿的文件列表（用于创建阶段），并同步视频总时长。
     * - 使用上传记录生成占位文件草稿
     * - 重建 `videoFilePosts` 列表
     * - 汇总并设置 `duration`（>0 生效，否则为 null）
     */
    fun initializeFilesFromUploads(customerId: Long, uploads: List<UploadSpec>) {
        val result = buildFromUploads(customerId, uploads)
        this.videoFilePosts.clear()
        this.videoFilePosts.addAll(result.fileDrafts)
        this.duration = result.totalDuration.takeIf { it > 0 }
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

    /**
     * 应用文件变更列表（按 fileId 或 uploadId 匹配）
     * - 支持新增（仅 uploadId）/保留（fileId 或 uploadId）/删除（未出现在列表中的既有）
     * - 重排 fileIndex
     * - 可选更新 fileName/fileSize/duration
     * - 同步汇总总时长并写入 VideoPost.duration
     */
    fun applyFileEdits(customerId: Long, edits: List<FileEditSpec>): FileEditOutcome {
        var hasNewFiles = false
        var hasRemovedFiles = false
        var hasFileMetaChange = false

        val byId = this.videoFilePosts.associateBy { it.id }.toMutableMap()
        val byUploadId = this.videoFilePosts.associateBy { it.uploadId }.toMutableMap()
        val seenIds = mutableSetOf<Long>()
        val seenUploadIds = mutableSetOf<Long>()

        val rebuilt = mutableListOf<VideoFilePost>()
        edits.forEachIndexed { index, spec ->
            val normalizedIndex = index + 1
            val existing = when {
                spec.fileId != null -> {
                    val id = spec.fileId
                    if (!seenIds.add(id)) throw IllegalArgumentException("重复的 fileId: $id")
                    byId.remove(id)
                }

                spec.uploadId != null -> {
                    val uid = spec.uploadId
                    if (!seenUploadIds.add(uid)) throw IllegalArgumentException("重复的 uploadId: $uid")
                    byUploadId.remove(uid)
                }

                else -> throw IllegalArgumentException("缺少 uploadId 或 fileId")
            }

            if (existing != null) {
                if (existing.fileIndex != normalizedIndex) {
                    existing.fileIndex = normalizedIndex
                    hasFileMetaChange = true
                }
                if (spec.fileName != existing.fileName) {
                    existing.fileName = spec.fileName
                    hasFileMetaChange = true
                }
                spec.fileSize?.let { sz ->
                    if (existing.fileSize != sz) {
                        existing.fileSize = sz
                        hasFileMetaChange = true
                    }
                }
                spec.duration?.let { du ->
                    val current = existing.duration ?: 0
                    if (du != current) {
                        existing.duration = du
                        hasFileMetaChange = true
                    }
                }
                rebuilt.add(existing)
            } else {
                // 新增：必须通过 uploadId 创建占位草稿文件
                val uid = spec.uploadId ?: throw IllegalArgumentException("新增文件缺少 uploadId")
                val newFile = VideoFilePost(
                    uploadId = uid,
                    customerId = customerId,
                    fileIndex = normalizedIndex,
                    fileName = spec.fileName,
                    fileSize = spec.fileSize,
                    updateType = UpdateType.HAS_UPDATE,
                    transferResult = TransferResult.TRANSCODING,
                    duration = spec.duration
                )
                rebuilt.add(newFile)
                hasNewFiles = true
            }
        }

        if (byId.isNotEmpty() || byUploadId.isNotEmpty()) {
            hasRemovedFiles = true
        }

        this.videoFilePosts.clear()
        this.videoFilePosts.addAll(rebuilt)

        val totalDuration = edits.sumOf { it.duration ?: 0 }
        val normalized = totalDuration.takeIf { it > 0 }
        if (this.duration != normalized) {
            this.duration = normalized
            hasFileMetaChange = true
        }

        return FileEditOutcome(
            hasNewFiles = hasNewFiles,
            hasRemovedFiles = hasRemovedFiles,
            hasFileMetaChange = hasFileMetaChange,
            totalDuration = totalDuration
        )
    }

    /**
     * 编辑后根据变更结果调整状态
     */
    fun adjustStatusAfterEdit(basicChanged: Boolean, outcome: FileEditOutcome?) {
        val hasNew = outcome?.hasNewFiles == true
        val hasMeta = outcome?.hasFileMetaChange == true
        val hasRemoved = outcome?.hasRemovedFiles == true
        when {
            hasNew -> this.markTranscoding()
            (basicChanged || hasMeta || hasRemoved) -> this.markPendingReview()
        }
    }

    // 【语义化编辑方法结束】
}
