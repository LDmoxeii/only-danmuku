package edu.only4.danmuku.application.queries._share.model

import edu.only4.danmuku.domain.aggregates.user.enums.UserType
import edu.only4.danmuku.domain.aggregates.user_abnormal_operation_log.enums.AbnormalOpType
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "user_abnormal_operation_log")
interface UserAbnormalOperationLog : BaseEntity {

    @Column(name = "user_id")
    val userId: Long

    @Column(name = "user_type")
    val userType: UserType

    @Column(name = "op_type")
    val opType: AbnormalOpType

    @Column(name = "ip")
    val ip: String

    @Column(name = "occur_time")
    val occurTime: Long

    @Column(name = "description")
    val description: String?

    @Column(name = "extra")
    val extra: String?
}
