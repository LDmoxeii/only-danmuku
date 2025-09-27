package com.edu.only4.danmuku.domain._share.meta.video_play_history.meta

import com.querydsl.core.types.OrderSpecifier
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import com.only4.cap4k.ddd.domain.repo.querydsl.QuerydslPredicate
import com.edu.only4.danmuku.domain._share.meta.Schema
import com.edu.only4.danmuku.domain.aggregates.video_play_history.AggVideoPlayHistory
import com.edu.only4.danmuku.domain.aggregates.video_play_history.VideoPlayHistory
import com.edu.only4.danmuku.domain.aggregates.video_play_history.QVideoPlayHistory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePredicate
import org.springframework.data.jpa.domain.Specification

import jakarta.persistence.criteria.*

/**
 * 视频播放历史;
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/09/27
 */
class SVideoPlayHistory(
    private val root: Path<VideoPlayHistory>,
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
         * 视频ID
         */
        val videoId = "videoId"
           
        /**
         * 文件索引
         */
        val fileIndex = "fileIndex"
           
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
        fun specify(builder: Schema.PredicateBuilder<SVideoPlayHistory>): Specification<VideoPlayHistory> {
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
        fun specify(builder: Schema.PredicateBuilder<SVideoPlayHistory>, distinct: Boolean): Specification<VideoPlayHistory> {
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
            builder: Schema.PredicateBuilder<SVideoPlayHistory>,
            vararg orderBuilders: Schema.OrderBuilder<SVideoPlayHistory>,
        ): Specification<VideoPlayHistory> {
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
            builder: Schema.PredicateBuilder<SVideoPlayHistory>,
            orderBuilders: List<Schema.OrderBuilder<SVideoPlayHistory>>,
        ): Specification<VideoPlayHistory> {
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
            builder: Schema.PredicateBuilder<SVideoPlayHistory>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SVideoPlayHistory>,
        ): Specification<VideoPlayHistory> {
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
            builder: Schema.PredicateBuilder<SVideoPlayHistory>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SVideoPlayHistory>>,
        ): Specification<VideoPlayHistory> {
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
        fun specify(specifier: Schema.Specification<VideoPlayHistory, SVideoPlayHistory>): Specification<VideoPlayHistory> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SVideoPlayHistory(root, criteriaBuilder)
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
            selectBuilder: Schema.ExpressionBuilder<SVideoPlayHistory, E>,
            predicateBuilder: Schema.PredicateBuilder<SVideoPlayHistory>,
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
            subqueryConfigure: Schema.SubqueryConfigure<E, SVideoPlayHistory>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(VideoPlayHistory::class.java)
            val schema = SVideoPlayHistory(root, criteriaBuilder)
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
        fun predicateById(id: Any): AggregatePredicate<AggVideoPlayHistory, VideoPlayHistory> {
            return JpaPredicate.byId(VideoPlayHistory::class.java, id).toAggregatePredicate(AggVideoPlayHistory::class.java)
        }
    
        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): AggregatePredicate<AggVideoPlayHistory, VideoPlayHistory> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(VideoPlayHistory::class.java, ids as Iterable<Any>).toAggregatePredicate(AggVideoPlayHistory::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): AggregatePredicate<AggVideoPlayHistory, VideoPlayHistory> {
            return JpaPredicate.byIds(VideoPlayHistory::class.java, ids.toList()).toAggregatePredicate(AggVideoPlayHistory::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SVideoPlayHistory>): AggregatePredicate<AggVideoPlayHistory, VideoPlayHistory> {
            return JpaPredicate.bySpecification(VideoPlayHistory::class.java, specify(builder)).toAggregatePredicate(AggVideoPlayHistory::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SVideoPlayHistory>, distinct: Boolean): AggregatePredicate<AggVideoPlayHistory, VideoPlayHistory> {
            return JpaPredicate.bySpecification(VideoPlayHistory::class.java, specify(builder, distinct)).toAggregatePredicate(AggVideoPlayHistory::class.java)
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
            builder: Schema.PredicateBuilder<SVideoPlayHistory>,
            orderBuilders: List<Schema.OrderBuilder<SVideoPlayHistory>>,
        ): AggregatePredicate<AggVideoPlayHistory, VideoPlayHistory> {
            return JpaPredicate.bySpecification(VideoPlayHistory::class.java, specify(builder, false, orderBuilders)).toAggregatePredicate(AggVideoPlayHistory::class.java)
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
            builder: Schema.PredicateBuilder<SVideoPlayHistory>,
            vararg orderBuilders: Schema.OrderBuilder<SVideoPlayHistory>,
        ): AggregatePredicate<AggVideoPlayHistory, VideoPlayHistory> {
            return JpaPredicate.bySpecification(VideoPlayHistory::class.java, specify(builder, false, *orderBuilders)).toAggregatePredicate(AggVideoPlayHistory::class.java)
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
            builder: Schema.PredicateBuilder<SVideoPlayHistory>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SVideoPlayHistory>>,
        ): AggregatePredicate<AggVideoPlayHistory, VideoPlayHistory> {
            return JpaPredicate.bySpecification(VideoPlayHistory::class.java, specify(builder, distinct, orderBuilders)).toAggregatePredicate(AggVideoPlayHistory::class.java)
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
            builder: Schema.PredicateBuilder<SVideoPlayHistory>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SVideoPlayHistory>,
        ): AggregatePredicate<AggVideoPlayHistory, VideoPlayHistory> {
            return JpaPredicate.bySpecification(VideoPlayHistory::class.java, specify(builder, distinct, *orderBuilders)).toAggregatePredicate(AggVideoPlayHistory::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: Schema.Specification<VideoPlayHistory, SVideoPlayHistory>): AggregatePredicate<AggVideoPlayHistory, VideoPlayHistory> {
            return JpaPredicate.bySpecification(VideoPlayHistory::class.java, specify(specifier)).toAggregatePredicate(AggVideoPlayHistory::class.java)
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
            filterBuilder: java.util.function.Function<QVideoPlayHistory, com.querydsl.core.types.Predicate>,
            vararg orderSpecifierBuilders: java.util.function.Function<QVideoPlayHistory, OrderSpecifier<*>>,
        ): AggregatePredicate<AggVideoPlayHistory, VideoPlayHistory> {
            return QuerydslPredicate.of(VideoPlayHistory::class.java)
                .where(filterBuilder.apply(QVideoPlayHistory.videoPlayHistory))
                .orderBy(*orderSpecifierBuilders.map { it.apply(QVideoPlayHistory.videoPlayHistory) }.toTypedArray())
                .toAggregatePredicate(AggVideoPlayHistory::class.java)
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
        ): AggregatePredicate<AggVideoPlayHistory, VideoPlayHistory> {
            return QuerydslPredicate.of(VideoPlayHistory::class.java)
                .where(filter)
                .orderBy(*orderSpecifiers)
                .toAggregatePredicate(AggVideoPlayHistory::class.java)
        }  
    }
    
    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<VideoPlayHistory> = root

    
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
     * 视频ID
     * bigint
     */
    fun videoId(): Schema.Field<Long> {
        return Schema.Field(root.get(props.videoId), this.criteriaBuilder)
    }
    /**
     * 文件索引
     * int
     */
    fun fileIndex(): Schema.Field<Int> {
        return Schema.Field(root.get(props.fileIndex), this.criteriaBuilder)
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
    fun spec(builder: Schema.PredicateBuilder<SVideoPlayHistory>): Predicate {
        return builder.build(this)
    }
    
    
}