package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "customer_focus")
interface CustomerFocus : BaseEntity {

    @IdView
    val customerId: Long

    @IdView
    val focusCustomerId: Long

    @OneToOne
    @JoinColumn(name = "customer_id")
    val customer: User

    @OneToOne
    @JoinColumn(name = "focus_customer_id")
    val focusCustomer: User
}
