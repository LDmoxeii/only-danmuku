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
     * 0:UNKNOW:未知类型
     * 1:VIDEO_VIEW:视频观看
     * 2:VIDEO_LIKE:视频点赞
     * 3:VIDEO_COMMENT:视频评论
     * 4:VIDEO_SHARE:视频分享
     * 5:USER_FOLLOW:用户关注
     * 6:USER_LOGIN:用户登录
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
     * 逻辑删除标识
     */
    @LogicalDeleted("true")
    val deleted: Boolean
}
