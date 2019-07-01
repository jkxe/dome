-- 格式如下:
--修改日期：
--修改内容：
--修改人：

--2017-09-14 qijigui 增加联系人关系 同学
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('219', '0015', NULL, '同学', '9');

--2017-09-14 huyanmin 增加公司序列号 sequence
ALTER TABLE company ADD sequence VARCHAR(4) DEFAULT NULL COMMENT '公司序列号';

--2017-09-15 huyanmin 增加新的权限码
INSERT INTO `resource` VALUES ('769', '176', '催大人', '公司名称', '080108', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 769);
INSERT INTO `resource` VALUES ('770', '239', '催大人', '批量管理', '090506', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 770);
INSERT INTO `resource` VALUES ('771', '243', '催大人', '公司名称', '080206', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 771);
INSERT INTO `resource` VALUES ('772', '239', '催大人', '数据导入批量', '090507', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 772);
INSERT INTO `resource` VALUES ('773', '184', '催大人', '系统公告', '0909FF', NULL, NULL, NULL, NULL, NULL, 18, NULL, NULL, NULL, NULL, 773);
INSERT INTO `resource` VALUES ('774', '773', '催大人', '用户名', '090901', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 774);
INSERT INTO `resource` VALUES ('775', '773', '催大人', '姓名', '090902', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 775);
INSERT INTO `resource` VALUES ('776', '773', '催大人', '状态', '090903', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 776);
INSERT INTO `resource` VALUES ('777', '773', '催大人', '全员发布公告', '090904', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 777);
INSERT INTO `resource` VALUES ('778', '773', '催大人', '发布公告', '090905', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 778);

--2017-09-19 huyanmin 删除权限码
DELETE from resource where id=708
--2017-09-19 huyanmin 增加新的权限码
INSERT INTO `resource` VALUES ('779', '74', '催大人', '结案案件', '040AFF', NULL, NULL, NULL, NULL, NULL, 18, NULL, NULL, NULL, NULL, 779);
INSERT INTO `resource` VALUES ('780', '779', '催大人', '客户姓名', '040A01', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 780);
INSERT INTO `resource` VALUES ('781', '779', '催大人', '手机号', '040A02', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 781);
INSERT INTO `resource` VALUES ('782', '779', '催大人', '申请省份', '040A03', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 782);
INSERT INTO `resource` VALUES ('783', '779', '催大人', '申请城市', '040A04', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 783);
INSERT INTO `resource` VALUES ('784', '779', '催大人', '批次号', '040A05', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 784);
INSERT INTO `resource` VALUES ('785', '779', '催大人', '还款状态', '040A06', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 785);
INSERT INTO `resource` VALUES ('786', '779', '催大人', '逾期天数', '040A07', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 786);
INSERT INTO `resource` VALUES ('787', '779', '催大人', '案件金额', '040A08', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 787);
INSERT INTO `resource` VALUES ('788', '779', '催大人', '案件手数', '040A09', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 788);
INSERT INTO `resource` VALUES ('789', '779', '催大人', '佣金比例', '040A10', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 789);
INSERT INTO `resource` VALUES ('790', '779', '催大人', '委托方', '040A11', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 790);
INSERT INTO `resource` VALUES ('791', '779', '催大人', '是否协催', '040A12', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 791);
INSERT INTO `resource` VALUES ('792', '779', '催大人', '催收反馈', '040A13', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 792);
INSERT INTO `resource` VALUES ('793', '779', '催大人', '协催方式', '040A14', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 793);
INSERT INTO `resource` VALUES ('794', '779', '催大人', '催收类型', '040A15', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 794);
INSERT INTO `resource` VALUES ('795', '779', '催大人', '结案删除', '040A16', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 795);
INSERT INTO `resource` VALUES ('796', '779', '催大人', '跟进记录', '040A17', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 796);
INSERT INTO `resource` VALUES ('797', '779', '催大人', '案件流转记录', '040A18', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 797);

--2017-09-20 huyanmin 增加新的权限码
INSERT INTO `resource` VALUES ('798', '107', '催大人', '案件退案', '040429', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 798);
INSERT INTO `resource` VALUES ('799', '74', '催大人', '退回案件', '040BFF', NULL, NULL, NULL, NULL, NULL, 18, NULL, NULL, NULL, NULL, 799);
INSERT INTO `resource` VALUES ('800', '799', '催大人', '客户姓名', '040B01', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 800);
INSERT INTO `resource` VALUES ('801', '799', '催大人', '手机号', '040B02', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 801);
INSERT INTO `resource` VALUES ('802', '799', '催大人', '申请省份', '040B03', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 802);
INSERT INTO `resource` VALUES ('803', '799', '催大人', '申请城市', '040B04', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 803);
INSERT INTO `resource` VALUES ('804', '799', '催大人', '批次号', '040B05', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 804);
INSERT INTO `resource` VALUES ('805', '799', '催大人', '还款状态', '040B06', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 805);
INSERT INTO `resource` VALUES ('806', '799', '催大人', '逾期天数', '	040B07', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 806);
INSERT INTO `resource` VALUES ('807', '799', '催大人', '案件金额', '040B08', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 807);
INSERT INTO `resource` VALUES ('808', '799', '催大人', '案件手数', '040B09', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 808);
INSERT INTO `resource` VALUES ('809', '799', '催大人', '佣金比例', '040B10', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 809);
INSERT INTO `resource` VALUES ('810', '799', '催大人', '委托方', '040B11', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 810);
INSERT INTO `resource` VALUES ('811', '799', '催大人', '是否协催', '040B12', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 811);
INSERT INTO `resource` VALUES ('812', '799', '催大人', '催收反馈', '040B13', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 812);
INSERT INTO `resource` VALUES ('813', '799', '催大人', '协催方式', '040B14', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 813);
INSERT INTO `resource` VALUES ('814', '799', '催大人', '催收类型', '040B15', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 814);
INSERT INTO `resource` VALUES ('815', '799', '催大人', '跟进记录', '040B16', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 815);

--2017-09-25 胡开甲 增加系统参数
INSERT INTO `sys_param` (`id`, `company_code`, `code`, `name`, `status`, `type`, `value`, `sign`, `operator`, `operate_time`, `remark`, `field`) VALUES ('ff8080815dfe341a797e0043da6f0007', '0001', 'Sysparam.mysqlbackup.address', 'mysql备份数据库脚本位置', '0', '9001', '/data/mysqlscript/mysqlbackup.sh', '0', 'administrator', '2017-09-21 14:36:06', 'mysql备份数据库脚本位置', NULL);
INSERT INTO `sys_param` (`id`, `company_code`, `code`, `name`, `status`, `type`, `value`, `sign`, `operator`, `operate_time`, `remark`, `field`) VALUES ('ff8080815dfe341a797e0043da6f0008', '0001', 'Sysparam.mysqlrecover.address', 'mysql数据库恢复脚本位置', '0', '9001', '/data/mysqlscript/mysqlrecover.sh', '0', 'administrator', '2017-09-21 14:38:24', 'mysql数据库恢复脚本位置', NULL);
INSERT INTO `sys_param` (`id`, `company_code`, `code`, `name`, `status`, `type`, `value`, `sign`, `operator`, `operate_time`, `remark`, `field`) VALUES ('ff8080815dfe341a797e0043da6f0009', '0001', 'Sysparam.mongodbbackup.address', 'mongodb备份数据库脚本位置', '0', '9001', '/data/mongoscript/mongdbbackup.sh', '0', 'administrator', '2017-09-22 15:35:52', 'mongodb备份数据库脚本位置', NULL);
INSERT INTO `sys_param` (`id`, `company_code`, `code`, `name`, `status`, `type`, `value`, `sign`, `operator`, `operate_time`, `remark`, `field`) VALUES ('ff8080815dfe341a797e0043da6f0010', '0001', 'Sysparam.mongodbrecover.address', 'mongodb数据库恢复脚本位置', '0', '9001', '/data/mongoscript/mongdbrecover.sh', '0', 'administrator', '2017-09-22 15:35:55', 'mongodb数据库恢复脚本位置', NULL);

--2017-09-26 祁吉贵 增加案件池类型
INSERT INTO `pangolin_business`.`data_dict_type` (`id`, `code`, `name`) VALUES ('50', '0050', '案件池类型');

INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('225', '0050', NULL, '内催', '0');
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('226', '0050', NULL, '委外', '1');
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('227', '0050', NULL, '司法', '2');
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('228', '0050', NULL, '核销', '3');


