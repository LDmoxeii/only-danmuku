package edu.only4.danmuku.adapter.domain.repositories

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.domain.repo.AbstractJpaRepository
import edu.only4.danmuku.domain.aggregates.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * Repository for User aggregate
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 */
@Repository
interface UserRepository : JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Component
    @Aggregate(aggregate = "User", name = "UserRepo", type = Aggregate.TYPE_REPOSITORY, description = "")
    class UserJpaRepositoryAdapter(
        jpaSpecificationExecutor: JpaSpecificationExecutor<User>,
        jpaRepository: JpaRepository<User, Long>
    ) : AbstractJpaRepository<User, Long>(
        jpaSpecificationExecutor,
        jpaRepository
    )
}
