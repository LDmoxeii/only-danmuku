package com.edu.only4.danmuku.domain.aggregates.customer_video_series

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 用户视频序列视频关联;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/09/27
 */
@Aggregate(aggregate = "CustomerVideoSeries", name = "CustomerVideoSeriesVideo", root = false, type = Aggregate.TYPE_ENTITY, description = "用户视频序列视频关联;")
@Entity
@Table(name = "`customer_video_series_video`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `customer_video_series_video` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class CustomerVideoSeriesVideo (
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
     * 用户ID
     * bigint
     */
    @Column(name = "`customer_id`")
    var customerId: Long = 0L,

    /**
     * 列表ID
     * bigint
     */
    @Column(name = "`series_id`")
    var seriesId: Long = 0L,

    /**
     * 视频ID
     * bigint
     */
    @Column(name = "`video_id`")
    var videoId: Long = 0L,

    /**
     * 排序
     * tinyint
     */
    @Column(name = "`sort`")
    var sort: Byte = 0,

    /**
     * 删除标识 0：未删除 id：已删除
     * tinyint(1)
     */
    @Column(name = "`deleted`")
    var deleted: Boolean = false,
) {

    @ManyToOne(cascade = [], fetch = FetchType.EAGER)
    @JoinColumn(name = "`series_id`", nullable = false, insertable = false, updatable = false)
    var customerVideoSeries: com.edu.only4.danmuku.domain.aggregates.customer_video_series.CustomerVideoSeries? = null

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】



    // 【行为方法结束】

}
