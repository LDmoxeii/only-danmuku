package edu.only4.danmuku.adapter.domain.repositories

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.domain.repo.AbstractJpaRepository
import edu.only4.danmuku.domain.aggregates.user_abnormal_operation_log.UserAbnormalOperationLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * Repository for UserAbnormalOperationLog aggregate
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 */
@Repository
interface UserAbnormalOperationLogRepository : JpaRepository<UserAbnormalOperationLog, Long>, JpaSpecificationExecutor<UserAbnormalOperationLog> {

    @Component
    @Aggregate(aggregate = "UserAbnormalOperationLog", name = "UserAbnormalOperationLogRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class UserAbnormalOperationLogJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<UserAbnormalOperationLog>,
        jpaRepository: JpaRepository<UserAbnormalOperationLog, Long>
    ) : AbstractJpaRepository<UserAbnormalOperationLog, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )
}
