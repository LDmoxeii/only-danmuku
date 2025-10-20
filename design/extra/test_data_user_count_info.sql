-- ====================================================================
-- 测试数据: getUserCountInfo 接口
-- 用于测试获取用户统计信息接口 (粉丝数、硬币数、关注数)
-- ====================================================================

-- ----------------------------
-- 1. 用户账号表 (user)
-- ----------------------------
-- 确保测试用户存在
INSERT INTO `user` (id, type, nick_name, email, password, join_time, last_login_time, last_login_ip, status, related_id,
                    create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES
    -- 测试用户1: 张三 (ID: 2001)
    (2001, 0, '张三的编程日记', 'zhangsan@example.com', 'password123', 1729180800, 1729440000, '192.168.1.100', 1, 0,
     2001, 'user_zhang', 1729180800, 2001, 'user_zhang', 1729440000, 0),
    -- 测试用户2: 李四 (ID: 2002)
    (2002, 0, '李四学Java', 'lisi@example.com', 'password456', 1729184400, 1729450000, '192.168.1.101', 1, 0,
     2002, 'user_li', 1729184400, 2002, 'user_li', 1729450000, 0),
    -- 测试用户3: 王五 (ID: 2003)
    (2003, 0, '王五的数据库笔记', 'wangwu@example.com', 'password789', 1729188000, 1729420000, '192.168.1.102', 1, 0,
     2003, 'user_wang', 1729188000, 2003, 'user_wang', 1729430000, 0),
    -- 测试用户4: 赵六 (ID: 2004)
    (2004, 0, '赵六看技术', 'zhaoliu@example.com', 'password000', 1729191600, 1729460000, '192.168.1.103', 1, 0,
     2004, 'user_zhao', 1729191600, 2004, 'user_zhao', 1729460000, 0),
    -- 测试用户5: 小明 (ID: 2005)
    (2005, 0, '小明的前端之路', 'xiaoming@example.com', 'password111', 1729195200, 1729470000, '192.168.1.104', 1, 0,
     2005, 'user_xiaoming', 1729195200, 2005, 'user_xiaoming', 1729470000, 0)
ON DUPLICATE KEY UPDATE nick_name = VALUES(nick_name);

-- ----------------------------
-- 2. 用户信息表 (customer_profile) - 包含硬币数
-- ----------------------------
INSERT INTO `customer_profile` (id, user_id, nick_name, avatar, email, sex, birthday, school, person_introduction,
                                 notice_info, total_coin_count, current_coin_count, theme,
                                 create_user_id, create_by, create_time, update_user_id, update_by, update_time, deleted)
VALUES
    -- 张三: 总硬币500, 当前硬币320
    (2001, 2001, '张三的编程日记', 'avatar_zhang.jpg', 'zhangsan@example.com', 2, '1995-06-15', '清华大学',
     '热爱编程，专注于Kotlin和Spring Boot开发', '欢迎来到我的空间！', 500, 320, 2,
     2001, 'user_zhang', 1729180800, 2001, 'user_zhang', 1729353600, 0),
    -- 李四: 总硬币800, 当前硬币650
    (2002, 2002, '李四学Java', 'avatar_li.jpg', 'lisi@example.com', 2, '1998-03-22', '北京大学',
     'Java后端工程师，喜欢分享技术', '一起学习，共同进步！', 800, 650, 1,
     2002, 'user_li', 1729184400, 2002, 'user_li', 1729354200, 0),
    -- 王五: 总硬币1200, 当前硬币980
    (2003, 2003, '王五的数据库笔记', 'avatar_wang.jpg', 'wangwu@example.com', 1, '1996-11-08', '复旦大学',
     'DBA，专注数据库性能优化', '分享数据库优化经验', 1200, 980, 2,
     2003, 'user_wang', 1729188000, 2003, 'user_wang', 1729356600, 0),
    -- 赵六: 总硬币600, 当前硬币420
    (2004, 2004, '赵六看技术', 'avatar_zhao.jpg', 'zhaoliu@example.com', 2, '1997-09-30', '浙江大学',
     '全栈开发者，喜欢学习新技术', '技术改变世界！', 600, 420, 3,
     2004, 'user_zhao', 1729191600, 2004, 'user_zhao', 1729357800, 0),
    -- 小明: 总硬币300, 当前硬币180
    (2005, 2005, '小明的前端之路', 'avatar_xiaoming.jpg', 'xiaoming@example.com', 2, '1999-05-20', '上海交通大学',
     '前端开发，React和Vue都会', '分享前端技术心得', 300, 180, 1,
     2005, 'user_xiaoming', 1729195200, 2005, 'user_xiaoming', 1729358400, 0)
ON DUPLICATE KEY UPDATE current_coin_count = VALUES(current_coin_count), total_coin_count = VALUES(total_coin_count);

-- ----------------------------
-- 3. 用户关注表 (customer_focus) - 用于计算粉丝数和关注数
-- ----------------------------
-- 关系说明:
-- customer_id: 谁发起的关注
-- focus_customer_id: 关注了谁
-- 例如: (2002, 2001) 表示李四(2002)关注了张三(2001)

DELETE FROM `customer_focus` WHERE id >= 6001 AND id <= 6020;

INSERT INTO `customer_focus` (id, customer_id, focus_customer_id, create_user_id, create_by, create_time,
                               update_user_id, update_by, update_time, deleted)
VALUES
    -- ========== 张三(2001)的关注关系 ==========
    -- 张三关注了: 李四、王五 (关注数 = 2)
    (6001, '2001', '2002', 2001, 'user_zhang', 1729267200, 2001, 'user_zhang', 1729267200, 0),
    (6002, '2001', '2003', 2001, 'user_zhang', 1729353600, 2001, 'user_zhang', 1729353600, 0),

    -- 关注张三的人: 李四、王五、赵六 (粉丝数 = 3)
    (6003, '2002', '2001', 2002, 'user_li', 1729267200, 2002, 'user_li', 1729267200, 0),
    (6004, '2003', '2001', 2003, 'user_wang', 1729267200, 2003, 'user_wang', 1729267200, 0),
    (6005, '2004', '2001', 2004, 'user_zhao', 1729270800, 2004, 'user_zhao', 1729270800, 0),

    -- ========== 李四(2002)的关注关系 ==========
    -- 李四关注了: 张三、王五、赵六 (关注数 = 3)
    -- (6003 已在上面定义: 李四关注张三)
    (6006, '2002', '2003', 2002, 'user_li', 1729267200, 2002, 'user_li', 1729267200, 0),
    (6007, '2002', '2004', 2002, 'user_li', 1729274400, 2002, 'user_li', 1729274400, 0),

    -- 关注李四的人: 张三、小明 (粉丝数 = 2)
    -- (6001 已在上面定义: 张三关注李四)
    (6008, '2005', '2002', 2005, 'user_xiaoming', 1729280000, 2005, 'user_xiaoming', 1729280000, 0),

    -- ========== 王五(2003)的关注关系 ==========
    -- 王五关注了: 张三 (关注数 = 1)
    -- (6004 已在上面定义: 王五关注张三)

    -- 关注王五的人: 张三、李四、小明 (粉丝数 = 3)
    -- (6002 已在上面定义: 张三关注王五)
    -- (6006 已在上面定义: 李四关注王五)
    (6009, '2005', '2003', 2005, 'user_xiaoming', 1729285600, 2005, 'user_xiaoming', 1729285600, 0),

    -- ========== 赵六(2004)的关注关系 ==========
    -- 赵六关注了: 张三、小明 (关注数 = 2)
    -- (6005 已在上面定义: 赵六关注张三)
    (6010, '2004', '2005', 2004, 'user_zhao', 1729291200, 2004, 'user_zhao', 1729291200, 0),

    -- 关注赵六的人: 李四 (粉丝数 = 1)
    -- (6007 已在上面定义: 李四关注赵六)

    -- ========== 小明(2005)的关注关系 ==========
    -- 小明关注了: 李四、王五 (关注数 = 2)
    -- (6008 已在上面定义: 小明关注李四)
    -- (6009 已在上面定义: 小明关注王五)

    -- 关注小明的人: 赵六 (粉丝数 = 1)
    -- (6010 已在上面定义: 赵六关注小明)
    (6011, '2005', '2004', 2005, 'user_xiaoming', 1729296800, 2005, 'user_xiaoming', 1729296800, 0);

-- ----------------------------
-- 测试数据统计汇总
-- ----------------------------
-- 用户ID | 昵称          | 粉丝数 | 关注数 | 当前硬币数
-- 2001   | 张三的编程日记  | 3      | 2      | 320
-- 2002   | 李四学Java     | 2      | 3      | 650
-- 2003   | 王五的数据库笔记| 3      | 1      | 980
-- 2004   | 赵六看技术      | 1      | 2      | 420
-- 2005   | 小明的前端之路  | 1      | 2      | 180

-- ----------------------------
-- 验证SQL查询 (可用于测试)
-- ----------------------------
-- 1. 验证张三(2001)的统计数据
-- 粉丝数 (有多少人关注了我):
SELECT COUNT(*) AS fans_count FROM customer_focus WHERE focus_customer_id = '2001' AND deleted = 0;
-- 预期结果: 3

-- 关注数 (我关注了多少人):
SELECT COUNT(*) AS focus_count FROM customer_focus WHERE customer_id = '2001' AND deleted = 0;
-- 预期结果: 2

-- 当前硬币数:
SELECT current_coin_count FROM customer_profile WHERE id = 2001 AND deleted = 0;
-- 预期结果: 320

-- 2. 验证李四(2002)的统计数据
SELECT COUNT(*) AS fans_count FROM customer_focus WHERE focus_customer_id = '2002' AND deleted = 0;  -- 预期: 2
SELECT COUNT(*) AS focus_count FROM customer_focus WHERE customer_id = '2002' AND deleted = 0;      -- 预期: 3
SELECT current_coin_count FROM customer_profile WHERE id = 2002 AND deleted = 0;                     -- 预期: 650

-- 3. 完整统计查询 (所有用户)
SELECT
    cp.id AS user_id,
    cp.nick_name,
    (SELECT COUNT(*) FROM customer_focus WHERE focus_customer_id = cp.id AND deleted = 0) AS fans_count,
    (SELECT COUNT(*) FROM customer_focus WHERE customer_id = cp.id AND deleted = 0) AS focus_count,
    cp.current_coin_count
FROM customer_profile cp
WHERE cp.deleted = 0
ORDER BY cp.id;
