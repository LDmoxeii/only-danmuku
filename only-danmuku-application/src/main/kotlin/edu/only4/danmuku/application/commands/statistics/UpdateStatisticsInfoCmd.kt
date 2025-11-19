package edu.only4.danmuku.application.commands.statistics

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.domain._share.meta.statistics.SStatistics
import edu.only4.danmuku.domain.aggregates.statistics.enums.StatisticsDataType
import edu.only4.danmuku.domain.aggregates.statistics.factory.StatisticsFactory
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZoneId
import kotlin.jvm.optionals.getOrNull

/**
 * 更新统计数据
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object UpdateStatisticsInfoCmd {

    @Service
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {
            val today = LocalDate.now()
                .atStartOfDay(ZoneId.systemDefault())
                .toEpochSecond()

            val predicate = SStatistics.predicate { schema ->
                schema.all(
                    schema.customerId.eq(request.customerId),
                    schema.dataType.eq(request.dataType),
                    schema.statisticsDate.eq(today)
                )
            }

            val statistics = Mediator.repositories.findOne(predicate)
                .getOrNull() ?: Mediator.factories.create(
                StatisticsFactory.Payload(
                    customerId = request.customerId,
                    dataType = request.dataType,
                    statisticsCount = 0,
                    statisticsDate = today
                )
            )

            statistics.updateCount(request.countDelta)

            Mediator.uow.save()
        }

    }

    data class Request(
        val customerId: Long,
        val dataType: StatisticsDataType,
        val countDelta: Int = 1
    ) : RequestParam<Unit>

}
