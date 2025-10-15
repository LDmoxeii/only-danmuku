package edu.only4.danmuku.domain._share.meta.video_draft

import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePredicate
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import com.only4.cap4k.ddd.domain.repo.querydsl.QuerydslPredicate

import com.querydsl.core.types.OrderSpecifier
import edu.only4.danmuku.domain._share.meta.Schema
import edu.only4.danmuku.domain.aggregates.video.enums.PostType
import edu.only4.danmuku.domain.aggregates.video_draft.AggVideoDraft
import edu.only4.danmuku.domain.aggregates.video_draft.QVideoDraft
import edu.only4.danmuku.domain.aggregates.video_draft.VideoDraft
import edu.only4.danmuku.domain.aggregates.video_draft.enums.VideoStatus

import jakarta.persistence.criteria.*

import org.springframework.data.jpa.domain.Specification

/**
 * 视频信息;
 *
 * Schema 类用于类型安全的 JPA Criteria 查询
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
class SVideoDraft(
    private val root: Path<VideoDraft>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {

        val id = "id"

        val videoId = "videoId"

        val videoCover = "videoCover"

        val videoName = "videoName"

        val customerId = "customerId"

        val pCategoryId = "pCategoryId"

        val categoryId = "categoryId"

        val status = "status"

        val postType = "postType"

        val originInfo = "originInfo"

        val tags = "tags"

        val introduction = "introduction"

        val interaction = "interaction"

        val duration = "duration"

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
     */
    val id: Schema.Field<Long> by lazy {
        Schema.Field(root.get("id"), criteriaBuilder)
    }


    /**
     * 视频ID
     */
    val videoId: Schema.Field<Long> by lazy {
        Schema.Field(root.get("videoId"), criteriaBuilder)
    }


    /**
     * 视频封面
     */
    val videoCover: Schema.Field<String> by lazy {
        Schema.Field(root.get("videoCover"), criteriaBuilder)
    }


    /**
     * 视频名称
     */
    val videoName: Schema.Field<String> by lazy {
        Schema.Field(root.get("videoName"), criteriaBuilder)
    }


    /**
     * 用户ID
     */
    val customerId: Schema.Field<Long> by lazy {
        Schema.Field(root.get("customerId"), criteriaBuilder)
    }


    /**
     * 父级分类ID
     */
    val pCategoryId: Schema.Field<Long> by lazy {
        Schema.Field(root.get("pCategoryId"), criteriaBuilder)
    }


    /**
     * 分类ID
     */
    val categoryId: Schema.Field<Long?> by lazy {
        Schema.Field(root.get("categoryId"), criteriaBuilder)
    }


    /**
     * 视频状态
     */
    val status: Schema.Field<VideoStatus> by lazy {
        Schema.Field(root.get("status"), criteriaBuilder)
    }


    /**
     * 投稿类型
     */
    val postType: Schema.Field<PostType> by lazy {
        Schema.Field(root.get("postType"), criteriaBuilder)
    }


    /**
     * 原资源说明
     */
    val originInfo: Schema.Field<String?> by lazy {
        Schema.Field(root.get("originInfo"), criteriaBuilder)
    }


    /**
     * 标签
     */
    val tags: Schema.Field<String?> by lazy {
        Schema.Field(root.get("tags"), criteriaBuilder)
    }


    /**
     * 简介
     */
    val introduction: Schema.Field<String?> by lazy {
        Schema.Field(root.get("introduction"), criteriaBuilder)
    }


    /**
     * 互动设置
     */
    val interaction: Schema.Field<String?> by lazy {
        Schema.Field(root.get("interaction"), criteriaBuilder)
    }


    /**
     * 持续时间（秒）
     */
    val duration: Schema.Field<Int?> by lazy {
        Schema.Field(root.get("duration"), criteriaBuilder)
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
    fun spec(builder: Schema.PredicateBuilder<SVideoDraft>): Predicate
    {
        return builder.build(this)
    }
}
