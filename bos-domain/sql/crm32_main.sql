CREATE DATABASE crm32;
USE crm32;
CREATE TABLE `t_customer`(
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) DEFAULT NULL,
    `station` VARCHAR(50) DEFAULT NULL,
    `telephone` VARCHAR(50) DEFAULT NULL,
    `location` VARCHAR(255) DEFAULT NULL,
    `decidedzone_id` INT(11) DEFAULT NULL,
    PRIMARY KEY(`id`)
)AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO `t_customer` VALUES ('1', '张三', '百度', '13811111111', '北京市西城区长安街100号', NULL);
INSERT INTO `t_customer` VALUES ('2', '李四', '哇哈哈', '13822222222', '上海市虹桥区南京路250号', NULL);
INSERT INTO `t_customer` VALUES ('3', '王五', '搜狗', '13533333333', '天津市河北区中山路30号', NULL);
INSERT INTO `t_customer` VALUES ('4', '赵六', '联想', '18633333333', '石家庄市桥西区和平路10号', NULL);
INSERT INTO `t_customer` VALUES ('5', '小白', '测试空间', '18511111111', '内蒙古自治区呼和浩特市和平路100号', NULL);
INSERT INTO `t_customer` VALUES ('6', '小黑', '联想', '13722222222', '天津市南开区红旗路20号', NULL);
INSERT INTO `t_customer` VALUES ('7', '小花', '百度', '13733333333', '北京市东城区王府井大街20号', NULL);
INSERT INTO `t_customer` VALUES ('8', '小李', '长城', '13788888888', '北京市昌平区建材城西路100号', NULL);