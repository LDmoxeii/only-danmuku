DROP TABLE IF EXISTS `video_file_variant`;
CREATE TABLE `video_file_variant`
    (
    `id`                bigint      NOT NULL COMMENT 'ID',
    `parent_id`         bigint      NOT NULL COMMENT '视频文件ID@Ref=video_file',
    `quality`           varchar(32) NOT NULL COMMENT '清晰度档位，如 1080p/720p',
    `width`             int         NOT NULL COMMENT '输出宽度(px)',
    `height`            int         NOT NULL COMMENT '输出高度(px)',
    `video_bitrate_kbps` int        NOT NULL COMMENT '视频码率(kbps)',
    `audio_bitrate_kbps` int        NOT NULL COMMENT '音频码率(kbps)',
    `bandwidth_bps`     int         NOT NULL COMMENT 'Master 中的 BANDWIDTH（bps）',
    `playlist_path`     varchar(512) NOT NULL COMMENT '子清晰度 m3u8 路径，如 720p/index.m3u8',
    `segment_prefix`    varchar(512) DEFAULT NULL COMMENT '切片目录前缀，如 720p/',
    `segment_duration`  int         DEFAULT NULL COMMENT '切片目标时长（秒）',
    `create_user_id`    bigint      DEFAULT NULL COMMENT '创建人ID',
    `create_by`         varchar(32) DEFAULT NULL COMMENT '创建人名称',
    `create_time`       bigint      DEFAULT NULL COMMENT '创建时间',
    `update_user_id`    bigint      DEFAULT NULL COMMENT '更新人ID',
    `update_by`         varchar(32) DEFAULT NULL COMMENT '更新人名称',
    `update_time`       bigint      DEFAULT NULL COMMENT '更新时间',
    `deleted`           bigint      NOT NULL DEFAULT 0 COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_i` (`parent_id`, `quality`, `deleted`) USING BTREE,
    KEY `idx_vfv_height` (`height`) USING BTREE
    )
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '视频文件分辨率档位;@P=video_file;';
