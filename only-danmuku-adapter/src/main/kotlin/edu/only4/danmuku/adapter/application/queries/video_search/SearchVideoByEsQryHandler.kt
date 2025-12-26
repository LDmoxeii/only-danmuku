package edu.only4.danmuku.adapter.application.queries.video_search

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.application.queries.video_search.SearchVideoByEsQry
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.elasticsearch._types.SortOrder
import co.elastic.clients.elasticsearch._types.query_dsl.Query
import co.elastic.clients.elasticsearch.core.SearchRequest

/**
 * 使用 ES 搜索视频（替代数据库模糊查询）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/26
 */
@Service
class SearchVideoByEsQryHandler(
    private val esClient: ElasticsearchClient,
    @Value("\${app.video-search.es.index:only-danmuku-video}")
    private val indexName: String,
) : PageQuery<SearchVideoByEsQry.Request, SearchVideoByEsQry.Response> {

    override fun exec(request: SearchVideoByEsQry.Request): PageData<SearchVideoByEsQry.Response> {
        val keyword = request.keyword.trim()
        if (keyword.isBlank()) {
            return PageData.create(
                pageNum = request.pageNum,
                pageSize = request.pageSize,
                list = emptyList(),
                totalCount = 0
            )
        }

        val pageNum = request.pageNum.coerceAtLeast(1)
        val pageSize = request.pageSize.coerceIn(1, 100)
        val from = (pageNum - 1) * pageSize

        val searchRequest = SearchRequest.Builder()
            .index(indexName)
            .from(from)
            .size(pageSize)
            .query(buildQuery(keyword))
            .apply { applySort(this, request.orderType) }
            .build()

        val response = esClient.search(searchRequest, Map::class.java)
        val hits = response.hits().hits()
        val list = hits.mapNotNull { hit ->
            val source = hit.source() ?: return@mapNotNull null
            mapToResponse(source)
        }
        val total = response.hits().total()?.value() ?: list.size.toLong()

        return PageData.create(
            pageNum = pageNum,
            pageSize = pageSize,
            list = list,
            totalCount = total
        )
    }

    private fun buildQuery(keyword: String): Query {
        val trimmed = keyword.trim()
        if (trimmed.length <= 1) {
            val like = "*$trimmed*"
            return Query.of { q ->
                q.bool { b ->
                    b.should { s ->
                        s.multiMatch { mm ->
                            mm.query(trimmed)
                                .fields(
                                    "videoName^3",
                                    "tags^2",
                                    "introduction",
                                    "categoryFullName",
                                    "nickName"
                                )
                        }
                    }
                    b.should { s ->
                        s.wildcard { w ->
                            w.field("videoName.keyword").value(like).caseInsensitive(true)
                        }
                    }
                    b.should { s ->
                        s.wildcard { w ->
                            w.field("tags.keyword").value(like).caseInsensitive(true)
                        }
                    }
                    b.minimumShouldMatch("1")
                }
            }
        }
        return Query.of { q ->
            q.bool { b ->
                b.should { s ->
                    s.multiMatch { mm ->
                        mm.query(trimmed)
                            .fields(
                                "videoName^3",
                                "tags^2",
                                "introduction",
                                "categoryFullName",
                                "nickName"
                            )
                    }
                }
                b.should { s ->
                    s.multiMatch { mm ->
                        mm.query(trimmed)
                            .fields(
                                "videoName.ngram^2",
                                "tags.ngram"
                            )
                    }
                }
                b.minimumShouldMatch("1")
            }
        }
    }

    private fun applySort(builder: SearchRequest.Builder, orderType: Int?) {
        when (orderType) {
            0 -> builder.sort { it.field { f -> f.field("playCount").order(SortOrder.Desc) } }
            1 -> builder.sort { it.field { f -> f.field("createTime").order(SortOrder.Desc) } }
            2 -> builder.sort { it.field { f -> f.field("danmuCount").order(SortOrder.Desc) } }
            3 -> builder.sort { it.field { f -> f.field("collectCount").order(SortOrder.Desc) } }
            else -> builder.sort { it.score { s -> s.order(SortOrder.Desc) } }
        }
        builder.sort { it.field { f -> f.field("lastUpdateTime").order(SortOrder.Desc) } }
    }

    private fun mapToResponse(source: Map<*, *>): SearchVideoByEsQry.Response? {
        val videoId = source.longValue("videoId") ?: return null
        return SearchVideoByEsQry.Response(
            videoId = videoId,
            videoCover = source.stringValue("videoCover"),
            videoName = source.stringValue("videoName"),
            userId = source.longValue("userId"),
            createTime = source.longValue("createTime") ?: 0L,
            lastUpdateTime = source.longValue("lastUpdateTime"),
            parentCategoryId = source.longValue("parentCategoryId") ?: 0L,
            categoryId = source.longValue("categoryId"),
            postType = source.intValue("postType") ?: 0,
            originInfo = source.stringValue("originInfo"),
            tags = source.stringValue("tags"),
            introduction = source.stringValue("introduction"),
            duration = source.intValue("duration") ?: 0,
            playCount = source.intValue("playCount") ?: 0,
            likeCount = source.intValue("likeCount") ?: 0,
            danmuCount = source.intValue("danmuCount") ?: 0,
            commentCount = source.intValue("commentCount") ?: 0,
            coinCount = source.intValue("coinCount") ?: 0,
            collectCount = source.intValue("collectCount") ?: 0,
            recommendType = source.intValue("recommendType") ?: 0,
            lastPlayTime = source.longValue("lastPlayTime"),
            nickName = source.stringValue("nickName"),
            avatar = source.stringValue("avatar"),
            categoryFullName = source.stringValue("categoryFullName")
        )
    }

    private fun Map<*, *>.stringValue(key: String): String? {
        return this[key]?.toString()
    }

    private fun Map<*, *>.intValue(key: String): Int? {
        val value = this[key]
        return when (value) {
            is Number -> value.toInt()
            is String -> value.toIntOrNull()
            else -> null
        }
    }

    private fun Map<*, *>.longValue(key: String): Long? {
        val value = this[key]
        return when (value) {
            is Number -> value.toLong()
            is String -> value.toLongOrNull()
            else -> null
        }
    }
}
