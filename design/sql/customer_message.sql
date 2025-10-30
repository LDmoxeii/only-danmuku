-- ----------------------------
-- Table structure for customer_message
-- ----------------------------
DROP TABLE IF EXISTS `customer_message`;
CREATE TABLE `customer_message`
    (
    `id`              bigint                                                NOT NULL COMMENT 'ID',
    `customer_id`     bigint                                                NOT NULL COMMENT '用户ID',
    `video_id`        bigint                                                NULL     DEFAULT NULL COMMENT '主体ID',
    `message_type`    tinyint(1)                                            NOT NULL DEFAULT 0 COMMENT '消息类型 @E=0:UNKNOW:未知消息|1:SYSTEM_MESSAGE:系统消息|2:COMMENT_REPLY:评论回复|3:VIDEO_DYNAMIC:视频动态|4:PRIVATE_MESSAGE:私信消息|5:ACTIVITY_NOTICE:活动通知|6:OTHER_MESSAGE:其他消息;@T=MessageType',
    `send_subject_id` bigint                                                NULL     DEFAULT NULL COMMENT '发送主体ID',
    `read_type`       tinyint(1)                                            NOT NULL DEFAULT 0 COMMENT '读取状态 @E=0:UNKNOW:未知状态|1:UNREAD:未读|2:READ:已读;@T=ReadType',
    `extend_json`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
    `create_user_id`  bigint                                                NULL     DEFAULT NULL COMMENT '创建人ID',
    `create_by`       varchar(32)                                           NULL     DEFAULT NULL COMMENT '创建人名称',
    `create_time`     bigint                                                NULL     DEFAULT NULL COMMENT '创建时间',
    `update_user_id`  bigint                                                NULL     DEFAULT NULL COMMENT '更新人ID',
    `update_by`       varchar(32)                                           NULL     DEFAULT NULL COMMENT '更新人名称',
    `update_time`     bigint                                                NULL     DEFAULT NULL COMMENT '更新时间',
    `deleted`         bigint                                                         DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE
    )
    ENGINE = InnoDB
    AUTO_INCREMENT = 23
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '用户消息表;@Spe;@Fac;'
    ROW_FORMAT = DYNAMIC;
