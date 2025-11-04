-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
    (
    `id`              bigint                                                        NOT NULL COMMENT 'ID',
    `type`            tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '帐号类型 @E=0:UNKNOW:未知类型|1:SYS_USER:系统管理员;@T=UserType',
    `nick_name`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '昵称',
    `email`           varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
    `password`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '密码',
    `join_time`       bigint                                                        NOT NULL COMMENT '加入时间',
    `last_login_time` bigint                                                        NULL     DEFAULT NULL COMMENT '最后登录时间',
    `last_login_ip`   varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '最后登录IP',
    `status`          tinyint(1)                                                    NOT NULL DEFAULT 1 COMMENT '0:禁用 1:正常',
    `related_id`      bigint                                                        NULL     DEFAULT NULL COMMENT '关联ID; 用户、管理员 = 0',
    `create_user_id`  bigint                                                        NULL     DEFAULT NULL COMMENT '创建人ID',
    `create_by`       varchar(32)                                                   NULL     DEFAULT NULL COMMENT '创建人名称',
    `create_time`     bigint                                                        NULL     DEFAULT NULL COMMENT '创建时间',
    `update_user_id`  bigint                                                        NULL     DEFAULT NULL COMMENT '更新人ID',
    `update_by`       varchar(32)                                                   NULL     DEFAULT NULL COMMENT '更新人名称',
    `update_time`     bigint                                                        NULL     DEFAULT NULL COMMENT '更新时间',
    `deleted`         bigint                                                                 DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_v_email` (`email`, `deleted`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '帐号;@Spe;@Fac;'
    ROW_FORMAT = Dynamic;

INSERT INTO `user`
VALUES (2001, 0, 'zhangsan@example.com', 'admin', 'admin123', 1729180800, 1729440000, '192.168.1.100', 1, 2001, 2001,
        'user_zhang', 1729180800, 2001, 'user_zhang', 1729440000, 0),
       (231189940578013184, 1, '2649075705@qq.com', '2649075705@qq.com', 'admin123', 1761836777, 1761836935,
        '0:0:0:0:0:0:0:1', 1, 231189940775145472, NULL, NULL, NULL, NULL, NULL, NULL, 0),
       (231190007951118336, 1, '1649075705@qq.com', '1649075705@qq.com', 'admin123', 1761836793, 1761836972,
        '0:0:0:0:0:0:0:1', 1, 231190007976284160, NULL, NULL, NULL, NULL, NULL, NULL, 0);
