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
    `deleted`        bigint                                                             DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_key_code` (`code`, `deleted`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci COMMENT = '分类信息;@Spe;@Fac;'
    ROW_FORMAT = DYNAMIC;

INSERT INTO `category`
VALUES (231168817651990528, 0, '/231168817651990528/', 1, 'bmig', '编程',
        'cover/202510/c858cef77cad4d8aa0d2f36923e60b.png', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0),
       (231168931523149824, 0, '/231168931523149824/', 2, 'rfgsving', '人工智能',
        'cover/202510/e4530034d0ab4592ae3153e1ea8c1a.png', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0),
       (231168999944830976, 0, '/231168999944830976/', 3, 'yyds', '运动',
        'cover/202510/5c104949cd334ecfb4bb131491e55c.png', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0),
       (231169038893137920, 0, '/231169038893137920/', 4, 'mwui', '美食',
        'cover/202510/784f6251c0824c64abae46b2037fa4.png', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0),
       (231169068978880512, 0, '/231169068978880512/', 5, 'qiie', '汽车',
        'cover/202510/ed2433e48354420bbc92ccdafa0448.png', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0),
       (231169246955782144, 0, '/231169246955782144/', 6, 'ybyt', '音乐',
        'cover/202510/ca3051f6e31141ad88b551e7434e89.png', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0),
       (231169290559766528, 0, '/231169290559766528/', 7, 'dshx', '动画',
        'cover/202510/b10897a728024d489b2ed138664011.png', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0),
       (231169343441551360, 0, '/231169343441551360/', 8, 'dmyk', '电影',
        'cover/202510/7e7e1cac2ce04d49baab9008a07baa.png', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0),
       (231169395257982976, 0, '/231169395257982976/', 9, 'viui', '知识',
        'cover/202510/2964320eb93e46099f7d4d6137ddcc.png', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0),
       (231169455911813120, 0, '/231169455911813120/', 10, 'yzxi', '游戏',
        'cover/202510/bc707d7edd7d453cb3bba7ea70743f.png', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0),
       (231169527605051392, 0, '/231169527605051392/', 11, 'gcxn', '搞笑',
        'cover/202510/232091fe49b9439cac2a8648174363.png', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0),
       (231169587294191616, 0, '/231169587294191616/', 12, 'drju', '短剧',
        'cover/202510/576081f985724989aedf95789b67bd.png', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0),
       (231169639785906176, 0, '/231169639785906176/', 13, 'huwd', '户外',
        'cover/202510/63d9afeb347449518b66e97272c9c1.png', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0),
       (231169735168573440, 0, '/231169735168573440/', 14, 'zixy', '资讯',
        'cover/202510/15a13f0cce55451ba67edf4ffb451d.png', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0),
       (231169791888146432, 0, '/231169791888146432/', 15, 'ugho', '生活',
        'cover/202510/5d0051d3df2a48aa829db5e397a185.png', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0),
       (231169851648589824, 0, '/231169851648589824/', 16, 'jilupm', '记录片',
        'cover/202510/881f58f9f0d34a3c9810428efe36f0.png', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0),
       (231169884234137600, 0, '/231169884234137600/', 17, 'keji', '科技',
        'cover/202510/81aa2dbf29b546ba9f5fa359e10389.png', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0),
       (231169930375675904, 0, '/231169930375675904/', 18, 'wudc', '舞蹈',
        'cover/202510/aefa3b506bc040cd9f4c0fb0602912.png', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0),
       (231169978782138368, 0, '/231169978782138368/', 19, 'vlog', 'VLOG',
        'cover/202510/6d2fe77adcda46a3a5f49ed8fde9ff.png', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0);
