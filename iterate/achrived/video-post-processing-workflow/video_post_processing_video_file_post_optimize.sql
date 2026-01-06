-- video_file_post 保留与字段优化（无数据版本）

-- 1) 清理旧聚合子表
DROP TABLE IF EXISTS `video_hls_abr_variant`;
DROP TABLE IF EXISTS `video_hls_quality_auth`;
DROP TABLE IF EXISTS `video_file_post_variant`;
DROP TABLE IF EXISTS `video_quality_policy`;

-- 2) video_file_post 增加 video_post_id 与产物摘要字段，移除处理阶段冗余字段
ALTER TABLE `video_file_post`
    CHANGE COLUMN `video_id` `video_post_id` bigint NOT NULL COMMENT '视频稿件ID' AFTER `id`,
    ADD COLUMN `transcode_output_prefix` varchar(512) DEFAULT NULL COMMENT '转码输出对象前缀' AFTER `file_path`,
    ADD COLUMN `transcode_variants_json` text DEFAULT NULL COMMENT '转码清晰度结果 JSON' AFTER `transcode_output_prefix`,
    ADD COLUMN `encrypt_output_prefix` varchar(512) DEFAULT NULL COMMENT '加密输出对象前缀' AFTER `transcode_variants_json`,
    ADD COLUMN `encrypt_key_version` int DEFAULT NULL COMMENT '加密密钥版本' AFTER `encrypt_method`,
    DROP COLUMN `abr_source_width`,
    DROP COLUMN `abr_source_height`,
    DROP COLUMN `abr_source_bitrate_kbps`,
    DROP COLUMN `encrypt_key_id`,
    DROP COLUMN `encrypt_fail_reason`,
    DROP COLUMN `update_type`,
    ADD UNIQUE KEY `uk_video_file_post_post_file` (`video_post_id`, `file_index`, `deleted`);

ALTER TABLE `video_file_post` COMMENT = '视频文件信息;@P=video_post;';

-- 3) 稿件文件分辨率档位子实体
CREATE TABLE `video_file_post_variant`
(
    `id`                bigint     NOT NULL COMMENT 'ID',
    `parent_id`          bigint     NOT NULL COMMENT '稿件文件ID@Ref=video_file_post',
    `quality`            varchar(32) NOT NULL COMMENT '清晰度档位，如 1080p/720p',
    `width`              int        NOT NULL COMMENT '输出宽度(px)',
    `height`             int        NOT NULL COMMENT '输出高度(px)',
    `video_bitrate_kbps` int        NOT NULL COMMENT '视频码率(kbps)',
    `audio_bitrate_kbps` int        NOT NULL COMMENT '音频码率(kbps)',
    `bandwidth_bps`      int        NOT NULL COMMENT 'Master 中的 BANDWIDTH（bps）',
    `playlist_path`      varchar(512) NOT NULL COMMENT '子清晰度 m3u8 路径，如 720p/index.m3u8',
    `segment_prefix`     varchar(512) DEFAULT NULL COMMENT '切片目录前缀，如 720p/',
    `segment_duration`   int        DEFAULT NULL COMMENT '切片目标时长（秒）',
    `create_user_id`     bigint     DEFAULT NULL COMMENT '创建人ID',
    `create_by`          varchar(32) DEFAULT NULL COMMENT '创建人名称',
    `create_time`        bigint     DEFAULT NULL COMMENT '创建时间',
    `update_user_id`     bigint     DEFAULT NULL COMMENT '更新人ID',
    `update_by`          varchar(32) DEFAULT NULL COMMENT '更新人名称',
    `update_time`        bigint     DEFAULT NULL COMMENT '更新时间',
    `deleted`            bigint     NOT NULL DEFAULT 0 COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_vfpv_quality` (`parent_id`, `quality`, `deleted`) USING BTREE,
    KEY `idx_vfpv_height` (`height`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4 COMMENT = '视频稿件文件分辨率档位;@P=video_file_post;';

-- 4) 分辨率策略聚合（发布后设置）
CREATE TABLE `video_quality_policy`
(
    `id`            bigint     NOT NULL COMMENT 'ID',
    `video_id`       bigint     NOT NULL COMMENT '视频ID',
    `file_index`     int        NOT NULL COMMENT '文件索引',
    `quality`        varchar(32) NOT NULL COMMENT '清晰度档位，如 1080p',
    `auth_policy`    int        NOT NULL DEFAULT 1 COMMENT '授权策略@E=0:UNKNOW:未知|1:PUBLIC:公开|2:LOGIN:登录|3:PAID:付费|4:CUSTOM:自定义;@T=QualityAuthPolicy',
    `remark`         varchar(256) DEFAULT NULL COMMENT '备注/策略说明',
    `create_user_id` bigint     DEFAULT NULL COMMENT '创建人ID',
    `create_by`      varchar(32) DEFAULT NULL COMMENT '创建人名称',
    `create_time`    bigint     DEFAULT NULL COMMENT '创建时间',
    `update_user_id` bigint     DEFAULT NULL COMMENT '更新人ID',
    `update_by`      varchar(32) DEFAULT NULL COMMENT '更新人名称',
    `update_time`    bigint     DEFAULT NULL COMMENT '更新时间',
    `deleted`        bigint     NOT NULL DEFAULT 0 COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_video_quality_policy` (`video_id`, `file_index`, `quality`, `deleted`) USING BTREE,
    KEY `idx_video_quality_policy` (`auth_policy`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4 COMMENT = '视频清晰度策略;@Spe;@Fac;';

-- 5) 加密密钥：改用 video_post_id + file_index，转正后补 video_id
ALTER TABLE `video_hls_encrypt_key`
    DROP INDEX `uk_i`,
    DROP INDEX `idx_video_hls_encrypt_key_file`,
    DROP COLUMN `file_id`,
    ADD COLUMN `video_post_id` bigint NOT NULL COMMENT '视频稿件ID' AFTER `id`,
    ADD COLUMN `video_id` bigint DEFAULT NULL COMMENT '视频ID' AFTER `video_post_id`,
    ADD COLUMN `file_index` int NOT NULL COMMENT '文件索引' AFTER `video_id`,
    ADD UNIQUE KEY `uk_post_file_key` (`video_post_id`, `file_index`, `key_id`, `key_version`, `quality`, `deleted`),
    ADD KEY `idx_video_hls_encrypt_key_post` (`video_post_id`, `file_index`, `status`),
    ADD KEY `idx_video_hls_encrypt_key_video` (`video_id`, `file_index`, `status`);

-- 6) 播放 token：改用 video_post_id + file_index，转正后补 video_id
ALTER TABLE `video_hls_key_token`
    DROP INDEX `idx_video_hls_key_token_file`,
    DROP COLUMN `file_id`,
    ADD COLUMN `video_post_id` bigint NOT NULL COMMENT '视频稿件ID' AFTER `id`,
    ADD COLUMN `video_id` bigint DEFAULT NULL COMMENT '视频ID' AFTER `video_post_id`,
    ADD COLUMN `file_index` int NOT NULL COMMENT '文件索引' AFTER `video_id`,
    ADD KEY `idx_video_hls_key_token_post` (`video_post_id`, `file_index`, `status`),
    ADD KEY `idx_video_hls_key_token_video` (`video_id`, `file_index`, `status`);
