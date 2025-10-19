-- ----------------------------
-- Table structure for customer_action
-- ----------------------------
DROP TABLE IF EXISTS `customer_action`;
CREATE TABLE `customer_action`
    (
    `id`             bigint                                                       NOT NULL COMMENT 'ID',
    `customer_id`    varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
    `video_id`       varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '视频ID',
    `video_owner_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '视频用户ID',
    `comment_id`     bigint                                                       NOT NULL DEFAULT 0 COMMENT '评论ID',
    `action_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '行为类型 @E=0:UNKNOW:未知行为|1:LIKE_COMMENT:评论喜欢点赞|2:HATE_COMMENT:讨厌评论|3:LIKE_VIDEO:视频点赞|4:FAVORITE_VIDEO:视频收藏|5:COIN_VIDEO:视频投币;@T=ActionType',
    `action_count`   int(11)                                                      NOT NULL COMMENT '数量',
    `action_time`    bigint                                                       NOT NULL COMMENT '操作时间',
    `create_by`      varchar(32)                                                  NULL     DEFAULT NULL COMMENT '创建人名称',
    `create_time`    bigint                                                       NULL     DEFAULT NULL COMMENT '创建时间',
    `update_user_id` bigint                                                       NULL     DEFAULT NULL COMMENT '更新人ID',
    `update_by`      varchar(32)                                                  NULL     DEFAULT NULL COMMENT '更新人名称',
    `update_time`    bigint                                                       NULL     DEFAULT NULL COMMENT '更新时间',
    `deleted`        tinyint(1)                                                            DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_key_video_comment_type_user` (`video_id`, `comment_id`, `action_type`, `customer_id`, `deleted`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '用户行为 点赞、评论;@Spe;@Fac;'
    ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Test data for customer_action
-- ----------------------------
-- 用户 2001 (张三) 的收藏和点赞行为
INSERT INTO customer_action (id, customer_id, video_id, video_owner_id, comment_id, action_type, action_count,
                             action_time, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES
-- 收藏视频 (action_type = 4)
(7001, 2001, 1002, 2001, 0, 4, 1, 1729267200, 'user_zhang', 1729267200, 2001, 'user_zhang', 1729267200, 0),
(7002, 2001, 1003, 2001, 0, 4, 1, 1729353600, 'user_zhang', 1729353600, 2001, 'user_zhang', 1729353600, 0),
(7003, 2001, 1004, 2002, 0, 4, 1, 1729440000, 'user_zhang', 1729440000, 2001, 'user_zhang', 1729440000, 0),
-- 视频点赞 (action_type = 3)
(7004, 2001, 1001, 2001, 0, 3, 1, 1729267200, 'user_zhang', 1729267200, 2001, 'user_zhang', 1729267200, 0),
(7005, 2001, 1002, 2001, 0, 3, 1, 1729267200, 'user_zhang', 1729267200, 2001, 'user_zhang', 1729267200, 0),
-- 视频投币 (action_type = 5)
(7006, 2001, 1001, 2001, 0, 5, 2, 1729267200, 'user_zhang', 1729267200, 2001, 'user_zhang', 1729267200, 0);

-- 用户 2002 (李四) 的收藏和点赞行为
INSERT INTO customer_action (id, customer_id, video_id, video_owner_id, comment_id, action_type, action_count,
                             action_time, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES
-- 收藏视频
(7007, 2002, 1001, 2001, 0, 4, 1, 1729267200, 'user_li', 1729267200, 2002, 'user_li', 1729267200, 0),
(7008, 2002, 1005, 2002, 0, 4, 1, 1729353600, 'user_li', 1729353600, 2002, 'user_li', 1729353600, 0),
-- 视频点赞
(7009, 2002, 1001, 2001, 0, 3, 1, 1729267200, 'user_li', 1729267200, 2002, 'user_li', 1729267200, 0),
(7010, 2002, 1003, 2001, 0, 3, 1, 1729353600, 'user_li', 1729353600, 2002, 'user_li', 1729353600, 0);
