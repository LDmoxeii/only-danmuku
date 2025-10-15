package edu.only4.danmuku.adapter.application.queries.video_play_history

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video_play_history.GetPlayProgressQry

import org.springframework.stereotype.Service

/**
 * 获取播放进度
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetPlayProgressQryHandler(
) : Query<GetPlayProgressQry.Request, GetPlayProgressQry.Response> {

    override fun exec(request: GetPlayProgressQry.Request): GetPlayProgressQry.Response {

        return GetPlayProgressQry.Response(

        )
    }
}
