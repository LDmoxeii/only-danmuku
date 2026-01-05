-- 1) video_hls_key_token：移除 key_id
ALTER TABLE `video_hls_key_token`
    DROP COLUMN `key_id`;
-- 2) video_hls_encrypt_key：修改 quality 为必填
ALTER TABLE `video_hls_encrypt_key`
    MODIFY `quality` VARCHAR(32) NOT NULL COMMENT '绑定清晰度';
