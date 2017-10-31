/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : sixing2

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-10-12 16:05:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `advice`
-- ----------------------------
DROP TABLE IF EXISTS `advice`;
CREATE TABLE `advice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK74A3691856AA4031` (`operator_id`),
  KEY `FK74A36918ACDF2125` (`user`),
  CONSTRAINT `FK74A3691856AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FK74A36918ACDF2125` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of advice
-- ----------------------------

-- ----------------------------
-- Table structure for `appraisement`
-- ----------------------------
DROP TABLE IF EXISTS `appraisement`;
CREATE TABLE `appraisement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `content` varchar(50) DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  `org_list` varchar(255) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `voter` varchar(1500) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKD784166956AA4031` (`operator_id`),
  CONSTRAINT `FKD784166956AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of appraisement
-- ----------------------------

-- ----------------------------
-- Table structure for `article`
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `author` varchar(10) DEFAULT NULL,
  `content` longtext,
  `count` int(11) NOT NULL DEFAULT '0',
  `imgUrl` varchar(255) DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  `targetOut` tinyint(1) DEFAULT NULL,
  `targetUrl` varchar(255) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK379164D656AA4031` (`operator_id`),
  KEY `FK379164D69E3E69BA` (`type`),
  CONSTRAINT `FK379164D656AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FK379164D69E3E69BA` FOREIGN KEY (`type`) REFERENCES `dict` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article
-- ----------------------------

-- ----------------------------
-- Table structure for `backlog`
-- ----------------------------
DROP TABLE IF EXISTS `backlog`;
CREATE TABLE `backlog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `doDate` date DEFAULT NULL,
  `mission_id` int(11) DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  `subject` varchar(50) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `year` varchar(10) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4E86B8DD56AA4031` (`operator_id`),
  CONSTRAINT `FK4E86B8DD56AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of backlog
-- ----------------------------

-- ----------------------------
-- Table structure for `backlog_user`
-- ----------------------------
DROP TABLE IF EXISTS `backlog_user`;
CREATE TABLE `backlog_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `is_finished` int(11) NOT NULL DEFAULT '0',
  `operator_id` int(11) DEFAULT NULL,
  `backlog_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK45B199CD56AA4031` (`operator_id`),
  KEY `FK45B199CDF79F8CAB` (`backlog_id`),
  KEY `FK45B199CDA3E32369` (`user_id`),
  CONSTRAINT `FK45B199CD56AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FK45B199CDA3E32369` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK45B199CDF79F8CAB` FOREIGN KEY (`backlog_id`) REFERENCES `backlog` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of backlog_user
-- ----------------------------

-- ----------------------------
-- Table structure for `calendar`
-- ----------------------------
DROP TABLE IF EXISTS `calendar`;
CREATE TABLE `calendar` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `doDate` date DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  `subject` varchar(15) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKF9395F5E56AA4031` (`operator_id`),
  CONSTRAINT `FKF9395F5E56AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of calendar
-- ----------------------------

-- ----------------------------
-- Table structure for `calendar_user`
-- ----------------------------
DROP TABLE IF EXISTS `calendar_user`;
CREATE TABLE `calendar_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `calendar_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7504002C56AA4031` (`operator_id`),
  KEY `FK7504002C25FBAB09` (`calendar_id`),
  KEY `FK7504002CA3E32369` (`user_id`),
  CONSTRAINT `FK7504002C25FBAB09` FOREIGN KEY (`calendar_id`) REFERENCES `calendar` (`id`),
  CONSTRAINT `FK7504002C56AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FK7504002CA3E32369` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of calendar_user
-- ----------------------------

-- ----------------------------
-- Table structure for `carousel`
-- ----------------------------
DROP TABLE IF EXISTS `carousel`;
CREATE TABLE `carousel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `author` varchar(10) DEFAULT NULL,
  `content` longtext,
  `imageUrl` varchar(255) DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  `targetOut` tinyint(1) DEFAULT NULL,
  `targetUrl` varchar(255) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK406C58056AA4031` (`operator_id`),
  KEY `FK406C5809E3E69BA` (`type`),
  CONSTRAINT `FK406C58056AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FK406C5809E3E69BA` FOREIGN KEY (`type`) REFERENCES `dict` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of carousel
-- ----------------------------

-- ----------------------------
-- Table structure for `choice`
-- ----------------------------
DROP TABLE IF EXISTS `choice`;
CREATE TABLE `choice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `choice` varchar(255) DEFAULT NULL,
  `evaluate` varchar(255) DEFAULT NULL,
  `userName` varchar(10) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `appr_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK784249C156AA4031` (`operator_id`),
  KEY `FK784249C19FC5D281` (`appr_id`),
  CONSTRAINT `FK784249C156AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FK784249C19FC5D281` FOREIGN KEY (`appr_id`) REFERENCES `appraisement` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of choice
-- ----------------------------

-- ----------------------------
-- Table structure for `data`
-- ----------------------------
DROP TABLE IF EXISTS `data`;
CREATE TABLE `data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2063CA56AA4031` (`operator_id`),
  CONSTRAINT `FK2063CA56AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of data
