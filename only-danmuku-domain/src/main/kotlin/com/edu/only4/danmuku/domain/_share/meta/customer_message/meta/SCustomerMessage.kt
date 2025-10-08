package com.edu.only4.danmuku.domain._share.meta.customer_message.meta

import com.querydsl.core.types.OrderSpecifier
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import com.only4.cap4k.ddd.domain.repo.querydsl.QuerydslPredicate
import com.edu.only4.danmuku.domain._share.meta.Schema
import com.edu.only4.danmuku.domain.aggregates.customer_message.AggCustomerMessage
import com.edu.only4.danmuku.domain.aggregates.customer_message.CustomerMessage
import com.edu.only4.danmuku.domain.aggregates.customer_message.QCustomerMessage
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePredicate
import org.springframework.data.jpa.domain.Specification

import jakarta.persistence.criteria.*

/**
 * 用户消息表;
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/10/08
 */
class SCustomerMessage(
    private val root: Path<CustomerMessage>,
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
         * 主体ID
         */
        val videoId = "videoId"
           
        /**
         * 消息类型
         */
        val messageType = "messageType"
           
        /**
         * 发送主体ID
         */
        val sendSubjectId = "sendSubjectId"
           
        /**
         * 读取状态
         */
        val readType = "readType"
           
        /**
         * 扩展信息
         */
        val extendJson = "extendJson"
           
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
        fun specify(builder: Schema.PredicateBuilder<SCustomerMessage>): Specification<CustomerMessage> {
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
        fun specify(builder: Schema.PredicateBuilder<SCustomerMessage>, distinct: Boolean): Specification<CustomerMessage> {
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
            builder: Schema.PredicateBuilder<SCustomerMessage>,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerMessage>,
        ): Specification<CustomerMessage> {
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
            builder: Schema.PredicateBuilder<SCustomerMessage>,
            orderBuilders: List<Schema.OrderBuilder<SCustomerMessage>>,
        ): Specification<CustomerMessage> {
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
            builder: Schema.PredicateBuilder<SCustomerMessage>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerMessage>,
        ): Specification<CustomerMessage> {
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
            builder: Schema.PredicateBuilder<SCustomerMessage>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SCustomerMessage>>,
        ): Specification<CustomerMessage> {
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
        fun specify(specifier: Schema.Specification<CustomerMessage, SCustomerMessage>): Specification<CustomerMessage> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SCustomerMessage(root, criteriaBuilder)
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
            selectBuilder: Schema.ExpressionBuilder<SCustomerMessage, E>,
            predicateBuilder: Schema.PredicateBuilder<SCustomerMessage>,
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
            subqueryConfigure: Schema.SubqueryConfigure<E, SCustomerMessage>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(CustomerMessage::class.java)
            val schema = SCustomerMessage(root, criteriaBuilder)
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
        fun predicateById(id: Any): AggregatePredicate<AggCustomerMessage, CustomerMessage> {
            return JpaPredicate.byId(CustomerMessage::class.java, id).toAggregatePredicate(AggCustomerMessage::class.java)
        }
    
        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): AggregatePredicate<AggCustomerMessage, CustomerMessage> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(CustomerMessage::class.java, ids as Iterable<Any>).toAggregatePredicate(AggCustomerMessage::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): AggregatePredicate<AggCustomerMessage, CustomerMessage> {
            return JpaPredicate.byIds(CustomerMessage::class.java, ids.toList()).toAggregatePredicate(AggCustomerMessage::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SCustomerMessage>): AggregatePredicate<AggCustomerMessage, CustomerMessage> {
            return JpaPredicate.bySpecification(CustomerMessage::class.java, specify(builder)).toAggregatePredicate(AggCustomerMessage::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SCustomerMessage>, distinct: Boolean): AggregatePredicate<AggCustomerMessage, CustomerMessage> {
            return JpaPredicate.bySpecification(CustomerMessage::class.java, specify(builder, distinct)).toAggregatePredicate(AggCustomerMessage::class.java)
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
            builder: Schema.PredicateBuilder<SCustomerMessage>,
            orderBuilders: List<Schema.OrderBuilder<SCustomerMessage>>,
        ): AggregatePredicate<AggCustomerMessage, CustomerMessage> {
            return JpaPredicate.bySpecification(CustomerMessage::class.java, specify(builder, false, orderBuilders)).toAggregatePredicate(AggCustomerMessage::class.java)
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
            builder: Schema.PredicateBuilder<SCustomerMessage>,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerMessage>,
        ): AggregatePredicate<AggCustomerMessage, CustomerMessage> {
            return JpaPredicate.bySpecification(CustomerMessage::class.java, specify(builder, false, *orderBuilders)).toAggregatePredicate(AggCustomerMessage::class.java)
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
            builder: Schema.PredicateBuilder<SCustomerMessage>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SCustomerMessage>>,
        ): AggregatePredicate<AggCustomerMessage, CustomerMessage> {
            return JpaPredicate.bySpecification(CustomerMessage::class.java, specify(builder, distinct, orderBuilders)).toAggregatePredicate(AggCustomerMessage::class.java)
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
            builder: Schema.PredicateBuilder<SCustomerMessage>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerMessage>,
        ): AggregatePredicate<AggCustomerMessage, CustomerMessage> {
            return JpaPredicate.bySpecification(CustomerMessage::class.java, specify(builder, distinct, *orderBuilders)).toAggregatePredicate(AggCustomerMessage::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: Schema.Specification<CustomerMessage, SCustomerMessage>): AggregatePredicate<AggCustomerMessage, CustomerMessage> {
            return JpaPredicate.bySpecification(CustomerMessage::class.java, specify(specifier)).toAggregatePredicate(AggCustomerMessage::class.java)
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
            filterBuilder: java.util.function.Function<QCustomerMessage, com.querydsl.core.types.Predicate>,
            vararg orderSpecifierBuilders: java.util.function.Function<QCustomerMessage, OrderSpecifier<*>>,
        ): AggregatePredicate<AggCustomerMessage, CustomerMessage> {
            return QuerydslPredicate.of(CustomerMessage::class.java)
                .where(filterBuilder.apply(QCustomerMessage.customerMessage))
                .orderBy(*orderSpecifierBuilders.map { it.apply(QCustomerMessage.customerMessage) }.toTypedArray())
                .toAggregatePredicate(AggCustomerMessage::class.java)
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
        ): AggregatePredicate<AggCustomerMessage, CustomerMessage> {
            return QuerydslPredicate.of(CustomerMessage::class.java)
                .where(filter)
                .orderBy(*orderSpecifiers)
                .toAggregatePredicate(AggCustomerMessage::class.java)
        }  
    }
    
    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<CustomerMessage> = root

    
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
     * 主体ID
     * varchar(10)
     */
    fun videoId(): Schema.Field<String?> {
        return Schema.Field(root.get(props.videoId), this.criteriaBuilder)
    }
    /**
     * 消息类型
     * 0:UNKNOW:未知消息
     * 1:SYSTEM_MESSAGE:系统消息
     * 2:COMMENT_REPLY:评论回复
     * 3:VIDEO_DYNAMIC:视频动态
     * 4:PRIVATE_MESSAGE:私信消息
     * 5:ACTIVITY_NOTICE:活动通知
     * 6:OTHER_MESSAGE:其他消息
     * tinyint(1)
     */
    fun messageType(): Schema.Field<com.edu.only4.danmuku.domain.aggregates.customer_message.enums.MessageType> {
        return Schema.Field(root.get(props.messageType), this.criteriaBuilder)
    }
    /**
     * 发送主体ID
     * varchar(10)
     */
    fun sendSubjectId(): Schema.Field<String?> {
        return Schema.Field(root.get(props.sendSubjectId), this.criteriaBuilder)
    }
    /**
     * 读取状态
     * 0:UNKNOW:未知状态
     * 1:UNREAD:未读
     * 2:READ:已读
     * tinyint(1)
     */
    fun readType(): Schema.Field<com.edu.only4.danmuku.domain.aggregates.customer_message.enums.ReadType> {
        return Schema.Field(root.get(props.readType), this.criteriaBuilder)
    }
    /**
     * 扩展信息
     * text
     */
    fun extendJson(): Schema.Field<String?> {
        return Schema.Field(root.get(props.extendJson), this.criteriaBuilder)
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
    fun spec(builder: Schema.PredicateBuilder<SCustomerMessage>): Predicate {
        return builder.build(this)
    }
    
    
}