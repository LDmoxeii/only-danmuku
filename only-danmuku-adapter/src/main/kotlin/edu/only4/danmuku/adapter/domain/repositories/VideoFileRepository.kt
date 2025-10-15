package edu.only4.danmuku.adapter.domain.repositories

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.domain.repo.AbstractJpaRepository
import com.only4.cap4k.ddd.domain.repo.querydsl.AbstractQuerydslRepository

import edu.only4.danmuku.domain.aggregates.video_file.VideoFile

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * Repository for VideoFile aggregate
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Repository
interface VideoFileRepository : JpaRepository<VideoFile, Long>, JpaSpecificationExecutor<VideoFile>,
    QuerydslPredicateExecutor<VideoFile> {

    @Component
    @Aggregate(aggregate = "VideoFile", name = "VideoFileRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoFileJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<VideoFile>,
        jpaRepository: JpaRepository<VideoFile, Long>
    ) : AbstractJpaRepository<VideoFile, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )
    @Component
    @Aggregate(aggregate = "VideoFile", name = "VideoFileQuerydslRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoFileQuerydslRepositoryAdapter(
        querydslPredicateExecutor: QuerydslPredicateExecutor<VideoFile>
    ) : AbstractQuerydslRepository<VideoFile>(
        querydslPredicateExecutor
    )
}
