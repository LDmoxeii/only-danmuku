# {功能名称}流程设计文档

> 基于 easylive-java 项目需求，按照 DDD 事件驱动模式设计

## 📋 业务需求概述

{1-2 句话简要描述业务场景}

---

## 📊 完整流程图

### ASCII 流程图

```
┌─────────────────────────────────────────────────────────────────┐
│ 请求：{HTTP 方法} {路径}                                          │
│ Payload:                                                        │
│ {                                                               │
│   "field1": "value1",                                           │
│   "field2": "value2"                                            │
│ }                                                               │
└────────────────────────────┬────────────────────────────────────┘
                             ↓
┌─────────────────────────────────────────────────────────────────┐
│ 命令：{CommandName}                                              │
│ 状态：✅/❌ {已定义/缺失} (design/...)                            │
│                                                                 │
│ 验证器：                                                         │
│   ├─ @Validator1 ✅                                             │
│   └─ @Validator2 ❌                                             │
│                                                                 │
│ 处理逻辑：                                                       │
│   1. ...                                                        │
│   2. ...                                                        │
└────────────────────────────┬────────────────────────────────────┘
                             ↓
┌─────────────────────────────────────────────────────────────────┐
│ 领域事件：{DomainEventName}                                      │
│ 状态：✅/❌ {已定义/缺失} (design/...)                            │
│                                                                 │
│ 事件载荷：                                                       │
│ {                                                               │
│   "field1": value1,                                             │
│   "field2": value2                                              │
│ }                                                               │
└────────────────────────────┬────────────────────────────────────┘
                             ↓
        ┌────────────────────┴────────────────────┐
        ↓                                         ↓
┌──────────────────────────┐          ┌──────────────────────────┐
│ 事件处理器 #1            │          │ 事件处理器 #2 (可选)     │
│ 监听: {EventName}        │          │ 监听: {EventName}        │
│ 触发: {CommandName}      │          │ 触发: ...                │
│ 状态: ❌/⚪               │          │ 状态: ⚪                 │
└──────┬───────────────────┘          └──────────────────────────┘
       ↓
┌─────────────────────────────────────────────────────────────────┐
│ 命令：{NextCommandName}                                          │
│ 状态：✅/❌                                                       │
│                                                                 │
│ 处理逻辑：                                                       │
│   1. ...                                                        │
│   2. ...                                                        │
└────────────────────────────┬────────────────────────────────────┘
                             ↓
                      ✅ 流程完成
```

### Mermaid 可视化流程图

```mermaid
graph TD
    A[请求: {HTTP方法} {路径}<br/>{参数说明}] --> B[命令: {CommandName} ✅/❌]

    B --> B1[验证器: @Validator1 ✅]
    B --> B2[验证器: @Validator2 ❌]
    B --> B3[{Factory/Logic}<br/>{简要说明}]

    B3 --> C[领域事件: {EventName} ✅/❌<br/>{事件载荷字段}]

    C --> D1[事件处理器 #1: {HandlerName} ❌<br/>监听: {Event} → 触发: {Command}]
    C --> D2[事件处理器 #2: {可选功能} ⚪<br/>可选扩展]

    D1 --> E[命令: {NextCommand} ✅/❌]
    E --> E1[查询: {QueryName} ❌<br/>{查询说明}]
    E --> E2[{Factory/Logic}<br/>{简要说明}]

    E2 --> F[领域事件: {FinalEvent} ✅/❌<br/>{事件载荷}]

    F --> G[✅ 流程完成]

    style A fill:#E3F2FD,stroke:#1976D2,stroke-width:3px
    style B fill:#C8E6C9,stroke:#388E3C,stroke-width:2px
    style C fill:#C8E6C9,stroke:#388E3C,stroke-width:2px
    style E fill:#C8E6C9,stroke:#388E3C,stroke-width:2px
    style F fill:#C8E6C9,stroke:#388E3C,stroke-width:2px
    style G fill:#C8E6C9,stroke:#388E3C,stroke-width:3px
    style B2 fill:#FFCDD2,stroke:#D32F2F,stroke-width:2px
    style D1 fill:#FFCDD2,stroke:#D32F2F,stroke-width:2px
    style E1 fill:#FFCDD2,stroke:#D32F2F,stroke-width:2px
    style D2 fill:#FFF9C4,stroke:#F57C00,stroke-width:2px
```

