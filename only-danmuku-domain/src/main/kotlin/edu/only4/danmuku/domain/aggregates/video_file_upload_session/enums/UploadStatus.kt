package edu.only4.danmuku.domain.aggregates.video_file_upload_session.enums
import com.fasterxml.jackson.annotation.JsonValue

import com.only.engine.exception.KnownException

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.AttributeConverter

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/11/24
 */
@Aggregate(aggregate = "VideoFileUploadSession", name = "UploadStatus", type = "enum", description = "")
enum class UploadStatus(
    @field:JsonValue
    val code: Int,
    val desc: String
) {

    /**
     * 未知类型
     */
    UNKNOW(0, "未知类型"),

    /**
     * 已创建
     */
    CREATED(1, "已创建"),

    /**
     * 上传中
     */
    UPLOADING(2, "上传中"),

    /**
     * 完成
     */
    DONE(3, "完成"),

    /**
     * 已放弃
     */
    ABORTED(4, "已放弃"),

    /**
     * 已过期
     */
    EXPIRED(5, "已过期"),
    ;

    companion object {
        private val enumMap: Map<Int, UploadStatus> by lazy {
            entries.associateBy { it.code }
        }

        fun valueOf(value: Int?): UploadStatus {
            return valueOfOrNull(value) ?: throw KnownException("枚举类型 UploadStatus 枚举值转换异常，不存在的值: $value")
        }

        fun valueOfOrNull(value: Int?): UploadStatus? {
            return enumMap[value]
        }
    }

    /**
     * JPA转换器
     */
    class Converter : AttributeConverter<UploadStatus, Int> {

        override fun convertToDatabaseColumn(attribute: UploadStatus): Int {
            return attribute.code
        }

        override fun convertToEntityAttribute(dbData: Int): UploadStatus {
            return valueOf(dbData)
        }
    }
}
