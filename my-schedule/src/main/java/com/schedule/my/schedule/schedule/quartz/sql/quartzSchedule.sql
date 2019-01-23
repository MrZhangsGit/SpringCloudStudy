CREATE TABLE `schedule_task` (
  `id` int(50) NOT NULL AUTO_INCREMENT COMMENT '数据自增Id',
  `cron` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;