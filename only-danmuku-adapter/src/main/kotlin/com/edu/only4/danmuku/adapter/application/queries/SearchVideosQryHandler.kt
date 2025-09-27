package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video.SearchVideosQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class SearchVideosQryHandler(
) : Query<SearchVideosQry.Request, SearchVideosQry.Response> {

    override fun exec(request: SearchVideosQry.Request): SearchVideosQry.Response {

        return SearchVideosQry.Response(

        )
    }
}
