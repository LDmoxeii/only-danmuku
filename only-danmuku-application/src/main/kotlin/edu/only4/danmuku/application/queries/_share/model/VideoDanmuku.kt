package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "video_danmuku")
interface VideoDanmuku : BaseEntity {

    @IdView
    val videoId: Long

    @OneToOne
    @JoinColumn(name = "video_id")
    val video: Video

    @OneToOne
    @JoinColumn(name = "customer_id")
    val customer: User

    @Column(name = "post_time")
    val postTime: Long?

    @Column(name = "text")
    val text: String?

    @Column(name = "mode")
    val mode: String?

    @Column(name = "color")
    val color: String?

    @Column(name = "time")
    val time: Int?
}
