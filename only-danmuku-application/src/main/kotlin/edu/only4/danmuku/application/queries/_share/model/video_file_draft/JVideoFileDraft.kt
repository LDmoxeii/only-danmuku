package edu.only4.danmuku.application.queries._share.model.video_file_draft

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.Table

/**
 * Jimmer 视频文件草稿实体
 * 用于 CQRS 读模型查询
 */
@Table(name = "video_file_draft")
@Entity
interface JVideoFileDraft {

    /**
     * ID
     */
    @Id
    val id: Long

    /**
     * 文件ID
     */
    val fileId: Long

    /**
     * 上传ID
     */
    val uploadId: Long

    /**
     * 用户ID
     */
    val customerId: Long

    /**
     * 视频ID
     */
    val videoId: Long

    /**
     * 文件索引
     */
    val fileIndex: Int

    /**
     * 文件名
     */
    val fileName: String?

    /**
     * 文件大小
     */
    val fileSize: Long?

    /**
     * 文件路径
     */
    val filePath: String?

    /**
     * 更新类型
     * 0:UNKNOW:未知类型
     * 1:NO_UPDATE:无更新
     * 2:HAS_UPDATE:有更新
     */
    val updateType: Byte

    /**
     * 转码结果
     * 0:UNKNOW:未知结果
     * 1:TRANSCODING:转码中
     * 2:SUCCESS:转码成功
     * 3:FAILED:转码失败
     */
    val transferResult: Byte

    /**
     * 持续时间（秒）
     */
    val duration: Int?

    /**
     * 逻辑删除标识（0：未删除，非0：已删除）
     */
    @LogicalDeleted
    val deleted: Long
}
