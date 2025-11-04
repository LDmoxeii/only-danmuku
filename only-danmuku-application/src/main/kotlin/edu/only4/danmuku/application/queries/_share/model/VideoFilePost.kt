package edu.only4.danmuku.application.queries._share.model

import edu.only4.danmuku.domain.aggregates.video_post.enums.TransferResult
import edu.only4.danmuku.domain.aggregates.video_post.enums.UpdateType
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

    @OneToOne
    @JoinColumn(name = "uploadId")
    val upload: VideoFileUploadSession

    @OneToOne
    @JoinColumn(name = "customer_id")
    val customer: CustomerProfile

    @ManyToOne
    @JoinColumn(name = "video_id")
    val video: VideoPost

    @Column(name = "file_index")
    val fileIndex: Int

    @Column(name = "file_name")
    val fileName: String?

    @Column(name = "file_size")
    val fileSize: Long?

    @Column(name = "file_path")
    val filePath: String?

    /**
     * 更新类型 @E=0:UNKNOW:未知类型|1:NO_UPDATE:无更新|2:HAS_UPDATE:有更新;@T=UpdateType
     */
    @Column(name = "update_type")
    val updateType: UpdateType

    /**
     * 转码结果 @E=0:UNKNOW:未知结果|1:TRANSCODING:转码中|2:SUCCESS:转码成功|3:FAILED:转码失败;@T=TransferResult
     */
    @Column(name = "transfer_result")
    val transferResult: TransferResult

    @Column(name = "duration")
    val duration: Int?
}
