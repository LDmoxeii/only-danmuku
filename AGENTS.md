# Repository Guidelines

## Project Structure & Module Organization
Modules map to DDD layers: `only-danmuku-domain/src/main/kotlin` owns aggregates, invariants, and repository ports; `only-danmuku-application` orchestrates commands and domain events; `only-danmuku-adapter` exposes REST/messaging adapters plus outbound gateways with config in `src/main/resources`. `only-danmuku-start` bootstraps Spring through `StartApplication.kt` and wires every module for runtime. Shared Gradle conventions live in `buildSrc`, code-generation specs sit in `design/`, and reusable scaffolds reside in `template/`.

## Build, Test, and Development Commands
- `./gradlew clean build` — compile every module, run tests, and assemble artifacts in `build/libs`.
- `./gradlew test` — execute the JUnit 5 suites with MockK support; run before any push.
- `./gradlew :only-danmuku-start:bootRun` — start the local HTTP service on Java 17.
- `./gradlew codegen` — regenerate DDD scaffolding from `design/*_gen.json`; needs the Aliyun credentials declared in `gradle.properties`.
Use the same verbs via `gradlew.bat` on Windows.

## Coding Style & Naming Conventions
The toolchain targets Kotlin 2.1.20 on Java 17. Follow four-space indentation, favor `val`, and use expression bodies for one-liners. Stick to the `edu.only4.danmuku.<layer>` package scheme and PascalCase class names; suffix adapters/configuration files with `Adapter` or `Config` and make DTOs immutable with `*Request` or `*Command`. Use constructor injection and module-scoped interfaces to keep seams testable.

## Testing Guidelines
JUnit 5 is enabled via `useJUnitPlatform`; place specs in `src/test/kotlin` mirroring the main hierarchy. Name methods `should<Behavior>When<Condition>` and keep builders or stubs in localized `testFixtures` packages. Mock external boundaries with MockK, prefer slice tests for adapters and repositories, and reserve end-to-end checks for wiring changes in `only-danmuku-start`. Surface any uncovered paths in the PR when delaying coverage.

## Commit & Pull Request Guidelines
Follow the `type(scope): summary` convention seen in history (`feat(category): ...`), keeping scopes aligned with modules or aggregates. The summary may be Chinese or English, stays imperative, and should fit within 72 characters. PRs need a problem statement, a high-level fix outline, and test evidence (`./gradlew test`). Link issues, attach API samples or UI screenshots for adapter work, and flag migrations or operational impacts up front.

## Code Generation & Configuration
Codegen reads MySQL metadata using the connection declared in `build.gradle.kts`; update `design/*_gen.json` whenever schemas shift to keep generated artifacts aligned. Store Aliyun credentials and database passwords in local `gradle.properties` or environment variables—never commit secrets. When evolving scaffolding, edit `template/`, rerun `./gradlew codegen`, and inspect the diff before raising a PR.
