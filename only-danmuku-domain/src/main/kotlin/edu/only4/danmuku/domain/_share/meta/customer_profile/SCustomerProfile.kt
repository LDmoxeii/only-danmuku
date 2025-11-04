package edu.only4.danmuku.domain._share.meta.customer_profile

import com.only4.cap4k.ddd.domain.repo.JpaPredicate

import edu.only4.danmuku.domain._share.meta.ExpressionBuilder
import edu.only4.danmuku.domain._share.meta.Field
import edu.only4.danmuku.domain._share.meta.OrderBuilder
import edu.only4.danmuku.domain._share.meta.PredicateBuilder
import edu.only4.danmuku.domain._share.meta.SchemaSpecification
import edu.only4.danmuku.domain._share.meta.SubqueryConfigure
import edu.only4.danmuku.domain.aggregates.customer_profile.CustomerProfile
import edu.only4.danmuku.domain.aggregates.customer_profile.enums.SexType
import edu.only4.danmuku.domain.aggregates.customer_profile.enums.ThemeType

import jakarta.persistence.criteria.*

import org.springframework.data.jpa.domain.Specification

/**
 * 用户信息;
 *
 * Schema 类用于类型安全的 JPA Criteria 查询
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/11/04
 */
class SCustomerProfile(
    private val root: Path<CustomerProfile>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {

        val id = "id"

        val userId = "userId"

        val nickName = "nickName"

        val avatar = "avatar"

        val email = "email"

        val sex = "sex"

        val birthday = "birthday"

        val school = "school"

        val personIntroduction = "personIntroduction"

        val noticeInfo = "noticeInfo"

        val totalCoinCount = "totalCoinCount"

        val currentCoinCount = "currentCoinCount"

        val theme = "theme"

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
        fun specify(builder: PredicateBuilder<SCustomerProfile>): Specification<CustomerProfile> {
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
        fun specify(builder: PredicateBuilder<SCustomerProfile>, distinct: Boolean): Specification<CustomerProfile> {
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
            builder: PredicateBuilder<SCustomerProfile>,
            vararg orderBuilders: OrderBuilder<SCustomerProfile>,
        ): Specification<CustomerProfile> {
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
            builder: PredicateBuilder<SCustomerProfile>,
            orderBuilders: List<OrderBuilder<SCustomerProfile>>,
        ): Specification<CustomerProfile> {
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
            builder: PredicateBuilder<SCustomerProfile>,
            distinct: Boolean,
            vararg orderBuilders: OrderBuilder<SCustomerProfile>,
        ): Specification<CustomerProfile> {
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
            builder: PredicateBuilder<SCustomerProfile>,
            distinct: Boolean,
            orderBuilders: List<OrderBuilder<SCustomerProfile>>,
        ): Specification<CustomerProfile> {
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
        fun specify(specifier: SchemaSpecification<CustomerProfile, SCustomerProfile>): Specification<CustomerProfile> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SCustomerProfile(root, criteriaBuilder)
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
            selectBuilder: ExpressionBuilder<SCustomerProfile, E>,
            predicateBuilder: PredicateBuilder<SCustomerProfile>,
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
            subqueryConfigure: SubqueryConfigure<E, SCustomerProfile>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(CustomerProfile::class.java)
            val schema = SCustomerProfile(root, criteriaBuilder)
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
        fun predicateById(id: Any): JpaPredicate<CustomerProfile> {
            return JpaPredicate.byId(CustomerProfile::class.java, id)
        }

        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): JpaPredicate<CustomerProfile> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(CustomerProfile::class.java, ids as Iterable<Any>)
        }

        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): JpaPredicate<CustomerProfile> {
            return JpaPredicate.byIds(CustomerProfile::class.java, ids.toList())
        }

        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: PredicateBuilder<SCustomerProfile>): JpaPredicate<CustomerProfile> {
            return JpaPredicate.bySpecification(CustomerProfile::class.java, specify(builder))
        }

        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: PredicateBuilder<SCustomerProfile>, distinct: Boolean): JpaPredicate<CustomerProfile> {
            return JpaPredicate.bySpecification(CustomerProfile::class.java, specify(builder, distinct))
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
            builder: PredicateBuilder<SCustomerProfile>,
            orderBuilders: List<OrderBuilder<SCustomerProfile>>,
        ): JpaPredicate<CustomerProfile> {
            return JpaPredicate.bySpecification(CustomerProfile::class.java, specify(builder, false, orderBuilders))
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
            builder: PredicateBuilder<SCustomerProfile>,
            vararg orderBuilders: OrderBuilder<SCustomerProfile>,
        ): JpaPredicate<CustomerProfile> {
            return JpaPredicate.bySpecification(CustomerProfile::class.java, specify(builder, false, *orderBuilders))
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
            builder: PredicateBuilder<SCustomerProfile>,
            distinct: Boolean,
            orderBuilders: List<OrderBuilder<SCustomerProfile>>,
        ): JpaPredicate<CustomerProfile> {
            return JpaPredicate.bySpecification(CustomerProfile::class.java, specify(builder, distinct, orderBuilders))
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
            builder: PredicateBuilder<SCustomerProfile>,
            distinct: Boolean,
            vararg orderBuilders: OrderBuilder<SCustomerProfile>,
        ): JpaPredicate<CustomerProfile> {
            return JpaPredicate.bySpecification(CustomerProfile::class.java, specify(builder, distinct, *orderBuilders))
        }

        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: SchemaSpecification<CustomerProfile, SCustomerProfile>): JpaPredicate<CustomerProfile> {
            return JpaPredicate.bySpecification(CustomerProfile::class.java, specify(specifier))
        }

    }

    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<CustomerProfile> = root


    /**
     * ID
     */
    val id: Field<Long> by lazy {
        Field(root.get("id"), criteriaBuilder)
    }


    /**
     * 用户ID
     */
    val userId: Field<Long> by lazy {
        Field(root.get("userId"), criteriaBuilder)
    }


    /**
     * 昵称
     */
    val nickName: Field<String> by lazy {
        Field(root.get("nickName"), criteriaBuilder)
    }


    /**
     * 头像
     */
    val avatar: Field<String?> by lazy {
        Field(root.get("avatar"), criteriaBuilder)
    }


    /**
     * 邮箱
     */
    val email: Field<String> by lazy {
        Field(root.get("email"), criteriaBuilder)
    }


    /**
     * 性别
     */
    val sex: Field<SexType> by lazy {
        Field(root.get("sex"), criteriaBuilder)
    }


    /**
     * 出生日期
     */
    val birthday: Field<String?> by lazy {
        Field(root.get("birthday"), criteriaBuilder)
    }


    /**
     * 学校
     */
    val school: Field<String?> by lazy {
        Field(root.get("school"), criteriaBuilder)
    }


    /**
     * 个人简介
     */
    val personIntroduction: Field<String?> by lazy {
        Field(root.get("personIntroduction"), criteriaBuilder)
    }


    /**
     * 空间公告
     */
    val noticeInfo: Field<String?> by lazy {
        Field(root.get("noticeInfo"), criteriaBuilder)
    }


    /**
     * 硬币总数量
     */
    val totalCoinCount: Field<Int> by lazy {
        Field(root.get("totalCoinCount"), criteriaBuilder)
    }


    /**
     * 当前硬币数
     */
    val currentCoinCount: Field<Int> by lazy {
        Field(root.get("currentCoinCount"), criteriaBuilder)
    }


    /**
     * 主题
     */
    val theme: Field<ThemeType> by lazy {
        Field(root.get("theme"), criteriaBuilder)
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
    fun spec(builder: PredicateBuilder<SCustomerProfile>): Predicate {
        return builder.build(this)
    }
}
