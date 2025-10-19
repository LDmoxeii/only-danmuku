# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**only-danmuku** is a Kotlin-based video platform application built with DDD (Domain-Driven Design) architecture. The
project uses Spring Boot 3.5.6, Kotlin 2.1.20, and follows a multi-module structure with clean architecture principles.

## Build System

This is a Gradle project using Kotlin DSL with custom code generation.

### Common Commands

```bash
# Build the entire project
./gradlew build

# Run tests
./gradlew test

# Run the application (starts Spring Boot)
./gradlew :only-danmuku-start:bootRun

# Clean build artifacts
./gradlew clean

# Generate code from design files
./gradlew codegen

# Build without running tests
./gradlew build -x test

# Run a single test class
./gradlew test --tests "ClassName"

# Run tests in a specific module
./gradlew :only-danmuku-domain:test
```

### Code Generation

The project uses `com.only4.codegen` plugin (version 0.1.1-SNAPSHOT) for DDD code generation:

- Design files are located in `design/` directory with `*_gen.json` pattern
- Architecture template: `cap4k-ddd-codegen-template-multi-nested.json`
- Base package: `edu.only4.danmuku`
- Database schema: `only_danmuku` (MySQL)
- Generation config in `build.gradle.kts` (lines 21-44)

## Architecture

### Multi-Module Structure

The project follows a strict layered DDD architecture with 4 modules:

1. **only-danmuku-domain** - Core domain logic
    - Aggregates, entities, value objects
    - Domain events and specifications
    - Factories and domain services
    - Uses JPA, QueryDSL, and kapt for metamodel generation
    - Location: `domain/aggregates/`

2. **only-danmuku-application** - Application services layer
    - Commands and command handlers (CQRS write side)
    - Queries and query handlers (CQRS read side)
    - Integration events and sagas
    - Domain event subscribers
    - Uses KSP for Jimmer DTO generation
    - Location: `application/commands/`, `application/queries/`

3. **only-danmuku-adapter** - Adapters and infrastructure
    - **Portal layer** (`adapter/portal/api/`) - REST controllers (entry points)
    - **Application adapters** (`adapter/application/queries/`) - Query handler implementations
    - **Domain adapters** (`adapter/domain/repositories/`) - Repository implementations
    - **Infrastructure** (`adapter/infra/`) - JPA configuration, persistence
    - Uses Spring Web, Jimmer, Druid, Blaze-Persistence

4. **only-danmuku-start** - Application bootstrap
    - Spring Boot application entry point: `StartApplication.kt`
    - Configuration files: `application.yml`
    - Database migrations: `src/main/resources/db.migration/`
    - Aggregates all other modules

### Module Dependencies

```
only-danmuku-start
    └── depends on → only-danmuku-adapter, only-danmuku-application, only-danmuku-domain

only-danmuku-adapter
    └── depends on → only-danmuku-application

only-danmuku-application
    └── depends on → only-danmuku-domain

only-danmuku-domain
    └── no dependencies (pure domain)
```

### Key Architectural Patterns

**DDD Pattern Implementation:**

- Aggregates are in `domain/aggregates/` with entity classes and `Agg*` prefix for aggregate roots
- Domain events are in `domain/aggregates/events/`
- Repositories are interfaces in domain, implementations in adapter layer
- Factories handle aggregate creation logic
- Specifications encapsulate domain rules

**CQRS Pattern:**

- Commands in `application/commands/`
- Queries in `application/queries/`
- Query handlers in `adapter/application/queries/`
- Separate read/write models

**Event-Driven Architecture:**

- Domain events for within-bounded-context communication
- Integration events for cross-bounded-context communication
- Event subscribers in `application/subscribers/domain/` and `application/subscribers/integration/`

### Framework Integration

**cap4k-ddd-starter (version 0.4.2-SNAPSHOT):**

- Provides DDD building blocks and infrastructure
- Event handling with interceptors
- Saga orchestration support
- Snowflake ID generation

**only-engine (version 0.1.3-SNAPSHOT):**

- Custom framework providing common utilities
- Modules: common, captcha, redis, jimmer, json, web, doc
- API documentation with SpringDoc integration

**Jimmer (version 0.9.106):**

- ORM framework used for persistence
- KSP-based compile-time code generation
- DTO projection support
- Configured with `jimmer.language: kotlin`

**Spring Data JPA:**

