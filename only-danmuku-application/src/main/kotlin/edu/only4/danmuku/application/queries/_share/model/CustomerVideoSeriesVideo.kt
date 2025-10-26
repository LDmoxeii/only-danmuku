package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "customer_video_series_video")
interface CustomerVideoSeriesVideo : BaseEntity {

    @OneToOne
    @JoinColumn(name = "customer_id")
    val customer: User

    @ManyToOne
    @JoinColumn(name = "series_id")
    val videoSeries: CustomerVideoSeries

    @OneToOne
    @JoinColumn(name = "video_id")
    val video: Video

    @Column(name = "sort")
    val sort: Int
}
