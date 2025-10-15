package edu.only4.danmuku.adapter.application.queries.customer_focus

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.customer_focus.GetFansCountQry

import org.springframework.stereotype.Service

/**
 * 获取粉丝数量
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetFansCountQryHandler(
) : Query<GetFansCountQry.Request, GetFansCountQry.Response> {

    override fun exec(request: GetFansCountQry.Request): GetFansCountQry.Response {

        return GetFansCountQry.Response(

        )
    }
}
