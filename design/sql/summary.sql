-- MySQL dump 10.13  Distrib 8.4.5, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: only_danmuku
-- ------------------------------------------------------
-- Server version	8.4.5

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL COMMENT 'ID',
  `parent_id` bigint NOT NULL COMMENT '父级ID',
  `node_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '路径',
  `sort` tinyint NOT NULL COMMENT '排序号',
  `code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编码',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
  `background` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '背景图',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_v_code` (`code`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='分类信息;@Spe;@Fac;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (231168817651990528,0,'/231168817651990528/',1,'bmig','编程','cover/202510/c858cef77cad4d8aa0d2f36923e60b.png','',NULL,NULL,NULL,2001,'admin',1763794073637,0),(231168931523149824,0,'/231168931523149824/',2,'rfgsving','人工智能','cover/202510/e4530034d0ab4592ae3153e1ea8c1a.png',NULL,NULL,NULL,NULL,2001,'admin',1763794073639,0),(231168999944830976,0,'/231168999944830976/',3,'yyds','运动','cover/202510/5c104949cd334ecfb4bb131491e55c.png',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(231169038893137920,0,'/231169038893137920/',4,'mwui','美食','cover/202510/784f6251c0824c64abae46b2037fa4.png',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(231169068978880512,0,'/231169068978880512/',5,'qiie','汽车','cover/202510/ed2433e48354420bbc92ccdafa0448.png',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(231169246955782144,0,'/231169246955782144/',6,'ybyt','音乐','cover/202510/ca3051f6e31141ad88b551e7434e89.png',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(231169290559766528,0,'/231169290559766528/',7,'dshx','动画','cover/202510/b10897a728024d489b2ed138664011.png',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(231169343441551360,0,'/231169343441551360/',8,'dmyk','电影','cover/202510/7e7e1cac2ce04d49baab9008a07baa.png',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(231169395257982976,0,'/231169395257982976/',9,'viui','知识','cover/202510/2964320eb93e46099f7d4d6137ddcc.png',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(231169455911813120,0,'/231169455911813120/',10,'yzxi','游戏','cover/202510/bc707d7edd7d453cb3bba7ea70743f.png',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(231169527605051392,0,'/231169527605051392/',11,'gcxn','搞笑','cover/202510/232091fe49b9439cac2a8648174363.png',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(231169587294191616,0,'/231169587294191616/',12,'drju','短剧','cover/202510/576081f985724989aedf95789b67bd.png',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(231169639785906176,0,'/231169639785906176/',13,'huwd','户外','cover/202510/63d9afeb347449518b66e97272c9c1.png',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(231169735168573440,0,'/231169735168573440/',14,'zixy','资讯','cover/202510/15a13f0cce55451ba67edf4ffb451d.png',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(231169791888146432,0,'/231169791888146432/',15,'ugho','生活','cover/202510/5d0051d3df2a48aa829db5e397a185.png',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(231169851648589824,0,'/231169851648589824/',16,'jilupm','记录片','cover/202510/881f58f9f0d34a3c9810428efe36f0.png',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(231169884234137600,0,'/231169884234137600/',17,'keji','科技','cover/202510/81aa2dbf29b546ba9f5fa359e10389.png',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(231169930375675904,0,'/231169930375675904/',18,'wudc','舞蹈','cover/202510/aefa3b506bc040cd9f4c0fb0602912.png',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(231169978782138368,0,'/231169978782138368/',19,'vlog','VLOG','cover/202510/6d2fe77adcda46a3a5f49ed8fde9ff.png',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(239399376522203136,231168817651990528,'/231168817651990528/239399376522203136/',1,'123','123',NULL,NULL,2001,'admin',1763794059382,2001,'admin',1763794060272,239399376522203136),(239399462878728192,231168931523149824,'/231168931523149824/239399462878728192/',1,'123','123',NULL,NULL,2001,'admin',1763794079780,2001,'admin',1763794093253,239399462878728192),(239399481400774656,231168931523149824,'/231168931523149824/239399481400774656/',2,'321','321',NULL,NULL,2001,'admin',1763794084194,2001,'admin',1763794093255,239399481400774656),(239399551009443840,231168817651990528,'/231168817651990528/239399551009443840/',2,'234','234',NULL,NULL,2001,'admin',1763794100790,2001,'admin',1763794108374,239399551009443840),(239399577584553984,231168817651990528,'/231168817651990528/239399577584553984/',1,'432','432',NULL,NULL,2001,'admin',1763794107127,2001,'admin',1763794108376,239399577584553984);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_action`
--

