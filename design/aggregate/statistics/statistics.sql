-- ----------------------------
-- Table structure for statistics_info
-- ----------------------------
DROP TABLE IF EXISTS `statistics_info`;
CREATE TABLE `statistics_info`
    (
    `id`               bigint      NOT NULL COMMENT 'ID',
    `customer_id`      bigint      NOT NULL COMMENT '用户ID',
    `data_type`        tinyint(1)  NOT NULL COMMENT '数据统计类型 @E=1:VIDEO_VIEW:视频观看|2:VIDEO_LIKE:视频点赞|3:VIDEO_COMMENT:视频评论|4:VIDEO_SHARE:视频分享|5:USER_FOLLOW:用户关注|6:USER_LOGIN:用户登录;@T=StatisticsDataType',
    `statistics_count` int(11)     NULL DEFAULT NULL COMMENT '统计数量',
    `statistics_date`  bigint      NOT NULL COMMENT '统计日期',
    `create_user_id`   bigint      NULL DEFAULT NULL COMMENT '创建人ID',
    `create_by`        varchar(32) NULL DEFAULT NULL COMMENT '创建人名称',
    `create_time`      bigint      NULL DEFAULT NULL COMMENT '创建时间',
    `update_user_id`   bigint      NULL DEFAULT NULL COMMENT '更新人ID',
    `update_by`        varchar(32) NULL DEFAULT NULL COMMENT '更新人名称',
    `update_time`      bigint      NULL DEFAULT NULL COMMENT '更新时间',
    `deleted`          tinyint(1)       DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '统计信息\nSpe;@Fac;'
    ROW_FORMAT = DYNAMIC;
