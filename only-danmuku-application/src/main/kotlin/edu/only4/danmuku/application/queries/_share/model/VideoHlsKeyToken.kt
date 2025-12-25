package edu.only4.danmuku.application.queries._share.model

import edu.only4.danmuku.domain.aggregates.video_hls_key_token.enums.EncryptTokenStatus
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "video_hls_key_token")
interface VideoHlsKeyToken : BaseEntity {

    @Column(name = "file_id")
    val fileId: Long

    @Column(name = "key_version")
    val keyVersion: Int

    @Column(name = "allowed_qualities")
    val allowedQualities: String?

    @Column(name = "token_hash")
    val tokenHash: String

    @Column(name = "audience")
    val audience: String?

    @Column(name = "expire_time")
    val expireTime: Long

    @Column(name = "max_use")
    val maxUse: Int

    @Column(name = "used_count")
    val usedCount: Int

    @Column(name = "status")
    val status: EncryptTokenStatus

    @Column(name = "issue_ip")
    val issueIp: String?
}
