package edu.only4.danmuku.application.queries._share.model

import edu.only4.danmuku.domain.aggregates.customer_action.enums.ActionType
import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "customer_action")
interface CustomerAction : BaseEntity {

    @OneToOne
    @JoinColumn(name = "customer_id")
    val customer: User

    @OneToOne
    @JoinColumn(name = "video_owner_id")
    val videoOwner: User

    @OneToOne
    @JoinColumn(
        name = "video_id",
        foreignKeyType = ForeignKeyType.FAKE,
    )
    val video: Video?

    @OneToOne
    @JoinColumn(name = "comment_id")
    val comment: VideoComment?

    /**
     * 行为类型
     * 0:UNKNOW:未知行为
     * 1:LIKE_COMMENT:评论喜欢点赞
     * 2:HATE_COMMENT:讨厌评论
     * 3:LIKE_VIDEO:视频点赞
     * 4:FAVORITE_VIDEO:视频收藏
     * 5:COIN_VIDEO:视频投币
     */
    @Column(name = "action_type")
    val actionType: ActionType

    @Column(name = "action_count")
    val actionCount: Int

    @Column(name = "action_time")
    val actionTime: Long
}
