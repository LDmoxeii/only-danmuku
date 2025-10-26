package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.MappedSuperclass

@MappedSuperclass
interface BaseEntity {
    @Id
    val id: Long

    val createBy: String?

    val createTime: Long?

    val updateUserId: Long?

    val updateBy: String?

    val updateTime: Long?

    @LogicalDeleted
    val deleted: Long
}
