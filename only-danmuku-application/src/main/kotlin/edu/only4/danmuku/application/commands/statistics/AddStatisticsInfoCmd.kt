package edu.only4.danmuku.application.commands.statistics

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain.aggregates.statistics.enums.StatisticsDataType
import edu.only4.danmuku.domain.aggregates.statistics.factory.StatisticsFactory
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZoneId

/**
 * 添加统计数据
 * 用于初始化用户的统计记录
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object AddStatisticsInfoCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            // 获取今日日期（秒级时间戳）
            val today = LocalDate.now()
                .atStartOfDay(ZoneId.systemDefault())
                .toEpochSecond()

            // 为用户创建初始统计记录（各类型统计数量初始化为0）
            val statisticsTypes = listOf(
                StatisticsDataType.VIDEO_VIEW,
                StatisticsDataType.VIDEO_LIKE,
                StatisticsDataType.VIDEO_COMMENT,
                StatisticsDataType.VIDEO_SHARE,
                StatisticsDataType.USER_FOLLOW,
                StatisticsDataType.USER_LOGIN
            )

            statisticsTypes.forEach { dataType ->
                Mediator.factories.create(
                    StatisticsFactory.Payload(
                        customerId = request.customerId,
                        dataType = dataType,
                        statisticsCount = 0,
                        statisticsDate = today
                    )
                )
            }

            Mediator.uow.save()

            return Response()
        }
    }

    data class Request(
        /** 用户ID */
        val customerId: Long
    ) : RequestParam<Response>

    class Response
}
