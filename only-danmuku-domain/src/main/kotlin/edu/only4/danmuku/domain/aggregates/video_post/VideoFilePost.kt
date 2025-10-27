package edu.only4.danmuku.domain.aggregates.video_post

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.DomainEventSupervisorSupport.events

import edu.only4.danmuku.domain.aggregates.video_post.enums.TransferResult
import edu.only4.danmuku.domain.aggregates.video_post.enums.UpdateType
import edu.only4.danmuku.domain.aggregates.video_post.events.VideoFileDraftCreatedDomainEvent
import edu.only4.danmuku.domain.aggregates.video_post.events.VideoFileDraftTranscodedDomainEvent

import jakarta.persistence.*

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 视频文件信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/10/26
 */
@Aggregate(
    aggregate = "VideoPost",
    name = "VideoFilePost",
    root = false,
    type = Aggregate.TYPE_ENTITY,
    description = "视频文件信息，"
)
@Entity
@Table(name = "`video_file_post`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `video_file_post` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class VideoFilePost(
    // 【字段映射开始】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    /**
     * ID
     * bigint
     */
    @Id
    @GeneratedValue(generator = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator")
    @GenericGenerator(
        name = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator",
        strategy = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator"
    )
    @Column(name = "`id`", insertable = false, updatable = false)
    var id: Long = 0L,

    /**
     * 上传ID
     * bigint
     */
    @Column(name = "`upload_id`")
    var uploadId: Long = 0L,

    /**
     * 用户ID
     * bigint
     */
    @Column(name = "`customer_id`")
    var customerId: Long = 0L,

    /**
     * 文件索引
     * int
     */
    @Column(name = "`file_index`")
    var fileIndex: Int = 0,

    /**
     * 文件名
     * varchar(200)
     */
    @Column(name = "`file_name`")
    var fileName: String? = null,

    /**
     * 文件大小
     * bigint
     */
    @Column(name = "`file_size`")
    var fileSize: Long? = null,

    /**
     * 文件路径
     * varchar(100)
     */
    @Column(name = "`file_path`")
    var filePath: String? = null,

    /**
     * 更新类型
     * 0:UNKNOW:未知类型
     * 1:NO_UPDATE:无更新
     * 2:HAS_UPDATE:有更新
     * tinyint
     */
    @Convert(converter = UpdateType.Converter::class)
    @Column(name = "`update_type`")
    var updateType: UpdateType = UpdateType.valueOf(0),

    /**
     * 转码结果
     * 0:UNKNOW:未知结果
     * 1:TRANSCODING:转码中
     * 2:SUCCESS:转码成功
     * 3:FAILED:转码失败
     * tinyint
     */
    @Convert(converter = TransferResult.Converter::class)
    @Column(name = "`transfer_result`")
    var transferResult: TransferResult = TransferResult.valueOf(0),

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
    @ManyToOne(cascade = [], fetch = FetchType.EAGER)
    @JoinColumn(name = "`video_id`", nullable = false, insertable = false, updatable = false)
    var videoPost: VideoPost? = null

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】

    fun onCreate() {
        events().attach(this) { VideoFileDraftCreatedDomainEvent(this) }
    }

    fun updateType(typpe: UpdateType) {
        this.updateType = typpe
    }

    /**
     * 标记转码开始
     */
    fun startTransfer() {
        this.transferResult = TransferResult.TRANSCODING
        this.updateType = UpdateType.HAS_UPDATE
    }

    /**
     * 标记转码成功并更新文件信息
     *
     * @param duration 视频时长(秒)
     * @param fileSize 文件大小(字节)
     * @param filePath 文件路径
     */
    fun markTransferSuccess(duration: Int, fileSize: Long, filePath: String) {
        this.transferResult = TransferResult.SUCCESS
        this.duration = duration
        this.fileSize = fileSize
        this.filePath = filePath
        this.updateType = UpdateType.NO_UPDATE
        events().attach(this) {
            VideoFileDraftTranscodedDomainEvent(entity = this, success = true)
        }
    }

    /**
     * 标记转码失败
     */
    fun markTransferFailed(errorMessage: String? = null) {
        this.transferResult = TransferResult.FAILED
        events().attach(this) {
            VideoFileDraftTranscodedDomainEvent(entity = this, success = false, errorMessage = errorMessage)
        }
    }

    /**
     * 更新文件基本信息
     *
     * @param fileName 文件名
     * @param fileSize 文件大小
     */
    fun updateFileInfo(fileName: String?, fileSize: Long?) {
        fileName?.let { this.fileName = it }
        fileSize?.let { this.fileSize = it }
    }

    /**
     * 检查是否正在转码中
     */
    fun isTranscoding(): Boolean {
        return this.transferResult == TransferResult.TRANSCODING
    }

    /**
     * 检查转码是否成功
     */
    fun isTransferSuccess(): Boolean {
        return this.transferResult == TransferResult.SUCCESS
    }

    /**
     * 检查转码是否失败
     */
    fun isTransferFailed(): Boolean {
        return this.transferResult == TransferResult.FAILED
    }

    data class UploadSpec(
        val uploadId: Long,
        val fileIndex: Int,
        val fileName: String,
        val fileSize: Long,
        val duration: Int,
    )

    data class BuildResult(
        val fileDrafts: List<VideoFilePost>,
        val totalDuration: Int,
    )

    companion object {
        fun buildFromUploads(
            customerId: Long,
            videoPost: VideoPost,
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
                    throw IllegalArgumentException("Duplicate uploadId: ${upload.uploadId}")
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
                ).also {
                    it.videoPost = videoPost
                    it.onCreate()
                }
            }
            return BuildResult(fileDrafts = fileDrafts, totalDuration = totalDuration)
        }
    }

    // 【行为方法结束】
}
