package com.edu.only4.danmuku.domain._share.meta.customer_action.meta

import com.querydsl.core.types.OrderSpecifier
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import com.only4.cap4k.ddd.domain.repo.querydsl.QuerydslPredicate
import com.edu.only4.danmuku.domain._share.meta.Schema
import com.edu.only4.danmuku.domain.aggregates.customer_action.AggCustomerAction
import com.edu.only4.danmuku.domain.aggregates.customer_action.CustomerAction
import com.edu.only4.danmuku.domain.aggregates.customer_action.QCustomerAction
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePredicate
import org.springframework.data.jpa.domain.Specification

import jakarta.persistence.criteria.*

/**
 * 用户行为 点赞、评论;
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/10/08
 */
class SCustomerAction(
    private val root: Path<CustomerAction>,
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
         * 视频ID
         */
        val videoId = "videoId"
           
        /**
         * 视频用户ID
         */
        val videoOwnerId = "videoOwnerId"
           
        /**
         * 评论ID
         */
        val commentId = "commentId"
           
        /**
         * 行为类型
         */
        val actionType = "actionType"
           
        /**
         * 数量
         */
        val actionCount = "actionCount"
           
        /**
         * 操作时间
         */
        val actionTime = "actionTime"
           
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
        fun specify(builder: Schema.PredicateBuilder<SCustomerAction>): Specification<CustomerAction> {
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
        fun specify(builder: Schema.PredicateBuilder<SCustomerAction>, distinct: Boolean): Specification<CustomerAction> {
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
            builder: Schema.PredicateBuilder<SCustomerAction>,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerAction>,
        ): Specification<CustomerAction> {
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
            builder: Schema.PredicateBuilder<SCustomerAction>,
            orderBuilders: List<Schema.OrderBuilder<SCustomerAction>>,
        ): Specification<CustomerAction> {
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
            builder: Schema.PredicateBuilder<SCustomerAction>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerAction>,
        ): Specification<CustomerAction> {
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
            builder: Schema.PredicateBuilder<SCustomerAction>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SCustomerAction>>,
        ): Specification<CustomerAction> {
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
        fun specify(specifier: Schema.Specification<CustomerAction, SCustomerAction>): Specification<CustomerAction> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SCustomerAction(root, criteriaBuilder)
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
            selectBuilder: Schema.ExpressionBuilder<SCustomerAction, E>,
            predicateBuilder: Schema.PredicateBuilder<SCustomerAction>,
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
            subqueryConfigure: Schema.SubqueryConfigure<E, SCustomerAction>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(CustomerAction::class.java)
            val schema = SCustomerAction(root, criteriaBuilder)
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
        fun predicateById(id: Any): AggregatePredicate<AggCustomerAction, CustomerAction> {
            return JpaPredicate.byId(CustomerAction::class.java, id).toAggregatePredicate(AggCustomerAction::class.java)
        }
    
        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): AggregatePredicate<AggCustomerAction, CustomerAction> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(CustomerAction::class.java, ids as Iterable<Any>).toAggregatePredicate(AggCustomerAction::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): AggregatePredicate<AggCustomerAction, CustomerAction> {
            return JpaPredicate.byIds(CustomerAction::class.java, ids.toList()).toAggregatePredicate(AggCustomerAction::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SCustomerAction>): AggregatePredicate<AggCustomerAction, CustomerAction> {
            return JpaPredicate.bySpecification(CustomerAction::class.java, specify(builder)).toAggregatePredicate(AggCustomerAction::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SCustomerAction>, distinct: Boolean): AggregatePredicate<AggCustomerAction, CustomerAction> {
            return JpaPredicate.bySpecification(CustomerAction::class.java, specify(builder, distinct)).toAggregatePredicate(AggCustomerAction::class.java)
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
            builder: Schema.PredicateBuilder<SCustomerAction>,
            orderBuilders: List<Schema.OrderBuilder<SCustomerAction>>,
        ): AggregatePredicate<AggCustomerAction, CustomerAction> {
            return JpaPredicate.bySpecification(CustomerAction::class.java, specify(builder, false, orderBuilders)).toAggregatePredicate(AggCustomerAction::class.java)
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
            builder: Schema.PredicateBuilder<SCustomerAction>,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerAction>,
        ): AggregatePredicate<AggCustomerAction, CustomerAction> {
            return JpaPredicate.bySpecification(CustomerAction::class.java, specify(builder, false, *orderBuilders)).toAggregatePredicate(AggCustomerAction::class.java)
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
            builder: Schema.PredicateBuilder<SCustomerAction>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SCustomerAction>>,
        ): AggregatePredicate<AggCustomerAction, CustomerAction> {
            return JpaPredicate.bySpecification(CustomerAction::class.java, specify(builder, distinct, orderBuilders)).toAggregatePredicate(AggCustomerAction::class.java)
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
            builder: Schema.PredicateBuilder<SCustomerAction>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerAction>,
        ): AggregatePredicate<AggCustomerAction, CustomerAction> {
            return JpaPredicate.bySpecification(CustomerAction::class.java, specify(builder, distinct, *orderBuilders)).toAggregatePredicate(AggCustomerAction::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: Schema.Specification<CustomerAction, SCustomerAction>): AggregatePredicate<AggCustomerAction, CustomerAction> {
            return JpaPredicate.bySpecification(CustomerAction::class.java, specify(specifier)).toAggregatePredicate(AggCustomerAction::class.java)
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
            filterBuilder: java.util.function.Function<QCustomerAction, com.querydsl.core.types.Predicate>,
            vararg orderSpecifierBuilders: java.util.function.Function<QCustomerAction, OrderSpecifier<*>>,
        ): AggregatePredicate<AggCustomerAction, CustomerAction> {
            return QuerydslPredicate.of(CustomerAction::class.java)
                .where(filterBuilder.apply(QCustomerAction.customerAction))
                .orderBy(*orderSpecifierBuilders.map { it.apply(QCustomerAction.customerAction) }.toTypedArray())
                .toAggregatePredicate(AggCustomerAction::class.java)
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
        ): AggregatePredicate<AggCustomerAction, CustomerAction> {
            return QuerydslPredicate.of(CustomerAction::class.java)
                .where(filter)
                .orderBy(*orderSpecifiers)
                .toAggregatePredicate(AggCustomerAction::class.java)
        }  
    }
    
    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<CustomerAction> = root

    
    /**
     * ID
     * bigint
     */
    fun id(): Schema.Field<Long> {
        return Schema.Field(root.get(props.id), this.criteriaBuilder)
    }
    /**
     * 用户ID
     * varchar(10)
     */
    fun customerId(): Schema.Field<String> {
        return Schema.Field(root.get(props.customerId), this.criteriaBuilder)
    }
    /**
     * 视频ID
     * varchar(10)
     */
    fun videoId(): Schema.Field<String> {
        return Schema.Field(root.get(props.videoId), this.criteriaBuilder)
    }
    /**
     * 视频用户ID
     * varchar(10)
     */
    fun videoOwnerId(): Schema.Field<String> {
        return Schema.Field(root.get(props.videoOwnerId), this.criteriaBuilder)
    }
    /**
     * 评论ID
     * bigint
     */
    fun commentId(): Schema.Field<Long> {
        return Schema.Field(root.get(props.commentId), this.criteriaBuilder)
    }
    /**
     * 行为类型
     * 0:UNKNOW:未知行为
     * 1:LIKE_COMMENT:评论喜欢点赞
     * 2:HATE_COMMENT:讨厌评论
     * 3:LIKE_VIDEO:视频点赞
     * 4:FAVORITE_VIDEO:视频收藏
     * 5:COIN_VIDEO:视频投币
     * tinyint(1)
     */
    fun actionType(): Schema.Field<com.edu.only4.danmuku.domain.aggregates.customer_action.enums.ActionType> {
        return Schema.Field(root.get(props.actionType), this.criteriaBuilder)
    }
    /**
     * 数量
     * int
     */
    fun actionCount(): Schema.Field<Int> {
        return Schema.Field(root.get(props.actionCount), this.criteriaBuilder)
    }
    /**
     * 操作时间
     * bigint
     */
    fun actionTime(): Schema.Field<Long> {
        return Schema.Field(root.get(props.actionTime), this.criteriaBuilder)
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
    fun spec(builder: Schema.PredicateBuilder<SCustomerAction>): Predicate {
        return builder.build(this)
    }
    
    
}