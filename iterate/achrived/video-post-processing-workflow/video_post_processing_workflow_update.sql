-- 视频投稿处理聚合 SQL 变更

DROP TABLE IF EXISTS `video_post_processing`;
CREATE TABLE `video_post_processing`
(
    `id`                 bigint     NOT NULL COMMENT 'ID',
    `video_post_id`       bigint     NOT NULL COMMENT '视频稿件ID',
    `total_files`         int        NOT NULL COMMENT '分P总数',
    `transcode_status`    tinyint(1) NOT NULL DEFAULT 0 COMMENT '转码状态 @E=0:UNKNOW:未知|1:PENDING:待处理|2:PROCESSING:处理中|3:SUCCESS:成功|4:FAILED:失败|5:SKIPPED:跳过;@T=ProcessStatus',
    `encrypt_status`      tinyint(1) NOT NULL DEFAULT 0 COMMENT '加密状态 @E=0:UNKNOW:未知|1:PENDING:待处理|2:PROCESSING:处理中|3:SUCCESS:成功|4:FAILED:失败|5:SKIPPED:跳过;@T=ProcessStatus',
    `transcode_done_count` int       NOT NULL DEFAULT 0 COMMENT '转码完成数',
    `encrypt_done_count`   int       NOT NULL DEFAULT 0 COMMENT '加密完成数（含 SKIPPED）',
    `failed_count`        int        NOT NULL DEFAULT 0 COMMENT '失败文件数',
    `last_fail_reason`    varchar(512) DEFAULT NULL COMMENT '最近失败原因',
    `create_user_id`      bigint              DEFAULT NULL COMMENT '创建人ID',
    `create_by`           varchar(32)         DEFAULT NULL COMMENT '创建人名称',
    `create_time`         bigint              DEFAULT NULL COMMENT '创建时间',
    `update_user_id`      bigint              DEFAULT NULL COMMENT '更新人ID',
    `update_by`           varchar(32)         DEFAULT NULL COMMENT '更新人名称',
    `update_time`         bigint              DEFAULT NULL COMMENT '更新时间',
    `deleted`             bigint     NOT NULL DEFAULT 0 COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_video_post_id` (`video_post_id`, `deleted`) USING BTREE,
    INDEX `idx_vpp_status` (`transcode_status`, `encrypt_status`, `update_time`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4 COMMENT = '视频稿件处理聚合;@Spe;@Fac;';

DROP TABLE IF EXISTS `video_post_processing_file`;
CREATE TABLE `video_post_processing_file`
(
    `id`                  bigint     NOT NULL COMMENT 'ID',
    `parent_id`            bigint     NOT NULL COMMENT '视频稿件处理ID@Ref=video_post_processing',
    `file_index`           int        NOT NULL COMMENT '文件索引',
    `upload_id`            bigint     NOT NULL COMMENT '上传会话ID',
    `transcode_status`     tinyint(1) NOT NULL DEFAULT 0 COMMENT '转码状态 @E=0:UNKNOW:未知|1:PENDING:待处理|2:PROCESSING:处理中|3:SUCCESS:成功|4:FAILED:失败|5:SKIPPED:跳过;@T=ProcessStatus',
    `encrypt_status`       tinyint(1) NOT NULL DEFAULT 0 COMMENT '加密状态 @E=0:UNKNOW:未知|1:PENDING:待处理|2:PROCESSING:处理中|3:SUCCESS:成功|4:FAILED:失败|5:SKIPPED:跳过;@T=ProcessStatus',
    `transcode_output_prefix` varchar(512) DEFAULT NULL COMMENT '转码输出对象前缀',
    `transcode_output_path`   varchar(512) DEFAULT NULL COMMENT '转码输出本地路径',
    `transcode_variants_json` text                 DEFAULT NULL COMMENT '转码清晰度结果 JSON',
    `encrypt_output_dir`      varchar(512) DEFAULT NULL COMMENT '加密输出本地目录',
    `encrypt_output_prefix`   varchar(512) DEFAULT NULL COMMENT '加密输出对象前缀',
    `duration`             int                 DEFAULT NULL COMMENT '时长（秒）',
    `file_size`            bigint              DEFAULT NULL COMMENT '文件大小',
    `fail_reason`          varchar(512)        DEFAULT NULL COMMENT '失败原因',
    `create_user_id`       bigint              DEFAULT NULL COMMENT '创建人ID',
    `create_by`            varchar(32)         DEFAULT NULL COMMENT '创建人名称',
    `create_time`          bigint              DEFAULT NULL COMMENT '创建时间',
    `update_user_id`       bigint              DEFAULT NULL COMMENT '更新人ID',
    `update_by`            varchar(32)         DEFAULT NULL COMMENT '更新人名称',
    `update_time`          bigint              DEFAULT NULL COMMENT '更新时间',
    `deleted`              bigint     NOT NULL DEFAULT 0 COMMENT '删除标识 0：未删除 id：已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_vppf_file` (`parent_id`, `file_index`, `deleted`) USING BTREE,
    INDEX `idx_vppf_post` (`parent_id`) USING BTREE,
    INDEX `idx_vppf_transcode` (`transcode_status`, `update_time`) USING BTREE,
    INDEX `idx_vppf_encrypt` (`encrypt_status`, `update_time`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4 COMMENT = '视频稿件处理文件状态;@P=video_post_processing;';

DROP TABLE IF EXISTS `video_post_processing_abr_variant`;
DROP TABLE IF EXISTS `video_post_processing_variant`;
DROP TABLE IF EXISTS `video_post_processing_quality_auth`;
CREATE TABLE `video_post_processing_variant`
(
    `id`                bigint     NOT NULL COMMENT 'ID',
    `parent_id`          bigint     NOT NULL COMMENT '视频稿件处理文件ID@Ref=video_post_processing_file',
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
    UNIQUE KEY `uk_vpp_av` (`parent_id`, `quality`, `deleted`) USING BTREE,
    KEY `idx_vpp_av_height` (`height`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4 COMMENT = '视频稿件处理分辨率档位;@P=video_post_processing_file;';

-- 本次迭代不包含“处理进度”读模型表
