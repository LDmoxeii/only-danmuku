package edu.only4.danmuku.domain._share.meta.customer_focus

import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePredicate
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import com.only4.cap4k.ddd.domain.repo.querydsl.QuerydslPredicate
import com.querydsl.core.types.OrderSpecifier
import edu.only4.danmuku.domain._share.meta.*
import edu.only4.danmuku.domain.aggregates.customer_focus.AggCustomerFocus
import edu.only4.danmuku.domain.aggregates.customer_focus.CustomerFocus
import edu.only4.danmuku.domain.aggregates.customer_focus.QCustomerFocus
import jakarta.persistence.criteria.*
import org.springframework.data.jpa.domain.Specification

/**
 * 用户关注;
 *
 * Schema 类用于类型安全的 JPA Criteria 查询
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/10/20
 */
class SCustomerFocus(
    private val root: Path<CustomerFocus>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {

        val id = "id"

        val customerId = "customerId"

        val focusCustomerId = "focusCustomerId"

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
        fun specify(builder: PredicateBuilder<SCustomerFocus>): Specification<CustomerFocus> {
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
        fun specify(builder: PredicateBuilder<SCustomerFocus>, distinct: Boolean): Specification<CustomerFocus> {
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
            builder: PredicateBuilder<SCustomerFocus>,
            vararg orderBuilders: OrderBuilder<SCustomerFocus>,
        ): Specification<CustomerFocus> {
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
            builder: PredicateBuilder<SCustomerFocus>,
            orderBuilders: List<OrderBuilder<SCustomerFocus>>,
        ): Specification<CustomerFocus> {
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
            builder: PredicateBuilder<SCustomerFocus>,
            distinct: Boolean,
            vararg orderBuilders: OrderBuilder<SCustomerFocus>,
        ): Specification<CustomerFocus> {
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
            builder: PredicateBuilder<SCustomerFocus>,
            distinct: Boolean,
            orderBuilders: List<OrderBuilder<SCustomerFocus>>,
        ): Specification<CustomerFocus> {
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
        fun specify(specifier: SchemaSpecification<CustomerFocus, SCustomerFocus>): Specification<CustomerFocus> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SCustomerFocus(root, criteriaBuilder)
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
            selectBuilder: ExpressionBuilder<SCustomerFocus, E>,
            predicateBuilder: PredicateBuilder<SCustomerFocus>,
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
            subqueryConfigure: SubqueryConfigure<E, SCustomerFocus>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(CustomerFocus::class.java)
            val schema = SCustomerFocus(root, criteriaBuilder)
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
        fun predicateById(id: Any): AggregatePredicate<AggCustomerFocus, CustomerFocus> {
            return JpaPredicate.byId(CustomerFocus::class.java, id).toAggregatePredicate(AggCustomerFocus::class.java)
        }

        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): AggregatePredicate<AggCustomerFocus, CustomerFocus> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(CustomerFocus::class.java, ids as Iterable<Any>).toAggregatePredicate(AggCustomerFocus::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): AggregatePredicate<AggCustomerFocus, CustomerFocus> {
            return JpaPredicate.byIds(CustomerFocus::class.java, ids.toList()).toAggregatePredicate(AggCustomerFocus::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: PredicateBuilder<SCustomerFocus>): AggregatePredicate<AggCustomerFocus, CustomerFocus> {
            return JpaPredicate.bySpecification(CustomerFocus::class.java, specify(builder)).toAggregatePredicate(AggCustomerFocus::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(
            builder: PredicateBuilder<SCustomerFocus>,
            distinct: Boolean,
        ): AggregatePredicate<AggCustomerFocus, CustomerFocus> {
            return JpaPredicate.bySpecification(CustomerFocus::class.java, specify(builder, distinct)).toAggregatePredicate(AggCustomerFocus::class.java)
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
            builder: PredicateBuilder<SCustomerFocus>,
            orderBuilders: List<OrderBuilder<SCustomerFocus>>,
        ): AggregatePredicate<AggCustomerFocus, CustomerFocus> {
            return JpaPredicate.bySpecification(CustomerFocus::class.java, specify(builder, false, orderBuilders)).toAggregatePredicate(AggCustomerFocus::class.java)
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
            builder: PredicateBuilder<SCustomerFocus>,
            vararg orderBuilders: OrderBuilder<SCustomerFocus>,
        ): AggregatePredicate<AggCustomerFocus, CustomerFocus> {
            return JpaPredicate.bySpecification(CustomerFocus::class.java, specify(builder, false, *orderBuilders)).toAggregatePredicate(AggCustomerFocus::class.java)
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
            builder: PredicateBuilder<SCustomerFocus>,
            distinct: Boolean,
            orderBuilders: List<OrderBuilder<SCustomerFocus>>,
        ): AggregatePredicate<AggCustomerFocus, CustomerFocus> {
            return JpaPredicate.bySpecification(CustomerFocus::class.java, specify(builder, distinct, orderBuilders)).toAggregatePredicate(AggCustomerFocus::class.java)
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
            builder: PredicateBuilder<SCustomerFocus>,
            distinct: Boolean,
            vararg orderBuilders: OrderBuilder<SCustomerFocus>,
        ): AggregatePredicate<AggCustomerFocus, CustomerFocus> {
            return JpaPredicate.bySpecification(CustomerFocus::class.java, specify(builder, distinct, *orderBuilders)).toAggregatePredicate(AggCustomerFocus::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: SchemaSpecification<CustomerFocus, SCustomerFocus>): AggregatePredicate<AggCustomerFocus, CustomerFocus> {
            return JpaPredicate.bySpecification(CustomerFocus::class.java, specify(specifier)).toAggregatePredicate(AggCustomerFocus::class.java)
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
            filterBuilder: java.util.function.Function<QCustomerFocus, com.querydsl.core.types.Predicate>,
            vararg orderSpecifierBuilders: java.util.function.Function<QCustomerFocus, OrderSpecifier<*>>,
        ): AggregatePredicate<AggCustomerFocus, CustomerFocus> {
            return QuerydslPredicate.of(CustomerFocus::class.java)
                .where(filterBuilder.apply(QCustomerFocus.customerFocus))
                .orderBy(*orderSpecifierBuilders.map { it.apply(QCustomerFocus.customerFocus) }.toTypedArray())
                .toAggregatePredicate(AggCustomerFocus::class.java)
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
        ): AggregatePredicate<AggCustomerFocus, CustomerFocus> {
            return QuerydslPredicate.of(CustomerFocus::class.java)
                .where(filter)
                .orderBy(*orderSpecifiers)
                .toAggregatePredicate(AggCustomerFocus::class.java)
        }
    }

    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<CustomerFocus> = root


    /**
     * ID
     */
    val id: Field<Long> by lazy {
        Field(root.get("id"), criteriaBuilder)
    }


    /**
     * 用户ID
     */
    val customerId: Field<String> by lazy {
        Field(root.get("customerId"), criteriaBuilder)
    }


    /**
     * 用户ID
     */
    val focusCustomerId: Field<String> by lazy {
        Field(root.get("focusCustomerId"), criteriaBuilder)
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
    val deleted: Field<Boolean> by lazy {
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
    fun spec(builder: PredicateBuilder<SCustomerFocus>): Predicate {
        return builder.build(this)
    }
}
