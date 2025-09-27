package com.edu.only4.danmuku.adapter.application.queries;

import com.edu.only4.danmuku.application.queries.video_danmuku.GetDanmukuListQry
import com.only4.cap4k.ddd.core.application.query.ListQuery
import org.springframework.stereotype.Service

@Service
class GetDanmukuListQryHandler(
) : ListQuery<GetDanmukuListQry.Request, GetDanmukuListQry.Response> {

    override fun exec(request: GetDanmukuListQry.Request): List<GetDanmukuListQry.Response > {

        return listOf(GetDanmukuListQry.Response(

        ))

    }
}
