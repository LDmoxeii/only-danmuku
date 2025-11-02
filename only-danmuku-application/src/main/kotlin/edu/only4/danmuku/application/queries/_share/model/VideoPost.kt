package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "video_post")
interface VideoPost : BaseEntity {

    @IdView
    val customerId: Long

    @IdView
    val parentCategoryId: Long

    @IdView
    val categoryId: Long?

    @OneToOne(mappedBy = "videoPost")
    val video: Video?

    @OneToOne
    @JoinColumn(name = "customer_id")
    val customer: User

    @OneToOne
    @JoinColumn(name = "p_category_id")
    val parentCategory: Category

    @OneToOne
    @JoinColumn(name = "category_id")
    val category: Category?

    @OneToMany(mappedBy = "video")
    val videoFiles : List<VideoFilePost>

    @Column(name = "video_cover")
    val videoCover: String

    @Column(name = "video_name")
    val videoName: String

    /**
     * 视频状态 @E=0:UNKNOW:未知状态|1:TRANSCODING:转码中|2:TRANSCODE_FAILED:转码失败|3:PENDING_REVIEW:待审核|4:REVIEW_PASSED:审核成功|5:REVIEW_FAILED:审核失败;@T=VideoStatus
     */
    @Column(name = "status")
    val status: Int

    /**
     * 投稿类型 @E=0:UNKNOW:未知类型|1:ORIGINAL:自制作|2:REPOST:转载;@T=PostType
     */
    @Column(name = "post_type")
    val postType: Int

    @Column(name = "origin_info")
    val originInfo: String?

    @Column(name = "tags")
    val tags: String?

    @Column(name = "introduction")
    val introduction: String?

    @Column(name = "interaction")
    val interaction: String?

    @Column(name = "duration")
    val duration: Int
}
