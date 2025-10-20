package edu.only4.danmuku.domain._share.meta.customer_profile

import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePredicate
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import com.only4.cap4k.ddd.domain.repo.querydsl.QuerydslPredicate

import com.querydsl.core.types.OrderSpecifier
import edu.only4.danmuku.domain._share.meta.Schema
import edu.only4.danmuku.domain.aggregates.customer_profile.AggCustomerProfile
import edu.only4.danmuku.domain.aggregates.customer_profile.CustomerProfile
import edu.only4.danmuku.domain.aggregates.customer_profile.QCustomerProfile
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
 * @date 2025/10/20
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
        fun specify(builder: Schema.PredicateBuilder<SCustomerProfile>): Specification<CustomerProfile> {
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
        fun specify(builder: Schema.PredicateBuilder<SCustomerProfile>, distinct: Boolean): Specification<CustomerProfile> {
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
            builder: Schema.PredicateBuilder<SCustomerProfile>,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerProfile>,
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
            builder: Schema.PredicateBuilder<SCustomerProfile>,
            orderBuilders: List<Schema.OrderBuilder<SCustomerProfile>>,
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
            builder: Schema.PredicateBuilder<SCustomerProfile>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerProfile>,
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
            builder: Schema.PredicateBuilder<SCustomerProfile>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SCustomerProfile>>,
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
        fun specify(specifier: Schema.Specification<CustomerProfile, SCustomerProfile>): Specification<CustomerProfile> {
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
            selectBuilder: Schema.ExpressionBuilder<SCustomerProfile, E>,
            predicateBuilder: Schema.PredicateBuilder<SCustomerProfile>,
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
            subqueryConfigure: Schema.SubqueryConfigure<E, SCustomerProfile>,
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
        fun predicateById(id: Any): AggregatePredicate<AggCustomerProfile, CustomerProfile> {
            return JpaPredicate.byId(CustomerProfile::class.java, id).toAggregatePredicate(AggCustomerProfile::class.java)
        }

        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): AggregatePredicate<AggCustomerProfile, CustomerProfile> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(CustomerProfile::class.java, ids as Iterable<Any>).toAggregatePredicate(AggCustomerProfile::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): AggregatePredicate<AggCustomerProfile, CustomerProfile> {
            return JpaPredicate.byIds(CustomerProfile::class.java, ids.toList()).toAggregatePredicate(AggCustomerProfile::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SCustomerProfile>): AggregatePredicate<AggCustomerProfile, CustomerProfile> {
            return JpaPredicate.bySpecification(CustomerProfile::class.java, specify(builder)).toAggregatePredicate(AggCustomerProfile::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SCustomerProfile>, distinct: Boolean): AggregatePredicate<AggCustomerProfile, CustomerProfile> {
            return JpaPredicate.bySpecification(CustomerProfile::class.java, specify(builder, distinct)).toAggregatePredicate(AggCustomerProfile::class.java)
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
            builder: Schema.PredicateBuilder<SCustomerProfile>,
            orderBuilders: List<Schema.OrderBuilder<SCustomerProfile>>,
        ): AggregatePredicate<AggCustomerProfile, CustomerProfile> {
            return JpaPredicate.bySpecification(CustomerProfile::class.java, specify(builder, false, orderBuilders)).toAggregatePredicate(AggCustomerProfile::class.java)
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
            builder: Schema.PredicateBuilder<SCustomerProfile>,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerProfile>,
        ): AggregatePredicate<AggCustomerProfile, CustomerProfile> {
            return JpaPredicate.bySpecification(CustomerProfile::class.java, specify(builder, false, *orderBuilders)).toAggregatePredicate(AggCustomerProfile::class.java)
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
            builder: Schema.PredicateBuilder<SCustomerProfile>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SCustomerProfile>>,
        ): AggregatePredicate<AggCustomerProfile, CustomerProfile> {
            return JpaPredicate.bySpecification(CustomerProfile::class.java, specify(builder, distinct, orderBuilders)).toAggregatePredicate(AggCustomerProfile::class.java)
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
            builder: Schema.PredicateBuilder<SCustomerProfile>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerProfile>,
        ): AggregatePredicate<AggCustomerProfile, CustomerProfile> {
            return JpaPredicate.bySpecification(CustomerProfile::class.java, specify(builder, distinct, *orderBuilders)).toAggregatePredicate(AggCustomerProfile::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: Schema.Specification<CustomerProfile, SCustomerProfile>): AggregatePredicate<AggCustomerProfile, CustomerProfile> {
            return JpaPredicate.bySpecification(CustomerProfile::class.java, specify(specifier)).toAggregatePredicate(AggCustomerProfile::class.java)
        }
       /**
         * 构建querydsl查询条件
         *
         * @param filterBuilder          查询条件构造器
         * @param orderSpecifierBuilders 排序构造器
         * @return
         */
        @JvmStatic
        fun querydsl(
            filterBuilder: java.util.function.Function<QCustomerProfile, com.querydsl.core.types.Predicate>,
            vararg orderSpecifierBuilders: java.util.function.Function<QCustomerProfile, OrderSpecifier<*>>,
        ): AggregatePredicate<AggCustomerProfile, CustomerProfile> {
            return QuerydslPredicate.of(CustomerProfile::class.java)
                .where(filterBuilder.apply(QCustomerProfile.customerProfile))
                .orderBy(*orderSpecifierBuilders.map { it.apply(QCustomerProfile.customerProfile) }.toTypedArray())
                .toAggregatePredicate(AggCustomerProfile::class.java)
        }

        /**
         * 构建querydsl查询条件
         *
         * @param filter          查询条件构造器
         * @param orderSpecifiers 排序构造器
         * @return
         */
        @JvmStatic
        fun querydsl(
            filter: com.querydsl.core.types.Predicate,
            vararg orderSpecifiers: OrderSpecifier<*>,
        ): AggregatePredicate<AggCustomerProfile, CustomerProfile> {
            return QuerydslPredicate.of(CustomerProfile::class.java)
                .where(filter)
                .orderBy(*orderSpecifiers)
                .toAggregatePredicate(AggCustomerProfile::class.java)
        }
    }

    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<CustomerProfile> = root


    /**
     * ID
     */
    val id: Schema.Field<Long> by lazy {
        Schema.Field(root.get("id"), criteriaBuilder)
    }


    /**
     * 用户ID
     */
    val userId: Schema.Field<Long> by lazy {
        Schema.Field(root.get("userId"), criteriaBuilder)
    }


    /**
     * 昵称
     */
    val nickName: Schema.Field<String> by lazy {
        Schema.Field(root.get("nickName"), criteriaBuilder)
    }


    /**
     * 头像
     */
    val avatar: Schema.Field<String?> by lazy {
        Schema.Field(root.get("avatar"), criteriaBuilder)
    }


    /**
     * 邮箱
     */
    val email: Schema.Field<String> by lazy {
        Schema.Field(root.get("email"), criteriaBuilder)
    }


    /**
     * 性别
     */
    val sex: Schema.Field<SexType> by lazy {
        Schema.Field(root.get("sex"), criteriaBuilder)
    }


    /**
     * 出生日期
     */
    val birthday: Schema.Field<String?> by lazy {
        Schema.Field(root.get("birthday"), criteriaBuilder)
    }


    /**
     * 学校
     */
    val school: Schema.Field<String?> by lazy {
        Schema.Field(root.get("school"), criteriaBuilder)
    }


    /**
     * 个人简介
     */
    val personIntroduction: Schema.Field<String?> by lazy {
        Schema.Field(root.get("personIntroduction"), criteriaBuilder)
    }


    /**
     * 空间公告
     */
    val noticeInfo: Schema.Field<String?> by lazy {
        Schema.Field(root.get("noticeInfo"), criteriaBuilder)
    }


    /**
     * 硬币总数量
     */
    val totalCoinCount: Schema.Field<Int> by lazy {
        Schema.Field(root.get("totalCoinCount"), criteriaBuilder)
    }


    /**
     * 当前硬币数
     */
    val currentCoinCount: Schema.Field<Int> by lazy {
        Schema.Field(root.get("currentCoinCount"), criteriaBuilder)
    }


    /**
     * 主题
     */
    val theme: Schema.Field<ThemeType> by lazy {
        Schema.Field(root.get("theme"), criteriaBuilder)
    }


    /**
     * 创建人ID
     */
    val createUserId: Schema.Field<Long?> by lazy {
        Schema.Field(root.get("createUserId"), criteriaBuilder)
    }


    /**
     * 创建人名称
     */
    val createBy: Schema.Field<String?> by lazy {
        Schema.Field(root.get("createBy"), criteriaBuilder)
    }


    /**
     * 创建时间
     */
    val createTime: Schema.Field<Long?> by lazy {
        Schema.Field(root.get("createTime"), criteriaBuilder)
    }


    /**
     * 更新人ID
     */
    val updateUserId: Schema.Field<Long?> by lazy {
        Schema.Field(root.get("updateUserId"), criteriaBuilder)
    }


    /**
     * 更新人名称
     */
    val updateBy: Schema.Field<String?> by lazy {
        Schema.Field(root.get("updateBy"), criteriaBuilder)
    }


    /**
     * 更新时间
     */
    val updateTime: Schema.Field<Long?> by lazy {
        Schema.Field(root.get("updateTime"), criteriaBuilder)
    }


    /**
     * 删除标识 0：未删除 id：已删除
     */
    val deleted: Schema.Field<Boolean> by lazy {
        Schema.Field(root.get("deleted"), criteriaBuilder)
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
     * 指定条件
     * @param builder
     * @return
     */
    fun spec(builder: Schema.PredicateBuilder<SCustomerProfile>): Predicate
    {
        return builder.build(this)
    }
}
