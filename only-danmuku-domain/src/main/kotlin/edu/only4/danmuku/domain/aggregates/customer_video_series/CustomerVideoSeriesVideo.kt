package edu.only4.danmuku.domain.aggregates.customer_video_series

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity

import jakarta.persistence.*
import jakarta.persistence.Table

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 用户视频序列视频关联;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
@Aggregate(aggregate = "CustomerVideoSeries", name = "CustomerVideoSeriesVideo", root = false, type = Aggregate.TYPE_ENTITY, description = "用户视频序列视频关联，")
@Entity
@Table(name = "`customer_video_series_video`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `customer_video_series_video` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class CustomerVideoSeriesVideo(
    id: Long = 0L,
    customerId: Long = 0L,
    videoId: Long = 0L,
    sort: Byte = 0,
) : AuditedFieldsEntity() {
    // 【字段映射开始】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动
    @ManyToOne(cascade = [], fetch = FetchType.EAGER)
    @JoinColumn(name = "`series_id`", nullable = false, insertable = false, updatable = false)
    var customerVideoSeries: CustomerVideoSeries? = null

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
     * 用户ID
     * bigint
     */
    @Column(name = "`customer_id`")
    var customerId: Long = customerId
        internal set

    /**
     * 视频ID
     * bigint
     */
    @Column(name = "`video_id`")
    var videoId: Long = videoId
        internal set

    /**
     * 排序
     * tinyint
     */
    @Column(name = "`sort`")
    var sort: Byte = sort
        internal set

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】

    // 【行为方法结束】
}
