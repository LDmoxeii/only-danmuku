package edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.enums.EncryptKeyStatus

import jakarta.persistence.*

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 视频 HLS 加密密钥
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
@Aggregate(aggregate = "VideoHlsEncryptKey", name = "VideoHlsEncryptKey", root = true, type = Aggregate.TYPE_ENTITY, description = "视频 HLS 加密密钥")
@Entity
@Table(name = "`video_hls_encrypt_key`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `video_hls_encrypt_key` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class VideoHlsEncryptKey(
    id: Long = 0L,
    fileId: Long = 0L,
    quality: String? = null,
    keyId: String = "",
    keyCiphertext: String = "",
    ivHex: String? = null,
    keyVersion: Int = 1,
    method: String = "HLS_AES_128",
    keyUriTemplate: String = "",
    expireTime: Long? = null,
    status: EncryptKeyStatus = EncryptKeyStatus.valueOf(1),
    remark: String? = null,
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
     * 绑定清晰度，空表示全局通用
     * varchar(32)
     */
    @Column(name = "`quality`")
    var quality: String? = quality
        internal set

    /**
     * Key ID（m3u8 暴露）
     * varchar(64)
     */
    @Column(name = "`key_id`")
    var keyId: String = keyId
        internal set

    /**
     * 密钥密文（KMS 加密后 Base64）
     * varchar(512)
     */
    @Column(name = "`key_ciphertext`")
    var keyCiphertext: String = keyCiphertext
        internal set

    /**
     * IV hex（16字节，可空）
     * varchar(64)
     */
    @Column(name = "`iv_hex`")
    var ivHex: String? = ivHex
        internal set

    /**
     * 密钥版本号（轮换递增）
     * int
     */
    @Column(name = "`key_version`")
    var keyVersion: Int = keyVersion
        internal set

    /**
     * 加密方式
     * varchar(32)
     */
    @Column(name = "`method`")
    var method: String = method
        internal set

    /**
     * m3u8 中的 URI 模板，含 token 占位
     * varchar(512)
     */
    @Column(name = "`key_uri_template`")
    var keyUriTemplate: String = keyUriTemplate
        internal set

    /**
     * 过期时间（ms）
     * bigint
     */
    @Column(name = "`expire_time`")
    var expireTime: Long? = expireTime
        internal set

    /**
     * 状态
     * 1:ACTIVE:可用
     * 2:REVOKED:吊销
     * 3:EXPIRED:过期
     * int
     */
    @Convert(converter = EncryptKeyStatus.Converter::class)
    @Column(name = "`status`")
    var status: EncryptKeyStatus = status
        internal set

    /**
     * 备注/轮换原因
     * varchar(256)
     */
    @Column(name = "`remark`")
    var remark: String? = remark
        internal set

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动
}
