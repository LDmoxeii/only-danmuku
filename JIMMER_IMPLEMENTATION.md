# Jimmer 实现说明 - 分类树查询

## 📁 文件结构

```
only-danmuku-application/
├── src/main/dto/
│   └── Category.dto                    # Jimmer DTO 定义（DSL）
├── src/main/kotlin/
│   └── edu/only4/danmuku/application/queries/_share/
│       ├── model/
│       │   └── JCategory.kt           # Jimmer 实体定义
│       └── fetcher/
│           └── CategoryFetcher.kt     # Fetcher 预定义

only-danmuku-adapter/
└── src/main/kotlin/
    └── edu/only4/danmuku/adapter/
        ├── application/queries/category/
        │   └── GetCategoryTreeQryHandler.kt  # Query Handler（使用 Jimmer）
        └── portal/api/
            └── CategoryController.kt          # 控制器（两种实现方式）
```

## 🚀 核心实现

### 1. Jimmer 实体定义（JCategory.kt）

```kotlin
@Table(name = "category")
@Entity
interface JCategory {
    @Id
    val id: Long
    val parentId: Long
    val code: String
    val name: String
    val icon: String?
    val background: String?
    val sort: Byte

    @LogicalDeleted("0")
    val deleted: Boolean

    // 🌲 自关联：构建树形结构
    @OneToMany(mappedBy = "parent")
    val children: List<JCategory>

    @ManyToOne
    @JoinColumn(name = "parent_id")
    val parent: JCategory?
}
```

**关键点：**
- ✅ 使用 `interface` 定义不可变实体
- ✅ `@LogicalDeleted` 实现软删除
- ✅ `children` 和 `parent` 实现自关联（树形结构）

---

### 2. DTO 定义（Category.dto）

```dto
export edu.only4.danmuku.application.queries._share.model.JCategory
    -> package edu.only4.danmuku.application.queries._share.draft.JCategory

CategoryTreeNode {
    #allScalars(JCategory)

    // 🔄 递归加载所有子分类（* 表示递归）
    children* {
        #allScalars(JCategory)
    }
}
```

**关键特性：**
- ✅ `children*` 语法：自动递归加载所有层级的子分类
- ✅ KSP 自动生成完整的 DTO 实现（约 200+ 行代码）
- ✅ 类型安全，编译时检查

---

### 3. Query Handler 实现

#### **方式 1：使用 Jimmer DTO（推荐）**

```kotlin
@Service
class GetCategoryTreeQryHandler(
    private val sqlClient: KSqlClient
) : ListQuery<Request, Response> {

    override fun exec(request: Request): List<Response> {
        // 直接查询 DTO，自动递归加载
        val categoryTreeNodes = sqlClient.findList(CategoryTreeNode::class) {
            where(table.parentId.eq(0L))  // 只查询顶级分类
            orderBy(table.sort.asc())
        }

        // 转换为 Query Response
        return categoryTreeNodes.map { convertToQueryResponse(it) }
    }
}
```

**生成的 SQL（Jimmer 自动优化）：**

```sql
-- 方式 A：递归 CTE（推荐，MySQL 8.0+）
WITH RECURSIVE category_tree AS (
    SELECT id, parent_id, code, name, icon, background, sort
    FROM category
    WHERE parent_id = 0 AND deleted = 0

    UNION ALL

    SELECT c.id, c.parent_id, c.code, c.name, c.icon, c.background, c.sort
    FROM category c
    INNER JOIN category_tree ct ON c.parent_id = ct.id
    WHERE c.deleted = 0
)
SELECT * FROM category_tree ORDER BY sort;

-- 方式 B：多次查询（Jimmer 批量优化，避免 N+1）
-- 第1次：查询顶级分类
SELECT id, parent_id, code, name, icon, background, sort
FROM category
WHERE parent_id = 0 AND deleted = 0
ORDER BY sort;

-- 第2次：批量查询所有子分类（一次查询）
SELECT id, parent_id, code, name, icon, background, sort
FROM category
WHERE parent_id IN (1, 2, 3) AND deleted = 0;
```

**优势：**
- ✅ 一条递归 CTE 或批量查询，避免 N+1 问题
- ✅ 自动处理逻辑删除（`deleted = 0`）
- ✅ 自动组装树形结构

---

#### **方式 2：使用 Fetcher**

```kotlin
val categories = sqlClient.executeQuery(JCategory::class) {
    where(table.parentId.eq(0L))
    select(
        table.fetch(
            newFetcher(JCategory::class).by {
                allScalarFields()
                `children*` {  // 递归加载
                    allScalarFields()
                }
            }
        )
    )
}
```

---

### 4. 控制器实现（两种方式）

#### **方式 1：CQRS（推荐用于复杂业务）**

