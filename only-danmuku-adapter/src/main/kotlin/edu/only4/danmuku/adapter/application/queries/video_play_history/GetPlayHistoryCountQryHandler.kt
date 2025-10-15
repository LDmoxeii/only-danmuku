package edu.only4.danmuku.adapter.application.queries.video_play_history

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video_play_history.GetPlayHistoryCountQry

import org.springframework.stereotype.Service

/**
 * 获取播放历史数量
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetPlayHistoryCountQryHandler(
) : Query<GetPlayHistoryCountQry.Request, GetPlayHistoryCountQry.Response> {

    override fun exec(request: GetPlayHistoryCountQry.Request): GetPlayHistoryCountQry.Response {

        return GetPlayHistoryCountQry.Response(

        )
    }
}
