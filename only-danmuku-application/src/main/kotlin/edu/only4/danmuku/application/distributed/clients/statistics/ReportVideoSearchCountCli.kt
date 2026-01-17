package edu.only4.danmuku.application.distributed.clients.statistics

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 上报视频搜索次数
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/17
 */
object ReportVideoSearchCountCli {

    data class Request(
        val keyword: String
    ) : RequestParam<Response>

    class Response
}
