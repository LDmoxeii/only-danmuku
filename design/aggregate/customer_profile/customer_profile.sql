-- ----------------------------
-- Table structure for customer_profile
-- ----------------------------
DROP TABLE IF EXISTS `customer_profile`;
CREATE TABLE `customer_profile`
    (
    `id`                  bigint                                                        NOT NULL COMMENT 'ID',
    `nick_name`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '昵称',
    `avatar`              varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '头像',
    `email`               varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
    `sex`                 tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '性别 @E=0:UNKNOWN:未知|1:FEMALE:女|2:MALE:男|;@T=SexType',
    `birthday`            varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '出生日期',
    `school`              varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '学校',
    `person_introduction` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '个人简介',
    `notice_info`         varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '空间公告',
    `total_coin_count`    int(11)                                                       NOT NULL COMMENT '硬币总数量',
    `current_coin_count`  int(11)                                                       NOT NULL COMMENT '当前硬币数',
    `theme`               tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '主题 @E=0:UNKNOW:未知主题|1:LIGHT:浅色主题|2:DARK:深色主题|3:SYSTEM:跟随系统;@T=ThemeType',
    `create_user_id`      bigint                                                        NULL     DEFAULT NULL COMMENT '创建人ID',
    `create_by`           varchar(32)                                                   NULL     DEFAULT NULL COMMENT '创建人名称',
    `create_time`         bigint                                                        NULL     DEFAULT NULL COMMENT '创建时间',
    `update_user_id`      bigint                                                        NULL     DEFAULT NULL COMMENT '更新人ID',
    `update_by`           varchar(32)                                                   NULL     DEFAULT NULL COMMENT '更新人名称',
    `update_time`         bigint                                                        NULL     DEFAULT NULL COMMENT '更新时间',
    `deleted`             tinyint(1)                                                             DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_key_email` (`email`, `deleted`) USING BTREE,
    UNIQUE INDEX `idx_nick_name` (`nick_name`, `deleted`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '用户信息;@Spe;@Fac;'
    ROW_FORMAT = Dynamic;

