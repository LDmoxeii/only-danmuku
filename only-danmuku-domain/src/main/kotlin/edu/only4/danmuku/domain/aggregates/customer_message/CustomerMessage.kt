package edu.only4.danmuku.domain.aggregates.customer_message

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity
import edu.only4.danmuku.domain.aggregates.customer_message.enums.MessageType
import edu.only4.danmuku.domain.aggregates.customer_message.enums.ReadType
import edu.only4.danmuku.domain.aggregates.customer_message.extend.UserMessageExtend

import jakarta.persistence.*
import jakarta.persistence.Table

import org.hibernate.annotations.*

/**
 * 用户消息表;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/11/04
 */
@Aggregate(aggregate = "CustomerMessage", name = "CustomerMessage", root = true, type = Aggregate.TYPE_ENTITY, description = "用户消息表，")
@Entity
@Table(name = "`customer_message`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `customer_message` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class CustomerMessage(
    id: Long = 0L,
    customerId: Long = 0L,
    videoId: Long? = null,
    messageType: MessageType = MessageType.valueOf(0),
    sendSubjectId: Long? = null,
    readType: ReadType = ReadType.valueOf(0),
    extendJson: UserMessageExtend? = null,
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
     * 主体ID
     * bigint
     */
    @Column(name = "`video_id`")
    var videoId: Long? = videoId
        internal set

    /**
     * 消息类型
     * 0:UNKNOW:未知消息
     * 1:SYSTEM_MESSAGE:系统消息
     * 2:LIKE_MESSAGE:收到的赞
     * 3:COLLECTION_MESSAGE:收到收藏
     * 4:COMMENT_MENTION:评论和@
     * 5:COMMENT_REPLY:评论回复
     * 6:VIDEO_DYNAMIC:视频动态
     * 7:PRIVATE_MESSAGE:私信消息
     * 8:ACTIVITY_NOTICE:活动通知
     * 9:OTHER_MESSAGE:其他消息
     * tinyint(1)
     */
    @Convert(converter = MessageType.Converter::class)
    @Column(name = "`message_type`")
    var messageType: MessageType = messageType
        internal set

    /**
     * 发送主体ID
     * bigint
     */
    @Column(name = "`send_subject_id`")
    var sendSubjectId: Long? = sendSubjectId
        internal set

    /**
     * 读取状态
     * 0:UNKNOW:未知状态
     * 1:UNREAD:未读
     * 2:READ:已读
     * tinyint(1)
     */
    @Convert(converter = ReadType.Converter::class)
    @Column(name = "`read_type`")
    var readType: ReadType = readType
        internal set

    /**
     * 扩展信息
     * text
     */
    @Convert(converter = UserMessageExtend.Converter::class)
    @Column(name = "`extend_json`")
    var extendJson: UserMessageExtend? = extendJson
        internal set

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】
    fun markAsRead(now: Long? = null) {
        if (this.readType == ReadType.READ) return
        this.readType = ReadType.READ
        this.updateTime = now ?: (System.currentTimeMillis() / 1000)
    }

    // 【行为方法结束】
}