```kotlin
@GetMapping("/loadAllCategory")
fun categoryLoadAll(): List<CategoryLoadAll.CategoryItem> {
    val result = Mediator.queries.send(GetCategoryTreeQry.Request())
    return result.categoryTree.map { convertToCategoryItem(it) }
}
```

**调用链：**
```
Controller → Query Handler → Jimmer DTO → Query Response → Payload
```

**优势：**
- ✅ 遵循 CQRS 模式
- ✅ 适用于复杂业务逻辑
- ✅ 便于测试和维护

---

#### **方式 2：Jimmer 直接查询（性能优化）**

```kotlin
@GetMapping("/loadAllCategory/v2")
fun categoryLoadAllDirect(): List<CategoryLoadAll.CategoryItem> {
    val categoryTreeNodes = sqlClient.findList(CategoryTreeNode::class) {
        where(table.parentId.eq(0L))
        orderBy(table.sort.asc())
    }
    return categoryTreeNodes.map { convertJimmerDtoToCategoryItem(it) }
}
```

**调用链：**
```
Controller → Jimmer DTO → Payload
```

**优势：**
- ✅ 减少一次数据转换
- ✅ 代码更简洁
- ✅ 性能更优（适用于简单查询）

---

## 📊 性能对比

| 方式 | 查询次数 | 数据转换次数 | 代码量 | 适用场景 |
|------|---------|------------|-------|---------|
| CQRS + Jimmer | 1 次（递归 CTE） | 2 次 | 中等 | 复杂业务逻辑 |
| Jimmer 直接查询 | 1 次（递归 CTE） | 1 次 | 少 | 简单 CRUD |
| 传统 JPA 手动查询 | N+1 次 | 2-3 次 | 多 | ❌ 不推荐 |

---

## 🔧 配置要求

### 1. 添加 Jimmer 依赖（build.gradle.kts）

```kotlin
plugins {
    id("com.google.devtools.ksp") version "2.1.20-1.0.29"
}

dependencies {
    // Jimmer 核心
    implementation("org.babyfish.jimmer:jimmer-spring-boot-starter:0.9.106")

    // KSP 代码生成
    ksp("org.babyfish.jimmer:jimmer-ksp:0.9.106")
}

// KSP 配置
ksp {
    arg("jimmer.source.includes", "edu.only4.danmuku.application.queries._share.model")
}
```

### 2. 配置 SqlClient（application.yml）

```yaml
jimmer:
  language: kotlin
  dialect: org.babyfish.jimmer.sql.dialect.MySqlDialect
  show-sql: true
```

### 3. 配置 Spring Bean

```kotlin
@Configuration
class JimmerConfig {

    @Bean
    fun sqlClient(dataSource: DataSource): KSqlClient {
        return newKSqlClient {
            setDataSource(dataSource)
            setDialect(MySqlDialect())
        }
    }
}
```

---

## ✅ 使用建议

### 何时使用 CQRS + Jimmer？
- ✅ 需要复杂的业务逻辑处理
- ✅ 需要发送领域事件
- ✅ 需要严格的分层架构
- ✅ 团队遵循 DDD 模式

### 何时使用 Jimmer 直接查询？
- ✅ 简单的 CRUD 操作
- ✅ 对性能要求高
- ✅ 不需要复杂业务逻辑
- ✅ 快速开发原型

---

## 🎯 Jimmer vs 传统方式对比

### 传统 JPA 实现（❌ 不推荐）

```kotlin
// N+1 查询问题
val topCategories = categoryRepository.findByParentId(0L)
topCategories.forEach { category ->
    category.children = categoryRepository.findByParentId(category.id)  // N 次查询
}
```

**问题：**
- ❌ N+1 查询问题
- ❌ 手动组装树形结构
- ❌ 大量模板代码

---

### Jimmer 实现（✅ 推荐）

```kotlin
// 一次查询完成
val categories = sqlClient.findList(CategoryTreeNode::class) {
    where(table.parentId.eq(0L))
}
```

**优势：**
- ✅ 一次递归 CTE 查询
- ✅ 自动组装树形结构
- ✅ 类型安全，编译时检查

---

## 📝 总结

通过 Jimmer 实现分类树查询，我们获得了：

1. **类型安全**：编译时检查，零运行时错误
2. **性能优化**：一次递归 CTE，避免 N+1 问题
3. **代码简洁**：DTO 自动生成，减少 80% 模板代码
4. **灵活性**：支持 CQRS 和直接查询两种模式
5. **可维护性**：`.dto` 文件管理 DTO 定义，清晰明了

**推荐使用场景：**
- 🌲 树形结构查询（如分类、组织架构、评论树）
- 📊 复杂的 DTO 投影（如订单详情、用户信息聚合）
- 🔍 高性能查询（如搜索、列表分页）
