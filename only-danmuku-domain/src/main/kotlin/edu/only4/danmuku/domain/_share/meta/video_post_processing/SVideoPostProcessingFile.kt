package edu.only4.danmuku.domain._share.meta.video_post_processing

import edu.only4.danmuku.domain._share.meta.ExpressionBuilder
import edu.only4.danmuku.domain._share.meta.Field
import edu.only4.danmuku.domain._share.meta.OrderBuilder
import edu.only4.danmuku.domain._share.meta.PredicateBuilder
import edu.only4.danmuku.domain._share.meta.SchemaSpecification
import edu.only4.danmuku.domain._share.meta.SubqueryConfigure
import edu.only4.danmuku.domain.aggregates.video_post.enums.EncryptMethod
import edu.only4.danmuku.domain.aggregates.video_post_processing.VideoPostProcessingFile
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
class SVideoPostProcessingFile(
    private val root: Path<VideoPostProcessingFile>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {

        val id = "id"

        val parentId = "parentId"

        val fileIndex = "fileIndex"

        val uploadId = "uploadId"

        val transcodeStatus = "transcodeStatus"

        val encryptStatus = "encryptStatus"

        val encryptMethod = "encryptMethod"

        val encryptKeyVersion = "encryptKeyVersion"

        val transcodeOutputPrefix = "transcodeOutputPrefix"

        val transcodeOutputPath = "transcodeOutputPath"

        val transcodeVariantsJson = "transcodeVariantsJson"

        val encryptOutputDir = "encryptOutputDir"

        val encryptOutputPrefix = "encryptOutputPrefix"

        val duration = "duration"

        val fileSize = "fileSize"

        val failReason = "failReason"

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
        fun specify(builder: PredicateBuilder<SVideoPostProcessingFile>): Specification<VideoPostProcessingFile> {
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
        fun specify(builder: PredicateBuilder<SVideoPostProcessingFile>, distinct: Boolean): Specification<VideoPostProcessingFile> {
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
            builder: PredicateBuilder<SVideoPostProcessingFile>,
            vararg orderBuilders: OrderBuilder<SVideoPostProcessingFile>,
        ): Specification<VideoPostProcessingFile> {
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
            builder: PredicateBuilder<SVideoPostProcessingFile>,
            orderBuilders: List<OrderBuilder<SVideoPostProcessingFile>>,
        ): Specification<VideoPostProcessingFile> {
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
            builder: PredicateBuilder<SVideoPostProcessingFile>,
            distinct: Boolean,
            vararg orderBuilders: OrderBuilder<SVideoPostProcessingFile>,
        ): Specification<VideoPostProcessingFile> {
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
            builder: PredicateBuilder<SVideoPostProcessingFile>,
            distinct: Boolean,
            orderBuilders: List<OrderBuilder<SVideoPostProcessingFile>>,
        ): Specification<VideoPostProcessingFile> {
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
        fun specify(specifier: SchemaSpecification<VideoPostProcessingFile, SVideoPostProcessingFile>): Specification<VideoPostProcessingFile> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SVideoPostProcessingFile(root, criteriaBuilder)
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
            selectBuilder: ExpressionBuilder<SVideoPostProcessingFile, E>,
            predicateBuilder: PredicateBuilder<SVideoPostProcessingFile>,
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
            subqueryConfigure: SubqueryConfigure<E, SVideoPostProcessingFile>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(VideoPostProcessingFile::class.java)
            val schema = SVideoPostProcessingFile(root, criteriaBuilder)
            subqueryConfigure.configure(sq, schema)
            return sq
        }
    }

    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<VideoPostProcessingFile> = root


    /**
     * ID
     */
    val id: Field<Long> by lazy {
        Field(root.get("id"), criteriaBuilder)
    }


    /**
     * 视频稿件处理ID
     */
    val parentId: Field<Long> by lazy {
        Field(root.get("parentId"), criteriaBuilder)
    }


    /**
     * 文件索引
     */
    val fileIndex: Field<Int> by lazy {
        Field(root.get("fileIndex"), criteriaBuilder)
    }


    /**
     * 上传会话ID
     */
    val uploadId: Field<Long> by lazy {
        Field(root.get("uploadId"), criteriaBuilder)
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
     * 加密方式
     */
    val encryptMethod: Field<EncryptMethod> by lazy {
        Field(root.get("encryptMethod"), criteriaBuilder)
    }


    /**
     * 加密密钥版本
     */
    val encryptKeyVersion: Field<Int?> by lazy {
        Field(root.get("encryptKeyVersion"), criteriaBuilder)
    }


    /**
     * 转码输出对象前缀
     */
    val transcodeOutputPrefix: Field<String?> by lazy {
        Field(root.get("transcodeOutputPrefix"), criteriaBuilder)
    }


    /**
     * 转码输出本地路径
     */
    val transcodeOutputPath: Field<String?> by lazy {
        Field(root.get("transcodeOutputPath"), criteriaBuilder)
    }


    /**
     * 转码清晰度结果 JSON
     */
    val transcodeVariantsJson: Field<String?> by lazy {
        Field(root.get("transcodeVariantsJson"), criteriaBuilder)
    }


    /**
     * 加密输出本地目录
     */
    val encryptOutputDir: Field<String?> by lazy {
        Field(root.get("encryptOutputDir"), criteriaBuilder)
    }


    /**
     * 加密输出对象前缀
     */
    val encryptOutputPrefix: Field<String?> by lazy {
        Field(root.get("encryptOutputPrefix"), criteriaBuilder)
    }


    /**
     * 时长（秒）
     */
    val duration: Field<Int?> by lazy {
        Field(root.get("duration"), criteriaBuilder)
    }


    /**
     * 文件大小
     */
    val fileSize: Field<Long?> by lazy {
        Field(root.get("fileSize"), criteriaBuilder)
    }


    /**
     * 失败原因
     */
    val failReason: Field<String?> by lazy {
        Field(root.get("failReason"), criteriaBuilder)
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
     * 关联: ManyToOne - VideoPostProcessing
     */
    val videoPostProcessing: Field<Any> by lazy {
        Field(root.get("videoPostProcessing"), criteriaBuilder)
    }


    /**
     * 关联: OneToMany - VideoPostProcessingVariant
     */
    val videoPostProcessingVariants: Field<Any> by lazy {
        Field(root.get("videoPostProcessingVariants"), criteriaBuilder)
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
    fun spec(builder: PredicateBuilder<SVideoPostProcessingFile>): Predicate {
        return builder.build(this)
    }
}
