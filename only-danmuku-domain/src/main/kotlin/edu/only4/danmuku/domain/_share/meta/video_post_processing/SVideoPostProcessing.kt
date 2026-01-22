package edu.only4.danmuku.domain._share.meta.video_post_processing

import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import edu.only4.danmuku.domain._share.meta.ExpressionBuilder
import edu.only4.danmuku.domain._share.meta.Field
import edu.only4.danmuku.domain._share.meta.OrderBuilder
import edu.only4.danmuku.domain._share.meta.PredicateBuilder
import edu.only4.danmuku.domain._share.meta.SchemaSpecification
import edu.only4.danmuku.domain._share.meta.SubqueryConfigure
import edu.only4.danmuku.domain.aggregates.video_post_processing.VideoPostProcessing
import edu.only4.danmuku.domain.aggregates.video_post_processing.enums.ProcessStatus
import jakarta.persistence.criteria.*
import org.springframework.data.jpa.domain.Specification

/**
 * Schema 类用于类型安全的 JPA Criteria 查询
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 */
class SVideoPostProcessing(
    private val root: Path<VideoPostProcessing>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {

        val id = "id"

        val videoPostId = "videoPostId"

        val totalFiles = "totalFiles"

        val transcodeStatus = "transcodeStatus"

        val encryptStatus = "encryptStatus"

        val transcodeDoneCount = "transcodeDoneCount"

        val encryptDoneCount = "encryptDoneCount"

        val failedCount = "failedCount"

        val lastFailReason = "lastFailReason"

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
        fun specify(builder: PredicateBuilder<SVideoPostProcessing>): Specification<VideoPostProcessing> {
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
        fun specify(builder: PredicateBuilder<SVideoPostProcessing>, distinct: Boolean): Specification<VideoPostProcessing> {
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
            builder: PredicateBuilder<SVideoPostProcessing>,
            vararg orderBuilders: OrderBuilder<SVideoPostProcessing>,
        ): Specification<VideoPostProcessing> {
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
            builder: PredicateBuilder<SVideoPostProcessing>,
            orderBuilders: List<OrderBuilder<SVideoPostProcessing>>,
        ): Specification<VideoPostProcessing> {
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
            builder: PredicateBuilder<SVideoPostProcessing>,
            distinct: Boolean,
            vararg orderBuilders: OrderBuilder<SVideoPostProcessing>,
        ): Specification<VideoPostProcessing> {
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
            builder: PredicateBuilder<SVideoPostProcessing>,
            distinct: Boolean,
            orderBuilders: List<OrderBuilder<SVideoPostProcessing>>,
        ): Specification<VideoPostProcessing> {
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
        fun specify(specifier: SchemaSpecification<VideoPostProcessing, SVideoPostProcessing>): Specification<VideoPostProcessing> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SVideoPostProcessing(root, criteriaBuilder)
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
            selectBuilder: ExpressionBuilder<SVideoPostProcessing, E>,
            predicateBuilder: PredicateBuilder<SVideoPostProcessing>,
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
            subqueryConfigure: SubqueryConfigure<E, SVideoPostProcessing>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(VideoPostProcessing::class.java)
            val schema = SVideoPostProcessing(root, criteriaBuilder)
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
        fun predicateById(id: Any): JpaPredicate<VideoPostProcessing> {
            return JpaPredicate.byId(VideoPostProcessing::class.java, id)
        }

        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): JpaPredicate<VideoPostProcessing> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(VideoPostProcessing::class.java, ids as Iterable<Any>)
        }

        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): JpaPredicate<VideoPostProcessing> {
            return JpaPredicate.byIds(VideoPostProcessing::class.java, ids.toList())
        }

        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: PredicateBuilder<SVideoPostProcessing>): JpaPredicate<VideoPostProcessing> {
            return JpaPredicate.bySpecification(VideoPostProcessing::class.java, specify(builder))
        }

        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: PredicateBuilder<SVideoPostProcessing>, distinct: Boolean): JpaPredicate<VideoPostProcessing> {
            return JpaPredicate.bySpecification(VideoPostProcessing::class.java, specify(builder, distinct))
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
            builder: PredicateBuilder<SVideoPostProcessing>,
            orderBuilders: List<OrderBuilder<SVideoPostProcessing>>,
        ): JpaPredicate<VideoPostProcessing> {
            return JpaPredicate.bySpecification(VideoPostProcessing::class.java, specify(builder, false, orderBuilders))
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
            builder: PredicateBuilder<SVideoPostProcessing>,
            vararg orderBuilders: OrderBuilder<SVideoPostProcessing>,
        ): JpaPredicate<VideoPostProcessing> {
            return JpaPredicate.bySpecification(VideoPostProcessing::class.java, specify(builder, false, *orderBuilders))
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
            builder: PredicateBuilder<SVideoPostProcessing>,
            distinct: Boolean,
            orderBuilders: List<OrderBuilder<SVideoPostProcessing>>,
        ): JpaPredicate<VideoPostProcessing> {
            return JpaPredicate.bySpecification(VideoPostProcessing::class.java, specify(builder, distinct, orderBuilders))
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
            builder: PredicateBuilder<SVideoPostProcessing>,
            distinct: Boolean,
            vararg orderBuilders: OrderBuilder<SVideoPostProcessing>,
        ): JpaPredicate<VideoPostProcessing> {
            return JpaPredicate.bySpecification(VideoPostProcessing::class.java, specify(builder, distinct, *orderBuilders))
        }

        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: SchemaSpecification<VideoPostProcessing, SVideoPostProcessing>): JpaPredicate<VideoPostProcessing> {
            return JpaPredicate.bySpecification(VideoPostProcessing::class.java, specify(specifier))
        }

    }

    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<VideoPostProcessing> = root


    /**
     * ID
     */
    val id: Field<Long> by lazy {
        Field(root.get("id"), criteriaBuilder)
    }


    /**
     * 视频稿件ID
     */
    val videoPostId: Field<Long> by lazy {
        Field(root.get("videoPostId"), criteriaBuilder)
    }


    /**
     * 分P总数
     */
    val totalFiles: Field<Int> by lazy {
        Field(root.get("totalFiles"), criteriaBuilder)
    }


    /**
     * 转码状态
     */
    val transcodeStatus: Field<ProcessStatus> by lazy {
        Field(root.get("transcodeStatus"), criteriaBuilder)
    }


    /**
     * 加密状态
     */
    val encryptStatus: Field<ProcessStatus> by lazy {
        Field(root.get("encryptStatus"), criteriaBuilder)
    }


    /**
     * 转码完成数
     */
    val transcodeDoneCount: Field<Int> by lazy {
        Field(root.get("transcodeDoneCount"), criteriaBuilder)
    }


    /**
     * 加密完成数（含 SKIPPED）
     */
    val encryptDoneCount: Field<Int> by lazy {
        Field(root.get("encryptDoneCount"), criteriaBuilder)
    }


    /**
     * 失败文件数
     */
    val failedCount: Field<Int> by lazy {
        Field(root.get("failedCount"), criteriaBuilder)
    }


    /**
     * 最近失败原因
     */
    val lastFailReason: Field<String?> by lazy {
        Field(root.get("lastFailReason"), criteriaBuilder)
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
     * 关联: OneToMany - VideoPostProcessingFile
     */
    val videoPostProcessingFiles: Field<Any> by lazy {
        Field(root.get("videoPostProcessingFiles"), criteriaBuilder)
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
    fun spec(builder: PredicateBuilder<SVideoPostProcessing>): Predicate {
        return builder.build(this)
    }
}
