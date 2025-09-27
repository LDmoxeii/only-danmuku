-- ----------------------------
-- Table structure for video_comment
-- ----------------------------
DROP TABLE IF EXISTS `video_comment`;
CREATE TABLE `video_comment`
    (
    `id`                bigint                                                        NOT NULL COMMENT '评论ID',
    `parent_id`         bigint                                                        NOT NULL COMMENT '父级评论ID',
    `video_id`          bigint                                                        NOT NULL COMMENT '视频ID',
    `video_owner_id`    bigint                                                        NOT NULL COMMENT '视频用户ID',
    `content`           varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回复内容',
    `img_path`          varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片',
    `customer_id`       bigint                                                        NOT NULL COMMENT '用户ID',
    `reply_customer_id` bigint                                                        NULL DEFAULT NULL COMMENT '回复人ID',
    `top_type`          tinyint(4)                                                    NULL DEFAULT 0 COMMENT '0:未置顶  1:置顶',
    `post_time`         bigint                                                        NOT NULL COMMENT '发布时间',
    `like_count`        int(11)                                                       NULL DEFAULT 0 COMMENT '喜欢数量',
    `hate_count`        int(11)                                                       NULL DEFAULT 0 COMMENT '讨厌数量',
    `create_user_id`    bigint                                                        NULL DEFAULT NULL COMMENT '创建人ID',
    `create_by`         varchar(32)                                                   NULL DEFAULT NULL COMMENT '创建人名称',
    `create_time`       bigint                                                        NULL DEFAULT NULL COMMENT '创建时间',
    `update_user_id`    bigint                                                        NULL DEFAULT NULL COMMENT '更新人ID',
    `update_by`         varchar(32)                                                   NULL DEFAULT NULL COMMENT '更新人名称',
    `update_time`       bigint                                                        NULL DEFAULT NULL COMMENT '更新时间',
    `deleted`           tinyint(1)                                                         DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '评论;@Spe;@Fac;'
    ROW_FORMAT = DYNAMIC;
