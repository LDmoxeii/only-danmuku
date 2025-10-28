package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "video_file_upload_session")
interface VideoFileUploadSession {

    @Id
    val id: Long

    @IdView
    val customerId: Long

    @OneToOne
    @Column(name = "customer_id")
    val customer: User

    @Column(name = "file_name")
    val fileName: String

    @Column(name = "chunks")
    val chunks: Int

    @Column(name = "chunk_index")
    val chunkIndex: Int

    @Column(name = "file_size")
    val fileSize: Long?

    @Column(name = "temp_path")
    val tempPath: String?

    @Column(name = "status")
    val status: Int

    @Column(name = "duration")
    val duration: String?

    @Column(name = "create_time")
    val createTime: Long?

    @Column(name = "update_time")
    val updateTime: Long?

    @Column(name = "expires_at")
    val expiresAt: Long?

    @LogicalDeleted
    val deleted: Long
}
