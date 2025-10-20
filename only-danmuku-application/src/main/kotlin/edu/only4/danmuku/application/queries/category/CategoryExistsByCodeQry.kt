package edu.only4.danmuku.application.queries.category

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 根据分类编码判断分类是否存在
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object CategoryExistsByCodeQry {

    class Request(
        val code: String,
    ) : RequestParam<Response>

    class Response(
        val exists: Boolean,  // true: 存在, false: 不存在
    )
}