- Entity scanning: `edu.only4.danmuku.domain.aggregates`
- Repository scanning: `edu.only4.danmuku.adapter.domain.repositories`
- QueryDSL integration via kapt

## Configuration

### Database

- MySQL database: `only_danmuku`
- Connection: `jdbc:mysql://localhost:3306/only_danmuku`
- Default credentials: root/123456
- Druid connection pool (initial: 30, max: 100)
- JPA DDL: none (managed by migrations)
- **Timestamp Convention**: All time-related fields in the database must use **second-level Unix timestamps** (not milliseconds, not formatted dates)
  - Example: `1729267200` (2024-10-18 16:00:00 in seconds)
  - ❌ Wrong: `1729267200000` (milliseconds)
  - ❌ Wrong: `20241018` (formatted date)
  - This applies to all date fields including: `create_time`, `update_time`, `statistics_date`, etc.

### Application Settings

- Server port: 8081
- Base package: `edu.only4.danmuku`
- API documentation enabled at `/swagger-ui.html`
- Redis: localhost:6379 (password: 123456)
- Redisson client name: ONLY-DANMUKU

### Environment Setup

Maven repositories require Aliyun credentials configured in `gradle.properties`:

- `aliyun.maven.username`
- `aliyun.maven.password`

## Code Generation Template

The project structure is defined by `cap4k-ddd-codegen-template-multi-nested.json` which maps design files to:

- Commands → `application/commands/{{ path }}/`
- Queries → `application/queries/{{ path }}/`
- Query handlers → `adapter/application/queries/`
- Aggregates → `domain/aggregates/{{ path }}/`
- Domain events → `domain/aggregates/events/`
- Repositories → `adapter/domain/repositories/{{ path }}/`
- API controllers → `adapter/portal/api/`

When adding new features, create design JSON files in `design/` with `_gen.json` suffix and run `./gradlew codegen`.

## Development Workflow

1. **Adding New Features:**
    - Create design file in `design/*_gen.json`
    - Run `./gradlew codegen` to generate scaffolding
    - Implement business logic in generated files
    - Domain logic goes in aggregates
    - Application logic in command/query handlers
    - API endpoints in portal controllers

