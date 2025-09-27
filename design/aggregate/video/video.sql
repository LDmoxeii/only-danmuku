-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video`
    (
    `id`             bigint                                                         NOT NULL COMMENT 'ID',
    `customer_id`    bigint                                                         NOT NULL COMMENT '用户ID',
    `video_cover`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL COMMENT '视频封面',
    `video_name`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '视频名称',
    `p_category_id`  bigint                                                         NOT NULL COMMENT '父级分类ID',
    `category_id`    bigint                                                         NULL DEFAULT NULL COMMENT '分类ID',
    `post_type`      tinyint(4)                                                     NOT NULL COMMENT '投稿类型 @E=0:ORIGINAL:自制作|1:REPOST:转载@T=PostType',
    `origin_info`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '原资源说明',
    `tags`           varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '标签',
    `introduction`   varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简介',
    `interaction`    varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NULL DEFAULT NULL COMMENT '互动设置',
    `duration`       int(11)                                                        NULL DEFAULT 0 COMMENT '持续时间（秒）',
    `play_count`     int(11)                                                        NULL DEFAULT 0 COMMENT '播放数量',
    `like_count`     int(11)                                                        NULL DEFAULT 0 COMMENT '点赞数量',
    `danmuku_count`  int(11)                                                        NULL DEFAULT 0 COMMENT '弹幕数量',
    `comment_count`  int(11)                                                        NULL DEFAULT 0 COMMENT '评论数量',
    `coin_count`     int(11)                                                        NULL DEFAULT 0 COMMENT '投币数量',
    `collect_count`  int(11)                                                        NULL DEFAULT 0 COMMENT '收藏数量',
    `recommend_type` tinyint(1)                                                     NULL DEFAULT 0 COMMENT '推荐状态 @E=0:NOT_RECOMMEND:未推荐|1:RECOMMEND:已推荐@T=RecommendType',
    `last_play_time` datetime                                                       NULL DEFAULT NULL COMMENT '最后播放时间',
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
