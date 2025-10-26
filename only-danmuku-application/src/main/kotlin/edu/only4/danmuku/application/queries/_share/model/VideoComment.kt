package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.ForeignKeyType
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

@Table(name = "video_comment")
@Entity
interface VideoComment : BaseEntity {

    @IdView
    val videoOwnerId: Long

    @IdView
    val videoId: Long

    @IdView
    val customerId: Long

    @IdView
    val replyCustomerId: Long?

    @IdView
    val parentId: Long?

    @OneToOne
    @JoinColumn(name = "video_owner_id")
    val videoOwner: User

    @OneToOne
    @JoinColumn(name = "video_id")
    val video: Video

    @OneToOne
    @JoinColumn(name = "customer_id")
    val customer: User

    @OneToOne
    @JoinColumn(name = "reply_customer_id")
    val replyCustomer: User?

    @OneToOne
    @JoinColumn(
        name = "parent_id",
        foreignKeyType = ForeignKeyType.FAKE
    )
    val parent: VideoComment?

    val content: String?

    val imgPath: String?

    val topType: Int?

    val postTime: Long

    val likeCount: Int?

    val hateCount: Int?
}
