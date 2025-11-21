package edu.only4.danmuku.domain.aggregates.video_file_post

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.DomainEventSupervisorSupport.events

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity
import edu.only4.danmuku.domain.aggregates.video_file_post.enums.TransferResult
import edu.only4.danmuku.domain.aggregates.video_file_post.enums.UpdateType
import edu.only4.danmuku.domain.aggregates.video_post.events.VideoFileDraftTranscodedDomainEvent
import edu.only4.danmuku.domain.aggregates.video_post.ports.VideoFileTranscodePort

import jakarta.persistence.*

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.io.File

/**
 * 视频文件信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
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
    duration: Int? = null,
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

    // UploadSpec / BuildResult / buildFromUploads 迁移至 VideoPost

    // 【行为方法结束】

    /**
     * 聚合方法：执行当前文件的完整转码流程
     * - 读取上传会话分片目录
     * - 合并分片为临时 MP4
     * - 如为 HEVC 则转码为 H.264
     * - 切片为 TS 并生成 m3u8
     * - 统计时长与目录大小，写入本实体并发布事件
     *
     * 任何步骤失败，将标记为转码失败并抛出异常给上层统计/补偿。
     */
    fun transcode(
        videoPostId: Long,
        port: VideoFileTranscodePort,
    ) {
        // 1) 解析目录
        val tempDir = port.resolveTempDir(this.uploadId)
        val (targetDir, relativePath) = port.resolveTargetDir(this.customerId, videoPostId, this.fileIndex)

        // 2) 合并分片
        val mergedMp4 = port.newMergedOutputFile(targetDir)
        try {
            port.mergeChunks(tempDir, mergedMp4)
        } catch (e: Exception) {
            this.markTransferFailed(e.message)
            throw e
        }

        try {
            // 3) 编码检查与可选转码
            val codec = port.detectCodec(mergedMp4.absolutePath)
            if (port.isHevc(codec)) {
                val hevcBackup = File(mergedMp4.parentFile, mergedMp4.name + ".hevc")
                // 将原文件改名为 .hevc 作为输入
                mergedMp4.renameTo(hevcBackup)
                // 输出写回 mergedMp4 路径
                port.transcodeHevcToH264(mergedMp4.absolutePath, hevcBackup.absolutePath)
                hevcBackup.delete()
            }

            // 4) 统计时长（以最终用于切片的 mp4 为准）
            val duration = port.durationOf(mergedMp4.absolutePath)

            // 5) 切片并生成 m3u8
            port.sliceToHls(targetDir, mergedMp4)
            // 删除合并后的临时 mp4
            mergedMp4.delete()

            // 6) 统计目录大小，标记成功
            val size = port.folderSize(targetDir)
            this.markTransferSuccess(duration = duration, fileSize = size, filePath = relativePath)

            // 7) 清理分片源目录
            port.cleanupTempDir(tempDir)
        } catch (e: Exception) {
            // 失败：标记并抛出给上层
            this.markTransferFailed(e.message)
            // 清理合并文件以避免脏数据
            runCatching { mergedMp4.delete() }
            throw e
        }
    }
}
