package edu.only4.danmuku.adapter.application.queries.video_file

import com.only4.cap4k.ddd.core.application.query.ListQuery
import edu.only4.danmuku.application.queries._share.model.VideoFile
import edu.only4.danmuku.application.queries._share.model.fetchBy
import edu.only4.danmuku.application.queries._share.model.fileIndex
import edu.only4.danmuku.application.queries._share.model.videoId
import edu.only4.danmuku.application.queries.video_file.GetVideoFilesByVideoIdQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 根据视频ID获取文件列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetVideoFilesByVideoIdQryHandler(
    private val sqlClient: KSqlClient,
) : ListQuery<GetVideoFilesByVideoIdQry.Request, GetVideoFilesByVideoIdQry.Response> {

    override fun exec(request: GetVideoFilesByVideoIdQry.Request): List<GetVideoFilesByVideoIdQry.Response> {
        // 使用 Jimmer 查询视频文件列表，按文件序号排序
        val fileList = sqlClient.createQuery(VideoFile::class) {
            where(table.videoId eq request.videoId)
            orderBy(table.fileIndex.asc())
            select(
                // 选择包含必要字段与关联主键的对象形状
                table.fetchBy {
                    allScalarFields()
                    video()
                    customer()
                }
            )
        }.execute()

        // 映射为响应对象
        return fileList.map { file ->
            GetVideoFilesByVideoIdQry.Response(
                fileId = file.id,
                videoId = file.video.id,
                userId = file.customer.id,
                fileIndex = file.fileIndex,
                fileName = file.fileName!!,
                fileSize = file.fileSize!!,
                filePath = file.filePath!!,
                duration = file.duration!!
            )
        }
    }
}