-- ----------------------------

-- ----------------------------
-- Table structure for `data_user`
-- ----------------------------
DROP TABLE IF EXISTS `data_user`;
CREATE TABLE `data_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `data_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKEA57EF4056AA4031` (`operator_id`),
  KEY `FKEA57EF402A5A309` (`data_id`),
  KEY `FKEA57EF40A3E32369` (`user_id`),
  CONSTRAINT `FKEA57EF402A5A309` FOREIGN KEY (`data_id`) REFERENCES `data` (`id`),
  CONSTRAINT `FKEA57EF4056AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FKEA57EF40A3E32369` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of data_user
-- ----------------------------

-- ----------------------------
-- Table structure for `dict`
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(255) DEFAULT NULL,
  `updateTime` varchar(255) DEFAULT NULL,
  `dictId` varchar(255) DEFAULT NULL,
  `dictName` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `seqCn` varchar(255) DEFAULT NULL,
  `seqNo` varchar(255) DEFAULT NULL,
  `sort` int(11) NOT NULL,
  `dictType` int(11) DEFAULT NULL,
  `parent` int(11) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK207FD656AA4031` (`operator_id`),
  KEY `FK207FD63FAB6DCA` (`dictType`),
  KEY `FK207FD662B2E32A` (`parent`),
  CONSTRAINT `FK207FD63FAB6DCA` FOREIGN KEY (`dictType`) REFERENCES `dicttype` (`id`),
  CONSTRAINT `FK207FD656AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FK207FD662B2E32A` FOREIGN KEY (`parent`) REFERENCES `dict` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dict
