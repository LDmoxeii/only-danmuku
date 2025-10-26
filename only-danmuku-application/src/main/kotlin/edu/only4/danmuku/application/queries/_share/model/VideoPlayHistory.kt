package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.*

@Table(name = "video_play_history")
@Entity
interface VideoPlayHistory : BaseEntity {

    @IdView
    val customerId: Long

    @IdView
    val videoId: Long

    @OneToOne
    @JoinColumn(name = "customer_id")
    val customer: User

    @ManyToOne
    @JoinColumn(name = "video_id")
    val video: Video

    val fileIndex: Int
}
