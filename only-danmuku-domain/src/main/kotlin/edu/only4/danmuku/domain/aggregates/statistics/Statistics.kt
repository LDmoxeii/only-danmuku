package edu.only4.danmuku.domain.aggregates.statistics

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.statistics.enums.StatisticsDataType

import jakarta.persistence.*
import jakarta.persistence.Table

import org.hibernate.annotations.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 统计信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/10/26
 */
@Aggregate(aggregate = "Statistics", name = "Statistics", root = true, type = Aggregate.TYPE_ENTITY, description = "统计信息，")
@Entity
@Table(name = "`statistics`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `statistics` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class Statistics (
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
     * 数据统计类型
     * 0:PLAY:播放量
     * 1:FANS:粉丝
     * 2:LIKE:点赞
     * 3:COLLECTION:收藏
     * 4:COIN:投币
     * 5:COMMENT:评论
     * 6:DANMU:弹幕
     * tinyint(1)
     */
    @Convert(converter = StatisticsDataType.Converter::class)
    @Column(name = "`data_type`")
    var dataType: StatisticsDataType = StatisticsDataType.valueOf(0),

    /**
     * 统计数量
     * int
     */
    @Column(name = "`statistics_count`")
    var statisticsCount: Int? = null,

    /**
     * 统计日期（秒级时间戳）
     * bigint
     */
    @Column(name = "`statistics_date`")
    var statisticsDate: Long = 0L,

    /**
     * 创建人ID
     * bigint
     */
    @Column(name = "`create_user_id`")
    var createUserId: Long? = null,

    /**
     * 创建人名称
     * varchar(32)
     */
    @Column(name = "`create_by`")
    var createBy: String? = null,

    /**
     * 创建时间（秒级时间戳）
     * bigint
     */
    @Column(name = "`create_time`")
    var createTime: Long? = null,

    /**
     * 更新人ID
     * bigint
     */
    @Column(name = "`update_user_id`")
    var updateUserId: Long? = null,

    /**
     * 更新人名称
     * varchar(32)
     */
    @Column(name = "`update_by`")
    var updateBy: String? = null,

    /**
     * 更新时间（秒级时间戳）
     * bigint
     */
    @Column(name = "`update_time`")
    var updateTime: Long? = null,

    /**
     * 删除标识 0：未删除 id：已删除
     * bigint
     */
    @Column(name = "`deleted`")
    var deleted: Long = 0L,
) {

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
