package edu.only4.danmuku.application.queries._share.model.video_file

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.Table

/**
 * Jimmer 视频文件实体
 * 用于 CQRS 读模型查询
 */
@Table(name = "video_file")
@Entity
interface JVideoFile {

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
     * 用户ID
     */
    val customerId: Long

    /**
     * 视频ID
     */
    val videoId: Long

    /**
     * 文件名
     */
    val fileName: String?

    /**
     * 文件索引
     */
    val fileIndex: Int

    /**
     * 文件大小
     */
    val fileSize: Long?

    /**
     * 文件路径
     */
    val filePath: String?

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
