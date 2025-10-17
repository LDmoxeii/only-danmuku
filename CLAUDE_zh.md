# CLAUDE_zh.md

本文件为 Claude Code (claude.ai/code) 在此仓库中工作时提供指导。

## 项目概览

**only-danmuku** 是一个基于 Kotlin 的视频平台应用，采用 DDD（领域驱动设计）架构。该项目使用 Spring Boot 3.5.6、Kotlin 2.1.20，并遵循多模块结构和整洁架构原则。

## 构建系统

这是一个使用 Kotlin DSL 的 Gradle 项目，支持自定义代码生成。

### 常用命令

```bash
# 构建整个项目
./gradlew build

# 运行测试
./gradlew test

# 运行应用程序（启动 Spring Boot）
./gradlew :only-danmuku-start:bootRun

# 清理构建产物
./gradlew clean

# 从设计文件生成代码
./gradlew codegen

# 构建但不运行测试
./gradlew build -x test

# 运行单个测试类
./gradlew test --tests "ClassName"

# 在特定模块中运行测试
./gradlew :only-danmuku-domain:test
```

### 代码生成

项目使用 `com.only4.codegen` 插件（版本 0.1.1-SNAPSHOT）进行 DDD 代码生成：

- 设计文件位于 `design/` 目录，使用 `*_gen.json` 命名模式
- 架构模板：`cap4k-ddd-codegen-template-multi-nested.json`
- 基础包：`edu.only4.danmuku`
- 数据库架构：`only_danmuku`（MySQL）
- 生成配置在 `build.gradle.kts`（第 21-44 行）

## 架构

### 多模块结构

项目遵循严格的分层 DDD 架构，包含 4 个模块：

1. **only-danmuku-domain** - 核心领域逻辑
    - 聚合、实体、值对象
    - 领域事件和规约
    - 工厂和领域服务
    - 使用 JPA、QueryDSL 和 kapt 进行元模型生成
    - 位置：`domain/aggregates/`

2. **only-danmuku-application** - 应用服务层
    - 命令和命令处理器（CQRS 写端）
    - 查询和查询处理器（CQRS 读端）
    - 集成事件和 Saga
    - 领域事件订阅者
    - 使用 KSP 进行 Jimmer DTO 生成
    - 位置：`application/commands/`、`application/queries/`

3. **only-danmuku-adapter** - 适配器和基础设施
    - **Portal 层**（`adapter/portal/api/`）- REST 控制器（入口点）
    - **应用适配器**（`adapter/application/queries/`）- 查询处理器实现
    - **领域适配器**（`adapter/domain/repositories/`）- 仓储实现
    - **基础设施**（`adapter/infra/`）- JPA 配置、持久化
    - 使用 Spring Web、Jimmer、Druid、Blaze-Persistence

4. **only-danmuku-start** - 应用启动
    - Spring Boot 应用入口点：`StartApplication.kt`
    - 配置文件：`application.yml`
    - 数据库迁移：`src/main/resources/db.migration/`
    - 聚合所有其他模块

### 模块依赖关系

```
only-danmuku-start
    └── 依赖 → only-danmuku-adapter、only-danmuku-application、only-danmuku-domain

only-danmuku-adapter
    └── 依赖 → only-danmuku-application

only-danmuku-application
    └── 依赖 → only-danmuku-domain

only-danmuku-domain
    └── 无依赖（纯领域层）
```

### 关键架构模式

**DDD 模式实现：**

- 聚合位于 `domain/aggregates/`，实体类使用 `Agg*` 前缀表示聚合根
- 领域事件位于 `domain/aggregates/events/`
- 仓储接口在领域层，实现在适配器层
- 工厂处理聚合创建逻辑
- 规约封装领域规则

**CQRS 模式：**

- 命令在 `application/commands/`
- 查询在 `application/queries/`
- 查询处理器在 `adapter/application/queries/`
- 读写模型分离

**事件驱动架构：**

- 领域事件用于限界上下文内通信
- 集成事件用于跨限界上下文通信
- 事件订阅者在 `application/subscribers/domain/` 和 `application/subscribers/integration/`

### 框架集成

**cap4k-ddd-starter（版本 0.4.2-SNAPSHOT）：**

- 提供 DDD 构建块和基础设施
- 带拦截器的事件处理
- Saga 编排支持
- 雪花算法 ID 生成

**only-engine（版本 0.1.3-SNAPSHOT）：**

