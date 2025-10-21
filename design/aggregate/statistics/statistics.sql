-- ----------------------------
-- Table structure for statistics
-- ----------------------------
DROP TABLE IF EXISTS statistics;
CREATE TABLE statistics
    (
    `id`               bigint      NOT NULL COMMENT 'ID',
    `customer_id`      bigint      NOT NULL COMMENT '用户ID',
    `data_type`        tinyint(1)  NOT NULL DEFAULT 0 COMMENT '数据统计类型 @E=0:UNKNOW:未知类型|1:VIDEO_VIEW:视频观看|2:VIDEO_LIKE:视频点赞|3:VIDEO_COMMENT:视频评论|4:VIDEO_SHARE:视频分享|5:USER_FOLLOW:用户关注|6:USER_LOGIN:用户登录;@T=StatisticsDataType',
    `statistics_count` int(11)     NULL     DEFAULT NULL COMMENT '统计数量',
    `statistics_date`  bigint      NOT NULL COMMENT '统计日期（秒级时间戳）',
    `create_user_id`   bigint      NULL     DEFAULT NULL COMMENT '创建人ID',
    `create_by`        varchar(32) NULL     DEFAULT NULL COMMENT '创建人名称',
    `create_time`      bigint      NULL     DEFAULT NULL COMMENT '创建时间（秒级时间戳）',
    `update_user_id`   bigint      NULL     DEFAULT NULL COMMENT '更新人ID',
    `update_by`        varchar(32) NULL     DEFAULT NULL COMMENT '更新人名称',
    `update_time`      bigint      NULL     DEFAULT NULL COMMENT '更新时间（秒级时间戳）',
    `deleted`          bigint               DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '统计信息;@Spe;@Fac;'
    ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Test data for statistics
-- ----------------------------
-- 前一天统计数据 (假设今天是 2025-10-18，前一天是 2025-10-17)
-- 统计日期使用当天零点的秒级时间戳：2025-10-18 00:00:00 = 1760716800
-- 视频观看统计
INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (1, 1001, 1, 1500, 1760716800, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (2, 1002, 1, 2300, 1760716800, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (3, 1003, 1, 800, 1760716800, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

-- 视频点赞统计
INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (4, 1001, 2, 320, 1760716800, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (5, 1002, 2, 580, 1760716800, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

-- 视频评论统计
INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (6, 1001, 3, 150, 1760716800, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (7, 1002, 3, 230, 1760716800, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (8, 1003, 3, 90, 1760716800, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

-- 视频分享统计
INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (9, 1001, 4, 45, 1760716800, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (10, 1002, 4, 78, 1760716800, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

-- 用户关注统计
INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (11, 1001, 5, 120, 1760716800, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (12, 1002, 5, 95, 1760716800, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (13, 1003, 5, 68, 1760716800, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

-- 用户登录统计
INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (14, 1001, 6, 350, 1760716800, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (15, 1002, 6, 420, 1760716800, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (16, 1003, 6, 280, 1760716800, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

-- 前两天统计数据 (2025-10-17 00:00:00 = 1760630400)
INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (17, 1001, 1, 1200, 1760630400, 1, 'system', 1729180800, 1, 'system', 1729180800, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (18, 1001, 2, 280, 1760630400, 1, 'system', 1729180800, 1, 'system', 1729180800, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (19, 1001, 3, 120, 1760630400, 1, 'system', 1729180800, 1, 'system', 1729180800, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (20, 1001, 4, 35, 1760630400, 1, 'system', 1729180800, 1, 'system', 1729180800, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (21, 1001, 5, 85, 1760630400, 1, 'system', 1729180800, 1, 'system', 1729180800, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (22, 1001, 6, 300, 1760630400, 1, 'system', 1729180800, 1, 'system', 1729180800, 0);

-- 今天的统计数据 (2024-10-19 00:00:00 = 1760716800) - 用于对比
INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (23, 1001, 1, 1800, 1760716800, 1, 'system', 1729353600, 1, 'system', 1729353600, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (24, 1002, 1, 2500, 1760716800, 1, 'system', 1729353600, 1, 'system', 1729353600, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (25, 1001, 2, 400, 1760716800, 1, 'system', 1729353600, 1, 'system', 1729353600, 0);
