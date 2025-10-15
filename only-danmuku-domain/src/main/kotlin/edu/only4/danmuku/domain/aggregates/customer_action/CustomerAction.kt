package edu.only4.danmuku.domain.aggregates.customer_action

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.customer_action.enums.ActionType

import jakarta.persistence.*

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
 * @date 2025/10/15
 */
@Aggregate(aggregate = "CustomerAction", name = "CustomerAction", root = true, type = Aggregate.TYPE_ENTITY, description = "用户行为 点赞、评论，")
@Entity
@Table(name = "`customer_action`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `customer_action` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class CustomerAction (
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
     * 用户ID
     * varchar(10)
     */
    @Column(name = "`customer_id`")
    var customerId: String = "",

    /**
     * 视频ID
     * varchar(10)
     */
    @Column(name = "`video_id`")
    var videoId: String = "",

    /**
     * 视频用户ID
     * varchar(10)
     */
    @Column(name = "`video_owner_id`")
    var videoOwnerId: String = "",

    /**
     * 评论ID
     * bigint
     */
    @Column(name = "`comment_id`")
    var commentId: Long = 0L,

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
    var actionType: ActionType = ActionType.valueOf(0),

    /**
     * 数量
     * int
     */
    @Column(name = "`action_count`")
    var actionCount: Int = 0,

    /**
     * 操作时间
     * bigint
     */
    @Column(name = "`action_time`")
    var actionTime: Long = 0L,

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
