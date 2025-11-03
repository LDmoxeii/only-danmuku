package edu.only4.danmuku.domain.aggregates.video_comment

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.DomainEventSupervisorSupport.events

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity
import edu.only4.danmuku.domain.aggregates.video_comment.events.CommentDeletedDomainEvent
import edu.only4.danmuku.domain.aggregates.video_comment.events.CommentPostedDomainEvent
import edu.only4.danmuku.domain.aggregates.video_comment.events.CommentToppedDomainEvent
import edu.only4.danmuku.domain.aggregates.video_comment.events.CommentUntoppedDomainEvent

import jakarta.persistence.*
import jakarta.persistence.Table

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 评论;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/11/03
 */
@Aggregate(aggregate = "VideoComment", name = "VideoComment", root = true, type = Aggregate.TYPE_ENTITY, description = "评论，")
@Entity
@Table(name = "`video_comment`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `video_comment` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class VideoComment(
    id: Long = 0L,
    parentId: Long = 0L,
    videoId: Long = 0L,
    videoOwnerId: Long = 0L,
    content: String? = null,
    imgPath: String? = null,
    customerId: Long = 0L,
    replyCustomerId: Long? = null,
    topType: Byte? = 0,
    postTime: Long = 0L,
    likeCount: Int? = 0,
    hateCount: Int? = 0,
) : AuditedFieldsEntity() {
    // 【字段映射开始】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    /**
     * 评论ID
     * bigint
     */
    @Id
    @GeneratedValue(generator = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator")
    @GenericGenerator(name = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator", strategy = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator")
    @Column(name = "`id`", insertable = false, updatable = false)
    var id: Long = id
        internal set

    /**
     * 父级评论ID
     * bigint
     */
    @Column(name = "`parent_id`")
    var parentId: Long = parentId
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
     * 回复内容
     * varchar(500)
     */
    @Column(name = "`content`")
    var content: String? = content
        internal set

    /**
     * 图片
     * varchar(150)
     */
    @Column(name = "`img_path`")
    var imgPath: String? = imgPath
        internal set

    /**
     * 用户ID
     * bigint
     */
    @Column(name = "`customer_id`")
    var customerId: Long = customerId
        internal set

    /**
     * 回复人ID
     * bigint
     */
    @Column(name = "`reply_customer_id`")
    var replyCustomerId: Long? = replyCustomerId
        internal set

    /**
     * 0:未置顶  1:置顶
     * tinyint
     */
    @Column(name = "`top_type`")
    var topType: Byte? = topType
        internal set

    /**
     * 发布时间
     * bigint
     */
    @Column(name = "`post_time`")
    var postTime: Long = postTime
        internal set

    /**
     * 喜欢数量
     * int
     */
    @Column(name = "`like_count`")
    var likeCount: Int? = likeCount
        internal set

    /**
     * 讨厌数量
     * int
     */
    @Column(name = "`hate_count`")
    var hateCount: Int? = hateCount
        internal set

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】

    /** 置顶评论 */
    fun top() {
        if (topType == 1.toByte()) return
        this.topType = 1
        events().attach(this) { CommentToppedDomainEvent(this) }

    }

    /** 取消置顶 */
    fun untop() {
        if (topType == 0.toByte()) return
        this.topType = 0
        events().attach(this) { CommentUntoppedDomainEvent(this) }

    }

    fun onCreate() {
        events().attach(this) { CommentPostedDomainEvent(this) }
    }

    /** 删除钩子：发布评论已删除领域事件 */
    fun onDelete() {
        events().attach(this) { CommentDeletedDomainEvent(this) }
    }

    /** 更新评论统计：like/hate 增量 */
    fun updateStatistics(likeChange: Int, hateChange: Int) {
        this.likeCount = ((this.likeCount ?: 0) + likeChange).coerceAtLeast(0)
        this.hateCount = ((this.hateCount ?: 0) + hateChange).coerceAtLeast(0)
    }

    /** 判断是否为根评论（非回复） */
    fun isRootComment(): Boolean {
        return this.parentId == 0L
    }

    // 【行为方法结束】
}