-- ----------------------------
INSERT INTO `dict` VALUES ('1', null, null, 'djyw', '党建要闻', null, null, null, '1', '9', null, null, null);
INSERT INTO `dict` VALUES ('2', null, null, 'tzgg', '通知公告', null, null, null, '2', '9', null, null, null);
INSERT INTO `dict` VALUES ('3', null, null, 'llxx', '理论学习', null, null, null, '3', '9', null, null, null);
INSERT INTO `dict` VALUES ('7', null, null, 'zyxw', '中央新闻', null, null, null, '1', '6', '1', null, null);
INSERT INTO `dict` VALUES ('8', null, null, 'hydt', '行业动态', null, null, null, '2', '6', '1', null, null);
INSERT INTO `dict` VALUES ('9', null, null, 'ayxx', '市委信息', null, null, null, '3', '6', '1', null, null);
INSERT INTO `dict` VALUES ('10', null, null, 'shyk', '三会一课', null, null, null, '2', '9', null, null, null);
INSERT INTO `dict` VALUES ('11', null, null, 'dydh', '党员大会', null, null, null, '1', '6', '10', null, null);
INSERT INTO `dict` VALUES ('12', null, null, 'dyhytz', '党员会议通知', null, null, null, '1', '6', '11', null, null);
INSERT INTO `dict` VALUES ('13', null, null, 'dyhyjy', '党员会议内容', null, null, null, '2', '6', '11', null, null);
INSERT INTO `dict` VALUES ('14', null, null, 'dzbwyh', '党支部委员会', null, null, null, '2', '6', '10', null, null);
INSERT INTO `dict` VALUES ('15', null, null, 'dxzh', '党小组会', null, null, null, '3', '6', '10', null, null);
INSERT INTO `dict` VALUES ('16', null, null, 'dk', '党课', null, null, null, '4', '6', '10', null, null);
INSERT INTO `dict` VALUES ('17', null, null, 'lxyz', '两学一做', null, null, null, '3', '9', null, null, null);
INSERT INTO `dict` VALUES ('18', null, null, 'mzshh', '民主生活会', null, null, null, '4', null, null, null, null);
INSERT INTO `dict` VALUES ('19', null, null, 'dg', '党规', null, null, null, '3', '6', '3', null, null);
INSERT INTO `dict` VALUES ('20', null, null, 'ds', '党史', null, null, null, '1', '6', '3', null, null);
INSERT INTO `dict` VALUES ('21', null, null, 'dz', '党章', null, null, null, '2', '6', '3', null, null);
INSERT INTO `dict` VALUES ('22', null, null, 'xljh', '系列讲话', null, null, null, '3', '6', '3', null, null);
INSERT INTO `dict` VALUES ('23', null, null, 'llts', '理论推送', null, null, null, '4', '6', '3', null, null);
INSERT INTO `dict` VALUES ('27', null, null, 'zxxx', '在线学习', null, null, null, '5', '6', '3', null, null);
INSERT INTO `dict` VALUES ('28', null, null, 'dzbhytz', '党支部会议通知', null, null, null, '1', '6', '14', null, null);
INSERT INTO `dict` VALUES ('29', null, null, 'dzbhyjy', '党支部会议纪要', null, null, null, '2', '6', '14', null, null);
INSERT INTO `dict` VALUES ('30', null, null, 'dxzhytz', '党小组会议通知', null, null, null, '1', '6', '15', null, null);
INSERT INTO `dict` VALUES ('31', null, null, 'dxzhyjy', '党小组会议内容', null, null, null, '2', '6', '15', null, null);
INSERT INTO `dict` VALUES ('32', null, null, 'dkjy', '党课讲义', null, null, null, '1', '6', '16', null, null);
INSERT INTO `dict` VALUES ('33', null, null, 'wdkxy', '微党课学习', null, null, null, '2', '6', '16', null, null);
INSERT INTO `dict` VALUES ('34', null, null, 'xdzdg', '学党章党规', null, null, null, '1', '6', '17', null, null);
INSERT INTO `dict` VALUES ('35', null, null, 'xxljh', '学系列讲话', null, null, null, '2', '6', '17', null, null);
INSERT INTO `dict` VALUES ('36', null, null, 'zhgdy', '做合格党员', null, null, null, '3', '6', '17', null, null);
INSERT INTO `dict` VALUES ('37', null, null, 'mzhytz', '民主会议通知', null, null, null, '1', '6', '18', null, null);
INSERT INTO `dict` VALUES ('38', null, null, 'mzhyjy', '民主会议决议', null, null, null, '2', '6', '18', null, null);
INSERT INTO `dict` VALUES ('39', null, null, 'dxz', '党组', null, null, null, '2', '6', '2', null, null);
INSERT INTO `dict` VALUES ('40', null, null, 'jgdw', '机关党委', null, null, null, '3', '6', '2', null, null);
INSERT INTO `dict` VALUES ('41', null, null, 'dzb', '党支部', null, null, null, '1', '6', '2', null, null);
INSERT INTO `dict` VALUES ('42', null, null, 'gzgf', '工作规范', null, null, null, '1', '9', null, null, null);
INSERT INTO `dict` VALUES ('43', null, null, 'djywjf', '党建要闻积分', null, null, null, '1', '8', null, '5', null);
INSERT INTO `dict` VALUES ('44', null, null, 'tzggjf', '通知公告积分', null, null, null, '2', '8', null, '1', null);
INSERT INTO `dict` VALUES ('45', null, null, 'llxxjf', '理论学习积分', null, null, null, '3', '8', null, '5', null);
INSERT INTO `dict` VALUES ('46', null, null, 'zxtpjf', '在线投票积分', null, null, null, '4', '8', null, '2', null);
INSERT INTO `dict` VALUES ('47', null, null, 'mzpyjf', '民主评议积分', null, null, null, '5', '8', null, '10', null);
INSERT INTO `dict` VALUES ('48', null, null, 'zxksjf', '在线考试积分', null, null, null, '6', '8', null, '3', null);
INSERT INTO `dict` VALUES ('49', null, null, 'zxjfjf', '在线缴费积分', null, null, null, '7', '8', null, '10', null);
INSERT INTO `dict` VALUES ('50', null, null, 'gzfkjf', '工作展示积分', null, null, null, '8', '8', null, '5', null);
INSERT INTO `dict` VALUES ('51', null, null, 'sxhbjf', '思想汇报积分', null, null, null, '9', '8', null, '5', null);
INSERT INTO `dict` VALUES ('53', null, null, 'lzjs', '廉政建设', null, null, null, '6', '9', null, null, null);
INSERT INTO `dict` VALUES ('54', null, null, 'ztxx', '专题学习', null, null, null, '3', '9', null, null, null);

-- ----------------------------
-- Table structure for `dicttype`
-- ----------------------------
DROP TABLE IF EXISTS `dicttype`;
CREATE TABLE `dicttype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(255) DEFAULT NULL,
  `updateTime` varchar(255) DEFAULT NULL,
  `dictTypeId` varchar(255) DEFAULT NULL,
  `dictTypeName` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKF9B8A83056AA4031` (`operator_id`),
  CONSTRAINT `FKF9B8A83056AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dicttype
-- ----------------------------
INSERT INTO `dicttype` VALUES ('6', null, null, 'article', '文章', null, null);
INSERT INTO `dicttype` VALUES ('7', null, null, 'orgversion', '联系人版本', '1506410526456', null);
INSERT INTO `dicttype` VALUES ('8', null, null, 'points', ' 积分配置', '1000', null);
INSERT INTO `dicttype` VALUES ('9', null, null, 'articleKind', '文章分类', null, null);

-- ----------------------------
-- Table structure for `exam`
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `content` varchar(100) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `expire` datetime DEFAULT NULL,
  `pass` double(5,2) NOT NULL,
  `times` int(11) DEFAULT NULL,
  `title` varchar(20) DEFAULT NULL,
  `total` double(5,2) NOT NULL DEFAULT '0.00',
  `type` int(11) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK212C3F56AA4031` (`operator_id`),
  CONSTRAINT `FK212C3F56AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exam
-- ----------------------------

-- ----------------------------
-- Table structure for `exam_questions`
-- ----------------------------
DROP TABLE IF EXISTS `exam_questions`;
CREATE TABLE `exam_questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `exam_id` int(11) DEFAULT NULL,
  `questions_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK76A1992D56AA4031` (`operator_id`),
  KEY `FK76A1992D5DC5D2E9` (`exam_id`),
  KEY `FK76A1992D838A2A6B` (`questions_id`),
  CONSTRAINT `FK76A1992D56AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FK76A1992D5DC5D2E9` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`),
  CONSTRAINT `FK76A1992D838A2A6B` FOREIGN KEY (`questions_id`) REFERENCES `questions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exam_questions
