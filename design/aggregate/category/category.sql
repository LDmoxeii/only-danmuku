-- ----------------------------
-- Table structure for category_info
-- ----------------------------
DROP TABLE IF EXISTS category;
CREATE TABLE category
    (
    `id`             bigint                                                        NOT NULL COMMENT 'ID',
    `parent_id`      bigint                                                        NOT NULL COMMENT '父级ID',
    `node_path`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '路径',
    `sort`           tinyint(4)                                                    NOT NULL COMMENT '排序号',
    `code`           varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '编码',
    `name`           varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '名称',
    `icon`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '图标',
    `background`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '背景图',
    `create_user_id` bigint                                                        NULL DEFAULT NULL COMMENT '创建人ID',
    `create_by`      varchar(32)                                                   NULL DEFAULT NULL COMMENT '创建人名称',
    `create_time`    bigint                                                        NULL DEFAULT NULL COMMENT '创建时间',
    `update_user_id` bigint                                                        NULL DEFAULT NULL COMMENT '更新人ID',
    `update_by`      varchar(32)                                                   NULL DEFAULT NULL COMMENT '更新人名称',
    `update_time`    bigint                                                        NULL DEFAULT NULL COMMENT '更新时间',
    `deleted`        tinyint(1)                                                         DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_key_code` (`code`, `deleted`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '分类信息;@Spe;@Fac;'
    ROW_FORMAT = DYNAMIC;
