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
    `category_id`    bigint                                                         NULL     DEFAULT NULL COMMENT '分类ID',
    `status`         tinyint(1)                                                     NOT NULL DEFAULT 0 COMMENT '视频状态 @E=0:UNKNOW:未知状态|1:TRANSCODING:转码中|2:TRANSCODE_FAILED:转码失败|3:PENDING_REVIEW:待审核|4:REVIEW_PASSED:审核成功|5:REVIEW_FAILED:审核失败;@T=VideoStatus',
    `post_type`      tinyint(4)                                                     NOT NULL DEFAULT 0 COMMENT '投稿类型 @E=0:UNKNOW:未知类型|1:ORIGINAL:自制作|2:REPOST:转载;@T=PostType',
    `origin_info`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '原资源说明',
    `tags`           varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '标签',
    `introduction`   varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '简介',
    `interaction`    varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NULL     DEFAULT NULL COMMENT '互动设置',
    `duration`       int(11)                                                        NULL     DEFAULT NULL COMMENT '持续时间（秒）',
    `create_user_id` bigint                                                         NULL     DEFAULT NULL COMMENT '创建人ID',
    `create_by`      varchar(32)                                                    NULL     DEFAULT NULL COMMENT '创建人名称',
    `create_time`    bigint                                                         NULL     DEFAULT NULL COMMENT '创建时间',
    `update_user_id` bigint                                                         NULL     DEFAULT NULL COMMENT '更新人ID',
    `update_by`      varchar(32)                                                    NULL     DEFAULT NULL COMMENT '更新人名称',
    `update_time`    bigint                                                         NULL     DEFAULT NULL COMMENT '更新时间',
    `deleted`        tinyint(1)                                                              DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '视频信息;@Spe;@Fac;'
    ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Test data for video_draft
-- ----------------------------
INSERT INTO video_draft (id, video_id, video_cover, video_name, customer_id, p_category_id, category_id, status,
                         post_type, origin_info, tags, introduction, interaction, duration, create_user_id, create_by,
                         create_time, update_user_id, update_by, update_time, deleted)
VALUES
-- 审核通过的视频 (status = 4 表示审核成功)
(4001, 1001, 'cover_kotlin.jpg', 'Kotlin 入门教程', 2001, 1, 101, 4, 1, NULL, 'Kotlin,编程,入门',
 '从零开始学习Kotlin编程语言', '111', 1800, 2001, 'user_zhang', 1729267200, 2001, 'user_zhang', 1729353600, 0),
(4002, 1002, 'cover_react.jpg', 'React 实战项目', 2001, 1, 102, 4, 1, NULL, 'React,前端,实战', 'React项目从0到1实战',
 '111', 2400, 2001, 'user_zhang', 1729267200, 2001, 'user_zhang', 1729353600, 0),

-- 审核失败的视频 (status = 5 表示审核失败)
(4003, 1007, 'cover_failed1.jpg', '测试审核失败视频1', 2001, 2, 201, 5, 1, NULL, '测试,审核',
 '这是一个审核失败的测试视频', '111', 600, 2001, 'user_zhang', 1729440000, 2001, 'user_zhang', 1729440000, 0),

-- 进行中的视频 (转码中: status = 1, 转码失败: status = 2, 待审核: status = 3)
(4004, 1008, 'cover_transcoding.jpg', '转码中的视频', 2001, 1, 103, 1, 1, NULL, 'Docker,容器', 'Docker容器化技术实战',
 '111', 3000, 2001, 'user_zhang', 1729526400, 2001, 'user_zhang', 1729526400, 0),
(4005, 1009, 'cover_failed_transcode.jpg', '转码失败的视频', 2001, 2, 202, 2, 2, 'https://example.com/original',
 'Python,转载', '转码失败需要重新上传', '111', NULL, 2001, 'user_zhang', 1729612800, 2001, 'user_zhang', 1729612800, 0),
(4006, 1010, 'cover_pending_review.jpg', '待审核视频', 2001, 1, 104, 3, 1, NULL, 'Kubernetes,云原生',
 'Kubernetes入门到实战', '111', 4200, 2001, 'user_zhang', 1729699200, 2001, 'user_zhang', 1729699200, 0);
