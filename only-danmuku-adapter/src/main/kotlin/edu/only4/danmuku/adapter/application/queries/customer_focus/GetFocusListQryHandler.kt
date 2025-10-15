package edu.only4.danmuku.adapter.application.queries.customer_focus

import com.only4.cap4k.ddd.core.application.query.ListQuery

import edu.only4.danmuku.application.queries.customer_focus.GetFocusListQry

import org.springframework.stereotype.Service

/**
 * 获取关注列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetFocusListQryHandler(
) : ListQuery<GetFocusListQry.Request, GetFocusListQry.Response> {

    override fun exec(request: GetFocusListQry.Request): List<GetFocusListQry.Response> {

        return listOf(GetFocusListQry.Response(

        ))

    }
}
