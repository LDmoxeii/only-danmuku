-- ----------------------------
-- Table structure for customer_action
-- ----------------------------
DROP TABLE IF EXISTS `customer_action`;
CREATE TABLE `customer_action`
    (
    `id`             bigint      NOT NULL COMMENT 'ID',
    `customer_id`    bigint      NOT NULL COMMENT '用户ID',
    `video_id`       bigint      NOT NULL COMMENT '视频ID',
    `video_owner_id` bigint      NOT NULL COMMENT '视频用户ID',
    `comment_id`     bigint      NULL     DEFAULT NULL COMMENT '评论ID',
    `action_type`    tinyint(1)  NOT NULL DEFAULT 0 COMMENT '行为类型 @E=0:UNKNOW:未知行为|1:LIKE_COMMENT:评论喜欢点赞|2:HATE_COMMENT:讨厌评论|3:LIKE_VIDEO:视频点赞|4:FAVORITE_VIDEO:视频收藏|5:COIN_VIDEO:视频投币;@T=ActionType',
    `action_count`   int(11)     NOT NULL COMMENT '数量',
    `action_time`    bigint      NOT NULL COMMENT '操作时间',
    `create_by`      varchar(32) NULL     DEFAULT NULL COMMENT '创建人名称',
    `create_time`    bigint      NULL     DEFAULT NULL COMMENT '创建时间',
    `update_user_id` bigint      NULL     DEFAULT NULL COMMENT '更新人ID',
    `update_by`      varchar(32) NULL     DEFAULT NULL COMMENT '更新人名称',
    `update_time`    bigint      NULL     DEFAULT NULL COMMENT '更新时间',
    `deleted`        bigint               DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_key_video_comment_type_user` (`video_id`, `comment_id`, `action_type`, `customer_id`, `deleted`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '用户行为 点赞、评论;@Spe;@Fac;'
    ROW_FORMAT = DYNAMIC;
