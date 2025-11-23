package edu.only4.danmuku.adapter.domain.repositories

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.domain.repo.AbstractJpaRepository

import edu.only4.danmuku.domain.aggregates.video_post.VideoPost

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * Repository for VideoPost aggregate
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 */
@Repository
interface VideoPostRepository : JpaRepository<VideoPost, Long>, JpaSpecificationExecutor<VideoPost> {

    @Component
    @Aggregate(aggregate = "VideoPost", name = "VideoPostRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoPostJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<VideoPost>,
        jpaRepository: JpaRepository<VideoPost, Long>
    ) : AbstractJpaRepository<VideoPost, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )
}
