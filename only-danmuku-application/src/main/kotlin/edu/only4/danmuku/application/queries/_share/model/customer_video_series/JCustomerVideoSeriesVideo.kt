package edu.only4.danmuku.application.queries._share.model.customer_video_series

import edu.only4.danmuku.application.queries._share.model.video.JVideo
import org.babyfish.jimmer.sql.*

/**
 * Jimmer 用户视频系列视频关联实体
 * 用于 CQRS 读模型查询
 */
@Table(name = "customer_video_series_video")
@Entity
interface JCustomerVideoSeriesVideo {

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
     * 系列ID
     */
    @IdView("series")
    val seriesId: Long

    /**
     * 视频ID
     */
    @IdView("video")
    val videoId: Long

    /**
     * 排序
     */
    val sort: Byte

    /**
     * 创建时间
     */
    val createTime: Long?

    /**
     * 关联的系列
     */
    @ManyToOne
    @JoinColumn(name = "series_id")
    val series: JCustomerVideoSeries

    /**
     * 关联的视频
     */
    @ManyToOne
    @JoinColumn(name = "video_id")
    val video: JVideo

    /**
     * 逻辑删除标识（0：未删除，非0：已删除）
     */
    @LogicalDeleted
    val deleted: Long
}
