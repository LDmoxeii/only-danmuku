package edu.only4.danmuku.domain._share.meta.video_file_draft

import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePredicate
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import com.only4.cap4k.ddd.domain.repo.querydsl.QuerydslPredicate
import com.querydsl.core.types.OrderSpecifier
import edu.only4.danmuku.domain._share.meta.*
import edu.only4.danmuku.domain.aggregates.video_file_draft.AggVideoFileDraft
import edu.only4.danmuku.domain.aggregates.video_file_draft.QVideoFileDraft
import edu.only4.danmuku.domain.aggregates.video_file_draft.VideoFileDraft
import edu.only4.danmuku.domain.aggregates.video_file_draft.enums.TransferResult
import edu.only4.danmuku.domain.aggregates.video_file_draft.enums.UpdateType
import jakarta.persistence.criteria.*
import org.springframework.data.jpa.domain.Specification

/**
 * 视频文件信息;
 *
 * Schema 类用于类型安全的 JPA Criteria 查询
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/10/20
 */
class SVideoFileDraft(
    private val root: Path<VideoFileDraft>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {

        val id = "id"

        val fileId = "fileId"

        val uploadId = "uploadId"

        val customerId = "customerId"

        val videoId = "videoId"

        val fileIndex = "fileIndex"

        val fileName = "fileName"

        val fileSize = "fileSize"

        val filePath = "filePath"

        val updateType = "updateType"

        val transferResult = "transferResult"

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
        fun specify(builder: PredicateBuilder<SVideoFileDraft>): Specification<VideoFileDraft> {
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
        fun specify(builder: PredicateBuilder<SVideoFileDraft>, distinct: Boolean): Specification<VideoFileDraft> {
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
            builder: PredicateBuilder<SVideoFileDraft>,
            vararg orderBuilders: OrderBuilder<SVideoFileDraft>,
        ): Specification<VideoFileDraft> {
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
            builder: PredicateBuilder<SVideoFileDraft>,
            orderBuilders: List<OrderBuilder<SVideoFileDraft>>,
        ): Specification<VideoFileDraft> {
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
            builder: PredicateBuilder<SVideoFileDraft>,
            distinct: Boolean,
            vararg orderBuilders: OrderBuilder<SVideoFileDraft>,
        ): Specification<VideoFileDraft> {
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
            builder: PredicateBuilder<SVideoFileDraft>,
            distinct: Boolean,
            orderBuilders: List<OrderBuilder<SVideoFileDraft>>,
        ): Specification<VideoFileDraft> {
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
        fun specify(specifier: SchemaSpecification<VideoFileDraft, SVideoFileDraft>): Specification<VideoFileDraft> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SVideoFileDraft(root, criteriaBuilder)
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
            selectBuilder: ExpressionBuilder<SVideoFileDraft, E>,
            predicateBuilder: PredicateBuilder<SVideoFileDraft>,
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
            subqueryConfigure: SubqueryConfigure<E, SVideoFileDraft>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(VideoFileDraft::class.java)
            val schema = SVideoFileDraft(root, criteriaBuilder)
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
        fun predicateById(id: Any): AggregatePredicate<AggVideoFileDraft, VideoFileDraft> {
            return JpaPredicate.byId(VideoFileDraft::class.java, id).toAggregatePredicate(AggVideoFileDraft::class.java)
        }

        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): AggregatePredicate<AggVideoFileDraft, VideoFileDraft> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(VideoFileDraft::class.java, ids as Iterable<Any>).toAggregatePredicate(AggVideoFileDraft::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): AggregatePredicate<AggVideoFileDraft, VideoFileDraft> {
            return JpaPredicate.byIds(VideoFileDraft::class.java, ids.toList()).toAggregatePredicate(AggVideoFileDraft::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: PredicateBuilder<SVideoFileDraft>): AggregatePredicate<AggVideoFileDraft, VideoFileDraft> {
            return JpaPredicate.bySpecification(VideoFileDraft::class.java, specify(builder)).toAggregatePredicate(AggVideoFileDraft::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(
            builder: PredicateBuilder<SVideoFileDraft>,
            distinct: Boolean,
        ): AggregatePredicate<AggVideoFileDraft, VideoFileDraft> {
            return JpaPredicate.bySpecification(VideoFileDraft::class.java, specify(builder, distinct)).toAggregatePredicate(AggVideoFileDraft::class.java)
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
            builder: PredicateBuilder<SVideoFileDraft>,
            orderBuilders: List<OrderBuilder<SVideoFileDraft>>,
        ): AggregatePredicate<AggVideoFileDraft, VideoFileDraft> {
            return JpaPredicate.bySpecification(VideoFileDraft::class.java, specify(builder, false, orderBuilders)).toAggregatePredicate(AggVideoFileDraft::class.java)
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
            builder: PredicateBuilder<SVideoFileDraft>,
            vararg orderBuilders: OrderBuilder<SVideoFileDraft>,
        ): AggregatePredicate<AggVideoFileDraft, VideoFileDraft> {
            return JpaPredicate.bySpecification(VideoFileDraft::class.java, specify(builder, false, *orderBuilders)).toAggregatePredicate(AggVideoFileDraft::class.java)
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
            builder: PredicateBuilder<SVideoFileDraft>,
            distinct: Boolean,
            orderBuilders: List<OrderBuilder<SVideoFileDraft>>,
        ): AggregatePredicate<AggVideoFileDraft, VideoFileDraft> {
            return JpaPredicate.bySpecification(VideoFileDraft::class.java, specify(builder, distinct, orderBuilders)).toAggregatePredicate(AggVideoFileDraft::class.java)
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
            builder: PredicateBuilder<SVideoFileDraft>,
            distinct: Boolean,
            vararg orderBuilders: OrderBuilder<SVideoFileDraft>,
        ): AggregatePredicate<AggVideoFileDraft, VideoFileDraft> {
            return JpaPredicate.bySpecification(VideoFileDraft::class.java, specify(builder, distinct, *orderBuilders)).toAggregatePredicate(AggVideoFileDraft::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: SchemaSpecification<VideoFileDraft, SVideoFileDraft>): AggregatePredicate<AggVideoFileDraft, VideoFileDraft> {
            return JpaPredicate.bySpecification(VideoFileDraft::class.java, specify(specifier)).toAggregatePredicate(AggVideoFileDraft::class.java)
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
            filterBuilder: java.util.function.Function<QVideoFileDraft, com.querydsl.core.types.Predicate>,
            vararg orderSpecifierBuilders: java.util.function.Function<QVideoFileDraft, OrderSpecifier<*>>,
        ): AggregatePredicate<AggVideoFileDraft, VideoFileDraft> {
            return QuerydslPredicate.of(VideoFileDraft::class.java)
                .where(filterBuilder.apply(QVideoFileDraft.videoFileDraft))
                .orderBy(*orderSpecifierBuilders.map { it.apply(QVideoFileDraft.videoFileDraft) }.toTypedArray())
                .toAggregatePredicate(AggVideoFileDraft::class.java)
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
        ): AggregatePredicate<AggVideoFileDraft, VideoFileDraft> {
            return QuerydslPredicate.of(VideoFileDraft::class.java)
                .where(filter)
                .orderBy(*orderSpecifiers)
                .toAggregatePredicate(AggVideoFileDraft::class.java)
        }
    }

    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<VideoFileDraft> = root


    /**
     * ID
     */
    val id: Field<Long> by lazy {
        Field(root.get("id"), criteriaBuilder)
    }


    /**
     * 唯一ID
     */
    val fileId: Field<Long> by lazy {
        Field(root.get("fileId"), criteriaBuilder)
    }


    /**
     * 上传ID
     */
    val uploadId: Field<Long> by lazy {
        Field(root.get("uploadId"), criteriaBuilder)
    }


    /**
     * 用户ID
     */
    val customerId: Field<Long> by lazy {
        Field(root.get("customerId"), criteriaBuilder)
    }


    /**
     * 视频ID
     */
    val videoId: Field<Long> by lazy {
        Field(root.get("videoId"), criteriaBuilder)
    }


    /**
     * 文件索引
     */
    val fileIndex: Field<Int> by lazy {
        Field(root.get("fileIndex"), criteriaBuilder)
    }


    /**
     * 文件名
     */
    val fileName: Field<String?> by lazy {
        Field(root.get("fileName"), criteriaBuilder)
    }


    /**
     * 文件大小
     */
    val fileSize: Field<Long?> by lazy {
        Field(root.get("fileSize"), criteriaBuilder)
    }


    /**
     * 文件路径
     */
    val filePath: Field<String?> by lazy {
        Field(root.get("filePath"), criteriaBuilder)
    }


    /**
     * 更新类型
     */
    val updateType: Field<UpdateType> by lazy {
        Field(root.get("updateType"), criteriaBuilder)
    }


    /**
     * 转码结果
     */
    val transferResult: Field<TransferResult> by lazy {
        Field(root.get("transferResult"), criteriaBuilder)
    }


    /**
     * 持续时间（秒）
     */
    val duration: Field<Int?> by lazy {
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
    val deleted: Field<Boolean> by lazy {
        Field(root.get("deleted"), criteriaBuilder)
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
    fun spec(builder: PredicateBuilder<SVideoFileDraft>): Predicate {
        return builder.build(this)
    }
}
