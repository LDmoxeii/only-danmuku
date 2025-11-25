package edu.only4.danmuku.adapter.application.distributed.clients.video_transcode

import com.only4.cap4k.ddd.core.application.RequestHandler
import com.only.engine.misc.FFprobeUtils
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.application.distributed.clients.video_transcode.ProbeVideoResolutionCli
import org.springframework.stereotype.Service
import java.io.File

/**
 * 防腐层：使用 ffprobe 获取源视频分辨率，为 ABR 档位决策提供输入
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
@Service
class ProbeVideoResolutionCliHandler(
    private val fileProps: FileAppProperties,
) : RequestHandler<ProbeVideoResolutionCli.Request, ProbeVideoResolutionCli.Response> {
    override fun exec(request: ProbeVideoResolutionCli.Request): ProbeVideoResolutionCli.Response {
        val absolutePath = resolveToAbsolute(request.inputPath)
        val result = FFprobeUtils.probeVideoResolution(absolutePath, showLog = fileProps.showFFmpegLog)
        return ProbeVideoResolutionCli.Response(
            width = result.width,
            height = result.height,
            bitrateKbps = result.bitrateKbps
        )
    }

    private fun resolveToAbsolute(path: String): String {
        val file = File(path)
        if (file.isAbsolute) {
            return file.absolutePath
        }
        return File(fileProps.projectFolder + Constants.FILE_FOLDER + path).absolutePath
    }
}
