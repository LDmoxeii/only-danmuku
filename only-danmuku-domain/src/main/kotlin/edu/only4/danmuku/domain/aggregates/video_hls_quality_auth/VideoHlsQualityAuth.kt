package edu.only4.danmuku.domain.aggregates.video_hls_quality_auth

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity
import edu.only4.danmuku.domain.aggregates.video_hls_quality_auth.enums.QualityAuthPolicy

import jakarta.persistence.*

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 视频清晰度授权策略
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
@Aggregate(aggregate = "VideoHlsQualityAuth", name = "VideoHlsQualityAuth", root = true, type = Aggregate.TYPE_ENTITY, description = "视频清晰度授权策略")
@Entity
@Table(name = "`video_hls_quality_auth`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `video_hls_quality_auth` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class VideoHlsQualityAuth(
    id: Long = 0L,
    fileId: Long = 0L,
    quality: String = "",
    authPolicy: QualityAuthPolicy = QualityAuthPolicy.valueOf(1),
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
     * 清晰度档位，如 1080p
     * varchar(32)
     */
    @Column(name = "`quality`")
    var quality: String = quality
        internal set

    /**
     * 授权策略
     * 1:PUBLIC:公开
     * 2:LOGIN:登录
     * 3:PAID:付费
     * 4:CUSTOM:自定义
     * int
     */
    @Convert(converter = QualityAuthPolicy.Converter::class)
    @Column(name = "`auth_policy`")
    var authPolicy: QualityAuthPolicy = authPolicy
        internal set

    /**
     * 备注/策略说明
     * varchar(256)
     */
    @Column(name = "`remark`")
    var remark: String? = remark
        internal set

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动
}
