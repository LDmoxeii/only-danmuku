alter table video_file_post
    comment '视频文件信息;';
alter table video_file_post
    modify video_id bigint not null comment '视频ID';
