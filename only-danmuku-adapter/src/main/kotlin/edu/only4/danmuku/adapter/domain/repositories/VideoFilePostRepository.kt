package edu.only4.danmuku.adapter.domain.repositories

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.domain.repo.AbstractJpaRepository

import edu.only4.danmuku.domain.aggregates.video_file_post.VideoFilePost

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * Repository for VideoFilePost aggregate
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 */
@Repository
interface VideoFilePostRepository : JpaRepository<VideoFilePost, Long>, JpaSpecificationExecutor<VideoFilePost> {

    @Component
    @Aggregate(aggregate = "VideoFilePost", name = "VideoFilePostRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoFilePostJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<VideoFilePost>,
        jpaRepository: JpaRepository<VideoFilePost, Long>
    ) : AbstractJpaRepository<VideoFilePost, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )
}