--2017-09-25 huyanmin 新增导入跟进记录参数
INSERT INTO `sys_param` VALUES ('ff8080815dfe341a797e0043da6f0066', '0001', 'sys.outcase.followup', '委外案件跟进记录导入模版', 0, '9005', 'http://192.168.3.10:9000/file-service/api/fileUploadController/view/59fad7cc0f25c0362c83cd63.xlsx', 0, 'administrator', '2017-9-25 19:10:20', '委外案件跟进记录导入模版', NULL);
--2017-09-25 huyanmin 在委外池中新增3个字段
ALTER TABLE `outsource_pool` ADD COLUMN `company_code` varchar(64) DEFAULT NULL COMMENT '公司特定标识';
ALTER TABLE `outsource_pool` ADD COLUMN `over_outsource_time` date DEFAULT NULL COMMENT '委外到期时间';
ALTER TABLE `outsource_pool` ADD COLUMN `end_outsource_time` date DEFAULT NULL COMMENT '已结案日期';
ALTER TABLE `outsource_pool`
MODIFY COLUMN `contract_amt`  decimal(18,4) NULL DEFAULT NULL COMMENT '案件总金额' AFTER `overdue_periods`;
ALTER TABLE `outsource_pool`
MODIFY COLUMN `commission_rate`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '佣金比例' AFTER `out_batch`;
ALTER TABLE `outsource_pool`
MODIFY COLUMN `commission`  bigint(100) NULL DEFAULT NULL COMMENT '佣金' AFTER `commission_rate`;

--2017-09-27 huyanmin 增加新的权限码
INSERT INTO `resource` VALUES ('816', '156', '催大人', '导出还款明细', '06020C', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 816);
INSERT INTO `resource` VALUES ('817', '156', '催大人', '按钮', '06020D', NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL, NULL, 817);

--2017-09-28 孙艳平 增加数据字典项
INSERT INTO `data_dict_type` (`id`, `code`, `name`) VALUES ('51', '0051', '策略类型');
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('230', '0051', NULL, '导入案件分配策略', '0');
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('231', '0051', NULL, '内催池案件分配策略', '1');
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('232', '0051', NULL, '委外池案件分配策略', '2');

--2017-09-29 胡艳敏 修改委外池中佣金比例名
ALTER TABLE `outsource_pool`
CHANGE COLUMN `commissionRate` `commission_rate`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `out_batch`;

--2017-09-29 胡艳敏 修改数据字典168-催收中
UPDATE `pangolin_business`.`data_dict` SET `name`='催收中' WHERE (`id`='168');
UPDATE `pangolin_business`.`data_dict` SET `name`='已结案'  WHERE (`id`='170');

--2017-10-09 胡艳敏 增加案件跟踪记录类型
ALTER TABLE `case_followup_record`
ADD COLUMN `case_followup_type`  int(4) DEFAULT NULL COMMENT '跟踪记录类型（内催、委外、司法、核销)';
ADD COLUMN `follow_time`  date DEFAULT NULL COMMENT '跟进时间';
ADD COLUMN `follow_person`  varchar(64) DEFAULT NULL COMMENT '跟进人员';

--2017-10-11 baizhangyu 近期sql修改记录整理
INSERT INTO `data_dict` VALUES (229, '0038', NULL, 'BeauPhone语音卡', 3);
INSERT INTO `sys_param` VALUES ('ff8080815dfe341a797e0043da6f0013', '0001', 'SysParam.bfyuyin.url', 'BeauPhone语音卡参数地址', 0, '9001', '192.168.3.79:8080', 0, 'administrator', '2017-9-29 10:46:40', NULL, NULL);
ALTER TABLE `user`
ADD COLUMN `channel_no`  varchar(64) NULL COMMENT '通道号码';
ALTER TABLE `user`
ADD COLUMN `zoneno`  varchar(10) NULL COMMENT '主叫电话区号';
ALTER TABLE `case_followup_record`
ADD COLUMN `file_name`  varchar(200) NULL COMMENT '录音文件名称';
ALTER TABLE `case_followup_record`
ADD COLUMN `file_path`  varchar(100) NULL COMMENT '录音文件在服务器上的目录';


--2017-10-13 孙艳平 案件回收表修改
ALTER TABLE `case_info_return`
DROP COLUMN `outsource_id`,
ADD COLUMN `outs_name`  varchar(100) NULL DEFAULT NULL COMMENT '委外方名称' AFTER `source`,
ADD COLUMN `out_time`  datetime NULL DEFAULT NULL COMMENT '委外日期' AFTER `outs_name`,
ADD COLUMN `over_outsource_time`  date NULL DEFAULT NULL COMMENT '委外结案日期' AFTER `out_time`,
ADD COLUMN `out_batch`  varchar(64) NULL DEFAULT NULL COMMENT '委外批次号' AFTER `over_outsource_time`,
ADD COLUMN `company_code`  varchar(64) NULL DEFAULT NULL COMMENT '公司Code' AFTER `out_batch`;

--2017-10-14 祁吉贵 增加了M0 配置了顺序
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('190', '0043', NULL, 'M1', '1');
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('191', '0043', NULL, 'M2', '2');
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('192', '0043', NULL, 'M3', '3');
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('193', '0043', NULL, 'M4', '4');
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('194', '0043', NULL, 'M5', '5');
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('195', '0043', NULL, 'M6+', '6');
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('244', '0043', NULL, 'M0', '0');

--2017-10-16 孙艳平 增加系统参数
INSERT INTO `pangolin_business`.`sys_param` (`id`, `company_code`, `code`, `name`, `status`, `type`, `value`, `sign`, `operator`, `operate_time`, `remark`, `field`) VALUES (UUID(), '0001', 'Sysparam.recover', '案件到期批量回收、回收提醒调度时间', '0', '9001', '000100', '0', 'administrator', '2017-10-16 13:45:51', '案件到期批量回收、提醒调度时间', NULL);
INSERT INTO `pangolin_business`.`sys_param` (`id`, `company_code`, `code`, `name`, `status`, `type`, `value`, `sign`, `operator`, `operate_time`, `remark`, `field`) VALUES (UUID(), '0001', 'Sysparam.recover.status', '案件到期批量回收、回收提醒状态', '0', '9001', '0', '0', 'administrator', '2017-10-16 11:37:13', '案件到期批量处理状态0-启用 1-停用', NULL);

--2017-10-17 白章宇 增加字段
ALTER TABLE `user_device`
ADD COLUMN `mac`  varchar(64) NULL COMMENT 'MAC地址' AFTER `field`;

--2017-10-17 孙艳平 resource增加权限
INSERT INTO `pangolin_business`.`resource` (`id`, `pid`, `sys_name`, `name`, `code`, `level`, `status`, `path`, `icon`, `type`, `file_type`, `remark`, `operator`, `operate_time`, `field`, `flag`) VALUES ('510', '484', '崔大人', '删除案件', '10030E', NULL, NULL, NULL, NULL, NULL, '19', NULL, NULL, NULL, NULL, '510');
INSERT INTO `pangolin_business`.`resource` (`id`, `pid`, `sys_name`, `name`, `code`, `level`, `status`, `path`, `icon`, `type`, `file_type`, `remark`, `operator`, `operate_time`, `field`, `flag`) VALUES ('511', '497', '催大人', '删除案件', '100409', NULL, NULL, NULL, NULL, NULL, '19', NULL, NULL, NULL, NULL, '511');
INSERT INTO `pangolin_business`.`resource` (`id`, `pid`, `sys_name`, `name`, `code`, `level`, `status`, `path`, `icon`, `type`, `file_type`, `remark`, `operator`, `operate_time`, `field`, `flag`) VALUES ('594', '526', '催大人', '删除案件', '0F0510', NULL, NULL, NULL, NULL, NULL, '19', NULL, NULL, NULL, NULL, '594');
INSERT INTO `pangolin_business`.`resource` (`id`, `pid`, `sys_name`, `name`, `code`, `level`, `status`, `path`, `icon`, `type`, `file_type`, `remark`, `operator`, `operate_time`, `field`, `flag`) VALUES ('595', '525', '催大人', '删除案件', '0F0412', NULL, NULL, NULL, NULL, NULL, '19', NULL, NULL, NULL, NULL, '595');

--2017-10-19 祁吉贵 增加催收机构
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('245', '0036', 'C', '催收机构', '4');

--2017-10-24 白章宇 增加系统参数
INSERT INTO `sys_param` VALUES ('ff8080815dfe341a797e0043da6f00016', '0001', 'Sysparam.revokedistribute', '案件分案撤销时长', 0, '9001', '30', 0, 'administrator', '2017-10-17 18:41:15', '案件分案撤销时长(分钟)', '1');