DROP TABLE IF EXISTS `customer_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_action` (
  `id` bigint NOT NULL COMMENT 'ID',
  `customer_id` bigint NOT NULL COMMENT '用户ID',
  `video_id` bigint NOT NULL COMMENT '视频ID',
  `video_owner_id` bigint NOT NULL COMMENT '视频用户ID',
  `comment_id` bigint DEFAULT NULL COMMENT '评论ID',
  `action_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '行为类型 @E=0:UNKNOW:未知行为|1:LIKE_COMMENT:评论喜欢点赞|2:HATE_COMMENT:讨厌评论|3:LIKE_VIDEO:视频点赞|4:FAVORITE_VIDEO:视频收藏|5:COIN_VIDEO:视频投币;@T=ActionType',
  `action_count` int NOT NULL COMMENT '数量',
  `action_time` bigint NOT NULL COMMENT '操作时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_v_type` (`video_id`,`comment_id`,`action_type`,`customer_id`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户行为 点赞、评论;@Spe;@Fac;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_focus`
--

DROP TABLE IF EXISTS `customer_focus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_focus` (
  `id` bigint NOT NULL COMMENT 'ID',
  `customer_id` bigint NOT NULL COMMENT '用户ID',
  `focus_customer_id` bigint NOT NULL COMMENT '用户ID',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户关注;@Spe;@Fac;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_message`
--

DROP TABLE IF EXISTS `customer_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_message` (
  `id` bigint NOT NULL COMMENT 'ID',
  `customer_id` bigint NOT NULL COMMENT '用户ID',
  `video_id` bigint DEFAULT NULL COMMENT '主体ID',
  `message_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '消息类型 @E=0:UNKNOW:未知消息|1:SYSTEM_MESSAGE:系统消息|2:LIKE_MESSAGE:收到的赞|3:COLLECTION_MESSAGE:收到收藏|4:COMMENT_MENTION:评论和@|5:PRIVATE_MESSAGE:私信消息;@T=MessageType',
  `send_subject_id` bigint DEFAULT NULL COMMENT '发送主体ID',
  `read_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '读取状态 @E=0:UNKNOW:未知状态|1:UNREAD:未读|2:READ:已读;@T=ReadType',
  `extend_json` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '扩展信息@T=UserMessageExtend?',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户消息表;@Spe;@Fac;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_profile`
--

DROP TABLE IF EXISTS `customer_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_profile` (
  `id` bigint NOT NULL COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `nick_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像',
  `email` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号',
  `sex` tinyint(1) NOT NULL DEFAULT '0' COMMENT '性别 @E=0:UNKNOWN:未知|1:FEMALE:女|2:MALE:男|;@T=SexType',
  `birthday` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '出生日期',
  `school` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '学校',
  `person_introduction` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '个人简介',
  `notice_info` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '空间公告',
  `total_coin_count` int NOT NULL COMMENT '硬币总数量',
  `current_coin_count` int NOT NULL COMMENT '当前硬币数',
  `theme` tinyint(1) NOT NULL DEFAULT '0' COMMENT '主题 @E=0:UNKNOW:未知主题|1:LIGHT:浅色主题|2:DARK:深色主题|3:SYSTEM:跟随系统;@T=ThemeType',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_v_email` (`email`,`deleted`),
  UNIQUE KEY `uk_v_nick_name` (`nick_name`,`deleted`),
  UNIQUE KEY `uk_v_phone` (`phone`,`deleted`),
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户信息;@Spe;@Fac;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_profile`
--

LOCK TABLES `customer_profile` WRITE;
/*!40000 ALTER TABLE `customer_profile` DISABLE KEYS */;
INSERT INTO `customer_profile` VALUES (231189940775145472,231189940578013184,'2649075705@qq.com',NULL,'2649075705@qq.com',NULL,0,NULL,NULL,NULL,NULL,15,-30,0,NULL,NULL,NULL,231189940578013184,'2649075705@qq.com',1765628340,0),(231190007976284160,231190007951118336,'1649075705@qq.com',NULL,'1649075705@qq.com',NULL,2,'2025-11-05','233','233','233',25,1007,0,NULL,NULL,NULL,231189940578013184,'2649075705@qq.com',1763873141586,0);
/*!40000 ALTER TABLE `customer_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_video_series`
--

DROP TABLE IF EXISTS `customer_video_series`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_video_series` (
  `id` bigint NOT NULL COMMENT 'ID',
  `customer_id` bigint NOT NULL COMMENT '用户ID',
  `series_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '列表名称',
  `series_description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  `sort` tinyint NOT NULL COMMENT '排序',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户视频序列归档;@Spe;@Fac;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_video_series_video`
--

DROP TABLE IF EXISTS `customer_video_series_video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_video_series_video` (
  `id` bigint NOT NULL COMMENT 'ID',
  `customer_id` bigint NOT NULL COMMENT '用户ID',
  `series_id` bigint NOT NULL COMMENT '列表ID @Ref=customer_video_series',
  `video_id` bigint NOT NULL COMMENT '视频ID',
  `sort` tinyint NOT NULL COMMENT '排序',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户视频序列视频关联;@P=customer_video_series;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `statistics`
--

DROP TABLE IF EXISTS `statistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `statistics` (
  `id` bigint NOT NULL COMMENT 'ID',
  `customer_id` bigint NOT NULL COMMENT '用户ID',
  `data_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '数据统计类型 @E=0:UNKNOW:未知类型|1:PLAY:播放量|2:FANS:粉丝|3:LIKE:点赞|4:COLLECTION:收藏|5:COIN:投币|6:COMMENT:评论|7:DANMUKU:弹幕;@T=StatisticsDataType',
  `statistics_count` int DEFAULT NULL COMMENT '统计数量',
  `statistics_date` bigint NOT NULL COMMENT '统计日期（秒级时间戳）',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间（秒级时间戳）',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间（秒级时间戳）',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='统计信息;@Spe;@Fac;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL COMMENT 'ID',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '帐号类型 @E=0:UNKNOW:未知类型|1:SYS_USER:系统管理员;@T=UserType',
  `nick_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `email` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `join_time` bigint NOT NULL COMMENT '加入时间',
  `last_login_time` bigint DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后登录IP',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0:禁用 1:正常',
  `related_id` bigint DEFAULT NULL COMMENT '关联ID; 用户、管理员 = 0',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_v_email` (`email`,`deleted`),
  UNIQUE KEY `uk_v_phone` (`phone`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='帐号;@Spe;@Fac;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (231189940578013184,1,'2649075705@qq.com','2649075705@qq.com',NULL,'admin123',1761836777,1765627622,'0:0:0:0:0:0:0:1',1,231189940775145472,NULL,NULL,NULL,231189940578013184,'2649075705@qq.com',1765627622,0),(231190007951118336,1,'1649075705@qq.com','1649075705@qq.com',NULL,'admin123',1761836793,1763873781,'0:0:0:0:0:0:0:1',1,231190007976284160,NULL,NULL,NULL,231190007951118336,NULL,1729440000,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_abnormal_operation_log`
--

DROP TABLE IF EXISTS `user_abnormal_operation_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_abnormal_operation_log` (
  `id` bigint NOT NULL COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `user_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户类型 @E=0:UNKNOW:未知类型|1:SYS_USER:系统管理员;@T=UserType',
  `op_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '异常类型 @E=0:UNKNOW:未知异常|1:PASSWORD_FAIL_TOO_MANY_TIMES:密码失败次数过多;@T=AbnormalOpType',
  `ip` varchar(45) NOT NULL COMMENT '触发IP',
  `occur_time` bigint NOT NULL COMMENT '触发时间',
  `description` varchar(200) DEFAULT NULL COMMENT '异常描述',
  `extra` text COMMENT '扩展JSON，如关联登录日志ID列表等',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_abnormal_op_user_type` (`user_id`,`op_type`) USING BTREE,
  KEY `idx_user_abnormal_op_time` (`occur_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户异常操作日志;@Spe;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_login_log`
--

DROP TABLE IF EXISTS `user_login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_login_log` (
  `id` bigint NOT NULL COMMENT 'ID',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID，可为空（未知用户）',
  `user_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户类型 @E=0:UNKNOW:未知类型|1:SYS_USER:系统管理员;@T=UserType',
  `login_name` varchar(150) NOT NULL COMMENT '登录名（邮箱或手机号）',
  `login_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '登录类型 @E=0:UNKNOW:未知登录类型|1:PASSWORD:密码登录|2:SMS_CODE:短信验证码登录|3:LOGOUT:退出登录;@T=LoginType',
  `result` tinyint(1) NOT NULL DEFAULT '0' COMMENT '登录结果 @E=0:UNKNOW:未知结果|1:SUCCESS:成功|2:FAILURE:失败;@T=LoginResult',
  `ip` varchar(45) NOT NULL COMMENT '登录IP',
  `user_agent` varchar(255) DEFAULT NULL COMMENT 'User-Agent',
  `reason` varchar(200) DEFAULT NULL COMMENT '失败原因描述',
  `occur_time` bigint NOT NULL COMMENT '发生时间（秒级或毫秒级时间戳）',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_login_log_user_id_time` (`user_id`,`occur_time`) USING BTREE,
  KEY `idx_user_login_log_login_name_time` (`login_name`,`occur_time`) USING BTREE,
  KEY `idx_user_login_log_result_time` (`result`,`occur_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户登录日志;@Spe;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `video`
--

DROP TABLE IF EXISTS `video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video` (
  `id` bigint NOT NULL COMMENT 'ID',
  `video_post_id` bigint NOT NULL COMMENT '视频草稿ID',
  `customer_id` bigint NOT NULL COMMENT '用户ID',
  `video_cover` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '视频封面',
  `video_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '视频名称',
  `p_category_id` bigint NOT NULL COMMENT '父级分类ID',
  `category_id` bigint DEFAULT NULL COMMENT '分类ID',
  `post_type` tinyint NOT NULL DEFAULT '0' COMMENT '投稿类型 @E=0:UNKNOW:未知类型|1:ORIGINAL:自制作|2:REPOST:转载;@T=PostType',
  `origin_info` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '原资源说明',
  `tags` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标签',
  `introduction` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '简介',
  `interaction` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '互动设置',
  `duration` int NOT NULL DEFAULT '0' COMMENT '持续时间（秒）',
  `play_count` int NOT NULL DEFAULT '0' COMMENT '播放数量',
  `like_count` int NOT NULL DEFAULT '0' COMMENT '点赞数量',
  `danmuku_count` int NOT NULL DEFAULT '0' COMMENT '弹幕数量',
  `comment_count` int NOT NULL DEFAULT '0' COMMENT '评论数量',
  `coin_count` int NOT NULL DEFAULT '0' COMMENT '投币数量',
  `collect_count` int NOT NULL DEFAULT '0' COMMENT '收藏数量',
  `recommend_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '推荐状态 @E=0:UNKNOW:未知状态|1:NOT_RECOMMEND:未推荐|2:RECOMMEND:已推荐;@T=RecommendType',
  `last_play_time` bigint DEFAULT NULL COMMENT '最后播放时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='视频信息;@Spe;@Fac;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `video_audit_trace`
--

DROP TABLE IF EXISTS `video_audit_trace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video_audit_trace` (
  `id` bigint NOT NULL COMMENT 'ID',
  `video_post_id` bigint NOT NULL COMMENT '视频稿件ID',
  `audit_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '审核状态 @E=0:UNKNOW:未知|1:PASSED:审核通过|2:FAILED:审核不通过;@T=AuditStatus',
  `reviewer_id` bigint DEFAULT NULL COMMENT '审核人ID，可为空',
  `reviewer_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '审核人类型 @E=0:UNKNOW:未知类型|1:SYS_USER:系统管理员;@T=UserType',
  `reason` varchar(255) DEFAULT NULL COMMENT '审核备注/失败原因',
  `occur_time` bigint NOT NULL COMMENT '审核发生时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_video_audit_trace_video_time` (`video_post_id`,`occur_time`) USING BTREE,
  KEY `idx_video_audit_trace_status_time` (`audit_status`,`occur_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='视频审核追溯记录;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `video_comment`
--

DROP TABLE IF EXISTS `video_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video_comment` (
  `id` bigint NOT NULL COMMENT '评论ID',
  `parent_id` bigint NOT NULL COMMENT '父级评论ID',
  `video_id` bigint NOT NULL COMMENT '视频ID',
  `video_owner_id` bigint NOT NULL COMMENT '视频用户ID',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '回复内容',
  `img_path` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图片',
  `customer_id` bigint NOT NULL COMMENT '用户ID',
  `reply_customer_id` bigint DEFAULT NULL COMMENT '回复人ID',
  `top_type` tinyint DEFAULT '0' COMMENT '0:未置顶  1:置顶',
  `post_time` bigint NOT NULL COMMENT '发布时间',
  `like_count` int DEFAULT '0' COMMENT '喜欢数量',
  `hate_count` int DEFAULT '0' COMMENT '讨厌数量',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='评论;@Spe;@Fac;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `video_danmuku`
--

DROP TABLE IF EXISTS `video_danmuku`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video_danmuku` (
  `id` bigint NOT NULL COMMENT 'ID',
  `video_id` bigint NOT NULL COMMENT '视频ID',
  `file_id` bigint NOT NULL COMMENT '唯一ID',
  `customer_id` bigint NOT NULL COMMENT '用户ID',
  `post_time` bigint DEFAULT NULL COMMENT '发布时间',
  `text` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '内容',
  `mode` tinyint(1) DEFAULT NULL COMMENT '展示位置',
  `color` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '颜色',
  `time` int DEFAULT NULL COMMENT '展示时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='视频弹幕;@Spe;@Fac;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `video_file`
--

DROP TABLE IF EXISTS `video_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video_file` (
  `id` bigint NOT NULL COMMENT 'ID',
  `customer_id` bigint NOT NULL COMMENT '用户ID',
  `video_id` bigint NOT NULL COMMENT '视频ID',
  `video_file_post_id` bigint NOT NULL COMMENT '视频文件草稿ID',
  `file_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件名',
  `file_index` int NOT NULL COMMENT '文件索引',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小',
  `file_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件路径',
  `duration` int DEFAULT NULL COMMENT '持续时间（秒）',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='视频文件信息;@P=video;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `video_file_post`
--

DROP TABLE IF EXISTS `video_file_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video_file_post` (
  `id` bigint NOT NULL COMMENT 'ID',
  `video_post_id` bigint NOT NULL COMMENT '视频稿件ID',
  `upload_id` bigint NOT NULL COMMENT '上传ID',
  `customer_id` bigint NOT NULL COMMENT '用户ID',
  `file_index` int NOT NULL COMMENT '文件索引',
  `file_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件名',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小',
  `transcode_output_prefix` varchar(512) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '转码输出对象前缀',
  `encrypt_output_prefix` varchar(512) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '加密输出对象前缀',
  `transfer_result` tinyint NOT NULL DEFAULT '0' COMMENT '转码结果 @E=0:UNKNOW:未知结果|1:TRANSCODING:转码中|2:SUCCESS:转码成功|3:FAILED:转码失败;@T=TransferResult',
  `encrypt_status` int NOT NULL DEFAULT '1' COMMENT '加密状态@E=0:UNKNOW:未知|1:UNENCRYPTED:未加密|2:ENCRYPTING:加密中|3:ENCRYPTED:已加密|4:FAILED:失败;@T=EncryptStatus',
  `encrypt_method` int NOT NULL DEFAULT '1' COMMENT '加密方式@E=0:UNKNOW:未知|1:HLS_AES_128:AES-128|2:SAMPLE_AES:SAMPLE-AES|3:DRM:DRM占位;@T=EncryptMethod',
  `encrypt_key_version` int DEFAULT NULL COMMENT '加密密钥版本',
  `duration` int DEFAULT NULL COMMENT '持续时间（秒）',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_v_upload_id` (`upload_id`,`customer_id`,`deleted`) USING BTREE,
  UNIQUE KEY `uk_i` (`video_post_id`,`file_index`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='视频文件信息;@P=video_post;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `video_file_post_variant`
--

DROP TABLE IF EXISTS `video_file_post_variant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video_file_post_variant` (
  `id` bigint NOT NULL COMMENT 'ID',
  `file_post_id` bigint NOT NULL COMMENT '稿件文件ID@Ref=video_file_post',
  `quality` varchar(32) NOT NULL COMMENT '清晰度档位，如 1080p/720p',
  `width` int NOT NULL COMMENT '输出宽度(px)',
  `height` int NOT NULL COMMENT '输出高度(px)',
  `video_bitrate_kbps` int NOT NULL COMMENT '视频码率(kbps)',
  `audio_bitrate_kbps` int NOT NULL COMMENT '音频码率(kbps)',
  `bandwidth_bps` int NOT NULL COMMENT 'Master 中的 BANDWIDTH（bps）',
  `playlist_path` varchar(512) NOT NULL COMMENT '子清晰度 m3u8 路径，如 720p/index.m3u8',
  `segment_prefix` varchar(512) DEFAULT NULL COMMENT '切片目录前缀，如 720p/',
  `segment_duration` int DEFAULT NULL COMMENT '切片目标时长（秒）',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_i` (`file_post_id`,`quality`,`deleted`),
  KEY `idx_vfpv_height` (`height`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='视频稿件文件分辨率档位;@P=video_file_post;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `video_file_upload_session`
--

DROP TABLE IF EXISTS `video_file_upload_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video_file_upload_session` (
  `id` bigint NOT NULL COMMENT 'ID',
  `customer_id` bigint NOT NULL COMMENT '用户ID',
  `file_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件名',
  `chunks` int NOT NULL COMMENT '分片总数',
  `chunk_index` int NOT NULL DEFAULT '0' COMMENT '当前已上传的最大分片索引',
  `file_size` bigint DEFAULT '0' COMMENT '累计已上传大小（字节）',
  `temp_dir` varchar(512) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '临时目录（绝对或相对路径）',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态 @E=0:UNKNOW:未知类型|1:CREATED:已创建|2:UPLOADING:上传中|3:DONE:完成|4:ABORTED:已放弃|5:EXPIRED:已过期;@T=UploadStatus',
  `duration` int DEFAULT NULL COMMENT '视频时长（秒，可选）',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `expires_at` bigint DEFAULT NULL COMMENT '过期时间（秒时间戳）',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_status` (`customer_id`,`status`) USING BTREE,
  KEY `idx_expires_at` (`expires_at`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='视频分片上传会话; 用于跟踪预上传与分片进度';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `video_file_variant`
--

DROP TABLE IF EXISTS `video_file_variant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video_file_variant` (
  `id` bigint NOT NULL COMMENT 'ID',
  `file_id` bigint NOT NULL COMMENT '视频文件ID@Ref=video_file',
  `quality` varchar(32) NOT NULL COMMENT '清晰度档位，如 1080p/720p',
  `width` int NOT NULL COMMENT '输出宽度(px)',
  `height` int NOT NULL COMMENT '输出高度(px)',
  `video_bitrate_kbps` int NOT NULL COMMENT '视频码率(kbps)',
  `audio_bitrate_kbps` int NOT NULL COMMENT '音频码率(kbps)',
  `bandwidth_bps` int NOT NULL COMMENT 'Master 中的 BANDWIDTH（bps）',
  `playlist_path` varchar(512) NOT NULL COMMENT '子清晰度 m3u8 路径，如 720p/index.m3u8',
  `segment_prefix` varchar(512) DEFAULT NULL COMMENT '切片目录前缀，如 720p/',
  `segment_duration` int DEFAULT NULL COMMENT '切片目标时长（秒）',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_i` (`file_id`,`quality`,`deleted`) USING BTREE,
  KEY `idx_vfv_height` (`height`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='视频文件分辨率档位;@P=video_file;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `video_hls_encrypt_key`
--

DROP TABLE IF EXISTS `video_hls_encrypt_key`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video_hls_encrypt_key` (
  `id` bigint NOT NULL COMMENT 'ID',
  `video_post_id` bigint NOT NULL COMMENT '视频稿件ID',
  `video_id` bigint DEFAULT NULL COMMENT '视频ID',
  `file_index` int NOT NULL COMMENT '文件索引',
  `quality` varchar(32) NOT NULL COMMENT '绑定清晰度',
  `key_id` varchar(64) NOT NULL COMMENT 'Key ID（m3u8 暴露）',
  `key_ciphertext` varchar(512) NOT NULL COMMENT '密钥密文（KMS 加密后 Base64）',
  `iv_hex` varchar(64) DEFAULT NULL COMMENT 'IV hex（16字节，可空）',
  `key_version` int NOT NULL DEFAULT '1' COMMENT '密钥版本号（轮换递增）',
  `method` int NOT NULL DEFAULT '1' COMMENT '加密方式@E=0:UNKNOW:未知|1:HLS_AES_128:AES-128|2:SAMPLE_AES:SAMPLE-AES|3:DRM:DRM占位;@T=EncryptMethod',
  `key_uri_template` varchar(512) NOT NULL COMMENT 'm3u8 中的 URI 模板，含 token 占位',
  `expire_time` bigint DEFAULT NULL COMMENT '过期时间（ms）',
  `status` int NOT NULL DEFAULT '1' COMMENT '状态@E=0:UNKNOW:未知|1:ACTIVE:可用|2:REVOKED:吊销|3:EXPIRED:过期;@T=EncryptKeyStatus',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注/轮换原因',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_i` (`video_post_id`,`file_index`,`key_id`,`key_version`,`quality`,`deleted`),
  KEY `idx_video_hls_encrypt_key_expire` (`expire_time`) USING BTREE,
  KEY `idx_video_hls_encrypt_key_post` (`video_post_id`,`file_index`,`status`),
  KEY `idx_video_hls_encrypt_key_video` (`video_id`,`file_index`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='视频 HLS 加密密钥';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `video_hls_key_token`
--

DROP TABLE IF EXISTS `video_hls_key_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video_hls_key_token` (
  `id` bigint NOT NULL COMMENT 'ID',
  `video_post_id` bigint NOT NULL COMMENT '视频稿件ID',
  `video_id` bigint DEFAULT NULL COMMENT '视频ID',
  `file_index` int NOT NULL COMMENT '文件索引',
  `key_version` int NOT NULL DEFAULT '1' COMMENT '密钥版本',
  `allowed_qualities` varchar(512) DEFAULT NULL COMMENT '授权清晰度列表 JSON，空表示全量',
  `token_hash` varchar(128) NOT NULL COMMENT 'token 哈希（sha256）',
  `audience` varchar(128) DEFAULT NULL COMMENT '受众标识（用户/终端）',
  `expire_time` bigint NOT NULL COMMENT '过期时间（ms）',
  `max_use` int NOT NULL DEFAULT '5' COMMENT '最大可用次数',
  `used_count` int NOT NULL DEFAULT '0' COMMENT '已使用次数',
  `status` int NOT NULL DEFAULT '1' COMMENT '状态@E=0:UNKNOW:未知|1:VALID:有效|2:EXHAUSTED:已用尽|3:EXPIRED:过期|4:REVOKED:吊销;@T=EncryptTokenStatus',
  `issue_ip` varchar(64) DEFAULT NULL COMMENT '签发 IP',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_i` (`token_hash`,`deleted`) USING BTREE,
  KEY `idx_video_hls_key_token_expire` (`expire_time`) USING BTREE,
  KEY `idx_video_hls_key_token_post` (`video_post_id`,`file_index`,`status`),
  KEY `idx_video_hls_key_token_video` (`video_id`,`file_index`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='HLS 加密播放 token';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `video_play_history`
--

DROP TABLE IF EXISTS `video_play_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video_play_history` (
  `id` bigint NOT NULL COMMENT 'ID',
  `customer_id` bigint NOT NULL COMMENT '用户ID',
  `video_id` bigint NOT NULL COMMENT '视频ID',
  `file_index` int NOT NULL COMMENT '文件索引',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='视频播放历史;@Spe;@Fac;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `video_post`
--

DROP TABLE IF EXISTS `video_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video_post` (
  `id` bigint NOT NULL COMMENT 'ID',
  `video_cover` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '视频封面',
  `video_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '视频名称',
  `customer_id` bigint NOT NULL COMMENT '用户ID',
  `p_category_id` bigint NOT NULL COMMENT '父级分类ID',
  `category_id` bigint DEFAULT NULL COMMENT '分类ID',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '视频状态 @E=0:UNKNOW:未知状态|1:TRANSCODING:转码中|2:TRANSCODE_FAILED:转码失败|3:PENDING_REVIEW:待审核|4:REVIEW_PASSED:审核成功|5:REVIEW_FAILED:审核失败;@T=VideoStatus',
  `post_type` tinyint NOT NULL DEFAULT '0' COMMENT '投稿类型 @E=0:UNKNOW:未知类型|1:ORIGINAL:自制作|2:REPOST:转载;@T=PostType',
  `origin_info` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '原资源说明',
  `tags` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标签',
  `introduction` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '简介',
  `interaction` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '互动设置',
  `duration` int NOT NULL DEFAULT '0' COMMENT '持续时间（秒）',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='视频信息;@Spe;@Fac;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `video_post_processing`
--

DROP TABLE IF EXISTS `video_post_processing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video_post_processing` (
  `id` bigint NOT NULL COMMENT 'ID',
  `video_post_id` bigint NOT NULL COMMENT '视频稿件ID',
  `total_files` int NOT NULL COMMENT '分P总数',
  `transcode_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '转码状态 @E=0:UNKNOW:未知|1:PENDING:待处理|2:PROCESSING:处理中|3:SUCCESS:成功|4:FAILED:失败|5:SKIPPED:跳过;@T=ProcessStatus',
  `encrypt_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '加密状态 @E=0:UNKNOW:未知|1:PENDING:待处理|2:PROCESSING:处理中|3:SUCCESS:成功|4:FAILED:失败|5:SKIPPED:跳过;@T=ProcessStatus',
  `transcode_done_count` int NOT NULL DEFAULT '0' COMMENT '转码完成数',
  `encrypt_done_count` int NOT NULL DEFAULT '0' COMMENT '加密完成数（含 SKIPPED）',
  `failed_count` int NOT NULL DEFAULT '0' COMMENT '失败文件数',
  `last_fail_reason` varchar(512) DEFAULT NULL COMMENT '最近失败原因',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_i` (`video_post_id`,`deleted`),
  KEY `idx_vpp_status` (`transcode_status`,`encrypt_status`,`update_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='视频稿件处理聚合;@Spe;@Fac;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `video_post_processing_file`
--

DROP TABLE IF EXISTS `video_post_processing_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video_post_processing_file` (
  `id` bigint NOT NULL COMMENT 'ID',
  `parent_id` bigint NOT NULL COMMENT '视频稿件处理ID@Ref=video_post_processing',
  `file_index` int NOT NULL COMMENT '文件索引',
  `upload_id` bigint NOT NULL COMMENT '上传会话ID',
  `transcode_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '转码状态 @E=0:UNKNOW:未知|1:PENDING:待处理|2:PROCESSING:处理中|3:SUCCESS:成功|4:FAILED:失败|5:SKIPPED:跳过;@T=ProcessStatus',
  `encrypt_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '加密状态 @E=0:UNKNOW:未知|1:PENDING:待处理|2:PROCESSING:处理中|3:SUCCESS:成功|4:FAILED:失败|5:SKIPPED:跳过;@T=ProcessStatus',
  `encrypt_method` int NOT NULL DEFAULT '1' COMMENT '加密方式@E=0:UNKNOW:未知|1:HLS_AES_128:AES-128|2:SAMPLE_AES:SAMPLE-AES|3:DRM:DRM占位;@T=EncryptMethod',
  `encrypt_key_version` int DEFAULT NULL COMMENT '加密密钥版本',
  `transcode_output_prefix` varchar(512) DEFAULT NULL COMMENT '转码输出对象前缀',
  `transcode_output_path` varchar(512) DEFAULT NULL COMMENT '转码输出本地路径',
  `transcode_variants_json` text COMMENT '转码清晰度结果 JSON',
  `encrypt_output_dir` varchar(512) DEFAULT NULL COMMENT '加密输出本地目录',
  `encrypt_output_prefix` varchar(512) DEFAULT NULL COMMENT '加密输出对象前缀',
  `duration` int DEFAULT NULL COMMENT '时长（秒）',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小',
  `fail_reason` varchar(512) DEFAULT NULL COMMENT '失败原因',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_i` (`parent_id`,`file_index`,`deleted`),
  KEY `idx_vppf_post` (`parent_id`) USING BTREE,
  KEY `idx_vppf_transcode` (`transcode_status`,`update_time`) USING BTREE,
  KEY `idx_vppf_encrypt` (`encrypt_status`,`update_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='视频稿件处理文件状态;@P=video_post_processing;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `video_post_processing_variant`
--

DROP TABLE IF EXISTS `video_post_processing_variant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video_post_processing_variant` (
  `id` bigint NOT NULL COMMENT 'ID',
  `parent_id` bigint NOT NULL COMMENT '视频稿件处理文件ID@Ref=video_post_processing_file',
  `quality` varchar(32) NOT NULL COMMENT '清晰度档位，如 1080p/720p',
  `width` int NOT NULL COMMENT '输出宽度(px)',
  `height` int NOT NULL COMMENT '输出高度(px)',
  `video_bitrate_kbps` int NOT NULL COMMENT '视频码率(kbps)',
  `audio_bitrate_kbps` int NOT NULL COMMENT '音频码率(kbps)',
  `bandwidth_bps` int NOT NULL COMMENT 'Master 中的 BANDWIDTH（bps）',
  `playlist_path` varchar(512) NOT NULL COMMENT '子清晰度 m3u8 路径，如 720p/index.m3u8',
  `segment_prefix` varchar(512) DEFAULT NULL COMMENT '切片目录前缀，如 720p/',
  `segment_duration` int DEFAULT NULL COMMENT '切片目标时长（秒）',
  `transcode_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '转码状态 @E=0:UNKNOW:未知|1:PENDING:待处理|2:PROCESSING:处理中|3:SUCCESS:成功|4:FAILED:失败|5:SKIPPED:跳过;@T=ProcessStatus',
  `encrypt_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '加密状态 @E=0:UNKNOW:未知|1:PENDING:待处理|2:PROCESSING:处理中|3:SUCCESS:成功|4:FAILED:失败|5:SKIPPED:跳过;@T=ProcessStatus',
  `encrypt_fail_reason` varchar(512) DEFAULT NULL COMMENT '加密失败原因',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_i` (`parent_id`,`quality`,`deleted`),
  KEY `idx_vpp_av_height` (`height`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='视频稿件处理分辨率档位;@P=video_post_processing_file;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `video_quality_policy`
--

DROP TABLE IF EXISTS `video_quality_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video_quality_policy` (
  `id` bigint NOT NULL COMMENT 'ID',
  `video_id` bigint NOT NULL COMMENT '视频ID',
  `file_index` int NOT NULL COMMENT '文件索引',
  `quality` varchar(32) NOT NULL COMMENT '清晰度档位，如 1080p',
  `auth_policy` int NOT NULL DEFAULT '1' COMMENT '授权策略@E=0:UNKNOW:未知|1:PUBLIC:公开|2:LOGIN:登录|3:PAID:付费|4:CUSTOM:自定义;@T=QualityAuthPolicy',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注/策略说明',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人名称',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人名称',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0：未删除 id：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_i` (`video_id`,`file_index`,`quality`,`deleted`),
  KEY `idx_video_quality_policy` (`auth_policy`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='视频清晰度策略;@Spe;@Fac;';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-14 15:38:08