**图例说明**：
- 🔵 蓝色：请求入口
- 🟢 绿色：已存在的设计（✅ 可直接使用）
- 🔴 红色：缺失的设计（❌ 需实现）
- 🟡 黄色：可选扩展（⚪）

---

## 📦 设计元素清单

### ✅ 已存在的设计

#### 命令 (Commands)

| 命令 | 描述 | 状态 | 位置 |
|------|------|------|------|
| `XxxCmd` | 描述 | ✅ 已定义 | `design/aggregate/xxx/_gen.json:行号` |

#### 领域事件 (Domain Events)

| 事件 | 描述 | 触发时机 | 状态 | 位置 |
|------|------|----------|------|------|
| `XxxDomainEvent` | 描述 | 时机 | ✅ 已定义 | `design/aggregate/xxx/_gen.json:行号` |

#### 查询 (Queries)

| 查询 | 描述 | 状态 | 位置 |
|------|------|------|------|
| `GetXxxQry` | 描述 | ✅ 已定义 | `design/aggregate/xxx/_gen.json:行号` |

---

### ❌ 缺失的设计清单

#### 需要补充的命令

| 序号 | 命令名称 | 描述 | 建议位置 | 优先级 |
|-----|---------|------|----------|-------|
| 1   | `XxxCmd` | 描述 | `design/extra/xxx_gen.json` | P0 |

**JSON 定义**（需新增到 `design/extra/xxx_gen.json`）：
```json
{
  "cmd": [
    {
      "package": "xxx",
      "name": "XxxCmd",
      "desc": "描述"
    }
  ]
}
```

#### 需要补充的领域事件

| 序号 | 事件名称 | 描述 | 触发时机 | 建议位置 | 优先级 |
|-----|---------|------|----------|----------|-------|
| 1   | `XxxDomainEvent` | 描述 | 时机 | `design/extra/xxx_gen.json` | P0 |

**JSON 定义**（需新增到 `design/extra/xxx_gen.json`）：
```json
{
  "de": [
    {
      "package": "xxx",
      "name": "XxxDomainEvent",
      "desc": "描述",
      "aggregates": ["AggregateName"],
      "entity": "EntityName",
      "persist": true
    }
  ]
}
```

#### 需要补充的查询

| 序号 | 查询名称 | 描述 | 返回值 | 建议位置 | 优先级 |
|-----|---------|------|--------|----------|-------|
| 1   | `GetXxxQry` | 描述 | 类型 | `design/extra/xxx_gen.json` | P0 |

**JSON 定义**（需新增到 `design/extra/xxx_gen.json`）：
```json
{
  "qry": [
    {
      "package": "xxx",
      "name": "GetXxxQry",
      "desc": "描述"
    }
  ]
}
```

#### 需要补充的验证器

| 序号 | 验证器名称 | 描述 | 依赖查询 | 实现路径 | 优先级 |
|-----|-----------|------|----------|----------|-------|
| 1   | `@XxxValidator` | 描述 | `XxxQry` | `application/.../validater/XxxValidator.kt` | P0 |

#### 需要补充的事件处理器

| 序号 | 处理器名称 | 监听事件 | 触发命令 | 实现路径 | 优先级 |
|-----|-----------|----------|----------|----------|-------|
| 1   | `XxxEventHandler` | `XxxEvent` | `XxxCmd` | `adapter/.../events/XxxEventHandler.kt` | P0 |

**优先级说明**：
- **P0**：核心功能，必须实现
- **P1**：重要功能，建议实现
- **P2**：可选功能，后续扩展

---

**文档版本**：v1.0
**创建时间**：{YYYY-MM-DD}
**维护者**：开发团队
