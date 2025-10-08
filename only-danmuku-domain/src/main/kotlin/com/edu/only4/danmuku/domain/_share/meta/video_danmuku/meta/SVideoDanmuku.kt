package com.edu.only4.danmuku.domain._share.meta.video_danmuku.meta

import com.edu.only4.danmuku.domain._share.meta.Schema
import com.edu.only4.danmuku.domain.aggregates.video_danmuku.AggVideoDanmuku
import com.edu.only4.danmuku.domain.aggregates.video_danmuku.QVideoDanmuku
import com.edu.only4.danmuku.domain.aggregates.video_danmuku.VideoDanmuku
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePredicate
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import com.only4.cap4k.ddd.domain.repo.querydsl.QuerydslPredicate
import com.querydsl.core.types.OrderSpecifier
import jakarta.persistence.criteria.*
import org.springframework.data.jpa.domain.Specification

/**
 * 视频弹幕;
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/10/08
 */
class SVideoDanmuku(
    private val root: Path<VideoDanmuku>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {

        /**
         * ID
         */
        val id = "id"

        /**
         * 视频ID
         */
        val videoId = "videoId"

        /**
         * 唯一ID
         */
        val fileId = "fileId"

        /**
         * 用户ID
         */
        val customerId = "customerId"

        /**
         * 发布时间
         */
        val postTime = "postTime"

        /**
         * 内容
         */
        val text = "text"

        /**
         * 展示位置
         */
        val mode = "mode"

        /**
         * 颜色
         */
        val color = "color"

        /**
         * 展示时间
         */
        val time = "time"

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
        fun specify(builder: Schema.PredicateBuilder<SVideoDanmuku>): Specification<VideoDanmuku> {
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
        fun specify(builder: Schema.PredicateBuilder<SVideoDanmuku>, distinct: Boolean): Specification<VideoDanmuku> {
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
            builder: Schema.PredicateBuilder<SVideoDanmuku>,
            vararg orderBuilders: Schema.OrderBuilder<SVideoDanmuku>,
        ): Specification<VideoDanmuku> {
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
            builder: Schema.PredicateBuilder<SVideoDanmuku>,
            orderBuilders: List<Schema.OrderBuilder<SVideoDanmuku>>,
        ): Specification<VideoDanmuku> {
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
            builder: Schema.PredicateBuilder<SVideoDanmuku>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SVideoDanmuku>,
        ): Specification<VideoDanmuku> {
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
            builder: Schema.PredicateBuilder<SVideoDanmuku>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SVideoDanmuku>>,
        ): Specification<VideoDanmuku> {
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
        fun specify(specifier: Schema.Specification<VideoDanmuku, SVideoDanmuku>): Specification<VideoDanmuku> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SVideoDanmuku(root, criteriaBuilder)
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
            selectBuilder: Schema.ExpressionBuilder<SVideoDanmuku, E>,
            predicateBuilder: Schema.PredicateBuilder<SVideoDanmuku>,
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
            subqueryConfigure: Schema.SubqueryConfigure<E, SVideoDanmuku>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(VideoDanmuku::class.java)
            val schema = SVideoDanmuku(root, criteriaBuilder)
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
        fun predicateById(id: Any): AggregatePredicate<AggVideoDanmuku, VideoDanmuku> {
            return JpaPredicate.byId(VideoDanmuku::class.java, id).toAggregatePredicate(AggVideoDanmuku::class.java)
        }

        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): AggregatePredicate<AggVideoDanmuku, VideoDanmuku> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(VideoDanmuku::class.java, ids as Iterable<Any>).toAggregatePredicate(AggVideoDanmuku::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): AggregatePredicate<AggVideoDanmuku, VideoDanmuku> {
            return JpaPredicate.byIds(VideoDanmuku::class.java, ids.toList()).toAggregatePredicate(AggVideoDanmuku::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SVideoDanmuku>): AggregatePredicate<AggVideoDanmuku, VideoDanmuku> {
            return JpaPredicate.bySpecification(VideoDanmuku::class.java, specify(builder)).toAggregatePredicate(AggVideoDanmuku::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SVideoDanmuku>, distinct: Boolean): AggregatePredicate<AggVideoDanmuku, VideoDanmuku> {
            return JpaPredicate.bySpecification(VideoDanmuku::class.java, specify(builder, distinct)).toAggregatePredicate(AggVideoDanmuku::class.java)
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
            builder: Schema.PredicateBuilder<SVideoDanmuku>,
            orderBuilders: List<Schema.OrderBuilder<SVideoDanmuku>>,
        ): AggregatePredicate<AggVideoDanmuku, VideoDanmuku> {
            return JpaPredicate.bySpecification(VideoDanmuku::class.java, specify(builder, false, orderBuilders)).toAggregatePredicate(AggVideoDanmuku::class.java)
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
            builder: Schema.PredicateBuilder<SVideoDanmuku>,
            vararg orderBuilders: Schema.OrderBuilder<SVideoDanmuku>,
        ): AggregatePredicate<AggVideoDanmuku, VideoDanmuku> {
            return JpaPredicate.bySpecification(VideoDanmuku::class.java, specify(builder, false, *orderBuilders)).toAggregatePredicate(AggVideoDanmuku::class.java)
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
            builder: Schema.PredicateBuilder<SVideoDanmuku>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SVideoDanmuku>>,
        ): AggregatePredicate<AggVideoDanmuku, VideoDanmuku> {
            return JpaPredicate.bySpecification(VideoDanmuku::class.java, specify(builder, distinct, orderBuilders)).toAggregatePredicate(AggVideoDanmuku::class.java)
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
            builder: Schema.PredicateBuilder<SVideoDanmuku>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SVideoDanmuku>,
        ): AggregatePredicate<AggVideoDanmuku, VideoDanmuku> {
            return JpaPredicate.bySpecification(VideoDanmuku::class.java, specify(builder, distinct, *orderBuilders)).toAggregatePredicate(AggVideoDanmuku::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: Schema.Specification<VideoDanmuku, SVideoDanmuku>): AggregatePredicate<AggVideoDanmuku, VideoDanmuku> {
            return JpaPredicate.bySpecification(VideoDanmuku::class.java, specify(specifier)).toAggregatePredicate(AggVideoDanmuku::class.java)
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
            filterBuilder: java.util.function.Function<QVideoDanmuku, com.querydsl.core.types.Predicate>,
            vararg orderSpecifierBuilders: java.util.function.Function<QVideoDanmuku, OrderSpecifier<*>>,
        ): AggregatePredicate<AggVideoDanmuku, VideoDanmuku> {
            return QuerydslPredicate.of(VideoDanmuku::class.java)
                .where(filterBuilder.apply(QVideoDanmuku.videoDanmuku))
                .orderBy(*orderSpecifierBuilders.map { it.apply(QVideoDanmuku.videoDanmuku) }.toTypedArray())
                .toAggregatePredicate(AggVideoDanmuku::class.java)
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
        ): AggregatePredicate<AggVideoDanmuku, VideoDanmuku> {
            return QuerydslPredicate.of(VideoDanmuku::class.java)
                .where(filter)
                .orderBy(*orderSpecifiers)
                .toAggregatePredicate(AggVideoDanmuku::class.java)
        }
    }

    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<VideoDanmuku> = root


    /**
     * ID
     * bigint
     */
    fun id(): Schema.Field<Long> {
        return Schema.Field(root.get(props.id), this.criteriaBuilder)
    }
    /**
     * 视频ID
     * bigint
     */
    fun videoId(): Schema.Field<Long> {
        return Schema.Field(root.get(props.videoId), this.criteriaBuilder)
    }
    /**
     * 唯一ID
     * bigint
     */
    fun fileId(): Schema.Field<Long> {
        return Schema.Field(root.get(props.fileId), this.criteriaBuilder)
    }
    /**
     * 用户ID
     * bigint
     */
    fun customerId(): Schema.Field<Long> {
        return Schema.Field(root.get(props.customerId), this.criteriaBuilder)
    }
    /**
     * 发布时间
     * bigint
     */
    fun postTime(): Schema.Field<Long?> {
        return Schema.Field(root.get(props.postTime), this.criteriaBuilder)
    }
    /**
     * 内容
     * varchar(300)
     */
    fun text(): Schema.Field<String?> {
        return Schema.Field(root.get(props.text), this.criteriaBuilder)
    }
    /**
     * 展示位置
     * tinyint(1)
     */
    fun mode(): Schema.Field<Boolean?> {
        return Schema.Field(root.get(props.mode), this.criteriaBuilder)
    }
    /**
     * 颜色
     * varchar(10)
     */
    fun color(): Schema.Field<String?> {
        return Schema.Field(root.get(props.color), this.criteriaBuilder)
    }
    /**
     * 展示时间
     * int
     */
    fun time(): Schema.Field<Int?> {
        return Schema.Field(root.get(props.time), this.criteriaBuilder)
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
    fun spec(builder: Schema.PredicateBuilder<SVideoDanmuku>): Predicate {
        return builder.build(this)
    }


}
