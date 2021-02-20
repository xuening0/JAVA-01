CREATE TABLE IF NOT EXISTS `t_app` (
 `id` bigint(20) NOT NULL COMMENT '主键ID',
  `status` int(11) DEFAULT '1' COMMENT '状态：1新建，5使用中',
  PRIMARY KEY (`id`)
);
