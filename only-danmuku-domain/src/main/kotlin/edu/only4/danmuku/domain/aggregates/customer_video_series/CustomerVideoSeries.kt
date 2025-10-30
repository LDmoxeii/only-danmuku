package edu.only4.danmuku.domain.aggregates.customer_video_series

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.*
import jakarta.persistence.CascadeType
import jakarta.persistence.Table

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 用户视频序列归档;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/10/30
 */
@Aggregate(aggregate = "CustomerVideoSeries", name = "CustomerVideoSeries", root = true, type = Aggregate.TYPE_ENTITY, description = "用户视频序列归档，")
@Entity
@Table(name = "`customer_video_series`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `customer_video_series` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class CustomerVideoSeries(
    id: Long = 0L,
    customerId: Long = 0L,
    seriesName: String = "",
    seriesDescription: String? = null,
    sort: Byte = 0,
    createUserId: Long? = null,
    createBy: String? = null,
    createTime: Long? = null,
    updateUserId: Long? = null,
    updateBy: String? = null,
    updateTime: Long? = null,
    deleted: Long = 0L
) {
    // 【字段映射开始】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "`series_id`", nullable = false)
    var customerVideoSeriesVideos: MutableList<CustomerVideoSeriesVideo> = mutableListOf()

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
     * 列表名称
     * varchar(100)
     */
    @Column(name = "`series_name`")
    var seriesName: String = seriesName
        internal set

    /**
     * 描述
     * varchar(200)
     */
    @Column(name = "`series_description`")
    var seriesDescription: String? = seriesDescription
        internal set

    /**
     * 排序
     * tinyint
     */
    @Column(name = "`sort`")
    var sort: Byte = sort
        internal set

    /**
     * 创建人ID
     * bigint
     */
    @Column(name = "`create_user_id`")
    var createUserId: Long? = createUserId
        internal set

    /**
     * 创建人名称
     * varchar(32)
     */
    @Column(name = "`create_by`")
    var createBy: String? = createBy
        internal set

    /**
     * 创建时间
     * bigint
     */
    @Column(name = "`create_time`")
    var createTime: Long? = createTime
        internal set

    /**
     * 更新人ID
     * bigint
     */
    @Column(name = "`update_user_id`")
    var updateUserId: Long? = updateUserId
        internal set

    /**
     * 更新人名称
     * varchar(32)
     */
    @Column(name = "`update_by`")
    var updateBy: String? = updateBy
        internal set

    /**
     * 更新时间
     * bigint
     */
    @Column(name = "`update_time`")
    var updateTime: Long? = updateTime
        internal set

    /**
     * 删除标识 0：未删除 id：已删除
     * bigint
     */
    @Column(name = "`deleted`")
    var deleted: Long = deleted
        internal set

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    fun updateBasicInfo(
        newName: String,
        newDescription: String?,
    ) {
        seriesName = newName
        seriesDescription = newDescription
    }

    fun replaceVideos(
        ownerId: Long,
        videoIds: List<Long>,
    ) {
        customerVideoSeriesVideos.clear()
        videoIds.forEachIndexed { index, videoId ->
            customerVideoSeriesVideos.add(
                CustomerVideoSeriesVideo(
                    customerId = ownerId,
                    videoId = videoId,
                    sort = (index + 1).toByte()
                )
            )
        }
    }

    fun removeVideo(videoId: Long): Boolean {
        val removed = customerVideoSeriesVideos.removeIf { it.videoId == videoId }
        if (removed) {
            customerVideoSeriesVideos.sortBy { it.sort }
            customerVideoSeriesVideos.forEachIndexed { index, video ->
                video.sort = (index + 1).toByte()
            }
        }
        return removed
    }

    // 【行为方法开始】

    /**
     * 更新系列排序值
     * @param newSort 新的排序值
     */
    fun updateSort(newSort: Byte) {
        this.sort = newSort
    }

    // 【行为方法结束】
}
