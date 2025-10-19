package edu.only4.danmuku.application.queries._share.model.customer_video_series

import org.babyfish.jimmer.sql.*

/**
 * Jimmer 用户视频系列实体
 * 用于 CQRS 读模型查询
 */
@Table(name = "customer_video_series")
@Entity
interface JCustomerVideoSeries {

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
     * 系列名称
     */
    val seriesName: String

    /**
     * 系列描述
     */
    val seriesDescription: String?

    /**
     * 排序
     */
    val sort: Byte

    /**
     * 创建时间
     */
    val createTime: Long?

    /**
     * 更新时间
     */
    val updateTime: Long?

    /**
     * 逻辑删除标识
     */
    @LogicalDeleted("true")
    val deleted: Boolean

    /**
     * 关联的系列视频列表
     */
    @OneToMany(mappedBy = "series")
    val seriesVideos: List<JCustomerVideoSeriesVideo>
}
