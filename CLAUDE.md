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

2. **Working with Aggregates:**
    - Aggregate roots extend generated base classes with `Agg*` prefix
    - Entities use JPA annotations
    - Domain events are raised within aggregates
    - Use factories for complex creation logic

3. **Testing:**
    - Test configuration in `buildSrc/src/main/kotlin/kotlin-jvm.gradle.kts`
    - JUnit 5 with 10-minute timeout
    - JVM args: `-Xmx2g -Xms512m`

4. **Convention Plugin:**
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
