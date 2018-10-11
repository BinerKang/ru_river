CREATE TABLE `t_article_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `introduction` text COMMENT '简介',
  `image` varchar(120) DEFAULT NULL COMMENT '图片地址',
  `author` varchar(30) DEFAULT NULL COMMENT '作者',
  `publish_time` timestamp NULL DEFAULT NULL COMMENT '发表时间',
  `link` varchar(200) DEFAULT NULL COMMENT '链接',
  `category` int(2) NOT NULL COMMENT '类别(1:学习;2:体育;3:娱乐)',
  `created_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` bigint(20) DEFAULT '0' COMMENT '创建人(0:系统)',
  `updated_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` bigint(20) DEFAULT '0' COMMENT '更新人(0:系统)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
