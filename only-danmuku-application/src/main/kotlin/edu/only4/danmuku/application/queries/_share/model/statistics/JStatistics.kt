package edu.only4.danmuku.application.queries._share.model.statistics

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.Table

/**
 * Jimmer 统计信息实体
 * 用于 CQRS 读模型查询
 */
@Table(name = "statistics")
@Entity
interface JStatistics {

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
     * 数据统计类型
     * 0:PLAY:播放量
     * 1:FANS:粉丝
     * 2:LIKE:点赞
     * 3:COLLECTION:收藏
     * 4:COIN:投币
     * 5:COMMENT:评论
     * 6:DANMU:弹幕
     */
    val dataType: Byte

    /**
     * 统计数量
     */
    val statisticsCount: Int?

    /**
     * 统计日期
     */
    val statisticsDate: Long

    /**
     * 创建人ID
     */
    val createUserId: Long?

    /**
     * 创建人名称
     */
    val createBy: String?

    /**
     * 创建时间
     */
    val createTime: Long?

    /**
     * 更新人ID
     */
    val updateUserId: Long?

    /**
     * 更新人名称
     */
    val updateBy: String?

    /**
     * 更新时间
     */
    val updateTime: Long?

    /**
     * 逻辑删除标识（0：未删除，非0：已删除）
     */
    @LogicalDeleted
    val deleted: Long
}
