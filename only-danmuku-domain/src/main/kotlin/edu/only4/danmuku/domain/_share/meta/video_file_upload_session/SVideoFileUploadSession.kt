package edu.only4.danmuku.domain._share.meta.video_file_upload_session

import com.only4.cap4k.ddd.domain.repo.JpaPredicate

import edu.only4.danmuku.domain._share.meta.ExpressionBuilder
import edu.only4.danmuku.domain._share.meta.Field
import edu.only4.danmuku.domain._share.meta.OrderBuilder
import edu.only4.danmuku.domain._share.meta.PredicateBuilder
import edu.only4.danmuku.domain._share.meta.SchemaSpecification
import edu.only4.danmuku.domain._share.meta.SubqueryConfigure
import edu.only4.danmuku.domain.aggregates.video_file_upload_session.VideoFileUploadSession
import edu.only4.danmuku.domain.aggregates.video_file_upload_session.enums.UploadStatus

import jakarta.persistence.criteria.*

import org.springframework.data.jpa.domain.Specification

/**
 * 视频分片上传会话; 用于跟踪预上传与分片进度
 *
 * Schema 类用于类型安全的 JPA Criteria 查询
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/11/03
 */
class SVideoFileUploadSession(
    private val root: Path<VideoFileUploadSession>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {

        val id = "id"

        val customerId = "customerId"

        val fileName = "fileName"

        val chunks = "chunks"

        val chunkIndex = "chunkIndex"

        val fileSize = "fileSize"

        val tempPath = "tempPath"

        val status = "status"

        val duration = "duration"

        val createUserId = "createUserId"

        val createBy = "createBy"

        val createTime = "createTime"

        val updateUserId = "updateUserId"

        val updateBy = "updateBy"

        val updateTime = "updateTime"

        val expiresAt = "expiresAt"

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
        fun specify(builder: PredicateBuilder<SVideoFileUploadSession>): Specification<VideoFileUploadSession> {
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
        fun specify(builder: PredicateBuilder<SVideoFileUploadSession>, distinct: Boolean): Specification<VideoFileUploadSession> {
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
            builder: PredicateBuilder<SVideoFileUploadSession>,
            vararg orderBuilders: OrderBuilder<SVideoFileUploadSession>,
        ): Specification<VideoFileUploadSession> {
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
            builder: PredicateBuilder<SVideoFileUploadSession>,
            orderBuilders: List<OrderBuilder<SVideoFileUploadSession>>,
        ): Specification<VideoFileUploadSession> {
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
            builder: PredicateBuilder<SVideoFileUploadSession>,
            distinct: Boolean,
            vararg orderBuilders: OrderBuilder<SVideoFileUploadSession>,
        ): Specification<VideoFileUploadSession> {
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
            builder: PredicateBuilder<SVideoFileUploadSession>,
            distinct: Boolean,
            orderBuilders: List<OrderBuilder<SVideoFileUploadSession>>,
        ): Specification<VideoFileUploadSession> {
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
        fun specify(specifier: SchemaSpecification<VideoFileUploadSession, SVideoFileUploadSession>): Specification<VideoFileUploadSession> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SVideoFileUploadSession(root, criteriaBuilder)
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
            selectBuilder: ExpressionBuilder<SVideoFileUploadSession, E>,
            predicateBuilder: PredicateBuilder<SVideoFileUploadSession>,
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
            subqueryConfigure: SubqueryConfigure<E, SVideoFileUploadSession>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(VideoFileUploadSession::class.java)
            val schema = SVideoFileUploadSession(root, criteriaBuilder)
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
        fun predicateById(id: Any): JpaPredicate<VideoFileUploadSession> {
            return JpaPredicate.byId(VideoFileUploadSession::class.java, id)
        }

        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): JpaPredicate<VideoFileUploadSession> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(VideoFileUploadSession::class.java, ids as Iterable<Any>)
        }

        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): JpaPredicate<VideoFileUploadSession> {
            return JpaPredicate.byIds(VideoFileUploadSession::class.java, ids.toList())
        }

        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: PredicateBuilder<SVideoFileUploadSession>): JpaPredicate<VideoFileUploadSession> {
            return JpaPredicate.bySpecification(VideoFileUploadSession::class.java, specify(builder))
        }

        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: PredicateBuilder<SVideoFileUploadSession>, distinct: Boolean): JpaPredicate<VideoFileUploadSession> {
            return JpaPredicate.bySpecification(VideoFileUploadSession::class.java, specify(builder, distinct))
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
            builder: PredicateBuilder<SVideoFileUploadSession>,
            orderBuilders: List<OrderBuilder<SVideoFileUploadSession>>,
        ): JpaPredicate<VideoFileUploadSession> {
            return JpaPredicate.bySpecification(VideoFileUploadSession::class.java, specify(builder, false, orderBuilders))
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
            builder: PredicateBuilder<SVideoFileUploadSession>,
            vararg orderBuilders: OrderBuilder<SVideoFileUploadSession>,
        ): JpaPredicate<VideoFileUploadSession> {
            return JpaPredicate.bySpecification(VideoFileUploadSession::class.java, specify(builder, false, *orderBuilders))
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
            builder: PredicateBuilder<SVideoFileUploadSession>,
            distinct: Boolean,
            orderBuilders: List<OrderBuilder<SVideoFileUploadSession>>,
        ): JpaPredicate<VideoFileUploadSession> {
            return JpaPredicate.bySpecification(VideoFileUploadSession::class.java, specify(builder, distinct, orderBuilders))
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
            builder: PredicateBuilder<SVideoFileUploadSession>,
            distinct: Boolean,
            vararg orderBuilders: OrderBuilder<SVideoFileUploadSession>,
        ): JpaPredicate<VideoFileUploadSession> {
            return JpaPredicate.bySpecification(VideoFileUploadSession::class.java, specify(builder, distinct, *orderBuilders))
        }

        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: SchemaSpecification<VideoFileUploadSession, SVideoFileUploadSession>): JpaPredicate<VideoFileUploadSession> {
            return JpaPredicate.bySpecification(VideoFileUploadSession::class.java, specify(specifier))
        }

    }

    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<VideoFileUploadSession> = root


    /**
     * ID
     */
    val id: Field<Long> by lazy {
        Field(root.get("id"), criteriaBuilder)
    }


    /**
     * 用户ID
     */
    val customerId: Field<Long> by lazy {
        Field(root.get("customerId"), criteriaBuilder)
    }


    /**
     * 文件名
     */
    val fileName: Field<String> by lazy {
        Field(root.get("fileName"), criteriaBuilder)
    }


    /**
     * 分片总数
     */
    val chunks: Field<Int> by lazy {
        Field(root.get("chunks"), criteriaBuilder)
    }


    /**
     * 当前已上传的最大分片索引
     */
    val chunkIndex: Field<Int> by lazy {
        Field(root.get("chunkIndex"), criteriaBuilder)
    }


    /**
     * 累计已上传大小（字节）
     */
    val fileSize: Field<Long?> by lazy {
        Field(root.get("fileSize"), criteriaBuilder)
    }


    /**
     * 临时目录（绝对或相对路径）
     */
    val tempPath: Field<String?> by lazy {
        Field(root.get("tempPath"), criteriaBuilder)
    }


    /**
     * 状态
     */
    val status: Field<UploadStatus> by lazy {
        Field(root.get("status"), criteriaBuilder)
    }


    /**
     * 视频时长（秒，可选）
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
     * 过期时间（秒时间戳）
     */
    val expiresAt: Field<Long?> by lazy {
        Field(root.get("expiresAt"), criteriaBuilder)
    }


    /**
     * 删除标识 0：未删除 id：已删除
     */
    val deleted: Field<Long> by lazy {
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
    fun spec(builder: PredicateBuilder<SVideoFileUploadSession>): Predicate {
        return builder.build(this)
    }
}
