package edu.only4.danmuku.application.validater

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.customer_video_series.GetCustomerVideoSeriesInfoQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 校验视频是否属于指定系列
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [VideoInSeries.Validator::class])
@MustBeDocumented
annotation class VideoInSeries(
    val message: String = "视频不在指定系列中",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val seriesIdField: String = "seriesId",
    val videoIdField: String = "videoId",
) {
    class Validator : ConstraintValidator<VideoInSeries, Any> {
        private lateinit var seriesField: String
        private lateinit var videoField: String

        override fun initialize(constraintAnnotation: VideoInSeries) {
            seriesField = constraintAnnotation.seriesIdField
            videoField = constraintAnnotation.videoIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true

            val props = value::class.memberProperties.associateBy { it.name }
            val seriesId = (props[seriesField]?.getter?.call(value) as? Long) ?: return true
            val videoId = (props[videoField]?.getter?.call(value) as? Long) ?: return true

            val series = runCatching {
                Mediator.queries.send(GetCustomerVideoSeriesInfoQry.Request(seriesId))
            }.getOrNull() ?: return false

            return series.videoList?.any { it.videoId == videoId } ?: false
        }
    }
}
