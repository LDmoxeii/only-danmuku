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
    `deleted`        tinyint(1)                                                         DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '视频文件信息\nSpe;@Fac;'
    ROW_FORMAT = DYNAMIC;
