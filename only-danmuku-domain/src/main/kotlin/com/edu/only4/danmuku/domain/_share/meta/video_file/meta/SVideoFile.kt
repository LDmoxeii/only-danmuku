package com.edu.only4.danmuku.domain._share.meta.video_file.meta

import com.querydsl.core.types.OrderSpecifier
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import com.only4.cap4k.ddd.domain.repo.querydsl.QuerydslPredicate
import com.edu.only4.danmuku.domain._share.meta.Schema
import com.edu.only4.danmuku.domain.aggregates.video_file.AggVideoFile
import com.edu.only4.danmuku.domain.aggregates.video_file.VideoFile
import com.edu.only4.danmuku.domain.aggregates.video_file.QVideoFile
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
class SVideoFile(
    private val root: Path<VideoFile>,
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
         * 用户ID
         */
        val customerId = "customerId"
           
        /**
         * 视频ID
         */
        val videoId = "videoId"
           
        /**
         * 文件名
         */
        val fileName = "fileName"
           
        /**
         * 文件索引
         */
        val fileIndex = "fileIndex"
           
        /**
         * 文件大小
         */
        val fileSize = "fileSize"
           
        /**
         * 文件路径
         */
        val filePath = "filePath"
           
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
        fun specify(builder: Schema.PredicateBuilder<SVideoFile>): Specification<VideoFile> {
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
        fun specify(builder: Schema.PredicateBuilder<SVideoFile>, distinct: Boolean): Specification<VideoFile> {
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
            builder: Schema.PredicateBuilder<SVideoFile>,
            vararg orderBuilders: Schema.OrderBuilder<SVideoFile>,
        ): Specification<VideoFile> {
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
            builder: Schema.PredicateBuilder<SVideoFile>,
            orderBuilders: List<Schema.OrderBuilder<SVideoFile>>,
        ): Specification<VideoFile> {
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
            builder: Schema.PredicateBuilder<SVideoFile>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SVideoFile>,
        ): Specification<VideoFile> {
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
            builder: Schema.PredicateBuilder<SVideoFile>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SVideoFile>>,
        ): Specification<VideoFile> {
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
        fun specify(specifier: Schema.Specification<VideoFile, SVideoFile>): Specification<VideoFile> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SVideoFile(root, criteriaBuilder)
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
            selectBuilder: Schema.ExpressionBuilder<SVideoFile, E>,
            predicateBuilder: Schema.PredicateBuilder<SVideoFile>,
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
            subqueryConfigure: Schema.SubqueryConfigure<E, SVideoFile>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(VideoFile::class.java)
            val schema = SVideoFile(root, criteriaBuilder)
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
        fun predicateById(id: Any): AggregatePredicate<AggVideoFile, VideoFile> {
            return JpaPredicate.byId(VideoFile::class.java, id).toAggregatePredicate(AggVideoFile::class.java)
        }
    
        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): AggregatePredicate<AggVideoFile, VideoFile> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(VideoFile::class.java, ids as Iterable<Any>).toAggregatePredicate(AggVideoFile::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): AggregatePredicate<AggVideoFile, VideoFile> {
            return JpaPredicate.byIds(VideoFile::class.java, ids.toList()).toAggregatePredicate(AggVideoFile::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SVideoFile>): AggregatePredicate<AggVideoFile, VideoFile> {
            return JpaPredicate.bySpecification(VideoFile::class.java, specify(builder)).toAggregatePredicate(AggVideoFile::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SVideoFile>, distinct: Boolean): AggregatePredicate<AggVideoFile, VideoFile> {
            return JpaPredicate.bySpecification(VideoFile::class.java, specify(builder, distinct)).toAggregatePredicate(AggVideoFile::class.java)
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
            builder: Schema.PredicateBuilder<SVideoFile>,
            orderBuilders: List<Schema.OrderBuilder<SVideoFile>>,
        ): AggregatePredicate<AggVideoFile, VideoFile> {
            return JpaPredicate.bySpecification(VideoFile::class.java, specify(builder, false, orderBuilders)).toAggregatePredicate(AggVideoFile::class.java)
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
            builder: Schema.PredicateBuilder<SVideoFile>,
            vararg orderBuilders: Schema.OrderBuilder<SVideoFile>,
        ): AggregatePredicate<AggVideoFile, VideoFile> {
            return JpaPredicate.bySpecification(VideoFile::class.java, specify(builder, false, *orderBuilders)).toAggregatePredicate(AggVideoFile::class.java)
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
            builder: Schema.PredicateBuilder<SVideoFile>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SVideoFile>>,
        ): AggregatePredicate<AggVideoFile, VideoFile> {
            return JpaPredicate.bySpecification(VideoFile::class.java, specify(builder, distinct, orderBuilders)).toAggregatePredicate(AggVideoFile::class.java)
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
            builder: Schema.PredicateBuilder<SVideoFile>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SVideoFile>,
        ): AggregatePredicate<AggVideoFile, VideoFile> {
            return JpaPredicate.bySpecification(VideoFile::class.java, specify(builder, distinct, *orderBuilders)).toAggregatePredicate(AggVideoFile::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: Schema.Specification<VideoFile, SVideoFile>): AggregatePredicate<AggVideoFile, VideoFile> {
            return JpaPredicate.bySpecification(VideoFile::class.java, specify(specifier)).toAggregatePredicate(AggVideoFile::class.java)
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
            filterBuilder: java.util.function.Function<QVideoFile, com.querydsl.core.types.Predicate>,
            vararg orderSpecifierBuilders: java.util.function.Function<QVideoFile, OrderSpecifier<*>>,
        ): AggregatePredicate<AggVideoFile, VideoFile> {
            return QuerydslPredicate.of(VideoFile::class.java)
                .where(filterBuilder.apply(QVideoFile.videoFile))
                .orderBy(*orderSpecifierBuilders.map { it.apply(QVideoFile.videoFile) }.toTypedArray())
                .toAggregatePredicate(AggVideoFile::class.java)
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
        ): AggregatePredicate<AggVideoFile, VideoFile> {
            return QuerydslPredicate.of(VideoFile::class.java)
                .where(filter)
                .orderBy(*orderSpecifiers)
                .toAggregatePredicate(AggVideoFile::class.java)
        }  
    }
    
    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<VideoFile> = root

    
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
     * 文件名
     * varchar(200)
     */
    fun fileName(): Schema.Field<String?> {
        return Schema.Field(root.get(props.fileName), this.criteriaBuilder)
    }
    /**
     * 文件索引
     * int
     */
    fun fileIndex(): Schema.Field<Int> {
        return Schema.Field(root.get(props.fileIndex), this.criteriaBuilder)
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
    fun spec(builder: Schema.PredicateBuilder<SVideoFile>): Predicate {
        return builder.build(this)
    }
    
    
}