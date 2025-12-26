package edu.only4.danmuku.adapter.application.distributed.clients.video_search

import co.elastic.clients.elasticsearch.ElasticsearchClient
import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application.distributed.clients.video_search.SyncVideoBasicsToEsCli
import edu.only4.danmuku.application.queries._share.model.Video
import edu.only4.danmuku.application.queries._share.model.fetchBy
import edu.only4.danmuku.application.queries._share.model.id
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

/**
 * 同步视频基础信息到 ES（upsert 文档）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/26
 */
@Service
class SyncVideoBasicsToEsCliHandler(
    private val sqlClient: KSqlClient,
    private val esClient: ElasticsearchClient,
    @Value("\${app.video-search.es.index:only-danmuku-video}")
    private val indexName: String,
) : RequestHandler<SyncVideoBasicsToEsCli.Request, SyncVideoBasicsToEsCli.Response> {
    override fun exec(request: SyncVideoBasicsToEsCli.Request): SyncVideoBasicsToEsCli.Response {
        return runCatching {
            val video = sqlClient.createQuery(Video::class) {
                where(table.id eq request.videoId)
                select(
                    table.fetchBy {
                        allScalarFields()
                        customer {
                            allScalarFields()
                            relation {
                                allScalarFields()
                            }
                        }
                        parentCategory {
                            allScalarFields()
                        }
                        category {
                            allScalarFields()
                        }
                    }
                )
            }.fetchOneOrNull() ?: return SyncVideoBasicsToEsCli.Response(
                success = false,
                failReason = "video_not_found"
            )

            val doc = VideoSearchDocument(
                videoId = video.id,
                videoPostId = request.videoPostId,
                userId = video.customerId,
                videoCover = video.videoCover,
                videoName = video.videoName,
                parentCategoryId = video.parentCategoryId,
                categoryId = video.categoryId,
                postType = video.postType.code,
                originInfo = video.originInfo,
                tags = video.tags,
                introduction = video.introduction,
                duration = video.duration,
                playCount = video.playCount,
                likeCount = video.likeCount,
                danmuCount = video.danmukuCount,
                commentCount = video.commentCount,
                coinCount = video.coinCount,
                collectCount = video.collectCount,
                recommendType = video.recommendType.code,
                createTime = video.createTime ?: 0L,
                lastUpdateTime = video.updateTime,
                lastPlayTime = video.lastPlayTime,
                nickName = video.customer.nickName,
                avatar = video.customer.relation?.avatar,
                categoryFullName = buildCategoryFullName(video.parentCategory.name, video.category?.name)
            )

            esClient.index { req ->
                req.index(indexName)
                    .id(video.id.toString())
                    .document(doc)
            }

            SyncVideoBasicsToEsCli.Response(success = true, failReason = null)
        }.getOrElse { ex ->
            SyncVideoBasicsToEsCli.Response(
                success = false,
                failReason = ex.message
            )
        }
    }

    private fun buildCategoryFullName(parent: String?, child: String?): String? {
        val p = parent?.trim().orEmpty()
        val c = child?.trim().orEmpty()
        return when {
            p.isBlank() && c.isBlank() -> null
            c.isBlank() -> p
            p.isBlank() -> c
            else -> p + c
        }
    }

    data class VideoSearchDocument(
        val videoId: Long,
        val videoPostId: Long?,
        val userId: Long,
        val videoCover: String?,
        val videoName: String?,
        val parentCategoryId: Long,
        val categoryId: Long?,
        val postType: Int,
        val originInfo: String?,
        val tags: String?,
        val introduction: String?,
        val duration: Int,
        val playCount: Int,
        val likeCount: Int,
        val danmuCount: Int,
        val commentCount: Int,
        val coinCount: Int,
        val collectCount: Int,
        val recommendType: Int,
        val createTime: Long,
        val lastUpdateTime: Long?,
        val lastPlayTime: Long?,
        val nickName: String?,
        val avatar: String?,
        val categoryFullName: String?
    )
}

