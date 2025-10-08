package com.edu.only4.danmuku.domain._share.meta.customer_profile.meta

import com.edu.only4.danmuku.domain._share.meta.Schema
import com.edu.only4.danmuku.domain.aggregates.customer_profile.AggCustomerProfile
import com.edu.only4.danmuku.domain.aggregates.customer_profile.CustomerProfile
import com.edu.only4.danmuku.domain.aggregates.customer_profile.QCustomerProfile
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePredicate
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import com.only4.cap4k.ddd.domain.repo.querydsl.QuerydslPredicate
import com.querydsl.core.types.OrderSpecifier
import jakarta.persistence.criteria.*
import org.springframework.data.jpa.domain.Specification

/**
 * 用户信息;
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/10/08
 */
class SCustomerProfile(
    private val root: Path<CustomerProfile>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {

        /**
         * ID
         */
        val id = "id"

        /**
         * 昵称
         */
        val nickName = "nickName"

        /**
         * 头像
         */
        val avatar = "avatar"

        /**
         * 邮箱
         */
        val email = "email"

        /**
         * 性别
         */
        val sex = "sex"

        /**
         * 出生日期
         */
        val birthday = "birthday"

        /**
         * 学校
         */
        val school = "school"

        /**
         * 个人简介
         */
        val personIntroduction = "personIntroduction"

        /**
         * 空间公告
         */
        val noticeInfo = "noticeInfo"

        /**
         * 硬币总数量
         */
        val totalCoinCount = "totalCoinCount"

        /**
         * 当前硬币数
         */
        val currentCoinCount = "currentCoinCount"

        /**
         * 主题
         */
        val theme = "theme"

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
        fun specify(builder: Schema.PredicateBuilder<SCustomerProfile>): Specification<CustomerProfile> {
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
        fun specify(builder: Schema.PredicateBuilder<SCustomerProfile>, distinct: Boolean): Specification<CustomerProfile> {
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
            builder: Schema.PredicateBuilder<SCustomerProfile>,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerProfile>,
        ): Specification<CustomerProfile> {
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
            builder: Schema.PredicateBuilder<SCustomerProfile>,
            orderBuilders: List<Schema.OrderBuilder<SCustomerProfile>>,
        ): Specification<CustomerProfile> {
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
            builder: Schema.PredicateBuilder<SCustomerProfile>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerProfile>,
        ): Specification<CustomerProfile> {
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
            builder: Schema.PredicateBuilder<SCustomerProfile>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SCustomerProfile>>,
        ): Specification<CustomerProfile> {
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
        fun specify(specifier: Schema.Specification<CustomerProfile, SCustomerProfile>): Specification<CustomerProfile> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SCustomerProfile(root, criteriaBuilder)
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
            selectBuilder: Schema.ExpressionBuilder<SCustomerProfile, E>,
            predicateBuilder: Schema.PredicateBuilder<SCustomerProfile>,
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
            subqueryConfigure: Schema.SubqueryConfigure<E, SCustomerProfile>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(CustomerProfile::class.java)
            val schema = SCustomerProfile(root, criteriaBuilder)
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
        fun predicateById(id: Any): AggregatePredicate<AggCustomerProfile, CustomerProfile> {
            return JpaPredicate.byId(CustomerProfile::class.java, id).toAggregatePredicate(AggCustomerProfile::class.java)
        }

        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): AggregatePredicate<AggCustomerProfile, CustomerProfile> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(CustomerProfile::class.java, ids as Iterable<Any>).toAggregatePredicate(AggCustomerProfile::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): AggregatePredicate<AggCustomerProfile, CustomerProfile> {
            return JpaPredicate.byIds(CustomerProfile::class.java, ids.toList()).toAggregatePredicate(AggCustomerProfile::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SCustomerProfile>): AggregatePredicate<AggCustomerProfile, CustomerProfile> {
            return JpaPredicate.bySpecification(CustomerProfile::class.java, specify(builder)).toAggregatePredicate(AggCustomerProfile::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SCustomerProfile>, distinct: Boolean): AggregatePredicate<AggCustomerProfile, CustomerProfile> {
            return JpaPredicate.bySpecification(CustomerProfile::class.java, specify(builder, distinct)).toAggregatePredicate(AggCustomerProfile::class.java)
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
            builder: Schema.PredicateBuilder<SCustomerProfile>,
            orderBuilders: List<Schema.OrderBuilder<SCustomerProfile>>,
        ): AggregatePredicate<AggCustomerProfile, CustomerProfile> {
            return JpaPredicate.bySpecification(CustomerProfile::class.java, specify(builder, false, orderBuilders)).toAggregatePredicate(AggCustomerProfile::class.java)
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
            builder: Schema.PredicateBuilder<SCustomerProfile>,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerProfile>,
        ): AggregatePredicate<AggCustomerProfile, CustomerProfile> {
            return JpaPredicate.bySpecification(CustomerProfile::class.java, specify(builder, false, *orderBuilders)).toAggregatePredicate(AggCustomerProfile::class.java)
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
            builder: Schema.PredicateBuilder<SCustomerProfile>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SCustomerProfile>>,
        ): AggregatePredicate<AggCustomerProfile, CustomerProfile> {
            return JpaPredicate.bySpecification(CustomerProfile::class.java, specify(builder, distinct, orderBuilders)).toAggregatePredicate(AggCustomerProfile::class.java)
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
            builder: Schema.PredicateBuilder<SCustomerProfile>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SCustomerProfile>,
        ): AggregatePredicate<AggCustomerProfile, CustomerProfile> {
            return JpaPredicate.bySpecification(CustomerProfile::class.java, specify(builder, distinct, *orderBuilders)).toAggregatePredicate(AggCustomerProfile::class.java)
        }

        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: Schema.Specification<CustomerProfile, SCustomerProfile>): AggregatePredicate<AggCustomerProfile, CustomerProfile> {
            return JpaPredicate.bySpecification(CustomerProfile::class.java, specify(specifier)).toAggregatePredicate(AggCustomerProfile::class.java)
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
            filterBuilder: java.util.function.Function<QCustomerProfile, com.querydsl.core.types.Predicate>,
            vararg orderSpecifierBuilders: java.util.function.Function<QCustomerProfile, OrderSpecifier<*>>,
        ): AggregatePredicate<AggCustomerProfile, CustomerProfile> {
            return QuerydslPredicate.of(CustomerProfile::class.java)
                .where(filterBuilder.apply(QCustomerProfile.customerProfile))
                .orderBy(*orderSpecifierBuilders.map { it.apply(QCustomerProfile.customerProfile) }.toTypedArray())
                .toAggregatePredicate(AggCustomerProfile::class.java)
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
        ): AggregatePredicate<AggCustomerProfile, CustomerProfile> {
            return QuerydslPredicate.of(CustomerProfile::class.java)
                .where(filter)
                .orderBy(*orderSpecifiers)
                .toAggregatePredicate(AggCustomerProfile::class.java)
        }
    }

    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<CustomerProfile> = root


    /**
     * ID
     * bigint
     */
    fun id(): Schema.Field<Long> {
        return Schema.Field(root.get(props.id), this.criteriaBuilder)
    }
    /**
     * 昵称
     * varchar(20)
     */
    fun nickName(): Schema.Field<String> {
        return Schema.Field(root.get(props.nickName), this.criteriaBuilder)
    }
    /**
     * 头像
     * varchar(100)
     */
    fun avatar(): Schema.Field<String?> {
        return Schema.Field(root.get(props.avatar), this.criteriaBuilder)
    }
    /**
     * 邮箱
     * varchar(150)
     */
    fun email(): Schema.Field<String> {
        return Schema.Field(root.get(props.email), this.criteriaBuilder)
    }
    /**
     * 性别
     * 0:UNKNOWN:未知
     * 1:FEMALE:女
     * 2:MALE:男
     * tinyint(1)
     */
    fun sex(): Schema.Field<com.edu.only4.danmuku.domain.aggregates.customer_profile.enums.SexType> {
        return Schema.Field(root.get(props.sex), this.criteriaBuilder)
    }
    /**
     * 出生日期
     * varchar(10)
     */
    fun birthday(): Schema.Field<String?> {
        return Schema.Field(root.get(props.birthday), this.criteriaBuilder)
    }
    /**
     * 学校
     * varchar(150)
     */
    fun school(): Schema.Field<String?> {
        return Schema.Field(root.get(props.school), this.criteriaBuilder)
    }
    /**
     * 个人简介
     * varchar(200)
     */
    fun personIntroduction(): Schema.Field<String?> {
        return Schema.Field(root.get(props.personIntroduction), this.criteriaBuilder)
    }
    /**
     * 空间公告
     * varchar(300)
     */
    fun noticeInfo(): Schema.Field<String?> {
        return Schema.Field(root.get(props.noticeInfo), this.criteriaBuilder)
    }
    /**
     * 硬币总数量
     * int
     */
    fun totalCoinCount(): Schema.Field<Int> {
        return Schema.Field(root.get(props.totalCoinCount), this.criteriaBuilder)
    }
    /**
     * 当前硬币数
     * int
     */
    fun currentCoinCount(): Schema.Field<Int> {
        return Schema.Field(root.get(props.currentCoinCount), this.criteriaBuilder)
    }
    /**
     * 主题
     * 0:UNKNOW:未知主题
     * 1:LIGHT:浅色主题
     * 2:DARK:深色主题
     * 3:SYSTEM:跟随系统
     * tinyint(1)
     */
    fun theme(): Schema.Field<com.edu.only4.danmuku.domain.aggregates.customer_profile.enums.ThemeType> {
        return Schema.Field(root.get(props.theme), this.criteriaBuilder)
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
    fun spec(builder: Schema.PredicateBuilder<SCustomerProfile>): Predicate {
        return builder.build(this)
    }


}
