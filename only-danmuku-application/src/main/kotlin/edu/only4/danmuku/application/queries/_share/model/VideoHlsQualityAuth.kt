package edu.only4.danmuku.application.queries._share.model

import edu.only4.danmuku.domain.aggregates.video_hls_quality_auth.enums.QualityAuthPolicy
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "video_hls_quality_auth")
interface VideoHlsQualityAuth : BaseEntity {

    @Column(name = "file_id")
    val fileId: Long

    @Column(name = "quality")
    val quality: String

    @Column(name = "auth_policy")
    val authPolicy: QualityAuthPolicy

    @Column(name = "remark")
    val remark: String?
}
