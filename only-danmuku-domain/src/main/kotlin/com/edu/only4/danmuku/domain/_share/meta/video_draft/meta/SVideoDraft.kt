package com.edu.only4.danmuku.domain._share.meta.video_draft.meta

import com.edu.only4.danmuku.domain._share.meta.Schema
import com.edu.only4.danmuku.domain.aggregates.video_draft.AggVideoDraft
import com.edu.only4.danmuku.domain.aggregates.video_draft.QVideoDraft
import com.edu.only4.danmuku.domain.aggregates.video_draft.VideoDraft
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePredicate
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import com.only4.cap4k.ddd.domain.repo.querydsl.QuerydslPredicate
import com.querydsl.core.types.OrderSpecifier
import jakarta.persistence.criteria.*
import org.springframework.data.jpa.domain.Specification

/**
 * 视频信息;
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/10/08
 */
class SVideoDraft(
    private val root: Path<VideoDraft>,
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
         * 视频封面
         */
        val videoCover = "videoCover"

        /**
         * 视频名称
         */
        val videoName = "videoName"

        /**
         * 用户ID
         */
        val customerId = "customerId"

        /**
         * 父级分类ID
         */
        val pCategoryId = "pCategoryId"

        /**
         * 分类ID
         */
        val categoryId = "categoryId"

        /**
         * 视频状态
         */
        val status = "status"

        /**
         * 投稿类型
         */
        val postType = "postType"

        /**
         * 原资源说明
         */
        val originInfo = "originInfo"

        /**
         * 标签
         */
        val tags = "tags"

        /**
         * 简介
         */
        val introduction = "introduction"

        /**
         * 互动设置
         */
        val interaction = "interaction"

        /**
         * 持续时间（秒）
         */
        val duration = "duration"

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
        fun specify(builder: Schema.PredicateBuilder<SVideoDraft>): Specification<VideoDraft> {
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
        fun specify(builder: Schema.PredicateBuilder<SVideoDraft>, distinct: Boolean): Specification<VideoDraft> {
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
            builder: Schema.PredicateBuilder<SVideoDraft>,
            vararg orderBuilders: Schema.OrderBuilder<SVideoDraft>,
        ): Specification<VideoDraft> {
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
            builder: Schema.PredicateBuilder<SVideoDraft>,
            orderBuilders: List<Schema.OrderBuilder<SVideoDraft>>,
        ): Specification<VideoDraft> {
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
            builder: Schema.PredicateBuilder<SVideoDraft>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SVideoDraft>,
        ): Specification<VideoDraft> {
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
            builder: Schema.PredicateBuilder<SVideoDraft>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SVideoDraft>>,
        ): Specification<VideoDraft> {
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
        fun specify(specifier: Schema.Specification<VideoDraft, SVideoDraft>): Specification<VideoDraft> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SVideoDraft(root, criteriaBuilder)
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
            selectBuilder: Schema.ExpressionBuilder<SVideoDraft, E>,
            predicateBuilder: Schema.PredicateBuilder<SVideoDraft>,
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
            subqueryConfigure: Schema.SubqueryConfigure<E, SVideoDraft>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(VideoDraft::class.java)
            val schema = SVideoDraft(root, criteriaBuilder)
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
        fun predicateById(id: Any): AggregatePredicate<AggVideoDraft, VideoDraft> {
            return JpaPredicate.byId(VideoDraft::class.java, id).toAggregatePredicate(AggVideoDraft::class.java)
        }

        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): AggregatePredicate<AggVideoDraft, VideoDraft> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(VideoDraft::class.java, ids as Iterable<Any>).toAggregatePredicate(AggVideoDraft::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): AggregatePredicate<AggVideoDraft, VideoDraft> {
            return JpaPredicate.byIds(VideoDraft::class.java, ids.toList()).toAggregatePredicate(AggVideoDraft::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SVideoDraft>): AggregatePredicate<AggVideoDraft, VideoDraft> {
            return JpaPredicate.bySpecification(VideoDraft::class.java, specify(builder)).toAggregatePredicate(AggVideoDraft::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SVideoDraft>, distinct: Boolean): AggregatePredicate<AggVideoDraft, VideoDraft> {
            return JpaPredicate.bySpecification(VideoDraft::class.java, specify(builder, distinct)).toAggregatePredicate(AggVideoDraft::class.java)
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
            builder: Schema.PredicateBuilder<SVideoDraft>,
            orderBuilders: List<Schema.OrderBuilder<SVideoDraft>>,
        ): AggregatePredicate<AggVideoDraft, VideoDraft> {
            return JpaPredicate.bySpecification(VideoDraft::class.java, specify(builder, false, orderBuilders)).toAggregatePredicate(AggVideoDraft::class.java)
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
            builder: Schema.PredicateBuilder<SVideoDraft>,
            vararg orderBuilders: Schema.OrderBuilder<SVideoDraft>,
        ): AggregatePredicate<AggVideoDraft, VideoDraft> {
            return JpaPredicate.bySpecification(VideoDraft::class.java, specify(builder, false, *orderBuilders)).toAggregatePredicate(AggVideoDraft::class.java)
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
            builder: Schema.PredicateBuilder<SVideoDraft>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SVideoDraft>>,
        ): AggregatePredicate<AggVideoDraft, VideoDraft> {
            return JpaPredicate.bySpecification(VideoDraft::class.java, specify(builder, distinct, orderBuilders)).toAggregatePredicate(AggVideoDraft::class.java)
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
            builder: Schema.PredicateBuilder<SVideoDraft>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SVideoDraft>,
        ): AggregatePredicate<AggVideoDraft, VideoDraft> {
            return JpaPredicate.bySpecification(VideoDraft::class.java, specify(builder, distinct, *orderBuilders)).toAggregatePredicate(AggVideoDraft::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: Schema.Specification<VideoDraft, SVideoDraft>): AggregatePredicate<AggVideoDraft, VideoDraft> {
            return JpaPredicate.bySpecification(VideoDraft::class.java, specify(specifier)).toAggregatePredicate(AggVideoDraft::class.java)
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
            filterBuilder: java.util.function.Function<QVideoDraft, com.querydsl.core.types.Predicate>,
            vararg orderSpecifierBuilders: java.util.function.Function<QVideoDraft, OrderSpecifier<*>>,
        ): AggregatePredicate<AggVideoDraft, VideoDraft> {
            return QuerydslPredicate.of(VideoDraft::class.java)
                .where(filterBuilder.apply(QVideoDraft.videoDraft))
                .orderBy(*orderSpecifierBuilders.map { it.apply(QVideoDraft.videoDraft) }.toTypedArray())
                .toAggregatePredicate(AggVideoDraft::class.java)
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
        ): AggregatePredicate<AggVideoDraft, VideoDraft> {
            return QuerydslPredicate.of(VideoDraft::class.java)
                .where(filter)
                .orderBy(*orderSpecifiers)
                .toAggregatePredicate(AggVideoDraft::class.java)
        }
    }

    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<VideoDraft> = root


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
     * 视频封面
     * varchar(50)
     */
    fun videoCover(): Schema.Field<String> {
        return Schema.Field(root.get(props.videoCover), this.criteriaBuilder)
    }
    /**
     * 视频名称
     * varchar(100)
     */
    fun videoName(): Schema.Field<String> {
        return Schema.Field(root.get(props.videoName), this.criteriaBuilder)
    }
    /**
     * 用户ID
     * bigint
     */
    fun customerId(): Schema.Field<Long> {
        return Schema.Field(root.get(props.customerId), this.criteriaBuilder)
    }
    /**
     * 父级分类ID
     * bigint
     */
    fun pCategoryId(): Schema.Field<Long> {
        return Schema.Field(root.get(props.pCategoryId), this.criteriaBuilder)
    }
    /**
     * 分类ID
     * bigint
     */
    fun categoryId(): Schema.Field<Long?> {
        return Schema.Field(root.get(props.categoryId), this.criteriaBuilder)
    }
    /**
     * 视频状态
     * 0:UNKNOW:未知状态
     * 1:TRANSCODING:转码中
     * 2:TRANSCODE_FAILED:转码失败
     * 3:PENDING_REVIEW:待审核
     * 4:REVIEW_PASSED:审核成功
     * 5:REVIEW_FAILED:审核失败
     * tinyint(1)
     */
    fun status(): Schema.Field<com.edu.only4.danmuku.domain.aggregates.video_draft.enums.VideoStatus> {
        return Schema.Field(root.get(props.status), this.criteriaBuilder)
    }
    /**
     * 投稿类型
     * 0:UNKNOW:未知类型
     * 1:ORIGINAL:自制作
     * 2:REPOST:转载
     * tinyint
     */
    fun postType(): Schema.Field<com.edu.only4.danmuku.domain.aggregates.video_draft.enums.PostType> {
        return Schema.Field(root.get(props.postType), this.criteriaBuilder)
    }
    /**
     * 原资源说明
     * varchar(200)
     */
    fun originInfo(): Schema.Field<String?> {
        return Schema.Field(root.get(props.originInfo), this.criteriaBuilder)
    }
    /**
     * 标签
     * varchar(300)
     */
    fun tags(): Schema.Field<String?> {
        return Schema.Field(root.get(props.tags), this.criteriaBuilder)
    }
    /**
     * 简介
     * varchar(2000)
     */
    fun introduction(): Schema.Field<String?> {
        return Schema.Field(root.get(props.introduction), this.criteriaBuilder)
    }
    /**
     * 互动设置
     * varchar(5)
     */
    fun interaction(): Schema.Field<String?> {
        return Schema.Field(root.get(props.interaction), this.criteriaBuilder)
    }
    /**
     * 持续时间（秒）
     * int
     */
    fun duration(): Schema.Field<Int?> {
        return Schema.Field(root.get(props.duration), this.criteriaBuilder)
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
    fun spec(builder: Schema.PredicateBuilder<SVideoDraft>): Predicate {
        return builder.build(this)
    }


}
