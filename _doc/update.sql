-- 更新的sql

-- 添加任务详情字段
ALTER TABLE `task_details`
ADD COLUMN `click_number`  int(20) NULL DEFAULT 0 COMMENT '点击次数' AFTER `make_list_number`;


-- 添加同城引流字段
ALTER TABLE `task_details`
ADD COLUMN `city`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '同城引流-城市名称' AFTER `click_number`;


-- 修改执行时间长度
ALTER TABLE `task`
MODIFY COLUMN `task_expect_running`  tinyint(20) NULL DEFAULT 0 COMMENT '任务运行时间' AFTER `task_start_time`;

ALTER TABLE `task`
MODIFY COLUMN `task_expect_running`  int(20) NULL DEFAULT 0 COMMENT '任务运行时间' AFTER `task_start_time`;


-- 修改礼物格子数据类型
ALTER TABLE `task_details`
MODIFY COLUMN `gift_box`  varchar(255) NULL DEFAULT NULL COMMENT '礼物格子数 ( no1 ......)' AFTER `gift_number`;
ALTER TABLE `task_details`
MODIFY COLUMN `gift_box`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '礼物格子数 ( no1 ......)' AFTER `gift_number`;

-- 添加是否属于超级热度详情字段
ALTER TABLE `task`
ADD COLUMN `is_live_hot`  int(1) NULL DEFAULT 0 COMMENT '0不属于超级热度和超级热度下的任务,1属于' AFTER `comment_interval`;

