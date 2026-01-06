package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "video_file_upload_session")
interface VideoFileUploadSession : BaseEntity {

    @IdView
    val customerId: Long

    @OneToOne
    @JoinColumn(name = "customer_id")
    val customer: User

    @Column(name = "file_name")
    val fileName: String

    @Column(name = "chunks")
    val chunks: Int

    @Column(name = "chunk_index")
    val chunkIndex: Int

    @Column(name = "file_size")
    val fileSize: Long?

    @Column(name = "temp_dir")
    val tempDir: String?

    @Column(name = "status")
    val status: Int

    @Column(name = "duration")
    val duration: String?

    @Column(name = "expires_at")
    val expiresAt: Long?

}
