package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Table

/**
 * 视频文件信息;@P=video_draft
 */
@Entity
@Table(name = "video_file_draft")
interface VideoFilePost : BaseEntity {
    /**
     * 文件索引
     */
    @Column(name = "file_index")
    val fileIndex: String

    /**
     * 文件名
     */
    @Column(name = "file_name")
    val fileName: String?

    /**
     * 文件大小
     */
    @Column(name = "file_size")
    val fileSize: Long?

    /**
     * 文件路径
     */
    @Column(name = "file_path")
    val filePath: String?

    /**
     * 更新类型 @E=0:UNKNOW:未知类型|1:NO_UPDATE:无更新|2:HAS_UPDATE:有更新;@T=UpdateType
     */
    @Column(name = "update_type")
    val updateType: Int

    /**
     * 转码结果 @E=0:UNKNOW:未知结果|1:TRANSCODING:转码中|2:SUCCESS:转码成功|3:FAILED:转码失败;@T=TransferResult
     */
    @Column(name = "transfer_result")
    val transferResult: Int

    /**
     * 持续时间（秒）
     */
    @Column(name = "duration")
    val duration: String?
}
