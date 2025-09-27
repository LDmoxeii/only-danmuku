package com.edu.only4.danmuku.domain.aggregates.statistics_info

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.*
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
 * @date 2025/09/27
 */
@Aggregate(aggregate = "StatisticsInfo", name = "StatisticsInfo", root = true, type = Aggregate.TYPE_ENTITY, description = "统计信息;")
@Entity
@Table(name = "`statistics_info`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `statistics_info` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class StatisticsInfo (
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
     * 0:UNKNOW:未知类型
     * 1:VIDEO_VIEW:视频观看
     * 2:VIDEO_LIKE:视频点赞
     * 3:VIDEO_COMMENT:视频评论
     * 4:VIDEO_SHARE:视频分享
     * 5:USER_FOLLOW:用户关注
     * 6:USER_LOGIN:用户登录
     * tinyint(1)
     */
    @Convert(converter = com.edu.only4.danmuku.domain.aggregates.statistics_info.enums.StatisticsDataType.Converter::class)
    @Column(name = "`data_type`")
    var dataType: com.edu.only4.danmuku.domain.aggregates.statistics_info.enums.StatisticsDataType = com.edu.only4.danmuku.domain.aggregates.statistics_info.enums.StatisticsDataType.valueOf(0),

    /**
     * 统计数量
     * int
     */
    @Column(name = "`statistics_count`")
    var statisticsCount: Int? = null,

    /**
     * 统计日期
     * bigint
     */
    @Column(name = "`statistics_date`")
    var statisticsDate: Long = 0L,

    /**
     * 删除标识 0：未删除 id：已删除
     * tinyint(1)
     */
    @Column(name = "`deleted`")
    var deleted: Boolean = false,
) {

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】



    // 【行为方法结束】

}
