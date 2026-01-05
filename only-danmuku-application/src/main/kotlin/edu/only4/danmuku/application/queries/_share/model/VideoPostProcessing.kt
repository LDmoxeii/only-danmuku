package edu.only4.danmuku.application.queries._share.model

import edu.only4.danmuku.domain.aggregates.video_post_processing.enums.ProcessStatus
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "video_post_processing")
interface VideoPostProcessing : BaseEntity {

    @IdView
    val videoPostId: Long

    @OneToOne
    @JoinColumn(name = "video_post_id")
    val videoPost: VideoPost

    @Column(name = "total_files")
    val totalFiles: Int

    @Column(name = "transcode_status")
    val transcodeStatus: ProcessStatus

    @Column(name = "encrypt_status")
    val encryptStatus: ProcessStatus

    @Column(name = "transcode_done_count")
    val transcodeDoneCount: Int

    @Column(name = "encrypt_done_count")
    val encryptDoneCount: Int

    @Column(name = "failed_count")
    val failedCount: Int

    @Column(name = "last_fail_reason")
    val lastFailReason: String?
}
