package edu.only4.danmuku.application.queries.category

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 根据分类ID判断分类是否存在
 */
object CategoryExistsByIdQry {

    class Request(
        val categoryId: Long
    ) : RequestParam<Response>

    class Response(
        val exists: Boolean,
    )
}

