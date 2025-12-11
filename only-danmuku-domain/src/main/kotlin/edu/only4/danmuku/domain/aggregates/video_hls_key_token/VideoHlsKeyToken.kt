package edu.only4.danmuku.domain.aggregates.video_hls_key_token

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity
import edu.only4.danmuku.domain.aggregates.video_hls_key_token.enums.EncryptTokenStatus

import jakarta.persistence.*
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * HLS 加密播放 token
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/12/11
 */
@Aggregate(aggregate = "VideoHlsKeyToken", name = "VideoHlsKeyToken", root = true, type = Aggregate.TYPE_ENTITY, description = "HLS 加密播放 token")
@Entity
@Table(name = "`video_hls_key_token`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `video_hls_key_token` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class VideoHlsKeyToken(
    id: Long = 0L,
    fileId: Long = 0L,
    keyId: String = "",
    keyVersion: Int = 1,
    allowedQualities: String? = null,
    tokenHash: String = "",
    audience: String? = null,
    expireTime: Long = 0L,
    maxUse: Int = 5,
    usedCount: Int = 0,
    status: EncryptTokenStatus = EncryptTokenStatus.valueOf(1),
    issueIp: String? = null,
) : AuditedFieldsEntity() {
    // 【字段映射开始】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    /**
     * ID
     * bigint
     */
    @Id
    @GeneratedValue(generator = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator")
    @GenericGenerator(name = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator", strategy = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator")
    @Column(name = "`id`", insertable = false, updatable = false)
    var id: Long = id
        internal set

    /**
     * 稿件态 fileId
     * bigint
     */
    @Column(name = "`file_id`")
    var fileId: Long = fileId
        internal set

    /**
     * Key ID
     * varchar(64)
     */
    @Column(name = "`key_id`")
    var keyId: String = keyId
        internal set

    /**
     * 密钥版本
     * int
     */
    @Column(name = "`key_version`")
    var keyVersion: Int = keyVersion
        internal set

    /**
     * 授权清晰度列表 JSON，空表示全量
     * varchar(512)
     */
    @Column(name = "`allowed_qualities`")
    var allowedQualities: String? = allowedQualities
        internal set

    /**
     * token 哈希（sha256）
     * varchar(128)
     */
    @Column(name = "`token_hash`")
    var tokenHash: String = tokenHash
        internal set

    /**
     * 受众标识（用户/终端）
     * varchar(128)
     */
    @Column(name = "`audience`")
    var audience: String? = audience
        internal set

    /**
     * 过期时间（ms）
     * bigint
     */
    @Column(name = "`expire_time`")
    var expireTime: Long = expireTime
        internal set

    /**
     * 最大可用次数
     * int
     */
    @Column(name = "`max_use`")
    var maxUse: Int = maxUse
        internal set

    /**
     * 已使用次数
     * int
     */
    @Column(name = "`used_count`")
    var usedCount: Int = usedCount
        internal set

    /**
     * 状态
     * 0:UNKNOW:未知
     * 1:VALID:有效
     * 2:EXHAUSTED:已用尽
     * 3:EXPIRED:过期
     * 4:REVOKED:吊销
     * int
     */
    @Convert(converter = EncryptTokenStatus.Converter::class)
    @Column(name = "`status`")
    var status: EncryptTokenStatus = status
        internal set

    /**
     * 签发 IP
     * varchar(64)
     */
    @Column(name = "`issue_ip`")
    var issueIp: String? = issueIp
        internal set

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】
    fun markExpired() {
        this.status = EncryptTokenStatus.EXPIRED
    }

    fun markExhausted() {
        this.status = EncryptTokenStatus.EXHAUSTED
    }

    fun consumeOnce() {
        this.usedCount += 1
        if (this.usedCount >= this.maxUse) {
            this.status = EncryptTokenStatus.EXHAUSTED
        }
    }
    // 【行为方法结束】
}
