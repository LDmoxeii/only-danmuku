package edu.only4.danmuku.adapter.application.queries.video

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.application.queries._share.model.Video
import edu.only4.danmuku.application.queries._share.model.fetchBy
import edu.only4.danmuku.application.queries._share.model.likeCount
import edu.only4.danmuku.application.queries._share.model.playCount
import edu.only4.danmuku.application.queries.video.GetHotVideoPageQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.desc
import org.springframework.stereotype.Service

/**
 * 获取热门视频列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetHotVideoPageQryHandler(
    private val sqlClient: KSqlClient,
) : PageQuery<GetHotVideoPageQry.Request, GetHotVideoPageQry.Response> {

    override fun exec(request: GetHotVideoPageQry.Request): PageData<GetHotVideoPageQry.Response> {
        // 查询热门视频列表 (按播放数和点赞数排序)
        // 注意: 这里暂时忽略 lastPlayHour 参数，因为需要播放历史表来实现精确过滤
        // 简化实现：直接按播放数降序返回热门视频
        val pageResult = sqlClient.createQuery(Video::class) {
            // 按播放数降序，点赞数作为次要排序
            orderBy(table.playCount.desc(), table.likeCount.desc())
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
            GetHotVideoPageQry.Response(
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
                danmukuCount = video.danmukuCount,
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
