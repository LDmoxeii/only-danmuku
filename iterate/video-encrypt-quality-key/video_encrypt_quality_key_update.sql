-- 1) video_hls_key_token：移除 key_id
ALTER TABLE `video_hls_key_token`
    DROP COLUMN `key_id`;
