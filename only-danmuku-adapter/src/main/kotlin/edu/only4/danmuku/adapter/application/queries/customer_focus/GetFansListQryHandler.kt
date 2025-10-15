package edu.only4.danmuku.adapter.application.queries.customer_focus

import com.only4.cap4k.ddd.core.application.query.ListQuery

import edu.only4.danmuku.application.queries.customer_focus.GetFansListQry

import org.springframework.stereotype.Service

/**
 * 获取粉丝列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetFansListQryHandler(
) : ListQuery<GetFansListQry.Request, GetFansListQry.Response> {

    override fun exec(request: GetFansListQry.Request): List<GetFansListQry.Response> {

        return listOf(GetFansListQry.Response(

        ))

    }
}
