package edu.only4.danmuku.adapter.application.queries.video

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.application.queries._share.model.Video
import edu.only4.danmuku.application.queries._share.model.categoryId
import edu.only4.danmuku.application.queries._share.model.createTime
import edu.only4.danmuku.application.queries._share.model.dto.Video.VideoListItem
import edu.only4.danmuku.application.queries._share.model.parentCategoryId
import edu.only4.danmuku.application.queries._share.model.recommendType
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
            where(table.parentCategoryId `eq?` request.pCategoryId)
            // 子分类过滤 (如果提供)
            where(table.categoryId `eq?` request.categoryId)
            // 推荐类型过滤 (如果提供)
            where(table.recommendType `eq?` request.recommendType)
            // 按创建时间降序
            orderBy(table.createTime.desc())
            // DTO投影
            select(table.fetch(VideoListItem::class))
        }.fetchPage(request.pageNum - 1, request.pageSize)

        // 转换为响应格式
        val responseList = pageResult.rows.map { video ->
            GetVideosByCategoryQry.Response(
                videoId = video.id,
                videoCover = video.videoCover,
                videoName = video.videoName,
                userId = video.customer.id,
                nickName = video.customer.relation!!.nickName,
                avatar = video.customer.relation!!.avatar,
                playCount = video.playCount,
                likeCount = video.likeCount,
                createTime = video.createTime ?: 0L
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
