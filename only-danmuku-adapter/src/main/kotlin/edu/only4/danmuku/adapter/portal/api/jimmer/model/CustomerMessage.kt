package edu.only4.danmuku.adapter.portal.api.jimmer.model

import edu.only4.danmuku.application.queries._share.model.BaseEntity
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Table

/**
 * 用户消息表;@Spe;@Fac;
 */
@Entity
@Table(name = "customer_message")
interface CustomerMessage : BaseEntity {
    /**
     * 消息类型 @E=0:UNKNOW:未知消息|1:SYSTEM_MESSAGE:系统消息|2:COMMENT_REPLY:评论回复|3:VIDEO_DYNAMIC:视频动态|4:PRIVATE_MESSAGE:私信消息|5:ACTIVITY_NOTICE:活动通知|6:OTHER_MESSAGE:其他消息;@T=MessageType
     */
    @Column(name = "message_type")
    val messageType: String

    /**
     * 读取状态 @E=0:UNKNOW:未知状态|1:UNREAD:未读|2:READ:已读;@T=ReadType
     */
    @Column(name = "read_type")
    val readType: String

    /**
     * 扩展信息
     */
    @Column(name = "extend_json")
    val extendJson: String?
}
