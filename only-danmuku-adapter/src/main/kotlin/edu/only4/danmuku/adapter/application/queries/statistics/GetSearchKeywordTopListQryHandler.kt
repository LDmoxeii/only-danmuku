package edu.only4.danmuku.adapter.application.queries.statistics

import com.only.engine.redis.misc.RedisUtils
import com.only4.cap4k.ddd.core.application.query.ListQuery
import edu.only4.danmuku.adapter.portal.api._share.constant.Constants
import edu.only4.danmuku.application.queries.statistics.GetSearchKeywordTopListQry
import org.springframework.stereotype.Service

/**
 * 获取搜索关键词排行榜
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/17
 */
@Service
class GetSearchKeywordTopListQryHandler: ListQuery<GetSearchKeywordTopListQry.Request, GetSearchKeywordTopListQry.Item> {

    override fun exec(request: GetSearchKeywordTopListQry.Request): List<GetSearchKeywordTopListQry.Item> {
        return RedisUtils.getCacheZSetRange<String>(Constants.REDIS_KEY_VIDEO_SEARCH_COUNT, 0, 9).map {
            GetSearchKeywordTopListQry.Item(it)
        }
    }
}
