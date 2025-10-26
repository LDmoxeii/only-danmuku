package edu.only4.danmuku.adapter.domain.repositories

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.domain.repo.AbstractJpaRepository

import edu.only4.danmuku.domain.aggregates.video.Video

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * Repository for Video aggregate
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/26
 */
@Repository
interface VideoRepository : JpaRepository<Video, Long>, JpaSpecificationExecutor<Video> {

    @Component
    @Aggregate(aggregate = "Video", name = "VideoRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<Video>,
        jpaRepository: JpaRepository<Video, Long>
    ) : AbstractJpaRepository<Video, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )
}
