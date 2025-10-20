package edu.only4.danmuku.domain._share.meta.customer_message

import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePredicate
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import com.only4.cap4k.ddd.domain.repo.querydsl.QuerydslPredicate
import com.querydsl.core.types.OrderSpecifier
import edu.only4.danmuku.domain._share.meta.*
import edu.only4.danmuku.domain.aggregates.customer_message.AggCustomerMessage
import edu.only4.danmuku.domain.aggregates.customer_message.CustomerMessage
import edu.only4.danmuku.domain.aggregates.customer_message.QCustomerMessage
import edu.only4.danmuku.domain.aggregates.customer_message.enums.MessageType
import edu.only4.danmuku.domain.aggregates.customer_message.enums.ReadType
import jakarta.persistence.criteria.*
import org.springframework.data.jpa.domain.Specification

/**
 * 用户消息表;
 *
 * Schema 类用于类型安全的 JPA Criteria 查询
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/10/20
 */
class SCustomerMessage(
    private val root: Path<CustomerMessage>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {

        val id = "id"

        val customerId = "customerId"

        val videoId = "videoId"

        val messageType = "messageType"

        val sendSubjectId = "sendSubjectId"

        val readType = "readType"

        val extendJson = "extendJson"

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
        fun specify(builder: PredicateBuilder<SCustomerMessage>): Specification<CustomerMessage> {
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
        fun specify(builder: PredicateBuilder<SCustomerMessage>, distinct: Boolean): Specification<CustomerMessage> {
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
            builder: PredicateBuilder<SCustomerMessage>,
            vararg orderBuilders: OrderBuilder<SCustomerMessage>,
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
            builder: PredicateBuilder<SCustomerMessage>,
            orderBuilders: List<OrderBuilder<SCustomerMessage>>,
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
            builder: PredicateBuilder<SCustomerMessage>,
            distinct: Boolean,
            vararg orderBuilders: OrderBuilder<SCustomerMessage>,
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
            builder: PredicateBuilder<SCustomerMessage>,
            distinct: Boolean,
            orderBuilders: List<OrderBuilder<SCustomerMessage>>,
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
        fun specify(specifier: SchemaSpecification<CustomerMessage, SCustomerMessage>): Specification<CustomerMessage> {
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
            selectBuilder: ExpressionBuilder<SCustomerMessage, E>,
            predicateBuilder: PredicateBuilder<SCustomerMessage>,
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
            subqueryConfigure: SubqueryConfigure<E, SCustomerMessage>,
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
        fun predicate(builder: PredicateBuilder<SCustomerMessage>): AggregatePredicate<AggCustomerMessage, CustomerMessage> {
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
        fun predicate(
            builder: PredicateBuilder<SCustomerMessage>,
            distinct: Boolean,
        ): AggregatePredicate<AggCustomerMessage, CustomerMessage> {
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
            builder: PredicateBuilder<SCustomerMessage>,
            orderBuilders: List<OrderBuilder<SCustomerMessage>>,
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
            builder: PredicateBuilder<SCustomerMessage>,
            vararg orderBuilders: OrderBuilder<SCustomerMessage>,
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
            builder: PredicateBuilder<SCustomerMessage>,
            distinct: Boolean,
            orderBuilders: List<OrderBuilder<SCustomerMessage>>,
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
            builder: PredicateBuilder<SCustomerMessage>,
            distinct: Boolean,
            vararg orderBuilders: OrderBuilder<SCustomerMessage>,
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
        fun predicate(specifier: SchemaSpecification<CustomerMessage, SCustomerMessage>): AggregatePredicate<AggCustomerMessage, CustomerMessage> {
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
     */
    val id: Field<Long> by lazy {
        Field(root.get("id"), criteriaBuilder)
    }


    /**
     * 用户ID
     */
    val customerId: Field<String> by lazy {
        Field(root.get("customerId"), criteriaBuilder)
    }


    /**
     * 主体ID
     */
    val videoId: Field<String?> by lazy {
        Field(root.get("videoId"), criteriaBuilder)
    }


    /**
     * 消息类型
     */
    val messageType: Field<MessageType> by lazy {
        Field(root.get("messageType"), criteriaBuilder)
    }


    /**
     * 发送主体ID
     */
    val sendSubjectId: Field<String?> by lazy {
        Field(root.get("sendSubjectId"), criteriaBuilder)
    }


    /**
     * 读取状态
     */
    val readType: Field<ReadType> by lazy {
        Field(root.get("readType"), criteriaBuilder)
    }


    /**
     * 扩展信息
     */
    val extendJson: Field<String?> by lazy {
        Field(root.get("extendJson"), criteriaBuilder)
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
    val deleted: Field<Boolean> by lazy {
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
    fun spec(builder: PredicateBuilder<SCustomerMessage>): Predicate {
        return builder.build(this)
    }
}
