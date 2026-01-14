package edu.only4.danmuku.domain.aggregates.video_file_upload_session

import com.only.engine.exception.KnownException

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.DomainEventSupervisorSupport.events

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity
import edu.only4.danmuku.domain.aggregates.video_file_upload_session.enums.UploadStatus
import edu.only4.danmuku.domain.aggregates.video_file_upload_session.events.UploadSessionAbortedDomainEvent
import edu.only4.danmuku.domain.aggregates.video_file_upload_session.events.UploadSessionCreatedDomainEvent
import edu.only4.danmuku.domain.aggregates.video_file_upload_session.events.VideoFileUploadSessionChunkUploadedDomainEvent
import edu.only4.danmuku.domain.aggregates.video_file_upload_session.events.VideoFileUploadSessionMarkedDoneDomainEvent

import jakarta.persistence.*

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 视频分片上传会话; 用于跟踪预上传与分片进度
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2026/01/14
 */
@Aggregate(aggregate = "VideoFileUploadSession", name = "VideoFileUploadSession", root = true, type = Aggregate.TYPE_ENTITY, description = "视频分片上传会话， 用于跟踪预上传与分片进度")
@Entity
@Table(name = "`video_file_upload_session`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `video_file_upload_session` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class VideoFileUploadSession(
    id: Long = 0L,
    customerId: Long = 0L,
    fileName: String = "",
    chunks: Int = 0,
    chunkIndex: Int = 0,
    fileSize: Long? = 0L,
    tempDir: String? = null,
    status: UploadStatus = UploadStatus.valueOf(0),
    duration: Int? = null,
    expiresAt: Long? = null,
) : AuditedFieldsEntity() {
    // 【字段映射开始】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

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
     * 用户ID
     * bigint
     */
    @Column(name = "`customer_id`")
    var customerId: Long = customerId
        internal set

    /**
     * 文件名
     * varchar(200)
     */
    @Column(name = "`file_name`")
    var fileName: String = fileName
        internal set

    /**
     * 分片总数
     * int
     */
    @Column(name = "`chunks`")
    var chunks: Int = chunks
        internal set

    /**
     * 当前已上传的最大分片索引
     * int
     */
    @Column(name = "`chunk_index`")
    var chunkIndex: Int = chunkIndex
        internal set

    /**
     * 累计已上传大小（字节）
     * bigint
     */
    @Column(name = "`file_size`")
    var fileSize: Long? = fileSize
        internal set

    /**
     * 临时目录（绝对或相对路径）
     * varchar(512)
     */
    @Column(name = "`temp_dir`")
    var tempDir: String? = tempDir
        internal set

    /**
     * 状态
     * 0:UNKNOW:未知类型
     * 1:CREATED:已创建
     * 2:UPLOADING:上传中
     * 3:DONE:完成
     * 4:ABORTED:已放弃
     * 5:EXPIRED:已过期
     * tinyint(1)
     */
    @Convert(converter = UploadStatus.Converter::class)
    @Column(name = "`status`")
    var status: UploadStatus = status
        internal set

    /**
     * 视频时长（秒，可选）
     * int
     */
    @Column(name = "`duration`")
    var duration: Int? = duration
        internal set

    /**
     * 过期时间（秒时间戳）
     * bigint
     */
    @Column(name = "`expires_at`")
    var expiresAt: Long? = expiresAt
        internal set

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】

    /** 校验归属 */
    fun ensureOwnedBy(userId: Long) {
        if (this.customerId != userId) {
            throw KnownException.illegalArgument("没有权限操作该上传")
        }
    }

    /** 校验会话处于活跃可上传状态 */
    fun ensureActive() {
        if (this.status == UploadStatus.ABORTED || this.status == UploadStatus.EXPIRED) {
            throw KnownException.illegalArgument("上传会话不可用")
        }
    }

    /**
     * 校验分片是否允许上传（允许重传，同步推进，不允许跳跃）
     */
    fun ensureChunkAllowed(incomingChunkIndex: Int) {
        if (incomingChunkIndex < 0 || incomingChunkIndex > this.chunks - 1) {
            throw KnownException.illegalArgument("分片索引非法")
        }
        if ((incomingChunkIndex - 1) > this.chunkIndex) {
            throw KnownException.illegalArgument("分片索引非法")
        }
    }

    fun onCreate() {
        events().attach(this) { UploadSessionCreatedDomainEvent(this) }
    }

    fun onDelete() {
        // no-op
    }

    /**
     * 终止会话（放弃上传）
     */
    fun abort(now: Long) {
        this.status = UploadStatus.ABORTED
        this.updateTime = now

        events().attach(this) { UploadSessionAbortedDomainEvent(this) }
    }

    /**
     * 处理分片已写入后的状态推进
     */
    fun onChunkUploaded(incomingChunkIndex: Int, addedBytes: Long, now: Long) {
        if (this.status == UploadStatus.CREATED) {
            this.status = UploadStatus.UPLOADING
        }
        if (incomingChunkIndex > this.chunkIndex) {
            this.chunkIndex = incomingChunkIndex
        }
        this.fileSize = (this.fileSize ?: 0L) + addedBytes
        this.updateTime = now

        events().attach(this) { VideoFileUploadSessionChunkUploadedDomainEvent(this) }
    }

    /**
     * 若已到最后一个分片，则标记完成
     */
    fun tryMarkDoneIfComplete() {
        if (this.chunkIndex >= this.chunks - 1) {
            this.status = UploadStatus.DONE
            events().attach(this) { VideoFileUploadSessionMarkedDoneDomainEvent(this) }
        }
    }

    /**
     * 初始化会话的临时目录并进入上传中状态
     */
    fun initTempAndStartUploading(tempDir: String, now: Long) {
        this.tempDir = tempDir
        if (this.status == UploadStatus.CREATED) {
            this.status = UploadStatus.UPLOADING
        }
        this.updateTime = now
    }

    // 【行为方法结束】
}