2. **Implementing Controllers (CQRS Pattern):**

   When implementing a new controller in `adapter/portal/api/`, follow these steps:

    1. **Identify Required Commands and Queries**
        - Analyze the controller requirements to determine what commands (write operations) and queries (read
          operations) are needed
        - Example: For category management, you might need:
            - Commands: `CreateCategoryCmd`, `UpdateCategoryInfoCmd`, `DeleteCategoryCmd`, `UpdateCategorySortOrderCmd`
            - Queries: `GetCategoryListQry`, `GetCategoryTreeQry`

    2. **Check Existing Aggregates and Supplement Missing Command/Query Definitions**
        - **First**: Check if required commands and queries already exist in `design/aggregate/` directory
            - Each subdirectory represents an existing aggregate (e.g., `video_comment/`, `video_danmuku/`)
            - Read the `_gen.json` file in each aggregate to see available commands and queries
            - Search the application layer to confirm existing implementations:
                - Commands location: `only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/commands/`
                - Queries location: `only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/queries/`
        - **Second**: Only create new design files in `design/extra/` for truly missing functionality
            - `design/aggregate/` = Already defined aggregates (DO NOT MODIFY)
            - `design/extra/` = Additional commands/queries for existing aggregates (ADD HERE only if needed)
            - Never duplicate commands/queries that already exist in aggregates
        - **Then**: Run `./gradlew codegen` to generate code for newly defined commands/queries
        - **IMPORTANT**: Define the contract for each new command/query with their request/response structures

    3. **Complete Input/Output Parameters**
        - Ensure all command requests have complete input parameters
        - Define query response types carefully:
            - **Single element**: `Response` (single object)
            - **List elements**: `List<Response>` (collection)
            - **Paginated elements**: `Page<Response>` (paginated collection)
        - Example from `GetCategoryListQry`:
          ```kotlin
          // Returns a list of category responses
          val listResult: List<GetCategoryListQry.Response> = Mediator.queries.send(...)
          ```

    4. **Optimize Payload Request/Response Structures**
        - **IMPORTANT**: Before defining payload structures, check if the interface is a list or pagination interface
        - **List Interface** (返回集合): Controller return type should be `List<Payload.ItemType>`
          ```kotlin
          // ✅ GOOD: List interface
          @PostMapping("/loadCategory")
          fun load(@RequestBody request: Request): List<AdminCategoryLoad.CategoryItem> {
              return listResult.map { transform(it) }
          }
          ```
        - **Pagination Interface** (返回分页): Controller return type should be `PageData<Payload.ItemType>`
          ```kotlin
          // ✅ GOOD: Pagination interface
          @PostMapping("/loadDanmu")
          fun load(@RequestBody request: Request): PageData<AdminInteractLoadDanmu.DanmuItem> {
              val pageData = PageData<AdminInteractLoadDanmu.DanmuItem>()
              pageData.list = queryResult.list.map { transform(it) }
              pageData.pageNo = queryResult.pageNo
              pageData.totalCount = queryResult.totalCount
              return pageData
          }
          ```
        - **IMPORTANT**: Do NOT use generic `Map<String, Any>` or `List<Any>` in payload definitions
        - Always create specific data classes for better type safety and frontend usability
        - Define nested data classes within the payload object for encapsulation
        - Example - **BAD Practice** (avoid this):
          ```kotlin
          data class Response(
              var preDayData: Map<String, Any>? = null,  // ❌ Hard for frontend to use
              var list: List<Any>? = null                // ❌ No type information
          )
          ```
        - Example - **GOOD Practice** (follow this):
          ```kotlin
          object AdminIndexGetActualTimeStatistics {
              data class Response(
                  var preDayData: StatisticsData? = null,      // ✅ Strongly typed
                  var totalCountInfo: StatisticsData? = null   // ✅ Clear structure
              )

              // Nested data class for better encapsulation
              data class StatisticsData(
                  var videoViewCount: Int = 0,
                  var videoLikeCount: Int = 0,
                  var videoCommentCount: Int = 0
              )
          }
          ```
        - Benefits of typed data classes:
            - IDE auto-completion support for frontend developers
            - Compile-time type checking
            - Clear API documentation
            - Easier to maintain and refactor

    5. **Implement Controller Using Commands and Queries**
        - Use `Mediator.commands.send()` for write operations
        - Use `Mediator.queries.send()` for read operations
        - Transform command/query responses to controller response format
        - **IMPORTANT**: At the initial implementation stage, focus ONLY on controller layer logic
            - Do NOT implement Command Handler or Query Handler business logic yet
            - The handlers can remain empty or return mock data initially
            - Actual handler implementation will be done in a separate step
        - Example structure:
          ```kotlin
          @RestController
          @RequestMapping("/admin/category")
          class AdminCategoryController {
              @PostMapping("/loadCategory")
              fun load(@RequestBody request: Request): Response {
                  // Query handler logic will be implemented later
                  val result = Mediator.queries.send(GetCategoryListQry.Request())
                  return Response(list = result.map { transform(it) })
              }

              @PostMapping("/saveCategory")
              fun save(@RequestBody request: Request): Response {
                  // Command handler logic will be implemented later
                  Mediator.commands.send(CreateCategoryCmd.Request(...))
                  return Response()
              }
          }
          ```

    6. **Handle Conditional Logic**
        - Use request parameters to determine behavior (e.g., create vs. update)
        - Choose appropriate query based on requirements (e.g., tree vs. list format)
        - Example:
          ```kotlin
          if (request.categoryId == null) {
              // Create new entity
              Mediator.commands.send(CreateCategoryCmd.Request(...))
          } else {
              // Update existing entity
              Mediator.commands.send(UpdateCategoryInfoCmd.Request(...))
          }
          ```

    7. **Data Transformation**
        - Convert domain query responses to controller response format using typed data classes
        - Avoid using `Map` - use specific data classes instead
        - Use helper functions for complex transformations
        - Example from `AdminIndexController`:
          ```kotlin
          // ✅ GOOD: Transform to typed data class
          val preDayDataObj = AdminIndexGetActualTimeStatistics.StatisticsData(
              videoViewCount = preDayData.videoViewCount,
              videoLikeCount = preDayData.videoLikeCount,
              videoCommentCount = preDayData.videoCommentCount,
              videoShareCount = preDayData.videoShareCount,
              userFollowCount = preDayData.userFollowCount,
              userLoginCount = preDayData.userLoginCount
          )

          // ✅ GOOD: Transform list to typed objects
          val resultList = weekData.map { item ->
              AdminIndexGetWeekStatistics.WeekStatisticsItem(
                  statisticsDate = item.date,
                  statisticsCount = item.count
              )
          }
          ```

    8. **Create HTTP Test File**
        - After implementing a controller method, create/update the corresponding `.http` file for testing
        - Location: `only-danmuku-adapter/src/test/kotlin/edu/only4/danmuku/adapter/portal/api/`
        - File naming: `ControllerName.http` (e.g., `AdminCategoryController.http`)
        - Environment configuration: `only-danmuku-adapter/src/test/resources/http-client.env.json`
        - Example structure:
          ```http
          ### Method description
          POST {{host}}/admin/category/loadCategory
          Content-Type: application/json

          {
            "parentId": null
          }

          ### Get category tree
          POST {{host}}/admin/category/loadCategory
          Content-Type: application/json

          {
            "convert2Tree": true
          }
          ```
        - **Best Practices**:
            - Group related test cases with descriptive comments (`### Description`)
            - Use environment variable `{{host}}` from `http-client.env.json`
            - Provide example request bodies for each endpoint
            - Keep test cases organized by controller functionality
            - **Note**: These are smoke tests for quick API verification, not comprehensive test suites

   **Implementation Workflow Summary:**
    - Step 1: Identify required commands and queries
    - Step 2: Check existing aggregates, only add new definitions in `design/extra/` for missing functionality, run `./gradlew codegen` to generate code
    - Step 3: Complete command/query input/output parameters (Request/Response)
    - Step 4: Optimize payload structures with typed data classes (NO Map/Any)
    - Step 5: Implement Controller layer with Mediator calls
    - Step 6: Leave Handler implementations for next iteration
    - Step 7: Implement Handler business logic separately (future step)
    - Step 8: Create/update HTTP test file for the controller endpoint

