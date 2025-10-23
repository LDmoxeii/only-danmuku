package edu.only4.danmuku.domain.aggregates.video_file_draft

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import edu.only4.danmuku.domain.aggregates.video_file_draft.enums.TransferResult
import edu.only4.danmuku.domain.aggregates.video_file_draft.enums.UpdateType
import jakarta.persistence.*
import jakarta.persistence.Table
import org.hibernate.annotations.*

/**
 * 视频文件信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Aggregate(aggregate = "VideoFileDraft", name = "VideoFileDraft", root = true, type = Aggregate.TYPE_ENTITY, description = "视频文件信息，")
@Entity
@Table(name = "`video_file_draft`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `video_file_draft` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class VideoFileDraft (
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
     * 唯一ID
     * bigint
     */
    @Column(name = "`file_id`")
    var fileId: Long = 0L,

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
     * 视频ID
     * bigint
     */
    @Column(name = "`video_id`")
    var videoId: Long = 0L,

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

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】

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
    }

    /**
     * 标记转码失败
     */
    fun markTransferFailed() {
        this.transferResult = TransferResult.FAILED
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

    // 【行为方法结束】
}