-- ----------------------------

-- ----------------------------
-- Table structure for `filesave`
-- ----------------------------
DROP TABLE IF EXISTS `filesave`;
CREATE TABLE `filesave` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `worktask_reply_id` int(11) DEFAULT NULL,
  `worktask_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKD802713956AA4031` (`operator_id`),
  KEY `FKD8027139E771383E` (`worktask_reply_id`),
  KEY `FKD802713952B55029` (`worktask_id`),
  CONSTRAINT `FKD802713952B55029` FOREIGN KEY (`worktask_id`) REFERENCES `worktask` (`id`),
  CONSTRAINT `FKD802713956AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FKD8027139E771383E` FOREIGN KEY (`worktask_reply_id`) REFERENCES `worktaskreply` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of filesave
-- ----------------------------

-- ----------------------------
-- Table structure for `jobback`
-- ----------------------------
DROP TABLE IF EXISTS `jobback`;
CREATE TABLE `jobback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `activityDate` datetime DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `desc1` varchar(255) DEFAULT NULL,
  `desc2` varchar(255) DEFAULT NULL,
  `image1` varchar(255) DEFAULT NULL,
  `image2` varchar(255) DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  `subject` varchar(50) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKD7B862456AA4031` (`operator_id`),
  KEY `FKD7B8624ACDF2125` (`user`),
  CONSTRAINT `FKD7B862456AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FKD7B8624ACDF2125` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jobback
-- ----------------------------

-- ----------------------------
-- Table structure for `jobbackreply`
-- ----------------------------
DROP TABLE IF EXISTS `jobbackreply`;
CREATE TABLE `jobbackreply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `jobBack_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK12E9D32656AA4031` (`operator_id`),
  KEY `FK12E9D3261D381B6B` (`jobBack_id`),
  KEY `FK12E9D326A3E32369` (`user_id`),
  CONSTRAINT `FK12E9D3261D381B6B` FOREIGN KEY (`jobBack_id`) REFERENCES `jobback` (`id`),
  CONSTRAINT `FK12E9D32656AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FK12E9D326A3E32369` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jobbackreply
-- ----------------------------

-- ----------------------------
-- Table structure for `jobback_user`
-- ----------------------------
DROP TABLE IF EXISTS `jobback_user`;
CREATE TABLE `jobback_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `jobback_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK926AE10656AA4031` (`operator_id`),
  KEY `FK926AE1061D381B6B` (`jobback_id`),
  KEY `FK926AE106A3E32369` (`user_id`),
  CONSTRAINT `FK926AE1061D381B6B` FOREIGN KEY (`jobback_id`) REFERENCES `jobback` (`id`),
  CONSTRAINT `FK926AE10656AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FK926AE106A3E32369` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jobback_user
-- ----------------------------

-- ----------------------------
-- Table structure for `log`
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `other_id` int(11) DEFAULT NULL,
  `result` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK12B2456AA4031` (`operator_id`),
  CONSTRAINT `FK12B2456AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log
-- ----------------------------

-- ----------------------------
-- Table structure for `mailbox`
-- ----------------------------
DROP TABLE IF EXISTS `mailbox`;
CREATE TABLE `mailbox` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK94C0209456AA4031` (`operator_id`),
  KEY `FK94C02094A3E32369` (`user_id`),
  CONSTRAINT `FK94C0209456AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FK94C02094A3E32369` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mailbox
-- ----------------------------

-- ----------------------------
-- Table structure for `mailboxreply`
-- ----------------------------
DROP TABLE IF EXISTS `mailboxreply`;
CREATE TABLE `mailboxreply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `mailbox_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKFF943EB656AA4031` (`operator_id`),
  KEY `FKFF943EB650255D4B` (`mailbox_id`),
  KEY `FKFF943EB6A3E32369` (`user_id`),
  CONSTRAINT `FKFF943EB650255D4B` FOREIGN KEY (`mailbox_id`) REFERENCES `mailbox` (`id`),
  CONSTRAINT `FKFF943EB656AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FKFF943EB6A3E32369` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mailboxreply
-- ----------------------------

