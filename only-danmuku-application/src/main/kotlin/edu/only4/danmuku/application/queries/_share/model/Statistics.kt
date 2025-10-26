package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "statistics")
interface Statistics : BaseEntity {

    @OneToOne
    @JoinColumn(name = "customer_id")
    val customer: User

    /**
     * 数据统计类型 @E=0:PLAY:播放量|1:FANS:粉丝|2:LIKE:点赞|3:COLLECTION:收藏|4:COIN:投币|5:COMMENT:评论|6:DANMU:弹幕;@T=StatisticsDataType
     */
    @Column(name = "data_type")
    val dataType: Int

    @Column(name = "statistics_count")
    val statisticsCount: Int?

    @Column(name = "statistics_date")
    val statisticsDate: Long
}
