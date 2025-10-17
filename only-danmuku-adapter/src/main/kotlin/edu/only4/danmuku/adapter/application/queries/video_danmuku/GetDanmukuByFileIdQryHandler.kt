package edu.only4.danmuku.adapter.application.queries.video_danmuku

import com.only4.cap4k.ddd.core.application.query.ListQuery
import edu.only4.danmuku.application.queries.video_danmuku.GetDanmukuByFileIdQry
import org.springframework.stereotype.Service

/**
 * 根据文件ID获取弹幕
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetDanmukuByFileIdQryHandler(
) : ListQuery<GetDanmukuByFileIdQry.Request, GetDanmukuByFileIdQry.Response> {

    override fun exec(request: GetDanmukuByFileIdQry.Request): List<GetDanmukuByFileIdQry.Response> {

        return listOf(
            // TODO: 实现根据文件ID获取弹幕逻辑
//            GetDanmukuByFileIdQry.Response(
//
//            )
        )
    }
}
