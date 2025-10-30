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

-- ----------------------------
-- Test data for customer_focus
-- ----------------------------
-- 用户 2001 (张三) 关注了 2002 (李四) 和 2003 (王五)
INSERT INTO customer_focus (id, customer_id, focus_customer_id, create_user_id, create_by, create_time, update_user_id,
                            update_by, update_time, deleted)
VALUES (6001, 2001, 2002, 2001, 'user_zhang', 1729267200, 2001, 'user_zhang', 1729267200, 0),
       (6002, 2001, 2003, 2001, 'user_zhang', 1729353600, 2001, 'user_zhang', 1729353600, 0);

-- 用户 2002 (李四) 关注了 2001 (张三) 和 2003 (王五)
INSERT INTO customer_focus (id, customer_id, focus_customer_id, create_user_id, create_by, create_time, update_user_id,
                            update_by, update_time, deleted)
VALUES (6003, 2002, 2001, 2002, 'user_li', 1729267200, 2002, 'user_li', 1729267200, 0),
       (6004, 2002, 2003, 2002, 'user_li', 1729267200, 2002, 'user_li', 1729267200, 0);

-- 用户 2003 (王五) 关注了 2001 (张三)
INSERT INTO customer_focus (id, customer_id, focus_customer_id, create_user_id, create_by, create_time, update_user_id,
                            update_by, update_time, deleted)
VALUES (6005, 2003, 2001, 2003, 'user_wang', 1729267200, 2003, 'user_wang', 1729267200, 0);
