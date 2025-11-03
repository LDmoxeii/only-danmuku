package edu.only4.danmuku.adapter.domain.repositories

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.domain.repo.AbstractJpaRepository

import edu.only4.danmuku.domain.aggregates.video_play_history.VideoPlayHistory

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * Repository for VideoPlayHistory aggregate
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/03
 */
@Repository
interface VideoPlayHistoryRepository : JpaRepository<VideoPlayHistory, Long>, JpaSpecificationExecutor<VideoPlayHistory> {

    @Component
    @Aggregate(aggregate = "VideoPlayHistory", name = "VideoPlayHistoryRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoPlayHistoryJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<VideoPlayHistory>,
        jpaRepository: JpaRepository<VideoPlayHistory, Long>
    ) : AbstractJpaRepository<VideoPlayHistory, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )
}
