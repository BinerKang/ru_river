CREATE TABLE `t_score_info` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `score` int(8) DEFAULT NULL COMMENT '分数',
  `user_id` int(20) DEFAULT NULL,
  `type` varchar(2) DEFAULT '1' COMMENT '游戏类型(1:弹球)',
  `created_time` timestamp NULL DEFAULT NULL,
  `created_by` int(20) DEFAULT NULL,
  `updated_time` timestamp NULL DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;