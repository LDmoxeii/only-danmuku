package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

/**
 * 发布视频接口载荷
 */
object UCenterPostVideo {

    data class Request(
        /** 视频ID(更新时传) */
        val videoId: String? = null,
        /** 视频封面 */

        @field:NotEmpty(message = "视频封面不能为空")
        val videoCover: String = "",
        /** 视频名称 */

        @field:NotEmpty(message = "视频名称不能为空")
        @field:Size(max = 100, message = "长度不能超过100个字符")
        val videoName: String = "",
        /** 父分类ID */

        @field:NotEmpty(message = "父分类ID不能为空")
        val pCategoryId: Int = 0,
        /** 分类ID */
        val categoryId: Int? = null,
        /** 上传类型(0自制/1转载) */

        @field:NotEmpty(message = "上传类型(0自制/1转载)不能为空")
        val postType: Int = 0,
        /** 标签 */

        @field:NotEmpty(message = "标签不能为空")
        @field:Size(max = 300, message = "长度不能超过300个字符")
        val tags: String = "",
        /** 简介 */

        @field:Size(max = 2000, message = "长度不能超过2000个字符")
        val introduction: String? = null,
        /** 互动设置 */

        @field:Size(max = 3, message = "长度不能超过3个字符")
        val interaction: String? = null,
        /** 上传文件列表JSON */

        @field:NotEmpty(message = "上传文件列表JSON不能为空")
        val uploadFileList: String = ""
    )

    class Response
}
