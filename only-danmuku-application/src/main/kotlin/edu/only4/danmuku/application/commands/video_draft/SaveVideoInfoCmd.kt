package edu.only4.danmuku.application.commands.video_draft

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import org.springframework.stereotype.Service

/**
 * 保存视频信息和文件
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object SaveVideoInfoCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.uow.save()

            return Response(
            )
        }

    }

    data class Request(
        /** 视频ID(更新时传) */
        val videoId: Long? = null,
        /** 视频封面 */
        val videoCover: String,
        /** 视频名称 */
        val videoName: String,
        /** 父分类ID */
        val pCategoryId: Int,
        /** 分类ID */
        val categoryId: Int? = null,
        /** 上传类型(0自制/1转载) */
        val postType: Int,
        /** 标签 */
        val tags: String,
        /** 简介 */
        val introduction: String? = null,
        /** 互动设置 */
        val interaction: String? = null,
        /** 上传文件列表 */
        val uploadFileList: List<VideoFileInfo>
    ) : RequestParam<Response>

    data class VideoFileInfo(
        val uploadId: String,
        val fileIndex: Int,
        val fileName: String,
        val fileSize: Long,
        val duration: Int
    )

    class Response()
}
