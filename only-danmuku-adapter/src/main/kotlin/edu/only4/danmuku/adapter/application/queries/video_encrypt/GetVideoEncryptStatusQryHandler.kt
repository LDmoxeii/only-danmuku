package edu.only4.danmuku.adapter.application.queries.video_encrypt

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.*
import edu.only4.danmuku.application.queries.video_encrypt.GetVideoEncryptStatusQry
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.enums.EncryptKeyStatus
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
            select(table.encryptStatus, table.encryptMethod, table.filePath)
        }.fetchOneOrNull()

        val status = row?._1?.name ?: "NOT_FOUND"
        val method = row?._2?.name
        val masterPath = row?._3?.let { "$it/enc/master.m3u8" }

        val latestVersion = resolveLatestKeyVersion(filePostId)
        val key = latestVersion?.let { version ->
            sqlClient.createQuery(VideoHlsEncryptKey::class) {
                where(table.fileId eq filePostId)
                where(table.keyVersion eq version)
                where(table.status eq EncryptKeyStatus.ACTIVE)
                select(table)
            }.execute()
                .sortedWith(
                    compareByDescending<VideoHlsEncryptKey> { qualityScore(it.quality ?: "") }
                        .thenBy { it.quality ?: "" }
                )
                .firstOrNull()
        }

        return GetVideoEncryptStatusQry.Response(
            encryptStatus = status,
            encryptMethod = method,
            keyId = key?.keyId,
            keyVersion = key?.keyVersion ?: latestVersion,
            keyQuality = key?.quality,
            encryptedMasterPath = masterPath
        )
    }

    private fun resolveLatestKeyVersion(filePostId: Long): Int? {
        val versions = sqlClient.createQuery(VideoHlsEncryptKey::class) {
            where(table.fileId eq filePostId)
            where(table.status eq EncryptKeyStatus.ACTIVE)
            select(table.keyVersion)
        }.execute()
        return versions.maxOrNull()
    }

    private fun qualityScore(quality: String): Int {
        val number = QUALITY_NUMBER_REGEX.find(quality)?.groupValues?.getOrNull(1)?.toIntOrNull()
        return number ?: Int.MIN_VALUE
    }

    private fun resolveFilePostId(request: GetVideoEncryptStatusQry.Request): Long? {
        request.videoFilePostId?.let { return it }
        val videoFileId = request.videoFileId ?: return null
        return sqlClient.createQuery(VideoFile::class) {
            where(table.id eq videoFileId)
            select(table.videoFilePostId)
        }.fetchOneOrNull()
    }

    companion object {
        private val QUALITY_NUMBER_REGEX = Regex("(\\d+)")
    }
}
