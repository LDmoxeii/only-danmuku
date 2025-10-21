package edu.only4.danmuku.domain.aggregates.video_draft.enums

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.AttributeConverter

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Aggregate(aggregate = "VideoDraft", name = "VideoStatus", type = "enum", description = "")
enum class VideoStatus(
    val code: Int,
    val desc: String
) {

    /**
     * 未知状态
     */
    UNKNOW(0, "未知状态"),

    /**
     * 转码中
     */
    TRANSCODING(1, "转码中"),

    /**
     * 转码失败
     */
    TRANSCODE_FAILED(2, "转码失败"),

    /**
     * 待审核
     */
    PENDING_REVIEW(3, "待审核"),

    /**
     * 审核成功
     */
    REVIEW_PASSED(4, "审核成功"),

    /**
     * 审核失败
     */
    REVIEW_FAILED(5, "审核失败"),
    ;

    companion object {
        private val enumMap: Map<Int, VideoStatus> by lazy {
            entries.associateBy { it.code }
        }

        fun valueOf(value: Int): VideoStatus {
            return enumMap[value] ?: throw IllegalArgumentException("枚举类型VideoStatus枚举值转换异常，不存在的值: $value")
        }

        fun valueOfOrNull(value: Int?): VideoStatus? {
            return if (value == null) null else valueOf(value)
        }
    }

    /**
     * JPA转换器
     */
    class Converter : AttributeConverter<VideoStatus, Int> {

        override fun convertToDatabaseColumn(attribute: VideoStatus): Int {
            return attribute.code
        }

        override fun convertToEntityAttribute(dbData: Int): VideoStatus {
            return valueOf(dbData)
        }
    }
}
