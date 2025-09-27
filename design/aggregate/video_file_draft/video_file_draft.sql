-- ----------------------------
-- Table structure for video_file_draft
-- ----------------------------
DROP TABLE IF EXISTS video_file_draft;
CREATE TABLE video_file_draft
    (
    `id`              bigint                                                        NOT NULL COMMENT 'ID',
    `file_id`         bigint                                                        NOT NULL COMMENT '唯一ID',
    `upload_id`       bigint                                                        NOT NULL COMMENT '上传ID',
    `customer_id`     bigint                                                        NOT NULL COMMENT '用户ID',
    `video_id`        bigint                                                        NOT NULL COMMENT '视频ID',
    `file_index`      int(11)                                                       NOT NULL COMMENT '文件索引',
    `file_name`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '文件名',
    `file_size`       bigint(20)                                                    NULL     DEFAULT NULL COMMENT '文件大小',
    `file_path`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '文件路径',
    `update_type`     tinyint(4)                                                    NOT NULL DEFAULT 0 COMMENT '更新类型 @E=0:UNKNOW:未知类型|1:NO_UPDATE:无更新|2:HAS_UPDATE:有更新;@T=UpdateType',
    `transfer_result` tinyint(4)                                                    NOT NULL DEFAULT 0 COMMENT '转码结果 @E=0:UNKNOW:未知结果|1:TRANSCODING:转码中|2:SUCCESS:转码成功|3:FAILED:转码失败;@T=TransferResult',
    `duration`        int(11)                                                       NULL     DEFAULT NULL COMMENT '持续时间（秒）',
    `create_user_id`  bigint                                                        NULL     DEFAULT NULL COMMENT '创建人ID',
    `create_by`       varchar(32)                                                   NULL     DEFAULT NULL COMMENT '创建人名称',
    `create_time`     bigint                                                        NULL     DEFAULT NULL COMMENT '创建时间',
    `update_user_id`  bigint                                                        NULL     DEFAULT NULL COMMENT '更新人ID',
    `update_by`       varchar(32)                                                   NULL     DEFAULT NULL COMMENT '更新人名称',
    `update_time`     bigint                                                        NULL     DEFAULT NULL COMMENT '更新时间',
    `deleted`         tinyint(1)                                                             DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_key_upload_id` (`upload_id`, `customer_id`, `deleted`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '视频文件信息\nSpe;@Fac;'
    ROW_FORMAT = DYNAMIC;
