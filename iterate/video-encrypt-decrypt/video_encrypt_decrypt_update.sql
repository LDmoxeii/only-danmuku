-- 1) video_file_post：加密状态与密钥关联
ALTER TABLE `video_file_post`
    ADD COLUMN `encrypt_status`      int(11)      NOT NULL DEFAULT 1 COMMENT '加密状态@E=1:UNENCRYPTED:未加密|2:ENCRYPTING:加密中|3:ENCRYPTED:已加密|4:FAILED:失败;@T=EncryptStatus' AFTER `abr_source_bitrate_kbps`,
    ADD COLUMN `encrypt_method`      varchar(32)           DEFAULT NULL COMMENT '加密方式，如 HLS_AES_128' AFTER `encrypt_status`,
    ADD COLUMN `encrypt_key_id`      bigint                DEFAULT NULL COMMENT '关联密钥ID' AFTER `encrypt_method`,
    ADD COLUMN `encrypt_fail_reason` varchar(512)          DEFAULT NULL COMMENT '加密失败原因' AFTER `encrypt_key_id`;

-- 2) video_hls_encrypt_key：每稿件的密钥与版本（可绑定清晰度）
DROP TABLE IF EXISTS `video_hls_encrypt_key`;
CREATE TABLE `video_hls_encrypt_key`
(
    `id`                bigint       NOT NULL COMMENT 'ID',
    `file_id`           bigint       NOT NULL COMMENT '稿件态 fileId',
    `quality`           varchar(32)           DEFAULT NULL COMMENT '绑定清晰度，空表示全局通用',
    `key_id`            varchar(64)  NOT NULL COMMENT 'Key ID（m3u8 暴露）',
    `key_ciphertext`    varchar(512) NOT NULL COMMENT '密钥密文（KMS 加密后 Base64）',
    `iv_hex`            varchar(64)           DEFAULT NULL COMMENT 'IV hex（16字节，可空）',
    `key_version`       int(11)      NOT NULL DEFAULT 1 COMMENT '密钥版本号（轮换递增）',
    `method`            varchar(32)  NOT NULL DEFAULT 'HLS_AES_128' COMMENT '加密方式',
    `key_uri_template`  varchar(512) NOT NULL COMMENT 'm3u8 中的 URI 模板，含 token 占位',
    `expire_time`       bigint                DEFAULT NULL COMMENT '过期时间（ms）',
    `status`            int(11)      NOT NULL DEFAULT 1 COMMENT '状态@E=1:ACTIVE:可用|2:REVOKED:吊销|3:EXPIRED:过期;@T=EncryptKeyStatus',
    `remark`            varchar(256)          DEFAULT NULL COMMENT '备注/轮换原因',
    `create_user_id`    bigint                DEFAULT NULL COMMENT '创建人ID',
    `create_by`         varchar(32)           DEFAULT NULL COMMENT '创建人名称',
    `create_time`       bigint                DEFAULT NULL COMMENT '创建时间',
    `update_user_id`    bigint                DEFAULT NULL COMMENT '更新人ID',
    `update_by`         varchar(32)           DEFAULT NULL COMMENT '更新人名称',
    `update_time`       bigint                DEFAULT NULL COMMENT '更新时间',
    `deleted`           bigint       NOT NULL DEFAULT 0 COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_i` (`file_id`, `key_id`, `key_version`, `quality`, `deleted`) USING BTREE,
    INDEX `idx_video_hls_encrypt_key_file` (`file_id`, `status`) USING BTREE,
    INDEX `idx_video_hls_encrypt_key_expire` (`expire_time`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4 COMMENT = '视频 HLS 加密密钥';

-- 3) video_hls_quality_auth：分辨率分级授权策略
DROP TABLE IF EXISTS `video_hls_quality_auth`;
CREATE TABLE `video_hls_quality_auth`
(
    `id`             bigint       NOT NULL COMMENT 'ID',
    `file_id`        bigint       NOT NULL COMMENT '稿件态 fileId',
    `quality`        varchar(32)  NOT NULL COMMENT '清晰度档位，如 1080p',
    `auth_policy`    int(11)      NOT NULL DEFAULT 1 COMMENT '授权策略@E=1:PUBLIC:公开|2:LOGIN:登录|3:PAID:付费|4:CUSTOM:自定义;@T=QualityAuthPolicy',
    `remark`         varchar(256)          DEFAULT NULL COMMENT '备注/策略说明',
    `create_user_id` bigint                DEFAULT NULL COMMENT '创建人ID',
    `create_by`      varchar(32)           DEFAULT NULL COMMENT '创建人名称',
    `create_time`    bigint                DEFAULT NULL COMMENT '创建时间',
    `update_user_id` bigint                DEFAULT NULL COMMENT '更新人ID',
    `update_by`      varchar(32)           DEFAULT NULL COMMENT '更新人名称',
    `update_time`    bigint                DEFAULT NULL COMMENT '更新时间',
    `deleted`        bigint       NOT NULL DEFAULT 0 COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_i` (`file_id`, `quality`, `deleted`) USING BTREE,
    INDEX `idx_video_hls_quality_auth_policy` (`auth_policy`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4 COMMENT = '视频清晰度授权策略';

-- 4) video_hls_key_token：播放 token 发放与使用计数
DROP TABLE IF EXISTS `video_hls_key_token`;
CREATE TABLE `video_hls_key_token`
(
    `id`              bigint       NOT NULL COMMENT 'ID',
    `file_id`         bigint       NOT NULL COMMENT '稿件态 fileId',
    `key_id`          varchar(64)  NOT NULL COMMENT 'Key ID',
    `key_version`     int(11)      NOT NULL DEFAULT 1 COMMENT '密钥版本',
    `allowed_qualities` varchar(512)        DEFAULT NULL COMMENT '授权清晰度列表 JSON，空表示全量',
    `token_hash`      varchar(128) NOT NULL COMMENT 'token 哈希（sha256）',
    `audience`        varchar(128)          DEFAULT NULL COMMENT '受众标识（用户/终端）',
    `expire_time`     bigint       NOT NULL COMMENT '过期时间（ms）',
    `max_use`         int(11)      NOT NULL DEFAULT 5 COMMENT '最大可用次数',
    `used_count`      int(11)      NOT NULL DEFAULT 0 COMMENT '已使用次数',
    `status`          int(11)      NOT NULL DEFAULT 1 COMMENT '状态@E=1:VALID:有效|2:EXHAUSTED:已用尽|3:EXPIRED:过期|4:REVOKED:吊销;@T=EncryptTokenStatus',
    `issue_ip`        varchar(64)           DEFAULT NULL COMMENT '签发 IP',
    `create_user_id`  bigint                DEFAULT NULL COMMENT '创建人ID',
    `create_by`       varchar(32)           DEFAULT NULL COMMENT '创建人名称',
    `create_time`     bigint                DEFAULT NULL COMMENT '创建时间',
    `update_user_id`  bigint                DEFAULT NULL COMMENT '更新人ID',
    `update_by`       varchar(32)           DEFAULT NULL COMMENT '更新人名称',
    `update_time`     bigint                DEFAULT NULL COMMENT '更新时间',
    `deleted`         bigint       NOT NULL DEFAULT 0 COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_i` (`token_hash`, `deleted`) USING BTREE,
    INDEX `idx_video_hls_key_token_file` (`file_id`, `key_id`, `status`) USING BTREE,
    INDEX `idx_video_hls_key_token_expire` (`expire_time`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4 COMMENT = 'HLS 加密播放 token';
