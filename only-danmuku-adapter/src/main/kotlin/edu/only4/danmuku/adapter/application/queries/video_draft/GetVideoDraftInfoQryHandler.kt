package edu.only4.danmuku.adapter.application.queries.video_draft

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.customerId
import edu.only4.danmuku.application.queries._share.model.dto.VideoFilePost.VideoFilePostItem
import edu.only4.danmuku.application.queries._share.model.dto.VideoPost.VideoPostDetail
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.videoId
import edu.only4.danmuku.application.queries.video_draft.GetVideoDraftInfoQry
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
class GetVideoDraftInfoQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetVideoDraftInfoQry.Request, GetVideoDraftInfoQry.Response> {

    override fun exec(request: GetVideoDraftInfoQry.Request): GetVideoDraftInfoQry.Response {

        // 查询视频草稿信息
        val draftList = sqlClient.findAll(VideoPostDetail::class) {
            where(table.id eq request.videoId)
            where(table.customerId eq request.userId)
        }

        if (draftList.isEmpty()) {
            throw IllegalArgumentException("视频草稿不存在或无权访问: videoId=${request.videoId}, userId=${request.userId}")
        }

        val draft = draftList.first()

        // 查询视频文件列表
        val videoFiles = sqlClient.findAll(VideoFilePostItem::class) {
            where(table.videoId eq request.videoId)
        }

        return GetVideoDraftInfoQry.Response(
            videoInfo = GetVideoDraftInfoQry.VideoInfo(
                videoId = draft.id,
                videoCover = draft.videoCover,
                videoName = draft.videoName,
                pCategoryId = draft.parentCategoryId,
                categoryId = draft.categoryId,
                postType = draft.postType,
                tags = draft.tags,
                introduction = draft.introduction,
                interaction = draft.interaction,
                status = draft.status
            ),
            videoFileList = videoFiles.map { file ->
                GetVideoDraftInfoQry.VideoFileItem(
                    fileId = file.id,
                    uploadId = file.id.toString(),
                    fileIndex = file.fileIndex,
                    fileName = file.fileName ?: "",
                    fileSize = file.fileSize ?: 0L,
                    filePath = file.filePath,
                    duration = file.duration ?: 0
                )
            }
        )
    }
}
