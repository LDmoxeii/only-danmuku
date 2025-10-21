package edu.only4.danmuku.application.queries._share.model.video_play_history

import edu.only4.danmuku.application.queries._share.model.video.JVideo
import org.babyfish.jimmer.sql.*

/**
 * Jimmer 视频播放历史实体
 * 用于 CQRS 读模型查询
 */
@Table(name = "video_play_history")
@Entity
interface JVideoPlayHistory {

    /**
     * ID
     */
    @Id
    val id: Long

    /**
     * 用户ID
     */
    val customerId: Long

    /**
     * 视频ID
     */
    @IdView("video")
    val videoId: Long

    /**
     * 关联的视频
     */
    @ManyToOne
    @JoinColumn(name = "video_id")
    val video: JVideo

    /**
     * 文件索引
     */
    val fileIndex: Int

    /**
     * 创建时间（播放时间）
     */
    val createTime: Long?

    /**
     * 更新时间
     */
    val updateTime: Long?

    /**
     * 逻辑删除标识（0：未删除，非0：已删除）
     */
    @LogicalDeleted
    val deleted: Long
}
