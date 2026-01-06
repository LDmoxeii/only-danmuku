package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "video_file_variant")
interface VideoFileVariant : BaseEntity {

    @IdView
    val videoFileId: Long

    @ManyToOne
    @JoinColumn(name = "parent_id")
    val videoFile: VideoFile

    @Column(name = "quality")
    val quality: String

    @Column(name = "width")
    val width: Int

    @Column(name = "height")
    val height: Int

    @Column(name = "video_bitrate_kbps")
    val videoBitrateKbps: Int

    @Column(name = "audio_bitrate_kbps")
    val audioBitrateKbps: Int

    @Column(name = "bandwidth_bps")
    val bandwidthBps: Int

    @Column(name = "playlist_path")
    val playlistPath: String

    @Column(name = "segment_prefix")
    val segmentPrefix: String?

    @Column(name = "segment_duration")
    val segmentDuration: Int?
}
