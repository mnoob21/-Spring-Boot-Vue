/*
 Navicat Premium Data Transfer

 Source Server         : Mysql
 Source Server Type    : MySQL
 Source Server Version : 80036
 Source Host           : localhost:3306
 Source Schema         : studentms

 Target Server Type    : MySQL
 Target Server Version : 80036
 File Encoding         : 65001

 Date: 21/10/2024 17:17:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for a
-- ----------------------------
DROP TABLE IF EXISTS `a`;
CREATE TABLE `a`  (
  `aid` int(0) NOT NULL AUTO_INCREMENT COMMENT '管理员id自增主键',
  `aname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `aphone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime(0) DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`aid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of a
-- ----------------------------
INSERT INTO `a` VALUES (1, 'admin1', '123456', '13111111111', '2024-10-11 15:19:06', NULL);
INSERT INTO `a` VALUES (2, 'admin2', '123456', '13222222222', '2024-10-11 15:19:32', NULL);

-- ----------------------------
-- Table structure for c
-- ----------------------------
DROP TABLE IF EXISTS `c`;
CREATE TABLE `c`  (
  `cid` int(0) NOT NULL AUTO_INCREMENT,
  `cname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ctype_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime(0) DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  `c_author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `c_startdate` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `c_enddate` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `c_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `c_status` int(0) DEFAULT NULL COMMENT '课程状态：0——已申请未通过审核\r\n                   1——已通过审核未到时间\r\n                   2——课程进行中\r\n                   3——课程结束\r\n                   4——申请被驳回',
  `c_reviewer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `c_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`cid`) USING BTREE,
  INDEX `c_ibfk_1`(`ctype_name`) USING BTREE,
  CONSTRAINT `c_ibfk_1` FOREIGN KEY (`ctype_name`) REFERENCES `ctype` (`ctype_name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 59 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of c
-- ----------------------------
INSERT INTO `c` VALUES (56, 'admin带你看世界', '人文技巧', '2024-10-20 15:24:43', '2024-10-20 15:32:50', 'admin1', '2024-10-21 08:30', '2024-10-21 10:00', 'www.52admin.com', 1, 'admin1', 'admin是什么，admin22222222222222222222222222');
INSERT INTO `c` VALUES (57, '啊mei的奇妙冒险', '诗词歌赋', '2024-10-20 15:33:44', '2024-10-20 15:34:38', '啊mei', '2024-10-21 09:00', '2024-10-21 11:30', 'www.ameishishuaige.com', 1, 'admin1', '啊mei对于如何去野外冒险的心得体会33333333');
INSERT INTO `c` VALUES (58, '我1.6cm 200斤是如何傍上富婆的腰', '人文技巧', '2024-10-21 16:47:38', '2024-10-21 16:49:49', '阿瓦达', '2024-10-22 08:30', '2024-10-22 12:30', 'www.wmdafp.com', 2, 'admin1', '教你屌丝如何大翻盘\n如何在地狱难度走上人生巅峰\n迎娶白富美\n出任CEO\n(狗屁)');

-- ----------------------------
-- Table structure for ccomment
-- ----------------------------
DROP TABLE IF EXISTS `ccomment`;
CREATE TABLE `ccomment`  (
  `ccomment_id` int(0) NOT NULL AUTO_INCREMENT,
  `ccomment_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `ccomment_author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `ccomment_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `c_id` int(0) DEFAULT NULL,
  PRIMARY KEY (`ccomment_id`) USING BTREE,
  INDEX `ccomment_ibfk_1`(`c_id`) USING BTREE,
  CONSTRAINT `ccomment_ibfk_1` FOREIGN KEY (`c_id`) REFERENCES `c` (`cid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ccomment
-- ----------------------------
INSERT INTO `ccomment` VALUES (50, '12312332123', 'admin1', '2024-10-21', 56);
INSERT INTO `ccomment` VALUES (51, '123123123123', 'admin1', '2024-10-21', 56);
INSERT INTO `ccomment` VALUES (52, '123123123123', 'admin1', '2024-10-21', 56);
INSERT INTO `ccomment` VALUES (53, '123123123123', 'admin1', '2024-10-21', 56);
INSERT INTO `ccomment` VALUES (54, '123', '张大爷', '2024-10-21', 57);
INSERT INTO `ccomment` VALUES (55, '12333123', '张大爷', '2024-10-21', 57);

-- ----------------------------
-- Table structure for ct
-- ----------------------------
DROP TABLE IF EXISTS `ct`;
CREATE TABLE `ct`  (
  `ctid` int(0) NOT NULL AUTO_INCREMENT,
  `cid` int(0) DEFAULT NULL,
  `tid` int(0) DEFAULT NULL,
  `term` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`ctid`) USING BTREE,
  INDEX `cid`(`cid`) USING BTREE,
  INDEX `tid`(`tid`) USING BTREE,
  CONSTRAINT `ct_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `c` (`cid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ct_ibfk_2` FOREIGN KEY (`tid`) REFERENCES `t` (`tid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ct
-- ----------------------------
INSERT INTO `ct` VALUES (59, 57, 24, '2024年度');
INSERT INTO `ct` VALUES (60, 58, 25, '2024年度');

-- ----------------------------
-- Table structure for ctype
-- ----------------------------
DROP TABLE IF EXISTS `ctype`;
CREATE TABLE `ctype`  (
  `ctype_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '课程类别主键',
  `ctype_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '课程类别名称',
  `ctype_author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime(0) DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`ctype_id`) USING BTREE,
  INDEX `ctype_name`(`ctype_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ctype
-- ----------------------------
INSERT INTO `ctype` VALUES (2, '诗词歌赋', 'admin2', '2024-10-11 19:10:28', NULL);
INSERT INTO `ctype` VALUES (3, '曲艺相声', 'admin1', '2024-10-12 11:22:40', NULL);
INSERT INTO `ctype` VALUES (4, '舞蹈演绎', 'admin1', '2024-10-12 11:30:27', NULL);
INSERT INTO `ctype` VALUES (5, '人文技巧', 'admin1', '2024-10-12 12:56:57', NULL);
INSERT INTO `ctype` VALUES (36, '计算机知识', 'admin1', '2024-10-13 13:45:55', NULL);
INSERT INTO `ctype` VALUES (37, '琴棋书画', 'admin1', '2024-10-15 10:23:22', NULL);

-- ----------------------------
-- Table structure for s
-- ----------------------------
DROP TABLE IF EXISTS `s`;
CREATE TABLE `s`  (
  `sid` int(0) NOT NULL AUTO_INCREMENT,
  `sname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ssex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `sphone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `semail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `sbirthday` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_time` datetime(0) DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`sid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s
-- ----------------------------
INSERT INTO `s` VALUES (1, '阮健乘', '1341241242', '男', '15323423222', '1412@14.com', '2024-11-03', '2024-10-10 12:14:08', '2024-10-09 15:36:56');
INSERT INTO `s` VALUES (2, '张四', '312313', '男', '15432454423', '15123@qwe.com', '2024-11-03', '2002-07-09 12:33:13', '2024-10-09 14:31:40');
INSERT INTO `s` VALUES (3, '李四', '321321', '男', '15323244444', '12312@1111.com', '2024-11-03', '2020-07-24 12:33:40', '2024-10-11 12:59:07');
INSERT INTO `s` VALUES (4, '彭昊辉', '4123213124', '男', '16754454433', '123512@qq.com', '2024-11-03', '2024-10-11 12:33:47', '2024-10-09 14:35:27');
INSERT INTO `s` VALUES (7, '董一超', '523432432', '男', '19876898765', '151231@11.com', '2024-11-03', '2024-10-14 12:33:55', '2024-10-09 14:55:57');
INSERT INTO `s` VALUES (8, '董超', '321', '男', '15345678906', '151231@11.com', '2024-11-03', '2024-10-04 12:33:58', '2024-10-09 15:40:02');
INSERT INTO `s` VALUES (9, '张千', '64436346346', '男', '16544677777', '151231@11.com', '2024-11-03', '2024-10-06 12:34:01', '2024-10-09 14:42:19');
INSERT INTO `s` VALUES (10, '李万', '10085', '男', '16789889876', '151231@11.com', '2024-11-03', '2024-10-02 12:34:04', NULL);
INSERT INTO `s` VALUES (14, '薇尔莉特', '123', '女', '17656787655', '151231@11.com', '2024-11-03', '2024-10-02 12:34:06', NULL);
INSERT INTO `s` VALUES (21, '庄亮', '123', '男', '16566777666', '151231@11.com', '2024-11-03', '2024-10-12 12:34:10', NULL);
INSERT INTO `s` VALUES (22, '钟平', '1231231', '女', '16544456765', '151231@11.com', '2024-11-03', '2024-10-13 12:34:13', '2024-10-09 14:51:54');
INSERT INTO `s` VALUES (23, '李煜豪', '123', '男', '12343233456', '151231@11.com', '2024-11-03', '2024-10-03 12:34:16', NULL);
INSERT INTO `s` VALUES (25, '12341', '1123123', '男', '17382939883', '12313@123.xom', '2024-11-03', '2024-10-08 18:37:24', NULL);
INSERT INTO `s` VALUES (26, 'M爱的1', '123123', '男', '18371369985', '1363007144@163.com', '2003-07-19', '2024-10-09 10:11:07', '2024-10-11 17:28:13');
INSERT INTO `s` VALUES (28, '阿拉蕾', '123123', '女', '15387208989', '4123@131.com', '2024-10-25', '2024-10-09 11:22:34', '2024-10-15 12:45:57');
INSERT INTO `s` VALUES (29, '1231', '11111', '男', '19473822222', '4123@1312.com', '2024-10-11', '2024-10-09 11:23:10', NULL);
INSERT INTO `s` VALUES (30, '我的天哪', '4232123', '女', '15323244456', '12313@1321.com', '2024-10-16', '2024-10-09 12:38:58', NULL);
INSERT INTO `s` VALUES (31, '啊达娃大', 'KzhjSfiVLeOVoMRBLJl4UZ5etYTkzH8iEkihCQaSlg8=:9CPnQnlNqtdirLOCCd9UgQ==', '男', '18371369911', '123213123@11.com', '2004-10-05', '2024-10-09 18:11:43', '2024-10-18 15:40:30');
INSERT INTO `s` VALUES (32, '仆人', 'WCoVOzTE11u0QNFTSY/pONZAFMRsWCoTkkndJFZIGnk=:bJSnIDebrovHW0iyKB85Ww==', '男', '18767387678', '123131@123.com', '2005-10-17', '2024-10-18 14:44:21', NULL);
INSERT INTO `s` VALUES (33, '张大爷', 'urfNiaDywbwbxB8ki7q5jRCULQYSRQ2AFQs0lDq4mHc=:afB0iNZNl5wVeKNCxYhIFg==', '男', '18989898989', '123132@123.com', '2024-10-22', '2024-10-18 14:55:40', '2024-10-18 15:56:00');

-- ----------------------------
-- Table structure for sct
-- ----------------------------
DROP TABLE IF EXISTS `sct`;
CREATE TABLE `sct`  (
  `sctid` int(0) NOT NULL AUTO_INCREMENT,
  `sid` int(0) DEFAULT NULL,
  `cid` int(0) DEFAULT NULL,
  `tid` int(0) DEFAULT NULL,
  `grade` float DEFAULT NULL,
  `term` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`sctid`) USING BTREE,
  INDEX `sid`(`sid`) USING BTREE,
  INDEX `tid`(`tid`) USING BTREE,
  INDEX `cid`(`cid`) USING BTREE,
  CONSTRAINT `sct_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `s` (`sid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sct_ibfk_2` FOREIGN KEY (`tid`) REFERENCES `ct` (`tid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sct_ibfk_3` FOREIGN KEY (`cid`) REFERENCES `ct` (`cid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sct
-- ----------------------------
INSERT INTO `sct` VALUES (63, 32, 57, 24, NULL, '2024年度');
INSERT INTO `sct` VALUES (64, 33, 58, 25, NULL, '2024年度');

-- ----------------------------
-- Table structure for t
-- ----------------------------
DROP TABLE IF EXISTS `t`;
CREATE TABLE `t`  (
  `tid` int(0) NOT NULL AUTO_INCREMENT,
  `tname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `treal_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `tid_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `tsex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `tphone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `temail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `tbirthday` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_time` datetime(0) DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`tid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t
-- ----------------------------
INSERT INTO `t` VALUES (4, '李老头', '123321123321', '曹操', '432133422231123111', '男', '19873678493', '12@11.com', '1995-10-04', '2024-10-10 16:49:03', '2024-10-13 13:41:15');
INSERT INTO `t` VALUES (13, '张三', '321321', '孙悟空', '512331233311231123', '男', '16453876549', '1231312@11.com', '1998-10-01', '2024-10-09 16:49:10', '2024-10-13 13:39:59');
INSERT INTO `t` VALUES (14, '王五123', '123123', '杨戬', '145554234432242442', '女', '18371369986', '312@11.com', '1998-10-02', '2024-10-09 16:49:14', '2024-10-13 13:42:10');
INSERT INTO `t` VALUES (15, '14123', '321321', '企鹅', '512333211133333334', '男', '15322222222', '13123131@13.com', '2024-10-17', '2024-10-08 16:32:05', NULL);
INSERT INTO `t` VALUES (16, '13123', '222111', 'qwe', '3123123213123', '女', '18763873678', '1123123', '1998-10-06', '2024-10-09 16:49:17', '2024-10-10 14:04:14');
INSERT INTO `t` VALUES (17, 'qiuweu', '111222', 'qwe', '837773666666666666', '男', '15322345678', '1231@131.com', '2024-10-06', '2024-10-08 17:48:53', '2024-10-10 13:59:34');
INSERT INTO `t` VALUES (18, '我是你爹', '654321123456', '埃德加', '512334200506120789', '男', '15325467654', '123123@163.com', '2005-06-11', '2024-10-09 10:16:39', NULL);
INSERT INTO `t` VALUES (23, '赵六是个刘', '123123', '赵六', '421120199803019371', '女', '15321234555', '13123@131.com', '1993-10-15', '2024-10-09 16:57:42', '2024-10-11 17:29:14');
INSERT INTO `t` VALUES (24, '啊mei', 'vSxAfoBKHinTH63TSW01D6Jrk1coVDIFpaFcZTruqsc=:qDQvsII6LO1Lxzn7MBrneg==', '马云', '487626200307190350', '男', '18371369988', '1231@163.com', '2004-10-12', '2024-10-18 13:55:07', '2024-10-19 13:27:43');
INSERT INTO `t` VALUES (25, '阿瓦达', 'xhdK5yH/OsDN3XqESd//SdG/HnNnZEurvKz0csnSdCU=:LGg9wW9eK8eY14K5hAchdA==', '巍峨', '456553637773656390', '男', '18371369900', '1231@123.com', '2024-10-22', '2024-10-18 14:28:54', '2024-10-18 15:06:56');

SET FOREIGN_KEY_CHECKS = 1;
