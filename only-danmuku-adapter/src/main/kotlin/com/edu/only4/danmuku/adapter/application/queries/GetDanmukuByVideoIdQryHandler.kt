package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video_danmuku.GetDanmukuByVideoIdQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetDanmukuByVideoIdQryHandler(
) : Query<GetDanmukuByVideoIdQry.Request, GetDanmukuByVideoIdQry.Response> {

    override fun exec(request: GetDanmukuByVideoIdQry.Request): GetDanmukuByVideoIdQry.Response {

        return GetDanmukuByVideoIdQry.Response(

        )
    }
}
