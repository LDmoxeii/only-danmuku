-- 用户行为监控 - 登录日志与异常操作 SQL 变更

-- 1. 登录日志表（若存在则删除后重建）
DROP TABLE IF EXISTS `user_login_log`;
CREATE TABLE `user_login_log` (
    `id`              bigint       NOT NULL COMMENT 'ID',
    `user_id`         bigint                DEFAULT NULL COMMENT '用户ID，可为空（未知用户）',
    `user_type`       tinyint(1)            NOT NULL DEFAULT 0 COMMENT '用户类型 @E=0:UNKNOW:未知类型|1:SYS_USER:系统管理员;@T=UserType',
    `login_name`      varchar(150) NOT NULL COMMENT '登录名（邮箱或手机号）',
    `login_type`      tinyint(1)   NOT NULL DEFAULT 0 COMMENT '登录类型 @E=0:UNKNOW:未知登录类型|1:PASSWORD:密码登录|2:SMS_CODE:短信验证码登录|3:LOGOUT:退出登录;@T=LoginType',
    `result`          tinyint(1)   NOT NULL DEFAULT 0 COMMENT '登录结果 @E=0:UNKNOW:未知结果|1:SUCCESS:成功|2:FAILURE:失败;@T=LoginResult',
    `ip`              varchar(45)  NOT NULL COMMENT '登录IP',
    `user_agent`      varchar(255)          DEFAULT NULL COMMENT 'User-Agent',
    `reason`          varchar(200)          DEFAULT NULL COMMENT '失败原因描述',
    `occur_time`      bigint       NOT NULL COMMENT '发生时间（秒级或毫秒级时间戳）',
    `create_user_id`  bigint                NULL DEFAULT NULL COMMENT '创建人ID',
    `create_by`       varchar(32)           NULL DEFAULT NULL COMMENT '创建人名称',
    `create_time`     bigint                NULL DEFAULT NULL COMMENT '创建时间',
    `update_user_id`  bigint                NULL DEFAULT NULL COMMENT '更新人ID',
    `update_by`       varchar(32)           NULL DEFAULT NULL COMMENT '更新人名称',
    `update_time`     bigint                NULL DEFAULT NULL COMMENT '更新时间',
    `deleted`         bigint                       DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_user_login_log_user_id_time` (`user_id`, `occur_time`) USING BTREE,
    INDEX `idx_user_login_log_login_name_time` (`login_name`, `occur_time`) USING BTREE,
    INDEX `idx_user_login_log_result_time` (`result`, `occur_time`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '用户登录日志;@Spe;';


-- 2. 异常操作日志表（若存在则删除后重建）
DROP TABLE IF EXISTS `user_abnormal_operation_log`;
CREATE TABLE `user_abnormal_operation_log` (
    `id`              bigint      NOT NULL COMMENT 'ID',
    `user_id`         bigint      NOT NULL COMMENT '用户ID',
    `user_type`       tinyint(1)  NOT NULL DEFAULT 0 COMMENT '用户类型 @E=0:UNKNOW:未知类型|1:SYS_USER:系统管理员;@T=UserType',
    `op_type`         tinyint(1)  NOT NULL DEFAULT 0 COMMENT '异常类型 @E=0:UNKNOW:未知异常|1:PASSWORD_FAIL_TOO_MANY_TIMES:密码失败次数过多;@T=AbnormalOpType',
    `ip`              varchar(45) NOT NULL COMMENT '触发IP',
    `occur_time`      bigint      NOT NULL COMMENT '触发时间',
    `description`     varchar(200)         DEFAULT NULL COMMENT '异常描述',
    `extra`           text                 DEFAULT NULL COMMENT '扩展JSON，如关联登录日志ID列表等',
    `create_user_id`  bigint              NULL DEFAULT NULL COMMENT '创建人ID',
    `create_by`       varchar(32)         NULL DEFAULT NULL COMMENT '创建人名称',
    `create_time`     bigint              NULL DEFAULT NULL COMMENT '创建时间',
    `update_user_id`  bigint              NULL DEFAULT NULL COMMENT '更新人ID',
    `update_by`       varchar(32)         NULL DEFAULT NULL COMMENT '更新人名称',
    `update_time`     bigint              NULL DEFAULT NULL COMMENT '更新时间',
    `deleted`         bigint                     DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_user_abnormal_op_user_type` (`user_id`, `op_type`) USING BTREE,
    INDEX `idx_user_abnormal_op_time` (`occur_time`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '用户异常操作日志;@Spe;';
