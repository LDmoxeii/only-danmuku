-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video`
    (
    `id`             bigint                                                         NOT NULL COMMENT 'ID',
    `video_draft_id` bigint NOT NULL COMMENT '视频草稿ID',
    `customer_id`    bigint                                                         NOT NULL COMMENT '用户ID',
    `video_cover`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL COMMENT '视频封面',
    `video_name`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '视频名称',
    `p_category_id`  bigint                                                         NOT NULL COMMENT '父级分类ID',
    `category_id`    bigint                                                         NULL     DEFAULT NULL COMMENT '分类ID',
    `post_type`      tinyint(4)                                                     NOT NULL DEFAULT 0 COMMENT '投稿类型 @E=0:UNKNOW:未知类型|1:ORIGINAL:自制作|2:REPOST:转载;@T=PostType',
    `origin_info`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '原资源说明',
    `tags`           varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '标签',
    `introduction`   varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '简介',
    `interaction`    varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NULL     DEFAULT NULL COMMENT '互动设置',
    `duration`       int(11)                                                        NULL     DEFAULT 0 COMMENT '持续时间（秒）',
    `play_count`     int(11)                                                        NULL     DEFAULT 0 COMMENT '播放数量',
    `like_count`     int(11)                                                        NULL     DEFAULT 0 COMMENT '点赞数量',
    `danmuku_count`  int(11)                                                        NULL     DEFAULT 0 COMMENT '弹幕数量',
    `comment_count`  int(11)                                                        NULL     DEFAULT 0 COMMENT '评论数量',
    `coin_count`     int(11)                                                        NULL     DEFAULT 0 COMMENT '投币数量',
    `collect_count`  int(11)                                                        NULL     DEFAULT 0 COMMENT '收藏数量',
    `recommend_type` tinyint(1)                                                     NOT NULL DEFAULT 0 COMMENT '推荐状态 @E=0:UNKNOW:未知状态|1:NOT_RECOMMEND:未推荐|2:RECOMMEND:已推荐;@T=RecommendType',
    `last_play_time` datetime                                                       NULL     DEFAULT NULL COMMENT '最后播放时间',
    `create_user_id` bigint                                                         NULL     DEFAULT NULL COMMENT '创建人ID',
    `create_by`      varchar(32)                                                    NULL     DEFAULT NULL COMMENT '创建人名称',
    `create_time`    bigint                                                         NULL     DEFAULT NULL COMMENT '创建时间',
    `update_user_id` bigint                                                         NULL     DEFAULT NULL COMMENT '更新人ID',
    `update_by`      varchar(32)                                                    NULL     DEFAULT NULL COMMENT '更新人名称',
    `update_time`    bigint                                                         NULL     DEFAULT NULL COMMENT '更新时间',
    `deleted`        bigint                                                                  DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '视频信息;@Spe;@Fac;'
    ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for video_file
-- ----------------------------
DROP TABLE IF EXISTS `video_file`;
CREATE TABLE `video_file`
    (
    `id`             bigint                                                        NOT NULL COMMENT 'ID',
    `customer_id`    bigint                                                        NOT NULL COMMENT '用户ID',
    `video_id`       bigint                                                        NOT NULL COMMENT '视频ID',
    `file_name`      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名',
    `file_index`     int(11)                                                       NOT NULL COMMENT '文件索引',
    `file_size`      bigint(20)                                                    NULL DEFAULT NULL COMMENT '文件大小',
    `file_path`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件路径',
    `duration`       int(11)                                                       NULL DEFAULT NULL COMMENT '持续时间（秒）',
    `create_user_id` bigint                                                        NULL DEFAULT NULL COMMENT '创建人ID',
    `create_by`      varchar(32)                                                   NULL DEFAULT NULL COMMENT '创建人名称',
    `create_time`    bigint                                                        NULL DEFAULT NULL COMMENT '创建时间',
    `update_user_id` bigint                                                        NULL DEFAULT NULL COMMENT '更新人ID',
    `update_by`      varchar(32)                                                   NULL DEFAULT NULL COMMENT '更新人名称',
    `update_time`    bigint                                                        NULL DEFAULT NULL COMMENT '更新时间',
    `deleted`        bigint                                                             DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '视频文件信息;@P=video;'
    ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Test data for video
-- ----------------------------
INSERT INTO video (id, video_draft_id, customer_id, video_cover, video_name, p_category_id, category_id, post_type,
                   origin_info, tags,
                   introduction, interaction, duration, play_count, like_count, danmuku_count, comment_count,
                   coin_count, collect_count, recommend_type, last_play_time, create_user_id, create_by, create_time,
                   update_user_id, update_by, update_time, deleted)
VALUES (1001, 4001, 2001, 'cover_kotlin.jpg', 'Kotlin 入门教程', 1, 101, 1, NULL, 'Kotlin,编程,入门',
        '从零开始学习Kotlin编程语言', '111', 1800, 5280, 326, 3, 45, 28, 156, 2, '2024-10-20 10:30:00', 2001,
        'user_zhang', 1729267200, 2001, 'user_zhang', 1729353600, 0);

INSERT INTO video (id, video_draft_id, customer_id, video_cover, video_name, p_category_id, category_id, post_type,
                   origin_info, tags,
                   introduction, interaction, duration, play_count, like_count, danmuku_count, comment_count,
                   coin_count, collect_count, recommend_type, last_play_time, create_user_id, create_by, create_time,
                   update_user_id, update_by, update_time, deleted)
VALUES (1002, 4002, 2002, 'cover_spring.jpg', 'Spring Boot 实战', 1, 101, 1, NULL, 'Spring Boot,Java,后端',
        'Spring Boot微服务开发实战课程', '111', 2400, 8650, 542, 2, 67, 42, 289, 2, '2024-10-20 11:00:00', 2002,
        'user_li', 1729270800, 2002, 'user_li', 1729354200, 0);

INSERT INTO video (id, video_draft_id, customer_id, video_cover, video_name, p_category_id, category_id, post_type,
                   origin_info, tags,
                   introduction, interaction, duration, play_count, like_count, danmuku_count, comment_count,
                   coin_count, collect_count, recommend_type, last_play_time, create_user_id, create_by, create_time,
                   update_user_id, update_by, update_time, deleted)
VALUES (1003, 4001, 2003, 'cover_mysql.jpg', 'MySQL 优化技巧', 1, 102, 1, NULL, 'MySQL,数据库,优化',
        '深入讲解MySQL数据库性能优化技巧', '111', 3600, 12300, 856, 3, 103, 68, 421, 2, '2024-10-20 14:30:00', 2003,
        'user_wang', 1729280800, 2003, 'user_wang', 1729356600, 0);

INSERT INTO video (id, video_draft_id, customer_id, video_cover, video_name, p_category_id, category_id, post_type,
                   origin_info, tags,
                   introduction, interaction, duration, play_count, like_count, danmuku_count, comment_count,
                   coin_count, collect_count, recommend_type, last_play_time, create_user_id, create_by, create_time,
                   update_user_id, update_by, update_time, deleted)
VALUES (1004, 4002, 2004, 'cover_react.jpg', 'React 前端开发', 1, 101, 1, NULL, 'React,前端,JavaScript',
        'React框架从入门到精通', '111', 2100, 6500, 423, 4, 58, 35, 215, 1, '2024-10-20 15:00:00', 2004,
        'user_zhao', 1729282800, 2004, 'user_zhao', 1729358000, 0);

INSERT INTO video (id, video_draft_id, customer_id, video_cover, video_name, p_category_id, category_id, post_type,
                   origin_info, tags,
                   introduction, interaction, duration, play_count, like_count, danmuku_count, comment_count,
                   coin_count, collect_count, recommend_type, last_play_time, create_user_id, create_by, create_time,
                   update_user_id, update_by, update_time, deleted)
VALUES (1005, 4001, 2001, 'cover_docker.jpg', 'Docker 容器技术', 1, 102, 1, NULL, 'Docker,容器,DevOps',
        'Docker容器化技术实战应用', '111', 2700, 9200, 612, 5, 76, 48, 325, 2, '2024-10-20 16:00:00', 2001,
        'user_zhang', 1729286400, 2001, 'user_zhang', 1729359600, 0);

-- ----------------------------
-- Test data for video_file
-- ----------------------------
-- 视频1001 的分片文件 (Kotlin 入门教程 - 3个分片)
INSERT INTO video_file (id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3001, 2001, 1001, 'kotlin_tutorial_part1.mp4', 1, 52428800, '/videos/2001/1001/part1.mp4', 600,
        2001, 'user_zhang', 1729267200, 2001, 'user_zhang', 1729267200, 0);

INSERT INTO video_file (id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3002, 2001, 1001, 'kotlin_tutorial_part2.mp4', 2, 58720256, '/videos/2001/1001/part2.mp4', 600,
        2001, 'user_zhang', 1729267200, 2001, 'user_zhang', 1729267200, 0);

INSERT INTO video_file (id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3003, 2001, 1001, 'kotlin_tutorial_part3.mp4', 3, 62914560, '/videos/2001/1001/part3.mp4', 600,
        2001, 'user_zhang', 1729267200, 2001, 'user_zhang', 1729267200, 0);

-- 视频1002 的分片文件 (Spring Boot 实战 - 4个分片)
INSERT INTO video_file (id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3004, 2002, 1002, 'springboot_part1.mp4', 1, 67108864, '/videos/2002/1002/part1.mp4', 600,
        2002, 'user_li', 1729270800, 2002, 'user_li', 1729270800, 0);

INSERT INTO video_file (id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3005, 2002, 1002, 'springboot_part2.mp4', 2, 71303168, '/videos/2002/1002/part2.mp4', 600,
        2002, 'user_li', 1729270800, 2002, 'user_li', 1729270800, 0);

INSERT INTO video_file (id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3006, 2002, 1002, 'springboot_part3.mp4', 3, 69206016, '/videos/2002/1002/part3.mp4', 600,
        2002, 'user_li', 1729270800, 2002, 'user_li', 1729270800, 0);

INSERT INTO video_file (id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3007, 2002, 1002, 'springboot_part4.mp4', 4, 73400320, '/videos/2002/1002/part4.mp4', 600,
        2002, 'user_li', 1729270800, 2002, 'user_li', 1729270800, 0);

-- 视频1003 的分片文件 (MySQL 优化技巧 - 6个分片)
INSERT INTO video_file (id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3008, 2003, 1003, 'mysql_optimize_part1.mp4', 1, 78643200, '/videos/2003/1003/part1.mp4', 600,
        2003, 'user_wang', 1729280800, 2003, 'user_wang', 1729280800, 0);

INSERT INTO video_file (id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3009, 2003, 1003, 'mysql_optimize_part2.mp4', 2, 81788928, '/videos/2003/1003/part2.mp4', 600,
        2003, 'user_wang', 1729280800, 2003, 'user_wang', 1729280800, 0);

INSERT INTO video_file (id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3010, 2003, 1003, 'mysql_optimize_part3.mp4', 3, 79691776, '/videos/2003/1003/part3.mp4', 600,
        2003, 'user_wang', 1729280800, 2003, 'user_wang', 1729280800, 0);

INSERT INTO video_file (id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3011, 2003, 1003, 'mysql_optimize_part4.mp4', 4, 83886080, '/videos/2003/1003/part4.mp4', 600,
        2003, 'user_wang', 1729280800, 2003, 'user_wang', 1729280800, 0);

INSERT INTO video_file (id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3012, 2003, 1003, 'mysql_optimize_part5.mp4', 5, 77594624, '/videos/2003/1003/part5.mp4', 600,
        2003, 'user_wang', 1729280800, 2003, 'user_wang', 1729280800, 0);

INSERT INTO video_file (id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3013, 2003, 1003, 'mysql_optimize_part6.mp4', 6, 88080384, '/videos/2003/1003/part6.mp4', 600,
        2003, 'user_wang', 1729280800, 2003, 'user_wang', 1729280800, 0);

