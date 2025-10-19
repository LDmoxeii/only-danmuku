package edu.only4.danmuku.adapter.application.queries.video_danmuku

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import com.only4.cap4k.ddd.domain.repo.toSpringData
import edu.only4.danmuku.application.queries._share.draft.video_danmuku.DanmukuPageItem
import edu.only4.danmuku.application.queries._share.model.video.videoName
import edu.only4.danmuku.application.queries._share.model.video_danmuku.JVideoDanmuku
import edu.only4.danmuku.application.queries._share.model.video_danmuku.postTime
import edu.only4.danmuku.application.queries._share.model.video_danmuku.video
import edu.only4.danmuku.application.queries.video_danmuku.GetDanmukuPageQry
import org.babyfish.jimmer.spring.repository.orderBy
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.desc
import org.babyfish.jimmer.sql.kt.ast.expression.`ilike?`
import org.springframework.stereotype.Service

/**
 * 获取弹幕分页列表(管理端)
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/17
 */
@Service
class GetDanmukuPageQryHandler(
    private val sqlClient: KSqlClient,
) : PageQuery<GetDanmukuPageQry.Request, GetDanmukuPageQry.Response> {

    override fun exec(request: GetDanmukuPageQry.Request): PageData<GetDanmukuPageQry.Response> {

        // 使用 Jimmer 分页查询弹幕数据
        val pageResult = sqlClient.createQuery(JVideoDanmuku::class) {
            // 支持按视频名称模糊查询
            where(table.video.videoName `ilike?` request.videoNameFuzzy)

            orderBy(table.postTime.desc())
            orderBy(toSpringData(request.sort))

            // 使用 DTO 投影查询
            select(table.fetch(DanmukuPageItem::class))
        }.fetchPage(request.pageNum - 1, request.pageSize)

        // 转换为查询响应格式
        val responseList = pageResult.rows.map { item ->
            GetDanmukuPageQry.Response(
                danmukuId = item.id,
                videoId = item.videoId,
                videoName = item.video.videoName,
                videoCover = item.video.videoCover,
                customerId = item.customerId,
                customerNickname = item.customer.nickName,
                text = item.text,
                mode = item.mode?.toInt(),
                color = item.color,
                time = item.time,
                postTime = item.postTime ?: 0L
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
