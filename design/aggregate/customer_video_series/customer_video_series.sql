-- ----------------------------
-- Table structure for customer_video_series
-- ----------------------------
DROP TABLE IF EXISTS `customer_video_series`;
CREATE TABLE `customer_video_series`
    (
    `id`                 bigint                                                        NOT NULL COMMENT 'ID',
    `customer_id`        bigint                                                        NOT NULL COMMENT '用户ID',
    `series_name`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '列表名称',
    `series_description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
    `sort`               tinyint(4)                                                    NOT NULL COMMENT '排序',
    `create_user_id`     bigint                                                        NULL DEFAULT NULL COMMENT '创建人ID',
    `create_by`          varchar(32)                                                   NULL DEFAULT NULL COMMENT '创建人名称',
    `create_time`        bigint                                                        NULL DEFAULT NULL COMMENT '创建时间',
    `update_user_id`     bigint                                                        NULL DEFAULT NULL COMMENT '更新人ID',
    `update_by`          varchar(32)                                                   NULL DEFAULT NULL COMMENT '更新人名称',
    `update_time`        bigint                                                        NULL DEFAULT NULL COMMENT '更新时间',
    `deleted`            tinyint(1)                                                         DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '用户视频序列归档;@Spe;@Fac;'
    ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for customer_video_series_video
-- ----------------------------
DROP TABLE IF EXISTS `customer_video_series_video`;
CREATE TABLE `customer_video_series_video`
    (
    `id`             bigint      NOT NULL COMMENT 'ID',
    `customer_id`    bigint      NOT NULL COMMENT '用户ID',
    `series_id`      bigint      NOT NULL COMMENT '列表ID @Ref=customer_video_series',
    `video_id`       bigint      NOT NULL COMMENT '视频ID',
    `sort`           tinyint(4)  NOT NULL COMMENT '排序',
    `create_user_id` bigint      NULL DEFAULT NULL COMMENT '创建人ID',
    `create_by`      varchar(32) NULL DEFAULT NULL COMMENT '创建人名称',
    `create_time`    bigint      NULL DEFAULT NULL COMMENT '创建时间',
    `update_user_id` bigint      NULL DEFAULT NULL COMMENT '更新人ID',
    `update_by`      varchar(32) NULL DEFAULT NULL COMMENT '更新人名称',
    `update_time`    bigint      NULL DEFAULT NULL COMMENT '更新时间',
    `deleted`        tinyint(1)       DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '用户视频序列视频关联;@P=customer_video_series;'
    ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Test data for customer_video_series
-- ----------------------------
INSERT INTO customer_video_series (id, customer_id, series_name, series_description, sort, create_user_id, create_by,
                                   create_time, update_user_id, update_by, update_time, deleted)
VALUES (5001, 2001, 'Kotlin学习系列', 'Kotlin编程从入门到精通', 1, 2001, 'user_zhang', 1729267200, 2001, 'user_zhang',
        1729267200, 0),
       (5002, 2001, 'Spring Boot实战', 'Spring Boot微服务开发教程', 2, 2001, 'user_zhang', 1729270800, 2001,
        'user_zhang', 1729270800, 0),
       (5003, 2002, '数据库优化系列', 'MySQL性能优化技巧', 1, 2002, 'user_li', 1729274400, 2002, 'user_li', 1729274400,
        0);

-- ----------------------------
-- Test data for customer_video_series_video
-- ----------------------------
INSERT INTO customer_video_series_video (id, customer_id, series_id, video_id, sort, create_user_id, create_by,
                                         create_time, update_user_id, update_by, update_time, deleted)
VALUES
-- user_zhang 的 Kotlin学习系列
(6001, 2001, 5001, 1001, 1, 2001, 'user_zhang', 1729267200, 2001, 'user_zhang', 1729267200, 0),
(6002, 2001, 5001, 1005, 2, 2001, 'user_zhang', 1729267200, 2001, 'user_zhang', 1729267200, 0),
-- user_zhang 的 Spring Boot实战
(6003, 2001, 5002, 1002, 1, 2001, 'user_zhang', 1729270800, 2001, 'user_zhang', 1729270800, 0),
-- user_li 的 数据库优化系列
(6004, 2002, 5003, 1003, 1, 2002, 'user_li', 1729274400, 2002, 'user_li', 1729274400, 0);
