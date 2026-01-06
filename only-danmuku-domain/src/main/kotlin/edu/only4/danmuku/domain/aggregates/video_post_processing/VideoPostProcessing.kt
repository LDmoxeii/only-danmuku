package edu.only4.danmuku.domain.aggregates.video_post_processing

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.DomainEventSupervisorSupport.events

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity
import edu.only4.danmuku.domain.aggregates.video_post_processing.enums.ProcessStatus
import edu.only4.danmuku.domain.aggregates.video_post_processing.events.VideoPostProcessingCompletedDomainEvent
import edu.only4.danmuku.domain.aggregates.video_post_processing.events.VideoPostProcessingStartedDomainEvent
import edu.only4.danmuku.domain.aggregates.video_post_processing.events.VideoPostProcessingTranscodeCompletedDomainEvent

import jakarta.persistence.*

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 视频稿件处理聚合;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2026/01/06
 */
@Aggregate(aggregate = "VideoPostProcessing", name = "VideoPostProcessing", root = true, type = Aggregate.TYPE_ENTITY, description = "视频稿件处理聚合，")
@Entity
@Table(name = "`video_post_processing`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `video_post_processing` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class VideoPostProcessing(
    id: Long = 0L,
    videoPostId: Long = 0L,
    totalFiles: Int = 0,
    transcodeStatus: ProcessStatus = ProcessStatus.valueOf(0),
    encryptStatus: ProcessStatus = ProcessStatus.valueOf(0),
    transcodeDoneCount: Int = 0,
    encryptDoneCount: Int = 0,
    failedCount: Int = 0,
    lastFailReason: String? = null,
) : AuditedFieldsEntity() {
    // 【字段映射开始】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "`parent_id`", nullable = false)
    var videoPostProcessingFiles: MutableList<VideoPostProcessingFile> = mutableListOf()

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
     * 视频稿件ID
     * bigint
     */
    @Column(name = "`video_post_id`")
    var videoPostId: Long = videoPostId
        internal set

    /**
     * 分P总数
     * int
     */
    @Column(name = "`total_files`")
    var totalFiles: Int = totalFiles
        internal set

    /**
     * 转码状态
     * 0:UNKNOW:未知
     * 1:PENDING:待处理
     * 2:PROCESSING:处理中
     * 3:SUCCESS:成功
     * 4:FAILED:失败
     * 5:SKIPPED:跳过
     * tinyint(1)
     */
    @Convert(converter = ProcessStatus.Converter::class)
    @Column(name = "`transcode_status`")
    var transcodeStatus: ProcessStatus = transcodeStatus
        internal set

    /**
     * 加密状态
     * 0:UNKNOW:未知
     * 1:PENDING:待处理
     * 2:PROCESSING:处理中
     * 3:SUCCESS:成功
     * 4:FAILED:失败
     * 5:SKIPPED:跳过
     * tinyint(1)
     */
    @Convert(converter = ProcessStatus.Converter::class)
    @Column(name = "`encrypt_status`")
    var encryptStatus: ProcessStatus = encryptStatus
        internal set

    /**
     * 转码完成数
     * int
     */
    @Column(name = "`transcode_done_count`")
    var transcodeDoneCount: Int = transcodeDoneCount
        internal set

    /**
     * 加密完成数（含 SKIPPED）
     * int
     */
    @Column(name = "`encrypt_done_count`")
    var encryptDoneCount: Int = encryptDoneCount
        internal set

    /**
     * 失败文件数
     * int
     */
    @Column(name = "`failed_count`")
    var failedCount: Int = failedCount
        internal set

    /**
     * 最近失败原因
     * varchar(512)
     */
    @Column(name = "`last_fail_reason`")
    var lastFailReason: String? = lastFailReason
        internal set

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】

    fun onCreate() {
    }

    fun appendFiles(fileList: List<AppendFileSpec>) {
        if (fileList.isEmpty()) return
        val existing = videoPostProcessingFiles.map { it.fileIndex }.toMutableSet()
        val appended = fileList.filter { existing.add(it.fileIndex) }.map { spec ->
            VideoPostProcessingFile(
                fileIndex = spec.fileIndex,
                uploadId = spec.uploadId,
                transcodeStatus = ProcessStatus.PROCESSING,
                encryptStatus = ProcessStatus.PENDING,
                transcodeOutputPrefix = spec.transcodeOutputPrefix,
                transcodeOutputPath = spec.transcodeOutputPath,
                transcodeVariantsJson = null,
                encryptOutputDir = spec.encryptOutputDir,
                encryptOutputPrefix = null,
                duration = spec.duration,
                fileSize = spec.fileSize,
                failReason = null,
            )
        }
        if (appended.isEmpty()) return
        videoPostProcessingFiles.addAll(appended)
        refreshStatus()
        events().attach(this) {
            VideoPostProcessingStartedDomainEvent(
                videoPostId = this.videoPostId,
                fileList = buildStartedFileList(appended),
                entity = this
            )
        }
    }

    fun applyTranscodeResult(
        fileIndex: Int,
        success: Boolean,
        outputPrefix: String?,
        outputPath: String?,
        duration: Int?,
        fileSize: Long?,
        variantsJson: String?,
        failReason: String?,
        variants: List<VideoPostProcessingVariant>,
    ) {
        val file = getFile(fileIndex)
        file.transcodeStatus = if (success) ProcessStatus.SUCCESS else ProcessStatus.FAILED
        if (!outputPrefix.isNullOrBlank()) {
            file.transcodeOutputPrefix = outputPrefix
        }
        if (!outputPath.isNullOrBlank()) {
            file.transcodeOutputPath = outputPath
        }
        file.transcodeVariantsJson = variantsJson
        if (duration != null) {
            file.duration = duration
        }
        if (fileSize != null) {
            file.fileSize = fileSize
        }
        file.failReason = if (success) null else failReason
        if (success) {
            file.encryptStatus = ProcessStatus.PROCESSING
            file.videoPostProcessingVariants.clear()
            file.videoPostProcessingVariants.addAll(variants)
        } else if (file.encryptStatus != ProcessStatus.SKIPPED) {
            file.encryptStatus = ProcessStatus.SKIPPED
        }
        refreshStatus()
        if (success) {
            events().attach(this) {
                VideoPostProcessingTranscodeCompletedDomainEvent(
                    videoPostId = videoPostId,
                    fileIndex = fileIndex,
                    outputPrefix = file.transcodeOutputPrefix,
                    encOutputDir = file.encryptOutputDir,
                    variantsJson = file.transcodeVariantsJson,
                    entity = this
                )
            }
        }
    }

    fun applyEncryptResult(
        fileIndex: Int,
        success: Boolean,
        encryptedPrefix: String?,
        failReason: String?,
    ) {
        val file = getFile(fileIndex)
        file.encryptStatus = if (success) ProcessStatus.SUCCESS else ProcessStatus.FAILED
        if (success && !encryptedPrefix.isNullOrBlank()) {
            file.encryptOutputPrefix = encryptedPrefix
        }
        file.failReason = if (success) null else failReason
        refreshStatus()
        if (isAllStepsCompleted()) {
            events().attach(this) {
                VideoPostProcessingCompletedDomainEvent(
                    videoPostId = videoPostId,
                    duration = totalDuration(),
                    failedCount = failedCount,
                    lastFailReason = lastFailReason,
                    entity = this
                )
            }
        }
    }

    fun isAllStepsCompleted(): Boolean {
        if (videoPostProcessingFiles.isEmpty() || failedCount > 0) return false
        return videoPostProcessingFiles.all { file ->
            isDone(file.transcodeStatus) && isDone(file.encryptStatus)
        }
    }

    fun totalDuration(): Int? {
        val durations = videoPostProcessingFiles.mapNotNull { it.duration }
        return if (durations.isEmpty()) null else durations.sum()
    }

    private fun getFile(fileIndex: Int): VideoPostProcessingFile {
        return videoPostProcessingFiles.firstOrNull { it.fileIndex == fileIndex }
            ?: throw IllegalStateException("处理文件不存在: fileIndex=$fileIndex")
    }

    private fun buildStartedFileList(files: List<VideoPostProcessingFile>): List<VideoPostProcessingStartedDomainEvent.FileItem> {
        return files.map { file ->
            VideoPostProcessingStartedDomainEvent.FileItem(
                uploadId = file.uploadId,
                fileIndex = file.fileIndex,
                outputDir = file.transcodeOutputPath ?: "",
                objectPrefix = file.transcodeOutputPrefix ?: "",
                encOutputDir = file.encryptOutputDir ?: ""
            )
        }
    }

    private fun refreshStatus() {
        totalFiles = videoPostProcessingFiles.size
        transcodeDoneCount = videoPostProcessingFiles.count { isDone(it.transcodeStatus) }
        encryptDoneCount = videoPostProcessingFiles.count { isDone(it.encryptStatus) }
        failedCount = videoPostProcessingFiles.count { it.transcodeStatus == ProcessStatus.FAILED || it.encryptStatus == ProcessStatus.FAILED }
        lastFailReason = videoPostProcessingFiles.mapNotNull { it.failReason?.takeIf(String::isNotBlank) }.lastOrNull()
        transcodeStatus = resolveStatus(videoPostProcessingFiles.map { it.transcodeStatus })
        encryptStatus = resolveStatus(videoPostProcessingFiles.map { it.encryptStatus })
    }

    private fun resolveStatus(statuses: List<ProcessStatus>): ProcessStatus {
        if (statuses.isEmpty()) return ProcessStatus.UNKNOW
        if (statuses.any { it == ProcessStatus.FAILED }) return ProcessStatus.FAILED
        if (statuses.all { it == ProcessStatus.SKIPPED }) return ProcessStatus.SKIPPED
        if (statuses.all { it == ProcessStatus.PENDING }) return ProcessStatus.PENDING
        if (statuses.all { isDone(it) }) return ProcessStatus.SUCCESS
        return ProcessStatus.PROCESSING
    }

    private fun isDone(status: ProcessStatus): Boolean {
        return status == ProcessStatus.SUCCESS || status == ProcessStatus.SKIPPED
    }

    data class AppendFileSpec(
        val uploadId: Long,
        val fileIndex: Int,
        val transcodeOutputPath: String,
        val transcodeOutputPrefix: String,
        val encryptOutputDir: String,
        val duration: Int?,
        val fileSize: Long?,
    )

    // 【行为方法结束】
}