-- 添加文件记录历史表
CREATE TABLE `NewTable` (
`id`  int NOT NULL AUTO_INCREMENT COMMENT '文件名称' ,
`version`  int(255) NULL DEFAULT NULL COMMENT '版本号' ,
`name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名称' ,
`state`  int(20) NULL DEFAULT NULL COMMENT '启用状态  0 未启用 1 启用' ,
`data_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件地址' ,
`remake`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注' ,
`create_time`  datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci
COMMENT='文件上传历史记录表'
;





-- 添加上传图片生成的二维码地址字段
ALTER TABLE `biz_apk_history`
ADD COLUMN `qr_code_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '二维码图片地址' AFTER `state`;




-- 添加设备更新类型字段
ALTER TABLE `task_details`
ADD COLUMN `apk_upgrade`  int(2) NULL DEFAULT NULL COMMENT '设备更新类型 1 版本更新  2 设备更新' AFTER `city`;

-- 超级热度子任务uuid
ALTER TABLE `live_hot_sub_task`
ADD COLUMN `uuid`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'uuid' AFTER `id`;
ALTER TABLE `live_hot_sub_task`
ADD COLUMN `content`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '私信文本内容' AFTER `click_number`;


-- 添加设备更新类型字段
ALTER TABLE `task_details`
ADD COLUMN `uuid`  varchar(100) NULL DEFAULT NULL COMMENT 'uuid' AFTER `del_flag`;

-- 添加设备更新类型字段
ALTER TABLE `task_details`
ADD COLUMN `creator`  varchar(20) NULL DEFAULT NULL COMMENT '作者' AFTER `uuid`;

-- 添加设备更新类型字段
ALTER TABLE `task_details`
ADD COLUMN `updator`  varchar(20) NULL DEFAULT NULL COMMENT '更新的作者' AFTER `creator`;

-- 任务详情表添加备注字段
ALTER TABLE `task_details`
ADD COLUMN `remarks`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注' AFTER `update_time`;

ALTER TABLE `live_hot_sub_task`
ADD COLUMN `remarks`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注' AFTER `content`;

ALTER TABLE `live_hot_sub_task`
ADD COLUMN `creator`  varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作者' AFTER `content`;

ALTER TABLE `live_hot_sub_task`
ADD COLUMN `updator`  varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者' AFTER `content`;

-- 超级热度子任务添加字段
ALTER TABLE `live_hot_sub_task`
ADD COLUMN `creator`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人' AFTER `comment_interval`,
ADD COLUMN `updator`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人' AFTER `creator`;

ALTER TABLE `sys_admin`
ADD COLUMN `code`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邀请码' AFTER `parent_id`;

ALTER TABLE `equipment`
ADD COLUMN `sys_admin_code`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邀请码' AFTER `state`;

ALTER TABLE `comment_template`
ADD COLUMN `type` int(1) NULL DEFAULT 0 COMMENT '是否是默认话术0 不是 1 是' AFTER `open`;

ALTER TABLE `equipment_group`
ADD COLUMN `state` int(1) NULL DEFAULT 0 COMMENT '是否是默认分组0 不是 1 是' AFTER `name`;

ALTER TABLE `sys_admin`
ADD COLUMN `ports` int(10) NULL DEFAULT 0 COMMENT '代理商最多设备数量' AFTER `code`;

CREATE TABLE `sys_admin_port` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `sys_admin_id` int(12) DEFAULT NULL COMMENT '代理商用户表的id',
  `start_port` int(12) DEFAULT NULL COMMENT '开始的端口数量',
  `update_port` int(12) DEFAULT NULL COMMENT '更新后端口数量',
  `create_time` datetime DEFAULT NULL COMMENT ' 创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `uuid` int(2) DEFAULT NULL COMMENT 'uuid ',
  `del_flag` int(10) DEFAULT NULL COMMENT '删除标记',
  `creator` varchar(10) DEFAULT NULL COMMENT '作者',
  `updator` varchar(10) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

ALTER TABLE `sys_admin_port`
ADD COLUMN `operat_admin` int(12) NULL DEFAULT null COMMENT '操作者的用户id' AFTER `id`;


-- 添加任务归属用户字段
ALTER TABLE `task`
ADD COLUMN `sys_admin_id`  int(10) NULL DEFAULT null COMMENT '用户表的连接' AFTER `task_start_time`;

-- 添加任务归属用户字段
ALTER TABLE `comment_template`
ADD COLUMN `sys_admin_id`  int(10) NULL DEFAULT null COMMENT '用户表的连接' AFTER `symbol`;

-- 添加任务归属用户字段
ALTER TABLE `task`
ADD COLUMN `task_name`  varchar (255) NULL DEFAULT null COMMENT '任务名称' AFTER `task_run_code`;

--添加私信的内容
ALTER TABLE `task_details`
ADD COLUMN `number` int(20) NULL DEFAULT 0 COMMENT '私信的个数' AFTER `comments`;

--添加私信方式
ALTER TABLE `task_details`
ADD COLUMN `letter_type`  varchar (50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT null COMMENT '私信的方式 letterAdmin私信用户letterFans私信粉丝' AFTER `number`;

-- 添加解析任务字段
ALTER TABLE `task`
ADD COLUMN `analysis_content`  varchar (255) NULL DEFAULT null COMMENT '解析的content地址' AFTER `live_in_content`;

-- add at 2020.9.22 start --
-- 任务发送状态
ALTER TABLE `task_details`
    ADD COLUMN `send_state` tinyint(4) DEFAULT 0 COMMENT '任务发送状态 0未发送 1已发送' AFTER `update_time`;
ALTER TABLE `live_hot_sub_task`
    ADD COLUMN `send_state` tinyint(4) DEFAULT 0 COMMENT '任务发送状态 0未发送 1已发送' AFTER `update_time`;
-- 任务发送时间
ALTER TABLE `live_hot_sub_task`
    ADD COLUMN `send_time` datetime(0) NULL COMMENT '任务发送时间' AFTER `update_time`;
ALTER TABLE `task_details`
    ADD COLUMN `send_time` datetime(0) NULL COMMENT '任务发送时间' AFTER `update_time`;
-- 任务发送耗时
ALTER TABLE `live_hot_sub_task`
    ADD COLUMN `send_took_millis` int(11) NULL COMMENT '任务发送耗时(毫秒)' AFTER `update_time`;
ALTER TABLE `task_details`
    ADD COLUMN `send_took_millis` int(11) NULL COMMENT '任务发送耗时(毫秒)' AFTER `update_time`;
-- add at 2020.9.22 end --

-- add at 2020.9.26 start --
ALTER TABLE `equipment`
    ADD COLUMN `channel_id` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '设备的通道id' AFTER `device_uid`;
-- add at 2020.9.26 end --

-- 自动养号需要
ALTER TABLE `task_details`
ADD COLUMN `key_word`  text NULL COMMENT '关键字' AFTER `send_took_millis`;



ALTER TABLE `task`
    ADD COLUMN `platform` varchar(255) NULL COMMENT '平台名称 有tiktok、快手等' AFTER `del_flag`;
ALTER TABLE `task_detail`
    ADD COLUMN `platform` varchar(255) NULL COMMENT '平台名称 有tiktok、快手等' AFTER `del_flag`;
ALTER TABLE `live_hot_sub_task`
    ADD COLUMN `platform` varchar(255) NULL COMMENT '平台名称 有tiktok、快手等' AFTER `del_flag`;


-- 修改系统配置字段长度
ALTER TABLE sys_conf
MODIFY COLUMN `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '名称对应的值' AFTER `nid`;