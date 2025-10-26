package edu.only4.danmuku.adapter.portal.api.jimmer

import cn.dev33.satoken.annotation.SaIgnore
import edu.only4.danmuku.application.queries._share.model.Category
import edu.only4.danmuku.application.queries._share.model.category.fetchBy
import edu.only4.danmuku.application.queries._share.model.CustomerAction
import edu.only4.danmuku.application.queries._share.model.customer_action.fetchBy
import edu.only4.danmuku.application.queries._share.model.CustomerFocus
import edu.only4.danmuku.application.queries._share.model.customer_focus.fetchBy
import edu.only4.danmuku.application.queries._share.model.CustomerProfile
import edu.only4.danmuku.application.queries._share.model.customer_profile.fetchBy
import edu.only4.danmuku.application.queries._share.model.CustomerVideoSeries
import edu.only4.danmuku.application.queries._share.model.customer_video_series.fetchBy
import edu.only4.danmuku.application.queries._share.model.CustomerVideoSeriesVideo
import edu.only4.danmuku.application.queries._share.model.customer_video_series_video.fetchBy
import edu.only4.danmuku.application.queries._share.model.Statistics
import edu.only4.danmuku.application.queries._share.model.statistics.fetchBy
import edu.only4.danmuku.application.queries._share.model.User
import edu.only4.danmuku.application.queries._share.model.user.fetchBy
import edu.only4.danmuku.application.queries._share.model.Video
import edu.only4.danmuku.application.queries._share.model.video.fetchBy
import edu.only4.danmuku.application.queries._share.model.VideoComment
import edu.only4.danmuku.application.queries._share.model.video_comment.fetchBy
import edu.only4.danmuku.application.queries._share.model.VideoDanmuku
import edu.only4.danmuku.application.queries._share.model.video_danmuku.fetchBy
import edu.only4.danmuku.application.queries._share.model.VideoPost
import edu.only4.danmuku.application.queries._share.model.video_draft.fetchBy
import edu.only4.danmuku.application.queries._share.model.VideoFile
import edu.only4.danmuku.application.queries._share.model.video_file.fetchBy
import edu.only4.danmuku.application.queries._share.model.VideoFilePost
import edu.only4.danmuku.application.queries._share.model.video_file_draft.fetchBy
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SaIgnore
@RestController
class JimmerTestController(
    private val sqlClient: KSqlClient,
) {
    @GetMapping("category")
    fun testCategoryReadModel() = sqlClient.executeQuery(Category::class) {
        select(
            table.fetchBy {
                allScalarFields()
                `parent*`()
                `children*`()
            }
        )
    }

    @GetMapping("customerAction")
    fun testCustomerActionReadModel() =
        sqlClient.executeQuery(CustomerAction::class) {
            select(
                table.fetchBy {
                    allScalarFields()
                    customer {
                        allScalarFields()

                    }
                    videoOwner {
                        allScalarFields()
                    }
                    video {
                        allScalarFields()
                    }
                    comment {
                        allScalarFields()
                        videoOwner {
                            allScalarFields()
                        }
                        video {
                            allScalarFields()
                        }
                        customer {
                            allScalarFields()
                        }
                        replyCustomer {
                            allScalarFields()
                        }
                    }
                }
            )

        }

    @GetMapping("customerFocus")
    fun testCustomerFocusReadModel() =
        sqlClient.executeQuery(CustomerFocus::class) {
            select(
                table.fetchBy {
                    allScalarFields()
                    customer {
                        allScalarFields()
                    }
                    focusCustomer {
                        allScalarFields()
                    }
                }
            )
        }

    @GetMapping("customerProfile")
    fun testCustomerProfileReadModel() =
        sqlClient.executeQuery(CustomerProfile::class) {
            select(
                table.fetchBy {
                    allScalarFields()
                    customer {
                        allScalarFields()
                    }
                }
            )
        }

    @GetMapping("customerVideoSeries")
    fun testCustomerVideoSeriesReadModel() =
        sqlClient.executeQuery(CustomerVideoSeries::class) {
            select(
                table.fetchBy {
                    allScalarFields()
                    customer {
                        allScalarFields()
                    }
                }
            )
        }

    @GetMapping("customerVideoSeriesVideo")
    fun testCustomerVideoSeriesVideoReadModel() =
        sqlClient.executeQuery(CustomerVideoSeriesVideo::class) {
            select(
                table.fetchBy {
                    allScalarFields()
                    customer {
                        allScalarFields()
                    }
                    videoSeries {
                        allScalarFields()
                    }
                    video {
                        allScalarFields()
                    }
                }
            )
        }

    @GetMapping("statistics")
    fun testStatisticsReadModel() =
        sqlClient.executeQuery(Statistics::class) {
            select(
                table.fetchBy {
                    allScalarFields()
                    customer {
                        allScalarFields()
                    }
                }
            )
        }

    @GetMapping("user")
    fun testUserReadModel() =
        sqlClient.executeQuery(User::class) {
            select(
                table.fetchBy {
                    allScalarFields()
                }
            )
        }

    @GetMapping("video")
    fun testVideoReadModel() =
        sqlClient.executeQuery(Video::class) {
            select(
                table.fetchBy {
                    allScalarFields()
                    videoPost {
                        allScalarFields()
                    }
                }
            )
        }

    @GetMapping("videoComment")
    fun testVideoCommentReadModel() =
        sqlClient.executeQuery(VideoComment::class) {
            select(
                table.fetchBy {
                    allScalarFields()
                    videoOwner {
                        allScalarFields()
                    }
                    video {
                        allScalarFields()
                    }
                    customer {
                        allScalarFields()
                    }
                    replyCustomer {
                        allScalarFields()
                    }
                }
            )
        }

    @GetMapping("videoDanmuku")
    fun testVideoDanmukuReadModel() =
        sqlClient.executeQuery(VideoDanmuku::class) {
            select(
                table.fetchBy {
                    allScalarFields()
                    video {
                        allScalarFields()
                    }
                    customer {
                        allScalarFields()
                    }
                }
            )
        }

    @GetMapping("videoPost")
    fun testVideoPostReadModel() =
        sqlClient.executeQuery(VideoPost::class) {
            select(
                table.fetchBy {
                    allScalarFields()
                    customer {
                        allScalarFields()
                    }
                }
            )
        }

    @GetMapping("videoFile")
    fun testVideoFileReadModel() =
        sqlClient.executeQuery(VideoFile::class) {
            select(
                table.fetchBy {
                    allScalarFields()
                    customer {
                        allScalarFields()
                    }
                    video {
                        allScalarFields()
                    }
                }
            )
        }

    @GetMapping("videoFilePost")
    fun testVideoFilePostReadModel() =
        sqlClient.executeQuery(VideoFilePost::class) {
            select(
                table.fetchBy {
                    allScalarFields()
                }
            )
        }
}
