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

-- ----------------------------
-- 测试数据 - 分类树形结构
-- ----------------------------
-- 顶级分类1：游戏
INSERT INTO category (id, parent_id, node_path, sort, code, name, icon, background, create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (1, 0, '/GAME/', 1, 'GAME', '游戏', 'icon-game', 'bg-game.jpg', 1, 'admin', UNIX_TIMESTAMP() * 1000, 1, 'admin',
        UNIX_TIMESTAMP() * 1000, 0);

-- 游戏子分类
INSERT INTO category (id, parent_id, node_path, sort, code, name, icon, background, create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (101, 1, '/GAME/GAME_MOBA/', 1, 'GAME_MOBA', 'MOBA', 'icon-moba', NULL, 1, 'admin', UNIX_TIMESTAMP() * 1000, 1,
        'admin', UNIX_TIMESTAMP() * 1000, 0);

INSERT INTO category (id, parent_id, node_path, sort, code, name, icon, background, create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (102, 1, '/GAME/GAME_FPS/', 2, 'GAME_FPS', 'FPS射击', 'icon-fps', NULL, 1, 'admin', UNIX_TIMESTAMP() * 1000, 1,
        'admin', UNIX_TIMESTAMP() * 1000, 0);

INSERT INTO category (id, parent_id, node_path, sort, code, name, icon, background, create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (103, 1, '/GAME/GAME_RPG/', 3, 'GAME_RPG', 'RPG角色扮演', 'icon-rpg', NULL, 1, 'admin', UNIX_TIMESTAMP() * 1000,
        1, 'admin', UNIX_TIMESTAMP() * 1000, 0);

-- MOBA 的三级分类
INSERT INTO category (id, parent_id, node_path, sort, code, name, icon, background, create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (1001, 101, '/GAME/GAME_MOBA/GAME_MOBA_LOL/', 1, 'GAME_MOBA_LOL', '英雄联盟', 'icon-lol', NULL, 1, 'admin',
        UNIX_TIMESTAMP() * 1000, 1, 'admin', UNIX_TIMESTAMP() * 1000, 0);

INSERT INTO category (id, parent_id, node_path, sort, code, name, icon, background, create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (1002, 101, '/GAME/GAME_MOBA/GAME_MOBA_DOTA2/', 2, 'GAME_MOBA_DOTA2', 'DOTA2', 'icon-dota2', NULL, 1, 'admin',
        UNIX_TIMESTAMP() * 1000, 1, 'admin', UNIX_TIMESTAMP() * 1000, 0);

-- 顶级分类2：知识
INSERT INTO category (id, parent_id, node_path, sort, code, name, icon, background, create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (2, 0, '/KNOWLEDGE/', 2, 'KNOWLEDGE', '知识', 'icon-knowledge', 'bg-knowledge.jpg', 1, 'admin',
        UNIX_TIMESTAMP() * 1000, 1, 'admin', UNIX_TIMESTAMP() * 1000, 0);

-- 知识子分类
INSERT INTO category (id, parent_id, node_path, sort, code, name, icon, background, create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (201, 2, '/KNOWLEDGE/KNOWLEDGE_TECH/', 1, 'KNOWLEDGE_TECH', '科技', 'icon-tech', NULL, 1, 'admin',
        UNIX_TIMESTAMP() * 1000, 1, 'admin', UNIX_TIMESTAMP() * 1000, 0);

INSERT INTO category (id, parent_id, node_path, sort, code, name, icon, background, create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (202, 2, '/KNOWLEDGE/KNOWLEDGE_SCIENCE/', 2, 'KNOWLEDGE_SCIENCE', '科学', 'icon-science', NULL, 1, 'admin',
        UNIX_TIMESTAMP() * 1000, 1, 'admin', UNIX_TIMESTAMP() * 1000, 0);

INSERT INTO category (id, parent_id, node_path, sort, code, name, icon, background, create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (203, 2, '/KNOWLEDGE/KNOWLEDGE_HUMANITIES/', 3, 'KNOWLEDGE_HUMANITIES', '人文', 'icon-humanities', NULL, 1,
        'admin', UNIX_TIMESTAMP() * 1000, 1, 'admin', UNIX_TIMESTAMP() * 1000, 0);

-- 科技的三级分类
INSERT INTO category (id, parent_id, node_path, sort, code, name, icon, background, create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (2001, 201, '/KNOWLEDGE/KNOWLEDGE_TECH/KNOWLEDGE_TECH_PROGRAM/', 1, 'KNOWLEDGE_TECH_PROGRAM', '编程',
        'icon-programming', NULL, 1, 'admin', UNIX_TIMESTAMP() * 1000, 1, 'admin', UNIX_TIMESTAMP() * 1000, 0);

INSERT INTO category (id, parent_id, node_path, sort, code, name, icon, background, create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (2002, 201, '/KNOWLEDGE/KNOWLEDGE_TECH/KNOWLEDGE_TECH_AI/', 2, 'KNOWLEDGE_TECH_AI', '人工智能', 'icon-ai', NULL,
        1, 'admin', UNIX_TIMESTAMP() * 1000, 1, 'admin', UNIX_TIMESTAMP() * 1000, 0);

-- 顶级分类3：生活
INSERT INTO category (id, parent_id, node_path, sort, code, name, icon, background, create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (3, 0, '/LIFE/', 3, 'LIFE', '生活', 'icon-life', 'bg-life.jpg', 1, 'admin', UNIX_TIMESTAMP() * 1000, 1, 'admin',
        UNIX_TIMESTAMP() * 1000, 0);

-- 生活子分类
INSERT INTO category (id, parent_id, node_path, sort, code, name, icon, background, create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (301, 3, '/LIFE/LIFE_FOOD/', 1, 'LIFE_FOOD', '美食', 'icon-food', NULL, 1, 'admin', UNIX_TIMESTAMP() * 1000, 1,
        'admin', UNIX_TIMESTAMP() * 1000, 0);

INSERT INTO category (id, parent_id, node_path, sort, code, name, icon, background, create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (302, 3, '/LIFE/LIFE_TRAVEL/', 2, 'LIFE_TRAVEL', '旅游', 'icon-travel', NULL, 1, 'admin',
        UNIX_TIMESTAMP() * 1000, 1, 'admin', UNIX_TIMESTAMP() * 1000, 0);

INSERT INTO category (id, parent_id, node_path, sort, code, name, icon, background, create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (303, 3, '/LIFE/LIFE_FASHION/', 3, 'LIFE_FASHION', '时尚', 'icon-fashion', NULL, 1, 'admin',
        UNIX_TIMESTAMP() * 1000, 1, 'admin', UNIX_TIMESTAMP() * 1000, 0);

-- 顶级分类4：娱乐
INSERT INTO category (id, parent_id, node_path, sort, code, name, icon, background, create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (4, 0, '/ENTERTAINMENT/', 4, 'ENTERTAINMENT', '娱乐', 'icon-entertainment', 'bg-entertainment.jpg', 1, 'admin',
        UNIX_TIMESTAMP() * 1000, 1, 'admin', UNIX_TIMESTAMP() * 1000, 0);

-- 娱乐子分类
INSERT INTO category (id, parent_id, node_path, sort, code, name, icon, background, create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (401, 4, '/ENTERTAINMENT/ENTERTAINMENT_MUSIC/', 1, 'ENTERTAINMENT_MUSIC', '音乐', 'icon-music', NULL, 1, 'admin',
        UNIX_TIMESTAMP() * 1000, 1, 'admin', UNIX_TIMESTAMP() * 1000, 0);

INSERT INTO category (id, parent_id, node_path, sort, code, name, icon, background, create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (402, 4, '/ENTERTAINMENT/ENTERTAINMENT_DANCE/', 2, 'ENTERTAINMENT_DANCE', '舞蹈', 'icon-dance', NULL, 1, 'admin',
        UNIX_TIMESTAMP() * 1000, 1, 'admin', UNIX_TIMESTAMP() * 1000, 0);

INSERT INTO category (id, parent_id, node_path, sort, code, name, icon, background, create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES (403, 4, '/ENTERTAINMENT/ENTERTAINMENT_MOVIE/', 3, 'ENTERTAINMENT_MOVIE', '影视', 'icon-movie', NULL, 1, 'admin',
        UNIX_TIMESTAMP() * 1000, 1, 'admin', UNIX_TIMESTAMP() * 1000, 0);
