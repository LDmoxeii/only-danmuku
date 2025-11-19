package edu.only4.danmuku.domain.aggregates.video_play_history

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.DomainEventSupervisorSupport.events

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity
import edu.only4.danmuku.domain.aggregates.videoplayhistory.events.VideoPlayHistoryCreatedDomainEvent
import edu.only4.danmuku.domain.aggregates.videoplayhistory.events.VideoPlayHistoryProgressUpdatedDomainEvent

import jakarta.persistence.*
import jakarta.persistence.Table

import org.hibernate.annotations.*

/**
 * 视频播放历史;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/11/04
 */
@Aggregate(aggregate = "VideoPlayHistory", name = "VideoPlayHistory", root = true, type = Aggregate.TYPE_ENTITY, description = "视频播放历史，")
@Entity
@Table(name = "`video_play_history`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `video_play_history` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class VideoPlayHistory(
    id: Long = 0L,
    customerId: Long = 0L,
    videoId: Long = 0L,
    fileIndex: Int = 0,
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
     * 文件索引
     * int
     */
    @Column(name = "`file_index`")
    var fileIndex: Int = fileIndex
        internal set

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】

    fun onCreate() {
        events().attach(this) { VideoPlayHistoryCreatedDomainEvent(this) }
    }

    fun updatePlayProgress(newFileIndex: Int, at: Long) {
        this.fileIndex = newFileIndex
        this.updateTime = at

        events().attach(this) { VideoPlayHistoryProgressUpdatedDomainEvent(this) }
    }

    // 【行为方法结束】
}
