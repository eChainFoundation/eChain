SET FOREIGN_KEY_CHECKS=0;
DROP DATABASE IF EXISTS echain;
CREATE DATABASE echain DEFAULT CHARSET 'utf8';

USE echain;

/*==============================================================*/
/* Table: `ec_user`                                   */
/* 用户基本信息表							*/
/* 创建时间:2018-03-08                                          */
/*==============================================================*/
DROP TABLE IF EXISTS `ec_user`;
CREATE TABLE `ec_user` (
  `id`                BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_state`        CHAR(2) NOT NULL DEFAULT '1' COMMENT '用户积分状态 1-正常，2-积分异常',
  `country`           CHAR(2) NOT NULL DEFAULT '0' COMMENT '用户所在地：0-中国，1-其他',
  `phone_number`      VARCHAR(11) NOT NULL COMMENT '联系方式，手机号码',
  `password`          VARCHAR(100) CHARACTER SET utf8 COMMENT '登陆密码',
  `create_time`       DATETIME NOT NULL COMMENT '创建时间',

  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_phone_number` (`phone_number`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='用户基本信息表';


/*==============================================================*/
/* Table: `ec_user_points`                                   */
/* 用户积分信息表							*/
/* 创建时间:2018-03-08                                          */
/*==============================================================*/
DROP TABLE IF EXISTS `ec_user_points`;
CREATE TABLE `ec_user_points` (
  `id`                BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_id`           BIGINT(20) NOT NULL COMMENT '用户ID',
  `now_points`        VARCHAR(100) DEFAULT NULL COMMENT '账户现有积分',
  `freeze_points`     VARCHAR(100) DEFAULT NULL COMMENT '账户目前冻结的积分',
  `consume_points`    VARCHAR(100) DEFAULT NULL COMMENT '账户已消费的积分',
  `all_points`        VARCHAR(100) DEFAULT NULL COMMENT '账户拥有的所有积分',
  `create_time`       DATETIME NOT NULL COMMENT '创建时间',

  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='用户积分信息表';


/*==============================================================*/
/* Table: `ec_user_wallet`                                   */
/* 用户钱包地址表							*/
/* 创建时间:2018-03-08                                          */
/*==============================================================*/
DROP TABLE IF EXISTS `ec_user_wallet`;
CREATE TABLE `ec_user_wallet` (
  `id`                BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_id`        	  BIGINT(20) NOT NULL COMMENT '用户ID',
  `wallet`            VARCHAR(100) DEFAULT NULL COMMENT '钱包地址',
  `type`      		  CHAR(2) NOT NULL DEFAULT '1' COMMENT '1-以太坊，2-EOS',
  `create_time`       DATETIME NOT NULL COMMENT '创建时间',

  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_wallet` (`wallet`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='用户钱包地址表';


/*==============================================================*/
/* Table: `ec_dapp`                                   */
/* dapp信息表					  */	
/* 创建时间:2018-03-08                                          */
/*==============================================================*/
DROP TABLE IF EXISTS `ec_dapp`;
CREATE TABLE `ec_dapp` (
  `id`                BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dapp_name`	      VARCHAR(100) CHARACTER SET utf8 NOT NULL COMMENT 'dapp名',
  `dapp_logo`	      VARCHAR(500) CHARACTER SET utf8 NOT NULL COMMENT 'dapp logo',
  `dapp_url`     	  VARCHAR(200) CHARACTER SET utf8 NOT NULL COMMENT 'dapp链接地址',
  `describe_text`     TEXT COMMENT 'dapp描述信息',
  `status`            CHAR(1) NOT NULL DEFAULT '1' COMMENT '状态，1-正常，0-禁用',
  `create_time`       DATETIME NOT NULL COMMENT '创建时间',

  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='dapp信息表';


/*==============================================================*/
/* Table: `ec_user_dapp`                                   */
/* 用户 dapp 关联表	  */	
/* 创建时间:2018-03-08                                          */
/*==============================================================*/
DROP TABLE IF EXISTS `ec_user_dapp`;
CREATE TABLE `ec_user_dapp` (
  `id`                BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id`      	  BIGINT(20) NOT NULL COMMENT '用户ID',
  `dapp_id`      	  BIGINT(20) NOT NULL COMMENT 'dapp ID',
  `points`      	  BIGINT(20) NOT NULL DEFAULT 0 COMMENT '该用户在这个dapp重获取的积分',
  `create_time`       DATETIME NOT NULL COMMENT '创建时间',

  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_dapp` (`user_id`,`dapp_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='用户 dapp 关联表';

/*==============================================================*/
/* Table: `ec_points`                                   */
/* 积分详情表	  */	
/* 创建时间:2018-03-08                                          */
/*==============================================================*/
DROP TABLE IF EXISTS `ec_points`;
CREATE TABLE `ec_points` (
  `id`                BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id`      	  BIGINT(20) NOT NULL COMMENT '用户ID',
  `dapp_id`      	  BIGINT(20) NOT NULL COMMENT 'dapp ID',
  `user_dapp_id`      BIGINT(20) NOT NULL DEFAULT 0 COMMENT '该用户在这个dapp重获取的积分,0-从echain中获取的',
  `transaction_id`    BIGINT(20) NOT NULL DEFAULT 0 COMMENT '获取积分的交易记录ID，0-表示积分不是通过交易获取到的',
  `type`    		  CHAR(1) NOT NULL DEFAULT 1 COMMENT '积分的类型，0-消费积分，1-得到积分',
  `describe_text`     VARCHAR(5000) COMMENT '获取积分详情描述',
  `create_time`       DATETIME NOT NULL COMMENT '创建时间',

  PRIMARY KEY (`id`),
  KEY `index_user_id` (`user_id`),
  KEY `index_dapp_id` (`dapp_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='产品属性表';

/*==============================================================*/
/* Table: `ec_points_pool`                                   */
/* 积分池表	  */	
/* 创建时间:2018-03-08                                          */
/*==============================================================*/
DROP TABLE IF EXISTS `ec_points_pool`;
CREATE TABLE `ec_points_pool` (
  `id`                BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `now_points`        VARCHAR(100) DEFAULT NULL COMMENT '现有总积分',
  `freeze_points`     VARCHAR(100) DEFAULT NULL COMMENT '目前冻结的总积分',
  `consume_points`    VARCHAR(100) DEFAULT NULL COMMENT '已消费的总积分',
  `all_points`        VARCHAR(100) DEFAULT NULL COMMENT '拥有的所有积分',
  `create_time`       DATETIME NOT NULL COMMENT '创建时间',

  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='产品属性表';


/*==============================================================*/
/* Table: `ec_transaction`                                   */
/* 交易详情表	         			         */
/* 创建时间:2018-03-08                                          */
/*==============================================================*/
DROP TABLE IF EXISTS `ec_transaction`;
CREATE TABLE `ec_transaction` (
  `id`                  BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id`      	    BIGINT(20) NOT NULL COMMENT '用户ID',
  `dapp_id`      	    BIGINT(20) NOT NULL COMMENT 'dapp ID',
  `user_dapp_id`        BIGINT(20) NOT NULL DEFAULT 0 COMMENT '该用户在这个dapp中的交易,0-从echain中上传的交易',
  `transaction_platform`  VARCHAR(100) COMMENT '订单交易平台，例如：淘宝，京东等',
  `transaction_no`      VARCHAR(100) COMMENT '订单编号',
  `transaction_picture` VARCHAR(100) COMMENT '订单照片路径',
  `logistics_company`   VARCHAR(100) DEFAULT NULL COMMENT '物流公司',
  `logistics_no`    	VARCHAR(100) DEFAULT NULL COMMENT '物流编号',
  `describe_text`       TEXT NOT NULL COMMENT '交易详情',
  `describe_md5`        CHAR(32) NOT NULL COMMENT '交易详情MD5',
  `status`	        CHAR(1) NOT NULL DEFAULT 0 COMMENT '交易状态，0-未审核，1-审核通过，2-审核不通过',
  `error_msg`	        VARCHAR(1000) DEFAULT NULL COMMENT '交易不通过的原因描述',
  `create_time`         DATETIME NOT NULL COMMENT '创建时间',
 
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`user_id`),
  KEY `index_dapp_id` (`dapp_id`),
  KEY `index_transaction_no` (`transaction_no`),
  KEY `index_logistics_no` (`logistics_no`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='交易详情表';

/*==============================================================*/
/* Table: `ec_logistics_record`                                       */
/* 物流记录信息表					*/	
/* 创建时间:2018-03-08                                          */
/*==============================================================*/
DROP TABLE IF EXISTS `ec_logistics_record`;
CREATE TABLE `ec_logistics_record` (
  `id`                		BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `logistics_company`       VARCHAR(100) NOT NULL COMMENT '物流公司',
  `logistics_no`    		VARCHAR(100) NOT NULL COMMENT '物流编号',
  `logistics_text`          TEXT NOT NULL COMMENT '物流详情描述',
  `logistics_md5`           CHAR(32) NOT NULL COMMENT '物流详情MD5',
  `create_time`      		DATETIME NOT NULL COMMENT '创建时间',

  PRIMARY KEY (`id`),
  KEY `index_logistics_no` (`logistics_no`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='物流记录信息表';



