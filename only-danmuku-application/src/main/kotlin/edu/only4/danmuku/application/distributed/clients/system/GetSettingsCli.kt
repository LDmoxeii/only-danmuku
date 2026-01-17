package edu.only4.danmuku.application.distributed.clients.system

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 获取系统设置
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/17
 */
object GetSettingsCli {

    class Request : RequestParam<Response>

    data class Response(
        val registerCoinCount: Int,
        val postVideoCoinCount: Int,
        val videoSize: Int,
        val videoPCount: Int,
        val videoCount: Int,
        val commentCount: Int,
        val danmukuCount: Int,
        val renameNicknameCoinCost: Int
    )
}