--2017-10-30 祁吉贵 增加撤销案件权限码
INSERT INTO `pangolin_business_test`.`resource` VALUES ('439', '390', '催大人', '撤销分案', '0406FF', NULL, NULL, NULL, NULL, NULL, '18', NULL, NULL, NULL, NULL, '439');
INSERT INTO `pangolin_business_test`.`resource` VALUES ('440', '439', '催大人', '客户姓名', '040601', NULL, NULL, NULL, NULL, NULL, '19', NULL, NULL, NULL, NULL, '440');
INSERT INTO `pangolin_business_test`.`resource` VALUES ('441', '439', '催大人', '案件编号', '040602', NULL, NULL, NULL, NULL, NULL, '19', NULL, NULL, NULL, NULL, '441');
INSERT INTO `pangolin_business_test`.`resource` VALUES ('442', '439', '催大人', '批次号', '040603', NULL, NULL, NULL, NULL, NULL, '19', NULL, NULL, NULL, NULL, '442');
INSERT INTO `pangolin_business_test`.`resource` VALUES ('443', '439', '催大人', '委托方', '040604', NULL, NULL, NULL, NULL, NULL, '19', NULL, NULL, NULL, NULL, '443');
INSERT INTO `pangolin_business_test`.`resource` VALUES ('444', '439', '催大人', '原催收员', '040605', NULL, NULL, NULL, NULL, NULL, '19', NULL, NULL, NULL, NULL, '444');
INSERT INTO `pangolin_business_test`.`resource` VALUES ('445', '439', '催大人', '当前催收员', '040606', NULL, NULL, NULL, NULL, NULL, '19', NULL, NULL, NULL, NULL, '445');
INSERT INTO `pangolin_business_test`.`resource` VALUES ('446', '439', '催大人', '分案时间', '040607', NULL, NULL, NULL, NULL, NULL, '19', NULL, NULL, NULL, NULL, '446');
INSERT INTO `pangolin_business_test`.`resource` VALUES ('447', '439', '催大人', '数据来源', '040608', NULL, NULL, NULL, NULL, NULL, '19', NULL, NULL, NULL, NULL, '447');
INSERT INTO `pangolin_business_test`.`resource` VALUES ('448', '439', '催大人', '案件金额', '040609', NULL, NULL, NULL, NULL, NULL, '19', NULL, NULL, NULL, NULL, '448');
INSERT INTO `pangolin_business_test`.`resource` VALUES ('449', '439', '催大人', '撤销分案', '040610', NULL, NULL, NULL, NULL, NULL, '19', NULL, NULL, NULL, NULL, '449');

--2017-10-30 白章宇 增加字段
ALTER TABLE `case_distributed_temporary`
ADD COLUMN `case_remark`  varchar(64) NULL COMMENT '案件备注ID';

--2017-10-30 胡艳敏 公司表中增加新字段
ALTER TABLE `company`
ADD COLUMN `register_day`  int(6) NULL DEFAULT NULL COMMENT '注册天数 null没有注册  99999表示无线注册' AFTER `sequence`;
--2017-10-30 胡艳敏 软件注册，增加新的参数
INSERT INTO `sys_param` (`id`, `company_code`, `code`, `name`, `status`, `type`, `value`, `sign`, `operator`, `operate_time`, `remark`, `field`) VALUES ('ff8080815dfe341a797e0043da6f0015', '0001', 'SysParam.registersoftware', '软件注册码', '0', '9001', '21218cca77804d2ba1922c33e0151105', '0', 'administrator', '2017-10-25 17:49:35', NULL, NULL);

--2017-10-30 白章宇 增加字段
ALTER TABLE `case_distributed_temporary`
ADD COLUMN `current_department_code`  varchar(64) NULL COMMENT '案件当前所在部门code码';

--2017-10-31 祁吉贵 增加受托方的机构类型

INSERT INTO `data_dict_type` (`id`, `code`, `name`) VALUES ('52', '0052', '受托方机构类型');

INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('246', '0052', 'P', '贷款公司', '1');
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('247', '0052', 'I', '保险公司', '2');
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('248', '0052', 'O', '其他', '4');
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('249', '0052', 'C', '催收机构', '3');

--2017-10-31
--新增数据字典项
--夏群
INSERT INTO `data_dict` VALUES ('253', '0008', null, '还款强制拒绝', '6');

--2017-10-31
--新增已结案设置导出项权限码
--胡艳敏
INSERT INTO `pangolin_business`.`resource` VALUES ('596', '525', '催大人', '设置导出项', '0F0413', NULL, NULL, NULL, NULL, NULL, '19', NULL, NULL, NULL, NULL, '596');
INSERT INTO `pangolin_business`.`resource` VALUES ('913', '858', '催大人', '操作时间', '090703', NULL, NULL, NULL, NULL, NULL, '19', NULL, NULL, NULL, NULL, '913');

--2017-11-06
--核销审批表案件来源字段
--袁艳婷
ALTER TABLE `case_info_verification_apply`
ADD COLUMN `source`  int(4) NULL DEFAULT NULL COMMENT '案件池来源' AFTER `commission_rate`;

--2017-11-06
--核销表添加打包状态字段
--袁艳婷
ALTER TABLE `case_info_verification`
DROP COLUMN `packing_status`,
ADD COLUMN `packing_status`  int(4) NULL DEFAULT NULL COMMENT '打包状态' AFTER `state`;

ALTER TABLE `case_info_verification`
ADD COLUMN `packing_status`  int(4) NULL COMMENT '打包状态' AFTER `state`;

--2017-11-14
--修改参数表名称
--胡艳敏
UPDATE `pangolin_business`.`sys_param` SET `name`='短信发送统计报表' WHERE name like '%报标';
UPDATE `pangolin_business`.`sys_param` SET `name`='协催申请失效天数(天)' WHERE name like '协催申请失效%';
UPDATE `pangolin_business`.`sys_param` SET `name`='消息提醒批量(时分秒)' WHERE name = '消息提醒批量';
UPDATE `pangolin_business`.`sys_param` SET `name`='案件到期批量回收、回收提醒调度时间(时分秒)' WHERE name = '案件到期批量回收、回收提醒调度时间';
UPDATE `pangolin_business`.`sys_param` SET `name`='案件分案撤销时长(分钟)' WHERE name = '案件分案撤销时长';

--2017-11-15
--司法表增加说明字段
--袁艳婷
ALTER TABLE `case_info_judicial`
ADD COLUMN `state`  varchar(255) NULL AFTER `company_code`;



--2017-12-07
--还款申请表增加部门Code
--祁吉贵
ALTER TABLE `case_pay_apply`
ADD COLUMN `dept_code`  varchar(128) NULL COMMENT '部门code' AFTER `case_amt`;


--2017-12-07
--电催提前流转表 修改depart_id 为 dept_code
--彭长须
ALTER TABLE `case_advance_turn_applay`
change `depart_id` `dept_code` varchar(64) DEFAULT NULL COMMENT '部门Code';


--2017-12-07
--协催表添加外访协催审批人的部门code
--胡艳敏
ALTER TABLE `case_assist`
ADD COLUMN `dept_code`  varchar(128) NULL COMMENT '外访协催审批人的部门code' AFTER `assist_close_flag`;

--2017-12-07
--case_info_verification_apply 部门Code
--彭长须
ALTER TABLE `case_info_verification_apply`
ADD COLUMN `dept_code`  varchar(128) NULL COMMENT '部门code' AFTER `case_number`;

--2017-12-07
--case_assist_apply 修改depart_id 为 dept_code
--彭长须
ALTER TABLE `case_assist_apply`
change `depart_id` `dept_code` varchar(64) DEFAULT NULL COMMENT '部门Code';

--2017-12-10
--修改委外outsource_commission表中字段的repair_households保留两位小数，与其他相同字段保持一致
--胡艳敏
ALTER TABLE `outsource_commission`
MODIFY COLUMN `repair_households`  decimal(10,2) NULL DEFAULT NULL COMMENT '修复户数' AFTER `repair_money`;

--2017-12-12
--增加系统参数显示不显示
--祁吉贵
ALTER TABLE `sys_param`
 ADD COLUMN is_show  int(4) NULL DEFAULT NULL COMMENT '0 展示 1 不展示' AFTER `style`;

--2017-12-15
--修改每期还款日字段类型
--夏群
ALTER TABLE `case_info`
MODIFY COLUMN `per_due_date`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '每期还款日' AFTER `periods`;

ALTER TABLE `case_info_distributed`
MODIFY COLUMN `per_due_date`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '每期还款日' AFTER `periods`;

ALTER TABLE `case_info_exception`
MODIFY COLUMN `per_due_date`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '每期还款日' AFTER `periods`;

ALTER TABLE `case_info_history`
MODIFY COLUMN `per_due_date`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '每期还款日' AFTER `periods`;

--2018-01-08
--同步AI功能
--祁吉贵
ALTER TABLE `user`
ADD COLUMN `age`  int(2) NULL DEFAULT NULL COMMENT '年龄' AFTER `field`,
ADD COLUMN `work_age`  int(2) NULL DEFAULT NULL COMMENT '工作年龄' AFTER `age`,
ADD COLUMN `residence`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地址' AFTER `work_age`,
ADD COLUMN `nation`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族' AFTER `residence`;


