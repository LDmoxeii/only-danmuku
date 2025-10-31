-- ----------------------------
-- Table structure for customer_focus
-- ----------------------------
DROP TABLE IF EXISTS `customer_focus`;
CREATE TABLE `customer_focus`
    (
    `id`                bigint           NOT NULL COMMENT 'ID',
    `customer_id`       bigint           NOT NULL COMMENT '用户ID',
    `focus_customer_id` bigint           NOT NULL COMMENT '用户ID',
    `create_user_id`    bigint NULL DEFAULT NULL COMMENT '创建人ID',
    `create_by`         varchar(32) NULL DEFAULT NULL COMMENT '创建人名称',
    `create_time`       bigint NULL DEFAULT NULL COMMENT '创建时间',
    `update_user_id`    bigint NULL DEFAULT NULL COMMENT '更新人ID',
    `update_by`         varchar(32) NULL DEFAULT NULL COMMENT '更新人名称',
    `update_time`       bigint NULL DEFAULT NULL COMMENT '更新时间',
    `deleted`           bigint DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE
    ) ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '用户关注;@Spe;@Fac;'
    ROW_FORMAT = DYNAMIC;
