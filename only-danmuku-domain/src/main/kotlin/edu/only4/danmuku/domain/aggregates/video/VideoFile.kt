package edu.only4.danmuku.domain.aggregates.video

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity

import jakarta.persistence.*

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 视频文件信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2026/01/14
 */
@Aggregate(aggregate = "Video", name = "VideoFile", root = false, type = Aggregate.TYPE_ENTITY, description = "视频文件信息，")
@Entity
@Table(name = "`video_file`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `video_file` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class VideoFile(
    id: Long = 0L,
    customerId: Long = 0L,
    videoFilePostId: Long = 0L,
    fileName: String? = null,
    fileIndex: Int = 0,
    fileSize: Long? = null,
    filePath: String? = null,
    duration: Int? = null,
) : AuditedFieldsEntity() {
    // 【字段映射开始】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动
    @ManyToOne(cascade = [], fetch = FetchType.EAGER)
    @JoinColumn(name = "`video_id`", nullable = false, insertable = false, updatable = false)
    var video: Video? = null
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "`parent_id`", nullable = false)
    var videoFileVariants: MutableList<VideoFileVariant> = mutableListOf()

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
     * 视频文件草稿ID
     * bigint
     */
    @Column(name = "`video_file_post_id`")
    var videoFilePostId: Long = videoFilePostId
        internal set

    /**
     * 文件名
     * varchar(200)
     */
    @Column(name = "`file_name`")
    var fileName: String? = fileName
        internal set

    /**
     * 文件索引
     * int
     */
    @Column(name = "`file_index`")
    var fileIndex: Int = fileIndex
        internal set

    /**
     * 文件大小
     * bigint
     */
    @Column(name = "`file_size`")
    var fileSize: Long? = fileSize
        internal set

    /**
     * 文件路径
     * varchar(100)
     */
    @Column(name = "`file_path`")
    var filePath: String? = filePath
        internal set

    /**
     * 持续时间（秒）
     * int
     */
    @Column(name = "`duration`")
    var duration: Int? = duration
        internal set

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】

    // 【行为方法结束】
}
