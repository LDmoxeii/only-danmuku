package edu.only4.danmuku.domain._share.meta.video_comment

import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePredicate
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import com.only4.cap4k.ddd.domain.repo.querydsl.QuerydslPredicate

import com.querydsl.core.types.OrderSpecifier
import edu.only4.danmuku.domain._share.meta.Schema
import edu.only4.danmuku.domain.aggregates.video_comment.AggVideoComment
import edu.only4.danmuku.domain.aggregates.video_comment.QVideoComment
import edu.only4.danmuku.domain.aggregates.video_comment.VideoComment

import jakarta.persistence.criteria.*

import org.springframework.data.jpa.domain.Specification

/**
 * 评论;
 *
 * Schema 类用于类型安全的 JPA Criteria 查询
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
class SVideoComment(
    private val root: Path<VideoComment>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {

        val id = "id"

        val parentId = "parentId"

        val videoId = "videoId"

        val videoOwnerId = "videoOwnerId"

        val content = "content"

        val imgPath = "imgPath"

        val customerId = "customerId"

        val replyCustomerId = "replyCustomerId"

        val topType = "topType"

        val postTime = "postTime"

        val likeCount = "likeCount"

        val hateCount = "hateCount"

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
        fun specify(builder: Schema.PredicateBuilder<SVideoComment>): Specification<VideoComment> {
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
        fun specify(builder: Schema.PredicateBuilder<SVideoComment>, distinct: Boolean): Specification<VideoComment> {
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
            builder: Schema.PredicateBuilder<SVideoComment>,
            vararg orderBuilders: Schema.OrderBuilder<SVideoComment>,
        ): Specification<VideoComment> {
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
            builder: Schema.PredicateBuilder<SVideoComment>,
            orderBuilders: List<Schema.OrderBuilder<SVideoComment>>,
        ): Specification<VideoComment> {
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
            builder: Schema.PredicateBuilder<SVideoComment>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SVideoComment>,
        ): Specification<VideoComment> {
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
            builder: Schema.PredicateBuilder<SVideoComment>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SVideoComment>>,
        ): Specification<VideoComment> {
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
        fun specify(specifier: Schema.Specification<VideoComment, SVideoComment>): Specification<VideoComment> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SVideoComment(root, criteriaBuilder)
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
            selectBuilder: Schema.ExpressionBuilder<SVideoComment, E>,
            predicateBuilder: Schema.PredicateBuilder<SVideoComment>,
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
            subqueryConfigure: Schema.SubqueryConfigure<E, SVideoComment>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(VideoComment::class.java)
            val schema = SVideoComment(root, criteriaBuilder)
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
        fun predicateById(id: Any): AggregatePredicate<AggVideoComment, VideoComment> {
            return JpaPredicate.byId(VideoComment::class.java, id).toAggregatePredicate(AggVideoComment::class.java)
        }

        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): AggregatePredicate<AggVideoComment, VideoComment> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(VideoComment::class.java, ids as Iterable<Any>).toAggregatePredicate(AggVideoComment::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): AggregatePredicate<AggVideoComment, VideoComment> {
            return JpaPredicate.byIds(VideoComment::class.java, ids.toList()).toAggregatePredicate(AggVideoComment::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SVideoComment>): AggregatePredicate<AggVideoComment, VideoComment> {
            return JpaPredicate.bySpecification(VideoComment::class.java, specify(builder)).toAggregatePredicate(AggVideoComment::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SVideoComment>, distinct: Boolean): AggregatePredicate<AggVideoComment, VideoComment> {
            return JpaPredicate.bySpecification(VideoComment::class.java, specify(builder, distinct)).toAggregatePredicate(AggVideoComment::class.java)
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
            builder: Schema.PredicateBuilder<SVideoComment>,
            orderBuilders: List<Schema.OrderBuilder<SVideoComment>>,
        ): AggregatePredicate<AggVideoComment, VideoComment> {
            return JpaPredicate.bySpecification(VideoComment::class.java, specify(builder, false, orderBuilders)).toAggregatePredicate(AggVideoComment::class.java)
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
            builder: Schema.PredicateBuilder<SVideoComment>,
            vararg orderBuilders: Schema.OrderBuilder<SVideoComment>,
        ): AggregatePredicate<AggVideoComment, VideoComment> {
            return JpaPredicate.bySpecification(VideoComment::class.java, specify(builder, false, *orderBuilders)).toAggregatePredicate(AggVideoComment::class.java)
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
            builder: Schema.PredicateBuilder<SVideoComment>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SVideoComment>>,
        ): AggregatePredicate<AggVideoComment, VideoComment> {
            return JpaPredicate.bySpecification(VideoComment::class.java, specify(builder, distinct, orderBuilders)).toAggregatePredicate(AggVideoComment::class.java)
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
            builder: Schema.PredicateBuilder<SVideoComment>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SVideoComment>,
        ): AggregatePredicate<AggVideoComment, VideoComment> {
            return JpaPredicate.bySpecification(VideoComment::class.java, specify(builder, distinct, *orderBuilders)).toAggregatePredicate(AggVideoComment::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: Schema.Specification<VideoComment, SVideoComment>): AggregatePredicate<AggVideoComment, VideoComment> {
            return JpaPredicate.bySpecification(VideoComment::class.java, specify(specifier)).toAggregatePredicate(AggVideoComment::class.java)
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
            filterBuilder: java.util.function.Function<QVideoComment, com.querydsl.core.types.Predicate>,
            vararg orderSpecifierBuilders: java.util.function.Function<QVideoComment, OrderSpecifier<*>>,
        ): AggregatePredicate<AggVideoComment, VideoComment> {
            return QuerydslPredicate.of(VideoComment::class.java)
                .where(filterBuilder.apply(QVideoComment.videoComment))
                .orderBy(*orderSpecifierBuilders.map { it.apply(QVideoComment.videoComment) }.toTypedArray())
                .toAggregatePredicate(AggVideoComment::class.java)
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
        ): AggregatePredicate<AggVideoComment, VideoComment> {
            return QuerydslPredicate.of(VideoComment::class.java)
                .where(filter)
                .orderBy(*orderSpecifiers)
                .toAggregatePredicate(AggVideoComment::class.java)
        }
    }

    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<VideoComment> = root


    /**
     * 评论ID
     */
    val id: Schema.Field<Long> by lazy {
        Schema.Field(root.get("id"), criteriaBuilder)
    }


    /**
     * 父级评论ID
     */
    val parentId: Schema.Field<Long> by lazy {
        Schema.Field(root.get("parentId"), criteriaBuilder)
    }


    /**
     * 视频ID
     */
    val videoId: Schema.Field<Long> by lazy {
        Schema.Field(root.get("videoId"), criteriaBuilder)
    }


    /**
     * 视频用户ID
     */
    val videoOwnerId: Schema.Field<Long> by lazy {
        Schema.Field(root.get("videoOwnerId"), criteriaBuilder)
    }


    /**
     * 回复内容
     */
    val content: Schema.Field<String?> by lazy {
        Schema.Field(root.get("content"), criteriaBuilder)
    }


    /**
     * 图片
     */
    val imgPath: Schema.Field<String?> by lazy {
        Schema.Field(root.get("imgPath"), criteriaBuilder)
    }


    /**
     * 用户ID
     */
    val customerId: Schema.Field<Long> by lazy {
        Schema.Field(root.get("customerId"), criteriaBuilder)
    }


    /**
     * 回复人ID
     */
    val replyCustomerId: Schema.Field<Long?> by lazy {
        Schema.Field(root.get("replyCustomerId"), criteriaBuilder)
    }


    /**
     * 0:未置顶  1:置顶
     */
    val topType: Schema.Field<Byte?> by lazy {
        Schema.Field(root.get("topType"), criteriaBuilder)
    }


    /**
     * 发布时间
     */
    val postTime: Schema.Field<Long> by lazy {
        Schema.Field(root.get("postTime"), criteriaBuilder)
    }


    /**
     * 喜欢数量
     */
    val likeCount: Schema.Field<Int?> by lazy {
        Schema.Field(root.get("likeCount"), criteriaBuilder)
    }


    /**
     * 讨厌数量
     */
    val hateCount: Schema.Field<Int?> by lazy {
        Schema.Field(root.get("hateCount"), criteriaBuilder)
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
    fun spec(builder: Schema.PredicateBuilder<SVideoComment>): Predicate
    {
        return builder.build(this)
    }
}
