-- 视频 HLS AES-128 加密支撑表
-- 若存在则先删除后重建
DROP TABLE IF EXISTS `video_hls_key`;
CREATE TABLE `video_hls_key` (
    `id`             bigint      NOT NULL COMMENT 'ID',
    `video_post_id`  bigint      NOT NULL COMMENT '视频稿件ID',
    `secret_key`     varchar(256)         NOT NULL COMMENT 'Base64 编码的 AES-128 密钥',
    `iv`             varchar(64)          DEFAULT NULL COMMENT '可选 IV，Base64',
    `key_path`       varchar(512)         NOT NULL COMMENT '密钥文件路径（绝对或相对 m3u8）',
    `algorithm`      varchar(32)          NOT NULL DEFAULT 'AES-128' COMMENT '加密算法，当前固定 AES-128',
    `create_user_id` bigint               DEFAULT NULL COMMENT '创建人ID',
    `create_by`      varchar(32)          DEFAULT NULL COMMENT '创建人名称',
    `create_time`    bigint               DEFAULT NULL COMMENT '创建时间',
    `update_user_id` bigint               DEFAULT NULL COMMENT '更新人ID',
    `update_by`      varchar(32)          DEFAULT NULL COMMENT '更新人名称',
    `update_time`    bigint               DEFAULT NULL COMMENT '更新时间',
    `deleted`        bigint      NOT NULL DEFAULT 0 COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_video_hls_key_video` (`video_post_id`) USING BTREE,
    INDEX `idx_video_hls_key_path` (`key_path`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '视频 HLS 密钥管理;@Spe;';
