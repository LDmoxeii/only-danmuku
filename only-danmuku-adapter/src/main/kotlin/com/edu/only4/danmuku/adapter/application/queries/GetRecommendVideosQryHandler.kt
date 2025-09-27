package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video.GetRecommendVideosQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetRecommendVideosQryHandler(
) : Query<GetRecommendVideosQry.Request, GetRecommendVideosQry.Response> {

    override fun exec(request: GetRecommendVideosQry.Request): GetRecommendVideosQry.Response {

        return GetRecommendVideosQry.Response(

        )
    }
}
