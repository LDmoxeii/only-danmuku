# Repository Guidelines

## Project Structure & Module Organization

- Root project uses Gradle Kotlin DSL with DDD-aligned modules: `only-danmuku-domain` holds aggregates and events under
  `src/main/kotlin/edu/only4/danmuku`, with matching tests in `src/test/kotlin`.
- `only-danmuku-application` hosts commands, queries, and subscribers; keep generated KSP sources under
  `build/generated/ksp/main/kotlin` committed only when required.
- `only-danmuku-adapter` exposes REST endpoints in `adapter/portal/api`, co-locating DTOs and validation annotations.
- `only-danmuku-start` bootstraps the Spring Boot entrypoint and shared configuration; runtime assets sit in
  `src/main/resources`.
- Code generation config resides in `design/*_gen.json`, while reusable templates live in `template/_tpl`; update JSON
  first, then regenerate code.

## Build, Test, and Development Commands

- `./gradlew build` compiles every module, runs code generation hooks, and assembles artifacts.
- `./gradlew test` executes the JUnit 5/MockK test suites; run before pushing to catch regressions.
- `./gradlew :only-danmuku-start:bootRun` launches the API locally with the default Spring profile.
- `./gradlew clean build -x test` is handy for validating codegen-driven refactors when test execution is unchanged.

## Coding Style & Naming Conventions

- Kotlin (JVM 17) with 4-space indentation; prefer expression bodies for simple returns and avoid wildcard imports.
- Follow CQRS layout from `代码实现规约.md`: controllers in `adapter/portal/api`, commands in
  `application/.../commands`, queries in `.../queries`.
- Name classes in UpperCamelCase (`VideoDraftCreatedDomainEvent`), methods and fields in lowerCamelCase, and suffix
  transport models with `Request`/`Response`.
- Use `KnownException` for domain validation errors, JSR-303 annotations for DTO checks, and keep logging to actionable
  lifecycle events.

## Testing Guidelines

- Place unit tests beside their modules within `src/test/kotlin`, mirroring the production package structure.
- Utilize JUnit 5 display names (e.g., `"should return recommended videos"`) and MockK for collaborators; prefer clear
  Given-When-Then blocks.
- Integration flows belong in `only-danmuku-start` with `@SpringBootTest`, isolating Redis or HTTP clients via stubs or
  embedded alternatives.
- Assert domain invariants and emitted events; add regression tests for each bug fix and ensure flaky tests are
  quarantined before merge.

## Commit & Pull Request Guidelines

- Commits follow `type(scope): summary` (e.g., `feat(video): 增强视频弹幕返回`); keep type lowercase and scope
  meaningful.
- Squash WIP commits locally; reference related issues or design docs when touching `design/*_gen.json` or generated
  sources.
- Pull requests need a concise problem statement, change summary, screenshots or sample payloads for API updates, and a
  checklist of executed tests.
- Call out schema, configuration, or codegen changes so reviewers can rerun `./gradlew build` and sync shared assets
  promptly.
