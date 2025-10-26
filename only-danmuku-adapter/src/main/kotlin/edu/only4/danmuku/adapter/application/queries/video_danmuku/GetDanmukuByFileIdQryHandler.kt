package edu.only4.danmuku.adapter.application.queries.video_danmuku

import com.only4.cap4k.ddd.core.application.query.ListQuery
import edu.only4.danmuku.application.queries._share.model.VideoDanmuku
import edu.only4.danmuku.application.queries._share.model.dto.VideoDanmuku.DanmukuPageItem
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.time
import edu.only4.danmuku.application.queries._share.model.videoId
import edu.only4.danmuku.application.queries.video_danmuku.GetDanmukuByFileIdQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 根据文件ID获取弹幕
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetDanmukuByFileIdQryHandler(
    private val sqlClient: KSqlClient,
) : ListQuery<GetDanmukuByFileIdQry.Request, GetDanmukuByFileIdQry.Response> {

    override fun exec(request: GetDanmukuByFileIdQry.Request): List<GetDanmukuByFileIdQry.Response> {
        // 使用 Jimmer 查询弹幕列表
        val danmukuList = sqlClient.createQuery(VideoDanmuku::class) {
            // 按文件ID过滤
            where(table.id eq request.fileId)
            // 按视频ID过滤
            where(table.videoId eq request.videoId)
            // 按弹幕时间（展示时间）排序，保证播放顺序
            orderBy(table.time.asc())
            // DTO 投影
            select(table.fetch(DanmukuPageItem::class))
        }.execute()

        // 转换为查询响应
        return danmukuList.map { danmuku ->
            GetDanmukuByFileIdQry.Response(
                danmukuId = danmuku.id,
                fileId = danmuku.fileId,
                videoId = danmuku.video.id,
                userId = danmuku.customer.id,
                text = danmuku.text ?: "",
                mode = danmuku.mode?.toInt() ?: 1, // 默认滚动模式
                color = danmuku.color ?: "#FFFFFF", // 默认白色
                time = danmuku.time ?: 0,
                postTime = danmuku.postTime ?: 0L
            )
        }
    }
}
