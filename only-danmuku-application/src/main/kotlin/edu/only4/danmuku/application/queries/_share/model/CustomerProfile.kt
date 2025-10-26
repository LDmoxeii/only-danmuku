package edu.only4.danmuku.application.queries._share.model

import edu.only4.danmuku.application.queries._share.model.User
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

/**
 * 用户信息;@Spe;@Fac;
 */
@Entity
@Table(name = "customer_profile")
interface CustomerProfile : BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id")
    val customer: User

    /**
     * 昵称
     */
    @Column(name = "nick_name")
    val nickName: String

    /**
     * 头像
     */
    @Column(name = "avatar")
    val avatar: String?

    /**
     * 邮箱
     */
    @Column(name = "email")
    val email: String

    /**
     * 性别 @E=0:UNKNOWN:未知|1:FEMALE:女|2:MALE:男|;@T=SexType
     */
    @Column(name = "sex")
    val sex: String

    /**
     * 出生日期
     */
    @Column(name = "birthday")
    val birthday: String?

    /**
     * 学校
     */
    @Column(name = "school")
    val school: String?

    /**
     * 个人简介
     */
    @Column(name = "person_introduction")
    val personIntroduction: String?

    /**
     * 空间公告
     */
    @Column(name = "notice_info")
    val noticeInfo: String?

    /**
     * 硬币总数量
     */
    @Column(name = "total_coin_count")
    val totalCoinCount: String

    /**
     * 当前硬币数
     */
    @Column(name = "current_coin_count")
    val currentCoinCount: String

    /**
     * 主题 @E=0:UNKNOW:未知主题|1:LIGHT:浅色主题|2:DARK:深色主题|3:SYSTEM:跟随系统;@T=ThemeType
     */
    @Column(name = "theme")
    val theme: String
}