-- ----------------------------
-- Table structure for `mailbox_user`
-- ----------------------------
DROP TABLE IF EXISTS `mailbox_user`;
CREATE TABLE `mailbox_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT b'0',
  `is_read` bit(1) DEFAULT b'0',
  `operator_id` int(11) DEFAULT NULL,
  `mailbox_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKF6876C7656AA4031` (`operator_id`),
  KEY `FKF6876C7650255D4B` (`mailbox_id`),
  KEY `FKF6876C76A3E32369` (`user_id`),
  CONSTRAINT `FKF6876C7650255D4B` FOREIGN KEY (`mailbox_id`) REFERENCES `mailbox` (`id`),
  CONSTRAINT `FKF6876C7656AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FKF6876C76A3E32369` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mailbox_user
-- ----------------------------

-- ----------------------------
-- Table structure for `menu`
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(255) DEFAULT NULL,
  `updateTime` varchar(255) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `display` varchar(255) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  `targetUrl` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK24897F37C61AA6` (`parent_id`) USING BTREE,
  KEY `FK24897F56AA4031` (`operator_id`),
  CONSTRAINT `FK24897F56AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '2017-05-18 15:44:10', '2017-05-18 15:51:35', 'admin', '&#xe62e;', '系统管理', '1', '', null, null);
INSERT INTO `menu` VALUES ('2', null, null, 'menu', null, '菜单管理', '3', 'static/back/menu.html', '1', null);
INSERT INTO `menu` VALUES ('3', null, null, 'role', null, '权限管理', '2', 'back/role/list', '1', null);
INSERT INTO `menu` VALUES ('4', null, null, null, '&#xe62c;', '用户管理', '2', null, null, null);
INSERT INTO `menu` VALUES ('5', null, null, null, null, 'APP用户管理', '1', 'back/user/list', '4', null);
INSERT INTO `menu` VALUES ('7', null, null, null, '&#xe68c;', '资讯管理', '3', null, null, null);
INSERT INTO `menu` VALUES ('8', null, null, null, '&#xe613;', '轮播图管理', '1', 'back/carousel/list', '7', null);
INSERT INTO `menu` VALUES ('9', null, null, null, null, '党建要闻管理', '2', 'back/article/list?dictId=djyw', '7', null);
INSERT INTO `menu` VALUES ('10', null, null, null, '&#xe690;', '考试管理', '4', null, null, null);
INSERT INTO `menu` VALUES ('11', null, '2017-07-11 09:30:04', 'operator', '', '操作员管理', '1', 'back/operator/list', '1', null);
INSERT INTO `menu` VALUES ('12', null, null, null, null, '三会一课管理', '3', 'back/article/list?dictId=shyk', '7', null);
INSERT INTO `menu` VALUES ('13', null, null, null, null, '两学一做管理', '4', 'back/article/list?dictId=lxyz', '7', null);
INSERT INTO `menu` VALUES ('14', null, '2017-09-11 15:30:04', '5', '', '廉政建设管理', '5', 'back/article/list?dictId=lzjs', '7', null);
INSERT INTO `menu` VALUES ('15', null, null, null, null, '通知公告管理', '6', 'back/article/list?dictId=tzgg', '7', null);
INSERT INTO `menu` VALUES ('16', null, null, null, null, '理论学习管理', '7', 'back/article/list?dictId=llxx', '7', null);
INSERT INTO `menu` VALUES ('17', null, null, null, null, '工作规范管理', '8', 'back/article/list?dictId=gzgf', '7', null);
INSERT INTO `menu` VALUES ('18', null, null, null, null, '试题管理', '1', 'back/questions/page', '10', null);
INSERT INTO `menu` VALUES ('19', null, null, null, null, '试卷管理', '2', 'back/exam/page', '10', null);
INSERT INTO `menu` VALUES ('20', null, null, null, null, '考试成绩管理', '3', 'back/score/page', '10', null);
INSERT INTO `menu` VALUES ('21', null, null, null, '&#xe628;', '党费管理', '5', null, null, null);
INSERT INTO `menu` VALUES ('22', null, null, null, '&#xe6c6;', '党务日历管理', '6', 'back/calendar/list', null, null);
INSERT INTO `menu` VALUES ('23', null, null, null, '&#xe643;', '组织管理', '7', null, null, null);
INSERT INTO `menu` VALUES ('24', null, null, null, '&#xe70c;', '展示汇报管理', '8', null, null, null);
INSERT INTO `menu` VALUES ('25', null, null, null, '&#xe6cf;', '评议投票管理', '9', null, null, null);
INSERT INTO `menu` VALUES ('26', null, null, null, '&#xe6b5;', '积分管理', '10', null, null, null);
INSERT INTO `menu` VALUES ('27', null, null, null, '&#xe6cd;', '待办事项管理', '11', null, null, null);
INSERT INTO `menu` VALUES ('28', null, null, null, '&#xe6c5;', '系统消息管理', '12', null, null, null);
INSERT INTO `menu` VALUES ('29', null, null, null, null, '组织架构管理', '1', 'back/org/page', '23', null);
INSERT INTO `menu` VALUES ('30', null, null, null, null, '党员信息管理', '2', 'back/partymember/page', '23', null);
INSERT INTO `menu` VALUES ('31', null, null, null, null, '工作展示管理', '1', 'back/job/list', '24', null);
INSERT INTO `menu` VALUES ('32', null, null, null, null, '思想汇报管理', '2', 'back/report/list', '24', null);
INSERT INTO `menu` VALUES ('33', null, null, null, null, '意见反馈管理', '3', 'back/advice/list', '24', null);
INSERT INTO `menu` VALUES ('34', null, null, null, null, '民主评议管理', '1', 'back/appr/page', '25', null);
INSERT INTO `menu` VALUES ('35', null, null, null, null, '在线投票管理', '2', 'back/vote/page', '25', null);
INSERT INTO `menu` VALUES ('36', '2017-05-25 09:39:31', '2017-05-25 09:39:31', 'calendar', '', '党务日历管理', '1', 'back/calendar/page', '22', null);
INSERT INTO `menu` VALUES ('37', '2017-05-27 16:15:20', '2017-05-27 16:15:20', 'backlog', '', '待办事项管理', '1', 'back/backlog/page', '27', null);
INSERT INTO `menu` VALUES ('38', '2017-05-31 15:10:59', '2017-05-31 15:10:59', 'dues', '', '党费管理', '0', 'back/dues/page', '21', null);
INSERT INTO `menu` VALUES ('40', '2017-06-02 17:52:48', '2017-06-02 17:53:33', 'message', '', '系统消息管理', '1', 'back/message/page', '28', null);
INSERT INTO `menu` VALUES ('41', '2017-06-06 15:45:55', '2017-06-06 15:45:55', 'points', '', '积分管理', '0', 'back/points/info', '26', null);
INSERT INTO `menu` VALUES ('42', '2017-09-18 20:59:58', '2017-09-18 20:59:58', 'room', '', '会议室成员管理', '2', 'back/room/page', '4', null);
INSERT INTO `menu` VALUES ('43', '2017-09-21 17:08:40', '2017-09-21 17:08:40', 'file', '', '资料下载', '13', 'back/file/page', null, null);
INSERT INTO `menu` VALUES ('44', null, null, null, null, '资料下载', null, 'back/file/page', '43', null);

-- ----------------------------
-- Table structure for `message`
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL,
  `org` varchar(255) DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  `org_list` varchar(255) DEFAULT NULL,
  `title` varchar(20) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9C2397E756AA4031` (`operator_id`),
  CONSTRAINT `FK9C2397E756AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for `note`
-- ----------------------------
DROP TABLE IF EXISTS `note`;
CREATE TABLE `note` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `content` longtext,
  `title` varchar(50) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK25241256AA4031` (`operator_id`),
  CONSTRAINT `FK25241256AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of note
-- ----------------------------

-- ----------------------------
-- Table structure for `operator`
-- ----------------------------
DROP TABLE IF EXISTS `operator`;
CREATE TABLE `operator` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `account` varchar(20) DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKE6048CC456AA4031` (`operator_id`),
  KEY `FKE6048CC4A86A1BCB` (`org_id`),
  KEY `FKE6048CC4E736A71` (`role_id`),
  CONSTRAINT `FKE6048CC456AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FKE6048CC4A86A1BCB` FOREIGN KEY (`org_id`) REFERENCES `org` (`id`),
  CONSTRAINT `FKE6048CC4E736A71` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of operator
