package edu.only4.danmuku.adapter.application.queries.video_play_history

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video_play_history.GetUserPlayHistoryQry

import org.springframework.stereotype.Service

/**
 * 获取用户播放历史
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetUserPlayHistoryQryHandler(
) : Query<GetUserPlayHistoryQry.Request, GetUserPlayHistoryQry.Response> {

    override fun exec(request: GetUserPlayHistoryQry.Request): GetUserPlayHistoryQry.Response {

        return GetUserPlayHistoryQry.Response(

        )
    }
}
