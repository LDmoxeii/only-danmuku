package edu.only4.danmuku.adapter.application.queries.video

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.dto.Video.VideoFullInfo
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries.video.GetVideoInfoQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 获取视频信息
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetVideoInfoQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetVideoInfoQry.Request, GetVideoInfoQry.Response> {

    override fun exec(request: GetVideoInfoQry.Request): GetVideoInfoQry.Response {
        // 查询视频完整信息
        val videoList = sqlClient.findAll(VideoFullInfo::class) {
            where(table.id eq request.videoId)
        }

        val video = videoList.first()

        return GetVideoInfoQry.Response(
            videoId = video.id,
            videoCover = video.videoCover,
            videoName = video.videoName,
            userId = video.customer.id,
            nickName = video.customer.relation!!.nickName,
            avatar = video.customer.relation!!.avatar,
            introduction = video.introduction,
            interaction = video.interaction,
            duration = video.duration,
            playCount = video.playCount,
            likeCount = video.likeCount,
            danmuCount = video.danmukuCount,
            commentCount = video.commentCount,
            coinCount = video.coinCount,
            collectCount = video.collectCount,
            createTime = video.createTime ?: 0L
        )
    }
}
