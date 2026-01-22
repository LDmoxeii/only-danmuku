package edu.only4.danmuku.domain._share.meta.user_login_log

import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import edu.only4.danmuku.domain._share.meta.ExpressionBuilder
import edu.only4.danmuku.domain._share.meta.Field
import edu.only4.danmuku.domain._share.meta.OrderBuilder
import edu.only4.danmuku.domain._share.meta.PredicateBuilder
import edu.only4.danmuku.domain._share.meta.SchemaSpecification
import edu.only4.danmuku.domain._share.meta.SubqueryConfigure
import edu.only4.danmuku.domain.aggregates.user.enums.UserType
import edu.only4.danmuku.domain.aggregates.user_login_log.UserLoginLog
import edu.only4.danmuku.domain.aggregates.user_login_log.enums.LoginResult
import edu.only4.danmuku.domain.aggregates.user_login_log.enums.LoginType
import jakarta.persistence.criteria.*
import org.springframework.data.jpa.domain.Specification

/**
 * Schema 类用于类型安全的 JPA Criteria 查询
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 */
class SUserLoginLog(
    private val root: Path<UserLoginLog>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {

        val id = "id"

        val userId = "userId"

        val userType = "userType"

        val loginName = "loginName"

        val loginType = "loginType"

        val result = "result"

        val ip = "ip"

        val userAgent = "userAgent"

        val reason = "reason"

        val occurTime = "occurTime"

        val createUserId = "createUserId"

        val createBy = "createBy"

        val createTime = "createTime"

        val updateUserId = "updateUserId"

        val updateBy = "updateBy"

        val updateTime = "updateTime"

        val deleted = "deleted"

    }

    companion object {

        val props = PROPERTY_NAMES()

        /**
         * 构建查询条件
         *
         * @param builder where条件构造器
         * @return
         */
        @JvmStatic
        fun specify(builder: PredicateBuilder<SUserLoginLog>): Specification<UserLoginLog> {
            return specify(builder, false, emptyList())
        }

        /**
         * 构建查询条件
         *
         * @param builder  where条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun specify(builder: PredicateBuilder<SUserLoginLog>, distinct: Boolean): Specification<UserLoginLog> {
            return specify(builder, distinct, emptyList())
        }

        /**
         * 构建查询条件
         *
         * @param builder       where条件构造器
         * @param orderBuilders 排序条件构造器
         * @return
         */
        @JvmStatic
        fun specify(
            builder: PredicateBuilder<SUserLoginLog>,
            vararg orderBuilders: OrderBuilder<SUserLoginLog>,
        ): Specification<UserLoginLog> {
            return specify(builder, orderBuilders.toList())
        }

        /**
         * 构建查询条件
         *
         * @param builder       where条件构造器
         * @param orderBuilders 排序条件构造器
         * @return
         */
        @JvmStatic
        fun specify(
            builder: PredicateBuilder<SUserLoginLog>,
            orderBuilders: List<OrderBuilder<SUserLoginLog>>,
        ): Specification<UserLoginLog> {
            return specify(builder, false, orderBuilders)
        }

        /**
        * 构建查询条件
        *
        * @param builder       where条件构造器
        * @param distinct      是否去重
        * @param orderBuilders 排序条件构造器
        * @return
        */
        @JvmStatic
        fun specify(
            builder: PredicateBuilder<SUserLoginLog>,
            distinct: Boolean,
            vararg orderBuilders: OrderBuilder<SUserLoginLog>,
        ): Specification<UserLoginLog> {
            return specify(builder, distinct, orderBuilders.toList())
        }

        /**
        * 构建查询条件
        *
        * @param builder       where条件构造器
        * @param distinct      是否去重
        * @param orderBuilders 排序条件构造器
        * @return
        */
        @JvmStatic
        fun specify(
            builder: PredicateBuilder<SUserLoginLog>,
            distinct: Boolean,
            orderBuilders: List<OrderBuilder<SUserLoginLog>>,
        ): Specification<UserLoginLog> {
            return specify { schema, criteriaQuery, criteriaBuilder ->
                criteriaQuery.where(builder.build(schema))
                criteriaQuery.distinct(distinct)
                if (orderBuilders.isNotEmpty()) {
                    criteriaQuery.orderBy(orderBuilders.map { it.build(schema) })
                }
                null
            }
        }

        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun specify(specifier: SchemaSpecification<UserLoginLog, SUserLoginLog>): Specification<UserLoginLog> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SUserLoginLog(root, criteriaBuilder)
                specifier.toPredicate(schema, criteriaQuery, criteriaBuilder)
            }
        }

        /**
        * 构建子查询
        *
        * @param resultClass      返回结果类型
        * @param selectBuilder    select条件构造器
        * @param predicateBuilder where条件构造器
        * @param criteriaBuilder
        * @param criteriaQuery
        * @param <E>
        * @return
        */
        @JvmStatic
        fun <E> subquery(
            resultClass: Class<E>,
            selectBuilder: ExpressionBuilder<SUserLoginLog, E>,
            predicateBuilder: PredicateBuilder<SUserLoginLog>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            return subquery(resultClass, { sq, schema ->
                sq.select(selectBuilder.build(schema))
                sq.where(predicateBuilder.build(schema))
            }, criteriaBuilder, criteriaQuery)
        }

        /**
         * 构建子查询
         *
         * @param resultClass       返回结果类型
         * @param subqueryConfigure 子查询配置
         * @param criteriaBuilder
         * @param criteriaQuery
         * @param <E>
         * @return
         */
        @JvmStatic
        fun <E> subquery(
            resultClass: Class<E>,
            subqueryConfigure: SubqueryConfigure<E, SUserLoginLog>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(UserLoginLog::class.java)
            val schema = SUserLoginLog(root, criteriaBuilder)
            subqueryConfigure.configure(sq, schema)
            return sq
        }
        /**
         * 构建查询条件
         *
         * @param id 主键
         * @return
         */
        @JvmStatic
        fun predicateById(id: Any): JpaPredicate<UserLoginLog> {
            return JpaPredicate.byId(UserLoginLog::class.java, id)
        }

        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): JpaPredicate<UserLoginLog> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(UserLoginLog::class.java, ids as Iterable<Any>)
        }

        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): JpaPredicate<UserLoginLog> {
            return JpaPredicate.byIds(UserLoginLog::class.java, ids.toList())
        }

        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: PredicateBuilder<SUserLoginLog>): JpaPredicate<UserLoginLog> {
            return JpaPredicate.bySpecification(UserLoginLog::class.java, specify(builder))
        }

        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: PredicateBuilder<SUserLoginLog>, distinct: Boolean): JpaPredicate<UserLoginLog> {
            return JpaPredicate.bySpecification(UserLoginLog::class.java, specify(builder, distinct))
        }

        /**
         * 构建查询条件
         *
         * @param builder       查询条件构造器
         * @param orderBuilders 排序构造器
         * @return
         */
        @JvmStatic
        fun predicate(
            builder: PredicateBuilder<SUserLoginLog>,
            orderBuilders: List<OrderBuilder<SUserLoginLog>>,
        ): JpaPredicate<UserLoginLog> {
            return JpaPredicate.bySpecification(UserLoginLog::class.java, specify(builder, false, orderBuilders))
        }

        /**
         * 构建查询条件
         *
         * @param builder       查询条件构造器
         * @param orderBuilders 排序构造器
         * @return
         */
        @JvmStatic
        fun predicate(
            builder: PredicateBuilder<SUserLoginLog>,
            vararg orderBuilders: OrderBuilder<SUserLoginLog>,
        ): JpaPredicate<UserLoginLog> {
            return JpaPredicate.bySpecification(UserLoginLog::class.java, specify(builder, false, *orderBuilders))
        }

        /**
         * 构建查询条件
         *
         * @param builder       查询条件构造器
         * @param distinct      是否去重
         * @param orderBuilders 排序构造器
         * @return
         */
        @JvmStatic
        fun predicate(
            builder: PredicateBuilder<SUserLoginLog>,
            distinct: Boolean,
            orderBuilders: List<OrderBuilder<SUserLoginLog>>,
        ): JpaPredicate<UserLoginLog> {
            return JpaPredicate.bySpecification(UserLoginLog::class.java, specify(builder, distinct, orderBuilders))
        }

        /**
         * 构建查询条件
         *
         * @param builder       查询条件构造器
         * @param distinct      是否去重
         * @param orderBuilders 排序构造器
         * @return
         */
        @JvmStatic
        fun predicate(
            builder: PredicateBuilder<SUserLoginLog>,
            distinct: Boolean,
            vararg orderBuilders: OrderBuilder<SUserLoginLog>,
        ): JpaPredicate<UserLoginLog> {
            return JpaPredicate.bySpecification(UserLoginLog::class.java, specify(builder, distinct, *orderBuilders))
        }

        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: SchemaSpecification<UserLoginLog, SUserLoginLog>): JpaPredicate<UserLoginLog> {
            return JpaPredicate.bySpecification(UserLoginLog::class.java, specify(specifier))
        }

    }

    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<UserLoginLog> = root


    /**
     * ID
     */
    val id: Field<Long> by lazy {
        Field(root.get("id"), criteriaBuilder)
    }


    /**
     * 用户ID，可为空（未知用户）
     */
    val userId: Field<Long?> by lazy {
        Field(root.get("userId"), criteriaBuilder)
    }


    /**
     * 用户类型
     */
    val userType: Field<UserType> by lazy {
        Field(root.get("userType"), criteriaBuilder)
    }


    /**
     * 登录名（邮箱或手机号）
     */
    val loginName: Field<String> by lazy {
        Field(root.get("loginName"), criteriaBuilder)
    }


    /**
     * 登录类型
     */
    val loginType: Field<LoginType> by lazy {
        Field(root.get("loginType"), criteriaBuilder)
    }


    /**
     * 登录结果
     */
    val result: Field<LoginResult> by lazy {
        Field(root.get("result"), criteriaBuilder)
    }


    /**
     * 登录IP
     */
    val ip: Field<String> by lazy {
        Field(root.get("ip"), criteriaBuilder)
    }


    /**
     * User-Agent
     */
    val userAgent: Field<String?> by lazy {
        Field(root.get("userAgent"), criteriaBuilder)
    }


    /**
     * 失败原因描述
     */
    val reason: Field<String?> by lazy {
        Field(root.get("reason"), criteriaBuilder)
    }


    /**
     * 发生时间（秒级或毫秒级时间戳）
     */
    val occurTime: Field<Long> by lazy {
        Field(root.get("occurTime"), criteriaBuilder)
    }


    /**
     * 创建人ID
     */
    val createUserId: Field<Long?> by lazy {
        Field(root.get("createUserId"), criteriaBuilder)
    }


    /**
     * 创建人名称
     */
    val createBy: Field<String?> by lazy {
        Field(root.get("createBy"), criteriaBuilder)
    }


    /**
     * 创建时间
     */
    val createTime: Field<Long?> by lazy {
        Field(root.get("createTime"), criteriaBuilder)
    }


    /**
     * 更新人ID
     */
    val updateUserId: Field<Long?> by lazy {
        Field(root.get("updateUserId"), criteriaBuilder)
    }


    /**
     * 更新人名称
     */
    val updateBy: Field<String?> by lazy {
        Field(root.get("updateBy"), criteriaBuilder)
    }


    /**
     * 更新时间
     */
    val updateTime: Field<Long?> by lazy {
        Field(root.get("updateTime"), criteriaBuilder)
    }


    /**
     * 删除标识 0：未删除 id：已删除
     */
    val deleted: Field<Long> by lazy {
        Field(root.get("deleted"), criteriaBuilder)
    }



    /**
     * 满足所有条件
     * @param restrictions
     * @return
     */
    fun all(vararg restrictions: Predicate): Predicate {
        return criteriaBuilder.and(*restrictions)
    }

    /**
     * 满足任一条件
     * @param restrictions
     * @return
     */
    fun any(vararg restrictions: Predicate): Predicate {
        return criteriaBuilder.or(*restrictions)
    }

    /**
     * 满足所有条件（过滤 null）
     * 类似 Jimmer 的 where { } 自动过滤 null 的行为
     */
    fun allNotNull(vararg restrictions: Predicate?): Predicate? {
        val nonNullRestrictions = restrictions.filterNotNull().toTypedArray()
        return when {
            nonNullRestrictions.isEmpty() -> null
            nonNullRestrictions.size == 1 -> nonNullRestrictions[0]
            else -> criteriaBuilder.and(*nonNullRestrictions)
        }
    }

    /**
     * 满足任一条件（过滤 null）
     */
    fun anyNotNull(vararg restrictions: Predicate?): Predicate? {
        val nonNullRestrictions = restrictions.filterNotNull().toTypedArray()
        return when {
            nonNullRestrictions.isEmpty() -> null
            nonNullRestrictions.size == 1 -> nonNullRestrictions[0]
            else -> criteriaBuilder.or(*nonNullRestrictions)
        }
    }

    /**
     * NOT 操作
     */
    fun not(restriction: Predicate): Predicate = criteriaBuilder.not(restriction)

    /**
     * 指定条件
     * @param builder
     * @return
     */
    fun spec(builder: PredicateBuilder<SUserLoginLog>): Predicate {
        return builder.build(this)
    }
}
