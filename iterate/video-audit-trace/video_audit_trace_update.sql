-- 视频审核追溯 SQL 变更

-- 若存在则先删除后重建
DROP TABLE IF EXISTS `video_audit_trace`;
CREATE TABLE `video_audit_trace` (
    `id`              bigint       NOT NULL COMMENT 'ID',
    `video_id`        bigint       NOT NULL COMMENT '视频ID',
    `audit_status`    tinyint(1)   NOT NULL DEFAULT 0 COMMENT '审核状态 @E=0:UNKNOW:未知|1:PASSED:审核通过|2:FAILED:审核不通过;@T=AuditStatus',
    `reviewer_id`     bigint                DEFAULT NULL COMMENT '审核人ID，可为空',
    `reviewer_type`   tinyint(1)   NOT NULL DEFAULT 0 COMMENT '审核人类型 @E=0:UNKNOW:未知类型|1:SYS_USER:系统管理员;@T=UserType',
    `reason`          varchar(255)         DEFAULT NULL COMMENT '审核备注/失败原因',
    `occur_time`      bigint       NOT NULL COMMENT '审核发生时间',
    `create_user_id`  bigint               DEFAULT NULL COMMENT '创建人ID',
    `create_by`       varchar(32)          DEFAULT NULL COMMENT '创建人名称',
    `create_time`     bigint               DEFAULT NULL COMMENT '创建时间',
    `update_user_id`  bigint               DEFAULT NULL COMMENT '更新人ID',
    `update_by`       varchar(32)          DEFAULT NULL COMMENT '更新人名称',
    `update_time`     bigint               DEFAULT NULL COMMENT '更新时间',
    `deleted`         bigint       NOT NULL DEFAULT 0 COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_video_audit_trace_video_time` (`video_id`, `occur_time`) USING BTREE,
    INDEX `idx_video_audit_trace_status_time` (`audit_status`, `occur_time`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '视频审核追溯记录;';
