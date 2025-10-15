package edu.only4.danmuku.domain._share.meta.video_play_history

import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePredicate
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import com.only4.cap4k.ddd.domain.repo.querydsl.QuerydslPredicate

import com.querydsl.core.types.OrderSpecifier
import edu.only4.danmuku.domain._share.meta.Schema
import edu.only4.danmuku.domain.aggregates.video_play_history.AggVideoPlayHistory
import edu.only4.danmuku.domain.aggregates.video_play_history.QVideoPlayHistory
import edu.only4.danmuku.domain.aggregates.video_play_history.VideoPlayHistory

import jakarta.persistence.criteria.*

import org.springframework.data.jpa.domain.Specification

/**
 * 视频播放历史;
 *
 * Schema 类用于类型安全的 JPA Criteria 查询
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
class SVideoPlayHistory(
    private val root: Path<VideoPlayHistory>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {

        val id = "id"

        val customerId = "customerId"

        val videoId = "videoId"

        val fileIndex = "fileIndex"

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
     * 视频ID
     */
    val videoId: Schema.Field<Long> by lazy {
        Schema.Field(root.get("videoId"), criteriaBuilder)
    }


    /**
     * 文件索引
     */
    val fileIndex: Schema.Field<Int> by lazy {
        Schema.Field(root.get("fileIndex"), criteriaBuilder)
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
    fun spec(builder: Schema.PredicateBuilder<SVideoPlayHistory>): Predicate
    {
        return builder.build(this)
    }
}
