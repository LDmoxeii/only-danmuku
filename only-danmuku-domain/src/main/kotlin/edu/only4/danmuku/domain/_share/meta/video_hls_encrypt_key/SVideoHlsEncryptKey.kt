package edu.only4.danmuku.domain._share.meta.video_hls_encrypt_key

import com.only4.cap4k.ddd.domain.repo.JpaPredicate

import edu.only4.danmuku.domain._share.meta.ExpressionBuilder
import edu.only4.danmuku.domain._share.meta.Field
import edu.only4.danmuku.domain._share.meta.OrderBuilder
import edu.only4.danmuku.domain._share.meta.PredicateBuilder
import edu.only4.danmuku.domain._share.meta.SchemaSpecification
import edu.only4.danmuku.domain._share.meta.SubqueryConfigure
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.VideoHlsEncryptKey
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.enums.EncryptKeyStatus

import jakarta.persistence.criteria.*

import org.springframework.data.jpa.domain.Specification

/**
 * Schema 类用于类型安全的 JPA Criteria 查询
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 */
class SVideoHlsEncryptKey(
    private val root: Path<VideoHlsEncryptKey>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {

        val id = "id"

        val fileId = "fileId"

        val quality = "quality"

        val keyId = "keyId"

        val keyCiphertext = "keyCiphertext"

        val ivHex = "ivHex"

        val keyVersion = "keyVersion"

        val method = "method"

        val keyUriTemplate = "keyUriTemplate"

        val expireTime = "expireTime"

        val status = "status"

        val remark = "remark"

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
        fun specify(builder: PredicateBuilder<SVideoHlsEncryptKey>): Specification<VideoHlsEncryptKey> {
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
        fun specify(builder: PredicateBuilder<SVideoHlsEncryptKey>, distinct: Boolean): Specification<VideoHlsEncryptKey> {
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
            builder: PredicateBuilder<SVideoHlsEncryptKey>,
            vararg orderBuilders: OrderBuilder<SVideoHlsEncryptKey>,
        ): Specification<VideoHlsEncryptKey> {
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
            builder: PredicateBuilder<SVideoHlsEncryptKey>,
            orderBuilders: List<OrderBuilder<SVideoHlsEncryptKey>>,
        ): Specification<VideoHlsEncryptKey> {
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
            builder: PredicateBuilder<SVideoHlsEncryptKey>,
            distinct: Boolean,
            vararg orderBuilders: OrderBuilder<SVideoHlsEncryptKey>,
        ): Specification<VideoHlsEncryptKey> {
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
            builder: PredicateBuilder<SVideoHlsEncryptKey>,
            distinct: Boolean,
            orderBuilders: List<OrderBuilder<SVideoHlsEncryptKey>>,
        ): Specification<VideoHlsEncryptKey> {
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
        fun specify(specifier: SchemaSpecification<VideoHlsEncryptKey, SVideoHlsEncryptKey>): Specification<VideoHlsEncryptKey> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SVideoHlsEncryptKey(root, criteriaBuilder)
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
            selectBuilder: ExpressionBuilder<SVideoHlsEncryptKey, E>,
            predicateBuilder: PredicateBuilder<SVideoHlsEncryptKey>,
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
            subqueryConfigure: SubqueryConfigure<E, SVideoHlsEncryptKey>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(VideoHlsEncryptKey::class.java)
            val schema = SVideoHlsEncryptKey(root, criteriaBuilder)
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
        fun predicateById(id: Any): JpaPredicate<VideoHlsEncryptKey> {
            return JpaPredicate.byId(VideoHlsEncryptKey::class.java, id)
        }

        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): JpaPredicate<VideoHlsEncryptKey> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(VideoHlsEncryptKey::class.java, ids as Iterable<Any>)
        }

        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): JpaPredicate<VideoHlsEncryptKey> {
            return JpaPredicate.byIds(VideoHlsEncryptKey::class.java, ids.toList())
        }

        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: PredicateBuilder<SVideoHlsEncryptKey>): JpaPredicate<VideoHlsEncryptKey> {
            return JpaPredicate.bySpecification(VideoHlsEncryptKey::class.java, specify(builder))
        }

        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: PredicateBuilder<SVideoHlsEncryptKey>, distinct: Boolean): JpaPredicate<VideoHlsEncryptKey> {
            return JpaPredicate.bySpecification(VideoHlsEncryptKey::class.java, specify(builder, distinct))
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
            builder: PredicateBuilder<SVideoHlsEncryptKey>,
            orderBuilders: List<OrderBuilder<SVideoHlsEncryptKey>>,
        ): JpaPredicate<VideoHlsEncryptKey> {
            return JpaPredicate.bySpecification(VideoHlsEncryptKey::class.java, specify(builder, false, orderBuilders))
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
            builder: PredicateBuilder<SVideoHlsEncryptKey>,
            vararg orderBuilders: OrderBuilder<SVideoHlsEncryptKey>,
        ): JpaPredicate<VideoHlsEncryptKey> {
            return JpaPredicate.bySpecification(VideoHlsEncryptKey::class.java, specify(builder, false, *orderBuilders))
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
            builder: PredicateBuilder<SVideoHlsEncryptKey>,
            distinct: Boolean,
            orderBuilders: List<OrderBuilder<SVideoHlsEncryptKey>>,
        ): JpaPredicate<VideoHlsEncryptKey> {
            return JpaPredicate.bySpecification(VideoHlsEncryptKey::class.java, specify(builder, distinct, orderBuilders))
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
            builder: PredicateBuilder<SVideoHlsEncryptKey>,
            distinct: Boolean,
            vararg orderBuilders: OrderBuilder<SVideoHlsEncryptKey>,
        ): JpaPredicate<VideoHlsEncryptKey> {
            return JpaPredicate.bySpecification(VideoHlsEncryptKey::class.java, specify(builder, distinct, *orderBuilders))
        }

        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: SchemaSpecification<VideoHlsEncryptKey, SVideoHlsEncryptKey>): JpaPredicate<VideoHlsEncryptKey> {
            return JpaPredicate.bySpecification(VideoHlsEncryptKey::class.java, specify(specifier))
        }

    }

    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<VideoHlsEncryptKey> = root


    /**
     * ID
     */
    val id: Field<Long> by lazy {
        Field(root.get("id"), criteriaBuilder)
    }


    /**
     * 稿件态 fileId
     */
    val fileId: Field<Long> by lazy {
        Field(root.get("fileId"), criteriaBuilder)
    }


    /**
     * 绑定清晰度，空表示全局通用
     */
    val quality: Field<String?> by lazy {
        Field(root.get("quality"), criteriaBuilder)
    }


    /**
     * Key ID（m3u8 暴露）
     */
    val keyId: Field<String> by lazy {
        Field(root.get("keyId"), criteriaBuilder)
    }


    /**
     * 密钥密文（KMS 加密后 Base64）
     */
    val keyCiphertext: Field<String> by lazy {
        Field(root.get("keyCiphertext"), criteriaBuilder)
    }


    /**
     * IV hex（16字节，可空）
     */
    val ivHex: Field<String?> by lazy {
        Field(root.get("ivHex"), criteriaBuilder)
    }


    /**
     * 密钥版本号（轮换递增）
     */
    val keyVersion: Field<Int> by lazy {
        Field(root.get("keyVersion"), criteriaBuilder)
    }


    /**
     * 加密方式
     */
    val method: Field<String> by lazy {
        Field(root.get("method"), criteriaBuilder)
    }


    /**
     * m3u8 中的 URI 模板，含 token 占位
     */
    val keyUriTemplate: Field<String> by lazy {
        Field(root.get("keyUriTemplate"), criteriaBuilder)
    }


    /**
     * 过期时间（ms）
     */
    val expireTime: Field<Long?> by lazy {
        Field(root.get("expireTime"), criteriaBuilder)
    }


    /**
     * 状态
     */
    val status: Field<EncryptKeyStatus> by lazy {
        Field(root.get("status"), criteriaBuilder)
    }


    /**
     * 备注/轮换原因
     */
    val remark: Field<String?> by lazy {
        Field(root.get("remark"), criteriaBuilder)
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
    fun spec(builder: PredicateBuilder<SVideoHlsEncryptKey>): Predicate {
        return builder.build(this)
    }
}
