-- ----------------------------
-- Table structure for video_play_history
-- ----------------------------
DROP TABLE IF EXISTS `video_play_history`;
CREATE TABLE `video_play_history`
    (
    `id`             bigint      NOT NULL COMMENT 'ID',
    `customer_id`    bigint      NOT NULL COMMENT '用户ID',
    `video_id`       bigint      NOT NULL COMMENT '视频ID',
    `file_index`     int(11)     NOT NULL COMMENT '文件索引',
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
    COLLATE = utf8mb4_general_ci COMMENT = '视频播放历史;@Spe;@Fac;'
    ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Test data for video_play_history
-- ----------------------------
-- 用户2001的播放历史
INSERT INTO video_play_history (id, customer_id, video_id, file_index, create_user_id, create_by, create_time,
                                update_user_id, update_by, update_time, deleted)
VALUES (5001, 2001, 1001, 1, 2001, 'user_zhang', 1729267200, 2001, 'user_zhang', 1729267200, 0);

INSERT INTO video_play_history (id, customer_id, video_id, file_index, create_user_id, create_by, create_time,
                                update_user_id, update_by, update_time, deleted)
VALUES (5002, 2001, 1002, 2, 2001, 'user_zhang', 1729353600, 2001, 'user_zhang', 1729353600, 0);

INSERT INTO video_play_history (id, customer_id, video_id, file_index, create_user_id, create_by, create_time,
                                update_user_id, update_by, update_time, deleted)
VALUES (5003, 2001, 1003, 1, 2001, 'user_zhang', 1729440000, 2001, 'user_zhang', 1729440000, 0);

-- 用户2002的播放历史
INSERT INTO video_play_history (id, customer_id, video_id, file_index, create_user_id, create_by, create_time,
                                update_user_id, update_by, update_time, deleted)
VALUES (5004, 2002, 1001, 2, 2002, 'user_li', 1729270800, 2002, 'user_li', 1729270800, 0);

INSERT INTO video_play_history (id, customer_id, video_id, file_index, create_user_id, create_by, create_time,
                                update_user_id, update_by, update_time, deleted)
VALUES (5005, 2002, 1003, 3, 2002, 'user_li', 1729357200, 2002, 'user_li', 1729357200, 0);

-- 用户2003的播放历史
INSERT INTO video_play_history (id, customer_id, video_id, file_index, create_user_id, create_by, create_time,
                                update_user_id, update_by, update_time, deleted)
VALUES (5006, 2003, 1002, 1, 2003, 'user_wang', 1729280800, 2003, 'user_wang', 1729280800, 0);

INSERT INTO video_play_history (id, customer_id, video_id, file_index, create_user_id, create_by, create_time,
                                update_user_id, update_by, update_time, deleted)
VALUES (5007, 2003, 1001, 3, 2003, 'user_wang', 1729367200, 2003, 'user_wang', 1729367200, 0);

