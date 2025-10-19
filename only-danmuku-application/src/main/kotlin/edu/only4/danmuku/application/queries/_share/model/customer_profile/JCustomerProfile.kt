package edu.only4.danmuku.application.queries._share.model.customer_profile

import edu.only4.danmuku.application.queries._share.model.user.JUser
import org.babyfish.jimmer.sql.*

/**
 * Jimmer 用户信息实体
 * 用于 CQRS 读模型查询
 */
@Table(name = "customer_profile")
@Entity
interface JCustomerProfile {

    /**
     * ID
     */
    @Id
    val id: Long

    /**
     * 昵称
     */
    val nickName: String

    /**
     * 头像
     */
    val avatar: String?

    /**
     * 邮箱
     */
    val email: String

    /**
     * 性别
     */
    val sex: Byte

    /**
     * 出生日期
     */
    val birthday: String?

    /**
     * 学校
     */
    val school: String?

    /**
     * 个人简介
     */
    val personIntroduction: String?

    /**
     * 空间公告
     */
    val noticeInfo: String?

    /**
     * 硬币总数量
     */
    val totalCoinCount: Int

    /**
     * 当前硬币数
     */
    val currentCoinCount: Int

    /**
     * 主题 (0:未知 1:浅色 2:深色 3:跟随系统)
     */
    val theme: Byte

    /**
     * 关联的用户账号
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: JUser

    /**
     * 用户ID
     */
    @IdView("user")
    val userId: Long

    /**
     * 逻辑删除标识
     */
    @LogicalDeleted("true")
    val deleted: Boolean
}
