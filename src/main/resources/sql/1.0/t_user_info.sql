CREATE TABLE `t_user_info` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(20) DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `mail` varchar(40) DEFAULT NULL COMMENT '邮箱地址',
  `validate` tinyint(1) DEFAULT NULL COMMENT '是否认证了邮箱(0:false,1:true)',
  `created_time` timestamp NULL DEFAULT NULL,
  `created_by` int(20) DEFAULT NULL,
  `updated_time` timestamp NULL DEFAULT NULL,
  `updated_by` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
