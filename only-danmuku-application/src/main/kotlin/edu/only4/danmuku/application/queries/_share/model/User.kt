package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Table

/**
 * 帐号;@Spe;@Fac;
 */
@Entity
@Table(name = "user")
interface User : BaseEntity {
    /**
     * 帐号类型 @E=0:UNKNOW:未知类型|1:SYS_USER:系统管理员;@T=UserType
     */
    @Column(name = "type")
    val type: String

    /**
     * 昵称
     */
    @Column(name = "nick_name")
    val nickName: String

    /**
     * 邮箱
     */
    @Column(name = "email")
    val email: String

    /**
     * 密码
     */
    @Column(name = "password")
    val password: String

    /**
     * 加入时间
     */
    @Column(name = "join_time")
    val joinTime: Long

    /**
     * 最后登录时间
     */
    @Column(name = "last_login_time")
    val lastLoginTime: Long?

    /**
     * 最后登录IP
     */
    @Column(name = "last_login_ip")
    val lastLoginIp: String?

    /**
     * 0:禁用 1:正常
     */
    @Column(name = "status")
    val status: String
}
