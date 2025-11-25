-- auto-generated definition
create table video_file_post
    (
    id                      bigint            not null comment 'ID' primary key,
    upload_id               bigint            not null comment '上传ID',
    customer_id             bigint            not null comment '用户ID',
    video_id                bigint            not null comment '视频ID',
    file_index              int               not null comment '文件索引',
    file_name               varchar(200)      null comment '文件名',
    file_size               bigint            null comment '文件大小',
    file_path               varchar(100)      null comment '文件路径',
    update_type             tinyint default 0 not null comment '更新类型 @E=0:UNKNOW:未知类型|1:NO_UPDATE:无更新|2:HAS_UPDATE:有更新;@T=UpdateType',
    transfer_result         tinyint default 0 not null comment '转码结果 @E=0:UNKNOW:未知结果|1:TRANSCODING:转码中|2:SUCCESS:转码成功|3:FAILED:转码失败;@T=TransferResult',
    abr_source_width        int               null comment 'ABR 源视频宽度(px)',
    abr_source_height       int               null comment 'ABR 源视频高度(px)',
    abr_source_bitrate_kbps int               null comment 'ABR 源视频码率(kbps)',
    duration                int               null comment '持续时间（秒）',
    create_user_id          bigint            null comment '创建人ID',
    create_by               varchar(32)       null comment '创建人名称',
    create_time             bigint            null comment '创建时间',
    update_user_id          bigint            null comment '更新人ID',
    update_by               varchar(32)       null comment '更新人名称',
    update_time             bigint            null comment '更新时间',
    deleted                 bigint  default 0 not null comment '删除标识 0：未删除 id：已删除',
    constraint uk_v_upload_id unique (upload_id, customer_id, deleted)
    ) comment '视频文件信息;'
    collate = utf8mb4_general_ci
    row_format = DYNAMIC;

-- auto-generated definition
create table video_hls_abr_variant
    (
    id                 bigint           not null comment 'ID' primary key,
    file_id            bigint           not null comment '稿件态fileId@@Ref=video_file_post',
    quality            varchar(32)      not null comment '清晰度档位，如 1080p/720p',
    width              int              not null comment '输出宽度(px)',
    height             int              not null comment '输出高度(px)',
    video_bitrate_kbps int              not null comment '视频码率(kbps)',
    audio_bitrate_kbps int              not null comment '音频码率(kbps)',
    bandwidth_bps      int              not null comment 'Master 中的 BANDWIDTH（bps，视频+音频估算）',
    playlist_path      varchar(512)     not null comment '子清晰度 m3u8 路径，如 720p/index.m3u8',
    segment_prefix     varchar(512)     null comment '切片目录前缀，如 720p/',
    segment_duration   int              null comment '切片目标时长（秒），便于校验',
    create_user_id     bigint           null comment '创建人ID',
    create_by          varchar(32)      null comment '创建人名称',
    create_time        bigint           null comment '创建时间',
    update_user_id     bigint           null comment '更新人ID',
    update_by          varchar(32)      null comment '更新人名称',
    update_time        bigint           null comment '更新时间',
    deleted            bigint default 0 not null comment '删除标识 0：未删除 id：已删除',
    constraint uk_v_quality unique (file_id, quality, deleted)
    ) comment '视频 HLS ABR 清晰度档位;@P=video_file_post;';

create index idx_video_hls_abr_variant_height on video_hls_abr_variant (height);





