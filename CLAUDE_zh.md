# CLAUDE_zh.md

本文件为 Claude Code 在此仓库中工作时提供指导。

## 项目概览

**only-danmuku** 是一个基于 Kotlin 的视频平台应用，采用 DDD 架构，使用 Spring Boot 3.5.6、Kotlin 2.1.20 和多模块整洁架构。

## 快速命令

```bash
./gradlew build                        # 构建项目
./gradlew :only-danmuku-start:bootRun  # 运行应用（端口 8081）
./gradlew codegen                      # 从 design/*.json 生成代码
./gradlew :only-danmuku-application:kspKotlin  # 生成 Jimmer DTO
```

## 架构概览

### 模块结构
```
only-danmuku-start（启动模块）
  └─> only-danmuku-adapter（基础设施层）
       └─> only-danmuku-application（CQRS 处理器层）
            └─> only-danmuku-domain（纯领域层）
```

### 关键模式
- **DDD**：聚合位于 `domain/aggregates/`，工厂、规约
- **CQRS**：命令/查询在 `application/`，处理器在 `adapter/application/queries/`
- **事件驱动**：领域事件（同步）、集成事件（异步）

### 框架栈
- **cap4k-ddd-starter** (0.4.2)：DDD 基础设施、Mediator、事件
- **Jimmer** (0.9.106)：ORM 与 KSP DTO 生成
- **QueryDSL**：类型安全的 JPA 查询（kapt）
- **Spring Data JPA**：实体/仓储扫描

## 配置
- **数据库**：MySQL `only_danmuku`（root/123456）
- **时间戳约定**：使用**秒级 Unix 时间戳**（不是毫秒）
  - ✅ `1729267200`（秒）
  - ❌ `1729267200000`（毫秒）
- **服务器**：端口 8081，Swagger UI 在 `/swagger-ui.html`
- **Redis**：localhost:6379（密码：123456）

## 开发工作流

### 1. 控制器实现（Portal 层）

**位置**：`only-danmuku-adapter/src/main/kotlin/.../adapter/portal/api/`

**步骤**：
1. ✅ 在创建新命令/查询之前，先检查 `design/aggregate/*/` 中的现有聚合
2. ✅ 仅在 `design/extra/` 中添加缺失的命令/查询（永不重复）
3. ✅ 运行 `./gradlew codegen` 生成脚手架
4. ✅ 使用 `Mediator.commands.send()` 处理写操作，`Mediator.queries.send()` 处理读操作
5. ✅ 返回类型化数据类（不使用 `Map<String, Any>` 或 `List<Any>`）
6. ✅ 在 `adapter/src/test/kotlin/.../portal/api/` 创建 `.http` 测试文件

**示例**：
```kotlin
@RestController
@RequestMapping("/admin/category")
class AdminCategoryController {
    @PostMapping("/save")
    fun save(@RequestBody request: Request): Response {
        if (request.categoryId == null) {
            Mediator.commands.send(CreateCategoryCmd.Request(...))
        } else {
            Mediator.commands.send(UpdateCategoryInfoCmd.Request(...))
        }
        return Response()
    }
}
```

### 2. QueryHandler 实现（读端）

**位置**：`only-danmuku-adapter/src/main/kotlin/.../adapter/application/queries/`

**步骤**：
1. 在 `application/queries/_share/model/` 定义 Jimmer 实体
2. 在 `application/src/main/dto/*.dto` 定义 DTO（使用显式字段，`children*` 表示递归）
3. 运行 `./gradlew :only-danmuku-application:kspKotlin`
4. 使用 `KSqlClient` 实现处理器，用 `sqlClient.findAll(DtoClass::class)` 查询
5. 使用 `eq?` 进行空安全过滤，`exists()` 检查存在性

**示例**：
```kotlin
@Service
class GetCategoryListQryHandler(
    private val sqlClient: KSqlClient
) : ListQuery<Request, Response> {
    override fun exec(request: Request): List<Response> {
        val dtos = sqlClient.findAll(CategorySimple::class) {
            where(table.parentId `eq?` request.parentId)  // 空安全
            orderBy(table.sort.asc())
        }
        return dtos.map { /* 转换 */ }
    }
}
```

**必须导入**：
```kotlin
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.`eq?`
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import edu.only4.danmuku.application.queries._share.model.sort  // 字段扩展
```

### 3. CommandHandler 实现（写端）

**位置**：`only-danmuku-application/src/main/kotlin/.../application/commands/`