- 提供通用工具的自定义框架
- 模块：common、captcha、redis、jimmer、json、web、doc
- 与 SpringDoc 集成的 API 文档

**Jimmer（版本 0.9.106）：**

- 用于持久化的 ORM 框架
- 基于 KSP 的编译时代码生成
- DTO 投影支持
- 配置：`jimmer.language: kotlin`

**Spring Data JPA：**

- 实体扫描：`edu.only4.danmuku.domain.aggregates`
- 仓储扫描：`edu.only4.danmuku.adapter.domain.repositories`
- 通过 kapt 集成 QueryDSL

## 配置

### 数据库

- MySQL 数据库：`only_danmuku`
- 连接：`jdbc:mysql://localhost:3306/only_danmuku`
- 默认凭证：root/123456
- Druid 连接池（初始：30，最大：100）
- JPA DDL：none（由迁移管理）

### 应用设置

- 服务器端口：8081
- 基础包：`edu.only4.danmuku`
- API 文档在 `/swagger-ui.html`
- Redis：localhost:6379（密码：123456）
- Redisson 客户端名称：ONLY-DANMUKU

### 环境设置

Maven 仓库需要在 `gradle.properties` 中配置阿里云凭证：

- `aliyun.maven.username`
- `aliyun.maven.password`

## 代码生成模板

项目结构由 `cap4k-ddd-codegen-template-multi-nested.json` 定义，将设计文件映射到：

- 命令 → `application/commands/{{ path }}/`
- 查询 → `application/queries/{{ path }}/`
- 查询处理器 → `adapter/application/queries/`
- 聚合 → `domain/aggregates/{{ path }}/`
- 领域事件 → `domain/aggregates/events/`
- 仓储 → `adapter/domain/repositories/{{ path }}/`
- API 控制器 → `adapter/portal/api/`

添加新功能时，在 `design/` 中创建以 `_gen.json` 为后缀的设计 JSON 文件，并运行 `./gradlew codegen`。

## 开发工作流

1. **添加新功能：**
    - 在 `design/*_gen.json` 中创建设计文件
    - 运行 `./gradlew codegen` 生成脚手架
    - 在生成的文件中实现业务逻辑
    - 领域逻辑放在聚合中
    - 应用逻辑在命令/查询处理器中
    - API 端点在 portal 控制器中

