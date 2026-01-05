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
        val row = sqlClient.createQuery(VideoFilePost::class) {
            where(table.videoPost.id eq request.videoPostId)
            where(table.fileIndex eq request.fileIndex)
            select(table.encryptStatus, table.encryptMethod, table.encryptOutputPrefix, table.filePath)
        }.fetchOneOrNull()

        val status = row?._1?.name ?: "NOT_FOUND"
        val method = row?._2?.name
        val outputPrefix = row?._3?.takeIf { it.isNotBlank() } ?: row?._4
        val masterPath = outputPrefix?.trimEnd('/')?.let { "$it/master.m3u8" }

        val latestVersion = resolveLatestKeyVersion(request.videoPostId, request.fileIndex)
        val key = latestVersion?.let { version ->
            sqlClient.createQuery(VideoHlsEncryptKey::class) {
                where(table.videoPostId eq request.videoPostId)
                where(table.fileIndex eq request.fileIndex)
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

    private fun resolveLatestKeyVersion(videoPostId: Long, fileIndex: Int): Int? {
        val versions = sqlClient.createQuery(VideoHlsEncryptKey::class) {
            where(table.videoPostId eq videoPostId)
            where(table.fileIndex eq fileIndex)
            where(table.status eq EncryptKeyStatus.ACTIVE)
            select(table.keyVersion)
        }.execute()
        return versions.maxOrNull()
    }

    private fun qualityScore(quality: String): Int {
        val number = QUALITY_NUMBER_REGEX.find(quality)?.groupValues?.getOrNull(1)?.toIntOrNull()
        return number ?: Int.MIN_VALUE
    }

    companion object {
        private val QUALITY_NUMBER_REGEX = Regex("(\\d+)")
    }
}
