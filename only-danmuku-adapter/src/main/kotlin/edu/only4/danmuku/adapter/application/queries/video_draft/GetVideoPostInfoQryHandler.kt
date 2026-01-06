package edu.only4.danmuku.adapter.application.queries.video_draft

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.customerId
import edu.only4.danmuku.application.queries._share.model.dto.VideoFilePost.VideoFilePostItem
import edu.only4.danmuku.application.queries._share.model.dto.VideoPost.VideoPostDetail
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.videoPostId
import edu.only4.danmuku.application.queries.video_draft.GetVideoPostInfoQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 获取视频草稿信息
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetVideoPostInfoQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetVideoPostInfoQry.Request, GetVideoPostInfoQry.Response> {

    override fun exec(request: GetVideoPostInfoQry.Request): GetVideoPostInfoQry.Response {

        // 查询视频草稿信息
        val videoPost = sqlClient.findOne(VideoPostDetail::class) {
            where(table.id eq request.videoPostId)
            where(table.customerId eq request.userId)
        }

        // 查询视频文件列表
        val videoFiles = sqlClient.findAll(VideoFilePostItem::class) {
            where(table.videoPostId eq request.videoPostId)
        }

        return GetVideoPostInfoQry.Response(
            videoInfo = GetVideoPostInfoQry.VideoInfo(
                videoId = videoPost.id,
                videoCover = videoPost.videoCover,
                videoName = videoPost.videoName,
                parentCategoryId = videoPost.parentCategoryId,
                categoryId = videoPost.categoryId,
                postType = videoPost.postType,
                originInfo = videoPost.originInfo,
                tags = videoPost.tags,
                introduction = videoPost.introduction,
                interaction = videoPost.interaction,
                status = videoPost.status
            ),
            videoFileList = videoFiles.map { file ->
                GetVideoPostInfoQry.VideoFileItem(
                    fileId = file.id,
                    uploadId = file.id.toString(),
                    fileIndex = file.fileIndex,
                    fileName = file.fileName ?: "",
                    fileSize = file.fileSize ?: 0L,
                    filePath = file.transcodeOutputPrefix,
                    duration = file.duration ?: 0,
                    transferResult = file.transferResult,
                )
            }
        )
    }
}
