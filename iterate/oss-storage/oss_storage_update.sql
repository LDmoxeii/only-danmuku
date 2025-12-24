-- 1) video_file_upload_session：临时路径可能为系统绝对路径，扩展长度
ALTER TABLE `video_file_upload_session`
    MODIFY COLUMN `temp_path` varchar(512) DEFAULT NULL COMMENT '临时目录（绝对或相对路径）';

-- 本迭代其余字段保持不变，file_path/video_cover/avatar/icon 继续存相对 key
