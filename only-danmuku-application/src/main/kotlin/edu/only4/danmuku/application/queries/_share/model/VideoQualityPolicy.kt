package edu.only4.danmuku.application.queries._share.model

import edu.only4.danmuku.domain.aggregates.video_quality_policy.enums.QualityAuthPolicy
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "video_quality_policy")
interface VideoQualityPolicy : BaseEntity {

    @Column(name = "video_id")
    val videoId: Long

    @Column(name = "file_index")
    val fileIndex: Int

    @Column(name = "quality")
    val quality: String

    @Column(name = "auth_policy")
    val authPolicy: QualityAuthPolicy

    @Column(name = "remark")
    val remark: String?
}
