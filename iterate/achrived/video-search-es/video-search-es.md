# 视频搜索 ES 化迭代说明

## 一、背景与目标
- 现有 `CompatibleVideoController#searchVideo` 走数据库模糊查询，响应慢且难以扩展权重/高亮。
- 本迭代引入 Elasticsearch 作为搜索主引擎，保持接口不变，提升搜索性能与相关性。
- 数据同步通过 CLI 实现，并在 `VideoBasicsSyncedDomainEventSubscriber` 中触发写入 ES。

## 二、范围
1) 新增 ES 查询：用于 `searchVideo`，替换 `GetVideoPageQry` 的模糊查询。  
2) 新增 ES 同步 CLI：根据视频基础信息构建文档并 upsert 到 ES。  
3) 事件触发：`VideoBasicsSyncedDomainEventSubscriber` 监听事件并调用 CLI 同步。  
4) 索引设计与映射策略。  
不在本迭代：搜索建议/联想、ES 删除同步、全量重建任务、其他接口的 ES 化。

## 三、设计要点
### 3.1 索引与文档结构
索引建议：`only-danmuku-video`（可在配置中可配置化）。  
文档主键：`videoId`（字符串）。

建议字段：
- 基础：`videoId`, `videoPostId`, `customerId`, `videoName`, `videoCover`
- 分类：`pCategoryId`, `categoryId`, `categoryFullName`
- 文本：`tags`, `introduction`
- 统计：`playCount`, `likeCount`, `danmukuCount`, `commentCount`, `coinCount`, `collectCount`
- 其他：`duration`, `postType`, `recommendType`, `createTime`, `lastUpdateTime`, `lastPlayTime`
- 展示：`nickName`, `avatar`

映射建议：
- `videoName/tags/introduction/categoryFullName/nickName` 使用 `text`，并保留 `keyword` 子字段。
- 其余为 `keyword/long/int/date`。
- 分析器：优先 `ik_max_word`（若集群支持），否则使用 `standard`。

### 3.2 查询策略（SearchVideoByEsQry）
默认搜索字段与权重：
- `videoName^3`, `tags^2`, `introduction`, `categoryFullName`, `nickName`
排序：
- 默认 `_score desc, lastUpdateTime desc`
- 若 `orderType` 存在，可扩展为 `playCount desc` 或 `lastPlayTime desc`（按前端约定）。

### 3.3 同步策略（SyncVideoBasicsToEsCli）
触发点：
- `VideoBasicsSyncedDomainEventSubscriber` 监听 `VideoBasicsSyncedDomainEvent`。
- 使用 `event.entity` 作为主数据来源，必要时补充用户/分类信息（nickName/avatar/categoryFullName）。

幂等：
- `videoId` 作为文档 ID，upsert 即可。

### 3.4 兼容与失败处理
- ES 查询异常时建议降级到原 `GetVideoPageQry`（可选，作为容灾策略）。
- 同步失败记录日志，不阻断主业务流程。

## 四、流程说明
```
TransferVideoToProductionCmd
  -> Video.syncFromBasics
     -> VideoBasicsSyncedDomainEvent
        -> VideoBasicsSyncedDomainEventSubscriber
           -> SyncVideoBasicsToEsCli (upsert 文档)

CompatibleVideoController.searchVideo
  -> SearchVideoByEsQry
  -> PageData 返回
```

## 五、接口与影响面
### 5.1 Controller
- `CompatibleVideoController#searchVideo`：改为调用 ES 查询。

### 5.2 Query
- 新增 `SearchVideoByEsQry`（返回分页 + 列表数据）。

### 5.3 CLI
- 新增 `SyncVideoBasicsToEsCli`（写入/更新 ES 文档）。

### 5.4 Event
- `VideoBasicsSyncedDomainEventSubscriber` 调用 CLI。

## 六、数据库变更
无数据库变更。

## 七、交付物
- 设计文档：`iterate/video-search-es/video-search-es.md`
- DDD 设计 JSON：`iterate/video-search-es/video_search_es_gen.json`
- SQL：`iterate/video-search-es/video_search_es_update.sql`
