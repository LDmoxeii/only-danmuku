package edu.only4.danmuku.domain.aggregates.video_post_processing

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity
import edu.only4.danmuku.domain.aggregates.video_post_processing.enums.ProcessStatus

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
 * @date 2026/01/05
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
}
