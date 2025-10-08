package com.edu.only4.danmuku.domain._share.meta.video_file_draft.meta

import com.querydsl.core.types.OrderSpecifier
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import com.only4.cap4k.ddd.domain.repo.querydsl.QuerydslPredicate
import com.edu.only4.danmuku.domain._share.meta.Schema
import com.edu.only4.danmuku.domain.aggregates.video_file_draft.AggVideoFileDraft
import com.edu.only4.danmuku.domain.aggregates.video_file_draft.VideoFileDraft
import com.edu.only4.danmuku.domain.aggregates.video_file_draft.QVideoFileDraft
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePredicate
import org.springframework.data.jpa.domain.Specification

import jakarta.persistence.criteria.*

/**
 * 视频文件信息;
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/10/08
 */
class SVideoFileDraft(
    private val root: Path<VideoFileDraft>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {
            
        /**
         * ID
         */
        val id = "id"
           
        /**
         * 唯一ID
         */
        val fileId = "fileId"
           
        /**
         * 上传ID
         */
        val uploadId = "uploadId"
           
        /**
         * 用户ID
         */
        val customerId = "customerId"
           
        /**
         * 视频ID
         */
        val videoId = "videoId"
           
        /**
         * 文件索引
         */
        val fileIndex = "fileIndex"
           
        /**
         * 文件名
         */
        val fileName = "fileName"
           
        /**
         * 文件大小
         */
        val fileSize = "fileSize"
           
        /**
         * 文件路径
         */
        val filePath = "filePath"
           
        /**
         * 更新类型
         */
        val updateType = "updateType"
           
        /**
         * 转码结果
         */
        val transferResult = "transferResult"
           
        /**
         * 持续时间（秒）
         */
        val duration = "duration"
           
        /**
         * 删除标识 0：未删除 id：已删除
         */
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
        fun specify(builder: Schema.PredicateBuilder<SVideoFileDraft>): Specification<VideoFileDraft> {
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
        fun specify(builder: Schema.PredicateBuilder<SVideoFileDraft>, distinct: Boolean): Specification<VideoFileDraft> {
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
            builder: Schema.PredicateBuilder<SVideoFileDraft>,
            vararg orderBuilders: Schema.OrderBuilder<SVideoFileDraft>,
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
            builder: Schema.PredicateBuilder<SVideoFileDraft>,
            orderBuilders: List<Schema.OrderBuilder<SVideoFileDraft>>,
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
            builder: Schema.PredicateBuilder<SVideoFileDraft>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SVideoFileDraft>,
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
            builder: Schema.PredicateBuilder<SVideoFileDraft>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SVideoFileDraft>>,
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
        fun specify(specifier: Schema.Specification<VideoFileDraft, SVideoFileDraft>): Specification<VideoFileDraft> {
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
            selectBuilder: Schema.ExpressionBuilder<SVideoFileDraft, E>,
            predicateBuilder: Schema.PredicateBuilder<SVideoFileDraft>,
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
            subqueryConfigure: Schema.SubqueryConfigure<E, SVideoFileDraft>,
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
        fun predicate(builder: Schema.PredicateBuilder<SVideoFileDraft>): AggregatePredicate<AggVideoFileDraft, VideoFileDraft> {
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
        fun predicate(builder: Schema.PredicateBuilder<SVideoFileDraft>, distinct: Boolean): AggregatePredicate<AggVideoFileDraft, VideoFileDraft> {
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
            builder: Schema.PredicateBuilder<SVideoFileDraft>,
            orderBuilders: List<Schema.OrderBuilder<SVideoFileDraft>>,
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
            builder: Schema.PredicateBuilder<SVideoFileDraft>,
            vararg orderBuilders: Schema.OrderBuilder<SVideoFileDraft>,
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
            builder: Schema.PredicateBuilder<SVideoFileDraft>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SVideoFileDraft>>,
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
            builder: Schema.PredicateBuilder<SVideoFileDraft>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SVideoFileDraft>,
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
        fun predicate(specifier: Schema.Specification<VideoFileDraft, SVideoFileDraft>): AggregatePredicate<AggVideoFileDraft, VideoFileDraft> {
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
     * bigint
     */
    fun id(): Schema.Field<Long> {
        return Schema.Field(root.get(props.id), this.criteriaBuilder)
    }
    /**
     * 唯一ID
     * bigint
     */
    fun fileId(): Schema.Field<Long> {
        return Schema.Field(root.get(props.fileId), this.criteriaBuilder)
    }
    /**
     * 上传ID
     * bigint
     */
    fun uploadId(): Schema.Field<Long> {
        return Schema.Field(root.get(props.uploadId), this.criteriaBuilder)
    }
    /**
     * 用户ID
     * bigint
     */
    fun customerId(): Schema.Field<Long> {
        return Schema.Field(root.get(props.customerId), this.criteriaBuilder)
    }
    /**
     * 视频ID
     * bigint
     */
    fun videoId(): Schema.Field<Long> {
        return Schema.Field(root.get(props.videoId), this.criteriaBuilder)
    }
    /**
     * 文件索引
     * int
     */
    fun fileIndex(): Schema.Field<Int> {
        return Schema.Field(root.get(props.fileIndex), this.criteriaBuilder)
    }
    /**
     * 文件名
     * varchar(200)
     */
    fun fileName(): Schema.Field<String?> {
        return Schema.Field(root.get(props.fileName), this.criteriaBuilder)
    }
    /**
     * 文件大小
     * bigint
     */
    fun fileSize(): Schema.Field<Long?> {
        return Schema.Field(root.get(props.fileSize), this.criteriaBuilder)
    }
    /**
     * 文件路径
     * varchar(100)
     */
    fun filePath(): Schema.Field<String?> {
        return Schema.Field(root.get(props.filePath), this.criteriaBuilder)
    }
    /**
     * 更新类型
     * 0:UNKNOW:未知类型
     * 1:NO_UPDATE:无更新
     * 2:HAS_UPDATE:有更新
     * tinyint
     */
    fun updateType(): Schema.Field<com.edu.only4.danmuku.domain.aggregates.video_file_draft.enums.UpdateType> {
        return Schema.Field(root.get(props.updateType), this.criteriaBuilder)
    }
    /**
     * 转码结果
     * 0:UNKNOW:未知结果
     * 1:TRANSCODING:转码中
     * 2:SUCCESS:转码成功
     * 3:FAILED:转码失败
     * tinyint
     */
    fun transferResult(): Schema.Field<com.edu.only4.danmuku.domain.aggregates.video_file_draft.enums.TransferResult> {
        return Schema.Field(root.get(props.transferResult), this.criteriaBuilder)
    }
    /**
     * 持续时间（秒）
     * int
     */
    fun duration(): Schema.Field<Int?> {
        return Schema.Field(root.get(props.duration), this.criteriaBuilder)
    }
    /**
     * 删除标识 0：未删除 id：已删除
     * tinyint(1)
     */
    fun deleted(): Schema.Field<Boolean> {
        return Schema.Field(root.get(props.deleted), this.criteriaBuilder)
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
    fun spec(builder: Schema.PredicateBuilder<SVideoFileDraft>): Predicate {
        return builder.build(this)
    }
    
    
}