package com.edu.only4.danmuku.domain._share.meta.category.meta

import com.querydsl.core.types.OrderSpecifier
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import com.only4.cap4k.ddd.domain.repo.querydsl.QuerydslPredicate
import com.edu.only4.danmuku.domain._share.meta.Schema
import com.edu.only4.danmuku.domain.aggregates.category.AggCategory
import com.edu.only4.danmuku.domain.aggregates.category.Category
import com.edu.only4.danmuku.domain.aggregates.category.QCategory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePredicate
import org.springframework.data.jpa.domain.Specification

import jakarta.persistence.criteria.*

/**
 * 分类信息;
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/10/08
 */
class SCategory(
    private val root: Path<Category>,
    private val criteriaBuilder: CriteriaBuilder,
) {
    class PROPERTY_NAMES {
            
        /**
         * ID
         */
        val id = "id"
           
        /**
         * 父级ID
         */
        val parentId = "parentId"
           
        /**
         * 路径
         */
        val nodePath = "nodePath"
           
        /**
         * 排序号
         */
        val sort = "sort"
           
        /**
         * 编码
         */
        val code = "code"
           
        /**
         * 名称
         */
        val name = "name"
           
        /**
         * 图标
         */
        val icon = "icon"
           
        /**
         * 背景图
         */
        val background = "background"
           
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
        fun specify(builder: Schema.PredicateBuilder<SCategory>): Specification<Category> {
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
        fun specify(builder: Schema.PredicateBuilder<SCategory>, distinct: Boolean): Specification<Category> {
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
            builder: Schema.PredicateBuilder<SCategory>,
            vararg orderBuilders: Schema.OrderBuilder<SCategory>,
        ): Specification<Category> {
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
            builder: Schema.PredicateBuilder<SCategory>,
            orderBuilders: List<Schema.OrderBuilder<SCategory>>,
        ): Specification<Category> {
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
            builder: Schema.PredicateBuilder<SCategory>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SCategory>,
        ): Specification<Category> {
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
            builder: Schema.PredicateBuilder<SCategory>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SCategory>>,
        ): Specification<Category> {
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
        fun specify(specifier: Schema.Specification<Category, SCategory>): Specification<Category> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SCategory(root, criteriaBuilder)
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
            selectBuilder: Schema.ExpressionBuilder<SCategory, E>,
            predicateBuilder: Schema.PredicateBuilder<SCategory>,
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
            subqueryConfigure: Schema.SubqueryConfigure<E, SCategory>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(Category::class.java)
            val schema = SCategory(root, criteriaBuilder)
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
        fun predicateById(id: Any): AggregatePredicate<AggCategory, Category> {
            return JpaPredicate.byId(Category::class.java, id).toAggregatePredicate(AggCategory::class.java)
        }
    
        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): AggregatePredicate<AggCategory, Category> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(Category::class.java, ids as Iterable<Any>).toAggregatePredicate(AggCategory::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): AggregatePredicate<AggCategory, Category> {
            return JpaPredicate.byIds(Category::class.java, ids.toList()).toAggregatePredicate(AggCategory::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SCategory>): AggregatePredicate<AggCategory, Category> {
            return JpaPredicate.bySpecification(Category::class.java, specify(builder)).toAggregatePredicate(AggCategory::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SCategory>, distinct: Boolean): AggregatePredicate<AggCategory, Category> {
            return JpaPredicate.bySpecification(Category::class.java, specify(builder, distinct)).toAggregatePredicate(AggCategory::class.java)
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
            builder: Schema.PredicateBuilder<SCategory>,
            orderBuilders: List<Schema.OrderBuilder<SCategory>>,
        ): AggregatePredicate<AggCategory, Category> {
            return JpaPredicate.bySpecification(Category::class.java, specify(builder, false, orderBuilders)).toAggregatePredicate(AggCategory::class.java)
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
            builder: Schema.PredicateBuilder<SCategory>,
            vararg orderBuilders: Schema.OrderBuilder<SCategory>,
        ): AggregatePredicate<AggCategory, Category> {
            return JpaPredicate.bySpecification(Category::class.java, specify(builder, false, *orderBuilders)).toAggregatePredicate(AggCategory::class.java)
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
            builder: Schema.PredicateBuilder<SCategory>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SCategory>>,
        ): AggregatePredicate<AggCategory, Category> {
            return JpaPredicate.bySpecification(Category::class.java, specify(builder, distinct, orderBuilders)).toAggregatePredicate(AggCategory::class.java)
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
            builder: Schema.PredicateBuilder<SCategory>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SCategory>,
        ): AggregatePredicate<AggCategory, Category> {
            return JpaPredicate.bySpecification(Category::class.java, specify(builder, distinct, *orderBuilders)).toAggregatePredicate(AggCategory::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: Schema.Specification<Category, SCategory>): AggregatePredicate<AggCategory, Category> {
            return JpaPredicate.bySpecification(Category::class.java, specify(specifier)).toAggregatePredicate(AggCategory::class.java)
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
            filterBuilder: java.util.function.Function<QCategory, com.querydsl.core.types.Predicate>,
            vararg orderSpecifierBuilders: java.util.function.Function<QCategory, OrderSpecifier<*>>,
        ): AggregatePredicate<AggCategory, Category> {
            return QuerydslPredicate.of(Category::class.java)
                .where(filterBuilder.apply(QCategory.category))
                .orderBy(*orderSpecifierBuilders.map { it.apply(QCategory.category) }.toTypedArray())
                .toAggregatePredicate(AggCategory::class.java)
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
        ): AggregatePredicate<AggCategory, Category> {
            return QuerydslPredicate.of(Category::class.java)
                .where(filter)
                .orderBy(*orderSpecifiers)
                .toAggregatePredicate(AggCategory::class.java)
        }  
    }
    
    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<Category> = root

    
    /**
     * ID
     * bigint
     */
    fun id(): Schema.Field<Long> {
        return Schema.Field(root.get(props.id), this.criteriaBuilder)
    }
    /**
     * 父级ID
     * bigint
     */
    fun parentId(): Schema.Field<Long> {
        return Schema.Field(root.get(props.parentId), this.criteriaBuilder)
    }
    /**
     * 路径
     * varchar(255)
     */
    fun nodePath(): Schema.Field<String> {
        return Schema.Field(root.get(props.nodePath), this.criteriaBuilder)
    }
    /**
     * 排序号
     * tinyint
     */
    fun sort(): Schema.Field<Byte> {
        return Schema.Field(root.get(props.sort), this.criteriaBuilder)
    }
    /**
     * 编码
     * varchar(30)
     */
    fun code(): Schema.Field<String> {
        return Schema.Field(root.get(props.code), this.criteriaBuilder)
    }
    /**
     * 名称
     * varchar(30)
     */
    fun name(): Schema.Field<String> {
        return Schema.Field(root.get(props.name), this.criteriaBuilder)
    }
    /**
     * 图标
     * varchar(50)
     */
    fun icon(): Schema.Field<String?> {
        return Schema.Field(root.get(props.icon), this.criteriaBuilder)
    }
    /**
     * 背景图
     * varchar(50)
     */
    fun background(): Schema.Field<String?> {
        return Schema.Field(root.get(props.background), this.criteriaBuilder)
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
    fun spec(builder: Schema.PredicateBuilder<SCategory>): Predicate {
        return builder.build(this)
    }
    
    
}