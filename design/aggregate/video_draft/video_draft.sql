-- ----------------------------
-- Table structure for video_post
-- ----------------------------
DROP TABLE IF EXISTS video_draft;
CREATE TABLE video_draft
    (
    `id`             bigint                                                         NOT NULL COMMENT 'ID',
    `video_id`       bigint                                                         NOT NULL COMMENT '视频ID',
    `video_cover`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL COMMENT '视频封面',
    `video_name`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '视频名称',
    `customer_id`    bigint                                                         NOT NULL COMMENT '用户ID',
    `p_category_id`  bigint                                                         NOT NULL COMMENT '父级分类ID',
    `category_id`    bigint                                                         NULL DEFAULT NULL COMMENT '分类ID',
    `status`         tinyint(1)                                                     NOT NULL COMMENT '视频状态 @E=0:TRANSCODING:转码中|1:TRANSCODE_FAILED:转码失败|2:PENDING_REVIEW:待审核|3:REVIEW_PASSED:审核成功|4:REVIEW_FAILED:审核失败;@T=VideoStatus',
    `post_type`      tinyint(4)                                                     NOT NULL COMMENT '投稿类型 @E=0:ORIGINAL:自制作|1:REPOST:转载;@T=PostType',
    `origin_info`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '原资源说明',
    `tags`           varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '标签',
    `introduction`   varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简介',
    `interaction`    varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NULL DEFAULT NULL COMMENT '互动设置',
    `duration`       int(11)                                                        NULL DEFAULT NULL COMMENT '持续时间（秒）',
    `create_user_id` bigint                                                         NULL DEFAULT NULL COMMENT '创建人ID',
    `create_by`      varchar(32)                                                    NULL DEFAULT NULL COMMENT '创建人名称',
    `create_time`    bigint                                                         NULL DEFAULT NULL COMMENT '创建时间',
    `update_user_id` bigint                                                         NULL DEFAULT NULL COMMENT '更新人ID',
    `update_by`      varchar(32)                                                    NULL DEFAULT NULL COMMENT '更新人名称',
    `update_time`    bigint                                                         NULL DEFAULT NULL COMMENT '更新时间',
    `deleted`        tinyint(1)                                                          DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '视频信息\nSpe;@Fac;'
    ROW_FORMAT = DYNAMIC;
