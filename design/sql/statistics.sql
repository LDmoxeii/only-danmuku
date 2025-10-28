-- ----------------------------
-- Table structure for statistics
-- ----------------------------
DROP TABLE IF EXISTS statistics;
CREATE TABLE statistics
    (
    `id`               bigint      NOT NULL COMMENT 'ID',
    `customer_id`      bigint      NOT NULL COMMENT '用户ID',
    `data_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '数据统计类型 @E=0:UNKNOW:未知类型|1:PLAY:播放量|2:FANS:粉丝|3:LIKE:点赞|4:COLLECTION:收藏|5:COIN:投币|6:COMMENT:评论|7:DANMUKU:弹幕;@T=StatisticsDataType',
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
-- 前一天统计数据 (假设今天是 2025-10-25 1761321600，前一天是 2025-10-24 1761235200)
-- 播放量统计 (PLAY = 0)
INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (1, 1001, 0, 1500, 1761321600, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (2, 1002, 0, 2300, 1761321600, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (3, 1003, 0, 800, 1761321600, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

-- 粉丝统计 (FANS = 1)
INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (4, 1001, 1, 120, 1761321600, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (5, 1002, 1, 95, 1761321600, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (6, 1003, 1, 68, 1761321600, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

-- 点赞统计 (LIKE = 2)
INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (7, 1001, 2, 320, 1761321600, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (8, 1002, 2, 580, 1761321600, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

-- 收藏统计 (COLLECTION = 3)
INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (9, 1001, 3, 150, 1761321600, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (10, 1002, 3, 230, 1761321600, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (11, 1003, 3, 90, 1761321600, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

-- 投币统计 (COIN = 4)
INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (12, 1001, 4, 45, 1761321600, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (13, 1002, 4, 78, 1761321600, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

-- 评论统计 (COMMENT = 5)
INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (14, 1001, 5, 85, 1761321600, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (15, 1002, 5, 160, 1761321600, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (16, 1003, 5, 75, 1761321600, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

-- 弹幕统计 (DANMU = 6)
INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (17, 1001, 6, 350, 1761321600, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (18, 1002, 6, 420, 1761321600, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (19, 1003, 6, 280, 1761321600, 1, 'system', 1729267200, 1, 'system', 1729267200, 0);

-- 前两天统计数据 (2025-10-23 00:00:00 = 1761148800)
INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (20, 1001, 0, 1200, 1761148800, 1, 'system', 1729180800, 1, 'system', 1729180800, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (21, 1001, 1, 100, 1761148800, 1, 'system', 1729180800, 1, 'system', 1729180800, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (22, 1001, 2, 280, 1761148800, 1, 'system', 1729180800, 1, 'system', 1729180800, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (23, 1001, 3, 120, 1761148800, 1, 'system', 1729180800, 1, 'system', 1729180800, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (24, 1001, 4, 35, 1761148800, 1, 'system', 1729180800, 1, 'system', 1729180800, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (25, 1001, 5, 70, 1761148800, 1, 'system', 1729180800, 1, 'system', 1729180800, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (26, 1001, 6, 300, 1761148800, 1, 'system', 1729180800, 1, 'system', 1729180800, 0);

-- 最近7天的统计数据（用于周统计）
-- 2025-10-12 00:00:00 = 1761062400
INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (27, 1001, 0, 1100, 1761062400, 1, 'system', 1729094400, 1, 'system', 1729094400, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (28, 1001, 1, 90, 1761062400, 1, 'system', 1729094400, 1, 'system', 1729094400, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (29, 1001, 2, 250, 1761062400, 1, 'system', 1729094400, 1, 'system', 1729094400, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (30, 1001, 3, 110, 1761062400, 1, 'system', 1729094400, 1, 'system', 1729094400, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (31, 1001, 4, 30, 1761062400, 1, 'system', 1729094400, 1, 'system', 1729094400, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (32, 1001, 5, 65, 1761062400, 1, 'system', 1729094400, 1, 'system', 1729094400, 0);

INSERT INTO statistics (id, customer_id, data_type, statistics_count, statistics_date, create_user_id, create_by,
                        create_time, update_user_id, update_by, update_time, deleted)
VALUES (33, 1001, 6, 280, 1761062400, 1, 'system', 1729094400, 1, 'system', 1729094400, 0);
