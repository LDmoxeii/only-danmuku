package edu.only4.danmuku.domain._share.meta.customer_video_series

import edu.only4.danmuku.domain._share.meta.Schema
import edu.only4.danmuku.domain.aggregates.customer_video_series.CustomerVideoSeriesVideo

import jakarta.persistence.criteria.*

import org.springframework.data.jpa.domain.Specification

/**
 * 用户视频序列视频关联;
 *
 * Schema 类用于类型安全的 JPA Criteria 查询
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
class SCustomerVideoSeriesVideo(
    private val root: Path<CustomerVideoSeriesVideo>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {

        val id = "id"

        val customerId = "customerId"

        val seriesId = "seriesId"

        val videoId = "videoId"

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
        fun specify(builder: Schema.PredicateBuilder<SCustomerVideoSeriesVideo>): Specification<CustomerVideoSeriesVideo> {
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
        fun specify(builder: Schema.PredicateBuilder<SCustomerVideoSeriesVideo>, distinct: Boolean): Specification<CustomerVideoSeriesVideo> {
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
            builder: Schema.PredicateBuilder<SCustomerVideoSeriesVideo>,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerVideoSeriesVideo>,
        ): Specification<CustomerVideoSeriesVideo> {
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
            builder: Schema.PredicateBuilder<SCustomerVideoSeriesVideo>,
            orderBuilders: List<Schema.OrderBuilder<SCustomerVideoSeriesVideo>>,
        ): Specification<CustomerVideoSeriesVideo> {
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
            builder: Schema.PredicateBuilder<SCustomerVideoSeriesVideo>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerVideoSeriesVideo>,
        ): Specification<CustomerVideoSeriesVideo> {
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
            builder: Schema.PredicateBuilder<SCustomerVideoSeriesVideo>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SCustomerVideoSeriesVideo>>,
        ): Specification<CustomerVideoSeriesVideo> {
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
        fun specify(specifier: Schema.Specification<CustomerVideoSeriesVideo, SCustomerVideoSeriesVideo>): Specification<CustomerVideoSeriesVideo> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SCustomerVideoSeriesVideo(root, criteriaBuilder)
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
            selectBuilder: Schema.ExpressionBuilder<SCustomerVideoSeriesVideo, E>,
            predicateBuilder: Schema.PredicateBuilder<SCustomerVideoSeriesVideo>,
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
            subqueryConfigure: Schema.SubqueryConfigure<E, SCustomerVideoSeriesVideo>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(CustomerVideoSeriesVideo::class.java)
            val schema = SCustomerVideoSeriesVideo(root, criteriaBuilder)
            subqueryConfigure.configure(sq, schema)
            return sq
        }
    }

    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<CustomerVideoSeriesVideo> = root


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
     * 列表ID
     */
    val seriesId: Schema.Field<Long> by lazy {
        Schema.Field(root.get("seriesId"), criteriaBuilder)
    }


    /**
     * 视频ID
     */
    val videoId: Schema.Field<Long> by lazy {
        Schema.Field(root.get("videoId"), criteriaBuilder)
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
     * 关联: ManyToOne - CustomerVideoSeries
     */
    val customerVideoSeries: Schema.Field<Any> by lazy {
        Schema.Field(root.get("customerVideoSeries"), criteriaBuilder)
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
    fun spec(builder: Schema.PredicateBuilder<SCustomerVideoSeriesVideo>): Predicate
    {
        return builder.build(this)
    }
}
