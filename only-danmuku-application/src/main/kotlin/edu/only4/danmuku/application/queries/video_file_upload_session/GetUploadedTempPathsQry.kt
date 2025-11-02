package edu.only4.danmuku.application.queries.video_file_upload_session

import com.only4.cap4k.ddd.core.application.query.ListQueryParam

/**
 * 获取上传完毕的临时路径
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/02
 */
object GetUploadedTempPathsQry {

    class Request(
        val customerId: Long,
        val videoId: Long,
    ) : ListQueryParam<String>
}
