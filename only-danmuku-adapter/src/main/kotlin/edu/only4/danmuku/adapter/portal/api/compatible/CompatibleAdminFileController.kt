package edu.only4.danmuku.adapter.portal.api.compatible

import cn.dev33.satoken.annotation.SaIgnore
import com.only.engine.web.annotation.IgnoreResultWrapper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.distributed.clients.file_storage.UploadImageResourceCli
import edu.only4.danmuku.application.queries.file_storage.GetResourceAccessUrlQry
import org.jetbrains.annotations.NotNull
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.net.URI

/**
 * 管理员文件管理控制器
 */
@RestController
@RequestMapping("/admin/file")
@Validated
class CompatibleAdminFileController {

    @PostMapping("/uploadImage")
    fun uploadImage(
        @NotNull file: MultipartFile,
        @NotNull createThumbnail: Boolean,
    ): String {
        val resp = Mediator.requests.send(
            UploadImageResourceCli.Request(
                file = file,
                createThumbnail = createThumbnail,
                bizType = "COVER"
            )
        )
        return resp.resourceKey
    }

    @SaIgnore
    @IgnoreResultWrapper
    @GetMapping("/getResource")
    fun getResource(
        @RequestParam sourceName: String
    ): ResponseEntity<Void> {
        val result = Mediator.queries.send(
            GetResourceAccessUrlQry.Request(resourceKey = sourceName)
        )
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create(result.url))
            .build()
    }

}