-- ----------------------------
INSERT INTO `operator` VALUES ('1', '2017-10-11 16:24:05', '2017-10-11 16:24:05', 'admin', '0', 'admin', 'a66abb5684c45962d887564f08346e8d', null, '1', '1');

-- ----------------------------
-- Table structure for `options`
-- ----------------------------
DROP TABLE IF EXISTS `options`;
CREATE TABLE `options` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `choice` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `evaluate` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `radio` tinyint(1) NOT NULL,
  `title` varchar(50) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `vote` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK18BF1E7E56AA4031` (`operator_id`),
  KEY `FK18BF1E7EACDFEF63` (`vote`),
  CONSTRAINT `FK18BF1E7E56AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FK18BF1E7EACDFEF63` FOREIGN KEY (`vote`) REFERENCES `vote` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of options
-- ----------------------------

-- ----------------------------
-- Table structure for `org`
-- ----------------------------
DROP TABLE IF EXISTS `org`;
CREATE TABLE `org` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `changeDate` datetime DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK132C456AA4031` (`operator_id`),
  KEY `FK132C45FF02445` (`parent_id`),
  CONSTRAINT `FK132C456AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FK132C45FF02445` FOREIGN KEY (`parent_id`) REFERENCES `org` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of org
-- ----------------------------
INSERT INTO `org` VALUES ('1', '2017-10-11 16:25:28', '2017-10-11 16:25:28', null, '001', '0', '优谱德', '1', null);

