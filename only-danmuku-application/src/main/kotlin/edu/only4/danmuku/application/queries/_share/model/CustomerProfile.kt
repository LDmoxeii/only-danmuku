package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table


@Entity
@Table(name = "customer_profile")
interface CustomerProfile : BaseEntity {

    @IdView
    val userId: Long

    @OneToOne
    @JoinColumn(name = "user_id")
    val user: User

    @Column(name = "nick_name")
    val nickName: String

    @Column(name = "avatar")
    val avatar: String?

    @Column(name = "email")
    val email: String

    /**
     * 性别 @E=0:UNKNOWN:未知|1:FEMALE:女|2:MALE:男|;@T=SexType
     */
    @Column(name = "sex")
    val sex: Int

    @Column(name = "birthday")
    val birthday: String?

    @Column(name = "school")
    val school: String?

    @Column(name = "person_introduction")
    val personIntroduction: String?

    @Column(name = "notice_info")
    val noticeInfo: String?

    @Column(name = "total_coin_count")
    val totalCoinCount: Int

    @Column(name = "current_coin_count")
    val currentCoinCount: Int

    /**
     * 主题 @E=0:UNKNOW:未知主题|1:LIGHT:浅色主题|2:DARK:深色主题|3:SYSTEM:跟随系统;@T=ThemeType
     */
    @Column(name = "theme")
    val theme: Int
}
