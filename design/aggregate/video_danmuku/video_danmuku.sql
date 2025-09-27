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
