package com.edu.only4.danmuku.domain._share.meta.video.meta

import com.querydsl.core.types.OrderSpecifier
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import com.only4.cap4k.ddd.domain.repo.querydsl.QuerydslPredicate
import com.edu.only4.danmuku.domain._share.meta.Schema
import com.edu.only4.danmuku.domain.aggregates.video.AggVideo
import com.edu.only4.danmuku.domain.aggregates.video.Video
import com.edu.only4.danmuku.domain.aggregates.video.QVideo
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePredicate
import org.springframework.data.jpa.domain.Specification

import jakarta.persistence.criteria.*

/**
 * 视频信息;
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/09/27
 */
class SVideo(
    private val root: Path<Video>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {
            
        /**
         * ID
         */
        val id = "id"
           
        /**
         * 用户ID
         */
        val customerId = "customerId"
           
        /**
         * 视频封面
         */
        val videoCover = "videoCover"
           
        /**
         * 视频名称
         */
        val videoName = "videoName"
           
        /**
         * 父级分类ID
         */
        val pCategoryId = "pCategoryId"
           
        /**
         * 分类ID
         */
        val categoryId = "categoryId"
           
        /**
         * 投稿类型
         */
        val postType = "postType"
           
        /**
         * 原资源说明
         */
        val originInfo = "originInfo"
           
        /**
         * 标签
         */
        val tags = "tags"
           
        /**
         * 简介
         */
        val introduction = "introduction"
           
        /**
         * 互动设置
         */
        val interaction = "interaction"
           
        /**
         * 持续时间（秒）
         */
        val duration = "duration"
           
        /**
         * 播放数量
         */
        val playCount = "playCount"
           
        /**
         * 点赞数量
         */
        val likeCount = "likeCount"
           
        /**
         * 弹幕数量
         */
        val danmukuCount = "danmukuCount"
           
        /**
         * 评论数量
         */
        val commentCount = "commentCount"
           
        /**
         * 投币数量
         */
        val coinCount = "coinCount"
           
        /**
         * 收藏数量
         */
        val collectCount = "collectCount"
           
        /**
         * 推荐状态
         */
        val recommendType = "recommendType"
           
        /**
         * 最后播放时间
         */
        val lastPlayTime = "lastPlayTime"
           
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
        fun specify(builder: Schema.PredicateBuilder<SVideo>): Specification<Video> {
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
        fun specify(builder: Schema.PredicateBuilder<SVideo>, distinct: Boolean): Specification<Video> {
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
            builder: Schema.PredicateBuilder<SVideo>,
            vararg orderBuilders: Schema.OrderBuilder<SVideo>,
        ): Specification<Video> {
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
            builder: Schema.PredicateBuilder<SVideo>,
            orderBuilders: List<Schema.OrderBuilder<SVideo>>,
        ): Specification<Video> {
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
            builder: Schema.PredicateBuilder<SVideo>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SVideo>,
        ): Specification<Video> {
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
            builder: Schema.PredicateBuilder<SVideo>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SVideo>>,
        ): Specification<Video> {
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
        fun specify(specifier: Schema.Specification<Video, SVideo>): Specification<Video> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SVideo(root, criteriaBuilder)
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
            selectBuilder: Schema.ExpressionBuilder<SVideo, E>,
            predicateBuilder: Schema.PredicateBuilder<SVideo>,
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
            subqueryConfigure: Schema.SubqueryConfigure<E, SVideo>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(Video::class.java)
            val schema = SVideo(root, criteriaBuilder)
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
        fun predicateById(id: Any): AggregatePredicate<AggVideo, Video> {
            return JpaPredicate.byId(Video::class.java, id).toAggregatePredicate(AggVideo::class.java)
        }
    
        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): AggregatePredicate<AggVideo, Video> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(Video::class.java, ids as Iterable<Any>).toAggregatePredicate(AggVideo::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): AggregatePredicate<AggVideo, Video> {
            return JpaPredicate.byIds(Video::class.java, ids.toList()).toAggregatePredicate(AggVideo::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SVideo>): AggregatePredicate<AggVideo, Video> {
            return JpaPredicate.bySpecification(Video::class.java, specify(builder)).toAggregatePredicate(AggVideo::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SVideo>, distinct: Boolean): AggregatePredicate<AggVideo, Video> {
            return JpaPredicate.bySpecification(Video::class.java, specify(builder, distinct)).toAggregatePredicate(AggVideo::class.java)
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
            builder: Schema.PredicateBuilder<SVideo>,
            orderBuilders: List<Schema.OrderBuilder<SVideo>>,
        ): AggregatePredicate<AggVideo, Video> {
            return JpaPredicate.bySpecification(Video::class.java, specify(builder, false, orderBuilders)).toAggregatePredicate(AggVideo::class.java)
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
            builder: Schema.PredicateBuilder<SVideo>,
            vararg orderBuilders: Schema.OrderBuilder<SVideo>,
        ): AggregatePredicate<AggVideo, Video> {
            return JpaPredicate.bySpecification(Video::class.java, specify(builder, false, *orderBuilders)).toAggregatePredicate(AggVideo::class.java)
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
            builder: Schema.PredicateBuilder<SVideo>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SVideo>>,
        ): AggregatePredicate<AggVideo, Video> {
            return JpaPredicate.bySpecification(Video::class.java, specify(builder, distinct, orderBuilders)).toAggregatePredicate(AggVideo::class.java)
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
            builder: Schema.PredicateBuilder<SVideo>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SVideo>,
        ): AggregatePredicate<AggVideo, Video> {
            return JpaPredicate.bySpecification(Video::class.java, specify(builder, distinct, *orderBuilders)).toAggregatePredicate(AggVideo::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: Schema.Specification<Video, SVideo>): AggregatePredicate<AggVideo, Video> {
            return JpaPredicate.bySpecification(Video::class.java, specify(specifier)).toAggregatePredicate(AggVideo::class.java)
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
            filterBuilder: java.util.function.Function<QVideo, com.querydsl.core.types.Predicate>,
            vararg orderSpecifierBuilders: java.util.function.Function<QVideo, OrderSpecifier<*>>,
        ): AggregatePredicate<AggVideo, Video> {
            return QuerydslPredicate.of(Video::class.java)
                .where(filterBuilder.apply(QVideo.video))
                .orderBy(*orderSpecifierBuilders.map { it.apply(QVideo.video) }.toTypedArray())
                .toAggregatePredicate(AggVideo::class.java)
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
        ): AggregatePredicate<AggVideo, Video> {
            return QuerydslPredicate.of(Video::class.java)
                .where(filter)
                .orderBy(*orderSpecifiers)
                .toAggregatePredicate(AggVideo::class.java)
        }  
    }
    
    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<Video> = root

    
    /**
     * ID
     * bigint
     */
    fun id(): Schema.Field<Long> {
        return Schema.Field(root.get(props.id), this.criteriaBuilder)
    }
    /**
     * 用户ID
     * bigint
     */
    fun customerId(): Schema.Field<Long> {
        return Schema.Field(root.get(props.customerId), this.criteriaBuilder)
    }
    /**
     * 视频封面
     * varchar(50)
     */
    fun videoCover(): Schema.Field<String> {
        return Schema.Field(root.get(props.videoCover), this.criteriaBuilder)
    }
    /**
     * 视频名称
     * varchar(100)
     */
    fun videoName(): Schema.Field<String> {
        return Schema.Field(root.get(props.videoName), this.criteriaBuilder)
    }
    /**
     * 父级分类ID
     * bigint
     */
    fun pCategoryId(): Schema.Field<Long> {
        return Schema.Field(root.get(props.pCategoryId), this.criteriaBuilder)
    }
    /**
     * 分类ID
     * bigint
     */
    fun categoryId(): Schema.Field<Long?> {
        return Schema.Field(root.get(props.categoryId), this.criteriaBuilder)
    }
    /**
     * 投稿类型
     * 0:UNKNOW:未知类型
     * 1:ORIGINAL:自制作
     * 2:REPOST:转载
     * tinyint
     */
    fun postType(): Schema.Field<com.edu.only4.danmuku.domain.aggregates.video_draft.enums.PostType> {
        return Schema.Field(root.get(props.postType), this.criteriaBuilder)
    }
    /**
     * 原资源说明
     * varchar(200)
     */
    fun originInfo(): Schema.Field<String?> {
        return Schema.Field(root.get(props.originInfo), this.criteriaBuilder)
    }
    /**
     * 标签
     * varchar(300)
     */
    fun tags(): Schema.Field<String?> {
        return Schema.Field(root.get(props.tags), this.criteriaBuilder)
    }
    /**
     * 简介
     * varchar(2000)
     */
    fun introduction(): Schema.Field<String?> {
        return Schema.Field(root.get(props.introduction), this.criteriaBuilder)
    }
    /**
     * 互动设置
     * varchar(5)
     */
    fun interaction(): Schema.Field<String?> {
        return Schema.Field(root.get(props.interaction), this.criteriaBuilder)
    }
    /**
     * 持续时间（秒）
     * int
     */
    fun duration(): Schema.Field<Int?> {
        return Schema.Field(root.get(props.duration), this.criteriaBuilder)
    }
    /**
     * 播放数量
     * int
     */
    fun playCount(): Schema.Field<Int?> {
        return Schema.Field(root.get(props.playCount), this.criteriaBuilder)
    }
    /**
     * 点赞数量
     * int
     */
    fun likeCount(): Schema.Field<Int?> {
        return Schema.Field(root.get(props.likeCount), this.criteriaBuilder)
    }
    /**
     * 弹幕数量
     * int
     */
    fun danmukuCount(): Schema.Field<Int?> {
        return Schema.Field(root.get(props.danmukuCount), this.criteriaBuilder)
    }
    /**
     * 评论数量
     * int
     */
    fun commentCount(): Schema.Field<Int?> {
        return Schema.Field(root.get(props.commentCount), this.criteriaBuilder)
    }
    /**
     * 投币数量
     * int
     */
    fun coinCount(): Schema.Field<Int?> {
        return Schema.Field(root.get(props.coinCount), this.criteriaBuilder)
    }
    /**
     * 收藏数量
     * int
     */
    fun collectCount(): Schema.Field<Int?> {
        return Schema.Field(root.get(props.collectCount), this.criteriaBuilder)
    }
    /**
     * 推荐状态
     * 0:UNKNOW:未知状态
     * 1:NOT_RECOMMEND:未推荐
     * 2:RECOMMEND:已推荐
     * tinyint(1)
     */
    fun recommendType(): Schema.Field<com.edu.only4.danmuku.domain.aggregates.video.enums.RecommendType> {
        return Schema.Field(root.get(props.recommendType), this.criteriaBuilder)
    }
    /**
     * 最后播放时间
     * datetime
     */
    fun lastPlayTime(): Schema.Field<java.time.LocalDateTime?> {
        return Schema.Field(root.get(props.lastPlayTime), this.criteriaBuilder)
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
    fun spec(builder: Schema.PredicateBuilder<SVideo>): Predicate {
        return builder.build(this)
    }
    
    
}