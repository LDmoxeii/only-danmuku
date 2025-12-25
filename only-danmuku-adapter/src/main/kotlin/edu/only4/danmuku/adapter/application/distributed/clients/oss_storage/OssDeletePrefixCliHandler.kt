package edu.only4.danmuku.adapter.application.distributed.clients.oss_storage

import com.only.engine.exception.KnownException
import com.only.engine.oss.factory.OssFactory
import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application.distributed.clients.oss_storage.OssDeletePrefixCli
import org.springframework.stereotype.Service

/**
 * 删除 OSS 前缀下的对象
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/24
 */
@Service
class OssDeletePrefixCliHandler : RequestHandler<OssDeletePrefixCli.Request, OssDeletePrefixCli.Response> {
    override fun exec(request: OssDeletePrefixCli.Request): OssDeletePrefixCli.Response {
        val prefix = request.objectPrefix.trim().trim('/')
        if (prefix.isBlank()) {
            throw KnownException.illegalArgument("objectPrefix")
        }
        val client = OssFactory.instance()
        val deleted = client.deleteByPrefix(prefix)
        return OssDeletePrefixCli.Response(
            deletedCount = deleted,
            success = true
        )
    }
}

