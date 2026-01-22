-- 单分辨率加密重构 - 处理聚合 SQL 变更

-- 处理档位（variant）增加状态字段
ALTER TABLE `video_post_processing_variant`
    ADD COLUMN `transcode_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '转码状态 @E=0:UNKNOW:未知|1:PENDING:待处理|2:PROCESSING:处理中|3:SUCCESS:成功|4:FAILED:失败|5:SKIPPED:跳过;@T=ProcessStatus' AFTER `segment_duration`,
    ADD COLUMN `encrypt_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '加密状态 @E=0:UNKNOW:未知|1:PENDING:待处理|2:PROCESSING:处理中|3:SUCCESS:成功|4:FAILED:失败|5:SKIPPED:跳过;@T=ProcessStatus' AFTER `transcode_status`,
    ADD COLUMN `encrypt_fail_reason` varchar(512) DEFAULT NULL COMMENT '加密失败原因' AFTER `encrypt_status`;

-- 处理文件增加加密批次信息（保持 keyVersion 一致）
ALTER TABLE `video_post_processing_file`
    ADD COLUMN `encrypt_method` int NOT NULL DEFAULT 1 COMMENT '加密方式@E=0:UNKNOW:未知|1:HLS_AES_128:AES-128|2:SAMPLE_AES:SAMPLE-AES|3:DRM:DRM占位;@T=EncryptMethod' AFTER `encrypt_status`,
    ADD COLUMN `encrypt_key_version` int DEFAULT NULL COMMENT '加密密钥版本' AFTER `encrypt_method`;

-- 可选索引（如需要按状态批量检索）
-- CREATE INDEX idx_vppv_encrypt_status ON video_post_processing_variant (parent_id, encrypt_status, update_time);
