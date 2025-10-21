# CLAUDE.md

This file provides guidance to Claude Code when working with code in this repository.

## Project Overview

**only-danmuku** is a Kotlin-based video platform with DDD architecture using Spring Boot 3.5.6, Kotlin 2.1.20, and multi-module clean architecture.

## Quick Commands

```bash
./gradlew build                        # Build project
./gradlew :only-danmuku-start:bootRun  # Run application (port 8081)
./gradlew codegen                      # Generate from design/*.json
./gradlew :only-danmuku-application:kspKotlin  # Generate Jimmer DTOs
```

## Architecture Overview

### Module Structure
```
only-danmuku-start (Bootstrap)
  └─> only-danmuku-adapter (Infrastructure)
       └─> only-danmuku-application (CQRS Handlers)
            └─> only-danmuku-domain (Pure Domain)
```

### Key Patterns
- **DDD**: Aggregates in `domain/aggregates/`, Factories, Specifications
- **CQRS**: Commands/Queries in `application/`, Handlers in `adapter/application/queries/`
- **Event-Driven**: Domain events (sync), Integration events (async)

### Framework Stack
- **cap4k-ddd-starter** (0.4.2): DDD infrastructure, Mediator, Events
- **Jimmer** (0.9.106): ORM with KSP DTO generation
- **QueryDSL**: Type-safe JPA queries (kapt)
- **Spring Data JPA**: Entity/Repository scanning

## Configuration
- **Database**: MySQL `only_danmuku` (root/123456)
- **Timestamp Convention**: Use **second-level Unix timestamps** (NOT milliseconds)
  - ✅ `1729267200` (seconds)
  - ❌ `1729267200000` (milliseconds)
- **Server**: Port 8081, Swagger UI at `/swagger-ui.html`
- **Redis**: localhost:6379 (password: 123456)

## Development Workflow

### 1. Controller Implementation (Portal Layer)

**Location**: `only-danmuku-adapter/src/main/kotlin/.../adapter/portal/api/`

**Steps**:
1. ✅ Check existing aggregates in `design/aggregate/*/` before creating new commands/queries
2. ✅ Add missing commands/queries ONLY to `design/extra/` (never duplicate)
3. ✅ Run `./gradlew codegen` to generate scaffolding
4. ✅ Use `Mediator.commands.send()` for writes, `Mediator.queries.send()` for reads
5. ✅ Return typed data classes (NO `Map<String, Any>` or `List<Any>`)
6. ✅ Create `.http` test file in `adapter/src/test/kotlin/.../portal/api/`

**Example**:
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

### 2. QueryHandler Implementation (Read Side)

**Location**: `only-danmuku-adapter/src/main/kotlin/.../adapter/application/queries/`

**Steps**:
1. Define Jimmer entity in `application/queries/_share/model/`
2. Define DTO in `application/src/main/dto/*.dto` (use explicit fields, `children*` for recursion)
3. Run `./gradlew :only-danmuku-application:kspKotlin`
4. Implement handler with `KSqlClient` using `sqlClient.findAll(DtoClass::class)`
5. Use `eq?` for null-safe filtering, `exists()` for existence checks

**Example**:
```kotlin
@Service
class GetCategoryListQryHandler(
    private val sqlClient: KSqlClient
) : ListQuery<Request, Response> {
    override fun exec(request: Request): List<Response> {
        val dtos = sqlClient.findAll(CategorySimple::class) {
            where(table.parentId `eq?` request.parentId)  // Null-safe
            orderBy(table.sort.asc())
        }
        return dtos.map { /* transform */ }
    }
}
```

**Must Import**:
```kotlin
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.`eq?`
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import edu.only4.danmuku.application.queries._share.model.sort  // Field extensions
```

### 3. CommandHandler Implementation (Write Side)

**Location**: `only-danmuku-application/src/main/kotlin/.../application/commands/`

