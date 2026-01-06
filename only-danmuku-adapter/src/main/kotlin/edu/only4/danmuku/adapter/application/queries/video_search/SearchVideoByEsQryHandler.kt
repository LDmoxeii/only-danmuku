package edu.only4.danmuku.adapter.application.queries.video_search

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video_search.SearchVideoByEsQry

import org.springframework.stereotype.Service
import org.babyfish.jimmer.sql.kt.KSqlClient

/**
 * 使用 ES 搜索视频（替代数据库模糊查询）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/26
 */
@Service
class SearchVideoByEsQryHandler(
    private val sqlClient: KSqlClient,
) : Query<SearchVideoByEsQry.Request, SearchVideoByEsQry.Response> {

    override fun exec(request: SearchVideoByEsQry.Request): SearchVideoByEsQry.Response {

        return SearchVideoByEsQry.Response(
            videoId = TODO("set videoId"),
            videoCover = TODO("set videoCover"),
            videoName = TODO("set videoName"),
            userId = TODO("set userId"),
            createTime = TODO("set createTime"),
            lastUpdateTime = TODO("set lastUpdateTime"),
            parentCategoryId = TODO("set parentCategoryId"),
            categoryId = TODO("set categoryId"),
            postType = TODO("set postType"),
            originInfo = TODO("set originInfo"),
            tags = TODO("set tags"),
            introduction = TODO("set introduction"),
            duration = TODO("set duration"),
            playCount = TODO("set playCount"),
            likeCount = TODO("set likeCount"),
            danmuCount = TODO("set danmuCount"),
            commentCount = TODO("set commentCount"),
            coinCount = TODO("set coinCount"),
            collectCount = TODO("set collectCount"),
            recommendType = TODO("set recommendType"),
            lastPlayTime = TODO("set lastPlayTime"),
            nickName = TODO("set nickName"),
            avatar = TODO("set avatar"),
            categoryFullName = TODO("set categoryFullName")
        )
    }
}
