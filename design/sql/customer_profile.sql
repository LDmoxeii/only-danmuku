-- ----------------------------
-- Table structure for customer_profile
-- ----------------------------
DROP TABLE IF EXISTS `customer_profile`;
CREATE TABLE `customer_profile`
    (
    `id`                  bigint                                                        NOT NULL COMMENT 'ID',
    `user_id` bigint NOT NULL COMMENT '用户ID',
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
    `deleted`             bigint                                                                DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_key_email` (`email`, `deleted`) USING BTREE,
    UNIQUE INDEX `idx_nick_name` (`nick_name`, `deleted`) USING BTREE,
    INDEX `idx_user_id` (`user_id`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '用户信息;@Spe;@Fac;'
    ROW_FORMAT = Dynamic;

-- ----------------------------
-- Test data for customer_profile
-- ----------------------------
INSERT INTO customer_profile (id, user_id, nick_name, avatar, email, sex, birthday, school, person_introduction,
                              notice_info,
                              total_coin_count, current_coin_count, theme, create_user_id, create_by, create_time,
                              update_user_id, update_by, update_time, deleted)
VALUES (2001, 2001, '张三的编程日记', 'avatar_zhang.jpg', 'zhangsan@example.com', 2, '1995-06-15', '清华大学',
        '热爱编程，专注于Kotlin和Spring Boot开发', '欢迎来到我的空间！', 500, 320, 2, 2001, 'user_zhang', 1729180800,
        2001, 'user_zhang', 1729353600, 0);

INSERT INTO customer_profile (id, user_id, nick_name, avatar, email, sex, birthday, school, person_introduction,
                              notice_info,
                              total_coin_count, current_coin_count, theme, create_user_id, create_by, create_time,
                              update_user_id, update_by, update_time, deleted)
VALUES (2002, 2002, '李四学Java', 'avatar_li.jpg', 'lisi@example.com', 2, '1998-03-22', '北京大学',
        'Java后端工程师，喜欢分享技术', '一起学习，共同进步！', 800, 650, 1, 2002, 'user_li', 1729184400, 2002, 'user_li',
        1729354200, 0);

INSERT INTO customer_profile (id, user_id, nick_name, avatar, email, sex, birthday, school, person_introduction,
                              notice_info,
                              total_coin_count, current_coin_count, theme, create_user_id, create_by, create_time,
                              update_user_id, update_by, update_time, deleted)
VALUES (2003, 2003, '王五的数据库笔记', 'avatar_wang.jpg', 'wangwu@example.com', 1, '1996-11-08', '复旦大学',
        'DBA，专注数据库性能优化', '分享数据库优化经验', 1200, 980, 2, 2003, 'user_wang', 1729188000, 2003, 'user_wang',
        1729356600, 0);

INSERT INTO customer_profile (id, user_id, nick_name, avatar, email, sex, birthday, school, person_introduction,
                              notice_info,
                              total_coin_count, current_coin_count, theme, create_user_id, create_by, create_time,
                              update_user_id, update_by, update_time, deleted)
VALUES (2004, 2004, '赵六看技术', 'avatar_zhao.jpg', 'zhaoliu@example.com', 2, '1997-09-30', '浙江大学',
        '全栈开发者，喜欢学习新技术', '技术改变世界！', 600, 420, 3, 2004, 'user_zhao', 1729191600, 2004, 'user_zhao',
        1729357800, 0);