**Structure**:
```kotlin
object XxxCmd {
    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            // 1. Query data: Mediator.repositories.find()
            // 2. Validate rules
            // 3. Call aggregate methods (NOT direct property modification)
            // 4. Save: Mediator.uow.save() (once at end)
            // 5. Return result
        }
    }

    @CustomValidator
    data class Request(...) : RequestParam<Response>
    data class Response(...)
}
```

**Component Collaboration**:
- **Mediator.repositories**: Query/persist data
  - `findFirst(predicate).get()` - Required entity
  - `findFirst(predicate, persist=false).getOrNull()` - Optional (read-only)
  - `find(predicate)` - Multiple entities
- **Mediator.factories**: Create aggregates with `Mediator.factories.create(Payload(...))`
- **Aggregate methods**: `category.updateBasicInfo()`, `category.changeParent()`, etc.
- **Mediator.uow.save()**: Commit ALL changes in ONE transaction

**Performance Tips**:
- ✅ Fetch related data in one query (parent + siblings)
- ✅ Use `persist = false` for read-only queries
- ✅ Batch updates in memory before `save()`
- ❌ Avoid N+1 queries

### 4. Custom Validators

**Location**: `only-danmuku-application/src/main/kotlin/.../application/validater/`

**Pattern** (Inner class):
```kotlin
@Target(AnnotationTarget.FIELD)
@Constraint(validatedBy = [UniqueEmail.Validator::class])
annotation class UniqueEmail(val message: String = "邮箱已被注册") {
    class Validator : ConstraintValidator<UniqueEmail, String> {
        override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
            if (value.isNullOrBlank()) return true
            // ✅ Use Mediator.queries (NOT repositories)
            return !Mediator.queries.send(CheckEmailExistsQry.Request(value)).exists
        }
    }
}
```

**Query Handler** (Use `exists()` for best performance):
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

## Critical Rules

### ⚠️ Check Existing Aggregates FIRST
```bash
# Before creating new commands/queries:
ls -la design/aggregate/          # Check existing aggregates
find only-danmuku-application -name "*Cmd.kt"  # Find existing commands
find only-danmuku-application -name "*Qry.kt"  # Find existing queries
```

**Rules**:
- `design/aggregate/` = Existing aggregates (DO NOT MODIFY)
- `design/extra/` = Additional commands/queries (ADD HERE if truly missing)
- Never duplicate Commands/Queries

### ✅ Best Practices

**Controllers**:
- Return typed data classes (NO `Map<String, Any>`)
- List endpoints → `List<ItemType>`, Pagination → `PageData<ItemType>`
- Create `.http` test files

**QueryHandlers**:
- Use DTO projection: `sqlClient.findAll(DtoClass::class)`
- Check domain enums before hardcoding types
- Use null-safe operators: `eq?`, `like?`, `gt?`
- Use `exists()` for existence checks (fastest)

**CommandHandlers**:
- Optimize queries (fetch related data together)
- Encapsulate logic in aggregate methods
- One handler = one transaction (`Mediator.uow.save()` once)
- Use `persist = false` for read-only queries

**Validators**:
- Use `Mediator.queries` (NOT `Mediator.repositories`)
- Inner class pattern (no `@Component`)
- Use `exists()` for best SQL performance

### ❌ Common Mistakes
- N+1 queries (fetch separately instead of together)
- Direct aggregate property modification (use methods)
- Multiple `save()` calls (should be one at end)
- Forgetting Jimmer field extension imports (`asc`, `desc`, field names)
- Using `findList()` (doesn't exist, use `findAll()`)
- Magic numbers instead of domain enums in QueryHandlers

## Important Notes

- Files marked `[cap4k-ddd-codegen-gradle-plugin:do-not-overwrite]` are protected
- Jimmer requires KSP: `build/generated/ksp/` must be in source sets
- QueryDSL metamodel: Clean build after entity changes
- Aliyun credentials required in `gradle.properties`

## Reference

For detailed examples and complete code samples, see `CLAUDE.md.backup`.
