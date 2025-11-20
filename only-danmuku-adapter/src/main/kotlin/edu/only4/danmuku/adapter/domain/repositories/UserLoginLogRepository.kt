package edu.only4.danmuku.adapter.domain.repositories

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.domain.repo.AbstractJpaRepository

import edu.only4.danmuku.domain.aggregates.user_login_log.UserLoginLog

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * Repository for UserLoginLog aggregate
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 */
@Repository
interface UserLoginLogRepository : JpaRepository<UserLoginLog, Long>, JpaSpecificationExecutor<UserLoginLog> {

    @Component
    @Aggregate(aggregate = "UserLoginLog", name = "UserLoginLogRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class UserLoginLogJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<UserLoginLog>,
        jpaRepository: JpaRepository<UserLoginLog, Long>
    ) : AbstractJpaRepository<UserLoginLog, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )
}
