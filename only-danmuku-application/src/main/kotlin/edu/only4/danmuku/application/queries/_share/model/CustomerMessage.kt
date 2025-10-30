package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.*

/** 用户消息表 */
@Entity
@Table(name = "customer_message")
interface CustomerMessage : BaseEntity {

    @Column(name = "customer_id")
    val customerId: Long

    @Column(name = "video_id")
    val videoId: Long?

    /** 消息类型，枚举以整型存储 */
    @Column(name = "message_type")
    val messageType: Int

    @Column(name = "send_subject_id")
    val sendSubjectId: Long?

    /** 阅读状态，枚举以整型存储 */
    @Column(name = "read_type")
    val readType: Int

    @Column(name = "extend_json")
    val extendJson: String?
}

