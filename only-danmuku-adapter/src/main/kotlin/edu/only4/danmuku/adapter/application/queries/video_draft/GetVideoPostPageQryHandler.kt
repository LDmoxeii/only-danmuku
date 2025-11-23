package edu.only4.danmuku.adapter.application.queries.video_draft

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.application.queries._share.model.*
import edu.only4.danmuku.application.queries.video_draft.GetVideoPostPageQry
import edu.only4.danmuku.domain.aggregates.video.enums.RecommendType
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.*
import org.springframework.stereotype.Service

/**
 * 获取视频分页列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetVideoPostPageQryHandler(
    private val sqlClient: KSqlClient,
) : PageQuery<GetVideoPostPageQry.Request, GetVideoPostPageQry.Response> {

    override fun exec(request: GetVideoPostPageQry.Request): PageData<GetVideoPostPageQry.Response> {

        val pageResult = sqlClient.createQuery(VideoPost::class) {
            where(table.customerId `eq?` request.userId)
            where(table.videoName `ilike?` request.videoNameFuzzy)
            where(table.parentCategoryId `eq?` request.categoryParentId)
            where(table.categoryId `eq?` request.categoryId)
            where(table.id `valueNotIn?` request.excludeVideoIds)
            when (request.recommendType) {

                RecommendType.RECOMMEND -> {
                    where(table.video.recommendType eq RecommendType.RECOMMEND)
                }

                RecommendType.NOT_RECOMMEND -> {
                    val v = table.`video?`

                    where(
                        or(
                            v.id.isNull(),                             // 没视频
                            v.recommendType eq RecommendType.NOT_RECOMMEND  // 有视频但为未推荐
                        )
                    )
                }

                else -> Unit
            }
            // 按创建时间倒序
            orderBy(table.createTime.desc())
            select(table.fetchBy {
                allScalarFields()
                video {
                    allScalarFields()
                }
                customer {
                    allScalarFields()
                    relation {
                        allScalarFields()
                    }
                }
                parentCategory {
                    allScalarFields()
                }
                category {
                    allScalarFields()
                }
            })
        }.fetchPage(request.pageNum - 1, request.pageSize)

        val responseList = pageResult.rows.map { videoPost ->
            GetVideoPostPageQry.Response(
                videoId = videoPost.id,
                videoCover = videoPost.videoCover,
                videoName = videoPost.videoName,
                userId = videoPost.customerId,
                createTime = videoPost.createTime ?: 0,
                lastUpdateTime = videoPost.updateTime,
                parentCategoryId = videoPost.parentCategoryId,
                categoryId = videoPost.categoryId,
                postType = videoPost.postType,
                originInfo = videoPost.originInfo,
                tags = videoPost.tags,
                introduction = videoPost.introduction,
                duration = videoPost.duration,
                status = videoPost.status,
                playCount = videoPost.video?.playCount ?: 0,
                likeCount = videoPost.video?.likeCount ?: 0,
                danmuCount = videoPost.video?.danmukuCount ?: 0,
                commentCount = videoPost.video?.commentCount ?: 0,
                coinCount = videoPost.video?.coinCount ?: 0,
                collectCount = videoPost.video?.collectCount ?: 0,
                recommendType = videoPost.video?.recommendType ?: RecommendType.NOT_RECOMMEND,
                lastPlayTime = videoPost.video?.lastPlayTime ?: 0,
                nickName = videoPost.customer.nickName,
                avatar = videoPost.customer.relation!!.avatar,
                categoryFullName = videoPost.parentCategory.name + videoPost.category?.name,
            )
        }

        return PageData.create(
            pageNum = request.pageNum,
            pageSize = request.pageSize,
            list = responseList,
            totalCount = pageResult.totalRowCount
        )
    }
}
