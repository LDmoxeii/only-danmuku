package edu.only4.danmuku.adapter.domain.repositories

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.domain.repo.AbstractJpaRepository
import edu.only4.danmuku.domain.aggregates.video_danmuku.VideoDanmuku
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * Repository for VideoDanmuku aggregate
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 */
@Repository
interface VideoDanmukuRepository : JpaRepository<VideoDanmuku, Long>, JpaSpecificationExecutor<VideoDanmuku> {

    @Component
    @Aggregate(aggregate = "VideoDanmuku", name = "VideoDanmukuRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class VideoDanmukuJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<VideoDanmuku>,
        jpaRepository: JpaRepository<VideoDanmuku, Long>
    ) : AbstractJpaRepository<VideoDanmuku, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )
}
