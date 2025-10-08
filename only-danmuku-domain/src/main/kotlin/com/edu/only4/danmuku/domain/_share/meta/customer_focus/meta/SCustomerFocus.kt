package com.edu.only4.danmuku.domain._share.meta.customer_focus.meta

import com.querydsl.core.types.OrderSpecifier
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import com.only4.cap4k.ddd.domain.repo.querydsl.QuerydslPredicate
import com.edu.only4.danmuku.domain._share.meta.Schema
import com.edu.only4.danmuku.domain.aggregates.customer_focus.AggCustomerFocus
import com.edu.only4.danmuku.domain.aggregates.customer_focus.CustomerFocus
import com.edu.only4.danmuku.domain.aggregates.customer_focus.QCustomerFocus
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePredicate
import org.springframework.data.jpa.domain.Specification

import jakarta.persistence.criteria.*

/**
 * 用户关注;
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/10/08
 */
class SCustomerFocus(
    private val root: Path<CustomerFocus>,
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
         * 用户ID
         */
        val focusCustomerId = "focusCustomerId"
           
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
        fun specify(builder: Schema.PredicateBuilder<SCustomerFocus>): Specification<CustomerFocus> {
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
        fun specify(builder: Schema.PredicateBuilder<SCustomerFocus>, distinct: Boolean): Specification<CustomerFocus> {
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
            builder: Schema.PredicateBuilder<SCustomerFocus>,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerFocus>,
        ): Specification<CustomerFocus> {
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
            builder: Schema.PredicateBuilder<SCustomerFocus>,
            orderBuilders: List<Schema.OrderBuilder<SCustomerFocus>>,
        ): Specification<CustomerFocus> {
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
            builder: Schema.PredicateBuilder<SCustomerFocus>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerFocus>,
        ): Specification<CustomerFocus> {
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
            builder: Schema.PredicateBuilder<SCustomerFocus>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SCustomerFocus>>,
        ): Specification<CustomerFocus> {
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
        fun specify(specifier: Schema.Specification<CustomerFocus, SCustomerFocus>): Specification<CustomerFocus> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SCustomerFocus(root, criteriaBuilder)
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
            selectBuilder: Schema.ExpressionBuilder<SCustomerFocus, E>,
            predicateBuilder: Schema.PredicateBuilder<SCustomerFocus>,
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
            subqueryConfigure: Schema.SubqueryConfigure<E, SCustomerFocus>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(CustomerFocus::class.java)
            val schema = SCustomerFocus(root, criteriaBuilder)
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
        fun predicateById(id: Any): AggregatePredicate<AggCustomerFocus, CustomerFocus> {
            return JpaPredicate.byId(CustomerFocus::class.java, id).toAggregatePredicate(AggCustomerFocus::class.java)
        }
    
        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): AggregatePredicate<AggCustomerFocus, CustomerFocus> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(CustomerFocus::class.java, ids as Iterable<Any>).toAggregatePredicate(AggCustomerFocus::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): AggregatePredicate<AggCustomerFocus, CustomerFocus> {
            return JpaPredicate.byIds(CustomerFocus::class.java, ids.toList()).toAggregatePredicate(AggCustomerFocus::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SCustomerFocus>): AggregatePredicate<AggCustomerFocus, CustomerFocus> {
            return JpaPredicate.bySpecification(CustomerFocus::class.java, specify(builder)).toAggregatePredicate(AggCustomerFocus::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SCustomerFocus>, distinct: Boolean): AggregatePredicate<AggCustomerFocus, CustomerFocus> {
            return JpaPredicate.bySpecification(CustomerFocus::class.java, specify(builder, distinct)).toAggregatePredicate(AggCustomerFocus::class.java)
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
            builder: Schema.PredicateBuilder<SCustomerFocus>,
            orderBuilders: List<Schema.OrderBuilder<SCustomerFocus>>,
        ): AggregatePredicate<AggCustomerFocus, CustomerFocus> {
            return JpaPredicate.bySpecification(CustomerFocus::class.java, specify(builder, false, orderBuilders)).toAggregatePredicate(AggCustomerFocus::class.java)
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
            builder: Schema.PredicateBuilder<SCustomerFocus>,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerFocus>,
        ): AggregatePredicate<AggCustomerFocus, CustomerFocus> {
            return JpaPredicate.bySpecification(CustomerFocus::class.java, specify(builder, false, *orderBuilders)).toAggregatePredicate(AggCustomerFocus::class.java)
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
            builder: Schema.PredicateBuilder<SCustomerFocus>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SCustomerFocus>>,
        ): AggregatePredicate<AggCustomerFocus, CustomerFocus> {
            return JpaPredicate.bySpecification(CustomerFocus::class.java, specify(builder, distinct, orderBuilders)).toAggregatePredicate(AggCustomerFocus::class.java)
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
            builder: Schema.PredicateBuilder<SCustomerFocus>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerFocus>,
        ): AggregatePredicate<AggCustomerFocus, CustomerFocus> {
            return JpaPredicate.bySpecification(CustomerFocus::class.java, specify(builder, distinct, *orderBuilders)).toAggregatePredicate(AggCustomerFocus::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: Schema.Specification<CustomerFocus, SCustomerFocus>): AggregatePredicate<AggCustomerFocus, CustomerFocus> {
            return JpaPredicate.bySpecification(CustomerFocus::class.java, specify(specifier)).toAggregatePredicate(AggCustomerFocus::class.java)
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
            filterBuilder: java.util.function.Function<QCustomerFocus, com.querydsl.core.types.Predicate>,
            vararg orderSpecifierBuilders: java.util.function.Function<QCustomerFocus, OrderSpecifier<*>>,
        ): AggregatePredicate<AggCustomerFocus, CustomerFocus> {
            return QuerydslPredicate.of(CustomerFocus::class.java)
                .where(filterBuilder.apply(QCustomerFocus.customerFocus))
                .orderBy(*orderSpecifierBuilders.map { it.apply(QCustomerFocus.customerFocus) }.toTypedArray())
                .toAggregatePredicate(AggCustomerFocus::class.java)
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
        ): AggregatePredicate<AggCustomerFocus, CustomerFocus> {
            return QuerydslPredicate.of(CustomerFocus::class.java)
                .where(filter)
                .orderBy(*orderSpecifiers)
                .toAggregatePredicate(AggCustomerFocus::class.java)
        }  
    }
    
    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<CustomerFocus> = root

    
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
     * 用户ID
     * varchar(10)
     */
    fun focusCustomerId(): Schema.Field<String> {
        return Schema.Field(root.get(props.focusCustomerId), this.criteriaBuilder)
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
    fun spec(builder: Schema.PredicateBuilder<SCustomerFocus>): Predicate {
        return builder.build(this)
    }
    
    
}