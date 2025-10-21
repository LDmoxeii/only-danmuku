package edu.only4.danmuku.domain._share.meta

import jakarta.persistence.criteria.*

import org.hibernate.query.NullPrecedence
import org.hibernate.query.SortDirection
import org.hibernate.query.sqm.tree.domain.SqmBasicValuedSimplePath
import org.hibernate.query.sqm.tree.select.SqmSortSpecification

/**
 * 实体结构基类
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */

// ============ 类型定义 ============

/**
 * Schema Specification 接口（避免与 Spring Data JPA Specification 冲突）
 */
fun interface SchemaSpecification<E, S> {
    fun toPredicate(schema: S, criteriaQuery: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder): Predicate?
}

/**
 * 子查询配置接口
 */
fun interface SubqueryConfigure<E, S> {
    fun configure(subquery: Subquery<E>, schema: S)
}

/**
 * 表达式构建器
 */
fun interface ExpressionBuilder<S, T> {
    fun build(schema: S): Expression<T>
}

/**
 * 断言构建器
 */
fun interface PredicateBuilder<S> {
    fun build(schema: S): Predicate
}

/**
 * 排序构建器
 */
fun interface OrderBuilder<S> {
    fun build(schema: S): Order
}

/**
 * JOIN 类型枚举
 */
enum class JoinType {
    INNER,
    LEFT,
    RIGHT
    ;

    fun toJpaJoinType(): jakarta.persistence.criteria.JoinType {
        return when (this) {
            INNER -> jakarta.persistence.criteria.JoinType.INNER
            LEFT -> jakarta.persistence.criteria.JoinType.LEFT
            RIGHT -> jakarta.persistence.criteria.JoinType.RIGHT
        }
    }
}

/**
 * 字段类 - 类型安全的查询字段
 *
 * @param <T> 字段类型
 */
@Suppress("UNCHECKED_CAST")
class Field<T> {
    private val path: Path<T>?
    private val criteriaBuilder: CriteriaBuilder?
    private val name: String?

    constructor(path: Path<T>, criteriaBuilder: CriteriaBuilder) {
        this.path = path
        this.criteriaBuilder = criteriaBuilder
        this.name = if (path is SqmBasicValuedSimplePath<*>) {
            path.navigablePath.localName
        } else null
    }

    constructor(name: String) {
        this.name = name
        this.path = null
        this.criteriaBuilder = null
    }

    protected fun _criteriaBuilder(): CriteriaBuilder? = criteriaBuilder

    fun path(): Path<T>? = path

    override fun toString(): String = name ?: ""

    fun asc(): Order =
        SqmSortSpecification(path as SqmBasicValuedSimplePath<T>, SortDirection.ASCENDING, NullPrecedence.NONE)

    fun desc(): Order =
        SqmSortSpecification(path as SqmBasicValuedSimplePath<T>, SortDirection.DESCENDING, NullPrecedence.NONE)

    fun isTrue(): Predicate = criteriaBuilder!!.isTrue(path as Expression<Boolean>)

    fun isFalse(): Predicate = criteriaBuilder!!.isFalse(path as Expression<Boolean>)

    fun isEmpty(): Predicate = criteriaBuilder!!.isEmpty(path as Expression<Collection<*>>)

    fun isNotEmpty(): Predicate = criteriaBuilder!!.isNotEmpty(path as Expression<Collection<*>>)

    fun equal(value: Any?): Predicate = criteriaBuilder!!.equal(path, value)

    fun equal(value: Expression<*>): Predicate = criteriaBuilder!!.equal(path, value)

    fun notEqual(value: Any?): Predicate = criteriaBuilder!!.notEqual(path, value)

    fun notEqual(value: Expression<*>): Predicate = criteriaBuilder!!.notEqual(path, value)

    fun isNull(): Predicate = criteriaBuilder!!.isNull(path)

    fun isNotNull(): Predicate = criteriaBuilder!!.isNotNull(path)

    fun <Y : Comparable<Y>> greaterThan(value: Y): Predicate =
        criteriaBuilder!!.greaterThan(path as Expression<Y>, value)

    fun <Y : Comparable<Y>> greaterThan(value: Expression<out Y>): Predicate =
        criteriaBuilder!!.greaterThan(path as Expression<Y>, value)

    fun <Y : Comparable<Y>> greaterThanOrEqualTo(value: Y): Predicate =
        criteriaBuilder!!.greaterThanOrEqualTo(path as Expression<Y>, value)

    fun <Y : Comparable<Y>> greaterThanOrEqualTo(value: Expression<out Y>): Predicate =
        criteriaBuilder!!.greaterThanOrEqualTo(path as Expression<Y>, value)

    fun <Y : Comparable<Y>> lessThan(value: Y): Predicate =
        criteriaBuilder!!.lessThan(path as Expression<Y>, value)

    fun <Y : Comparable<Y>> lessThan(value: Expression<out Y>): Predicate =
        criteriaBuilder!!.lessThan(path as Expression<Y>, value)

    fun <Y : Comparable<Y>> lessThanOrEqualTo(value: Y): Predicate =
        criteriaBuilder!!.lessThanOrEqualTo(path as Expression<Y>, value)

    fun <Y : Comparable<Y>> lessThanOrEqualTo(value: Expression<out Y>): Predicate =
        criteriaBuilder!!.lessThanOrEqualTo(path as Expression<Y>, value)

    fun <Y : Comparable<Y>> between(value1: Y, value2: Y): Predicate =
        criteriaBuilder!!.between(path as Expression<Y>, value1, value2)

    fun <Y : Comparable<Y>> between(value1: Expression<out Y>, value2: Expression<out Y>): Predicate =
        criteriaBuilder!!.between(path as Expression<Y>, value1, value2)

