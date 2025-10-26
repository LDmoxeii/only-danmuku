package edu.only4.danmuku.application.queries._share.model

import edu.only4.danmuku.application.queries._share.model.User
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

/**
 * 用户关注;@Spe;@Fac;
 */
@Entity
@Table(name = "customer_focus")
interface CustomerFocus : BaseEntity {

    @OneToOne
    @JoinColumn(name = "customer_id")
    val customer: User

    @OneToOne
    @JoinColumn(name = "focus_customer_id")
    val focusCustomer: User
}
