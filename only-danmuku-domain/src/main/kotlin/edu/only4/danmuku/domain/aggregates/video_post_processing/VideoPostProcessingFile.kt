package edu.only4.danmuku.domain.aggregates.video_post_processing

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity
import edu.only4.danmuku.domain.aggregates.video_post.enums.EncryptMethod
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
 * 视频稿件处理文件状态;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2026/01/06
 */
@Aggregate(aggregate = "VideoPostProcessing", name = "VideoPostProcessingFile", root = false, type = Aggregate.TYPE_ENTITY, description = "视频稿件处理文件状态，")
@Entity
@Table(name = "`video_post_processing_file`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `video_post_processing_file` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class VideoPostProcessingFile(
    id: Long = 0L,
    fileIndex: Int = 0,
    uploadId: Long = 0L,
    transcodeStatus: ProcessStatus = ProcessStatus.valueOf(0),
    encryptStatus: ProcessStatus = ProcessStatus.valueOf(0),
    encryptMethod: EncryptMethod = EncryptMethod.valueOf(1),
    encryptKeyVersion: Int? = null,
    transcodeOutputPrefix: String? = null,
    transcodeOutputPath: String? = null,
    transcodeVariantsJson: String? = null,
    encryptOutputDir: String? = null,
    encryptOutputPrefix: String? = null,
    duration: Int? = null,
    fileSize: Long? = null,
    failReason: String? = null,
) : AuditedFieldsEntity() {
    // 【字段映射开始】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动
    @ManyToOne(cascade = [], fetch = FetchType.EAGER)
    @JoinColumn(name = "`parent_id`", nullable = false, insertable = false, updatable = false)
    var videoPostProcessing: VideoPostProcessing? = null
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "`parent_id`", nullable = false)
    var videoPostProcessingVariants: MutableList<VideoPostProcessingVariant> = mutableListOf()

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
     * 文件索引
     * int
     */
    @Column(name = "`file_index`")
    var fileIndex: Int = fileIndex
        internal set

    /**
     * 上传会话ID
     * bigint
     */
    @Column(name = "`upload_id`")
    var uploadId: Long = uploadId
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
     * 加密方式
     * 0:UNKNOW:未知
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
     * 加密密钥版本
     * int
     */
    @Column(name = "`encrypt_key_version`")
    var encryptKeyVersion: Int? = encryptKeyVersion
        internal set

    /**
     * 转码输出对象前缀
     * varchar(512)
     */
    @Column(name = "`transcode_output_prefix`")
    var transcodeOutputPrefix: String? = transcodeOutputPrefix
        internal set

    /**
     * 转码输出本地路径
     * varchar(512)
     */
    @Column(name = "`transcode_output_path`")
    var transcodeOutputPath: String? = transcodeOutputPath
        internal set

    /**
     * 转码清晰度结果 JSON
     * text
     */
    @Column(name = "`transcode_variants_json`")
    var transcodeVariantsJson: String? = transcodeVariantsJson
        internal set

    /**
     * 加密输出本地目录
     * varchar(512)
     */
    @Column(name = "`encrypt_output_dir`")
    var encryptOutputDir: String? = encryptOutputDir
        internal set

    /**
     * 加密输出对象前缀
     * varchar(512)
     */
    @Column(name = "`encrypt_output_prefix`")
    var encryptOutputPrefix: String? = encryptOutputPrefix
        internal set

    /**
     * 时长（秒）
     * int
     */
    @Column(name = "`duration`")
    var duration: Int? = duration
        internal set

    /**
     * 文件大小
     * bigint
     */
    @Column(name = "`file_size`")
    var fileSize: Long? = fileSize
        internal set

    /**
     * 失败原因
     * varchar(512)
     */
    @Column(name = "`fail_reason`")
    var failReason: String? = failReason
        internal set

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动
}
