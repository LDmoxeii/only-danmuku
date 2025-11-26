package edu.only4.danmuku.domain._share.meta.video_file_post

import com.only4.cap4k.ddd.domain.repo.JpaPredicate

import edu.only4.danmuku.domain._share.meta.ExpressionBuilder
import edu.only4.danmuku.domain._share.meta.Field
import edu.only4.danmuku.domain._share.meta.OrderBuilder
import edu.only4.danmuku.domain._share.meta.PredicateBuilder
import edu.only4.danmuku.domain._share.meta.SchemaSpecification
import edu.only4.danmuku.domain._share.meta.SubqueryConfigure
import edu.only4.danmuku.domain.aggregates.video_file_post.VideoFilePost
import edu.only4.danmuku.domain.aggregates.video_file_post.enums.EncryptMethod
import edu.only4.danmuku.domain.aggregates.video_file_post.enums.EncryptStatus
import edu.only4.danmuku.domain.aggregates.video_file_post.enums.TransferResult
import edu.only4.danmuku.domain.aggregates.video_file_post.enums.UpdateType

import jakarta.persistence.criteria.*

import org.springframework.data.jpa.domain.Specification

/**
 * Schema 类用于类型安全的 JPA Criteria 查询
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 */
class SVideoFilePost(
    private val root: Path<VideoFilePost>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {

        val id = "id"

        val uploadId = "uploadId"

        val customerId = "customerId"

        val videoId = "videoId"

        val fileIndex = "fileIndex"

        val fileName = "fileName"

        val fileSize = "fileSize"

        val filePath = "filePath"

        val updateType = "updateType"

        val transferResult = "transferResult"

        val abrSourceWidth = "abrSourceWidth"

        val abrSourceHeight = "abrSourceHeight"

        val abrSourceBitrateKbps = "abrSourceBitrateKbps"

        val encryptStatus = "encryptStatus"

        val encryptMethod = "encryptMethod"

        val encryptKeyId = "encryptKeyId"

        val encryptFailReason = "encryptFailReason"

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
        fun specify(builder: PredicateBuilder<SVideoFilePost>): Specification<VideoFilePost> {
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
        fun specify(builder: PredicateBuilder<SVideoFilePost>, distinct: Boolean): Specification<VideoFilePost> {
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
            builder: PredicateBuilder<SVideoFilePost>,
            vararg orderBuilders: OrderBuilder<SVideoFilePost>,
        ): Specification<VideoFilePost> {
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
            builder: PredicateBuilder<SVideoFilePost>,
            orderBuilders: List<OrderBuilder<SVideoFilePost>>,
        ): Specification<VideoFilePost> {
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
            builder: PredicateBuilder<SVideoFilePost>,
            distinct: Boolean,
            vararg orderBuilders: OrderBuilder<SVideoFilePost>,
        ): Specification<VideoFilePost> {
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
            builder: PredicateBuilder<SVideoFilePost>,
            distinct: Boolean,
            orderBuilders: List<OrderBuilder<SVideoFilePost>>,
        ): Specification<VideoFilePost> {
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
        fun specify(specifier: SchemaSpecification<VideoFilePost, SVideoFilePost>): Specification<VideoFilePost> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SVideoFilePost(root, criteriaBuilder)
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
            selectBuilder: ExpressionBuilder<SVideoFilePost, E>,
            predicateBuilder: PredicateBuilder<SVideoFilePost>,
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
            subqueryConfigure: SubqueryConfigure<E, SVideoFilePost>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(VideoFilePost::class.java)
            val schema = SVideoFilePost(root, criteriaBuilder)
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
        fun predicateById(id: Any): JpaPredicate<VideoFilePost> {
            return JpaPredicate.byId(VideoFilePost::class.java, id)
        }

        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): JpaPredicate<VideoFilePost> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(VideoFilePost::class.java, ids as Iterable<Any>)
        }

        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): JpaPredicate<VideoFilePost> {
            return JpaPredicate.byIds(VideoFilePost::class.java, ids.toList())
        }

        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: PredicateBuilder<SVideoFilePost>): JpaPredicate<VideoFilePost> {
            return JpaPredicate.bySpecification(VideoFilePost::class.java, specify(builder))
        }

        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: PredicateBuilder<SVideoFilePost>, distinct: Boolean): JpaPredicate<VideoFilePost> {
            return JpaPredicate.bySpecification(VideoFilePost::class.java, specify(builder, distinct))
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
            builder: PredicateBuilder<SVideoFilePost>,
            orderBuilders: List<OrderBuilder<SVideoFilePost>>,
        ): JpaPredicate<VideoFilePost> {
            return JpaPredicate.bySpecification(VideoFilePost::class.java, specify(builder, false, orderBuilders))
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
            builder: PredicateBuilder<SVideoFilePost>,
            vararg orderBuilders: OrderBuilder<SVideoFilePost>,
        ): JpaPredicate<VideoFilePost> {
            return JpaPredicate.bySpecification(VideoFilePost::class.java, specify(builder, false, *orderBuilders))
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
            builder: PredicateBuilder<SVideoFilePost>,
            distinct: Boolean,
            orderBuilders: List<OrderBuilder<SVideoFilePost>>,
        ): JpaPredicate<VideoFilePost> {
            return JpaPredicate.bySpecification(VideoFilePost::class.java, specify(builder, distinct, orderBuilders))
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
            builder: PredicateBuilder<SVideoFilePost>,
            distinct: Boolean,
            vararg orderBuilders: OrderBuilder<SVideoFilePost>,
        ): JpaPredicate<VideoFilePost> {
            return JpaPredicate.bySpecification(VideoFilePost::class.java, specify(builder, distinct, *orderBuilders))
        }

        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: SchemaSpecification<VideoFilePost, SVideoFilePost>): JpaPredicate<VideoFilePost> {
            return JpaPredicate.bySpecification(VideoFilePost::class.java, specify(specifier))
        }

    }

    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<VideoFilePost> = root


    /**
     * ID
     */
    val id: Field<Long> by lazy {
        Field(root.get("id"), criteriaBuilder)
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
     * ABR 源视频宽度(px)
     */
    val abrSourceWidth: Field<Int?> by lazy {
        Field(root.get("abrSourceWidth"), criteriaBuilder)
    }


    /**
     * ABR 源视频高度(px)
     */
    val abrSourceHeight: Field<Int?> by lazy {
        Field(root.get("abrSourceHeight"), criteriaBuilder)
    }


    /**
     * ABR 源视频码率(kbps)
     */
    val abrSourceBitrateKbps: Field<Int?> by lazy {
        Field(root.get("abrSourceBitrateKbps"), criteriaBuilder)
    }


    /**
     * 加密状态
     */
    val encryptStatus: Field<EncryptStatus> by lazy {
        Field(root.get("encryptStatus"), criteriaBuilder)
    }


    /**
     * 加密方式
     */
    val encryptMethod: Field<EncryptMethod> by lazy {
        Field(root.get("encryptMethod"), criteriaBuilder)
    }


    /**
     * 关联密钥ID
     */
    val encryptKeyId: Field<Long?> by lazy {
        Field(root.get("encryptKeyId"), criteriaBuilder)
    }


    /**
     * 加密失败原因
     */
    val encryptFailReason: Field<String?> by lazy {
        Field(root.get("encryptFailReason"), criteriaBuilder)
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
    val deleted: Field<Long> by lazy {
        Field(root.get("deleted"), criteriaBuilder)
    }



    /**
     * 关联: OneToMany - VideoHlsAbrVariant
     */
    val videoHlsAbrVariants: Field<Any> by lazy {
        Field(root.get("videoHlsAbrVariants"), criteriaBuilder)
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
    fun spec(builder: PredicateBuilder<SVideoFilePost>): Predicate {
        return builder.build(this)
    }
}
