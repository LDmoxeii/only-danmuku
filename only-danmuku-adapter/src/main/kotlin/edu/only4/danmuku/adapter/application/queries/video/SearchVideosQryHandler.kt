package edu.only4.danmuku.adapter.application.queries.video

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.application.queries._share.model.*
import edu.only4.danmuku.application.queries.video.SearchVideosQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.desc
import org.babyfish.jimmer.sql.kt.ast.expression.`eq?`
import org.babyfish.jimmer.sql.kt.ast.expression.`ilike?`
import org.babyfish.jimmer.sql.kt.ast.expression.`valueNotIn?`
import org.springframework.stereotype.Service

/**
 * 搜索视频
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class SearchVideosQryHandler(
    private val sqlClient: KSqlClient,
) : PageQuery<SearchVideosQry.Request, SearchVideosQry.Response> {

    override fun exec(request: SearchVideosQry.Request): PageData<SearchVideosQry.Response> {
        // 使用 Jimmer 查询视频列表，关联用户档案表
        val pageResult = sqlClient.createQuery(Video::class) {
            // 用户ID过滤
            where(table.customerId `eq?` request.userId)
            // 视频名称模糊查询
            where(table.videoName `ilike?` request.videoNameFuzzy)
            // 父分类过滤
            where(table.parentCategoryId `eq?` request.categoryParentId)
            // 分类过滤
            where(table.categoryId `eq?` request.categoryId)
            // 状态过滤 (这里过滤的是推荐状态)
            where(table.recommendType `eq?` request.recommendType)
            // 排除视频ID集合
            if (!request.excludeVideoIds.isNullOrEmpty()) {
                where(table.id `valueNotIn?` request.excludeVideoIds!!)
            }
            // 按创建时间倒序
            orderBy(table.createTime.desc())
            // DTO投影
            select(table.fetchBy {
                allScalarFields()
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
                videoPost {
                    status()
                }
            })
        }.fetchPage(request.pageNum - 1, request.pageSize)

        // 转换为查询响应
        val responseList = pageResult.rows.map { video ->
            SearchVideosQry.Response(
                videoId = video.id,
                videoCover = video.videoCover,
                videoName = video.videoName,
                userId = video.customerId,
                createTime = video.createTime!!,
                lastUpdateTime = video.updateTime,
                parentCategoryId = video.parentCategoryId,
                categoryId = video.categoryId,
                postType = video.postType,
                originInfo = video.originInfo,
                tags = video.tags,
                introduction = video.introduction,
                duration = video.duration,
                status = video.videoPost.status,
                playCount = video.playCount,
                likeCount = video.likeCount,
                danmuCount = video.danmukuCount,
                commentCount = video.commentCount,
                coinCount = video.coinCount,
                collectCount = video.collectCount,
                recommendType = video.recommendType,
                lastPlayTime = video.lastPlayTime,
                nickName = video.customer.nickName,
                avatar = video.customer.relation!!.avatar,
                categoryFullName = video.parentCategory.name + video.category?.name,
            )
        }

        // 返回分页结果
        return PageData.create(
            pageNum = request.pageNum,
            pageSize = request.pageSize,
            list = responseList,
            totalCount = pageResult.totalRowCount
        )
    }
}
