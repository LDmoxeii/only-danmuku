package com.edu.only4.danmuku.domain._share.meta.customer_video_series.meta

import com.edu.only4.danmuku.domain._share.meta.Schema
import com.edu.only4.danmuku.domain.aggregates.customer_video_series.CustomerVideoSeriesVideo
import org.springframework.data.jpa.domain.Specification

import jakarta.persistence.criteria.*

/**
 * 用户视频序列视频关联;
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/09/28
 */
class SCustomerVideoSeriesVideo(
    private val root: Path<CustomerVideoSeriesVideo>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {
            
        /**
         * ID
         */
        val id = "id"
           
        /**
         * 用户ID
         */
        val customerId = "customerId"
           
        /**
         * 列表ID
         */
        val seriesId = "seriesId"
           
        /**
         * 视频ID
         */
        val videoId = "videoId"
           
        /**
         * 排序
         */
        val sort = "sort"
           
        /**
         * 删除标识 0：未删除 id：已删除
         */
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
     * bigint
     */
    fun id(): Schema.Field<Long> {
        return Schema.Field(root.get(props.id), this.criteriaBuilder)
    }
    /**
     * 用户ID
     * bigint
     */
    fun customerId(): Schema.Field<Long> {
        return Schema.Field(root.get(props.customerId), this.criteriaBuilder)
    }
    /**
     * 列表ID
     * bigint
     */
    fun seriesId(): Schema.Field<Long> {
        return Schema.Field(root.get(props.seriesId), this.criteriaBuilder)
    }
    /**
     * 视频ID
     * bigint
     */
    fun videoId(): Schema.Field<Long> {
        return Schema.Field(root.get(props.videoId), this.criteriaBuilder)
    }
    /**
     * 排序
     * tinyint
     */
    fun sort(): Schema.Field<Byte> {
        return Schema.Field(root.get(props.sort), this.criteriaBuilder)
    }
    /**
     * 删除标识 0：未删除 id：已删除
     * tinyint(1)
     */
    fun deleted(): Schema.Field<Boolean> {
        return Schema.Field(root.get(props.deleted), this.criteriaBuilder)
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
    fun spec(builder: Schema.PredicateBuilder<SCustomerVideoSeriesVideo>): Predicate {
        return builder.build(this)
    }
    
    
}