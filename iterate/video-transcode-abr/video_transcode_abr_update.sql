-- 1) video_file_post（后台/稿件态 fileId）新增 ABR 元数据字段；状态沿用 transfer_result
ALTER TABLE `video_file_post`
    ADD COLUMN `abr_source_width`        int(11) DEFAULT NULL COMMENT 'ABR 源视频宽度(px)' AFTER `transfer_result`,
    ADD COLUMN `abr_source_height`       int(11) DEFAULT NULL COMMENT 'ABR 源视频高度(px)' AFTER `abr_source_width`,
    ADD COLUMN `abr_source_bitrate_kbps` int(11) DEFAULT NULL COMMENT 'ABR 源视频码率(kbps)' AFTER `abr_source_height`;

DROP TABLE IF EXISTS `video_hls_abr_variant`;
CREATE TABLE `video_hls_abr_variant`
    (
    `id`                 bigint       NOT NULL COMMENT 'ID',
    `file_id`            bigint       NOT NULL COMMENT '稿件态fileId;@Ref=video_file_post',
    `quality`            varchar(32)  NOT NULL COMMENT '清晰度档位，如 1080p/720p',
    `width`              int(11)      NOT NULL COMMENT '输出宽度(px)',
    `height`             int(11)      NOT NULL COMMENT '输出高度(px)',
    `video_bitrate_kbps` int(11)      NOT NULL COMMENT '视频码率(kbps)',
    `audio_bitrate_kbps` int(11)      NOT NULL COMMENT '音频码率(kbps)',
    `bandwidth_bps`      int(11)      NOT NULL COMMENT 'Master 中的 BANDWIDTH（bps，视频+音频估算）',
    `playlist_path`      varchar(512) NOT NULL COMMENT '子清晰度 m3u8 路径，如 720p/index.m3u8',
    `segment_prefix`     varchar(512)          DEFAULT NULL COMMENT '切片目录前缀，如 720p/',
    `segment_duration`   int(11)               DEFAULT NULL COMMENT '切片目标时长（秒），便于校验',
    `create_user_id`     bigint                DEFAULT NULL COMMENT '创建人ID',
    `create_by`          varchar(32)           DEFAULT NULL COMMENT '创建人名称',
    `create_time`        bigint                DEFAULT NULL COMMENT '创建时间',
    `update_user_id`     bigint                DEFAULT NULL COMMENT '更新人ID',
    `update_by`          varchar(32)           DEFAULT NULL COMMENT '更新人名称',
    `update_time`        bigint                DEFAULT NULL COMMENT '更新时间',
    `deleted`            bigint       NOT NULL DEFAULT 0 COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_video_hls_abr_variant` (`file_id`, `quality`, `deleted`) USING BTREE,
    INDEX `idx_video_hls_abr_variant_height` (`height`) USING BTREE
    )
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4 COMMENT = '视频 HLS ABR 清晰度档位;@P=video_file_post;';