3. **Working with Aggregates:**
    - Aggregate roots extend generated base classes with `Agg*` prefix
    - Entities use JPA annotations
    - Domain events are raised within aggregates
    - Use factories for complex creation logic

4. **Implementing QueryHandlers with Jimmer ORM:**

   When implementing QueryHandlers in the CQRS read side using Jimmer, follow this workflow:

    1. **Define Jimmer Entity Interface**
        - Location: `only-danmuku-application/src/main/kotlin/.../application/queries/_share/model/`
        - Create Kotlin interface with `@Entity` annotation
        - Use `@Table(name = "table_name")` to map to database table
        - Example:
          ```kotlin
          @Table(name = "category")
          @Entity
          interface JCategory {
              @Id
              val id: Long
              val code: String
              val name: String

              // Self-referencing relationship for tree structure
              @OneToMany(mappedBy = "parent")
              val children: List<JCategory>

              @ManyToOne
              @JoinColumn(name = "parent_id", foreignKeyType = ForeignKeyType.FAKE)
              val parent: JCategory?

              @IdView("parent")  // Access parent's ID without loading parent entity
              val parentId: Long?
          }
          ```
        - **Important**: For self-referencing entities with nullable foreign keys:
            - Use `ForeignKeyType.FAKE` to allow special values (e.g., `parent_id = 0`)
            - Use `@IdView` to access foreign key IDs through associations
            - Match nullability between association and `@IdView` property

    2. **Define DTO Using Jimmer DTO Language**
        - Location: `only-danmuku-application/src/main/dto/`
        - Create `.dto` file with Jimmer DTO Language syntax
        - Export entity and define DTO structures
        - Example (`Category.dto`):
          ```dto
          export edu.only4.danmuku.application.queries._share.model.JCategory
              -> package edu.only4.danmuku.application.queries._share.draft

          /**
           * Category tree structure DTO
           */
          CategoryTreeNode {
              id
              parentId
              code
              name
              icon
              background

              // Recursive loading (use * for recursion)
              children*
          }

          CategorySimple {
              id
              code
              name
          }
          ```
        - **Syntax Rules**:
            - Use explicit field names (no macros like `#allScalars`)
            - Recursive syntax: `children*` (no nested field definition)
            - Package export uses `-> package target.package`

    3. **Run KSP Code Generation**
        - Execute: `./gradlew :only-danmuku-application:kspKotlin`
        - Or: `./gradlew build` (includes KSP processing)
        - Generated DTO classes: `build/generated/ksp/main/kotlin/.../draft/`
        - Generated DTOs are immutable data classes with `View<T>` interface
        - **Common Errors**:
            - ❌ KSP syntax error: Check DTO syntax, use explicit fields
            - ❌ Missing `@IdView`: Add annotation for foreign key access
            - ❌ Nullable mismatch: Match nullability of association and `@IdView`

    4. **Implement QueryHandler Using DTO Projection**
        - Location: `only-danmuku-adapter/src/main/kotlin/.../adapter/application/queries/`
        - Inject `KSqlClient` from Jimmer
        - Use `sqlClient.findAll(DtoClass::class)` for direct DTO queries
        - **IMPORTANT - Using Domain Enums in QueryHandlers**:
            - When implementing QueryHandlers that need to work with enumerated types (e.g., data classification, status codes), **always check the domain model first** before writing hardcoded logic
            - Domain enums are located in: `only-danmuku-domain/src/main/kotlin/edu/only4/danmuku/domain/aggregates/{aggregate}/enums/`
            - Example scenario: When processing statistics by data type, check `Statistics.kt` in the domain module for available enums
            - **Why this matters**:
                - ✅ Reuses domain knowledge and type safety from the write model
                - ✅ Avoids magic numbers and hardcoded type mappings
                - ✅ Ensures consistency between read and write sides
                - ✅ Makes code more maintainable and self-documenting
            - **BAD Practice** (avoid hardcoded type checks):
              ```kotlin
              // ❌ BAD: Magic numbers without domain context
              when (stats.dataType.toInt()) {
                  1 -> videoViewCount += count      // What is 1?
                  2 -> videoLikeCount += count       // What is 2?
                  3 -> videoCommentCount += count    // What is 3?
              }
              ```
            - **GOOD Practice** (use domain enums):
              ```kotlin
              // ✅ GOOD: Use domain enum from write model
              import edu.only4.danmuku.domain.aggregates.statistics.enums.StatisticsDataType

              val countsByType = statisticsList
                  .groupingBy { StatisticsDataType.valueOf(it.dataType.toInt()) }
                  .fold(0) { acc, item -> acc + (item.statisticsCount ?: 0) }

              val videoViewCount = countsByType[StatisticsDataType.VIDEO_VIEW] ?: 0
              val videoLikeCount = countsByType[StatisticsDataType.VIDEO_LIKE] ?: 0
              ```
            - **Workflow**: Before implementing type-based logic in QueryHandlers:
                1. Check the corresponding aggregate in `only-danmuku-domain/src/main/kotlin/edu/only4/danmuku/domain/aggregates/{aggregate}/`
                2. Look for enum classes in the `enums/` subdirectory or as nested classes
                3. Import and use the domain enum for type-safe operations
                4. This ensures CQRS read side stays aligned with domain model semantics
        - Example:
          ```kotlin
          @Service
          class GetCategoryTreeQryHandler(
              private val sqlClient: KSqlClient
          ) : ListQuery<GetCategoryTreeQry.Request, GetCategoryTreeQry.Response> {

              override fun exec(request: GetCategoryTreeQry.Request): List<GetCategoryTreeQry.Response> {
                  // Direct DTO query with filtering
                  val readModel = sqlClient.findAll(CategoryTreeNode::class) {
                      where(table.parentId eq 0L)
                  }

                  // Transform to query response
                  return readModel.map { readModelToResponse(it) }
              }

              private fun readModelToResponse(dto: CategoryTreeNode): GetCategoryTreeQry.Response {
                  return GetCategoryTreeQry.Response(
                      categoryId = dto.id,
                      code = dto.code,
                      name = dto.name,
                      children = dto.children?.map { readModelToResponse(it) } ?: emptyList()
                  )
              }
          }
          ```
        - **Best Practices**:
            - ✅ Use `sqlClient.findAll(DtoClass::class)` for DTO projection
            - ✅ DTO projection avoids `UnloadedException`
            - ✅ All DTO fields are automatically loaded via METADATA
            - ✅ Use `eq?` operator for optional filtering (handles null gracefully)
            - ❌ Avoid using Fetcher objects in CQRS (no reuse scenarios)
            - ❌ Do NOT use `findList()` (doesn't exist in Jimmer API)
        - **Optional Filtering Pattern**:
          ```kotlin
          // ❌ BAD: Verbose if-else branching
          val result = if (request.parentId != null) {
              sqlClient.findAll(CategorySimple::class) {
                  where(table.parentId eq request.parentId)
                  orderBy(table.sort.asc())
              }
          } else {
              sqlClient.findAll(CategorySimple::class) {
                  orderBy(table.sort.asc())
              }
          }

          // ✅ GOOD: Use eq? operator for null-safe filtering
          val result = sqlClient.findAll(CategorySimple::class) {
              where(table.parentId `eq?` request.parentId)  // Ignores condition if null
              orderBy(table.sort.asc())
          }
          ```
        - **Jimmer Null-Safe Operators**:
            - `eq?` - Equals or ignore if null
            - `ne?` - Not equals or ignore if null
            - `like?` - Like or ignore if null
            - `gt?`, `ge?`, `lt?`, `le?` - Comparison or ignore if null
            - These operators automatically skip the condition when the value is null
        - **Required Imports for Jimmer Queries**:

          Jimmer generates extension properties for entity fields via KSP. These **must be explicitly imported**:
          ```kotlin
          import org.babyfish.jimmer.sql.kt.KSqlClient
          import org.babyfish.jimmer.sql.kt.ast.expression.eq
          import org.babyfish.jimmer.sql.kt.ast.expression.`eq?`  // Null-safe equals
          import org.babyfish.jimmer.sql.kt.ast.expression.asc   // For sorting
          import org.babyfish.jimmer.sql.kt.ast.expression.desc  // For sorting
          import edu.only4.danmuku.application.queries._share.model.fieldName  // Generated field extensions
          ```
        - **Common Import Errors**:
            - ❌ **Error**: `Unresolved reference 'asc'` when using `orderBy(table.sort.asc())`
            - ✅ **Solution**: Import `org.babyfish.jimmer.sql.kt.ast.expression.asc`
            - ❌ **Error**: `Unresolved reference 'sort'` when accessing `table.sort`
            - ✅ **Solution**: Import field extension from model package (e.g., `edu.only4.danmuku.application.queries._share.model.sort`)
            - **Important**: You need BOTH the field extension AND the sorting function imports

    5. **Test and Verify**
        - Create test data in design SQL files (`design/aggregate/*/table.sql`)
        - Run the application: `./gradlew :only-danmuku-start:bootRun`
        - Test API endpoint via Swagger UI: `http://localhost:8081/swagger-ui.html`
        - Verify recursive loading for tree structures
        - Check generated SQL in logs

   **Jimmer QueryHandler Architecture Decision:**
   - In CQRS architecture, each QueryHandler has unique requirements
   - **Do NOT create predefined Fetcher objects** (no reuse scenarios)
   - Use direct DTO queries in handlers: `sqlClient.findAll(DtoClass::class)`
   - DTO Language approach is preferred over Kotlin Fetcher DSL
   - Keep query logic simple and maintainable

   **Common Errors and Solutions:**

   | Error | Cause | Solution |
   |-------|-------|----------|
   | `KSP failed with exit code: PROCESSING_ERROR` | Invalid DTO syntax | Use explicit fields, fix recursive syntax `children*` |
   | `Illegal property, please add @IdView` | Missing annotation for FK | Add `@IdView("association")` |
   | `Property is not nullable but association is nullable` | Nullability mismatch | Match `@IdView` nullability with association |
   | `The property is nullable, but DB column is nonnull` | DB constraint conflict | Use `foreignKeyType = ForeignKeyType.FAKE` |
   | `UnloadedException: property is unloaded` | Using Fetcher without loading field | Use DTO projection instead of Fetcher |
   | `Unresolved reference 'findList'` | Wrong API method | Use `findAll(DtoClass::class)` instead |

5. **Implementing Custom Validators:**

   When implementing custom validators for Bean Validation in the DDD + CQRS architecture, follow this pattern:

   **Architecture Principle:**
    - **Validators in `application` layer**: Business logic, no direct database access
    - **Query handlers in `adapter` layer**: Data access implementation
    - **Decouple via `Mediator`**: Validators use CQRS queries to fetch data

   **Standard Implementation Steps:**

   **Step 1: Define Validation Annotation (Inner Class Pattern)**

   Location: `only-danmuku-application/src/main/kotlin/.../application/validater/`

   ```kotlin
   package edu.only4.danmuku.application.validater

   import com.only4.cap4k.ddd.core.Mediator
   import edu.only4.danmuku.application.queries.user.CheckEmailExistsQry
   import jakarta.validation.Constraint
   import jakarta.validation.ConstraintValidator
   import jakarta.validation.ConstraintValidatorContext
   import jakarta.validation.Payload
   import kotlin.reflect.KClass

   /**
    * Validates email uniqueness
    */
   @Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
   @Retention(AnnotationRetention.RUNTIME)
   @Constraint(validatedBy = [UniqueUserEmail.Validator::class])
   @MustBeDocumented
   annotation class UniqueUserEmail(
       val message: String = "邮箱已被注册",
       val groups: Array<KClass<*>> = [],
       val payload: Array<KClass<out Payload>> = []
   ) {

       /**
        * Validator implementation (inner class)
        */
       class Validator : ConstraintValidator<UniqueUserEmail, String> {

           override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
               // Null/blank values handled by other annotations (e.g., @NotBlank)
               if (value.isNullOrBlank()) {
                   return true
               }

               // Use CQRS query to fetch data
               return !Mediator.queries.send(
                   CheckEmailExistsQry.Request(email = value)
               ).exists
           }
       }
   }
   ```

   **Key Points:**
    - ✅ Use `@Constraint(validatedBy = [XXX.Validator::class])` to bind validator
    - ✅ Validator as **inner class**, no need for `@Component` annotation
    - ✅ Use `Mediator.queries.send()` instead of direct database access
    - ✅ Null handling: Return `true` to let other annotations handle it
    - ✅ Validation logic: Return `true` for pass, `false` for fail

   **Step 2: Define CQRS Query Object**

   Location: `only-danmuku-application/src/main/kotlin/.../application/queries/`

   ```kotlin
   package edu.only4.danmuku.application.queries.user

   import com.only4.cap4k.ddd.core.application.RequestParam

   /**
    * Check if email exists
    */
   object CheckEmailExistsQry {

       class Request(
           val email: String
       ) : RequestParam<Response>

       class Response(
           val exists: Boolean  // true: exists, false: not exists
       )
   }
   ```

   **Step 3: Implement Query Handler (Using Jimmer)**

   Location: `only-danmuku-adapter/src/main/kotlin/.../adapter/application/queries/`

   ```kotlin
   package edu.only4.danmuku.adapter.application.queries.user

   import com.only4.cap4k.ddd.core.application.query.Query
   import edu.only4.danmuku.application.queries._share.model.user.JUser
   import edu.only4.danmuku.application.queries._share.model.user.email
   import edu.only4.danmuku.application.queries.user.CheckEmailExistsQry
   import org.babyfish.jimmer.sql.kt.KSqlClient
   import org.babyfish.jimmer.sql.kt.ast.expression.eq
   import org.babyfish.jimmer.sql.kt.exists
   import org.springframework.stereotype.Service

   /**
    * Check email exists query handler
    */
   @Service
   class CheckEmailExistsQryHandler(
       private val sqlClient: KSqlClient
   ) : Query<CheckEmailExistsQry.Request, CheckEmailExistsQry.Response> {

       override fun exec(request: CheckEmailExistsQry.Request): CheckEmailExistsQry.Response {
           // Use Jimmer exists() for optimal performance
           val exists = sqlClient.exists(JUser::class) {
               where(table.email eq request.email)
           }

           return CheckEmailExistsQry.Response(exists = exists)
       }
   }
   ```

   **Key Points:**
    - ✅ Use `@Service` to register as Spring Bean
    - ✅ Inject `KSqlClient` for Jimmer queries
    - ✅ Use `sqlClient.exists()` instead of `findAll()` (better performance)
    - ✅ Generates efficient SQL: `SELECT EXISTS(SELECT 1 FROM user WHERE email = ?)`
    - ✅ Must import Jimmer extensions:
        - `import edu.only4.danmuku.application.queries._share.model.user.email`  // Field extension
        - `import org.babyfish.jimmer.sql.kt.ast.expression.eq`  // Operator
        - `import org.babyfish.jimmer.sql.kt.exists`  // exists function

   **Step 4: Usage in Commands**

   ```kotlin
   package edu.only4.danmuku.application.commands.user

   import edu.only4.danmuku.application.validater.UniqueUserEmail
   import jakarta.validation.constraints.Email
   import jakarta.validation.constraints.NotBlank

   object RegisterAccountCmd {

       class Request(
           @field:NotBlank(message = "邮箱不能为空")
           @field:Email(message = "邮箱格式不正确")
           @field:UniqueUserEmail()  // Automatically validates email uniqueness
           val email: String,

           @field:NotBlank(message = "昵称不能为空")
           val nickName: String,

           @field:NotBlank(message = "密码不能为空")
           val registerPassword: String
       ) : RequestParam<Response>

       class Response
   }
   ```

   **Validation Flow:**
    1. Spring MVC receives request
    2. Triggers Bean Validation automatically
    3. Executes sequentially: `@NotBlank` → `@Email` → `@UniqueUserEmail`
    4. If email exists, returns 400 error: "邮箱已被注册"

   **Best Practices:**

   ✅ **Recommended:**
    - Inner class pattern: Better encapsulation
    - CQRS separation: Use queries, not repositories
    - Use Jimmer `exists()`: Best performance
    - Null handling: Only validate non-null values
    - Clear semantics: `exists = true` means already exists, validator returns `!exists`

   ❌ **Avoid:**
    - ~~Inject `@Autowired` dependencies in validator~~ (inner class cannot inject)
    - ~~Use `Mediator.repos` to access repositories~~ (should use `Mediator.queries`)
    - ~~Use `findAll()` for existence check~~ (poor performance, use `exists()`)
    - ~~Validator returns `true` for failure~~ (should return `false`)
    - ~~Forget to import Jimmer extension functions~~ (causes compile errors)

   **Performance Comparison:**

   | Method | SQL Generated | Performance |
      |--------|---------------|-------------|
   | ❌ `findAll().isNotEmpty()` | `SELECT * FROM user WHERE email = ?` | Slow (full scan) |
   | ⚠️ `count() > 0` | `SELECT COUNT(*) FROM user WHERE email = ?` | Medium |
   | ✅ `exists()` | `SELECT EXISTS(SELECT 1 FROM user WHERE email = ?)` | **Fastest** |

6. **Testing:**
    - Test configuration in `buildSrc/src/main/kotlin/kotlin-jvm.gradle.kts`
    - JUnit 5 with 10-minute timeout
    - JVM args: `-Xmx2g -Xms512m`

7. **Convention Plugin:**
    - Shared build logic in `buildSrc/`
    - Convention: `buildsrc.convention.kotlin-jvm`
    - Applies Kotlin JVM, Spring, and JPA plugins
    - Java 17 toolchain configured

## Important Notes

- **Do not modify files marked with `[cap4k-ddd-codegen-gradle-plugin:do-not-overwrite]`** - these are protected from
  code generation overwrites
- The adapter layer's portal package recently underwent refactoring (see commit 863b04d)
- Domain events use both synchronous subscribers and async integration events
- Jimmer requires KSP processing - ensure `build/generated/ksp/` is in source sets
- QueryDSL metamodel generation via kapt requires clean build after entity changes

### ⚠️ Critical: Check Existing Aggregates Before Creating New Commands/Queries

**ALWAYS follow this workflow when implementing a new Controller:**

1. **First**: Check `design/aggregate/` directory for existing aggregate definitions
   - Each subdirectory represents an existing aggregate (e.g., `video_comment/`, `video_danmuku/`)
   - Read the `_gen.json` file in each aggregate to see available Commands and Queries
   - Example: `design/aggregate/video_comment/_gen.json` contains `DelCommentCmd`, `VideoCommentPageQry`, etc.

2. **Second**: Search for existing Commands/Queries in the application layer
   - Commands location: `only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/commands/`
   - Queries location: `only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/queries/`
   - Use file search to find existing implementations before creating new ones

3. **Third**: Only create new design files in `design/extra/` for truly missing functionality
   - `design/aggregate/` = Already defined aggregates (DO NOT MODIFY)
   - `design/extra/` = Additional commands/queries for existing aggregates (ADD HERE if needed)
   - Never duplicate Commands/Queries that already exist in aggregates

4. **Fourth**: Use existing Commands/Queries with correct package paths
   - Existing: `edu.only4.danmuku.application.commands.video_comment.DelCommentCmd`
   - ❌ Wrong: `edu.only4.danmuku.application.commands.comment.DeleteCommentCmd`
   - Check actual file locations and import statements carefully

**Lesson Learned**: In a previous implementation, I incorrectly created `design/extra/interact_gen.json` with duplicate
Commands/Queries that already existed in `design/aggregate/video_comment/` and `design/aggregate/video_danmuku/`. This
caused import errors because I used non-existent package paths. Always verify existing aggregates first to avoid this mistake.

**Quick Check Commands**:
```bash
# List all existing aggregates
ls -la design/aggregate/

# Check specific aggregate definition
cat design/aggregate/video_comment/_gen.json

# Search for existing Commands
find only-danmuku-application -name "*Cmd.kt" -type f

# Search for existing Queries
find only-danmuku-application -name "*Qry.kt" -type f
```
