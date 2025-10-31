-- ----------------------------
-- Table structure for video_post
-- ----------------------------
DROP TABLE IF EXISTS video_post;
CREATE TABLE video_post
    (
    `id`             bigint                                                        NOT NULL COMMENT 'ID',
    `video_cover`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '视频封面',
    `video_name`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '视频名称',
    `customer_id`    bigint                                                        NOT NULL COMMENT '用户ID',
    `p_category_id`  bigint                                                        NOT NULL COMMENT '父级分类ID',
    `category_id`    bigint NULL     DEFAULT NULL COMMENT '分类ID',
    `status`         tinyint(1)                                                     NOT NULL DEFAULT 0 COMMENT '视频状态 @E=0:UNKNOW:未知状态|1:TRANSCODING:转码中|2:TRANSCODE_FAILED:转码失败|3:PENDING_REVIEW:待审核|4:REVIEW_PASSED:审核成功|5:REVIEW_FAILED:审核失败;@T=VideoStatus',
    `post_type`      tinyint(4)                                                     NOT NULL DEFAULT 0 COMMENT '投稿类型 @E=0:UNKNOW:未知类型|1:ORIGINAL:自制作|2:REPOST:转载;@T=PostType',
    `origin_info`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '原资源说明',
    `tags`           varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '标签',
    `introduction`   varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '简介',
    `interaction`    varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '互动设置',
    `duration`       int(11)                                                        NOT NULL DEFAULT 0 COMMENT '持续时间（秒）',
    `create_user_id` bigint NULL     DEFAULT NULL COMMENT '创建人ID',
    `create_by`      varchar(32) NULL     DEFAULT NULL COMMENT '创建人名称',
    `create_time`    bigint NULL     DEFAULT NULL COMMENT '创建时间',
    `update_user_id` bigint NULL     DEFAULT NULL COMMENT '更新人ID',
    `update_by`      varchar(32) NULL     DEFAULT NULL COMMENT '更新人名称',
    `update_time`    bigint NULL     DEFAULT NULL COMMENT '更新时间',
    `deleted`        bigint DEFAULT 0                                              NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE
    ) ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '视频信息;@Spe;@Fac;'
    ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for video_file_post
-- ----------------------------
DROP TABLE IF EXISTS video_file_post;
CREATE TABLE video_file_post
    (
    `id`              bigint           NOT NULL COMMENT 'ID',
    `upload_id`       bigint           NOT NULL COMMENT '上传ID',
    `customer_id`     bigint           NOT NULL COMMENT '用户ID',
    `video_id`        bigint           NOT NULL COMMENT '视频ID@@Ref=video_post',
    `file_index`      int(11)                                                       NOT NULL COMMENT '文件索引',
    `file_name`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '文件名',
    `file_size`       bigint(20)                                                    NULL     DEFAULT NULL COMMENT '文件大小',
    `file_path`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '文件路径',
    `update_type`     tinyint(4)                                                    NOT NULL DEFAULT 0 COMMENT '更新类型 @E=0:UNKNOW:未知类型|1:NO_UPDATE:无更新|2:HAS_UPDATE:有更新;@T=UpdateType',
    `transfer_result` tinyint(4)                                                    NOT NULL DEFAULT 0 COMMENT '转码结果 @E=0:UNKNOW:未知结果|1:TRANSCODING:转码中|2:SUCCESS:转码成功|3:FAILED:转码失败;@T=TransferResult',
    `duration`        int(11)                                                       NULL     DEFAULT NULL COMMENT '持续时间（秒）',
    `create_user_id`  bigint NULL     DEFAULT NULL COMMENT '创建人ID',
    `create_by`       varchar(32) NULL     DEFAULT NULL COMMENT '创建人名称',
    `create_time`     bigint NULL     DEFAULT NULL COMMENT '创建时间',
    `update_user_id`  bigint NULL     DEFAULT NULL COMMENT '更新人ID',
    `update_by`       varchar(32) NULL     DEFAULT NULL COMMENT '更新人名称',
    `update_time`     bigint NULL     DEFAULT NULL COMMENT '更新时间',
    `deleted`         bigint DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_key_upload_id` (`upload_id`, `customer_id`, `deleted`) USING BTREE
    ) ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '视频文件信息;@P=video_post'
    ROW_FORMAT = DYNAMIC;
