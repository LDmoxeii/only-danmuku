package edu.only4.danmuku.adapter.application.queries.video_encrypt

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.VideoFile
import edu.only4.danmuku.application.queries._share.model.VideoFilePost
import edu.only4.danmuku.application.queries._share.model.VideoHlsEncryptKey
import edu.only4.danmuku.application.queries._share.model.encryptKeyId
import edu.only4.danmuku.application.queries._share.model.encryptMethod
import edu.only4.danmuku.application.queries._share.model.encryptStatus
import edu.only4.danmuku.application.queries._share.model.filePath
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.keyId
import edu.only4.danmuku.application.queries._share.model.keyVersion
import edu.only4.danmuku.application.queries._share.model.quality
import edu.only4.danmuku.application.queries._share.model.videoFilePostId
import edu.only4.danmuku.application.queries.video_encrypt.GetVideoEncryptStatusQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 查询稿件文件的加密状态及最新 key 信息
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
@Service
class GetVideoEncryptStatusQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetVideoEncryptStatusQry.Request, GetVideoEncryptStatusQry.Response> {

    override fun exec(request: GetVideoEncryptStatusQry.Request): GetVideoEncryptStatusQry.Response {
        val filePostId = resolveFilePostId(request) ?: return GetVideoEncryptStatusQry.Response(
            encryptStatus = "NOT_FOUND",
            encryptMethod = null,
            keyId = null,
            keyVersion = null,
            keyQuality = null,
            encryptedMasterPath = null
        )

        val row = sqlClient.createQuery(VideoFilePost::class) {
            where(table.id eq filePostId)
            select(table.encryptStatus, table.encryptMethod, table.encryptKeyId, table.filePath)
        }.fetchOneOrNull()

        val status = row?._1?.name ?: "NOT_FOUND"
        val method = row?._2?.name
        val keyDbId = row?._3
        val masterPath = row?._4?.let { "$it/enc/master.m3u8" }

        val key = keyDbId?.let { sqlClient.findById(VideoHlsEncryptKey::class, it) }

        return GetVideoEncryptStatusQry.Response(
            encryptStatus = status,
            encryptMethod = method,
            keyId = key?.keyId,
            keyVersion = key?.keyVersion,
            keyQuality = key?.quality,
            encryptedMasterPath = masterPath
        )
    }

    private fun resolveFilePostId(request: GetVideoEncryptStatusQry.Request): Long? {
        request.videoFilePostId?.let { return it }
        val videoFileId = request.videoFileId ?: return null
        return sqlClient.createQuery(VideoFile::class) {
            where(table.id eq videoFileId)
            select(table.videoFilePostId)
        }.fetchOneOrNull()
    }
}