    fun `in`(vararg values: Any?): Predicate = `in`(listOf(*values))

    fun `in`(values: Collection<*>): Predicate {
        val predicate = criteriaBuilder!!.`in`(path)
        values.forEach { value ->
            @Suppress("UNCHECKED_CAST")
            predicate.value(value as T)
        }
        return predicate
    }

    fun `in`(vararg expressions: Expression<*>): Predicate {
        val predicate = criteriaBuilder!!.`in`(path)
        expressions.forEach { expression ->
            @Suppress("UNCHECKED_CAST")
            predicate.value(expression as Expression<out T>)
        }
        return predicate
    }

    fun notIn(vararg values: Any?): Predicate = notIn(listOf(*values))

    fun notIn(values: Collection<*>): Predicate = criteriaBuilder!!.not(`in`(values))

    fun notIn(vararg expressions: Expression<*>): Predicate = criteriaBuilder!!.not(`in`(*expressions))

    fun like(value: Expression<String>): Predicate = criteriaBuilder!!.like(path as Expression<String>, value)

    fun notLike(value: Expression<String>): Predicate = criteriaBuilder!!.notLike(path as Expression<String>, value)

    // ============ Kotlin DSL 风格中缀函数 ============

    infix fun eq(value: Any?): Predicate = equal(value)

    infix fun neq(value: Any?): Predicate = notEqual(value)

    infix fun <Y : Comparable<Y>> gt(value: Y): Predicate = greaterThan(value)

    infix fun <Y : Comparable<Y>> ge(value: Y): Predicate = greaterThanOrEqualTo(value)

    infix fun <Y : Comparable<Y>> lt(value: Y): Predicate = lessThan(value)

    infix fun <Y : Comparable<Y>> le(value: Y): Predicate = lessThanOrEqualTo(value)

    infix fun like(value: String): Predicate = criteriaBuilder!!.like(path as Expression<String>, value)

    infix fun notLike(value: String): Predicate = criteriaBuilder!!.notLike(path as Expression<String>, value)

    // 可空值支持 - 类似 Jimmer 的 eq?
    infix fun `eq?`(value: Any?): Predicate? = if (value == null) null else equal(value)

    infix fun `neq?`(value: Any?): Predicate? = if (value == null) null else notEqual(value)

    infix fun `like?`(value: String?): Predicate? = if (value == null) null else like(value)

    infix fun `notLike?`(value: String?): Predicate? = if (value == null) null else notLike(value)

    infix fun <Y : Comparable<Y>> `gt?`(value: Y?): Predicate? = if (value == null) null else greaterThan(value)

    infix fun <Y : Comparable<Y>> `ge?`(value: Y?): Predicate? = if (value == null) null else greaterThanOrEqualTo(value)

    infix fun <Y : Comparable<Y>> `lt?`(value: Y?): Predicate? = if (value == null) null else lessThan(value)

    infix fun <Y : Comparable<Y>> `le?`(value: Y?): Predicate? = if (value == null) null else lessThanOrEqualTo(value)

    infix fun `in?`(values: Collection<*>?): Predicate? = if (values.isNullOrEmpty()) null else `in`(values)

    infix fun `notIn?`(values: Collection<*>?): Predicate? = if (values.isNullOrEmpty()) null else notIn(values)
}

// ============ Predicate 顶层扩展函数（仿照 Jimmer 风格）============

/**
 * AND 组合多个 Predicate（自动过滤 null）
 * 类似 Jimmer 的 and() 函数
 */
fun and(vararg predicates: Predicate?): Predicate? =
    predicates.filterNotNull().let {
        when (it.size) {
            0 -> null
            1 -> it[0]
            else -> {
                val cb = getCriteriaBuilderFromPredicate(it[0])
                cb.and(*it.toTypedArray())
            }
        }
    }

/**
 * OR 组合多个 Predicate（自动过滤 null）
 * 类似 Jimmer 的 or() 函数
 */
fun or(vararg predicates: Predicate?): Predicate? =
    predicates.filterNotNull().let {
        when (it.size) {
            0 -> null
            1 -> it[0]
            else -> {
                val cb = getCriteriaBuilderFromPredicate(it[0])
                cb.or(*it.toTypedArray())
            }
        }
    }

/**
 * Predicate 中缀扩展：AND 操作
 * 使用示例：(predicate1) and (predicate2)
 */
infix fun Predicate.and(other: Predicate): Predicate {
    val cb = getCriteriaBuilderFromPredicate(this)
    return cb.and(this, other)
}

/**
 * Predicate 中缀扩展：OR 操作
 * 使用示例：(predicate1) or (predicate2)
 */
infix fun Predicate.or(other: Predicate): Predicate {
    val cb = getCriteriaBuilderFromPredicate(this)
    return cb.or(this, other)
}

/**
 * Predicate NOT 操作
 */
fun Predicate.not(): Predicate {
    val cb = getCriteriaBuilderFromPredicate(this)
    return cb.not(this)
}

/**
 * 从 Predicate 实例中获取 CriteriaBuilder
 * 利用 Hibernate 的内部实现
 */
private fun getCriteriaBuilderFromPredicate(predicate: Predicate): CriteriaBuilder {
    return when (predicate) {
        is org.hibernate.query.sqm.tree.predicate.SqmPredicate -> {
            predicate.nodeBuilder()
        }
        else -> {
            // 尝试反射获取
            try {
                val method = predicate.javaClass.getMethod("criteriaBuilder")
                method.invoke(predicate) as CriteriaBuilder
            } catch (e: Exception) {
                throw IllegalStateException("无法从 Predicate 获取 CriteriaBuilder: ${predicate.javaClass}", e)
            }
        }
    }
}
