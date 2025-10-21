-- ----------------------------
-- Table structure for video_file
-- ----------------------------
DROP TABLE IF EXISTS `video_file`;
CREATE TABLE `video_file`
    (
    `id`             bigint                                                        NOT NULL COMMENT 'ID',
    `file_id`        bigint                                                        NOT NULL COMMENT '唯一ID',
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
    COLLATE = utf8mb4_general_ci COMMENT = '视频文件信息;@Spe;@Fac;'
    ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Test data for video_file
-- ----------------------------
-- 视频1001 的分片文件 (Kotlin 入门教程 - 3个分片)
INSERT INTO video_file (id, file_id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3001, 3001, 2001, 1001, 'kotlin_tutorial_part1.mp4', 1, 52428800, '/videos/2001/1001/part1.mp4', 600,
        2001, 'user_zhang', 1729267200, 2001, 'user_zhang', 1729267200, 0);

INSERT INTO video_file (id, file_id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3002, 3002, 2001, 1001, 'kotlin_tutorial_part2.mp4', 2, 58720256, '/videos/2001/1001/part2.mp4', 600,
        2001, 'user_zhang', 1729267200, 2001, 'user_zhang', 1729267200, 0);

INSERT INTO video_file (id, file_id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3003, 3003, 2001, 1001, 'kotlin_tutorial_part3.mp4', 3, 62914560, '/videos/2001/1001/part3.mp4', 600,
        2001, 'user_zhang', 1729267200, 2001, 'user_zhang', 1729267200, 0);

-- 视频1002 的分片文件 (Spring Boot 实战 - 4个分片)
INSERT INTO video_file (id, file_id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3004, 3004, 2002, 1002, 'springboot_part1.mp4', 1, 67108864, '/videos/2002/1002/part1.mp4', 600,
        2002, 'user_li', 1729270800, 2002, 'user_li', 1729270800, 0);

INSERT INTO video_file (id, file_id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3005, 3005, 2002, 1002, 'springboot_part2.mp4', 2, 71303168, '/videos/2002/1002/part2.mp4', 600,
        2002, 'user_li', 1729270800, 2002, 'user_li', 1729270800, 0);

INSERT INTO video_file (id, file_id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3006, 3006, 2002, 1002, 'springboot_part3.mp4', 3, 69206016, '/videos/2002/1002/part3.mp4', 600,
        2002, 'user_li', 1729270800, 2002, 'user_li', 1729270800, 0);

INSERT INTO video_file (id, file_id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3007, 3007, 2002, 1002, 'springboot_part4.mp4', 4, 73400320, '/videos/2002/1002/part4.mp4', 600,
        2002, 'user_li', 1729270800, 2002, 'user_li', 1729270800, 0);

-- 视频1003 的分片文件 (MySQL 优化技巧 - 6个分片)
INSERT INTO video_file (id, file_id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3008, 3008, 2003, 1003, 'mysql_optimize_part1.mp4', 1, 78643200, '/videos/2003/1003/part1.mp4', 600,
        2003, 'user_wang', 1729280800, 2003, 'user_wang', 1729280800, 0);

INSERT INTO video_file (id, file_id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3009, 3009, 2003, 1003, 'mysql_optimize_part2.mp4', 2, 81788928, '/videos/2003/1003/part2.mp4', 600,
        2003, 'user_wang', 1729280800, 2003, 'user_wang', 1729280800, 0);

INSERT INTO video_file (id, file_id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3010, 3010, 2003, 1003, 'mysql_optimize_part3.mp4', 3, 79691776, '/videos/2003/1003/part3.mp4', 600,
        2003, 'user_wang', 1729280800, 2003, 'user_wang', 1729280800, 0);

INSERT INTO video_file (id, file_id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3011, 3011, 2003, 1003, 'mysql_optimize_part4.mp4', 4, 83886080, '/videos/2003/1003/part4.mp4', 600,
        2003, 'user_wang', 1729280800, 2003, 'user_wang', 1729280800, 0);

INSERT INTO video_file (id, file_id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3012, 3012, 2003, 1003, 'mysql_optimize_part5.mp4', 5, 77594624, '/videos/2003/1003/part5.mp4', 600,
        2003, 'user_wang', 1729280800, 2003, 'user_wang', 1729280800, 0);

INSERT INTO video_file (id, file_id, customer_id, video_id, file_name, file_index, file_size, file_path, duration,
                        create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3013, 3013, 2003, 1003, 'mysql_optimize_part6.mp4', 6, 88080384, '/videos/2003/1003/part6.mp4', 600,
        2003, 'user_wang', 1729280800, 2003, 'user_wang', 1729280800, 0);

