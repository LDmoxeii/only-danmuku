package edu.only4.danmuku.application.queries._share.model

import edu.only4.danmuku.domain.aggregates.video_post.enums.EncryptMethod
import edu.only4.danmuku.domain.aggregates.video_post_processing.enums.ProcessStatus
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "video_post_processing_file")
interface VideoPostProcessingFile : BaseEntity {

    @IdView
    val videoPostProcessingId: Long

    @ManyToOne
    @JoinColumn(name = "parent_id")
    val videoPostProcessing: VideoPostProcessing

    @Column(name = "file_index")
    val fileIndex: Int

    @Column(name = "upload_id")
    val uploadId: Long

    @Column(name = "transcode_status")
    val transcodeStatus: ProcessStatus

    @Column(name = "encrypt_status")
    val encryptStatus: ProcessStatus

    @Column(name = "encrypt_method")
    val encryptMethod: EncryptMethod

    @Column(name = "encrypt_key_version")
    val encryptKeyVersion: Int?

    @Column(name = "transcode_output_prefix")
    val transcodeOutputPrefix: String?

    @Column(name = "transcode_output_path")
    val transcodeOutputPath: String?

    @Column(name = "transcode_variants_json")
    val transcodeVariantsJson: String?

    @Column(name = "encrypt_output_dir")
    val encryptOutputDir: String?

    @Column(name = "encrypt_output_prefix")
    val encryptOutputPrefix: String?

    @Column(name = "duration")
    val duration: Int?

    @Column(name = "file_size")
    val fileSize: Long?

    @Column(name = "fail_reason")
    val failReason: String?
}
