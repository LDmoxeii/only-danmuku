package com.edu.only4.danmuku.domain._share.meta.category_info.meta

import com.querydsl.core.types.OrderSpecifier
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import com.only4.cap4k.ddd.domain.repo.querydsl.QuerydslPredicate
import com.edu.only4.danmuku.domain._share.meta.Schema
import com.edu.only4.danmuku.domain.aggregates.category_info.AggCategoryInfo
import com.edu.only4.danmuku.domain.aggregates.category_info.CategoryInfo
import com.edu.only4.danmuku.domain.aggregates.category_info.QCategoryInfo
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePredicate
import org.springframework.data.jpa.domain.Specification

import jakarta.persistence.criteria.*

/**
 * 分类信息;
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/09/27
 */
class SCategoryInfo(
    private val root: Path<CategoryInfo>,
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
        fun specify(builder: Schema.PredicateBuilder<SCategoryInfo>): Specification<CategoryInfo> {
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
        fun specify(builder: Schema.PredicateBuilder<SCategoryInfo>, distinct: Boolean): Specification<CategoryInfo> {
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
            builder: Schema.PredicateBuilder<SCategoryInfo>,
            vararg orderBuilders: Schema.OrderBuilder<SCategoryInfo>,
        ): Specification<CategoryInfo> {
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
            builder: Schema.PredicateBuilder<SCategoryInfo>,
            orderBuilders: List<Schema.OrderBuilder<SCategoryInfo>>,
        ): Specification<CategoryInfo> {
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
            builder: Schema.PredicateBuilder<SCategoryInfo>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SCategoryInfo>,
        ): Specification<CategoryInfo> {
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
            builder: Schema.PredicateBuilder<SCategoryInfo>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SCategoryInfo>>,
        ): Specification<CategoryInfo> {
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
        fun specify(specifier: Schema.Specification<CategoryInfo, SCategoryInfo>): Specification<CategoryInfo> {
            return Specification { root, criteriaQuery, criteriaBuilder ->
                val schema = SCategoryInfo(root, criteriaBuilder)
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
            selectBuilder: Schema.ExpressionBuilder<SCategoryInfo, E>,
            predicateBuilder: Schema.PredicateBuilder<SCategoryInfo>,
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
            subqueryConfigure: Schema.SubqueryConfigure<E, SCategoryInfo>,
            criteriaBuilder: CriteriaBuilder,
            criteriaQuery: CriteriaQuery<*>,
        ): Subquery<E> {
            val sq = criteriaQuery.subquery(resultClass)
            val root = sq.from(CategoryInfo::class.java)
            val schema = SCategoryInfo(root, criteriaBuilder)
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
        fun predicateById(id: Any): AggregatePredicate<AggCategoryInfo, CategoryInfo> {
            return JpaPredicate.byId(CategoryInfo::class.java, id).toAggregatePredicate(AggCategoryInfo::class.java)
        }
    
        /**
        * 构建查询条件
        *
        * @param ids 主键
        * @return
        */
        @JvmStatic
        fun predicateByIds(ids: Iterable<*>): AggregatePredicate<AggCategoryInfo, CategoryInfo> {
            @Suppress("UNCHECKED_CAST")
            return JpaPredicate.byIds(CategoryInfo::class.java, ids as Iterable<Any>).toAggregatePredicate(AggCategoryInfo::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param ids 主键
         * @return
         */
        @JvmStatic
        fun predicateByIds(vararg ids: Any): AggregatePredicate<AggCategoryInfo, CategoryInfo> {
            return JpaPredicate.byIds(CategoryInfo::class.java, ids.toList()).toAggregatePredicate(AggCategoryInfo::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param builder 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SCategoryInfo>): AggregatePredicate<AggCategoryInfo, CategoryInfo> {
            return JpaPredicate.bySpecification(CategoryInfo::class.java, specify(builder)).toAggregatePredicate(AggCategoryInfo::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param builder  查询条件构造器
         * @param distinct 是否去重
         * @return
         */
        @JvmStatic
        fun predicate(builder: Schema.PredicateBuilder<SCategoryInfo>, distinct: Boolean): AggregatePredicate<AggCategoryInfo, CategoryInfo> {
            return JpaPredicate.bySpecification(CategoryInfo::class.java, specify(builder, distinct)).toAggregatePredicate(AggCategoryInfo::class.java)
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
            builder: Schema.PredicateBuilder<SCategoryInfo>,
            orderBuilders: List<Schema.OrderBuilder<SCategoryInfo>>,
        ): AggregatePredicate<AggCategoryInfo, CategoryInfo> {
            return JpaPredicate.bySpecification(CategoryInfo::class.java, specify(builder, false, orderBuilders)).toAggregatePredicate(AggCategoryInfo::class.java)
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
            builder: Schema.PredicateBuilder<SCategoryInfo>,
            vararg orderBuilders: Schema.OrderBuilder<SCategoryInfo>,
        ): AggregatePredicate<AggCategoryInfo, CategoryInfo> {
            return JpaPredicate.bySpecification(CategoryInfo::class.java, specify(builder, false, *orderBuilders)).toAggregatePredicate(AggCategoryInfo::class.java)
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
            builder: Schema.PredicateBuilder<SCategoryInfo>,
            distinct: Boolean,
            orderBuilders: List<Schema.OrderBuilder<SCategoryInfo>>,
        ): AggregatePredicate<AggCategoryInfo, CategoryInfo> {
            return JpaPredicate.bySpecification(CategoryInfo::class.java, specify(builder, distinct, orderBuilders)).toAggregatePredicate(AggCategoryInfo::class.java)
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
            builder: Schema.PredicateBuilder<SCategoryInfo>,
            distinct: Boolean,
            vararg orderBuilders: Schema.OrderBuilder<SCategoryInfo>,
        ): AggregatePredicate<AggCategoryInfo, CategoryInfo> {
            return JpaPredicate.bySpecification(CategoryInfo::class.java, specify(builder, distinct, *orderBuilders)).toAggregatePredicate(AggCategoryInfo::class.java)
        }
    
        /**
         * 构建查询条件
         *
         * @param specifier 查询条件构造器
         * @return
         */
        @JvmStatic
        fun predicate(specifier: Schema.Specification<CategoryInfo, SCategoryInfo>): AggregatePredicate<AggCategoryInfo, CategoryInfo> {
            return JpaPredicate.bySpecification(CategoryInfo::class.java, specify(specifier)).toAggregatePredicate(AggCategoryInfo::class.java)
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
            filterBuilder: java.util.function.Function<QCategoryInfo, com.querydsl.core.types.Predicate>,
            vararg orderSpecifierBuilders: java.util.function.Function<QCategoryInfo, OrderSpecifier<*>>,
        ): AggregatePredicate<AggCategoryInfo, CategoryInfo> {
            return QuerydslPredicate.of(CategoryInfo::class.java)
                .where(filterBuilder.apply(QCategoryInfo.categoryInfo))
                .orderBy(*orderSpecifierBuilders.map { it.apply(QCategoryInfo.categoryInfo) }.toTypedArray())
                .toAggregatePredicate(AggCategoryInfo::class.java)
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
        ): AggregatePredicate<AggCategoryInfo, CategoryInfo> {
            return QuerydslPredicate.of(CategoryInfo::class.java)
                .where(filter)
                .orderBy(*orderSpecifiers)
                .toAggregatePredicate(AggCategoryInfo::class.java)
        }  
    }
    
    fun _criteriaBuilder(): CriteriaBuilder = criteriaBuilder

    fun _root(): Path<CategoryInfo> = root

    
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
    fun spec(builder: Schema.PredicateBuilder<SCategoryInfo>): Predicate {
        return builder.build(this)
    }
    
    
}