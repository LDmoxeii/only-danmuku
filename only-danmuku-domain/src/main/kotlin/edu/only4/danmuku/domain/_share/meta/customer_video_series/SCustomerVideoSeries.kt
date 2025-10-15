package edu.only4.danmuku.domain._share.meta.customer_video_series

import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePredicate
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import com.only4.cap4k.ddd.domain.repo.querydsl.QuerydslPredicate

import com.querydsl.core.types.OrderSpecifier
import edu.only4.danmuku.domain._share.meta.Schema
import edu.only4.danmuku.domain.aggregates.customer_video_series.AggCustomerVideoSeries
import edu.only4.danmuku.domain.aggregates.customer_video_series.CustomerVideoSeries
import edu.only4.danmuku.domain.aggregates.customer_video_series.QCustomerVideoSeries

import jakarta.persistence.criteria.*

import org.springframework.data.jpa.domain.Specification

/**
 * 用户视频序列归档;
 *
 * Schema 类用于类型安全的 JPA Criteria 查询
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
class SCustomerVideoSeries(
    private val root: Path<CustomerVideoSeries>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {

        val id = "id"

        val customerId = "customerId"

        val seriesName = "seriesName"

        val seriesDescription = "seriesDescription"

        val sort = "sort"

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
        fun specify(builder: Schema.PredicateBuilder<SCustomerVideoSeries>): Specification<CustomerVideoSeries> {
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
        fun specify(builder: Schema.PredicateBuilder<SCustomerVideoSeries>, distinct: Boolean): Specification<CustomerVideoSeries> {
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
            builder: Schema.PredicateBuilder<SCustomerVideoSeries>,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerVideoSeries>,
        ): Specification<CustomerVideoSeries> {
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
            builder: Schema.PredicateBuilder<SCustomerVideoSeries>,
            orderBuilders: List<Schema.OrderBuilder<SCustomerVideoSeries>>,
        ): Specification<CustomerVideoSeries> {
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
            builder: Schema.PredicateBuilder<SCustomerVideoSeries>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerVideoSeries>,
        ): Specification<CustomerVideoSeries> {
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
            builder: Schema.PredicateBuilder<SCustomerVideoSeries>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SCustomerVideoSeries>>,
        ): Specification<CustomerVideoSeries> {
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
        fun specify(specifier: Schema.Specification<CustomerVideoSeries, SCustomerVideoSeries>): Specification<CustomerVideoSeries> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SCustomerVideoSeries(root, criteriaBuilder)
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
            selectBuilder: Schema.ExpressionBuilder<SCustomerVideoSeries, E>,
            predicateBuilder: Schema.PredicateBuilder<SCustomerVideoSeries>,
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
            subqueryConfigure: Schema.SubqueryConfigure<E, SCustomerVideoSeries>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(CustomerVideoSeries::class.java)
            val schema = SCustomerVideoSeries(root, criteriaBuilder)
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
        fun predicateById(id: Any): AggregatePredicate<AggCustomerVideoSeries, CustomerVideoSeries> {
            return JpaPredicate.byId(CustomerVideoSeries::class.java, id).toAggregatePredicate(AggCustomerVideoSeries::class.java)
        }

        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): AggregatePredicate<AggCustomerVideoSeries, CustomerVideoSeries> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(CustomerVideoSeries::class.java, ids as Iterable<Any>).toAggregatePredicate(AggCustomerVideoSeries::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): AggregatePredicate<AggCustomerVideoSeries, CustomerVideoSeries> {
            return JpaPredicate.byIds(CustomerVideoSeries::class.java, ids.toList()).toAggregatePredicate(AggCustomerVideoSeries::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SCustomerVideoSeries>): AggregatePredicate<AggCustomerVideoSeries, CustomerVideoSeries> {
            return JpaPredicate.bySpecification(CustomerVideoSeries::class.java, specify(builder)).toAggregatePredicate(AggCustomerVideoSeries::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SCustomerVideoSeries>, distinct: Boolean): AggregatePredicate<AggCustomerVideoSeries, CustomerVideoSeries> {
            return JpaPredicate.bySpecification(CustomerVideoSeries::class.java, specify(builder, distinct)).toAggregatePredicate(AggCustomerVideoSeries::class.java)
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
            builder: Schema.PredicateBuilder<SCustomerVideoSeries>,
            orderBuilders: List<Schema.OrderBuilder<SCustomerVideoSeries>>,
        ): AggregatePredicate<AggCustomerVideoSeries, CustomerVideoSeries> {
            return JpaPredicate.bySpecification(CustomerVideoSeries::class.java, specify(builder, false, orderBuilders)).toAggregatePredicate(AggCustomerVideoSeries::class.java)
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
            builder: Schema.PredicateBuilder<SCustomerVideoSeries>,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerVideoSeries>,
        ): AggregatePredicate<AggCustomerVideoSeries, CustomerVideoSeries> {
            return JpaPredicate.bySpecification(CustomerVideoSeries::class.java, specify(builder, false, *orderBuilders)).toAggregatePredicate(AggCustomerVideoSeries::class.java)
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
            builder: Schema.PredicateBuilder<SCustomerVideoSeries>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SCustomerVideoSeries>>,
        ): AggregatePredicate<AggCustomerVideoSeries, CustomerVideoSeries> {
            return JpaPredicate.bySpecification(CustomerVideoSeries::class.java, specify(builder, distinct, orderBuilders)).toAggregatePredicate(AggCustomerVideoSeries::class.java)
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
            builder: Schema.PredicateBuilder<SCustomerVideoSeries>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerVideoSeries>,
        ): AggregatePredicate<AggCustomerVideoSeries, CustomerVideoSeries> {
            return JpaPredicate.bySpecification(CustomerVideoSeries::class.java, specify(builder, distinct, *orderBuilders)).toAggregatePredicate(AggCustomerVideoSeries::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: Schema.Specification<CustomerVideoSeries, SCustomerVideoSeries>): AggregatePredicate<AggCustomerVideoSeries, CustomerVideoSeries> {
            return JpaPredicate.bySpecification(CustomerVideoSeries::class.java, specify(specifier)).toAggregatePredicate(AggCustomerVideoSeries::class.java)
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
            filterBuilder: java.util.function.Function<QCustomerVideoSeries, com.querydsl.core.types.Predicate>,
            vararg orderSpecifierBuilders: java.util.function.Function<QCustomerVideoSeries, OrderSpecifier<*>>,
        ): AggregatePredicate<AggCustomerVideoSeries, CustomerVideoSeries> {
            return QuerydslPredicate.of(CustomerVideoSeries::class.java)
                .where(filterBuilder.apply(QCustomerVideoSeries.customerVideoSeries))
                .orderBy(*orderSpecifierBuilders.map { it.apply(QCustomerVideoSeries.customerVideoSeries) }.toTypedArray())
                .toAggregatePredicate(AggCustomerVideoSeries::class.java)
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
        ): AggregatePredicate<AggCustomerVideoSeries, CustomerVideoSeries> {
            return QuerydslPredicate.of(CustomerVideoSeries::class.java)
                .where(filter)
                .orderBy(*orderSpecifiers)
                .toAggregatePredicate(AggCustomerVideoSeries::class.java)
        }
    }

    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<CustomerVideoSeries> = root


    /**
     * ID
     */
    val id: Schema.Field<Long> by lazy {
        Schema.Field(root.get("id"), criteriaBuilder)
    }


    /**
     * 用户ID
     */
    val customerId: Schema.Field<Long> by lazy {
        Schema.Field(root.get("customerId"), criteriaBuilder)
    }


    /**
     * 列表名称
     */
    val seriesName: Schema.Field<String> by lazy {
        Schema.Field(root.get("seriesName"), criteriaBuilder)
    }


    /**
     * 描述
     */
    val seriesDescription: Schema.Field<String?> by lazy {
        Schema.Field(root.get("seriesDescription"), criteriaBuilder)
    }


    /**
     * 排序
     */
    val sort: Schema.Field<Byte> by lazy {
        Schema.Field(root.get("sort"), criteriaBuilder)
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
     * 关联: OneToMany - CustomerVideoSeriesVideo
     */
    val customerVideoSeriesVideos: Schema.Field<Any> by lazy {
        Schema.Field(root.get("customerVideoSeriesVideos"), criteriaBuilder)
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
    fun spec(builder: Schema.PredicateBuilder<SCustomerVideoSeries>): Predicate
    {
        return builder.build(this)
    }
}
