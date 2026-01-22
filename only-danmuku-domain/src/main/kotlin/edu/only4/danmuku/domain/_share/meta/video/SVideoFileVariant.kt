package edu.only4.danmuku.domain._share.meta.video

import edu.only4.danmuku.domain._share.meta.ExpressionBuilder
import edu.only4.danmuku.domain._share.meta.Field
import edu.only4.danmuku.domain._share.meta.OrderBuilder
import edu.only4.danmuku.domain._share.meta.PredicateBuilder
import edu.only4.danmuku.domain._share.meta.SchemaSpecification
import edu.only4.danmuku.domain._share.meta.SubqueryConfigure
import edu.only4.danmuku.domain.aggregates.video.VideoFileVariant
import jakarta.persistence.criteria.*
import org.springframework.data.jpa.domain.Specification

/**
 * Schema 类用于类型安全的 JPA Criteria 查询
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 */
class SVideoFileVariant(
    private val root: Path<VideoFileVariant>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {

        val id = "id"

        val fileId = "fileId"

        val quality = "quality"

        val width = "width"

        val height = "height"

        val videoBitrateKbps = "videoBitrateKbps"

        val audioBitrateKbps = "audioBitrateKbps"

        val bandwidthBps = "bandwidthBps"

        val playlistPath = "playlistPath"

        val segmentPrefix = "segmentPrefix"

        val segmentDuration = "segmentDuration"

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
        fun specify(builder: PredicateBuilder<SVideoFileVariant>): Specification<VideoFileVariant> {
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
        fun specify(builder: PredicateBuilder<SVideoFileVariant>, distinct: Boolean): Specification<VideoFileVariant> {
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
            builder: PredicateBuilder<SVideoFileVariant>,
            vararg orderBuilders: OrderBuilder<SVideoFileVariant>,
        ): Specification<VideoFileVariant> {
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
            builder: PredicateBuilder<SVideoFileVariant>,
            orderBuilders: List<OrderBuilder<SVideoFileVariant>>,
        ): Specification<VideoFileVariant> {
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
            builder: PredicateBuilder<SVideoFileVariant>,
            distinct: Boolean,
            vararg orderBuilders: OrderBuilder<SVideoFileVariant>,
        ): Specification<VideoFileVariant> {
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
            builder: PredicateBuilder<SVideoFileVariant>,
            distinct: Boolean,
            orderBuilders: List<OrderBuilder<SVideoFileVariant>>,
        ): Specification<VideoFileVariant> {
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
        fun specify(specifier: SchemaSpecification<VideoFileVariant, SVideoFileVariant>): Specification<VideoFileVariant> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SVideoFileVariant(root, criteriaBuilder)
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
            selectBuilder: ExpressionBuilder<SVideoFileVariant, E>,
            predicateBuilder: PredicateBuilder<SVideoFileVariant>,
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
            subqueryConfigure: SubqueryConfigure<E, SVideoFileVariant>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(VideoFileVariant::class.java)
            val schema = SVideoFileVariant(root, criteriaBuilder)
            subqueryConfigure.configure(sq, schema)
            return sq
        }
    }

    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<VideoFileVariant> = root


    /**
     * ID
     */
    val id: Field<Long> by lazy {
        Field(root.get("id"), criteriaBuilder)
    }


    /**
     * 视频文件ID
     */
    val fileId: Field<Long> by lazy {
        Field(root.get("fileId"), criteriaBuilder)
    }


    /**
     * 清晰度档位，如 1080p/720p
     */
    val quality: Field<String> by lazy {
        Field(root.get("quality"), criteriaBuilder)
    }


    /**
     * 输出宽度(px)
     */
    val width: Field<Int> by lazy {
        Field(root.get("width"), criteriaBuilder)
    }


    /**
     * 输出高度(px)
     */
    val height: Field<Int> by lazy {
        Field(root.get("height"), criteriaBuilder)
    }


    /**
     * 视频码率(kbps)
     */
    val videoBitrateKbps: Field<Int> by lazy {
        Field(root.get("videoBitrateKbps"), criteriaBuilder)
    }


    /**
     * 音频码率(kbps)
     */
    val audioBitrateKbps: Field<Int> by lazy {
        Field(root.get("audioBitrateKbps"), criteriaBuilder)
    }


    /**
     * Master 中的 BANDWIDTH（bps）
     */
    val bandwidthBps: Field<Int> by lazy {
        Field(root.get("bandwidthBps"), criteriaBuilder)
    }


    /**
     * 子清晰度 m3u8 路径，如 720p/index.m3u8
     */
    val playlistPath: Field<String> by lazy {
        Field(root.get("playlistPath"), criteriaBuilder)
    }


    /**
     * 切片目录前缀，如 720p/
     */
    val segmentPrefix: Field<String?> by lazy {
        Field(root.get("segmentPrefix"), criteriaBuilder)
    }


    /**
     * 切片目标时长（秒）
     */
    val segmentDuration: Field<Int?> by lazy {
        Field(root.get("segmentDuration"), criteriaBuilder)
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
     * 关联: ManyToOne - VideoFile
     */
    val videoFile: Field<Any> by lazy {
        Field(root.get("videoFile"), criteriaBuilder)
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
    fun spec(builder: PredicateBuilder<SVideoFileVariant>): Predicate {
        return builder.build(this)
    }
}
