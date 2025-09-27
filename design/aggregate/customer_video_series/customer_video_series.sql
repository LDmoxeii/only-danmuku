-- ----------------------------
-- Table structure for customer_video_series
-- ----------------------------
DROP TABLE IF EXISTS `customer_video_series`;
CREATE TABLE `customer_video_series`
    (
    `id`                 bigint                                                        NOT NULL COMMENT 'ID',
    `customer_id`        bigint                                                        NOT NULL COMMENT '用户ID',
    `series_name`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '列表名称',
    `series_description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
    `sort`               tinyint(4)                                                    NOT NULL COMMENT '排序',
    `create_user_id`     bigint                                                        NULL DEFAULT NULL COMMENT '创建人ID',
    `create_by`          varchar(32)                                                   NULL DEFAULT NULL COMMENT '创建人名称',
    `create_time`        bigint                                                        NULL DEFAULT NULL COMMENT '创建时间',
    `update_user_id`     bigint                                                        NULL DEFAULT NULL COMMENT '更新人ID',
    `update_by`          varchar(32)                                                   NULL DEFAULT NULL COMMENT '更新人名称',
    `update_time`        bigint                                                        NULL DEFAULT NULL COMMENT '更新时间',
    `deleted`            tinyint(1)                                                         DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '用户视频序列归档;@Spe;@Fac;'
    ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for customer_video_series_video
-- ----------------------------
DROP TABLE IF EXISTS `customer_video_series_video`;
CREATE TABLE `customer_video_series_video`
    (
    `id`             bigint      NOT NULL COMMENT 'ID',
    `customer_id`    bigint      NOT NULL COMMENT '用户ID',
    `series_id`      bigint      NOT NULL COMMENT '列表ID',
    `video_id`       bigint      NOT NULL COMMENT '视频ID',
    `sort`           tinyint(4)  NOT NULL COMMENT '排序',
    `create_user_id` bigint      NULL DEFAULT NULL COMMENT '创建人ID',
    `create_by`      varchar(32) NULL DEFAULT NULL COMMENT '创建人名称',
    `create_time`    bigint      NULL DEFAULT NULL COMMENT '创建时间',
    `update_user_id` bigint      NULL DEFAULT NULL COMMENT '更新人ID',
    `update_by`      varchar(32) NULL DEFAULT NULL COMMENT '更新人名称',
    `update_time`    bigint      NULL DEFAULT NULL COMMENT '更新时间',
    `deleted`        tinyint(1)       DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '用户视频序列视频关联;@@P=customer_video_series;'
    ROW_FORMAT = DYNAMIC;
