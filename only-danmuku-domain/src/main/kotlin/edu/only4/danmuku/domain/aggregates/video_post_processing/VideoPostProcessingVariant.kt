package edu.only4.danmuku.domain.aggregates.video_post_processing

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity
import edu.only4.danmuku.domain.aggregates.video_post_processing.enums.ProcessStatus

import jakarta.persistence.*

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 视频稿件处理分辨率档位;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2026/01/14
 */
@Aggregate(aggregate = "VideoPostProcessing", name = "VideoPostProcessingVariant", root = false, type = Aggregate.TYPE_ENTITY, description = "视频稿件处理分辨率档位，")
@Entity
@Table(name = "`video_post_processing_variant`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `video_post_processing_variant` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class VideoPostProcessingVariant(
    id: Long = 0L,
    quality: String = "",
    width: Int = 0,
    height: Int = 0,
    videoBitrateKbps: Int = 0,
    audioBitrateKbps: Int = 0,
    bandwidthBps: Int = 0,
    playlistPath: String = "",
    segmentPrefix: String? = null,
    segmentDuration: Int? = null,
    transcodeStatus: ProcessStatus = ProcessStatus.valueOf(0),
    encryptStatus: ProcessStatus = ProcessStatus.valueOf(0),
    encryptFailReason: String? = null,
) : AuditedFieldsEntity() {
    // 【字段映射开始】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动
    @ManyToOne(cascade = [], fetch = FetchType.EAGER)
    @JoinColumn(name = "`parent_id`", nullable = false, insertable = false, updatable = false)
    var videoPostProcessingFile: VideoPostProcessingFile? = null

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
     * 清晰度档位，如 1080p/720p
     * varchar(32)
     */
    @Column(name = "`quality`")
    var quality: String = quality
        internal set

    /**
     * 输出宽度(px)
     * int
     */
    @Column(name = "`width`")
    var width: Int = width
        internal set

    /**
     * 输出高度(px)
     * int
     */
    @Column(name = "`height`")
    var height: Int = height
        internal set

    /**
     * 视频码率(kbps)
     * int
     */
    @Column(name = "`video_bitrate_kbps`")
    var videoBitrateKbps: Int = videoBitrateKbps
        internal set

    /**
     * 音频码率(kbps)
     * int
     */
    @Column(name = "`audio_bitrate_kbps`")
    var audioBitrateKbps: Int = audioBitrateKbps
        internal set

    /**
     * Master 中的 BANDWIDTH（bps）
     * int
     */
    @Column(name = "`bandwidth_bps`")
    var bandwidthBps: Int = bandwidthBps
        internal set

    /**
     * 子清晰度 m3u8 路径，如 720p/index.m3u8
     * varchar(512)
     */
    @Column(name = "`playlist_path`")
    var playlistPath: String = playlistPath
        internal set

    /**
     * 切片目录前缀，如 720p/
     * varchar(512)
     */
    @Column(name = "`segment_prefix`")
    var segmentPrefix: String? = segmentPrefix
        internal set

    /**
     * 切片目标时长（秒）
     * int
     */
    @Column(name = "`segment_duration`")
    var segmentDuration: Int? = segmentDuration
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
     * 加密失败原因
     * varchar(512)
     */
    @Column(name = "`encrypt_fail_reason`")
    var encryptFailReason: String? = encryptFailReason
        internal set

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动
}
