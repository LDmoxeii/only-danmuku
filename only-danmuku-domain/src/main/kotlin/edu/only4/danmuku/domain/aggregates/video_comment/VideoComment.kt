package edu.only4.danmuku.domain.aggregates.video_comment

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import jakarta.persistence.*
import jakarta.persistence.Table
import org.hibernate.annotations.*

/**
 * 评论;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/10/20
 */
@Aggregate(aggregate = "VideoComment", name = "VideoComment", root = true, type = Aggregate.TYPE_ENTITY, description = "评论，")
@Entity
@Table(name = "`video_comment`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `video_comment` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class VideoComment (
    // 【字段映射开始】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    /**
     * 评论ID
     * bigint
     */
    @Id
    @GeneratedValue(generator = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator")
    @GenericGenerator(name = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator", strategy = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator")
    @Column(name = "`id`", insertable = false, updatable = false)
    var id: Long = 0L,

    /**
     * 父级评论ID
     * bigint
     */
    @Column(name = "`parent_id`")
    var parentId: Long = 0L,

    /**
     * 视频ID
     * bigint
     */
    @Column(name = "`video_id`")
    var videoId: Long = 0L,

    /**
     * 视频用户ID
     * bigint
     */
    @Column(name = "`video_owner_id`")
    var videoOwnerId: Long = 0L,

    /**
     * 回复内容
     * varchar(500)
     */
    @Column(name = "`content`")
    var content: String? = null,

    /**
     * 图片
     * varchar(150)
     */
    @Column(name = "`img_path`")
    var imgPath: String? = null,

    /**
     * 用户ID
     * bigint
     */
    @Column(name = "`customer_id`")
    var customerId: Long = 0L,

    /**
     * 回复人ID
     * bigint
     */
    @Column(name = "`reply_customer_id`")
    var replyCustomerId: Long? = null,

    /**
     * 0:未置顶  1:置顶
     * tinyint
     */
    @Column(name = "`top_type`")
    var topType: Byte? = 0,

    /**
     * 发布时间
     * bigint
     */
    @Column(name = "`post_time`")
    var postTime: Long = 0L,

    /**
     * 喜欢数量
     * int
     */
    @Column(name = "`like_count`")
    var likeCount: Int? = 0,

    /**
     * 讨厌数量
     * int
     */
    @Column(name = "`hate_count`")
    var hateCount: Int? = 0,

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
