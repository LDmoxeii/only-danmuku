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
    `deleted`         bigint                                                                 DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_key_upload_id` (`upload_id`, `customer_id`, `deleted`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '视频文件信息;@Spe;@Fac;'
    ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Test data for video_file_draft
-- ----------------------------
INSERT INTO video_file_draft (id, file_id, upload_id, customer_id, video_id, file_index, file_name, file_size,
                              file_path, update_type, transfer_result, duration, create_user_id, create_by, create_time,
                              update_user_id, update_by, update_time, deleted)
VALUES
-- 视频 1001 的文件草稿 (Kotlin 入门教程 - 3个分片)
(5001, 3001, 8001, 2001, 1001, 1, 'kotlin_tutorial_part1.mp4', 104857600, '/videos/2001/1001/part1.mp4', 1, 2, 600,
 2001, 'user_zhang', 1729267200, 2001, 'user_zhang', 1729267200, 0),
(5002, 3002, 8002, 2001, 1001, 2, 'kotlin_tutorial_part2.mp4', 104857600, '/videos/2001/1001/part2.mp4', 1, 2, 600,
 2001, 'user_zhang', 1729267200, 2001, 'user_zhang', 1729267200, 0),
(5003, 3003, 8003, 2001, 1001, 3, 'kotlin_tutorial_part3.mp4', 104857600, '/videos/2001/1001/part3.mp4', 1, 2, 600,
 2001, 'user_zhang', 1729267200, 2001, 'user_zhang', 1729267200, 0),

-- 视频 1002 的文件草稿 (React 实战项目 - 4个分片)
(5004, 3004, 8004, 2001, 1002, 1, 'react_project_part1.mp4', 125829120, '/videos/2001/1002/part1.mp4', 1, 2, 600, 2001,
 'user_zhang', 1729267200, 2001, 'user_zhang', 1729267200, 0),
(5005, 3005, 8005, 2001, 1002, 2, 'react_project_part2.mp4', 125829120, '/videos/2001/1002/part2.mp4', 1, 2, 600, 2001,
 'user_zhang', 1729267200, 2001, 'user_zhang', 1729267200, 0),
(5006, 3006, 8006, 2001, 1002, 3, 'react_project_part3.mp4', 125829120, '/videos/2001/1002/part3.mp4', 1, 2, 600, 2001,
 'user_zhang', 1729267200, 2001, 'user_zhang', 1729267200, 0),
(5007, 3007, 8007, 2001, 1002, 4, 'react_project_part4.mp4', 125829120, '/videos/2001/1002/part4.mp4', 1, 2, 600, 2001,
 'user_zhang', 1729267200, 2001, 'user_zhang', 1729267200, 0),

-- 视频 1010 的文件草稿 (待审核视频 - 转码中)
(5008, 3014, 8008, 2001, 1010, 1, 'kubernetes_tutorial.mp4', 209715200, '/videos/2001/1010/part1.mp4', 1, 1, 4200, 2001,
 'user_zhang', 1729699200, 2001, 'user_zhang', 1729699200, 0);
