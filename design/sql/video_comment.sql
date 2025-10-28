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
    `deleted`           bigint                                                             DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '评论;@Spe;@Fac;'
    ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Test data for video_comment
-- ----------------------------
-- 评论1: 视频1001 (Kotlin 入门教程) - 用户2002的评论
INSERT INTO video_comment (id, parent_id, video_id, video_owner_id, content, img_path, customer_id, reply_customer_id,
                           top_type, post_time, like_count, hate_count, create_user_id, create_by, create_time,
                           update_user_id, update_by, update_time, deleted)
VALUES (3001, 0, 1001, 2001, '讲解得很清晰，对Kotlin新手非常友好！', NULL, 2002, NULL,
        0, 1729267800, 15, 0, 2002, 'user_li', 1729267800,
        2002, 'user_li', 1729267800, 0);

-- 评论2: 视频1001 - 用户2003的评论
INSERT INTO video_comment (id, parent_id, video_id, video_owner_id, content, img_path, customer_id, reply_customer_id,
                           top_type, post_time, like_count, hate_count, create_user_id, create_by, create_time,
                           update_user_id, update_by, update_time, deleted)
VALUES (3002, 0, 1001, 2001, '示例代码很实用，已经跟着练习了', NULL, 2003, NULL,
        1, 1729271400, 28, 0, 2003, 'user_wang', 1729271400,
        2003, 'user_wang', 1729271400, 0);

-- 评论3: 视频1001 - 用户2004回复评论3001
INSERT INTO video_comment (id, parent_id, video_id, video_owner_id, content, img_path, customer_id, reply_customer_id,
                           top_type, post_time, like_count, hate_count, create_user_id, create_by, create_time,
                           update_user_id, update_by, update_time, deleted)
VALUES (3003, 3001, 1001, 2001, '同意！比Java入门简单多了', NULL, 2004, 2002,
        0, 1729275000, 8, 0, 2004, 'user_zhao', 1729275000,
        2004, 'user_zhao', 1729275000, 0);

-- 评论4: 视频1002 (Spring Boot 实战) - 用户2001的评论
INSERT INTO video_comment (id, parent_id, video_id, video_owner_id, content, img_path, customer_id, reply_customer_id,
                           top_type, post_time, like_count, hate_count, create_user_id, create_by, create_time,
                           update_user_id, update_by, update_time, deleted)
VALUES (3004, 0, 1002, 2002, '微服务架构讲得很透彻，受益匪浅', NULL, 2001, NULL,
        1, 1729278600, 42, 0, 2001, 'user_zhang', 1729278600,
        2001, 'user_zhang', 1729278600, 0);

-- 评论5: 视频1002 - 用户2003的评论
INSERT INTO video_comment (id, parent_id, video_id, video_owner_id, content, img_path, customer_id, reply_customer_id,
                           top_type, post_time, like_count, hate_count, create_user_id, create_by, create_time,
                           update_user_id, update_by, update_time, deleted)
VALUES (3005, 0, 1002, 2002, '想请问一下配置中心的最佳实践', NULL, 2003, NULL,
        0, 1729282200, 12, 0, 2003, 'user_wang', 1729282200,
        2003, 'user_wang', 1729282200, 0);

-- 评论6: 视频1002 - 用户2002回复评论3005
INSERT INTO video_comment (id, parent_id, video_id, video_owner_id, content, img_path, customer_id, reply_customer_id,
                           top_type, post_time, like_count, hate_count, create_user_id, create_by, create_time,
                           update_user_id, update_by, update_time, deleted)
VALUES (3006, 3005, 1002, 2002, '推荐使用Nacos或Apollo，我后续会出专题视频', NULL, 2002, 2003,
        0, 1729285800, 18, 0, 2002, 'user_li', 1729285800,
        2002, 'user_li', 1729285800, 0);

-- 评论7: 视频1003 (MySQL 优化技巧) - 用户2001的评论
INSERT INTO video_comment (id, parent_id, video_id, video_owner_id, content, img_path, customer_id, reply_customer_id,
                           top_type, post_time, like_count, hate_count, create_user_id, create_by, create_time,
                           update_user_id, update_by, update_time, deleted)
VALUES (3007, 0, 1003, 2003, '索引优化部分讲得太好了！', NULL, 2001, NULL,
        1, 1729289400, 56, 0, 2001, 'user_zhang', 1729289400,
        2001, 'user_zhang', 1729289400, 0);

-- 评论8: 视频1003 - 用户2002的评论
INSERT INTO video_comment (id, parent_id, video_id, video_owner_id, content, img_path, customer_id, reply_customer_id,
                           top_type, post_time, like_count, hate_count, create_user_id, create_by, create_time,
                           update_user_id, update_by, update_time, deleted)
VALUES (3008, 0, 1003, 2003, 'Explain的使用技巧很实用', NULL, 2002, NULL,
        0, 1729293000, 34, 0, 2002, 'user_li', 1729293000,
        2002, 'user_li', 1729293000, 0);

-- 评论9: 视频1003 - 用户2004的评论
INSERT INTO video_comment (id, parent_id, video_id, video_owner_id, content, img_path, customer_id, reply_customer_id,
                           top_type, post_time, like_count, hate_count, create_user_id, create_by, create_time,
                           update_user_id, update_by, update_time, deleted)
VALUES (3009, 0, 1003, 2003, '能否出一期关于分库分表的视频？', NULL, 2004, NULL,
        0, 1729296600, 21, 0, 2004, 'user_zhao', 1729296600,
        2004, 'user_zhao', 1729296600, 0);

-- 评论10: 视频1003 - 用户2003回复评论3009
INSERT INTO video_comment (id, parent_id, video_id, video_owner_id, content, img_path, customer_id, reply_customer_id,
                           top_type, post_time, like_count, hate_count, create_user_id, create_by, create_time,
                           update_user_id, update_by, update_time, deleted)
VALUES (3010, 3009, 1003, 2003, '好的，这个主题我会安排到下一期', NULL, 2003, 2004,
        0, 1729300200, 15, 0, 2003, 'user_wang', 1729300200,
        2003, 'user_wang', 1729300200, 0);