CREATE TABLE `case_info_learning` (
  `id` varchar(64) NOT NULL,
  `case_number` varchar(64) DEFAULT NULL COMMENT '案件编号',
  `machine_learning_result` mediumtext COMMENT '分析结果',
  `model_type` varchar(64) DEFAULT NULL COMMENT '分析类型',
  `operator` varchar(64) DEFAULT NULL COMMENT '操作人',
  `operator_time` datetime DEFAULT NULL COMMENT '操作时间',
  `company_code` varchar(64) DEFAULT NULL COMMENT '公司code',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--2017-12-29
--系统参数表增加数据
--袁艳婷
INSERT INTO `pangolin_business`.`sys_param` (`id`, `company_code`, `code`, `name`, `status`, `type`, `value`, `sign`, `operator`, `operate_time`, `remark`, `field`) VALUES (UUID(), '0001', 'SysParam.caseinfo.analysis', '是否启用智能云分析功能', '0', '9001', '1', '0', 'administrator', '2017-10-12 20:26:22', '0-案件数据不传输，1-案件数据传输', '');
INSERT INTO `pangolin_business`.`sys_param` (`id`, `company_code`, `code`, `name`, `status`, `type`, `value`, `sign`, `operator`, `operate_time`, `remark`, `field`) VALUES (UUID(), '0001', 'SysParam.caseinfo.caseAnalysis', '实时案件分析', '0', '9001', 'http://103.20.248.234:8888/api/case_ml_analysis', '0', 'administrator', '2017-10-12 20:26:22', '实时案件分析', '');
INSERT INTO `pangolin_business`.`sys_param` (`id`, `company_code`, `code`, `name`, `status`, `type`, `value`, `sign`, `operator`, `operate_time`, `remark`, `field`) VALUES (UUID(), '0001', 'SysParam.caseinfo.caseClosed', '已结案案件分析', '0', '9001', 'http://103.20.248.234:8888/api/history_case_datas', '1', 'administrator', '2017-10-12 20:26:22', '已结案案件分析', '');
INSERT INTO `pangolin_business`.`sys_param` (`id`, `company_code`, `code`, `name`, `status`, `type`, `value`, `sign`, `operator`, `operate_time`, `remark`, `field`) VALUES (UUID(), '0001', 'SysParam.caseinfo.caseHangling', '处理中案件分析', '0', '9001', 'http://103.20.248.234:8888/api/dealing_case_datas', '0', 'administrator', '2017-10-12 20:26:22', '处理中案件分析', '');
INSERT INTO `pangolin_business`.`sys_param` (`id`, `company_code`, `code`, `name`, `status`, `type`, `value`, `sign`, `operator`, `operate_time`, `remark`, `field`) VALUES (UUID(), '0001', 'SysParam.caseinfo.userAnalysis', '催员分析', '0', '9001', 'http://103.20.248.234:8888/api/user_datas', '0', 'administrator', '2017-10-12 20:26:22', '催员分析', '');
INSERT INTO `pangolin_business`.`sys_param` (`id`, `company_code`, `code`, `name`, `status`, `type`, `value`, `sign`, `operator`, `operate_time`, `remark`, `field`) VALUES (UUID(), '0001', 'SysParam.caseinfo.caseToDistribute','待分配案件分析', '0', '9001', 'http://103.20.248.234:8888/api/case_ml_allocation', '0', 'administrator', '2017-12-20 15:06:41', '待分配案件分析', '');

--2017-11-24
--用户表增加字段
--袁艳婷
ALTER TABLE `user`
ADD COLUMN `age`  int(2) NULL DEFAULT NULL COMMENT '年龄' AFTER `field`,
ADD COLUMN `work_age`  int(2) NULL DEFAULT NULL COMMENT '工作年龄' AFTER `age`,
ADD COLUMN `residence`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地址' AFTER `work_age`,
ADD COLUMN `nation`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族' AFTER `residence`;

--2017-11-24
--数据字典表里添加数据
--袁艳婷
INSERT INTO `pangolin_business`.`data_dict_type` (`id`, `code`, `name`) VALUES ('55', '0055', '性别');
INSERT INTO `pangolin_business`.`data_dict_type` (`id`, `code`, `name`) VALUES ('56', '0056', '民族');
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('256', '0055', NULL, '男', '0');
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('257', '0055', NULL, '女', '1');
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('258', '0056', NULL, '汉族', '0');
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('259', '0056', NULL, '壮族', '1');
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('260', '0056', NULL, '回族', '2');
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('261', '0056', NULL, '满族', '3');
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('262', '0056', NULL, '维吾尔族', '4');
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('263', '0056', NULL, '苗族', '5');
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`) VALUES ('264', '0056', NULL, '其他', '6');

--2018-02-27
--添加任务盒子表
--胡艳敏
CREATE TABLE `task_box` (
  `id` varchar(64) NOT NULL COMMENT '任务ID',
  `type` int(2) DEFAULT '0' COMMENT '任务类型（查看、导出 、导入、手工同步）',
  `begin_date` datetime DEFAULT NULL COMMENT '任务开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '任务结束时间',
  `task_status` int(2) DEFAULT '0' COMMENT '任务状态（未完成、完成、失败、未知）',
  `task_describe` varchar(150) DEFAULT NULL COMMENT '任务简述',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息，导出时可以放URL地址',
  `operator` varchar(64) DEFAULT NULL COMMENT '操作人',
  `operator_time` datetime DEFAULT NULL COMMENT '操作时间',
  `company_code` varchar(64) DEFAULT NULL COMMENT '公司码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务盒子';


--2018-03-20
--修改用户表民族字段类型
--段文武
ALTER TABLE `user`
MODIFY COLUMN `nation`  int(10) NULL DEFAULT NULL COMMENT '民族' AFTER `residence`;

-- 2018-06-13
-- 添加流程对应的表结构
-- 王昭
CREATE TABLE `flow_approval` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `node_id` varchar(64) DEFAULT NULL COMMENT '节点id',
  `case_number` varchar(64) DEFAULT NULL COMMENT '案件编号',
  `process_state` varchar(10) DEFAULT NULL COMMENT '流程状态（0、正常 1、结束  2、拒绝）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='审批表';

CREATE TABLE `flow_history` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `approval_id` varchar(64) DEFAULT NULL COMMENT '审批id',
  `task_id` varchar(64) DEFAULT NULL COMMENT '任务id',
  `node_id` varchar(64) DEFAULT NULL COMMENT '节点id',
  `case_number` varchar(64) DEFAULT NULL COMMENT '案件编号',
  `node_state` varchar(10) DEFAULT NULL COMMENT '审批状态',
  `node_opinion` varchar(200) DEFAULT NULL COMMENT '审批意见',
  `approval_time` datetime DEFAULT NULL COMMENT '审批时间',
  `approval_user` varchar(100) DEFAULT NULL COMMENT '审批人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='历史表';

CREATE TABLE `flow_node` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `task_id` varchar(64) DEFAULT NULL COMMENT '任务id',
  `role_id` varchar(64) DEFAULT NULL COMMENT '角色id',
  `step` int(4) DEFAULT NULL COMMENT '当前步数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务节点表';

CREATE TABLE `flow_task` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `task_name` varchar(100) DEFAULT NULL COMMENT '任务名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务表';

ALTER TABLE case_assist_apply
ADD COLUMN `approval_id`  VARCHAR(64) NULL DEFAULT NULL COMMENT '审批流程id';


CREATE TABLE `case_record_apply` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `approval_id` varchar(64) DEFAULT NULL COMMENT '审批流程id',
  `case_id` varchar(64) DEFAULT NULL COMMENT '案件id',
  `source_type` int(4) DEFAULT NULL COMMENT '案件来源',
  `goal_type` int(4) DEFAULT NULL COMMENT '案件去向',
  `apply_user` varchar(50) DEFAULT NULL COMMENT '申请人',
  `apply_time` time DEFAULT NULL COMMENT '申请时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流转申请表';

ALTER TABLE case_record_apply
ADD COLUMN `approval_status`  int(4) NULL DEFAULT NULL COMMENT '审批状态(0、代审批，1、审批中，2、审批结束)';

INSERT INTO `sys_param` (`id`, `company_code`, `code`, `name`, `status`, `type`, `value`, `sign`, `operator`, `operate_time`, `remark`, `field`, `style`, `is_show`) VALUES ('18228439r574045difier398493rhf938', NULL, 'Sys.core.push.data.url', '核心推送逾期数据路径', '0', '9002', 'C:/Users/feng/Desktop/锦程项目/one.txt', '0', 'administrator', '2018-06-20 10:01:09', '核心推送逾期数据路径', NULL, '1', '0');

-- 2018-6-20
-- 修改数据字典
-- 陈京

INSERT INTO `data_dict_type` (`id`, `code`, `name`) VALUES ('254', '0254', '流转来源和去向');
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('816', '0254', NULL, '内催', '1', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('817', '0254', NULL, '电催', '2', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('818', '0254', NULL, '外访', '3', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('819', '0254', NULL, '委外', '4', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('820', '0254', NULL, '特殊', '5', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('821', '0254', NULL, '停催', '6', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('822', '0254', NULL, '贷后预警', '7', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('823', '0254', NULL, '内诉', '8', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('824', '0254', NULL, '反欺诈', '9', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('825', '0254', NULL, '核心系统', '10', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('826', '0254', NULL, 'Excel导入', '11', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('829', '0254', NULL, '外诉', '12', NULL);

-- 催收状态
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('800', '0004', NULL, '归C', '9', NULL);

--案件池类型
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('801', '0050', NULL, '特殊', '4', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('802', '0050', NULL, '停催', '5', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('803', '0050', NULL, '贷后预警', '6', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('804', '0050', NULL, '诉讼', '7', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('805', '0050', NULL, '反欺诈', '8', NULL);

-- 诉讼阶段
INSERT INTO `data_dict_type` (`id`, `code`, `name`) VALUES ('251', '0251', '诉讼阶段');
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('806', '0251', NULL, '初始', '1', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('807', '0251', NULL, '立案', '2', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('808', '0251', NULL, '开庭', '3', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('809', '0251', NULL, '公告', '4', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('827', '0251', NULL, '审批', '5', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('828', '0251', NULL, '执行', '6', NULL);

-- 诉讼类型
INSERT INTO `data_dict_type` (`id`, `code`, `name`) VALUES ('253', '0253', '诉讼类型');
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('814', '0253', NULL, '内部诉讼', '1', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('815', '0253', NULL, '外部诉讼', '2', NULL);

-- 反欺诈结果
INSERT INTO `data_dict_type` (`id`, `code`, `name`) VALUES ('255', '0255', '反欺诈结果');
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('830', '0255', NULL, '本人贷款', '1', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('831', '0255', NULL, '非本人贷款', '2', NULL);

-- case_info表修改

ALTER TABLE `case_info`change COLUMN `case_pool_type` `case_pool_type` int(4) NULL COMMENT '案件池类型（内催-225、委外-226、特殊-801、停催-802、贷后预警-803）' ;
ALTER TABLE `case_info`ADD COLUMN `turn_from_pool` int(4) NULL COMMENT '流转来源（816, 内催,817, 电催,818, 外访,819, 委外,820, 特殊,821, 停催,822, 贷后预警,823,诉讼,824,反欺诈,825,核心系统,826,Excel导入）' ;

--修改客户表增加客户类型枚举
ALTER TABLE `personal`ADD COLUMN `type` int(4) NULL COMMENT '客户类型（A类810 B类811 C类812 D类813 ）' ;
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('810', '0252', NULL, 'A类', '1', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('811', '0252', NULL, 'B类', '2', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('812', '0252', NULL, 'C类', '3', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('813', '0252', NULL, 'D类', '4', NULL);

-- 添加流转记录表字段
ALTER TABLE `case_turn_record`ADD COLUMN `turn_to_pool` int(4) NULL COMMENT '流转去向（内催816,委外819, 特殊820, 停催821, 内诉823,外诉829，反欺诈824）' ;
ALTER TABLE `case_turn_record`ADD COLUMN `turn_from_pool` int(4) NULL COMMENT '流转来源（电催817, 外访818, 819, 委外819, 特殊820, 停催821, 核心系统825,Excel导入826）' ;
ALTER TABLE `case_turn_record`ADD COLUMN `turn_describe` varchar(255) NULL COMMENT '流转说明';
ALTER TABLE `case_turn_record`ADD COLUMN `turn_approval_status` int(4) NULL COMMENT '流转审核状态（待审批-213，通过-214，拒绝-215）';
ALTER TABLE `case_turn_record`ADD COLUMN `approval_opinion` varchar(255) NULL COMMENT '审批意见';

-- 2018-6-21
-- 修改催记表
-- 陈京
ALTER TABLE `case_followup_record`ADD COLUMN `result_type` int(4) NULL COMMENT '反馈类型: 有效联络88，无效联络99' ;


-- 2018-6-22
-- 修改委外方表
-- 王昭
ALTER TABLE `outsource`
ADD COLUMN `outs_level`  int(4) NULL DEFAULT NULL COMMENT '等级',
ADD COLUMN `outs_rate`  DECIMAL(18,4) NULL DEFAULT NULL COMMENT '费率',
ADD COLUMN `contract_start_time`  datetime NULL DEFAULT NULL COMMENT '合同开始时间',
ADD COLUMN `contract_end_time`  datetime NULL DEFAULT NULL COMMENT '合同结束时间';


INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('832', '0039', NULL, '归C', '4', NULL);

-- 2018-6-24
-- 首次失联定义天数
insert into `sys_param` (`id`, `company_code`, `code`, `name`, `status`, `type`, `value`, `sign`, `operator`, `operate_time`, `remark`, `field`, `style`, `is_show`) values('2c918089606c741601606c8736c70058',NULL,'SysParam.invalid.contact.param','首次失联定义天数','0','9001','5','0','administrator','2018-06-24 16:01:09','首次失联定义天数',NULL,'1','0');

-- 2018-6-26
-- 电催 二级资源  归C
INSERT INTO `resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8', NULL, '', '归C案件', '2500', '18', '/call-acc/return-c-case', '1', '0');
-- 外访 二级资源  归C
INSERT INTO `resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('189', NULL, '', '归C案件', '3450', '18', '/visit-acc/visit-return-c-case', '181', '0');
-- 内催 二级资源  归C
INSERT INTO `resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('478', NULL, '', '归C案件', '12150', '18', '/inrushcase-manage/inrush-case-return-c', '461', '0');
-- 电催  三级资源  归C
INSERT INTO `resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('6100', '', '', '客户姓名', '1', '19', '', '8', '0');
INSERT INTO `resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('6101', '', '', '手机号', '2', '19', '', '8', '0');
INSERT INTO `resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('6102', '', '', '机构', '3', '19', '', '8', '0');
INSERT INTO `resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('6103', '', '', '催收员', '4', '19', '', '8', '0');
INSERT INTO `resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('6104', '', '', '委托方', '5', '19', '', '8', '0');
INSERT INTO `resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('6105', '', '', '批次号', '6', '19', '', '8', '0');
INSERT INTO `resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('6106', '', '', '逾期期数', '7', '19', '', '8', '0');
INSERT INTO `resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('6107', '', '', '身份证号', '8', '19', '', '8', '0');
INSERT INTO `resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('6108', '', '', '逾期天数', '9', '19', '', '8', '0');
INSERT INTO `resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('6109', '', '', '还款金额', '10', '19', '', '8', '0');
INSERT INTO `resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('6110', '', '', '案件金额', '11', '19', '', '8', '0');
INSERT INTO `resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('6111', '', '', '结案说明', '12', '19', '', '8', '0');
INSERT INTO `resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('6112', '', '', '跟进明细', '13', '19', '', '8', '0');

-- 案件查找 流转记录、首次失联
INSERT INTO `resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('373', NULL, '', '案件流转记录', '10101', '18', '/case-search/case-circulation-record', '371', '0');
INSERT INTO `resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('374', NULL, '', '案件首次失联查询', '10102', '18', '/case-search/first-lost-query', '371', '0');


INSERT INTO `resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('979', NULL, NULL, '流程配置', '21000', '18', '/system-manage/work-flow-manage', 851, '0');
-- 历史催记查询
INSERT INTO `resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('375', NULL, NULL, '历史催记查询', '10103', '18', '/case-search/search-history-reminders', 371, '0');
-- 2018-6-28
-- 催收反馈类型
-- 系统备份
INSERT INTO `data_dict_type` (`id`, `code`, `name`) VALUES ('257', '0257', '系统备注');
-- 有效联络
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('836', '0020', 'PTP', '承诺缴款,时效为2两天内能还款', '4', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('837', '0020', 'HPTP', '有困难，2、3天内可能会还款', '5', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('838', '0020', 'CAPT', '已经还款，资金在途', '6', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('839', '0020', 'OPTP', '第三人代缴（父母\\朋友\\联系代缴）', '7', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('840', '0020', 'CDLT', '资金有困难，需要时间周转，承诺时间长，超过3天，但要还款', '8', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('841', '0020', 'EYQK', '恶意欠款', '9', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('842', '0020', 'NOST', '本人拒绝沟通，不愿还款', '10', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('843', '0020', 'LMS', '第三人留言', '11', NULL);
-- 无效联络
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('90', '0021', 'OFF', '电话关机', '0', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('91', '0021', 'MST', '秘书台', '1', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('92', '0021', 'BUSY', '用户忙', '2', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('93', '0021', 'OUGT', '类似讲方言无法沟通，跟第三人沟通时下的CODE', '3', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('94', '0021', 'NOAS', '无人接听', '4', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('95', '0021', 'NOEX', '电话空号', '5', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('96', '0021', 'TNIS', '电话停机', '6', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('97', '0021', 'NOPK', '拒接接听', '7', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('98', '0021', 'NOCH', '查无此人', '8', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('99', '0021', 'YJJD', '一接就断，类似信号不好', '9', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('844', '0021', 'CHQJ', '客已离职', '10', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('845', '0021', 'WFJT', '电话无法接通', '11', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('846', '0021', 'DUDU', '电话嘟嘟', '12', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('847', '0021', 'STAP', '稍后联系', '13', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('848', '0021', 'CHLC', '无法联系，完全失联', '14', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('849', '0021', 'TSAJ', '客户死亡、刑拘、丧失民事行为能力、失踪等特殊案件', '15', NULL);
-- 系统备份
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('850', '0257', 'FAWU', '转诉讼', '0', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('851', '0257', 'FRAU', '转欺诈', '1', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('852', '0257', 'MEMO', '就是注记的意思，一般写长MEMO时所用的', '2', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('853', '0257', 'ZXJG', '记录征信查询结果', '3', NULL);

-- 2018-7-4
-- wangzhao
ALTER TABLE `personal`
ADD COLUMN `certificates_type`  int(4) NULL COMMENT '证件类型',
ADD COLUMN `certificates_number`  varchar(50) NULL COMMENT '证件号码',
ADD COLUMN `id_card_expirydate` date NULL DEFAULT NULL COMMENT '身份证到期时间',
ADD COLUMN `live_move_time`  date NULL COMMENT '现居住迁入时间',
ADD COLUMN `home_ownership` varchar(50) NULL DEFAULT NULL COMMENT '居住房屋所有权',
ADD COLUMN `children_number`  int(4) NULL COMMENT '子女人数',
ADD COLUMN `nationality`  varchar(50) NULL COMMENT '国籍';

ALTER TABLE `personal_bank`
ADD COLUMN `personal_source`  varchar(100) NULL COMMENT '客户来源',
ADD COLUMN `other_source`  varchar(100) NULL COMMENT '其他来源',
ADD COLUMN `referrer`  varchar(100) NULL COMMENT '推介人',
ADD COLUMN `introduction_bank`  varchar(100) NULL COMMENT '推介支行';

ALTER TABLE `personal_car`
ADD COLUMN `driver_number`  varchar(25) NULL COMMENT '驾照号';

ALTER TABLE `personal_income_exp`
ADD COLUMN `income_type`  int(4) NULL COMMENT '收入类型',
ADD COLUMN `fund_comp_mame`  varchar(50) NULL COMMENT '公积金缴存单位',
ADD COLUMN `fund_count`  int(4) NULL COMMENT '缴存期数（月）',
ADD COLUMN `fund_amt`  decimal(18,4) NULL COMMENT '个人公积金额度',
ADD COLUMN `social_security_amt`  decimal(18,4) NULL COMMENT '个人社保缴存额度',
ADD COLUMN `social_security_rato`  varchar(10) NULL COMMENT '社保缴存比例',
ADD COLUMN `subsidy_month`  decimal(18,4) NULL COMMENT '月固定补贴',
ADD COLUMN `family_income`  decimal(18,4) NULL COMMENT '家庭月收入',
ADD COLUMN `hosing_loan`  decimal(18,4) NULL COMMENT '房贷（月）/房租';

ALTER TABLE `personal_job`
ADD COLUMN `work_mother`  int(4) NULL COMMENT '工作时长（月）';

ALTER TABLE `personal_property`
ADD COLUMN `house_purchase_price`  decimal(18,4) NULL COMMENT '房屋购置价',
ADD COLUMN `house_ass_amt`  decimal(18,4) NULL COMMENT '房屋评估价',
ADD COLUMN `first_payment`  decimal(18,4) NULL COMMENT '首付金额',
ADD COLUMN `repayment_periods`  int(4) NULL COMMENT '房贷已还期数',
ADD COLUMN `total_periods`  int(4) NULL COMMENT '房贷总期数',
ADD COLUMN `month_payment_amount`  decimal(18,4) NULL COMMENT '房贷月均还款额';

ALTER TABLE `case_pay_apply`
ADD COLUMN `periods`  int(4) NULL COMMENT '期数',
ADD COLUMN `payment_voucher`  varchar(200) NULL COMMENT '还款凭证',
ADD COLUMN `current_status`  varchar(10) NULL COMMENT '当时状态';

ALTER TABLE `leave_case_apply`
ADD COLUMN `left_date`  datetime NULL COMMENT '结案时间';

ALTER TABLE `case_turn_record`ADD COLUMN `approval_id` varchar(64) NULL COMMENT '审批流程id';

ALTER TABLE `case_file`ADD COLUMN `file_code` varchar(64) NULL COMMENT '文件代码';

ALTER TABLE `pay_plan`
ADD COLUMN `pay_noun`  decimal(18,4) NULL COMMENT '应还复利',
ADD COLUMN `has_pay_principal`  decimal(18,4) NULL COMMENT '已偿还本金',
ADD COLUMN `has_pay_interest`  decimal(18,4) NULL COMMENT '已偿还利息',
ADD COLUMN `has_pay_noun`  decimal(18,4) NULL COMMENT '已偿还复利',
ADD COLUMN `has_pay_liquidated`  decimal(18,4) NULL COMMENT '已偿还违约金',
ADD COLUMN `has_pay_fine`  decimal(18,4) NULL COMMENT '已偿还罚息',
ADD COLUMN `has_pay_management`  decimal(18,4) NULL COMMENT '已偿还账号管理费',
ADD COLUMN `pay_amount`  decimal(18,4) NULL COMMENT '还款总额',
ADD COLUMN `pay_type`  int(4) NULL COMMENT '还款类型';


alter table pay_plan drop column `overdue_period`;
alter table pay_plan drop column `over_days`;
alter table pay_plan drop column `over_fine`;
alter table pay_plan drop column `over_liquidated`;
alter table pay_plan drop column `surplus_fine`;

ALTER TABLE `personal`
ADD COLUMN `personal_source`  varchar(100) NULL COMMENT '客户来源',
ADD COLUMN `other_source`  varchar(100) NULL COMMENT '其他来源',
ADD COLUMN `referrer`  varchar(100) NULL COMMENT '推介人',
ADD COLUMN `introduction_bank`  varchar(100) NULL COMMENT '推介支行';

alter table personal_bank drop column `personal_source`;
alter table personal_bank drop column `other_source`;
alter table personal_bank drop column `referrer`;
alter table personal_bank drop column `introduction_bank`;

-- 添加借据还款接口系统参数
-- 2018-7-12
INSERT INTO `sys_param` (`id`, `company_code`, `code`, `name`, `status`, `type`, `value`, `sign`, `operator`, `operate_time`, `remark`, `field`, `style`, `is_show`)VALUES ('ff80808163b071150163b3f37f2c042e', '0001', 'SysParam.RepaymentRecord.Url', '借据还款接口', '0', '0001', 'https://JCServer:Port/fintell/receipt', '0', 'administrator1', '2018-07-12 21:03:31', NULL, NULL, '5', '0');
INSERT INTO `sys_param` (`id`, `company_code`, `code`, `name`, `status`, `type`, `value`, `sign`, `operator`, `operate_time`, `remark`, `field`, `style`, `is_show`)VALUES ('ff80808163b071150163b3f37f2c042f', '0001', 'SysParam.RepaymentRecord.PlatformId', '借据还款接口平台ID', '0', '0001', 'D18FINTELL', '0', 'administrator1', '2018-07-12 21:03:31', NULL, NULL, '5', '0');


UPDATE `resource` SET `id`='5365', `remark`='', `icon`='', `name`='重新分配', `sort`='5', `type`='19', `url`='', `parent_id`='334', `show`='0' WHERE (`id`='5365');
UPDATE `resource` SET `id`='5114', `remark`='', `icon`='', `name`='批量分配', `sort`='16', `type`='19', `url`='', `parent_id`='182', `show`='0' WHERE (`id`='5114');
UPDATE `resource` SET `id`='5116', `remark`='', `icon`='', `name`='申请流转', `sort`='18', `type`='19', `url`='', `parent_id`='182', `show`='0' WHERE (`id`='5116');


-- 特殊，停催添加3级资源
-- 2018-7-14
-- 特殊待分配

INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8004', '', '', '客户姓名', '1', '19', '', '1201', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8005', '', '', '手机号', '2', '19', '', '1201', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8006', '', '', '案件标记', '3', '19', '', '1201', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8007', '', '', '委托方', '4', '19', '', '1201', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8008', '', '', '批次号', '5', '19', '', '1201', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8009', '', '', '机构', '6', '19', '', '1201', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8010', '', '', '催收员', '7', '19', '', '1201', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8011', '', '', '逾期期数', '8', '19', '', '1201', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8012', '', '', '身份证号', '9', '19', '', '1201', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8013', '', '', '协催方式', '10', '19', '', '1201', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8014', '', '', '案件金额', '11', '19', '', '1201', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8015', '', '', '逾期天数', '12', '19', '', '1201', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8016', '', '', '批量分配', '13', '19', '', '1201', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8017', '', '', '留案', '14', '19', '', '1201', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8018', '', '', '取消留案', '15', '19', '', '1201', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8019', '', '', '提前流转', '16', '19', '', '1201', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8020', '', '', '电话录音', '17', '19', '', '1201', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8021', '', '', '跟进明细', '18', '19', '', '1201', '0');

-- 特殊催收中

INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8022', '', '', '客户姓名', '1', '19', '', '1202', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8023', '', '', '手机号', '2', '19', '', '1202', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8024', '', '', '案件状态', '3', '19', '', '1202', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8025', '', '', '案件标记', '4', '19', '', '1202', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8026', '', '', '委托方', '5', '19', '', '1202', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8027', '', '', '批次号', '6', '19', '', '1202', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8028', '', '', '催收反馈', '7', '19', '', '1202', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8029', '', '', '机构', '8', '19', '', '1202', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8030', '', '', '催收员', '9', '19', '', '1202', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8031', '', '', '协催方式', '10', '19', '', '1202', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8032', '', '', '逾期期数', '11', '19', '', '1202', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8033', '', '', '身份证号', '12', '19', '', '1202', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8034', '', '', '案件金额', '13', '19', '', '1202', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8035', '', '', '逾期天数', '14', '19', '', '1202', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8036', '', '', '批量分配', '15', '19', '', '1202', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8037', '', '', '留案', '16', '19', '', '1202', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8038', '', '', '取消留案', '17', '19', '', '1202', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8039', '', '', '提前流转', '18', '19', '', '1202', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8040', '', '', '电话录音', '19', '19', '', '1202', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8041', '', '', '跟进明细', '20', '19', '', '1202', '0');

-- 特殊归C案件

INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8042', '', '', '客户姓名', '1', '19', '', '1203', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8043', '', '', '手机号', '2', '19', '', '1203', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8044', '', '', '机构', '3', '19', '', '1203', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8045', '', '', '催收员', '4', '19', '', '1203', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8046', '', '', '委托方', '5', '19', '', '1203', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8047', '', '', '批次号', '6', '19', '', '1203', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8048', '', '', '逾期期数', '7', '19', '', '1203', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8049', '', '', '身份证号', '8', '19', '', '1203', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8050', '', '', '逾期天数', '9', '19', '', '1203', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8051', '', '', '还款金额', '10', '19', '', '1203', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8052', '', '', '案件金额', '11', '19', '', '1203', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8053', '', '', '结案说明', '12', '19', '', '1203', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8054', '', '', '跟进明细', '13', '19', '', '1203', '0');

-- 特殊已结清

INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8055', '', '', '客户姓名', '1', '19', '', '1204', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8056', '', '', '手机号', '2', '19', '', '1204', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8057', '', '', '机构', '3', '19', '', '1204', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8058', '', '', '催收员', '4', '19', '', '1204', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8059', '', '', '委托方', '5', '19', '', '1204', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8060', '', '', '批次号', '6', '19', '', '1204', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8061', '', '', '逾期期数', '7', '19', '', '1204', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8062', '', '', '身份证号', '8', '19', '', '1204', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8063', '', '', '逾期天数', '9', '19', '', '1204', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8064', '', '', '还款金额', '10', '19', '', '1204', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8065', '', '', '案件金额', '11', '19', '', '1204', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8066', '', '', '结案说明', '12', '19', '', '1204', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8067', '', '', '跟进明细', '13', '19', '', '1204', '0');






-- 停催待分配

INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8068', '', '', '客户姓名', '1', '19', '', '1301', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8069', '', '', '手机号', '2', '19', '', '1301', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8070', '', '', '案件标记', '3', '19', '', '1301', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8071', '', '', '委托方', '4', '19', '', '1301', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8072', '', '', '批次号', '5', '19', '', '1301', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8073', '', '', '机构', '6', '19', '', '1301', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8074', '', '', '催收员', '7', '19', '', '1301', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8075', '', '', '逾期期数', '8', '19', '', '1301', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8076', '', '', '身份证号', '9', '19', '', '1301', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8077', '', '', '协催方式', '10', '19', '', '1301', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8078', '', '', '案件金额', '11', '19', '', '1301', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8079', '', '', '逾期天数', '12', '19', '', '1301', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8080', '', '', '批量分配', '13', '19', '', '1301', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8081', '', '', '留案', '14', '19', '', '1301', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8082', '', '', '取消留案', '15', '19', '', '1301', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8083', '', '', '提前流转', '16', '19', '', '1301', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8084', '', '', '电话录音', '17', '19', '', '1301', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8085', '', '', '跟进明细', '18', '19', '', '1301', '0');

-- 停催催收中

INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8086', '', '', '客户姓名', '1', '19', '', '1302', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8087', '', '', '手机号', '2', '19', '', '1302', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8088', '', '', '案件状态', '3', '19', '', '1302', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8089', '', '', '案件标记', '4', '19', '', '1302', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8090', '', '', '委托方', '5', '19', '', '1302', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8091', '', '', '批次号', '6', '19', '', '1302', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8092', '', '', '催收反馈', '7', '19', '', '1302', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8093', '', '', '机构', '8', '19', '', '1302', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8094', '', '', '催收员', '9', '19', '', '1302', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8095', '', '', '协催方式', '10', '19', '', '1302', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8096', '', '', '逾期期数', '11', '19', '', '1302', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8097', '', '', '身份证号', '12', '19', '', '1302', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8098', '', '', '案件金额', '13', '19', '', '1302', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8099', '', '', '逾期天数', '14', '19', '', '1302', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8100', '', '', '批量分配', '15', '19', '', '1302', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8101', '', '', '留案', '16', '19', '', '1302', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8102', '', '', '取消留案', '17', '19', '', '1302', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8103', '', '', '提前流转', '18', '19', '', '1302', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8104', '', '', '电话录音', '19', '19', '', '1302', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8105', '', '', '跟进明细', '20', '19', '', '1302', '0');

-- 停催归C案件

INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8106', '', '', '客户姓名', '1', '19', '', '1303', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8107', '', '', '手机号', '2', '19', '', '1303', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8108', '', '', '机构', '3', '19', '', '1303', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8109', '', '', '催收员', '4', '19', '', '1303', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8110', '', '', '委托方', '5', '19', '', '1303', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8111', '', '', '批次号', '6', '19', '', '1303', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8112', '', '', '逾期期数', '7', '19', '', '1303', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8113', '', '', '身份证号', '8', '19', '', '1303', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8114', '', '', '逾期天数', '9', '19', '', '1303', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8115', '', '', '还款金额', '10', '19', '', '1303', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8116', '', '', '案件金额', '11', '19', '', '1303', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8117', '', '', '结案说明', '12', '19', '', '1303', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8118', '', '', '跟进明细', '13', '19', '', '1303', '0');

-- 停催已结清

INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8119', '', '', '客户姓名', '1', '19', '', '1304', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8120', '', '', '手机号', '2', '19', '', '1304', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8121', '', '', '机构', '3', '19', '', '1304', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8122', '', '', '催收员', '4', '19', '', '1304', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8123', '', '', '委托方', '5', '19', '', '1304', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8124', '', '', '批次号', '6', '19', '', '1304', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8125', '', '', '逾期期数', '7', '19', '', '1304', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8126', '', '', '身份证号', '8', '19', '', '1304', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8127', '', '', '逾期天数', '9', '19', '', '1304', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8128', '', '', '还款金额', '10', '19', '', '1304', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8129', '', '', '案件金额', '11', '19', '', '1304', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8130', '', '', '结案说明', '12', '19', '', '1304', '0');
INSERT INTO `pangolin_business`.`resource` (`id`, `remark`, `icon`, `name`, `sort`, `type`, `url`, `parent_id`, `show`) VALUES ('8131', '', '', '跟进明细', '13', '19', '', '1304', '0');

-- 2018-7-15
-- 王昭
ALTER TABLE `case_followup_record`
ADD COLUMN `out_followup_back`  varchar(500) NULL COMMENT '委外催收反馈';

ALTER TABLE `case_info_distributed`
ADD COLUMN `executed_periods`  int(4) NULL COMMENT '已执行期数';

ALTER TABLE `case_info_distributed`
ADD COLUMN  `max_overdue_days` int(4)  NULL COMMENT '逾期最大天数',
ADD COLUMN  `lates_date_return` date  NULL COMMENT '最近一次应还日期',
ADD COLUMN  `left_periods` int(4)  NULL COMMENT '剩余期数',
ADD COLUMN  `unpaid_principal` decimal(18,4)  NULL COMMENT '未尝还本金',
ADD COLUMN  `account_balance` decimal(18,4)  NULL COMMENT '账户余额',
ADD COLUMN  `unpaid_interest` decimal(18,4)  NULL COMMENT '未尝还利息',
ADD COLUMN  `unpaid_fine` decimal(18,4)  NULL COMMENT '未尝还罚息',
ADD COLUMN  `unpaid_other_interest` decimal(18,4)  NULL COMMENT '未尝还其他利息',
ADD COLUMN  `unpaid_mth_fee` decimal(18,4)  NULL COMMENT '未尝还管理费',
ADD COLUMN  `unpaid_other_fee` decimal(18,4)  NULL COMMENT '未尝还其他费用',
ADD COLUMN  `unpaid_lpc` decimal(18,4)  NULL COMMENT '未尝还滞纳金',
ADD COLUMN  `curr_pnlt_interest` decimal(18,4)  NULL COMMENT '当前未结罚息复利',
ADD COLUMN  `remain_fee` decimal(18,4)  NULL COMMENT '剩余月服务费',
ADD COLUMN  `overdue_account_number` int(4)  NULL COMMENT '逾期账户数',
ADD COLUMN  `in_colcnt` int(4)  NULL COMMENT '內催次数',
ADD COLUMN  `out_colcnt` int(4)  NULL COMMENT '外包次数';


ALTER TABLE `case_info`
ADD COLUMN `executed_periods`  int(4) NULL COMMENT '已执行期数';

ALTER TABLE `case_info`
ADD COLUMN  `max_overdue_days` int(4)  NULL COMMENT '逾期最大天数',
ADD COLUMN  `lates_date_return` date  NULL COMMENT '最近一次应还日期',
ADD COLUMN  `left_periods` int(4)  NULL COMMENT '剩余期数',
ADD COLUMN  `unpaid_principal` decimal(18,4)  NULL COMMENT '未尝还本金',
ADD COLUMN  `account_balance` decimal(18,4)  NULL COMMENT '账户余额',
ADD COLUMN  `unpaid_interest` decimal(18,4)  NULL COMMENT '未尝还利息',
ADD COLUMN  `unpaid_fine` decimal(18,4)  NULL COMMENT '未尝还罚息',
ADD COLUMN  `unpaid_other_interest` decimal(18,4)  NULL COMMENT '未尝还其他利息',
ADD COLUMN  `unpaid_mth_fee` decimal(18,4)  NULL COMMENT '未尝还管理费',
ADD COLUMN  `unpaid_other_fee` decimal(18,4)  NULL COMMENT '未尝还其他费用',
ADD COLUMN  `unpaid_lpc` decimal(18,4)  NULL COMMENT '未尝还滞纳金',
ADD COLUMN  `curr_pnlt_interest` decimal(18,4)  NULL COMMENT '当前未结罚息复利',
ADD COLUMN  `remain_fee` decimal(18,4)  NULL COMMENT '剩余月服务费',
ADD COLUMN  `overdue_account_number` int(4)  NULL COMMENT '逾期账户数',
ADD COLUMN  `in_colcnt` int(4)  NULL COMMENT '內催次数',
ADD COLUMN  `out_colcnt` int(4)  NULL COMMENT '外包次数';


ALTER TABLE `case_info_history`
ADD COLUMN `executed_periods`  int(4) NULL COMMENT '已执行期数',
ADD COLUMN  `max_overdue_days` int(4)  NULL COMMENT '逾期最大天数',
ADD COLUMN  `lates_date_return` date  NULL COMMENT '最近一次应还日期',
ADD COLUMN  `left_periods` int(4)  NULL COMMENT '剩余期数',
ADD COLUMN  `account_balance` decimal(18,4)  NULL COMMENT '账户余额',
ADD COLUMN  `unpaid_principal` decimal(18,4)  NULL COMMENT '未尝还本金',
ADD COLUMN  `unpaid_interest` decimal(18,4)  NULL COMMENT '未尝还利息',
ADD COLUMN  `unpaid_fine` decimal(18,4)  NULL COMMENT '未尝还罚息',
ADD COLUMN  `unpaid_other_interest` decimal(18,4)  NULL COMMENT '未尝还其他利息',
ADD COLUMN  `unpaid_mth_fee` decimal(18,4)  NULL COMMENT '未尝还管理费',
ADD COLUMN  `unpaid_other_fee` decimal(18,4)  NULL COMMENT '未尝还其他费用',
ADD COLUMN  `unpaid_lpc` decimal(18,4)  NULL COMMENT '未尝还滞纳金',
ADD COLUMN  `curr_pnlt_interest` decimal(18,4)  NULL COMMENT '当前未结罚息复利',
ADD COLUMN  `remain_fee` decimal(18,4)  NULL COMMENT '剩余月服务费',
ADD COLUMN  `overdue_account_number` int(4)  NULL COMMENT '逾期账户数',
ADD COLUMN  `in_colcnt` int(4)  NULL COMMENT '內催次数',
ADD COLUMN  `out_colcnt` int(4)  NULL COMMENT '外包次数',
ADD COLUMN `product_type` varchar(50) DEFAULT NULL,
ADD COLUMN  `product_name` varchar(50) DEFAULT NULL;

-- 2018-7-20
-- 陈京
-- 协催审批状态
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('34', '0007', NULL, '外访待审批', '2', NULL);
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('35', '0007', NULL, '协催审批中', '3', NULL);
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('210', '0007', NULL, '审批拒绝', '5', NULL);
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('288', '0007', NULL, '协催待审批', '4', NULL);
INSERT INTO `pangolin_business`.`data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('289', '0007', NULL, '协催审批完成', '6', NULL);

-- 2018-7-24
-- 王昭
-- 在文件表中添加文件来源
ALTER TABLE `case_file`
ADD COLUMN `file_source`  int(4) NULL COMMENT '文件来源';

INSERT INTO `data_dict_type` (`id`, `code`, `name`) VALUES ('309', '0309', '文件来源');

INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('1000', '0309', NULL, '进件资料', '1', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('1001', '0309', NULL, '补充资料', '2', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('1002', '0309', NULL, '线上合同', '3', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('1003', '0309', NULL, '线下合同', '4', NULL);
INSERT INTO `data_dict` (`id`, `type_code`, `code`, `name`, `sort`, `language`) VALUES ('1004', '0309', NULL, '其他资料', '5', NULL);

-- 2018-7-25
-- 王昭
-- 在文件表中添加文件来源
ALTER TABLE `case_info`
ADD COLUMN `settle_amount` decimal(18,4) NULL COMMENT '结清金额',
ADD COLUMN `clean_date` date NULL COMMENT '归C时间',
ADD COLUMN `settle_date` date NULL COMMENT '结清时间';

ALTER TABLE `case_info_distributed`
ADD COLUMN `settle_amount` decimal(18,4) NULL COMMENT '结清金额',
ADD COLUMN `clean_date` date NULL COMMENT '归C时间',
ADD COLUMN `settle_date` date NULL COMMENT '结清时间';


ALTER TABLE `case_info_history`
ADD COLUMN `settle_amount` decimal(18,4) NULL COMMENT '结清金额',
ADD COLUMN `clean_date` date NULL COMMENT '归C时间',
ADD COLUMN `settle_date` date NULL COMMENT '结清时间';

ALTER TABLE `case_info`
ADD COLUMN `pnlt_interest` decimal(18,4) NULL COMMENT '未结利息',
ADD COLUMN `pnlt_fine` decimal(18,4) NULL COMMENT '未结罚息';

ALTER TABLE `case_info_distributed`
ADD COLUMN `pnlt_interest` decimal(18,4) NULL COMMENT '未结利息',
ADD COLUMN `pnlt_fine` decimal(18,4) NULL COMMENT '未结罚息';

ALTER TABLE `case_info_history`
ADD COLUMN `pnlt_interest` decimal(18,4) NULL COMMENT '未结利息',
ADD COLUMN `pnlt_fine` decimal(18,4) NULL COMMENT '未结罚息';