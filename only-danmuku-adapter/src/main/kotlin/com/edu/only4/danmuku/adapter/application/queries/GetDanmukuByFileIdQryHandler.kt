package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video_danmuku.GetDanmukuByFileIdQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetDanmukuByFileIdQryHandler(
) : Query<GetDanmukuByFileIdQry.Request, GetDanmukuByFileIdQry.Response> {

    override fun exec(request: GetDanmukuByFileIdQry.Request): GetDanmukuByFileIdQry.Response {

        return GetDanmukuByFileIdQry.Response(

        )
    }
}
