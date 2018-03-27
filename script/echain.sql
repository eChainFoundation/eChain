SET FOREIGN_KEY_CHECKS=0;
DROP DATABASE IF EXISTS echain;
CREATE DATABASE echain DEFAULT CHARSET 'utf8';

USE echain;


/*==============================================================*/
/* Table: `ec_logistics_record`                                       */
/* 物流记录信息表					*/	
/* 创建时间:2018-03-08                                          */
/*==============================================================*/
DROP TABLE IF EXISTS `ec_logistics_record`;
CREATE TABLE `ec_logistics_record` (
  `id`                		BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `logistics_company_id`    BIGINT(20) NOT NULL COMMENT '物流公司id',
  `transaction_id`          BIGINT(20) NOT NULL COMMENT '交易id',
  `product_name`        	VARCHAR(100) COMMENT '产品名',
  `optioner_name`     		VARCHAR(100) NOT NULL COMMENT '操作人名',
  `option_content`            	VARCHAR(1000) NOT NULL COMMENT '操作内容',
  `describe_text`          		TEXT COMMENT '描述',
  `create_time`      		DATETIME NOT NULL COMMENT '创建时间',

  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='物流记录信息表';
