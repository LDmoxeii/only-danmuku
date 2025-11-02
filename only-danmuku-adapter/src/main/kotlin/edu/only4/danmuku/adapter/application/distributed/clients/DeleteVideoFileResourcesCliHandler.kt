package edu.only4.danmuku.adapter.application.distributed.clients

import com.only4.cap4k.ddd.core.application.RequestHandler

import edu.only4.danmuku.application.distributed.clients.DeleteVideoFileResourcesCli

import org.springframework.stereotype.Service

/**
 * 从硬盘物理删除视频文件资源
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/02
 */
@Service
class DeleteVideoFileResourcesCliHandler : RequestHandler<DeleteVideoFileResourcesCli.Request, Unit> {
    override fun exec(request: DeleteVideoFileResourcesCli.Request) {
        // TODO: 实现远程调用逻辑或适配调用

    }
}
