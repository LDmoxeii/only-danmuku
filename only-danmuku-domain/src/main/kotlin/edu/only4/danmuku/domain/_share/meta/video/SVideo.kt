package edu.only4.danmuku.domain._share.meta.video

import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePredicate
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import com.only4.cap4k.ddd.domain.repo.querydsl.QuerydslPredicate

import com.querydsl.core.types.OrderSpecifier
import edu.only4.danmuku.domain._share.meta.Schema
import edu.only4.danmuku.domain.aggregates.video.AggVideo
import edu.only4.danmuku.domain.aggregates.video.QVideo
import edu.only4.danmuku.domain.aggregates.video.Video
import edu.only4.danmuku.domain.aggregates.video.enums.PostType
import edu.only4.danmuku.domain.aggregates.video.enums.RecommendType

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
class SVideo(
    private val root: Path<Video>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {

        val id = "id"

        val customerId = "customerId"

        val videoCover = "videoCover"

        val videoName = "videoName"

        val pCategoryId = "pCategoryId"

        val categoryId = "categoryId"

        val postType = "postType"

        val originInfo = "originInfo"

        val tags = "tags"

        val introduction = "introduction"

        val interaction = "interaction"

        val duration = "duration"

        val playCount = "playCount"

        val likeCount = "likeCount"

        val danmukuCount = "danmukuCount"

        val commentCount = "commentCount"

        val coinCount = "coinCount"

        val collectCount = "collectCount"

        val recommendType = "recommendType"

        val lastPlayTime = "lastPlayTime"

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
        fun specify(builder: Schema.PredicateBuilder<SVideo>): Specification<Video> {
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
        fun specify(builder: Schema.PredicateBuilder<SVideo>, distinct: Boolean): Specification<Video> {
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
            builder: Schema.PredicateBuilder<SVideo>,
            vararg orderBuilders: Schema.OrderBuilder<SVideo>,
        ): Specification<Video> {
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
            builder: Schema.PredicateBuilder<SVideo>,
            orderBuilders: List<Schema.OrderBuilder<SVideo>>,
        ): Specification<Video> {
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
            builder: Schema.PredicateBuilder<SVideo>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SVideo>,
        ): Specification<Video> {
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
            builder: Schema.PredicateBuilder<SVideo>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SVideo>>,
        ): Specification<Video> {
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
        fun specify(specifier: Schema.Specification<Video, SVideo>): Specification<Video> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SVideo(root, criteriaBuilder)
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
            selectBuilder: Schema.ExpressionBuilder<SVideo, E>,
            predicateBuilder: Schema.PredicateBuilder<SVideo>,
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
            subqueryConfigure: Schema.SubqueryConfigure<E, SVideo>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(Video::class.java)
            val schema = SVideo(root, criteriaBuilder)
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
        fun predicateById(id: Any): AggregatePredicate<AggVideo, Video> {
            return JpaPredicate.byId(Video::class.java, id).toAggregatePredicate(AggVideo::class.java)
        }

        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): AggregatePredicate<AggVideo, Video> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(Video::class.java, ids as Iterable<Any>).toAggregatePredicate(AggVideo::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): AggregatePredicate<AggVideo, Video> {
            return JpaPredicate.byIds(Video::class.java, ids.toList()).toAggregatePredicate(AggVideo::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SVideo>): AggregatePredicate<AggVideo, Video> {
            return JpaPredicate.bySpecification(Video::class.java, specify(builder)).toAggregatePredicate(AggVideo::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SVideo>, distinct: Boolean): AggregatePredicate<AggVideo, Video> {
            return JpaPredicate.bySpecification(Video::class.java, specify(builder, distinct)).toAggregatePredicate(AggVideo::class.java)
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
            builder: Schema.PredicateBuilder<SVideo>,
            orderBuilders: List<Schema.OrderBuilder<SVideo>>,
        ): AggregatePredicate<AggVideo, Video> {
            return JpaPredicate.bySpecification(Video::class.java, specify(builder, false, orderBuilders)).toAggregatePredicate(AggVideo::class.java)
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
            builder: Schema.PredicateBuilder<SVideo>,
            vararg orderBuilders: Schema.OrderBuilder<SVideo>,
        ): AggregatePredicate<AggVideo, Video> {
            return JpaPredicate.bySpecification(Video::class.java, specify(builder, false, *orderBuilders)).toAggregatePredicate(AggVideo::class.java)
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
            builder: Schema.PredicateBuilder<SVideo>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SVideo>>,
        ): AggregatePredicate<AggVideo, Video> {
            return JpaPredicate.bySpecification(Video::class.java, specify(builder, distinct, orderBuilders)).toAggregatePredicate(AggVideo::class.java)
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
            builder: Schema.PredicateBuilder<SVideo>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SVideo>,
        ): AggregatePredicate<AggVideo, Video> {
            return JpaPredicate.bySpecification(Video::class.java, specify(builder, distinct, *orderBuilders)).toAggregatePredicate(AggVideo::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: Schema.Specification<Video, SVideo>): AggregatePredicate<AggVideo, Video> {
            return JpaPredicate.bySpecification(Video::class.java, specify(specifier)).toAggregatePredicate(AggVideo::class.java)
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
            filterBuilder: java.util.function.Function<QVideo, com.querydsl.core.types.Predicate>,
            vararg orderSpecifierBuilders: java.util.function.Function<QVideo, OrderSpecifier<*>>,
        ): AggregatePredicate<AggVideo, Video> {
            return QuerydslPredicate.of(Video::class.java)
                .where(filterBuilder.apply(QVideo.video))
                .orderBy(*orderSpecifierBuilders.map { it.apply(QVideo.video) }.toTypedArray())
                .toAggregatePredicate(AggVideo::class.java)
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
        ): AggregatePredicate<AggVideo, Video> {
            return QuerydslPredicate.of(Video::class.java)
                .where(filter)
                .orderBy(*orderSpecifiers)
                .toAggregatePredicate(AggVideo::class.java)
        }
    }

    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<Video> = root


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
     * 播放数量
     */
    val playCount: Schema.Field<Int?> by lazy {
        Schema.Field(root.get("playCount"), criteriaBuilder)
    }


    /**
     * 点赞数量
     */
    val likeCount: Schema.Field<Int?> by lazy {
        Schema.Field(root.get("likeCount"), criteriaBuilder)
    }


    /**
     * 弹幕数量
     */
    val danmukuCount: Schema.Field<Int?> by lazy {
        Schema.Field(root.get("danmukuCount"), criteriaBuilder)
    }


    /**
     * 评论数量
     */
    val commentCount: Schema.Field<Int?> by lazy {
        Schema.Field(root.get("commentCount"), criteriaBuilder)
    }


    /**
     * 投币数量
     */
    val coinCount: Schema.Field<Int?> by lazy {
        Schema.Field(root.get("coinCount"), criteriaBuilder)
    }


    /**
     * 收藏数量
     */
    val collectCount: Schema.Field<Int?> by lazy {
        Schema.Field(root.get("collectCount"), criteriaBuilder)
    }


    /**
     * 推荐状态
     */
    val recommendType: Schema.Field<RecommendType> by lazy {
        Schema.Field(root.get("recommendType"), criteriaBuilder)
    }


    /**
     * 最后播放时间
     */
    val lastPlayTime: Schema.Field<java.time.LocalDateTime?> by lazy {
        Schema.Field(root.get("lastPlayTime"), criteriaBuilder)
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
    fun spec(builder: Schema.PredicateBuilder<SVideo>): Predicate
    {
        return builder.build(this)
    }
}
