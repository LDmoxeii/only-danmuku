package edu.only4.danmuku.domain.aggregates.statistics

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity
import edu.only4.danmuku.domain.aggregates.statistics.enums.StatisticsDataType

import jakarta.persistence.*
import jakarta.persistence.Table

import org.hibernate.annotations.*

/**
 * 统计信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/11/03
 */
@Aggregate(aggregate = "Statistics", name = "Statistics", root = true, type = Aggregate.TYPE_ENTITY, description = "统计信息，")
@Entity
@Table(name = "`statistics`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `statistics` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class Statistics(
    id: Long = 0L,
    customerId: Long = 0L,
    dataType: StatisticsDataType = StatisticsDataType.valueOf(0),
    statisticsCount: Int? = null,
    statisticsDate: Long = 0L,
    deleted: Long = 0L
) : AuditedFieldsEntity() {
    // 【字段映射开始】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

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
     * 数据统计类型
     * 0:UNKNOW:未知类型
     * 1:PLAY:播放量
     * 2:FANS:粉丝
     * 3:LIKE:点赞
     * 4:COLLECTION:收藏
     * 5:COIN:投币
     * 6:COMMENT:评论
     * 7:DANMUKU:弹幕
     * tinyint(1)
     */
    @Convert(converter = StatisticsDataType.Converter::class)
    @Column(name = "`data_type`")
    var dataType: StatisticsDataType = dataType
        internal set

    /**
     * 统计数量
     * int
     */
    @Column(name = "`statistics_count`")
    var statisticsCount: Int? = statisticsCount
        internal set

    /**
     * 统计日期（秒级时间戳）
     * bigint
     */
    @Column(name = "`statistics_date`")
    var statisticsDate: Long = statisticsDate
        internal set

    /**
     * 删除标识 0：未删除 id：已删除
     * bigint
     */
    @Column(name = "`deleted`")
    var deleted: Long = deleted
        internal set

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】

    /**
     * 更新统计数量
     * @param delta 增量（正数表示增加，负数表示减少）
     */
    fun updateCount(delta: Int) {
        this.statisticsCount = (this.statisticsCount ?: 0) + delta
    }

    // 【行为方法结束】
}
