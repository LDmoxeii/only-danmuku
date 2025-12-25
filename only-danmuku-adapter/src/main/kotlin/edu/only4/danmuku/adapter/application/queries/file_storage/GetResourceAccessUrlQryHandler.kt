package edu.only4.danmuku.adapter.application.queries.file_storage

import com.only.engine.oss.enums.AccessPolicyType
import com.only.engine.oss.factory.OssFactory
import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries.file_storage.GetResourceAccessUrlQry
import org.springframework.stereotype.Service
import java.time.Duration

/**
 * 根据资源 key 返回可访问 URL（OSS）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/24
 */
@Service
class GetResourceAccessUrlQryHandler : Query<GetResourceAccessUrlQry.Request, GetResourceAccessUrlQry.Response> {

    override fun exec(request: GetResourceAccessUrlQry.Request): GetResourceAccessUrlQry.Response {
        val client = OssFactory.instance()
        val resourceKey = request.resourceKey.trim().trimStart('/')
        val expireSeconds = request.expireSeconds.coerceAtLeast(1)
        val url = if (request.preferPresign || client.getAccessPolicy() != AccessPolicyType.PUBLIC) {
            client.createPresignedGetUrl(resourceKey, Duration.ofSeconds(expireSeconds.toLong()))
        } else {
            client.publicUrlForKey(resourceKey)
        }
        return GetResourceAccessUrlQry.Response(
            url = url
        )
    }
}
