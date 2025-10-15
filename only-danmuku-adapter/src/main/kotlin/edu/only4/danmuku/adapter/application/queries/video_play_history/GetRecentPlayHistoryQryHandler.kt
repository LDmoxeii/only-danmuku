package edu.only4.danmuku.adapter.application.queries.video_play_history

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video_play_history.GetRecentPlayHistoryQry

import org.springframework.stereotype.Service

/**
 * 获取最近播放历史
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetRecentPlayHistoryQryHandler(
) : Query<GetRecentPlayHistoryQry.Request, GetRecentPlayHistoryQry.Response> {

    override fun exec(request: GetRecentPlayHistoryQry.Request): GetRecentPlayHistoryQry.Response {

        return GetRecentPlayHistoryQry.Response(

        )
    }
}
