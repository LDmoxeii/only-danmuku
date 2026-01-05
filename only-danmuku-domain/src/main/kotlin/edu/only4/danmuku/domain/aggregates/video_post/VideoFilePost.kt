package edu.only4.danmuku.domain.aggregates.video_post

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity
import edu.only4.danmuku.domain.aggregates.video_post.enums.EncryptMethod
import edu.only4.danmuku.domain.aggregates.video_post.enums.EncryptStatus
import edu.only4.danmuku.domain.aggregates.video_post.enums.TransferResult

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
 * @date 2026/01/05
 */
@Aggregate(aggregate = "VideoPost", name = "VideoFilePost", root = false, type = Aggregate.TYPE_ENTITY, description = "视频文件信息，")
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
    fileIndex: Int = 0,
    fileName: String? = null,
    fileSize: Long? = null,
    filePath: String? = null,
    transcodeOutputPrefix: String? = null,
    transcodeVariantsJson: String? = null,
    encryptOutputPrefix: String? = null,
    transferResult: TransferResult = TransferResult.valueOf(0),
    encryptStatus: EncryptStatus = EncryptStatus.valueOf(1),
    encryptMethod: EncryptMethod = EncryptMethod.valueOf(1),
    encryptKeyVersion: Int? = null,
    duration: Int? = null,
) : AuditedFieldsEntity() {
    // 【字段映射开始】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动
    @ManyToOne(cascade = [], fetch = FetchType.EAGER)
    @JoinColumn(name = "`video_post_id`", nullable = false, insertable = false, updatable = false)
    var videoPost: VideoPost? = null
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "`parent_id`", nullable = false)
    var videoFilePostVariants: MutableList<VideoFilePostVariant> = mutableListOf()

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
     * 转码输出对象前缀
     * varchar(512)
     */
    @Column(name = "`transcode_output_prefix`")
    var transcodeOutputPrefix: String? = transcodeOutputPrefix
        internal set

    /**
     * 转码清晰度结果 JSON
     * text
     */
    @Column(name = "`transcode_variants_json`")
    var transcodeVariantsJson: String? = transcodeVariantsJson
        internal set

    /**
     * 加密输出对象前缀
     * varchar(512)
     */
    @Column(name = "`encrypt_output_prefix`")
    var encryptOutputPrefix: String? = encryptOutputPrefix
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
     * 加密状态
     * 0:UNKNOW:未知
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
     * 持续时间（秒）
     * int
     */
    @Column(name = "`duration`")
    var duration: Int? = duration
        internal set

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】
    fun applyEncryptResult(
        success: Boolean,
        method: EncryptMethod,
        keyVersion: Int?,
        outputPrefix: String?,
    ) {
        this.encryptMethod = method
        this.encryptKeyVersion = keyVersion
        if (success) {
            if (!outputPrefix.isNullOrBlank()) {
                this.encryptOutputPrefix = outputPrefix
            }
            this.encryptStatus = EncryptStatus.ENCRYPTED
        } else {
            this.encryptStatus = EncryptStatus.FAILED
        }
    }
    // 【行为方法结束】
}