**结构**：
```kotlin
object XxxCmd {
    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            // 1. 查询数据：Mediator.repositories.find()
            // 2. 验证规则
            // 3. 调用聚合方法（不直接修改属性）
            // 4. 保存：Mediator.uow.save()（最后一次性调用）
            // 5. 返回结果
        }
    }

    @CustomValidator
    data class Request(...) : RequestParam<Response>
    data class Response(...)
}
```

**组件协作**：
- **Mediator.repositories**：查询/持久化数据
  - `findFirst(predicate).get()` - 必需的实体
  - `findFirst(predicate, persist=false).getOrNull()` - 可选（只读）
  - `find(predicate)` - 多个实体
- **Mediator.factories**：使用 `Mediator.factories.create(Payload(...))` 创建聚合
- **聚合方法**：`category.updateBasicInfo()`、`category.changeParent()` 等
- **Mediator.uow.save()**：在一个事务中提交所有变更

**性能提示**：
- ✅ 在一次查询中获取相关数据（父节点 + 兄弟节点）
- ✅ 对只读查询使用 `persist = false`
- ✅ 在 `save()` 之前在内存中批量更新
- ❌ 避免 N+1 查询

### 4. 自定义验证器

**位置**：`only-danmuku-application/src/main/kotlin/.../application/validater/`

**模式**（内部类）：
```kotlin
@Target(AnnotationTarget.FIELD)
@Constraint(validatedBy = [UniqueEmail.Validator::class])
annotation class UniqueEmail(val message: String = "邮箱已被注册") {
    class Validator : ConstraintValidator<UniqueEmail, String> {
        override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
            if (value.isNullOrBlank()) return true
            // ✅ 使用 Mediator.queries（不是 repositories）
            return !Mediator.queries.send(CheckEmailExistsQry.Request(value)).exists
        }
    }
}
```

**查询处理器**（使用 `exists()` 获得最佳性能）：
```kotlin
@Service
class CheckEmailExistsQryHandler(
    private val sqlClient: KSqlClient
) : Query<Request, Response> {
    override fun exec(request: Request): Response {
        val exists = sqlClient.exists(JUser::class) {
            where(table.email eq request.email)
        }
        return Response(exists)
    }
}
```

## 关键规则

### ⚠️ 首先检查现有聚合
```bash
# 在创建新命令/查询之前：
ls -la design/aggregate/          # 检查现有聚合
find only-danmuku-application -name "*Cmd.kt"  # 查找现有命令
find only-danmuku-application -name "*Qry.kt"  # 查找现有查询
```

**规则**：
- `design/aggregate/` = 现有聚合（不要修改）
- `design/extra/` = 附加命令/查询（仅在真正缺失时添加）
- 永不重复命令/查询

### ✅ 最佳实践

**控制器**：
- 返回类型化数据类（不使用 `Map<String, Any>`）
- 列表端点 → `List<ItemType>`，分页 → `PageData<ItemType>`
- 创建 `.http` 测试文件

**QueryHandlers**：
- 使用 DTO 投影：`sqlClient.findAll(DtoClass::class)`
- 在硬编码类型之前检查领域枚举
- 使用空安全操作符：`eq?`、`like?`、`gt?`
- 使用 `exists()` 检查存在性（最快）

**CommandHandlers**：
- 优化查询（一起获取相关数据）
- 在聚合方法中封装逻辑
- 一个处理器 = 一个事务（`Mediator.uow.save()` 调用一次）
- 对只读查询使用 `persist = false`

**验证器**：
- 使用 `Mediator.queries`（不是 `Mediator.repositories`）
- 内部类模式（无需 `@Component`）
- 使用 `exists()` 获得最佳 SQL 性能

### ❌ 常见错误
- N+1 查询（分别获取而非一起获取）
- 直接修改聚合属性（应使用方法）
- 多次 `save()` 调用（应在最后调用一次）
- 忘记导入 Jimmer 字段扩展（`asc`、`desc`、字段名）
- 使用 `findList()`（不存在，应使用 `findAll()`）
- QueryHandler 中使用魔法数字而非领域枚举

## 重要注意事项

- 标记为 `[cap4k-ddd-codegen-gradle-plugin:do-not-overwrite]` 的文件受保护
- Jimmer 需要 KSP：`build/generated/ksp/` 必须在源集中
- QueryDSL 元模型：实体更改后需清理构建
- `gradle.properties` 中需要阿里云凭证

## 参考

详细示例和完整代码样本请参见 `CLAUDE_zh.md.backup`。