-- ----------------------------
-- Table structure for `org_user`
-- ----------------------------
DROP TABLE IF EXISTS `org_user`;
CREATE TABLE `org_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4E5DA44656AA4031` (`operator_id`),
  KEY `FK4E5DA446A86A1BCB` (`org_id`),
  KEY `FK4E5DA446A3E32369` (`user_id`),
  CONSTRAINT `FK4E5DA44656AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FK4E5DA446A3E32369` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK4E5DA446A86A1BCB` FOREIGN KEY (`org_id`) REFERENCES `org` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of org_user
-- ----------------------------

-- ----------------------------
-- Table structure for `party_membership_dues`
-- ----------------------------
DROP TABLE IF EXISTS `party_membership_dues`;
CREATE TABLE `party_membership_dues` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `amount` double NOT NULL DEFAULT '0',
  `fee_received` double NOT NULL DEFAULT '0',
  `pay_type` varchar(5) DEFAULT NULL,
  `status` bit(1) DEFAULT b'0',
  `trade_no` varchar(255) DEFAULT NULL,
  `year` varchar(7) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6F78C46F56AA4031` (`operator_id`),
  KEY `FK6F78C46FACDF2125` (`user`),
  CONSTRAINT `FK6F78C46F56AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FK6F78C46FACDF2125` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of party_membership_dues
-- ----------------------------

-- ----------------------------
-- Table structure for `questions`
-- ----------------------------
DROP TABLE IF EXISTS `questions`;
CREATE TABLE `questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `exam_type` int(11) DEFAULT NULL,
  `options` varchar(255) DEFAULT NULL,
  `options_result` varchar(50) DEFAULT NULL,
  `score` double(4,2) NOT NULL,
  `title` varchar(200) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK95C5414D56AA4031` (`operator_id`),
  CONSTRAINT `FK95C5414D56AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of questions
-- ----------------------------

-- ----------------------------
-- Table structure for `report`
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `subject` varchar(50) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK91B1415456AA4031` (`operator_id`),
  KEY `FK91B14154ACDF2125` (`user`),
  CONSTRAINT `FK91B1415456AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FK91B14154ACDF2125` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report
-- ----------------------------

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK26F49656AA4031` (`operator_id`),
  CONSTRAINT `FK26F49656AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '2017-05-18 19:22:30', '2017-07-11 11:20:10', '超级管理员', null);

-- ----------------------------
-- Table structure for `role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `Role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  KEY `FK8B67BB88E736A71` (`Role_id`) USING BTREE,
  KEY `FK14042788F504D5D1` (`menu_id`) USING BTREE,
  CONSTRAINT `role_menu_ibfk_1` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`),
  CONSTRAINT `role_menu_ibfk_2` FOREIGN KEY (`Role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('1', '1');
INSERT INTO `role_menu` VALUES ('1', '11');
INSERT INTO `role_menu` VALUES ('1', '3');
INSERT INTO `role_menu` VALUES ('1', '2');
INSERT INTO `role_menu` VALUES ('1', '4');
INSERT INTO `role_menu` VALUES ('1', '5');
INSERT INTO `role_menu` VALUES ('1', '42');
INSERT INTO `role_menu` VALUES ('1', '7');
INSERT INTO `role_menu` VALUES ('1', '8');
INSERT INTO `role_menu` VALUES ('1', '9');
INSERT INTO `role_menu` VALUES ('1', '12');
INSERT INTO `role_menu` VALUES ('1', '13');
INSERT INTO `role_menu` VALUES ('1', '14');
INSERT INTO `role_menu` VALUES ('1', '15');
INSERT INTO `role_menu` VALUES ('1', '16');
INSERT INTO `role_menu` VALUES ('1', '17');
INSERT INTO `role_menu` VALUES ('1', '10');
INSERT INTO `role_menu` VALUES ('1', '18');
INSERT INTO `role_menu` VALUES ('1', '19');
INSERT INTO `role_menu` VALUES ('1', '20');
INSERT INTO `role_menu` VALUES ('1', '21');
INSERT INTO `role_menu` VALUES ('1', '38');
INSERT INTO `role_menu` VALUES ('1', '22');
INSERT INTO `role_menu` VALUES ('1', '36');
INSERT INTO `role_menu` VALUES ('1', '23');
INSERT INTO `role_menu` VALUES ('1', '29');
INSERT INTO `role_menu` VALUES ('1', '30');
INSERT INTO `role_menu` VALUES ('1', '24');
INSERT INTO `role_menu` VALUES ('1', '31');
INSERT INTO `role_menu` VALUES ('1', '32');
INSERT INTO `role_menu` VALUES ('1', '33');
INSERT INTO `role_menu` VALUES ('1', '25');
INSERT INTO `role_menu` VALUES ('1', '34');
INSERT INTO `role_menu` VALUES ('1', '35');
INSERT INTO `role_menu` VALUES ('1', '26');
INSERT INTO `role_menu` VALUES ('1', '41');
INSERT INTO `role_menu` VALUES ('1', '27');
INSERT INTO `role_menu` VALUES ('1', '37');
INSERT INTO `role_menu` VALUES ('1', '28');
INSERT INTO `role_menu` VALUES ('1', '40');

