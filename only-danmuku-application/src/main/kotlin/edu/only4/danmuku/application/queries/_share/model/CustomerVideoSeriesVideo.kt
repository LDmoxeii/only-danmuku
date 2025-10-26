package edu.only4.danmuku.application.queries._share.model

import edu.only4.danmuku.application.queries._share.model.User
import edu.only4.danmuku.application.queries._share.model.Video
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

/**
 * 用户视频序列视频关联;@P=customer_video_series;
 */
@Entity
@Table(name = "customer_video_series_video")
interface CustomerVideoSeriesVideo : BaseEntity {

    @OneToOne
    @JoinColumn(name = "customer_id")
    val customer: User

    @OneToOne
    @JoinColumn(name = "series_id")
    val videoSeries: CustomerVideoSeries

    @OneToOne
    @JoinColumn(name = "video_id")
    val video: Video

    /**
     * 排序
     */
    @Column(name = "sort")
    val sort: Int
}
