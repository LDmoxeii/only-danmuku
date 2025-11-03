package edu.only4.danmuku.adapter.domain._share.configure

import org.springframework.core.convert.converter.Converter
import org.springframework.core.convert.support.ConfigurableConversionService
import org.springframework.core.convert.support.DefaultConversionService
import java.time.Instant
import java.time.temporal.TemporalAccessor

/**
 * Converters to support auditing dates into Long epoch seconds.
 */
object AuditingLongConverters {

    class TemporalAccessorToLongSeconds : Converter<TemporalAccessor, Long> {
        override fun convert(source: TemporalAccessor): Long = Instant.from(source).epochSecond
    }

    class InstantToLongSeconds : Converter<Instant, Long> {
        override fun convert(source: Instant): Long = source.epochSecond
    }

    @JvmStatic
    fun registerWithDefaultConversionService() {
        val shared = DefaultConversionService.getSharedInstance()
        if (shared is ConfigurableConversionService) {
            // Idempotent registration (duplicates are ignored gracefully)
            shared.addConverter(TemporalAccessorToLongSeconds())
            shared.addConverter(InstantToLongSeconds())
        }
    }
}

