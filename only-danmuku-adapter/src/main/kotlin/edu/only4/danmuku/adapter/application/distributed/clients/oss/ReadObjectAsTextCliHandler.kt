package edu.only4.danmuku.adapter.application.distributed.clients.oss

import com.only.engine.oss.factory.OssFactory
import com.only4.cap4k.ddd.core.application.RequestHandler

import edu.only4.danmuku.application.distributed.clients.oss.ReadObjectAsTextCli

import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets

/**
 * 读取 OSS 对象为文本
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/17
 */
@Service
class ReadObjectAsTextCliHandler : RequestHandler<ReadObjectAsTextCli.Request, ReadObjectAsTextCli.Response> {
    override fun exec(request: ReadObjectAsTextCli.Request): ReadObjectAsTextCli.Response {
        val objectKey = request.objectKey
        return ReadObjectAsTextCli.Response(
            OssFactory.instance().getObjectContent(objectKey)
                .bufferedReader(StandardCharsets.UTF_8)
                .use { it.readText() }
        )
    }
}

