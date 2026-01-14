package edu.only4.danmuku.adapter.application.queries.video_danmuku

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import com.only4.cap4k.ddd.domain.repo.toSpringData
import edu.only4.danmuku.application.queries._share.model.*
import edu.only4.danmuku.application.queries.video_danmuku.GetVideoDanmukuPageQry
import org.babyfish.jimmer.spring.repository.orderBy
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.desc
import org.babyfish.jimmer.sql.kt.ast.expression.`eq?`
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
class GetVideoDanmukuPageQryHandler(
    private val sqlClient: KSqlClient,
) : PageQuery<GetVideoDanmukuPageQry.Request, GetVideoDanmukuPageQry.Response> {

    override fun exec(request: GetVideoDanmukuPageQry.Request): PageData<GetVideoDanmukuPageQry.Response> {

        val pageResult = sqlClient.createQuery(VideoDanmuku::class) {
            where(table.video.customerId `eq?` request.videoUserId)
            where(table.video.videoName `ilike?` request.videoNameFuzzy)
            orderBy(table.postTime.desc())
            orderBy(toSpringData(request.sort))
            select(table.fetchBy {
                text()
                mode()
                color()
                time()
                postTime()
                video {
                    videoName()
                    videoCover()
                }
                customer {
                    nickName()
                }
            })
        }.fetchPage(request.pageNum - 1, request.pageSize)

        val responseList = pageResult.rows.map { item ->
            GetVideoDanmukuPageQry.Response(
                danmukuId = item.id,
                videoId = item.video.id,
                videoName = item.video.videoName,
                videoCover = item.video.videoCover,
                customerId = item.customer.id,
                customerNickname = item.customer.nickName,
                text = item.text!!,
                mode = item.mode!!.toInt(),
                color = item.color!!,
                time = item.time!!,
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
