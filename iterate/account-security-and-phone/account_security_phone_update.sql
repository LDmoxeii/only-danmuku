-- 账号安全与手机号支持 - 表结构更新脚本
-- 注意：请在已有库结构基础上执行本脚本，而不是替换原始建表 SQL。

-- 1. user 表新增手机号字段及唯一索引
ALTER TABLE `user`
    ADD COLUMN `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号' AFTER `email`,
    ADD UNIQUE INDEX `uk_v_phone` (`phone`, `deleted`);

-- 2. customer_profile 表新增手机号字段及唯一索引
ALTER TABLE `customer_profile`
    ADD COLUMN `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号' AFTER `email`,
    ADD UNIQUE INDEX `uk_v_phone` (`phone`, `deleted`);

-- 3. 历史数据初始化（可选）
-- 现有数据在新增字段时会自动填充默认值：
--   - phone = NULL
-- 如需显式清理，可按需执行类似语句：
-- UPDATE `user` SET `phone` = NULL WHERE `phone` IS NULL;
-- UPDATE `customer_profile` SET `phone` = NULL WHERE `phone` IS NULL;
