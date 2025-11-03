-- ----------------------------
-- Table structure for video_file_upload_session
-- ----------------------------
DROP TABLE IF EXISTS `video_file_upload_session`;
CREATE TABLE `video_file_upload_session`
    (
    `id`             bigint                                                        NOT NULL COMMENT 'ID',
    `customer_id`    bigint                                                        NOT NULL COMMENT '用户ID',
    `file_name`      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件名',
    `chunks`         int(11)                                                       NOT NULL COMMENT '分片总数',
    `chunk_index`    int(11)                                                       NOT NULL DEFAULT 0 COMMENT '当前已上传的最大分片索引',
    `file_size`      bigint(20)                                                    NULL     DEFAULT 0 COMMENT '累计已上传大小（字节）',
    `temp_path`      varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '临时目录（绝对或相对路径）',
    `status`         tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '状态 @E=0:UNKNOW:未知类型|1:CREATED:已创建|2:UPLOADING:上传中|3:DONE:完成|4:ABORTED:已放弃|5:EXPIRED:已过期;@T=UploadStatus',
    `duration`       int(11)                                                       NULL     DEFAULT NULL COMMENT '视频时长（秒，可选）',
    `create_user_id` bigint                                                        NULL     DEFAULT NULL COMMENT '创建人ID',
    `create_by`      varchar(32)                                                   NULL     DEFAULT NULL COMMENT '创建人名称',
    `create_time`    bigint                                                        NULL     DEFAULT NULL COMMENT '创建时间',
    `update_user_id` bigint                                                        NULL     DEFAULT NULL COMMENT '更新人ID',
    `update_by`      varchar(32)                                                   NULL     DEFAULT NULL COMMENT '更新人名称',
    `update_time`    bigint                                                        NULL     DEFAULT NULL COMMENT '更新时间',
    `expires_at`     bigint                                                        NULL     DEFAULT NULL COMMENT '过期时间（秒时间戳）',
    `deleted`        bigint                                                                 DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_user_status` (`customer_id`, `status`) USING BTREE,
    INDEX `idx_expires_at` (`expires_at`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '视频分片上传会话; 用于跟踪预上传与分片进度'
    ROW_FORMAT = DYNAMIC;

