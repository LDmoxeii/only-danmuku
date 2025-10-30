package edu.only4.danmuku.domain._share.meta.video_post

import com.only4.cap4k.ddd.domain.repo.JpaPredicate

import edu.only4.danmuku.domain._share.meta.ExpressionBuilder
import edu.only4.danmuku.domain._share.meta.Field
import edu.only4.danmuku.domain._share.meta.OrderBuilder
import edu.only4.danmuku.domain._share.meta.PredicateBuilder
import edu.only4.danmuku.domain._share.meta.SchemaSpecification
import edu.only4.danmuku.domain._share.meta.SubqueryConfigure
import edu.only4.danmuku.domain.aggregates.video.enums.PostType
import edu.only4.danmuku.domain.aggregates.video_post.VideoPost
import edu.only4.danmuku.domain.aggregates.video_post.enums.VideoStatus

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
 * @date 2025/10/30
 */
class SVideoPost(
    private val root: Path<VideoPost>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {

        val id = "id"

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
        fun specify(builder: PredicateBuilder<SVideoPost>): Specification<VideoPost> {
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
        fun specify(builder: PredicateBuilder<SVideoPost>, distinct: Boolean): Specification<VideoPost> {
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
            builder: PredicateBuilder<SVideoPost>,
            vararg orderBuilders: OrderBuilder<SVideoPost>,
        ): Specification<VideoPost> {
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
            builder: PredicateBuilder<SVideoPost>,
            orderBuilders: List<OrderBuilder<SVideoPost>>,
        ): Specification<VideoPost> {
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
            builder: PredicateBuilder<SVideoPost>,
            distinct: Boolean,
            vararg orderBuilders: OrderBuilder<SVideoPost>,
        ): Specification<VideoPost> {
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
            builder: PredicateBuilder<SVideoPost>,
            distinct: Boolean,
            orderBuilders: List<OrderBuilder<SVideoPost>>,
        ): Specification<VideoPost> {
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
        fun specify(specifier: SchemaSpecification<VideoPost, SVideoPost>): Specification<VideoPost> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SVideoPost(root, criteriaBuilder)
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
            selectBuilder: ExpressionBuilder<SVideoPost, E>,
            predicateBuilder: PredicateBuilder<SVideoPost>,
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
            subqueryConfigure: SubqueryConfigure<E, SVideoPost>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(VideoPost::class.java)
            val schema = SVideoPost(root, criteriaBuilder)
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
        fun predicateById(id: Any): JpaPredicate<VideoPost> {
            return JpaPredicate.byId(VideoPost::class.java, id)
        }

        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): JpaPredicate<VideoPost> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(VideoPost::class.java, ids as Iterable<Any>)
        }

        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): JpaPredicate<VideoPost> {
            return JpaPredicate.byIds(VideoPost::class.java, ids.toList())
        }

        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: PredicateBuilder<SVideoPost>): JpaPredicate<VideoPost> {
            return JpaPredicate.bySpecification(VideoPost::class.java, specify(builder))
        }

        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: PredicateBuilder<SVideoPost>, distinct: Boolean): JpaPredicate<VideoPost> {
            return JpaPredicate.bySpecification(VideoPost::class.java, specify(builder, distinct))
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
            builder: PredicateBuilder<SVideoPost>,
            orderBuilders: List<OrderBuilder<SVideoPost>>,
        ): JpaPredicate<VideoPost> {
            return JpaPredicate.bySpecification(VideoPost::class.java, specify(builder, false, orderBuilders))
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
            builder: PredicateBuilder<SVideoPost>,
            vararg orderBuilders: OrderBuilder<SVideoPost>,
        ): JpaPredicate<VideoPost> {
            return JpaPredicate.bySpecification(VideoPost::class.java, specify(builder, false, *orderBuilders))
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
            builder: PredicateBuilder<SVideoPost>,
            distinct: Boolean,
            orderBuilders: List<OrderBuilder<SVideoPost>>,
        ): JpaPredicate<VideoPost> {
            return JpaPredicate.bySpecification(VideoPost::class.java, specify(builder, distinct, orderBuilders))
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
            builder: PredicateBuilder<SVideoPost>,
            distinct: Boolean,
            vararg orderBuilders: OrderBuilder<SVideoPost>,
        ): JpaPredicate<VideoPost> {
            return JpaPredicate.bySpecification(VideoPost::class.java, specify(builder, distinct, *orderBuilders))
        }

        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: SchemaSpecification<VideoPost, SVideoPost>): JpaPredicate<VideoPost> {
            return JpaPredicate.bySpecification(VideoPost::class.java, specify(specifier))
        }

    }

    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<VideoPost> = root


    /**
     * ID
     */
    val id: Field<Long> by lazy {
        Field(root.get("id"), criteriaBuilder)
    }


    /**
     * 视频封面
     */
    val videoCover: Field<String> by lazy {
        Field(root.get("videoCover"), criteriaBuilder)
    }


    /**
     * 视频名称
     */
    val videoName: Field<String> by lazy {
        Field(root.get("videoName"), criteriaBuilder)
    }


    /**
     * 用户ID
     */
    val customerId: Field<Long> by lazy {
        Field(root.get("customerId"), criteriaBuilder)
    }


    /**
     * 父级分类ID
     */
    val pCategoryId: Field<Long> by lazy {
        Field(root.get("pCategoryId"), criteriaBuilder)
    }


    /**
     * 分类ID
     */
    val categoryId: Field<Long?> by lazy {
        Field(root.get("categoryId"), criteriaBuilder)
    }


    /**
     * 视频状态
     */
    val status: Field<VideoStatus> by lazy {
        Field(root.get("status"), criteriaBuilder)
    }


    /**
     * 投稿类型
     */
    val postType: Field<PostType> by lazy {
        Field(root.get("postType"), criteriaBuilder)
    }


    /**
     * 原资源说明
     */
    val originInfo: Field<String?> by lazy {
        Field(root.get("originInfo"), criteriaBuilder)
    }


    /**
     * 标签
     */
    val tags: Field<String?> by lazy {
        Field(root.get("tags"), criteriaBuilder)
    }


    /**
     * 简介
     */
    val introduction: Field<String?> by lazy {
        Field(root.get("introduction"), criteriaBuilder)
    }


    /**
     * 互动设置
     */
    val interaction: Field<String?> by lazy {
        Field(root.get("interaction"), criteriaBuilder)
    }


    /**
     * 持续时间（秒）
     */
    val duration: Field<Int> by lazy {
        Field(root.get("duration"), criteriaBuilder)
    }


    /**
     * 创建人ID
     */
    val createUserId: Field<Long?> by lazy {
        Field(root.get("createUserId"), criteriaBuilder)
    }


    /**
     * 创建人名称
     */
    val createBy: Field<String?> by lazy {
        Field(root.get("createBy"), criteriaBuilder)
    }


    /**
     * 创建时间
     */
    val createTime: Field<Long?> by lazy {
        Field(root.get("createTime"), criteriaBuilder)
    }


    /**
     * 更新人ID
     */
    val updateUserId: Field<Long?> by lazy {
        Field(root.get("updateUserId"), criteriaBuilder)
    }


    /**
     * 更新人名称
     */
    val updateBy: Field<String?> by lazy {
        Field(root.get("updateBy"), criteriaBuilder)
    }


    /**
     * 更新时间
     */
    val updateTime: Field<Long?> by lazy {
        Field(root.get("updateTime"), criteriaBuilder)
    }


    /**
     * 删除标识 0：未删除 id：已删除
     */
    val deleted: Field<Long> by lazy {
        Field(root.get("deleted"), criteriaBuilder)
    }



    /**
     * 关联: OneToMany - VideoFilePost
     */
    val videoFilePosts: Field<Any> by lazy {
        Field(root.get("videoFilePosts"), criteriaBuilder)
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
     * 满足所有条件（过滤 null）
     * 类似 Jimmer 的 where { } 自动过滤 null 的行为
     */
    fun allNotNull(vararg restrictions: Predicate?): Predicate? {
        val nonNullRestrictions = restrictions.filterNotNull().toTypedArray()
        return when {
            nonNullRestrictions.isEmpty() -> null
            nonNullRestrictions.size == 1 -> nonNullRestrictions[0]
            else -> criteriaBuilder.and(*nonNullRestrictions)
        }
    }

    /**
     * 满足任一条件（过滤 null）
     */
    fun anyNotNull(vararg restrictions: Predicate?): Predicate? {
        val nonNullRestrictions = restrictions.filterNotNull().toTypedArray()
        return when {
            nonNullRestrictions.isEmpty() -> null
            nonNullRestrictions.size == 1 -> nonNullRestrictions[0]
            else -> criteriaBuilder.or(*nonNullRestrictions)
        }
    }

    /**
     * NOT 操作
     */
    fun not(restriction: Predicate): Predicate = criteriaBuilder.not(restriction)

    /**
     * 指定条件
     * @param builder
     * @return
     */
    fun spec(builder: PredicateBuilder<SVideoPost>): Predicate {
        return builder.build(this)
    }
}
