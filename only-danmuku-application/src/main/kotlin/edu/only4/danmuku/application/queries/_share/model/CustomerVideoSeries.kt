package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "customer_video_series")
interface CustomerVideoSeries : BaseEntity {

    @OneToOne
    @JoinColumn(name = "customer_id")
    val customer: User

    @OneToMany(mappedBy = "videoSeries")
    val seriesVideos: List<CustomerVideoSeriesVideo>

    @Column(name = "series_name")
    val seriesName: String

    @Column(name = "series_description")
    val seriesDescription: String?

    @Column(name = "sort")
    val sort: Int
}