2. **实现控制器（CQRS 模式）：**

   在 `adapter/portal/api/` 中实现新控制器时，遵循以下步骤：

    1. **识别所需的命令和查询**
        - 分析控制器需求，确定需要哪些命令（写操作）和查询（读操作）
        - 示例：对于分类管理，您可能需要：
            - 命令：`CreateCategoryCmd`、`UpdateCategoryInfoCmd`、`DeleteCategoryCmd`、`UpdateCategorySortOrderCmd`
            - 查询：`GetCategoryListQry`、`GetCategoryTreeQry`

    2. **检查现有聚合并补充缺失的命令/查询定义**
        - **首先**：检查 `design/aggregate/` 目录中是否已有所需的命令和查询定义
            - 每个子目录代表一个现有聚合（例如，`video_comment/`、`video_danmuku/`）
            - 阅读各聚合中的 `_gen.json` 文件查看可用的命令和查询
            - 在应用层搜索确认现有实现：
                - 命令位置：`only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/commands/`
                - 查询位置：`only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/queries/`
        - **其次**：仅为缺失的功能在 `design/extra/` 中创建新的设计文件
            - `design/aggregate/` = 已定义的聚合（不要修改）
            - `design/extra/` = 现有聚合的附加命令/查询（仅在需要时添加）
            - 永远不要重复聚合中已存在的命令/查询
        - **然后**：运行 `./gradlew codegen` 生成新定义的命令/查询代码
        - **重要**：定义每个新命令/查询的契约及其请求/响应结构

    3. **完善输入/输出参数**
        - 确保所有命令请求具有完整的输入参数
        - 仔细定义查询响应类型：
            - **单个元素**：`Response`（单个对象）
            - **列表元素**：`List<Response>`（集合）
            - **分页元素**：`PageData<Response>`（分页集合）
        - 来自 `GetCategoryListQry` 的示例：
          ```kotlin
          // 返回分类响应列表
          val listResult: List<GetCategoryListQry.Response> = Mediator.queries.send(...)
          // 返回弹幕分页响应
          val pageResult: PageData<GetCategoryListQry.Response> = Mediator.queries.send(...)
          ```

    4. **优化 Payload 请求/响应结构**
        - **重要**：在定义 payload 结构之前，检查接口是列表接口还是分页接口
        - **列表接口**（返回集合）：控制器返回类型应为 `List<Payload.Response>`
          ```kotlin
          // ✅ 正确：列表接口
          @PostMapping("/loadCategory")
          fun load(@RequestBody request: Request): List<AdminCategoryLoad.Response> {
              return listResult.map { transform(it) }
          }
          ```
        - **分页接口**（返回分页）：控制器返回类型应为 `PageData<Payload.ItemType>`
          ```kotlin
          // ✅ 正确：负载定义
          class Request() : PageParam()
          
          // ✅ 正确：分页接口
          @PostMapping("/loadDanmu")
          fun load(@RequestBody request: Request): PageData<AdminInteractLoadDanmu.Response> {
              val pageData = PageData<AdminInteractLoadDanmu.DanmuItem>()
              pageData.list = queryResult.list.map { transform(it) }
              pageData.pageNo = queryResult.pageNo
              pageData.totalCount = queryResult.totalCount
              return pageData
          }
          ```
        - **重要**：不要在 payload 定义中使用泛型 `Map<String, Any>` 或 `List<Any>`
        - 始终创建特定的数据类以获得更好的类型安全性和前端可用性
        - 在 payload 对象内定义嵌套数据类以实现封装
        - 示例 - **错误实践**（避免这样做）：
          ```kotlin
          data class Response(
              var preDayData: Map<String, Any>? = null,  // ❌ 前端难以使用
              var list: List<Any>? = null                // ❌ 没有类型信息
          )
          ```
        - 示例 - **正确实践**（遵循这个）：
          ```kotlin
          object AdminIndexGetActualTimeStatistics {
              data class Response(
                  var preDayData: StatisticsData? = null,      // ✅ 强类型
                  var totalCountInfo: StatisticsData? = null   // ✅ 结构清晰
              )

              // 嵌套数据类以实现更好的封装
              data class StatisticsData(
                  var videoViewCount: Int = 0,
                  var videoLikeCount: Int = 0,
                  var videoCommentCount: Int = 0
              )
          }
          ```
        - 使用类型化数据类的好处：
            - 为前端开发人员提供 IDE 自动完成支持
            - 编译时类型检查
            - 清晰的 API 文档
            - 更容易维护和重构

    5. **使用命令和查询实现控制器**
        - 对写操作使用 `Mediator.commands.send()`
        - 对读操作使用 `Mediator.queries.send()`
        - 将命令/查询响应转换为控制器响应格式
        - **重要**：在初始实现阶段，仅关注控制器层逻辑
            - 暂时不要实现命令处理器或查询处理器的业务逻辑
            - 处理器可以暂时保持为空或返回模拟数据
            - 实际的处理器实现将在单独的步骤中完成
        - 示例结构：
          ```kotlin
          @RestController
          @RequestMapping("/admin/category")
          class AdminCategoryController {
              @PostMapping("/loadCategory")
              fun load(@RequestBody request: Request): Response {
                  // 查询处理器逻辑将在稍后实现
                  val result = Mediator.queries.send(GetCategoryListQry.Request())
                  return Response(list = result.map { transform(it) })
              }

              @PostMapping("/saveCategory")
              fun save(@RequestBody request: Request): Response {
                  // 命令处理器逻辑将在稍后实现
                  Mediator.commands.send(CreateCategoryCmd.Request(...))
                  return Response()
              }
          }
          ```

    6. **处理条件逻辑**
        - 使用请求参数确定行为（例如，创建 vs 更新）
        - 根据需求选择适当的查询（例如，树形 vs 列表格式）
        - 示例：
          ```kotlin
          if (request.categoryId == null) {
              // 创建新实体
              Mediator.commands.send(CreateCategoryCmd.Request(...))
          } else {
              // 更新现有实体
              Mediator.commands.send(UpdateCategoryInfoCmd.Request(...))
          }
          ```

    7. **数据转换**
        - 使用类型化数据类将领域查询响应转换为控制器响应格式
        - 避免使用 `Map` - 改用特定的数据类
        - 对复杂转换使用辅助函数
        - 来自 `AdminIndexController` 的示例：
          ```kotlin
          // ✅ 正确：转换为类型化数据类
          val preDayDataObj = AdminIndexGetActualTimeStatistics.StatisticsData(
              videoViewCount = preDayData.videoViewCount,
              videoLikeCount = preDayData.videoLikeCount,
              videoCommentCount = preDayData.videoCommentCount,
              videoShareCount = preDayData.videoShareCount,
              userFollowCount = preDayData.userFollowCount,
              userLoginCount = preDayData.userLoginCount
          )

          // ✅ 正确：将列表转换为类型化对象
          val resultList = weekData.map { item ->
              AdminIndexGetWeekStatistics.WeekStatisticsItem(
                  statisticsDate = item.date,
                  statisticsCount = item.count
              )
          }
          ```

   **实现工作流总结：**
    - 步骤 1：识别所需的命令和查询
    - 步骤 2：检查现有聚合，仅为缺失功能在 `design/extra/` 中添加新定义，运行 `./gradlew codegen` 生成代码
    - 步骤 3：完善命令/查询的输入/输出参数（Request/Response）
    - 步骤 4：使用类型化数据类优化 payload 结构（不使用 Map/Any）
    - 步骤 5：通过 Mediator 调用实现控制器层
    - 步骤 6：将处理器实现留待下次迭代
    - 步骤 7：单独实现处理器业务逻辑（未来步骤）

