package com.edu.only4.danmuku.domain._share.meta.statistics_info.meta

import com.querydsl.core.types.OrderSpecifier
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import com.only4.cap4k.ddd.domain.repo.querydsl.QuerydslPredicate
import com.edu.only4.danmuku.domain._share.meta.Schema
import com.edu.only4.danmuku.domain.aggregates.statistics_info.AggStatisticsInfo
import com.edu.only4.danmuku.domain.aggregates.statistics_info.StatisticsInfo
import com.edu.only4.danmuku.domain.aggregates.statistics_info.QStatisticsInfo
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePredicate
import org.springframework.data.jpa.domain.Specification

import jakarta.persistence.criteria.*

/**
 * 统计信息;
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/09/27
 */
class SStatisticsInfo(
    private val root: Path<StatisticsInfo>,
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
         * 数据统计类型
         */
        val dataType = "dataType"
           
        /**
         * 统计数量
         */
        val statisticsCount = "statisticsCount"
           
        /**
         * 统计日期
         */
        val statisticsDate = "statisticsDate"
           
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
        fun specify(builder: Schema.PredicateBuilder<SStatisticsInfo>): Specification<StatisticsInfo> {
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
        fun specify(builder: Schema.PredicateBuilder<SStatisticsInfo>, distinct: Boolean): Specification<StatisticsInfo> {
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
            builder: Schema.PredicateBuilder<SStatisticsInfo>,
            vararg orderBuilders: Schema.OrderBuilder<SStatisticsInfo>,
        ): Specification<StatisticsInfo> {
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
            builder: Schema.PredicateBuilder<SStatisticsInfo>,
            orderBuilders: List<Schema.OrderBuilder<SStatisticsInfo>>,
        ): Specification<StatisticsInfo> {
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
            builder: Schema.PredicateBuilder<SStatisticsInfo>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SStatisticsInfo>,
        ): Specification<StatisticsInfo> {
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
            builder: Schema.PredicateBuilder<SStatisticsInfo>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SStatisticsInfo>>,
        ): Specification<StatisticsInfo> {
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
        fun specify(specifier: Schema.Specification<StatisticsInfo, SStatisticsInfo>): Specification<StatisticsInfo> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SStatisticsInfo(root, criteriaBuilder)
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
            selectBuilder: Schema.ExpressionBuilder<SStatisticsInfo, E>,
            predicateBuilder: Schema.PredicateBuilder<SStatisticsInfo>,
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
            subqueryConfigure: Schema.SubqueryConfigure<E, SStatisticsInfo>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(StatisticsInfo::class.java)
            val schema = SStatisticsInfo(root, criteriaBuilder)
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
        fun predicateById(id: Any): AggregatePredicate<AggStatisticsInfo, StatisticsInfo> {
            return JpaPredicate.byId(StatisticsInfo::class.java, id).toAggregatePredicate(AggStatisticsInfo::class.java)
        }
    
        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): AggregatePredicate<AggStatisticsInfo, StatisticsInfo> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(StatisticsInfo::class.java, ids as Iterable<Any>).toAggregatePredicate(AggStatisticsInfo::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): AggregatePredicate<AggStatisticsInfo, StatisticsInfo> {
            return JpaPredicate.byIds(StatisticsInfo::class.java, ids.toList()).toAggregatePredicate(AggStatisticsInfo::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SStatisticsInfo>): AggregatePredicate<AggStatisticsInfo, StatisticsInfo> {
            return JpaPredicate.bySpecification(StatisticsInfo::class.java, specify(builder)).toAggregatePredicate(AggStatisticsInfo::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SStatisticsInfo>, distinct: Boolean): AggregatePredicate<AggStatisticsInfo, StatisticsInfo> {
            return JpaPredicate.bySpecification(StatisticsInfo::class.java, specify(builder, distinct)).toAggregatePredicate(AggStatisticsInfo::class.java)
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
            builder: Schema.PredicateBuilder<SStatisticsInfo>,
            orderBuilders: List<Schema.OrderBuilder<SStatisticsInfo>>,
        ): AggregatePredicate<AggStatisticsInfo, StatisticsInfo> {
            return JpaPredicate.bySpecification(StatisticsInfo::class.java, specify(builder, false, orderBuilders)).toAggregatePredicate(AggStatisticsInfo::class.java)
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
            builder: Schema.PredicateBuilder<SStatisticsInfo>,
            vararg orderBuilders: Schema.OrderBuilder<SStatisticsInfo>,
        ): AggregatePredicate<AggStatisticsInfo, StatisticsInfo> {
            return JpaPredicate.bySpecification(StatisticsInfo::class.java, specify(builder, false, *orderBuilders)).toAggregatePredicate(AggStatisticsInfo::class.java)
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
            builder: Schema.PredicateBuilder<SStatisticsInfo>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SStatisticsInfo>>,
        ): AggregatePredicate<AggStatisticsInfo, StatisticsInfo> {
            return JpaPredicate.bySpecification(StatisticsInfo::class.java, specify(builder, distinct, orderBuilders)).toAggregatePredicate(AggStatisticsInfo::class.java)
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
            builder: Schema.PredicateBuilder<SStatisticsInfo>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SStatisticsInfo>,
        ): AggregatePredicate<AggStatisticsInfo, StatisticsInfo> {
            return JpaPredicate.bySpecification(StatisticsInfo::class.java, specify(builder, distinct, *orderBuilders)).toAggregatePredicate(AggStatisticsInfo::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: Schema.Specification<StatisticsInfo, SStatisticsInfo>): AggregatePredicate<AggStatisticsInfo, StatisticsInfo> {
            return JpaPredicate.bySpecification(StatisticsInfo::class.java, specify(specifier)).toAggregatePredicate(AggStatisticsInfo::class.java)
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
            filterBuilder: java.util.function.Function<QStatisticsInfo, com.querydsl.core.types.Predicate>,
            vararg orderSpecifierBuilders: java.util.function.Function<QStatisticsInfo, OrderSpecifier<*>>,
        ): AggregatePredicate<AggStatisticsInfo, StatisticsInfo> {
            return QuerydslPredicate.of(StatisticsInfo::class.java)
                .where(filterBuilder.apply(QStatisticsInfo.statisticsInfo))
                .orderBy(*orderSpecifierBuilders.map { it.apply(QStatisticsInfo.statisticsInfo) }.toTypedArray())
                .toAggregatePredicate(AggStatisticsInfo::class.java)
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
        ): AggregatePredicate<AggStatisticsInfo, StatisticsInfo> {
            return QuerydslPredicate.of(StatisticsInfo::class.java)
                .where(filter)
                .orderBy(*orderSpecifiers)
                .toAggregatePredicate(AggStatisticsInfo::class.java)
        }  
    }
    
    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<StatisticsInfo> = root

    
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
     * 数据统计类型
     * 0:UNKNOW:未知类型
     * 1:VIDEO_VIEW:视频观看
     * 2:VIDEO_LIKE:视频点赞
     * 3:VIDEO_COMMENT:视频评论
     * 4:VIDEO_SHARE:视频分享
     * 5:USER_FOLLOW:用户关注
     * 6:USER_LOGIN:用户登录
     * tinyint(1)
     */
    fun dataType(): Schema.Field<com.edu.only4.danmuku.domain.aggregates.statistics_info.enums.StatisticsDataType> {
        return Schema.Field(root.get(props.dataType), this.criteriaBuilder)
    }
    /**
     * 统计数量
     * int
     */
    fun statisticsCount(): Schema.Field<Int?> {
        return Schema.Field(root.get(props.statisticsCount), this.criteriaBuilder)
    }
    /**
     * 统计日期
     * bigint
     */
    fun statisticsDate(): Schema.Field<Long> {
        return Schema.Field(root.get(props.statisticsDate), this.criteriaBuilder)
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
    fun spec(builder: Schema.PredicateBuilder<SStatisticsInfo>): Predicate {
        return builder.build(this)
    }
    
    
}