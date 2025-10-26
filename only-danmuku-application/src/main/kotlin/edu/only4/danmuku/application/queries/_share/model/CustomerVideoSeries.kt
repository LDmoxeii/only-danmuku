package edu.only4.danmuku.application.queries._share.model

import edu.only4.danmuku.application.queries._share.model.User
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

/**
 * 用户视频序列归档;@Spe;@Fac;
 */
@Entity
@Table(name = "customer_video_series")
interface CustomerVideoSeries : BaseEntity {

    @OneToOne
    @JoinColumn(name = "customer_id")
    val customer: User

    /**
     * 列表名称
     */
    @Column(name = "series_name")
    val seriesName: String

    /**
     * 描述
     */
    @Column(name = "series_description")
    val seriesDescription: String?

    /**
     * 排序
     */
    @Column(name = "sort")
    val sort: Int
}
