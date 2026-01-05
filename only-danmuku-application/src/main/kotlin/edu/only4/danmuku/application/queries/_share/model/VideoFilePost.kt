package edu.only4.danmuku.application.queries._share.model

import edu.only4.danmuku.domain.aggregates.video_post.enums.EncryptMethod
import edu.only4.danmuku.domain.aggregates.video_post.enums.EncryptStatus
import edu.only4.danmuku.domain.aggregates.video_post.enums.TransferResult
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "video_file_post")
interface VideoFilePost : BaseEntity {

    @IdView
    val customerId: Long

    @IdView
    val uploadId: Long

    @IdView
    val videoPostId: Long

    @OneToOne
    @JoinColumn(name = "upload_id")
    val upload: VideoFileUploadSession

    @OneToOne
    @JoinColumn(name = "customer_id")
    val customer: CustomerProfile

    @ManyToOne
    @JoinColumn(name = "video_post_id")
    val videoPost: VideoPost

    @Column(name = "file_index")
    val fileIndex: Int

    @Column(name = "file_name")
    val fileName: String?

    @Column(name = "file_size")
    val fileSize: Long?

    @Column(name = "file_path")
    val filePath: String?

    @Column(name = "transcode_output_prefix")
    val transcodeOutputPrefix: String?

    @Column(name = "transcode_variants_json")
    val transcodeVariantsJson: String?

    @Column(name = "encrypt_output_prefix")
    val encryptOutputPrefix: String?

    /**
     * 转码结果 @E=0:UNKNOW:未知结果|1:TRANSCODING:转码中|2:SUCCESS:转码成功|3:FAILED:转码失败;@T=TransferResult
     */
    @Column(name = "transfer_result")
    val transferResult: TransferResult

    @Column(name = "duration")
    val duration: Int?

    /**
     * 加密状态 @E=1:UNENCRYPTED:未加密|2:ENCRYPTING:加密中|3:ENCRYPTED:已加密|4:FAILED:失败;@T=EncryptStatus
     */
    @Column(name = "encrypt_status")
    val encryptStatus: EncryptStatus

    /**
     * 加密方式 @E=1:HLS_AES_128:AES-128|2:SAMPLE_AES:SAMPLE-AES|3:DRM:DRM占位;@T=EncryptMethod
     */
    @Column(name = "encrypt_method")
    val encryptMethod: EncryptMethod

    @Column(name = "encrypt_key_version")
    val encryptKeyVersion: Int?
}