3. **使用聚合：**
    - 聚合根扩展带 `Agg*` 前缀的生成基类
    - 实体使用 JPA 注解
    - 领域事件在聚合内引发
    - 对复杂的创建逻辑使用工厂

4. **测试：**
    - 测试配置在 `buildSrc/src/main/kotlin/kotlin-jvm.gradle.kts`
    - JUnit 5，10 分钟超时
    - JVM 参数：`-Xmx2g -Xms512m`

5. **约定插件：**
    - `buildSrc/` 中的共享构建逻辑
    - 约定：`buildsrc.convention.kotlin-jvm`
    - 应用 Kotlin JVM、Spring 和 JPA 插件
    - 配置了 Java 17 工具链

## 重要注意事项

- **不要修改标记为 `[cap4k-ddd-codegen-gradle-plugin:do-not-overwrite]` 的文件** - 这些文件受保护，不会被代码生成覆盖
- 适配器层的 portal 包最近进行了重构（见提交 863b04d）
- 领域事件同时使用同步订阅者和异步集成事件
- Jimmer 需要 KSP 处理 - 确保 `build/generated/ksp/` 在源集中
- QueryDSL 元模型通过 kapt 生成，实体更改后需要清理构建

### ⚠️ 关键：在创建新命令/查询之前检查现有聚合

**实现新控制器时始终遵循此工作流：**

1. **首先**：检查 `design/aggregate/` 目录中的现有聚合定义
   - 每个子目录代表一个现有聚合（例如，`video_comment/`、`video_danmuku/`）
   - 阅读每个聚合中的 `_gen.json` 文件以查看可用的命令和查询
   - 示例：`design/aggregate/video_comment/_gen.json` 包含 `DelCommentCmd`、`VideoCommentPageQry` 等

2. **其次**：在应用层中搜索现有的命令/查询
   - 命令位置：`only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/commands/`
   - 查询位置：`only-danmuku-application/src/main/kotlin/edu/only4/danmuku/application/queries/`
   - 在创建新实现之前使用文件搜索查找现有实现

3. **第三**：仅为真正缺失的功能在 `design/extra/` 中创建新的设计文件
   - `design/aggregate/` = 已定义的聚合（不要修改）
   - `design/extra/` = 现有聚合的附加命令/查询（如需要，在此添加）
   - 永远不要重复聚合中已存在的命令/查询

4. **第四**：使用正确的包路径使用现有的命令/查询
   - 现有：`edu.only4.danmuku.application.commands.video_comment.DelCommentCmd`
   - ❌ 错误：`edu.only4.danmuku.application.commands.comment.DeleteCommentCmd`
   - 仔细检查实际的文件位置和导入语句

**经验教训**：在之前的实现中，我错误地创建了 `design/extra/interact_gen.json`，其中包含已在 `design/aggregate/video_comment/` 和 `design/aggregate/video_danmuku/` 中存在的重复命令/查询。这导致导入错误，因为我使用了不存在的包路径。始终先验证现有聚合以避免此错误。

**快速检查命令**：
```bash
# 列出所有现有聚合
ls -la design/aggregate/

# 检查特定聚合定义
cat design/aggregate/video_comment/_gen.json

# 搜索现有命令
find only-danmuku-application -name "*Cmd.kt" -type f

# 搜索现有查询
find only-danmuku-application -name "*Qry.kt" -type f
```
