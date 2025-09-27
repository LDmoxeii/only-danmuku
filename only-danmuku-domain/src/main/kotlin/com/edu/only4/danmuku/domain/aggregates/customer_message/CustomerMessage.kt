package com.edu.only4.danmuku.domain.aggregates.customer_message

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 用户消息表;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/09/27
 */
@Aggregate(aggregate = "CustomerMessage", name = "CustomerMessage", root = true, type = Aggregate.TYPE_ENTITY, description = "用户消息表;")
@Entity
@Table(name = "`customer_message`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `customer_message` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class CustomerMessage (
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
     * varchar(10)
     */
    @Column(name = "`customer_id`")
    var customerId: String = "",

    /**
     * 主体ID
     * varchar(10)
     */
    @Column(name = "`video_id`")
    var videoId: String? = null,

    /**
     * 消息类型
     * 0:UNKNOW:未知消息
     * 1:SYSTEM_MESSAGE:系统消息
     * 2:COMMENT_REPLY:评论回复
     * 3:VIDEO_DYNAMIC:视频动态
     * 4:PRIVATE_MESSAGE:私信消息
     * 5:ACTIVITY_NOTICE:活动通知
     * 6:OTHER_MESSAGE:其他消息
     * tinyint(1)
     */
    @Convert(converter = com.edu.only4.danmuku.domain.aggregates.customer_message.enums.MessageType.Converter::class)
    @Column(name = "`message_type`")
    var messageType: com.edu.only4.danmuku.domain.aggregates.customer_message.enums.MessageType = com.edu.only4.danmuku.domain.aggregates.customer_message.enums.MessageType.valueOf(0),

    /**
     * 发送主体ID
     * varchar(10)
     */
    @Column(name = "`send_subject_id`")
    var sendSubjectId: String? = null,

    /**
     * 读取状态
     * 0:UNKNOW:未知状态
     * 1:UNREAD:未读
     * 2:READ:已读
     * tinyint(1)
     */
    @Convert(converter = com.edu.only4.danmuku.domain.aggregates.customer_message.enums.ReadType.Converter::class)
    @Column(name = "`read_type`")
    var readType: com.edu.only4.danmuku.domain.aggregates.customer_message.enums.ReadType = com.edu.only4.danmuku.domain.aggregates.customer_message.enums.ReadType.valueOf(0),

    /**
     * 扩展信息
     * text
     */
    @Column(name = "`extend_json`")
    var extendJson: String? = null,

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
