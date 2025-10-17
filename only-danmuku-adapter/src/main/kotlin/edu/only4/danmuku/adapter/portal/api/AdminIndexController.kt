package edu.only4.danmuku.adapter.portal.api

import edu.only4.danmuku.adapter.portal.api.payload.AdminIndexGetActualTimeStatistics
import edu.only4.danmuku.adapter.portal.api.payload.AdminIndexGetWeekStatistics
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 管理员统计信息控制器
 */
@RestController
@RequestMapping("/admin/index")
@Validated
class AdminIndexController {

    /**
     * 获取实时统计信息
     */
    @PostMapping("/getActualTimeStatisticsInfo")
    fun adminIndexGetActualTimeStatistics(): AdminIndexGetActualTimeStatistics.Response {
        // TODO: 实现获取实时统计信息逻辑
        return AdminIndexGetActualTimeStatistics.Response()
    }

    /**
     * 获取周统计信息
     */
    @PostMapping("/getWeekStatisticsInfo")
    fun adminIndexGetWeekStatistics(@RequestBody request: AdminIndexGetWeekStatistics.Request): AdminIndexGetWeekStatistics.Response {
        // TODO: 实现获取周统计信息逻辑
        return AdminIndexGetWeekStatistics.Response()
    }

}
