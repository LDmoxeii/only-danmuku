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

    2. **Persist Command/Query Definitions**
        - Document required commands and queries in `design/extra/` directory
        - Define the contract for each command/query with their request/response structures

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

   **Implementation Workflow Summary:**
    - Step 1: Define Query/Command contracts (Request/Response)
    - Step 2: Optimize payload structures with typed data classes (NO Map/Any)
    - Step 3: Implement Controller layer with Mediator calls
    - Step 4: Leave Handler implementations for next iteration
    - Step 5: Implement Handler business logic separately (future step)

3. **Working with Aggregates:**
    - Aggregate roots extend generated base classes with `Agg*` prefix
    - Entities use JPA annotations
    - Domain events are raised within aggregates
    - Use factories for complex creation logic

4. **Testing:**
    - Test configuration in `buildSrc/src/main/kotlin/kotlin-jvm.gradle.kts`
    - JUnit 5 with 10-minute timeout
    - JVM args: `-Xmx2g -Xms512m`

5. **Convention Plugin:**
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
