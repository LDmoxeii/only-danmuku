package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

/**
 * 视频文件信息;@P=video;
 */
@Entity
@Table(name = "video_file")
interface VideoFile : BaseEntity {
    @OneToOne
    @JoinColumn(name = "customer_id")
    val customer: User

    @ManyToOne
    @JoinColumn(name = "video_id")
    val video: Video

    /**
     * 文件名
     */
    @Column(name = "file_name")
    val fileName: String?

    /**
     * 文件索引
     */
    @Column(name = "file_index")
    val fileIndex: String

    /**
     * 文件大小
     */
    @Column(name = "file_size")
    val fileSize: Long?

    /**
     * 文件路径
     */
    @Column(name = "file_path")
    val filePath: String?

    /**
     * 持续时间（秒）
     */
    @Column(name = "duration")
    val duration: String?
}
