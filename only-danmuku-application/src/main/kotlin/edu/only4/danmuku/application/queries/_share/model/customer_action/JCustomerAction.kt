package edu.only4.danmuku.application.queries._share.model.customer_action

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.Table

/**
 * Jimmer 用户行为实体
 * 用于 CQRS 读模型查询
 */
@Table(name = "customer_action")
@Entity
interface JCustomerAction {

    /**
     * ID
     */
    @Id
    val id: Long

    /**
     * 用户ID
     */
    val customerId: Long

    /**
     * 视频ID
     */
    val videoId: Long

    /**
     * 视频所有者ID
     */
    val videoOwnerId: Long

    /**
     * 评论ID
     */
    val commentId: Long

    /**
     * 行为类型
     * 0:UNKNOW:未知行为
     * 1:LIKE_COMMENT:评论喜欢点赞
     * 2:HATE_COMMENT:讨厌评论
     * 3:LIKE_VIDEO:视频点赞
     * 4:FAVORITE_VIDEO:视频收藏
     * 5:COIN_VIDEO:视频投币
     */
    val actionType: Byte

    /**
     * 数量
     */
    val actionCount: Int

    /**
     * 操作时间
     */
    val actionTime: Long

    /**
     * 逻辑删除标识（0：未删除，非0：已删除）
     */
    @LogicalDeleted
    val deleted: Long
}
