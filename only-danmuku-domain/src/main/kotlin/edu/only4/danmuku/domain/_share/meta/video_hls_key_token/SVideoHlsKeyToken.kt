package edu.only4.danmuku.domain._share.meta.video_hls_key_token

import com.only4.cap4k.ddd.domain.repo.JpaPredicate

import edu.only4.danmuku.domain._share.meta.ExpressionBuilder
import edu.only4.danmuku.domain._share.meta.Field
import edu.only4.danmuku.domain._share.meta.OrderBuilder
import edu.only4.danmuku.domain._share.meta.PredicateBuilder
import edu.only4.danmuku.domain._share.meta.SchemaSpecification
import edu.only4.danmuku.domain._share.meta.SubqueryConfigure
import edu.only4.danmuku.domain.aggregates.video_hls_key_token.VideoHlsKeyToken
import edu.only4.danmuku.domain.aggregates.video_hls_key_token.enums.EncryptTokenStatus

import jakarta.persistence.criteria.*

import org.springframework.data.jpa.domain.Specification

/**
 * Schema 类用于类型安全的 JPA Criteria 查询
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 */
class SVideoHlsKeyToken(
    private val root: Path<VideoHlsKeyToken>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {

        val id = "id"

        val videoPostId = "videoPostId"

        val videoId = "videoId"

        val fileIndex = "fileIndex"

        val keyVersion = "keyVersion"

        val allowedQualities = "allowedQualities"

        val tokenHash = "tokenHash"

        val audience = "audience"

        val expireTime = "expireTime"

        val maxUse = "maxUse"

        val usedCount = "usedCount"

        val status = "status"

        val issueIp = "issueIp"

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
        fun specify(builder: PredicateBuilder<SVideoHlsKeyToken>): Specification<VideoHlsKeyToken> {
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
        fun specify(builder: PredicateBuilder<SVideoHlsKeyToken>, distinct: Boolean): Specification<VideoHlsKeyToken> {
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
            builder: PredicateBuilder<SVideoHlsKeyToken>,
            vararg orderBuilders: OrderBuilder<SVideoHlsKeyToken>,
        ): Specification<VideoHlsKeyToken> {
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
            builder: PredicateBuilder<SVideoHlsKeyToken>,
            orderBuilders: List<OrderBuilder<SVideoHlsKeyToken>>,
        ): Specification<VideoHlsKeyToken> {
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
            builder: PredicateBuilder<SVideoHlsKeyToken>,
            distinct: Boolean,
            vararg orderBuilders: OrderBuilder<SVideoHlsKeyToken>,
        ): Specification<VideoHlsKeyToken> {
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
            builder: PredicateBuilder<SVideoHlsKeyToken>,
            distinct: Boolean,
            orderBuilders: List<OrderBuilder<SVideoHlsKeyToken>>,
        ): Specification<VideoHlsKeyToken> {
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
        fun specify(specifier: SchemaSpecification<VideoHlsKeyToken, SVideoHlsKeyToken>): Specification<VideoHlsKeyToken> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SVideoHlsKeyToken(root, criteriaBuilder)
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
            selectBuilder: ExpressionBuilder<SVideoHlsKeyToken, E>,
            predicateBuilder: PredicateBuilder<SVideoHlsKeyToken>,
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
            subqueryConfigure: SubqueryConfigure<E, SVideoHlsKeyToken>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(VideoHlsKeyToken::class.java)
            val schema = SVideoHlsKeyToken(root, criteriaBuilder)
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
        fun predicateById(id: Any): JpaPredicate<VideoHlsKeyToken> {
            return JpaPredicate.byId(VideoHlsKeyToken::class.java, id)
        }

        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): JpaPredicate<VideoHlsKeyToken> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(VideoHlsKeyToken::class.java, ids as Iterable<Any>)
        }

        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): JpaPredicate<VideoHlsKeyToken> {
            return JpaPredicate.byIds(VideoHlsKeyToken::class.java, ids.toList())
        }

        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: PredicateBuilder<SVideoHlsKeyToken>): JpaPredicate<VideoHlsKeyToken> {
            return JpaPredicate.bySpecification(VideoHlsKeyToken::class.java, specify(builder))
        }

        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: PredicateBuilder<SVideoHlsKeyToken>, distinct: Boolean): JpaPredicate<VideoHlsKeyToken> {
            return JpaPredicate.bySpecification(VideoHlsKeyToken::class.java, specify(builder, distinct))
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
            builder: PredicateBuilder<SVideoHlsKeyToken>,
            orderBuilders: List<OrderBuilder<SVideoHlsKeyToken>>,
        ): JpaPredicate<VideoHlsKeyToken> {
            return JpaPredicate.bySpecification(VideoHlsKeyToken::class.java, specify(builder, false, orderBuilders))
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
            builder: PredicateBuilder<SVideoHlsKeyToken>,
            vararg orderBuilders: OrderBuilder<SVideoHlsKeyToken>,
        ): JpaPredicate<VideoHlsKeyToken> {
            return JpaPredicate.bySpecification(VideoHlsKeyToken::class.java, specify(builder, false, *orderBuilders))
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
            builder: PredicateBuilder<SVideoHlsKeyToken>,
            distinct: Boolean,
            orderBuilders: List<OrderBuilder<SVideoHlsKeyToken>>,
        ): JpaPredicate<VideoHlsKeyToken> {
            return JpaPredicate.bySpecification(VideoHlsKeyToken::class.java, specify(builder, distinct, orderBuilders))
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
            builder: PredicateBuilder<SVideoHlsKeyToken>,
            distinct: Boolean,
            vararg orderBuilders: OrderBuilder<SVideoHlsKeyToken>,
        ): JpaPredicate<VideoHlsKeyToken> {
            return JpaPredicate.bySpecification(VideoHlsKeyToken::class.java, specify(builder, distinct, *orderBuilders))
        }

        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: SchemaSpecification<VideoHlsKeyToken, SVideoHlsKeyToken>): JpaPredicate<VideoHlsKeyToken> {
            return JpaPredicate.bySpecification(VideoHlsKeyToken::class.java, specify(specifier))
        }

    }

    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<VideoHlsKeyToken> = root


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
     * 视频ID
     */
    val videoId: Field<Long?> by lazy {
        Field(root.get("videoId"), criteriaBuilder)
    }


    /**
     * 文件索引
     */
    val fileIndex: Field<Int> by lazy {
        Field(root.get("fileIndex"), criteriaBuilder)
    }


    /**
     * 密钥版本
     */
    val keyVersion: Field<Int> by lazy {
        Field(root.get("keyVersion"), criteriaBuilder)
    }


    /**
     * 授权清晰度列表 JSON，空表示全量
     */
    val allowedQualities: Field<String?> by lazy {
        Field(root.get("allowedQualities"), criteriaBuilder)
    }


    /**
     * token 哈希（sha256）
     */
    val tokenHash: Field<String> by lazy {
        Field(root.get("tokenHash"), criteriaBuilder)
    }


    /**
     * 受众标识（用户/终端）
     */
    val audience: Field<String?> by lazy {
        Field(root.get("audience"), criteriaBuilder)
    }


    /**
     * 过期时间（ms）
     */
    val expireTime: Field<Long> by lazy {
        Field(root.get("expireTime"), criteriaBuilder)
    }


    /**
     * 最大可用次数
     */
    val maxUse: Field<Int> by lazy {
        Field(root.get("maxUse"), criteriaBuilder)
    }


    /**
     * 已使用次数
     */
    val usedCount: Field<Int> by lazy {
        Field(root.get("usedCount"), criteriaBuilder)
    }


    /**
     * 状态
     */
    val status: Field<EncryptTokenStatus> by lazy {
        Field(root.get("status"), criteriaBuilder)
    }


    /**
     * 签发 IP
     */
    val issueIp: Field<String?> by lazy {
        Field(root.get("issueIp"), criteriaBuilder)
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
    fun spec(builder: PredicateBuilder<SVideoHlsKeyToken>): Predicate {
        return builder.build(this)
    }
}
