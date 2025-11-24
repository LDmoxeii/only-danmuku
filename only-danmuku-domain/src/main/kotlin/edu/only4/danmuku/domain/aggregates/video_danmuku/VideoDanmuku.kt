package edu.only4.danmuku.domain.aggregates.video_danmuku

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.DomainEventSupervisorSupport.events

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity
import edu.only4.danmuku.domain.aggregates.video_danmuku.events.DanmukuDeletedDomainEvent
import edu.only4.danmuku.domain.aggregates.video_danmuku.events.DanmukuPostedDomainEvent

import jakarta.persistence.*
import jakarta.persistence.Table

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 视频弹幕;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/11/24
 */
@Aggregate(aggregate = "VideoDanmuku", name = "VideoDanmuku", root = true, type = Aggregate.TYPE_ENTITY, description = "视频弹幕，")
@Entity
@Table(name = "`video_danmuku`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `video_danmuku` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class VideoDanmuku(
    id: Long = 0L,
    videoId: Long = 0L,
    fileId: Long = 0L,
    customerId: Long = 0L,
    postTime: Long? = null,
    text: String? = null,
    mode: Boolean? = null,
    color: String? = null,
    time: Int? = null,
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
     * 视频ID
     * bigint
     */
    @Column(name = "`video_id`")
    var videoId: Long = videoId
        internal set

    /**
     * 唯一ID
     * bigint
     */
    @Column(name = "`file_id`")
    var fileId: Long = fileId
        internal set

    /**
     * 用户ID
     * bigint
     */
    @Column(name = "`customer_id`")
    var customerId: Long = customerId
        internal set

    /**
     * 发布时间
     * bigint
     */
    @Column(name = "`post_time`")
    var postTime: Long? = postTime
        internal set

    /**
     * 内容
     * varchar(300)
     */
    @Column(name = "`text`")
    var text: String? = text
        internal set

    /**
     * 展示位置
     * tinyint(1)
     */
    @Column(name = "`mode`")
    var mode: Boolean? = mode
        internal set

    /**
     * 颜色
     * varchar(10)
     */
    @Column(name = "`color`")
    var color: String? = color
        internal set

    /**
     * 展示时间
     * int
     */
    @Column(name = "`time`")
    var time: Int? = time
        internal set

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】
    fun onCreate() {
        events().attach(this) { DanmukuPostedDomainEvent(this) }
    }

    fun onDelete() {
        events().attach(this) { DanmukuDeletedDomainEvent(this) }
    }

    // 【行为方法结束】
}
