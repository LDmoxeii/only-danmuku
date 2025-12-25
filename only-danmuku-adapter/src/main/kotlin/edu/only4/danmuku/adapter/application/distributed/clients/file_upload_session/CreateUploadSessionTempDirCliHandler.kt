package edu.only4.danmuku.adapter.application.distributed.clients.file_upload_session

import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application.distributed.clients.file_upload_session.CreateUploadSessionTempDirCli
import org.springframework.stereotype.Service
import java.nio.file.Files

/**
 * 创建上传会话临时目录（系统临时目录）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/25
 */
@Service
class CreateUploadSessionTempDirCliHandler :
    RequestHandler<CreateUploadSessionTempDirCli.Request, CreateUploadSessionTempDirCli.Response> {
    override fun exec(request: CreateUploadSessionTempDirCli.Request): CreateUploadSessionTempDirCli.Response {
        val tempDir = Files.createTempDirectory("upload-session-${request.uploadId}-").toFile()
        return CreateUploadSessionTempDirCli.Response(
            tempPath = tempDir.absolutePath
        )
    }
}
