package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "user")
interface User : BaseEntity {

    @OneToOne
    @JoinColumn(
        name = "related_id",
        foreignKeyType = ForeignKeyType.FAKE
    )
    val relation: CustomerProfile?

    /**
     * 帐号类型 @E=0:UNKNOW:未知类型|1:SYS_USER:系统管理员;@T=UserType
     */
    @Column(name = "type")
    val type: Int

    @Column(name = "nick_name")
    val nickName: String

    @Column(name = "email")
    val email: String

    @Column(name = "password")
    val password: String

    @Column(name = "join_time")
    val joinTime: Long

    @Column(name = "last_login_time")
    val lastLoginTime: Long?

    @Column(name = "last_login_ip")
    val lastLoginIp: String?

    /**
     * 0:禁用 1:正常
     */
    @Column(name = "status")
    val status: Int
}
