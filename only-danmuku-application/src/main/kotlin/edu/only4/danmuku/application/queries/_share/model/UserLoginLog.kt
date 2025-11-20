package edu.only4.danmuku.application.queries._share.model

import edu.only4.danmuku.domain.aggregates.user.enums.UserType
import edu.only4.danmuku.domain.aggregates.user_login_log.enums.LoginResult
import edu.only4.danmuku.domain.aggregates.user_login_log.enums.LoginType
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "user_login_log")
interface UserLoginLog : BaseEntity {

    @Column(name = "user_id")
    val userId: Long?

    @Column(name = "user_type")
    val userType: UserType

    @Column(name = "login_name")
    val loginName: String

    @Column(name = "login_type")
    val loginType: LoginType

    @Column(name = "result")
    val result: LoginResult

    @Column(name = "ip")
    val ip: String

    @Column(name = "user_agent")
    val userAgent: String?

    @Column(name = "reason")
    val reason: String?

    @Column(name = "occur_time")
    val occurTime: Long
}
