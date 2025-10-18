-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
    (
    `id`              bigint                                                        NOT NULL COMMENT 'ID',
    `type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '帐号类型 @E=0:UNKNOW:未知类型|1:SYS_USER:系统管理员;@T=UserType',
    `email`           varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
    `password`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '密码',
    `join_time`       bigint                                                        NOT NULL COMMENT '加入时间',
    `last_login_time` bigint                                                        NULL     DEFAULT NULL COMMENT '最后登录时间',
    `last_login_ip`   varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '最后登录IP',
    `status`          tinyint(1)                                                    NOT NULL DEFAULT 1 COMMENT '0:禁用 1:正常',
    `related_id`      int(11)                                                       NULL     DEFAULT NULL COMMENT '关联ID; 用户、管理员 = 0',
    `create_user_id`  bigint                                                        NULL     DEFAULT NULL COMMENT '创建人ID',
    `create_by`       varchar(32)                                                   NULL     DEFAULT NULL COMMENT '创建人名称',
    `create_time`     bigint                                                        NULL     DEFAULT NULL COMMENT '创建时间',
    `update_user_id`  bigint                                                        NULL     DEFAULT NULL COMMENT '更新人ID',
    `update_by`       varchar(32)                                                   NULL     DEFAULT NULL COMMENT '更新人名称',
    `update_time`     bigint                                                        NULL     DEFAULT NULL COMMENT '更新时间',
    `deleted`         tinyint(1)                                                             DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_key_email` (`email`, `deleted`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '帐号;@Spe;@Fac;'
    ROW_FORMAT = Dynamic;

-- ----------------------------
-- Test data for user
-- ----------------------------
-- 用户1: 张三 - 正常状态
INSERT INTO user (id, type, email, password, join_time, last_login_time, last_login_ip, status, related_id,
                  create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (2001, 0, 'zhangsan@example.com', 'password123', 1729180800, 1729440000, '192.168.1.100', 1, 0,
        2001, 'user_zhang', 1729180800, 2001, 'user_zhang', 1729440000, 0);

-- 用户2: 李四 - 正常状态
INSERT INTO user (id, type, email, password, join_time, last_login_time, last_login_ip, status, related_id,
                  create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (2002, 0, 'lisi@example.com', 'password456', 1729184400, 1729450000, '192.168.1.101', 1, 0,
        2002, 'user_li', 1729184400, 2002, 'user_li', 1729450000, 0);

-- 用户3: 王五 - 禁用状态
INSERT INTO user (id, type, email, password, join_time, last_login_time, last_login_ip, status, related_id,
                  create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (2003, 0, 'wangwu@example.com', 'password789', 1729188000, 1729420000, '192.168.1.102', 0, 0,
        2003, 'user_wang', 1729188000, 2003, 'user_wang', 1729430000, 0);

-- 用户4: 赵六 - 正常状态
INSERT INTO user (id, type, email, password, join_time, last_login_time, last_login_ip, status, related_id,
                  create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (2004, 0, 'zhaoliu@example.com', 'password000', 1729191600, 1729460000, '192.168.1.103', 1, 0,
        2004, 'user_zhao', 1729191600, 2004, 'user_zhao', 1729460000, 0);

