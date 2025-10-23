package edu.only4.danmuku.application.commands.customer_profile

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.statistics.SStatistics
import edu.only4.danmuku.domain.aggregates.statistics.enums.StatisticsDataType
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull
import java.time.*

/**
 * 更新用户统计信息（关注数/粉丝数增量）。
 * 当前实现为占位，实际落库逻辑后续迭代补充。
 */
object UpdateCustomerStatisticCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            // 目前粉丝/关注数由查询实时统计（customer_focus 表）完成。
            // 这里补充一份“用户关注相关”的日统计落库，便于看板与趋势分析。
            // 统计口径：按天、按用户聚合（dataType=USER_FOLLOW），delta 可正可负，结果不小于0。

            val delta = (request.focusCountDelta ?: 0) + (request.fansCountDelta ?: 0)
            if (delta == 0) return Response()

            val zone = ZoneId.systemDefault()
            val startOfDayEpoch = LocalDate.now(zone).atStartOfDay(zone).toEpochSecond()

            val stats = Mediator.repositories.findFirst(
                SStatistics.predicate { s ->
                    s.all(
                        s.customerId eq request.targetCustomerId,
                        s.dataType eq StatisticsDataType.USER_FOLLOW,
                        s.statisticsDate eq startOfDayEpoch
                    )
                },
                persist = true
            ).getOrNull()

            if (stats != null) {
                val current = stats.statisticsCount ?: 0
                val updated = (current + delta).coerceAtLeast(0)
                stats.statisticsCount = updated
                Mediator.uow.save()
                return Response()
            }

            // TODO: 如当天无记录，可新建 Statistics(日统计) 后设置初始值为 max(delta,0)。
            // 由于当前项目的 StatisticsFactory 未完善，这里先跳过创建，避免脏数据。
            // 若需启用：创建 Statistics(customerId, USER_FOLLOW, statisticsCount=max(delta,0), statisticsDate=startOfDayEpoch)，再 save。

            Mediator.uow.save() // 虽无更新，这里保持事务语义一致
            return Response()
        }
    }

    data class Request(
        val targetCustomerId: Long,
        val focusCountDelta: Int? = null,
        val fansCountDelta: Int? = null,
    ) : RequestParam<Response>

    class Response
}
