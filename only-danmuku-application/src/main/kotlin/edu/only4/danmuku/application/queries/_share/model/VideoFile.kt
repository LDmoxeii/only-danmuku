package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "video_file")
interface VideoFile : BaseEntity {

    @IdView
    val videoFilePostId: Long

    @OneToOne
    @JoinColumn(name = "customer_id")
    val customer: User

    @ManyToOne
    @JoinColumn(name = "video_id")
    val video: Video

    @OneToOne
    val videoFilePost: VideoFilePost

    @Column(name = "file_name")
    val fileName: String?

    @Column(name = "file_index")
    val fileIndex: Int

    @Column(name = "file_size")
    val fileSize: Long?

    @Column(name = "file_path")
    val filePath: String?

    @Column(name = "duration")
    val duration: Int?
}
