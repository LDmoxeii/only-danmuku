package edu.only4.danmuku.adapter.domain.repositories

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.domain.repo.AbstractJpaRepository

import edu.only4.danmuku.domain.aggregates.video_hls_key_token.VideoHlsKeyToken

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * Repository for VideoHlsKeyToken aggregate
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 */
@Repository
interface VideoHlsKeyTokenRepository : JpaRepository<VideoHlsKeyToken, Long>, JpaSpecificationExecutor<VideoHlsKeyToken> {

    @Component
    @Aggregate(aggregate = "VideoHlsKeyToken", name = "VideoHlsKeyTokenRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoHlsKeyTokenJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<VideoHlsKeyToken>,
        jpaRepository: JpaRepository<VideoHlsKeyToken, Long>
    ) : AbstractJpaRepository<VideoHlsKeyToken, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )
}
