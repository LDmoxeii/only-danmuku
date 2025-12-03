package edu.only4.danmuku.domain.aggregates.video_file_post

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.DomainEventSupervisorSupport.events

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity
import edu.only4.danmuku.domain.aggregates.video_file_post.enums.EncryptMethod
import edu.only4.danmuku.domain.aggregates.video_file_post.enums.EncryptStatus
import edu.only4.danmuku.domain.aggregates.video_file_post.enums.TransferResult
import edu.only4.danmuku.domain.aggregates.video_file_post.enums.UpdateType
import edu.only4.danmuku.domain.aggregates.video_file_post.events.VideoFilePostCreatedDomainEvent
import edu.only4.danmuku.domain.aggregates.video_file_post.events.VideoFilePostDeletedDomainEvent
import edu.only4.danmuku.domain.aggregates.video_file_post.events.VideoFilePostTranscodeResultUpdatedDomainEvent

import jakarta.persistence.*

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 视频文件信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/11/26
 */
@Aggregate(aggregate = "VideoFilePost", name = "VideoFilePost", root = true, type = Aggregate.TYPE_ENTITY, description = "视频文件信息，")
@Entity
@Table(name = "`video_file_post`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `video_file_post` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class VideoFilePost(
    id: Long = 0L,
    uploadId: Long = 0L,
    customerId: Long = 0L,
    videoId: Long = 0L,
    fileIndex: Int = 0,
    fileName: String? = null,
    fileSize: Long? = null,
    filePath: String? = null,
    updateType: UpdateType = UpdateType.valueOf(0),
    transferResult: TransferResult = TransferResult.valueOf(0),
    abrSourceWidth: Int? = null,
    abrSourceHeight: Int? = null,
    abrSourceBitrateKbps: Int? = null,
    encryptStatus: EncryptStatus = EncryptStatus.valueOf(1),
    encryptMethod: EncryptMethod = EncryptMethod.valueOf(1),
    encryptKeyId: Long? = null,
    encryptFailReason: String? = null,
    duration: Int? = null,
) : AuditedFieldsEntity() {
    // 【字段映射开始】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "`file_id`", nullable = false)
    var videoHlsAbrVariants: MutableList<VideoHlsAbrVariant> = mutableListOf()

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
     * 上传ID
     * bigint
     */
    @Column(name = "`upload_id`")
    var uploadId: Long = uploadId
        internal set

    /**
     * 用户ID
     * bigint
     */
    @Column(name = "`customer_id`")
    var customerId: Long = customerId
        internal set

    /**
     * 视频ID
     * bigint
     */
    @Column(name = "`video_id`")
    var videoId: Long = videoId
        internal set

    /**
     * 文件索引
     * int
     */
    @Column(name = "`file_index`")
    var fileIndex: Int = fileIndex
        internal set

    /**
     * 文件名
     * varchar(200)
     */
    @Column(name = "`file_name`")
    var fileName: String? = fileName
        internal set

    /**
     * 文件大小
     * bigint
     */
    @Column(name = "`file_size`")
    var fileSize: Long? = fileSize
        internal set

    /**
     * 文件路径
     * varchar(100)
     */
    @Column(name = "`file_path`")
    var filePath: String? = filePath
        internal set

    /**
     * 更新类型
     * 0:UNKNOW:未知类型
     * 1:NO_UPDATE:无更新
     * 2:HAS_UPDATE:有更新
     * tinyint
     */
    @Convert(converter = UpdateType.Converter::class)
    @Column(name = "`update_type`")
    var updateType: UpdateType = updateType
        internal set

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
    var transferResult: TransferResult = transferResult
        internal set

    /**
     * ABR 源视频宽度(px)
     * int
     */
    @Column(name = "`abr_source_width`")
    var abrSourceWidth: Int? = abrSourceWidth
        internal set

    /**
     * ABR 源视频高度(px)
     * int
     */
    @Column(name = "`abr_source_height`")
    var abrSourceHeight: Int? = abrSourceHeight
        internal set

    /**
     * ABR 源视频码率(kbps)
     * int
     */
    @Column(name = "`abr_source_bitrate_kbps`")
    var abrSourceBitrateKbps: Int? = abrSourceBitrateKbps
        internal set

    /**
     * 加密状态
     * 1:UNENCRYPTED:未加密
     * 2:ENCRYPTING:加密中
     * 3:ENCRYPTED:已加密
     * 4:FAILED:失败
     * int
     */
    @Convert(converter = EncryptStatus.Converter::class)
    @Column(name = "`encrypt_status`")
    var encryptStatus: EncryptStatus = encryptStatus
        internal set

    /**
     * 加密方式
     * 1:HLS_AES_128:AES-128
     * 2:SAMPLE_AES:SAMPLE-AES
     * 3:DRM:DRM占位
     * int
     */
    @Convert(converter = EncryptMethod.Converter::class)
    @Column(name = "`encrypt_method`")
    var encryptMethod: EncryptMethod = encryptMethod
        internal set

    /**
     * 关联密钥ID
     * bigint
     */
    @Column(name = "`encrypt_key_id`")
    var encryptKeyId: Long? = encryptKeyId
        internal set

    /**
     * 加密失败原因
     * varchar(512)
     */
    @Column(name = "`encrypt_fail_reason`")
    var encryptFailReason: String? = encryptFailReason
        internal set

    /**
     * 持续时间（秒）
     * int
     */
    @Column(name = "`duration`")
    var duration: Int? = duration
        internal set

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】

    fun updateType(typpe: UpdateType) {
        this.updateType = typpe
    }

    /**
     * 标记转码成功并更新文件信息
     *
     * @param duration 视频时长(秒)
     * @param fileSize 文件大小(字节)
     * @param filePath 文件路径
     */
    fun markTransferSuccess(duration: Int?, fileSize: Long?, filePath: String?) {
        this.transferResult = TransferResult.SUCCESS
        this.duration = duration
        this.fileSize = fileSize
        this.filePath = filePath
        this.updateType = UpdateType.NO_UPDATE
        events().attach(this) { VideoFilePostTranscodeResultUpdatedDomainEvent(this) }
    }

    /**
     * 标记转码失败
     */
    fun markTransferFailed(errorMessage: String? = null) {
        this.transferResult = TransferResult.FAILED
        this.updateType = UpdateType.HAS_UPDATE
        events().attach(this) { VideoFilePostTranscodeResultUpdatedDomainEvent(this) }
    }

    fun onCreate() {
        this.transferResult = TransferResult.TRANSCODING
        this.updateType = UpdateType.HAS_UPDATE
        events().attach(this) { VideoFilePostCreatedDomainEvent(this) }
    }

    fun onDelete() {
        events().attach(this) { VideoFilePostDeletedDomainEvent(this) }
    }

    fun applyTranscodeResult(
        success: Boolean,
        duration: Int?,
        fileSize: Long?,
        filePath: String?,
        failReason: String?,
        abrSourceWidth: Int?,
        abrSourceHeight: Int?,
        abrSourceBitrateKbps: Int?,
        variants: List<VideoHlsAbrVariant>?
    ) {
        // 更新 ABR 元数据（成功/失败都记录探测值，方便追踪）
        abrSourceWidth?.let { this.abrSourceWidth = it }
        abrSourceHeight?.let { this.abrSourceHeight = it }
        abrSourceBitrateKbps?.let { this.abrSourceBitrateKbps = it }

        if (success) {
            // 重置档位列表
            videoHlsAbrVariants.clear()
            variants?.let { videoHlsAbrVariants.addAll(it) }
            markTransferSuccess(duration, fileSize, filePath ?: this.filePath)
        } else {
            markTransferFailed(failReason)
        }
    }

    fun applyEncryptResult(
        success: Boolean,
        method: edu.only4.danmuku.domain.aggregates.video_file_post.enums.EncryptMethod,
        keyDbId: Long?,
        failReason: String?,
    ) {
        this.encryptMethod = method
        this.encryptKeyId = keyDbId
        this.encryptFailReason = failReason
        this.encryptStatus =
            if (success) edu.only4.danmuku.domain.aggregates.video_file_post.enums.EncryptStatus.ENCRYPTED else edu.only4.danmuku.domain.aggregates.video_file_post.enums.EncryptStatus.FAILED
    }

    // 【行为方法结束】
}
