package edu.only4.danmuku.domain.aggregates.video_danmuku

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import jakarta.persistence.*
import jakarta.persistence.Table
import org.hibernate.annotations.*

/**
 * 视频弹幕;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/10/20
 */
@Aggregate(aggregate = "VideoDanmuku", name = "VideoDanmuku", root = true, type = Aggregate.TYPE_ENTITY, description = "视频弹幕，")
@Entity
@Table(name = "`video_danmuku`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `video_danmuku` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class VideoDanmuku (
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
     * 视频ID
     * bigint
     */
    @Column(name = "`video_id`")
    var videoId: Long = 0L,

    /**
     * 唯一ID
     * bigint
     */
    @Column(name = "`file_id`")
    var fileId: Long = 0L,

    /**
     * 用户ID
     * bigint
     */
    @Column(name = "`customer_id`")
    var customerId: Long = 0L,

    /**
     * 发布时间
     * bigint
     */
    @Column(name = "`post_time`")
    var postTime: Long? = null,

    /**
     * 内容
     * varchar(300)
     */
    @Column(name = "`text`")
    var text: String? = null,

    /**
     * 展示位置
     * tinyint(1)
     */
    @Column(name = "`mode`")
    var mode: Boolean? = null,

    /**
     * 颜色
     * varchar(10)
     */
    @Column(name = "`color`")
    var color: String? = null,

    /**
     * 展示时间
     * int
     */
    @Column(name = "`time`")
    var time: Int? = null,

    /**
     * 创建人ID
     * bigint
     */
    @Column(name = "`create_user_id`")
    var createUserId: Long? = null,

    /**
     * 创建人名称
     * varchar(32)
     */
    @Column(name = "`create_by`")
    var createBy: String? = null,

    /**
     * 创建时间
     * bigint
     */
    @Column(name = "`create_time`")
    var createTime: Long? = null,

    /**
     * 更新人ID
     * bigint
     */
    @Column(name = "`update_user_id`")
    var updateUserId: Long? = null,

    /**
     * 更新人名称
     * varchar(32)
     */
    @Column(name = "`update_by`")
    var updateBy: String? = null,

    /**
     * 更新时间
     * bigint
     */
    @Column(name = "`update_time`")
    var updateTime: Long? = null,

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