-- ----------------------------
-- Table structure for `room`
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `group_id` varchar(30) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `name` varchar(10) DEFAULT NULL,
  `owner` varchar(12) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK26F4FB56AA4031` (`operator_id`),
  KEY `FK26F4FBA86A1BCB` (`org_id`),
  CONSTRAINT `FK26F4FB56AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FK26F4FBA86A1BCB` FOREIGN KEY (`org_id`) REFERENCES `org` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of room
-- ----------------------------

-- ----------------------------
-- Table structure for `room_user`
-- ----------------------------
DROP TABLE IF EXISTS `room_user`;
CREATE TABLE `room_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `room_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC06174AF56AA4031` (`operator_id`),
  KEY `FKC06174AFFEE64969` (`room_id`),
  KEY `FKC06174AFA3E32369` (`user_id`),
  CONSTRAINT `FKC06174AF56AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FKC06174AFA3E32369` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKC06174AFFEE64969` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of room_user
-- ----------------------------

-- ----------------------------
-- Table structure for `score`
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `pass` bit(1) NOT NULL DEFAULT b'0',
  `value` double(5,2) NOT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `exam_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4C04E7256AA4031` (`operator_id`),
  KEY `FK4C04E725DC5D2E9` (`exam_id`),
  KEY `FK4C04E72A3E32369` (`user_id`),
  CONSTRAINT `FK4C04E7256AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FK4C04E725DC5D2E9` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`),
  CONSTRAINT `FK4C04E72A3E32369` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of score
-- ----------------------------

-- ----------------------------
-- Table structure for `track`
-- ----------------------------
DROP TABLE IF EXISTS `track`;
CREATE TABLE `track` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `subject` varchar(100) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4D5012B56AA4031` (`operator_id`),
  KEY `FK4D5012BACDF2125` (`user`),
  CONSTRAINT `FK4D5012B56AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FK4D5012BACDF2125` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of track
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `account` varchar(20) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `award` varchar(1000) DEFAULT NULL,
  `birth` datetime DEFAULT NULL,
  `branchRank` int(11) DEFAULT NULL,
  `committeeRank` int(11) DEFAULT NULL,
  `contact` varchar(20) DEFAULT NULL,
  `contactMobile` varchar(11) DEFAULT NULL,
  `duty` varchar(30) DEFAULT NULL,
  `education` int(11) DEFAULT NULL,
  `employeeNumber` varchar(20) DEFAULT NULL,
  `groupRank` int(11) DEFAULT NULL,
  `idCard` varchar(18) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `job` varchar(30) DEFAULT NULL,
  `leader` bit(1) NOT NULL DEFAULT b'0',
  `nation` varchar(10) DEFAULT NULL,
  `nativePlace` varchar(20) DEFAULT NULL,
  `nickname` varchar(20) DEFAULT NULL,
  `partyTime` datetime DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `points` int(11) NOT NULL DEFAULT '0',
  `punishment` varchar(1000) DEFAULT NULL,
  `sex` varchar(2) DEFAULT NULL,
  `train` varchar(1000) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `highest_org` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK285FEB56AA4031` (`operator_id`),
  KEY `FK285FEB1836903E` (`highest_org`),
  CONSTRAINT `FK285FEB1836903E` FOREIGN KEY (`highest_org`) REFERENCES `org` (`id`),
  CONSTRAINT `FK285FEB56AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for `vote`
-- ----------------------------
DROP TABLE IF EXISTS `vote`;
CREATE TABLE `vote` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  `org_list` varchar(255) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `voter` varchar(1500) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK28C70A56AA4031` (`operator_id`),
  CONSTRAINT `FK28C70A56AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of vote
-- ----------------------------

-- ----------------------------
-- Table structure for `worktask`
-- ----------------------------
DROP TABLE IF EXISTS `worktask`;
CREATE TABLE `worktask` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5EDE45656AA4031` (`operator_id`),
  KEY `FK5EDE456A3E32369` (`user_id`),
  CONSTRAINT `FK5EDE45656AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FK5EDE456A3E32369` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of worktask
-- ----------------------------

-- ----------------------------
-- Table structure for `worktaskreply`
-- ----------------------------
DROP TABLE IF EXISTS `worktaskreply`;
CREATE TABLE `worktaskreply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `workTask_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4E94843456AA4031` (`operator_id`),
  KEY `FK4E948434A3E32369` (`user_id`),
  KEY `FK4E94843452B55029` (`workTask_id`),
  CONSTRAINT `FK4E94843452B55029` FOREIGN KEY (`workTask_id`) REFERENCES `worktask` (`id`),
  CONSTRAINT `FK4E94843456AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FK4E948434A3E32369` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of worktaskreply
-- ----------------------------

-- ----------------------------
-- Table structure for `worktask_user`
-- ----------------------------
DROP TABLE IF EXISTS `worktask_user`;
CREATE TABLE `worktask_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `worktask_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA841B65456AA4031` (`operator_id`),
  KEY `FKA841B654A3E32369` (`user_id`),
  KEY `FKA841B65452B55029` (`worktask_id`),
  CONSTRAINT `FKA841B65452B55029` FOREIGN KEY (`worktask_id`) REFERENCES `worktask` (`id`),
  CONSTRAINT `FKA841B65456AA4031` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FKA841B654A3E32369` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of worktask_user
-- ----------------------------
