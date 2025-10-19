-- ----------------------------
-- Table structure for video_danmu
-- ----------------------------
DROP TABLE IF EXISTS video_danmuku;
CREATE TABLE video_danmuku
    (
    `id`             bigint                                                        NOT NULL COMMENT 'ID',
    `video_id`       bigint                                                        NOT NULL COMMENT '视频ID',
    `file_id`        bigint                                                        NOT NULL COMMENT '唯一ID',
    `customer_id`    bigint                                                        NOT NULL COMMENT '用户ID',
    `post_time`      bigint                                                        NULL DEFAULT NULL COMMENT '发布时间',
    `text`           varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容',
    `mode`           tinyint(1)                                                    NULL DEFAULT NULL COMMENT '展示位置',
    `color`          varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '颜色',
    `time`           int(11)                                                       NULL DEFAULT NULL COMMENT '展示时间',
    `create_user_id` bigint                                                        NULL DEFAULT NULL COMMENT '创建人ID',
    `create_by`      varchar(32)                                                   NULL DEFAULT NULL COMMENT '创建人名称',
    `create_time`    bigint                                                        NULL DEFAULT NULL COMMENT '创建时间',
    `update_user_id` bigint                                                        NULL DEFAULT NULL COMMENT '更新人ID',
    `update_by`      varchar(32)                                                   NULL DEFAULT NULL COMMENT '更新人名称',
    `update_time`    bigint                                                        NULL DEFAULT NULL COMMENT '更新时间',
    `deleted`        tinyint(1)                                                         DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '视频弹幕;@Spe;@Fac;'
    ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Test data for video_danmuku
-- ----------------------------
-- 注意：需要先在 video 表和 customer_profile 表插入对应的测试数据

-- 视频1的弹幕（假设 video_id=1001, 视频名称="Kotlin 入门教程"）
INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (1, 1001, 10001, 2001, 1729353600000, '讲得太好了！', 1, '#FFFFFF', 120, 2001, 'user_zhang', 1729353600, 2001,
        'user_zhang', 1729353600, 0);

INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (2, 1001, 10001, 2002, 1729354200000, '这个知识点很重要', 1, '#00FF00', 180, 2002, 'user_li', 1729354200, 2002,
        'user_li', 1729354200, 0);

INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3, 1001, 10001, 2003, 1729354800000, '老师能再详细讲解一下吗', 2, '#FF0000', 240, 2003, 'user_wang', 1729354800,
        2003, 'user_wang', 1729354800, 0);

-- 视频2的弹幕（假设 video_id=1002, 视频名称="Spring Boot 实战"）
INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (4, 1002, 10002, 2001, 1729355400000, '666', 1, '#FFFF00', 60, 2001, 'user_zhang', 1729355400, 2001,
        'user_zhang', 1729355400, 0);

INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (5, 1002, 10002, 2004, 1729356000000, 'Spring Boot真香', 1, '#00FFFF', 90, 2004, 'user_zhao', 1729356000, 2004,
        'user_zhao', 1729356000, 0);

-- 视频3的弹幕（假设 video_id=1003, 视频名称="MySQL 优化技巧"）
INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (6, 1003, 10003, 2002, 1729356600000, '学到了很多优化技巧', 1, '#FF00FF', 150, 2002, 'user_li', 1729356600, 2002,
        'user_li', 1729356600, 0);

INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (7, 1003, 10003, 2003, 1729357200000, '索引优化讲得很清楚', 2, '#FFA500', 200, 2003, 'user_wang', 1729357200,
        2003, 'user_wang', 1729357200, 0);

INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (8, 1003, 10003, 2004, 1729357800000, '收藏了，以后肯定会用到', 1, '#800080', 250, 2004, 'user_zhao', 1729357800,
        2004, 'user_zhao', 1729357800, 0);

-- 较早的弹幕（用于测试排序）
INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (9, 1001, 10001, 2004, 1729267200000, '来得早不如来得巧', 1, '#0000FF', 30, 2004, 'user_zhao', 1729267200, 2004,
        'user_zhao', 1729267200, 0);

INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (10, 1002, 10002, 2003, 1729280800000, '前排占座', 1, '#008000', 10, 2003, 'user_wang', 1729280800, 2003,
        'user_wang', 1729280800, 0);

-- ----------------------------
-- Additional test data with correct file_id values matching video_file table
-- ----------------------------
-- 视频1001 的弹幕 (file_id: 3001, 3002, 3003)
INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (11, 1001, 3001, 2001, 1729353600, '来得早不如来得巧', 1, '#0000FF', 30, 2001, 'user_zhang', 1729353600, 2001,
        'user_zhang', 1729353600, 0);

INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (12, 1001, 3001, 2002, 1729354200, '讲得太好了！', 1, '#FFFFFF', 120, 2002, 'user_li', 1729354200, 2002,
        'user_li', 1729354200, 0);

INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (13, 1001, 3001, 2003, 1729354800, '这个知识点很重要', 1, '#00FF00', 180, 2003, 'user_wang', 1729354800, 2003,
        'user_wang', 1729354800, 0);

INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (14, 1001, 3002, 2004, 1729355400, '第二部分开始了', 2, '#FF0000', 45, 2004, 'user_zhao', 1729355400, 2004,
        'user_zhao', 1729355400, 0);

INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (15, 1001, 3003, 2001, 1729356000, '学到了很多', 1, '#FFFF00', 90, 2001, 'user_zhang', 1729356000, 2001,
        'user_zhang', 1729356000, 0);

-- 视频1002 的弹幕 (file_id: 3004, 3005, 3006, 3007)
INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (16, 1002, 3004, 2003, 1729280800, '前排占座', 1, '#008000', 10, 2003, 'user_wang', 1729280800, 2003,
        'user_wang', 1729280800, 0);

INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (17, 1002, 3004, 2001, 1729355400, '666', 1, '#FFFF00', 60, 2001, 'user_zhang', 1729355400, 2001,
        'user_zhang', 1729355400, 0);

INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (18, 1002, 3005, 2004, 1729356000, 'Spring Boot真香', 1, '#00FFFF', 90, 2004, 'user_zhao', 1729356000, 2004,
        'user_zhao', 1729356000, 0);

INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (19, 1002, 3006, 2002, 1729356600, '第三部分讲得很清楚', 1, '#FF00FF', 120, 2002, 'user_li', 1729356600, 2002,
        'user_li', 1729356600, 0);

INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (20, 1002, 3007, 2003, 1729357200, '收藏了', 2, '#FFA500', 150, 2003, 'user_wang', 1729357200, 2003,
        'user_wang', 1729357200, 0);

-- 视频1003 的弹幕 (file_id: 3008, 3009, 3010, 3011, 3012, 3013)
INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (21, 1003, 3008, 2002, 1729356600, '学到了很多优化技巧', 1, '#FF00FF', 150, 2002, 'user_li', 1729356600, 2002,
        'user_li', 1729356600, 0);

INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (22, 1003, 3009, 2003, 1729357200, '索引优化讲得很清楚', 2, '#FFA500', 200, 2003, 'user_wang', 1729357200,
        2003, 'user_wang', 1729357200, 0);

INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (23, 1003, 3010, 2004, 1729357800, '收藏了，以后肯定会用到', 1, '#800080', 250, 2004, 'user_zhao', 1729357800,
        2004, 'user_zhao', 1729357800, 0);

INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (24, 1003, 3011, 2001, 1729358400, 'explain讲解非常详细', 1, '#0000FF', 300, 2001, 'user_zhang', 1729358400,
        2001,
        'user_zhang', 1729358400, 0);

INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (25, 1003, 3012, 2002, 1729359000, '慢查询优化太实用了', 1, '#008000', 350, 2002, 'user_li', 1729359000, 2002,
        'user_li', 1729359000, 0);

INSERT INTO video_danmuku (id, video_id, file_id, customer_id, post_time, text, mode, color, time, create_user_id,
                           create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (26, 1003, 3013, 2004, 1729359600, '完结撒花', 3, '#FF0000', 400, 2004, 'user_zhao', 1729359600, 2004,
        'user_zhao', 1729359600, 0);
