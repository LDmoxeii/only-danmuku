package com.edu.only4.danmuku.domain._share.meta.user.meta

import com.querydsl.core.types.OrderSpecifier
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import com.only4.cap4k.ddd.domain.repo.querydsl.QuerydslPredicate
import com.edu.only4.danmuku.domain._share.meta.Schema
import com.edu.only4.danmuku.domain.aggregates.user.AggUser
import com.edu.only4.danmuku.domain.aggregates.user.User
import com.edu.only4.danmuku.domain.aggregates.user.QUser
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePredicate
import org.springframework.data.jpa.domain.Specification

import jakarta.persistence.criteria.*

/**
 * 帐号;
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/10/08
 */
class SUser(
    private val root: Path<User>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {
            
        /**
         * ID
         */
        val id = "id"
           
        /**
         * 帐号类型
         */
        val type = "type"
           
        /**
         * 邮箱
         */
        val email = "email"
           
        /**
         * 密码
         */
        val password = "password"
           
        /**
         * 加入时间
         */
        val joinTime = "joinTime"
           
        /**
         * 最后登录时间
         */
        val lastLoginTime = "lastLoginTime"
           
        /**
         * 最后登录IP
         */
        val lastLoginIp = "lastLoginIp"
           
        /**
         * 0:禁用 1:正常
         */
        val status = "status"
           
        /**
         * 关联ID; 用户、管理员 = 0
         */
        val relatedId = "relatedId"
           
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
        fun specify(builder: Schema.PredicateBuilder<SUser>): Specification<User> {
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
        fun specify(builder: Schema.PredicateBuilder<SUser>, distinct: Boolean): Specification<User> {
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
            builder: Schema.PredicateBuilder<SUser>,
            vararg orderBuilders: Schema.OrderBuilder<SUser>,
        ): Specification<User> {
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
            builder: Schema.PredicateBuilder<SUser>,
            orderBuilders: List<Schema.OrderBuilder<SUser>>,
        ): Specification<User> {
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
            builder: Schema.PredicateBuilder<SUser>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SUser>,
        ): Specification<User> {
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
            builder: Schema.PredicateBuilder<SUser>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SUser>>,
        ): Specification<User> {
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
        fun specify(specifier: Schema.Specification<User, SUser>): Specification<User> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SUser(root, criteriaBuilder)
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
            selectBuilder: Schema.ExpressionBuilder<SUser, E>,
            predicateBuilder: Schema.PredicateBuilder<SUser>,
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
            subqueryConfigure: Schema.SubqueryConfigure<E, SUser>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(User::class.java)
            val schema = SUser(root, criteriaBuilder)
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
        fun predicateById(id: Any): AggregatePredicate<AggUser, User> {
            return JpaPredicate.byId(User::class.java, id).toAggregatePredicate(AggUser::class.java)
        }
    
        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): AggregatePredicate<AggUser, User> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(User::class.java, ids as Iterable<Any>).toAggregatePredicate(AggUser::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): AggregatePredicate<AggUser, User> {
            return JpaPredicate.byIds(User::class.java, ids.toList()).toAggregatePredicate(AggUser::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SUser>): AggregatePredicate<AggUser, User> {
            return JpaPredicate.bySpecification(User::class.java, specify(builder)).toAggregatePredicate(AggUser::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SUser>, distinct: Boolean): AggregatePredicate<AggUser, User> {
            return JpaPredicate.bySpecification(User::class.java, specify(builder, distinct)).toAggregatePredicate(AggUser::class.java)
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
            builder: Schema.PredicateBuilder<SUser>,
            orderBuilders: List<Schema.OrderBuilder<SUser>>,
        ): AggregatePredicate<AggUser, User> {
            return JpaPredicate.bySpecification(User::class.java, specify(builder, false, orderBuilders)).toAggregatePredicate(AggUser::class.java)
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
            builder: Schema.PredicateBuilder<SUser>,
            vararg orderBuilders: Schema.OrderBuilder<SUser>,
        ): AggregatePredicate<AggUser, User> {
            return JpaPredicate.bySpecification(User::class.java, specify(builder, false, *orderBuilders)).toAggregatePredicate(AggUser::class.java)
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
            builder: Schema.PredicateBuilder<SUser>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SUser>>,
        ): AggregatePredicate<AggUser, User> {
            return JpaPredicate.bySpecification(User::class.java, specify(builder, distinct, orderBuilders)).toAggregatePredicate(AggUser::class.java)
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
            builder: Schema.PredicateBuilder<SUser>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SUser>,
        ): AggregatePredicate<AggUser, User> {
            return JpaPredicate.bySpecification(User::class.java, specify(builder, distinct, *orderBuilders)).toAggregatePredicate(AggUser::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: Schema.Specification<User, SUser>): AggregatePredicate<AggUser, User> {
            return JpaPredicate.bySpecification(User::class.java, specify(specifier)).toAggregatePredicate(AggUser::class.java)
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
            filterBuilder: java.util.function.Function<QUser, com.querydsl.core.types.Predicate>,
            vararg orderSpecifierBuilders: java.util.function.Function<QUser, OrderSpecifier<*>>,
        ): AggregatePredicate<AggUser, User> {
            return QuerydslPredicate.of(User::class.java)
                .where(filterBuilder.apply(QUser.user))
                .orderBy(*orderSpecifierBuilders.map { it.apply(QUser.user) }.toTypedArray())
                .toAggregatePredicate(AggUser::class.java)
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
        ): AggregatePredicate<AggUser, User> {
            return QuerydslPredicate.of(User::class.java)
                .where(filter)
                .orderBy(*orderSpecifiers)
                .toAggregatePredicate(AggUser::class.java)
        }  
    }
    
    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<User> = root

    
    /**
     * ID
     * bigint
     */
    fun id(): Schema.Field<Long> {
        return Schema.Field(root.get(props.id), this.criteriaBuilder)
    }
    /**
     * 帐号类型
     * 0:UNKNOW:未知类型
     * 1:SYS_USER:系统管理员
     * tinyint(1)
     */
    fun type(): Schema.Field<com.edu.only4.danmuku.domain.aggregates.user.enums.UserType> {
        return Schema.Field(root.get(props.type), this.criteriaBuilder)
    }
    /**
     * 邮箱
     * varchar(150)
     */
    fun email(): Schema.Field<String> {
        return Schema.Field(root.get(props.email), this.criteriaBuilder)
    }
    /**
     * 密码
     * varchar(50)
     */
    fun password(): Schema.Field<String> {
        return Schema.Field(root.get(props.password), this.criteriaBuilder)
    }
    /**
     * 加入时间
     * bigint
     */
    fun joinTime(): Schema.Field<Long> {
        return Schema.Field(root.get(props.joinTime), this.criteriaBuilder)
    }
    /**
     * 最后登录时间
     * bigint
     */
    fun lastLoginTime(): Schema.Field<Long?> {
        return Schema.Field(root.get(props.lastLoginTime), this.criteriaBuilder)
    }
    /**
     * 最后登录IP
     * varchar(15)
     */
    fun lastLoginIp(): Schema.Field<String?> {
        return Schema.Field(root.get(props.lastLoginIp), this.criteriaBuilder)
    }
    /**
     * 0:禁用 1:正常
     * tinyint(1)
     */
    fun status(): Schema.Field<Boolean> {
        return Schema.Field(root.get(props.status), this.criteriaBuilder)
    }
    /**
     * 关联ID; 用户、管理员 = 0
     * int
     */
    fun relatedId(): Schema.Field<Int?> {
        return Schema.Field(root.get(props.relatedId), this.criteriaBuilder)
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
    fun spec(builder: Schema.PredicateBuilder<SUser>): Predicate {
        return builder.build(this)
    }
    
    
}