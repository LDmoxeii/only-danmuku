package edu.only4.danmuku.domain.aggregates.video_quality_policy

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity
import edu.only4.danmuku.domain.aggregates.video_quality_policy.enums.QualityAuthPolicy

import jakarta.persistence.*

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 视频清晰度策略
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2026/01/21
 */
@Aggregate(aggregate = "VideoQualityPolicy", name = "VideoQualityPolicy", root = true, type = Aggregate.TYPE_ENTITY, description = "视频清晰度策略")
@Entity
@Table(name = "`video_quality_policy`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `video_quality_policy` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class VideoQualityPolicy(
    id: Long = 0L,
    videoId: Long = 0L,
    fileIndex: Int = 0,
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
     * 视频ID
     * bigint
     */
    @Column(name = "`video_id`")
    var videoId: Long = videoId
        internal set

    /**
     * 文件索引
     * int
     */
    @Column(name = "`file_index`")
    var fileIndex: Int = fileIndex
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
     * 0:UNKNOW:未知
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

    // 【行为方法开始】
    fun applyPolicy(
        authPolicy: QualityAuthPolicy,
        remark: String?,
    ) {
        this.authPolicy = authPolicy
        this.remark = remark
    }
    // 【行为方法结束】
}
