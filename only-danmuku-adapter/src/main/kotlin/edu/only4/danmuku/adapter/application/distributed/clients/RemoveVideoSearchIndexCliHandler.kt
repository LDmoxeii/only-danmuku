package edu.only4.danmuku.adapter.application.distributed.clients

import co.elastic.clients.elasticsearch.ElasticsearchClient
import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application.distributed.clients.RemoveVideoSearchIndexCli
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

/**
 * 删除视频搜索索引
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/02
 */
@Service
class RemoveVideoSearchIndexCliHandler(
    private val esClient: ElasticsearchClient,
    @Value("\${app.video-search.es.index:only-danmuku-video}")
    private val indexName: String,
) : RequestHandler<RemoveVideoSearchIndexCli.Request, Unit> {

    private val logger = LoggerFactory.getLogger(RemoveVideoSearchIndexCliHandler::class.java)
    override fun exec(request: RemoveVideoSearchIndexCli.Request) {
        runCatching {
            esClient.delete { req ->
                req.index(indexName)
                    .id(request.videoId.toString())
            }
        }.onFailure { ex ->
            logger.error("RemoveVideoSearchIndex failed, videoId={}", request.videoId, ex)
        }
    }
}

