/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : bookmanager

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-12-04 10:04:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `username` varchar(40) DEFAULT NULL COMMENT '管理员账号',
  `password` varchar(40) DEFAULT NULL COMMENT '管理员密码',
  `nickname` varchar(40) DEFAULT NULL COMMENT '管理员匿名',
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------

-- ----------------------------
-- Table structure for `book`
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `book_id` int(40) NOT NULL AUTO_INCREMENT COMMENT '图书id',
  `book_name` varchar(40) DEFAULT NULL COMMENT '书名',
  `author` varchar(40) DEFAULT NULL COMMENT '图书作者',
  `image_url` varchar(255) DEFAULT NULL COMMENT '图书封面',
  `location` varchar(255) DEFAULT NULL COMMENT '图书位置',
  `borrow_date` datetime DEFAULT NULL COMMENT '借出日期',
  `return_date` datetime DEFAULT NULL COMMENT '还书时间',
  `type` int(11) DEFAULT NULL COMMENT '图书类型',
  `isbn` varchar(100) DEFAULT NULL COMMENT 'isbn',
  `state` int(11) DEFAULT NULL COMMENT '图书状态(0:未借出;1:已借出;2:已销毁;)',
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book
-- ----------------------------

-- ----------------------------
-- Table structure for `group`
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group` (
  `user_group_id` int(40) NOT NULL DEFAULT '0',
  `user_group_info` varchar(40) DEFAULT '普通用户' COMMENT '群组信息',
  PRIMARY KEY (`user_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of group
-- ----------------------------

-- ----------------------------
-- Table structure for `state`
-- ----------------------------
DROP TABLE IF EXISTS `state`;
CREATE TABLE `state` (
  `state_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '状态码',
  `state_name` varchar(40) DEFAULT '' COMMENT '状态',
  PRIMARY KEY (`state_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of state
-- ----------------------------

-- ----------------------------
-- Table structure for `type`
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of type
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '用户密码',
  `nickname` varchar(255) NOT NULL COMMENT '用户匿名',
  `points` int(40) NOT NULL DEFAULT '50' COMMENT '信誉积分(小于等于0封号;初始值50;上限100)',
  `group` int(40) NOT NULL DEFAULT '0' COMMENT '用户组群',
  `user_state` int(40) NOT NULL DEFAULT '0' COMMENT '用户状态(0:审核中;1:通过;2:封禁;)',
  `e-mail` varchar(40) NOT NULL COMMENT '用于用户的注册验证的唯一性',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for `user_state`
-- ----------------------------
DROP TABLE IF EXISTS `user_state`;
CREATE TABLE `user_state` (
  `user_state_id` int(40) NOT NULL AUTO_INCREMENT,
  `user_state_info` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_state_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_state
-- ----------------------------
