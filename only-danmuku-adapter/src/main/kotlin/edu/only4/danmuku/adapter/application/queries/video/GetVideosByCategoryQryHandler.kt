package edu.only4.danmuku.adapter.application.queries.video

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.application.queries._share.model.*
import edu.only4.danmuku.application.queries.video.GetVideosByCategoryQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.desc
import org.babyfish.jimmer.sql.kt.ast.expression.`eq?`
import org.springframework.stereotype.Service

/**
 * 按分类获取视频列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetVideosByCategoryQryHandler(
    private val sqlClient: KSqlClient,
) : PageQuery<GetVideosByCategoryQry.Request, GetVideosByCategoryQry.Response> {

    override fun exec(request: GetVideosByCategoryQry.Request): PageData<GetVideosByCategoryQry.Response> {
        // 查询视频列表，支持父分类、子分类和推荐类型过滤
        val pageResult = sqlClient.createQuery(Video::class) {
            // 父分类过滤 (如果提供)
            where(table.parentCategoryId `eq?` request.parentCategoryId)
            // 子分类过滤 (如果提供)
            where(table.categoryId `eq?` request.categoryId)
            // 推荐类型过滤 (如果提供)
            where(table.recommendType `eq?` request.recommendType)
            // 按创建时间降序
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
            })
        }.fetchPage(request.pageNum - 1, request.pageSize)

        // 转换为响应格式
        val responseList = pageResult.rows.map { video ->
            GetVideosByCategoryQry.Response(
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

        return PageData.create(
            pageNum = request.pageNum,
            pageSize = request.pageSize,
            list = responseList,
            totalCount = pageResult.totalRowCount
        )
    }
}
