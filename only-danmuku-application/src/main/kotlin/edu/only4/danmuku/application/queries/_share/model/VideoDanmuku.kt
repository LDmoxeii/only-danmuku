package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

/**
 * 视频弹幕;@Spe;@Fac;
 */
@Entity
@Table(name = "video_danmuku")
interface VideoDanmuku : BaseEntity {

    @OneToOne
    @JoinColumn(name = "video_id")
    val video: Video

    @OneToOne
    @JoinColumn(name = "customer_id")
    val customer: User

    /**
     * 发布时间
     */
    @Column(name = "post_time")
    val postTime: Long?

    /**
     * 内容
     */
    @Column(name = "text")
    val text: String?

    /**
     * 展示位置
     */
    @Column(name = "mode")
    val mode: String?

    /**
     * 颜色
     */
    @Column(name = "color")
    val color: String?

    /**
     * 展示时间
     */
    @Column(name = "time")
    val time: String?
}
