package edu.only4.danmuku.domain.aggregates.customer_action

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.DomainEventSupervisorSupport.events

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity
import edu.only4.danmuku.domain.aggregates.customer_action.enums.ActionType
import edu.only4.danmuku.domain.aggregates.customer_action.enums.ActionType.*
import edu.only4.danmuku.domain.aggregates.customer_action.events.CustomerCoinGivenDomainEvent
import edu.only4.danmuku.domain.aggregates.customer_action.events.CustomerCollectedVideoDomainEvent
import edu.only4.danmuku.domain.aggregates.customer_action.events.CustomerDislikedCommentDomainEvent
import edu.only4.danmuku.domain.aggregates.customer_action.events.CustomerLikedCommentDomainEvent
import edu.only4.danmuku.domain.aggregates.customer_action.events.CustomerLikedVideoDomainEvent
import edu.only4.danmuku.domain.aggregates.customer_action.events.CustomerUncollectedVideoDomainEvent
import edu.only4.danmuku.domain.aggregates.customer_action.events.CustomerUndislikedCommentDomainEvent
import edu.only4.danmuku.domain.aggregates.customer_action.events.CustomerUnlikedCommentDomainEvent
import edu.only4.danmuku.domain.aggregates.customer_action.events.CustomerUnlikedVideoDomainEvent

import jakarta.persistence.*
import jakarta.persistence.Table

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 用户行为 点赞、评论;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/11/24
 */
@Aggregate(aggregate = "CustomerAction", name = "CustomerAction", root = true, type = Aggregate.TYPE_ENTITY, description = "用户行为 点赞、评论，")
@Entity
@Table(name = "`customer_action`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `customer_action` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class CustomerAction(
    id: Long = 0L,
    customerId: Long = 0L,
    videoId: Long = 0L,
    videoOwnerId: Long = 0L,
    commentId: Long? = null,
    actionType: ActionType = ActionType.valueOf(0),
    actionCount: Int = 0,
    actionTime: Long = 0L,
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
     * 视频ID
     * bigint
     */
    @Column(name = "`video_id`")
    var videoId: Long = videoId
        internal set

    /**
     * 视频用户ID
     * bigint
     */
    @Column(name = "`video_owner_id`")
    var videoOwnerId: Long = videoOwnerId
        internal set

    /**
     * 评论ID
     * bigint
     */
    @Column(name = "`comment_id`")
    var commentId: Long? = commentId
        internal set

    /**
     * 行为类型
     * 0:UNKNOW:未知行为
     * 1:LIKE_COMMENT:评论喜欢点赞
     * 2:HATE_COMMENT:讨厌评论
     * 3:LIKE_VIDEO:视频点赞
     * 4:FAVORITE_VIDEO:视频收藏
     * 5:COIN_VIDEO:视频投币
     * tinyint(1)
     */
    @Convert(converter = ActionType.Converter::class)
    @Column(name = "`action_type`")
    var actionType: ActionType = actionType
        internal set

    /**
     * 数量
     * int
     */
    @Column(name = "`action_count`")
    var actionCount: Int = actionCount
        internal set

    /**
     * 操作时间
     * bigint
     */
    @Column(name = "`action_time`")
    var actionTime: Long = actionTime
        internal set

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】

    fun onCreate() {
        when(actionType) {
            UNKNOW -> Unit

            LIKE_VIDEO -> events().attach(this) { CustomerLikedVideoDomainEvent(this) }

            LIKE_COMMENT -> events().attach(this) { CustomerLikedCommentDomainEvent(this) }

            HATE_COMMENT -> events().attach(this) { CustomerDislikedCommentDomainEvent(this) }

            FAVORITE_VIDEO -> events().attach(this) { CustomerCollectedVideoDomainEvent(this) }

            COIN_VIDEO -> events().attach(this) { CustomerCoinGivenDomainEvent(this) }
        }
    }

    fun onDelete() {
        when (actionType) {
            UNKNOW -> Unit

            LIKE_COMMENT -> events().attach(this) { CustomerUnlikedCommentDomainEvent(this) }

            HATE_COMMENT -> events().attach(this) { CustomerUndislikedCommentDomainEvent(this) }

            LIKE_VIDEO -> events().attach(this) { CustomerUnlikedVideoDomainEvent(this) }

            FAVORITE_VIDEO -> events().attach(this) { CustomerUncollectedVideoDomainEvent(this) }

            COIN_VIDEO -> Unit
        }
    }

    // 【行为方法结束】
}
