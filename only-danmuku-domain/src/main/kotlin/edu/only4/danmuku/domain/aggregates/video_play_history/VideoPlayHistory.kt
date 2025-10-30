package edu.only4.danmuku.domain.aggregates.video_play_history

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.*
import jakarta.persistence.Table

import org.hibernate.annotations.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 视频播放历史;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/10/30
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
    createUserId: Long? = null,
    createBy: String? = null,
    createTime: Long? = null,
    updateUserId: Long? = null,
    updateBy: String? = null,
    updateTime: Long? = null,
    deleted: Long = 0L
) {
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

    /**
     * 创建人ID
     * bigint
     */
    @Column(name = "`create_user_id`")
    var createUserId: Long? = createUserId
        internal set

    /**
     * 创建人名称
     * varchar(32)
     */
    @Column(name = "`create_by`")
    var createBy: String? = createBy
        internal set

    /**
     * 创建时间
     * bigint
     */
    @Column(name = "`create_time`")
    var createTime: Long? = createTime
        internal set

    /**
     * 更新人ID
     * bigint
     */
    @Column(name = "`update_user_id`")
    var updateUserId: Long? = updateUserId
        internal set

    /**
     * 更新人名称
     * varchar(32)
     */
    @Column(name = "`update_by`")
    var updateBy: String? = updateBy
        internal set

    /**
     * 更新时间
     * bigint
     */
    @Column(name = "`update_time`")
    var updateTime: Long? = updateTime
        internal set

    /**
     * 删除标识 0：未删除 id：已删除
     * bigint
     */
    @Column(name = "`deleted`")
    var deleted: Long = deleted
        internal set

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】

    fun updatePlayProgress(newFileIndex: Int, at: Long) {
        this.fileIndex = newFileIndex
        this.updateTime = at
    }

    // 【行为方法结束】
}
