-- 格式如下:
--修改日期：
--修改内容：
--修改人：

--2017-09-20
--新增案件备注信息表
--夏群
CREATE TABLE `case_info_remark` (
  `id` varchar(64) NOT NULL COMMENT '主键ID',
  `case_id` varchar(64) DEFAULT NULL COMMENT '案件ID',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注信息',
  `operator_user_name` varchar(64) DEFAULT NULL COMMENT '操作人用户名',
  `operator_real_name` varchar(200) DEFAULT NULL COMMENT '操作人姓名',
  `operator_time` datetime DEFAULT NULL COMMENT '操作时间',
  `company_code` varchar(64) DEFAULT NULL COMMENT '公司code码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='案件备注信息';

--2017-09-25
--新增数据库备份表
--胡开甲
DROP TABLE IF EXISTS `system_backup`;
CREATE TABLE `system_backup` (
  `id` varchar(64) NOT NULL,
  `company_code` varchar(64) DEFAULT NULL COMMENT '公司的标识',
  `type` int(4) DEFAULT NULL COMMENT '备份类型 0：自动 1：手动',
  `mysql_name` varchar(255) DEFAULT NULL COMMENT 'mysql数据库文件名称',
  `mongdb_name` varchar(255) DEFAULT NULL COMMENT 'mongdb数据库名称',
  `operator` varchar(200) DEFAULT NULL COMMENT '操作人',
  `operate_time` datetime DEFAULT NULL COMMENT '备份时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--2017-09-26
--新增案件回收表
--祁吉贵
CREATE TABLE `case_info_return` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT '主键',
  `case_id` varchar(64) DEFAULT NULL COMMENT '案件ID',
  `outsource_id` varchar(64) DEFAULT NULL COMMENT '委外案件ID',
  `operator_time` datetime DEFAULT NULL COMMENT '操作时间',
  `operator` varchar(64) DEFAULT NULL COMMENT '操作人（username)',
  `reason` varchar(1000) DEFAULT NULL COMMENT '退案原因',
  `source` int(4) DEFAULT NULL COMMENT '退回来源：内催，委外，司法，核销',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='案件回收';

--2017-09-26
--新增司法案件表
--夏群
CREATE TABLE `case_info_judicial` (
  `id` varchar(64) NOT NULL COMMENT '主键ID',
  `case_id` varchar(64) DEFAULT NULL COMMENT '案件ID',
  `operator_user_name` varchar(64) DEFAULT NULL COMMENT '操作人用户名',
  `operator_real_name` varchar(200) DEFAULT NULL COMMENT '操作人姓名',
  `operator_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='司法案件池';

--2017-09-28
--新增委外跟进记录表
--胡艳敏
DROP TABLE IF EXISTS `outsource_follow_record`;
CREATE TABLE `outsource_follow_record` (
  `id` varchar(64) NOT NULL,
  `company_code` varchar(64) DEFAULT NULL COMMENT '公司的标识',
  `case_id` varchar(64) DEFAULT NULL COMMENT '案件id',
  `case_num` varchar(64) DEFAULT NULL COMMENT '案件编号',
  `follow_time` datetime DEFAULT NULL COMMENT '跟进时间',
  `follow_type` int(4) DEFAULT NULL COMMENT '跟进方式 0：电话 1：外访',
  `object_name` int(4) DEFAULT NULL COMMENT '催收对象',
  `user_name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `tel_status` int(4) DEFAULT NULL COMMENT '电话状态 64, 正常 65, 空号 66, 停机 67, 关机 68, 未知',
  `feedback` int(4) DEFAULT NULL COMMENT '催收反馈',
  `follow_record` varchar(1024) DEFAULT NULL COMMENT '跟进记录',
  `follow_person` varchar(64) DEFAULT NULL COMMENT '跟进人',
  `operator_name` varchar(100) DEFAULT NULL COMMENT '操作人姓名',
  `operator_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='委外案件跟进记录信息';

--2017-10-10
--新增委外跟进记录表
--高贝贝
CREATE TABLE `export_items` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `company_code` varchar(64) DEFAULT NULL COMMENT '公司code',
  `category` int(64) DEFAULT NULL COMMENT '类别(内催，委外，案件更新)',
  `type` int(4) DEFAULT NULL COMMENT '类型(客户，联系人，工作，案件，银行，跟进记录)',
  `name` varchar(64) DEFAULT NULL COMMENT '属性名称',
  `statu` int(4) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

--2017-10-24
--新增案件分配结果临时表
--白章宇
CREATE TABLE `case_distributed_temporary` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT '主键ID',
  `case_id` varchar(64) DEFAULT NULL COMMENT '案件ID',
  `case_number` varchar(64) DEFAULT NULL COMMENT '案件编号',
  `batch_number` varchar(64) DEFAULT NULL COMMENT '批次号',
  `personal_name` varchar(200) DEFAULT NULL COMMENT '客户姓名',
  `overdue_amt` decimal(18,4) DEFAULT NULL COMMENT '案件金额',
  `case_turn_record` int(64) DEFAULT NULL COMMENT '流转记录ID',
  `case_repair_id` varchar(64) DEFAULT NULL COMMENT '案件修复ID',
  `last_collector` varchar(64) DEFAULT NULL COMMENT '上一个催收员ID',
  `last_collector_name` varchar(200) DEFAULT NULL COMMENT '上一个催收员姓名',
  `current_collector` varchar(64) DEFAULT NULL COMMENT '当前催收员ID',
  `current_collector_name` varchar(200) DEFAULT NULL COMMENT '当前催收员姓名',
  `last_department` varchar(64) DEFAULT NULL COMMENT '案件上一个所在部门ID',
  `last_department_name` varchar(200) DEFAULT NULL COMMENT '案件上一个所在部门名称',
  `last_collection_status` int(4) DEFAULT NULL COMMENT '案件原催收状态',
  `last_collection_type` int(4) DEFAULT NULL COMMENT '原案件催收类型',
  `last_assist_flag` int(4) DEFAULT NULL COMMENT '案件协催标识',
  `current_department` varchar(64) DEFAULT NULL COMMENT '案件当前所在部门ID',
  `current_department_name` varchar(200) DEFAULT NULL COMMENT '案件当前所在部门名称',
  `principal_name` varchar(200) DEFAULT NULL COMMENT '委托方名称',
  `type` int(4) DEFAULT NULL COMMENT '分案类型',
  `operator_user_name` varchar(64) DEFAULT NULL COMMENT '操作人用户名',
  `operator_real_name` varchar(200) DEFAULT NULL COMMENT '操作人姓名',
  `operator_time` datetime DEFAULT NULL COMMENT '操作时间',
  `company_code` varchar(64) DEFAULT NULL COMMENT '公司code码',
  `last_collector_has_days` int(4) DEFAULT NULL COMMENT '上一催收员持案天数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='案件分配结果临时表';


--2017-10-24
--用户录音文件
--祁吉贵
CREATE TABLE `user_video` (
  `id` varchar(64) NOT NULL COMMENT '主键ID',
  `user_name` varchar(64) DEFAULT NULL COMMENT '催收员（字母）',
  `user_real_name` varchar(200) DEFAULT NULL COMMENT '催收员真名',
  `video_name` varchar(200) DEFAULT NULL COMMENT '录音文件名',
  `video_url` varchar(200) DEFAULT NULL COMMENT '文件路径',
  `video_length` varchar(200) DEFAULT NULL COMMENT '录音长度',
  `operator_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 诉讼表
-- 2018-6-20
-- 陈京

CREATE TABLE `lawsuit_record` (
  `id` varchar(64) NOT NULL COMMENT '主键ID',
  `case_number` varchar(64) DEFAULT NULL COMMENT '案件编号（借据号）',
  `lawsuit_result` int(4) DEFAULT NULL COMMENT '诉讼阶段（初始806、立案807、开庭808、公告809、审批827、执行828）',
  `lawsuit_type` int(4) DEFAULT NULL COMMENT '诉讼类型（内部诉讼814、外部诉讼815）',
  `turn_from_pool` int(4) DEFAULT NULL COMMENT '流转来源（电催817, 818, 外访818, 819, 委外819, 特殊820, 停催821,核心系统825,Excel导入826）',
  `overdue_amount` decimal(18,4) DEFAULT NULL COMMENT '案件金额',
  `overdue_periods` int(4) DEFAULT NULL COMMENT '逾期期数',
  `overdue_days` int(4) DEFAULT NULL COMMENT '逾期天数',
  `hold_days` int(4) DEFAULT NULL COMMENT '持案天数',
  `left_days` int(4) DEFAULT NULL COMMENT '剩余天数',
  `collection_status` int(4) DEFAULT NULL COMMENT '催收状态',
  `collector` varchar(64) DEFAULT NULL COMMENT '催收员',
  `recover_remark` int(4) DEFAULT NULL COMMENT '回收标识（0-未回收，1-回收）',
  `principal_id` varchar(64) DEFAULT NULL COMMENT '委托方ID(律师)',
  `approver` varchar(64) DEFAULT NULL COMMENT '操作人',
  `creation_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- 反欺诈表
-- 2018-6-20
-- 陈京

CREATE TABLE `anti_fraud_record` (
  `id` varchar(64) NOT NULL COMMENT '主键ID',
  `case_number` varchar(64) DEFAULT NULL COMMENT '案件编号（借据号）',
  `anti_fraud_result` int(4) DEFAULT NULL COMMENT '（反欺诈结果（本人贷款830、非本人贷款831）',
  `turn_from_pool` int(4) DEFAULT NULL COMMENT '流转来源（电催817, 818, 外访818, 819, 委外819, 特殊820, 停催821,核心系统825,Excel导入826）',
  `overdue_amount` decimal(18,4) DEFAULT NULL COMMENT '案件金额',
  `overdue_periods` int(4) DEFAULT NULL COMMENT '逾期期数',
  `overdue_days` int(4) DEFAULT NULL COMMENT '逾期天数',
  `hold_days` int(4) DEFAULT NULL COMMENT '持案天数',
  `left_days` int(4) DEFAULT NULL COMMENT '剩余天数',
  `collection_status` int(4) DEFAULT NULL COMMENT '催收状态',
  `collector` varchar(64) DEFAULT NULL COMMENT '催收员',
  `recover_remark` int(4) DEFAULT NULL COMMENT '回收标识（0-未回收，1-回收）',
  `approver` varchar(64) DEFAULT NULL COMMENT '操作人',
  `creation_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 失联案件表
-- 2018-6-24
-- duchao

CREATE TABLE `missing_connection_info` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `case_number` varchar(64) DEFAULT NULL COMMENT '案件编号',
  `contract_number` varchar(64) DEFAULT NULL COMMENT '合同编号',
  `personal_name` varchar(64) DEFAULT NULL COMMENT '客户姓名',
  `id_card` varchar(32) DEFAULT NULL COMMENT '身份证号码',
  `mobile_no` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `sex` int(4) DEFAULT NULL COMMENT '性别',
  `first_missing_time` date DEFAULT NULL COMMENT '首次失联时间',
  `current_missing_days` int(16) DEFAULT NULL COMMENT '当前失联天数',
  `longest_missing_days` int(16) DEFAULT NULL COMMENT '最长失联天数',
  `missing_times` int(16) DEFAULT NULL COMMENT '失联次数',
  `current_missing_flag` int(4) DEFAULT NULL COMMENT '当前是否失联',
  `company_code` varchar(64) DEFAULT NULL COMMENT '公司code',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 委外分配历史记录表
-- 2018-6-27
-- wangzhao
CREATE TABLE `history_outSource_distribution` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `personal_id` varchar(64) DEFAULT NULL COMMENT '客户id',
  `out_id` varchar(64) DEFAULT NULL COMMENT '委外方id',
  `opertor_time` datetime DEFAULT NULL COMMENT '分配时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='案件分配委外记录历史表';

CREATE TABLE `outSource_whip` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `case_id` varchar(64) DEFAULT NULL COMMENT '案件id',
  `out_id` varchar(64) DEFAULT NULL COMMENT '委外方id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='委外案件分配临时表';

CREATE TABLE `outsource_area` (
  `outsource_id` varchar(64) DEFAULT NULL COMMENT '委外方id',
  `area_id` int(11) DEFAULT NULL COMMENT '城市id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='委外地区关联关系表';


CREATE TABLE `leave_case_apply` (
  `id` varchar(64) NOT NULL COMMENT '主键',
	`approval_id` varchar(64) DEFAULT NULL COMMENT '审批流程id',
  `case_id` varchar(64) DEFAULT NULL COMMENT '案件id',
	`approval_status` int(4) DEFAULT NULL COMMENT '审批状态(0、代审批，1、审批中，2、同意，3、拒绝)',
  `leave_reason` varchar(64) DEFAULT NULL COMMENT '流案说明',
	`apply_user` varchar(50) DEFAULT NULL COMMENT '申请人',
  `apply_time` datetime DEFAULT NULL COMMENT '申请时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='案件流案申请表';

-- 修改表记录 zmm 2018-07-01
ALTER TABLE user ADD COLUMN collection_grade int(4) DEFAULT NULL COMMENT '催收员等级';
ALTER TABLE user ADD COLUMN division_switch int(4) DEFAULT NULL COMMENT '催收员分案开关(开启:504,关闭:505)';
ALTER TABLE department ADD COLUMN internal_type int(4) DEFAULT NULL COMMENT '内催部门类型(电催:630,外访:631)';
ALTER TABLE case_info_distributed ADD COLUMN case_pool_type int(4) DEFAULT NULL COMMENT '案件池类型';

ALTER TABLE case_info_distributed ADD COLUMN product_type varchar(50) DEFAULT NULL COMMENT '产品类型';
ALTER TABLE case_info_distributed ADD COLUMN product_name varchar(50) DEFAULT NULL COMMENT '产品名称';
ALTER TABLE case_info_distributed ADD COLUMN is_processed varchar(10) DEFAULT NULL COMMENT '是否已经处理';

insert into data_dict_type values('100','0100','催收员等级');
insert into data_dict_type values('101','0101','催收员分案开关');
insert into data_dict_type values('102','0102','案件分配模式');
insert into data_dict_type values('103','0103','案件类型');
insert into data_dict_type values('104','0104','产品类型');
insert into data_dict_type values('105','0105','催收员案件分配模式');
insert into data_dict_type values('106','0106','策略执行频率');
insert into data_dict_type values('107','0107','是否分配到催员');
insert into data_dict_type values('108','0108','内催部门类型');

-- 数据字典表 zmm 2018-07-01
insert into data_dict values('500','0100','1.8','A','1',NULL);
insert into data_dict values('501','0100','1.5','B','2',NULL);
insert into data_dict values('502','0100','1.2','C','3',NULL);
insert into data_dict values('503','0100','1.0','D','4',NULL);
insert into data_dict values('504','0101',NULL,'开启','1',NULL);
insert into data_dict values('505','0101',NULL,'关闭','2',NULL);


insert into data_dict values('530','0102',NULL,'月初对月底','1',NULL);
insert into data_dict values('531','0102',NULL,'账单对账单','2',NULL);

-- 修改及添加组织结构类型 50x 是新添加
insert into data_dict values('196','0001',NULL,'综合管理','0',NULL);
insert into data_dict values('1','0001',NULL,'电话催收','1',NULL);
insert into data_dict values('2','0001',NULL,'外访催收','2',NULL);
insert into data_dict values('4','0001',NULL,'委外催收','3',NULL);
insert into data_dict values('506','0001',NULL,'停催催收','4',NULL);
insert into data_dict values('508','0001',NULL,'特殊催收','5',NULL);
insert into data_dict values('510','0001',NULL,'内诉催收','6',NULL);
insert into data_dict values('511','0001',NULL,'外诉催收','7',NULL);
insert into data_dict values('507','0001',NULL,'反欺诈催收','8',NULL);
insert into data_dict values('509','0001',NULL,'贷后预警催收','9',NULL);
insert into data_dict values('7','0001',NULL,'修复管理','10',NULL);
insert into data_dict values('6','0001',NULL,'提醒催收','11',NULL);
insert into data_dict values('5','0001',NULL,'智能催收','12',NULL);
insert into data_dict values('3','0001',NULL,'司法催收','13',NULL);

insert into data_dict values('540','0103',NULL,'内催案件','1',NULL);
insert into data_dict values('541','0103',NULL,'委外案件','2',NULL);
insert into data_dict values('542','0103',NULL,'特殊案件','3',NULL);
insert into data_dict values('543','0103',NULL,'停催案件','4',NULL);
insert into data_dict values('544','0103',NULL,'诉讼案件','5',NULL);
insert into data_dict values('545','0103',NULL,'反欺诈案件','6',NULL);

INSERT INTO data_dict VALUES('560','0104',NULL,'耐消','1',NULL);
INSERT INTO data_dict VALUES('561','0104',NULL,'信用','2',NULL);
INSERT INTO data_dict VALUES('562','0104',NULL,'抵押','3',NULL);
INSERT INTO data_dict VALUES('563','0104',NULL,'保证','4',NULL);


INSERT INTO data_dict VALUES('600','0105',NULL,'按金额','1',NULL);
INSERT INTO data_dict VALUES('601','0105',NULL,'按户数','2',NULL);
INSERT INTO data_dict VALUES('602','0105',NULL,'综合均分','3',NULL);
INSERT INTO data_dict VALUES('603','0105',NULL,'按催收员配比','4',NULL);
INSERT INTO data_dict VALUES('604','0105',NULL,'按金额填坑','5',NULL);
INSERT INTO data_dict VALUES('605','0105',NULL,'按户数填坑','6',NULL);

INSERT INTO data_dict VALUES('610','0106',NULL,'每天','1',NULL);
INSERT INTO data_dict VALUES('611','0106',NULL,'月初','2',NULL);

INSERT INTO data_dict VALUES('620','0107',NULL,'是','1',NULL);
INSERT INTO data_dict VALUES('621','0107',NULL,'否','2',NULL);

INSERT INTO data_dict VALUES('630','0108',NULL,'电催','1',NULL);
INSERT INTO data_dict VALUES('631','0108',NULL,'外访','2',NULL);

-- 部门表
ALTER TABLE department ADD COLUMN category int(4) DEFAULT NULL COMMENT '组织类别(诉讼，停催，反欺诈，特殊，贷后预警)';


-- 参数表
insert into sys_param values('71a9d6d5-ff57-2222-b8b9-2c4d54e41111','0001','SysParam.outsource.consumption','耐消委外时长(天)','0','9001','300','0','administrator',NULL,NULL,NULL,'1','0');
insert into sys_param values('71a9d6d5-ff57-2222-b8b9-2c4d54e41112','0001','SysParam.outsource.credit','信用委外时长(天)','0','9001','60','0','administrator',NULL,NULL,NULL,'1','0');
insert into sys_param values('71a9d6d5-ff57-2222-b8b9-2c4d54e41113','0001','SysParam.outsource.mortgage','抵押委外时长(天)','0','9001','90','0','administrator',NULL,NULL,NULL,'1','0');
insert into sys_param values('71a9d6d5-ff57-2222-b8b9-2c4d54e41114','0001','SysParam.outsource.pledge','保证委外时长(天)','0','9001','60','0','administrator',NULL,NULL,NULL,'1','0');


insert into sys_param values('71a9d6d5-ff57-2222-b8b9-2c4d54e41115','0001','SysParam.division.A','A级催收员系数','0','9001','1.8','0','administrator',NULL,NULL,NULL,'1','0');
insert into sys_param values('71a9d6d5-ff57-2222-b8b9-2c4d54e41116','0001','SysParam.division.B','B级催收员系数','0','9001','1.2','0','administrator',NULL,NULL,NULL,'1','0');
insert into sys_param values('71a9d6d5-ff57-2222-b8b9-2c4d54e41117','0001','SysParam.division.C','C级催收员系数','0','9001','1.0','0','administrator',NULL,NULL,NULL,'1','0');
insert into sys_param values('71a9d6d5-ff57-2222-b8b9-2c4d54e41118','0001','SysParam.division.D','D级催收员系数','0','9001','0.9','0','administrator',NULL,NULL,NULL,'1','0');

-- 2018-7-4
-- wangzhao
CREATE TABLE `pay_plan` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `case_number` varchar(64) DEFAULT NULL COMMENT '案件编号',
  `pay_period` int(4) DEFAULT NULL COMMENT '还款期数',
  `pay_date` date DEFAULT NULL COMMENT '还款日期',
  `overdue_period` int(4) DEFAULT NULL COMMENT '逾期期数',
  `pay_amt` decimal(18,4) DEFAULT NULL COMMENT '应还金额',
  `pay_principal` decimal(18,4) DEFAULT NULL COMMENT '应还本金',
  `pay_interest` decimal(18,4) DEFAULT NULL COMMENT '应还利息',
  `pay_platform_fee` decimal(18,4) DEFAULT NULL COMMENT '应还平台管理费',
  `pay_fine` decimal(18,4) DEFAULT NULL COMMENT '应还罚息',
  `pay_liquidated` decimal(18,4) DEFAULT NULL COMMENT '应还违约金',
  `over_days` int(4) DEFAULT NULL COMMENT '逾期天数',
  `over_fine` decimal(18,4) DEFAULT NULL COMMENT '逾期罚息',
  `over_liquidated` decimal(18,4) DEFAULT NULL COMMENT '逾期违约金',
  `surplus_fine` decimal(18,4) DEFAULT NULL COMMENT '剩余罚息',
  `surplus_principal` decimal(18,4) DEFAULT NULL COMMENT '剩余本金',
  `surplus_interest` decimal(18,4) DEFAULT NULL COMMENT '剩余利息',
  `surplus_platform_fee` decimal(18,4) DEFAULT NULL COMMENT '剩余管理费',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='还款计划表';


CREATE TABLE `case_info_extend` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `case_number` varchar(64) DEFAULT NULL COMMENT '案件编号',
  `max_overdue_days` int(4) DEFAULT NULL COMMENT '逾期最大天数',
  `lates_date_return` date DEFAULT NULL COMMENT '最近一次应还日期',
  `left_periods` int(4) DEFAULT NULL COMMENT '剩余期数',
  `unpaid_principal` decimal(18,4) DEFAULT NULL COMMENT '未尝还本金',
  `unpaid_interest` decimal(18,4) DEFAULT NULL COMMENT '未尝还利息',
  `unpaid_fine` decimal(18,4) DEFAULT NULL COMMENT '未尝还罚息',
  `unpaid_other_interest` decimal(18,4) DEFAULT NULL COMMENT '未尝还其他利息',
  `unpaid_mth_fee` decimal(18,4) DEFAULT NULL COMMENT '未尝还管理费',
  `unpaid_other_fee` decimal(18,4) DEFAULT NULL COMMENT '未尝还其他费用',
  `unpaid_lpc` decimal(18,4) DEFAULT NULL COMMENT '未尝还滞纳金',
  `curr_pnlt_interest` decimal(18,4) DEFAULT NULL COMMENT '当前未结罚息复利',
  `remain_fee` decimal(18,4) DEFAULT NULL COMMENT '剩余月服务费',
  `overdue_account_number` int(4) DEFAULT NULL COMMENT '逾期账户数',
  `in_colcnt` int(4) DEFAULT NULL COMMENT '內催次数',
  `out_colcnt` int(4) DEFAULT NULL COMMENT '外包次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='案件扩展表';


CREATE TABLE `material` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `personal_id` varchar(64) DEFAULT NULL COMMENT '客户id',
  `resident_doc_memo` varchar(200) DEFAULT NULL COMMENT '居住证明核查备注',
  `resident_approve_memo` varchar(200) DEFAULT NULL COMMENT '居住证明审批备注',
  `income_doc_memo` varchar(200) DEFAULT NULL COMMENT '收入证明核查备注',
  `income_approve_memo` varchar(200) DEFAULT NULL COMMENT '收入证明审批备注',
  `cophone_existind` varchar(200) DEFAULT NULL COMMENT '单位电话调查结果',
  `cophone_memo` varchar(200) DEFAULT NULL COMMENT '单位电话备注',
  `cophone_approve_memo` varchar(200) DEFAULT NULL COMMENT '单位电话审批备注',
  `home_phone_memo` varchar(200) DEFAULT NULL COMMENT '家庭电话备注',
  `home_phone_approve_memo` varchar(200) DEFAULT NULL COMMENT '家庭电话审批备注',
  `mobile_memo` varchar(200) DEFAULT NULL COMMENT '本人手机备注',
  `mobile_approve_memo` varchar(200) DEFAULT NULL COMMENT '本人手机审批备注',
  `relative_memo` varchar(200) DEFAULT NULL COMMENT '其他联系人备注',
  `inet_memo` varchar(200) DEFAULT NULL COMMENT '系统校验备注',
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户备注表';

CREATE TABLE `order_repayment_plan` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单id',
  `first_due_date` date DEFAULT NULL COMMENT '首次还款日',
  `month_amt` decimal(18,4) DEFAULT NULL COMMENT '每月还款额',
  `amorz_type` int(4) DEFAULT NULL COMMENT '还款计划类型',
  `year_rate` decimal(18,4) DEFAULT NULL COMMENT '年利率',
  `repay_method` varchar(10) DEFAULT NULL COMMENT '还款方式',
  `comsive_rate` decimal(18,4) DEFAULT NULL COMMENT '综合利率',
  `penalty_rate` decimal(18,4) DEFAULT NULL COMMENT '罚息费率',
  `contract_penalty_rate` decimal(18,4) DEFAULT NULL COMMENT '合同违约金费率',
  `advance_payment_rate` decimal(18,4) DEFAULT NULL COMMENT '提前还款违约金费率',
  `staging_fee_rate` decimal(18,4) DEFAULT NULL COMMENT '分期服务费率',
  `repay_subloan_wether` varchar(10) DEFAULT NULL COMMENT '是否还后续贷款',
  `black_flag` varchar(10) DEFAULT NULL COMMENT '黑名单标志',
  `max_mortgage_mark` varchar(10) DEFAULT NULL COMMENT '最高额抵押标识',
  `main_loan_logo` varchar(10) DEFAULT NULL COMMENT '主贷款标识',
  `main_apply_number` varchar(50) DEFAULT NULL COMMENT '主贷款申请号',
  `sales_remark` varchar(200) DEFAULT NULL COMMENT '销售代表备注',
  `pboc_score` decimal(18,4) DEFAULT NULL COMMENT '人行征信评分',
  `decision_no` varchar(10) DEFAULT NULL COMMENT '决定代码',
  `decision_reason` varchar(100) DEFAULT NULL COMMENT '决定原因',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单还款计划表';


CREATE TABLE `order_info` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `case_number` varchar(64) DEFAULT NULL COMMENT '案件编号',
  `promotion_number` varchar(50) DEFAULT NULL COMMENT '活动项目编号',
  `channel_number` varchar(50) DEFAULT NULL COMMENT '申请渠道代码',
  `total_price` decimal(18,4) DEFAULT NULL COMMENT '商品总价',
  `self_pay_amount` decimal(18,4) DEFAULT NULL COMMENT '自付金额',
  `loan_amount` decimal(18,4) DEFAULT NULL COMMENT '申请金额',
  `loan_date` date DEFAULT NULL COMMENT '放款日期',
  `approved_loan_amt` decimal(18,4) DEFAULT NULL COMMENT '批准贷款金额',
  `loan_tenure` int(4) DEFAULT NULL COMMENT '贷款期限',
  `loan_status` varchar(10) DEFAULT NULL COMMENT '贷款状态',
  `bill_cycle` int(4) DEFAULT NULL COMMENT '账单周期',
  `store_number` varchar(20) DEFAULT NULL COMMENT '销售门店代码',
  `sale_name` varchar(20) DEFAULT NULL COMMENT '销售代表姓名',
  `sale_name_phone` varchar(20) DEFAULT NULL COMMENT '销售代表手机号',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

CREATE TABLE `order_comm` (
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单id',
  `comm_id` varchar(64) DEFAULT NULL COMMENT '商品id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单商品关系';

CREATE TABLE `commodity` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `comm_type` varchar(100) DEFAULT NULL COMMENT '商品类型',
  `comm_brand` varchar(100) DEFAULT NULL COMMENT '商品品牌',
  `comm_model` varchar(100) DEFAULT NULL COMMENT '商品型号',
  `comm_price` decimal(18,4) DEFAULT NULL COMMENT '商品价格',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';


-- 修改表中的字段，原来的单词拼写错误。 张明明 2018-07-10
alter table case_info_distributed change prodcut_type product_type varchar(50);
alter table case_info_distributed change prodcut_name product_name varchar(50);
alter table case_info change prodcut_type product_type varchar(50);
alter table case_info change prodcut_name product_name varchar(50);
alter table product change prodcut_code product_code varchar(200);
alter table product change prodcut_name product_name varchar(200);


-- 2018-7-12
-- chenjing
CREATE TABLE `case_repayment_record` (
  `id` varchar(64) NOT NULL COMMENT '主键ID',
  `appl_no` varchar(64) NOT NULL COMMENT '贷款申请号',
  `loan_amt` decimal(18,4) NOT NULL COMMENT '借款金额',
  `loan_number` int(4) NOT NULL COMMENT '借款期数',
  `current_amt` decimal(18,4) DEFAULT NULL COMMENT '当期金额',
  `termination_flag` varchar(4) DEFAULT NULL COMMENT '是否结清 Y/N',
  `delq_flag` varchar(4) DEFAULT NULL COMMENT '是否逾期 Y/N',
  `transfer_user` varchar(64) DEFAULT NULL COMMENT '调取记录人',
  `transferTime` date DEFAULT NULL COMMENT '调取时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='借据还款记录表';


-- 2018-7-25
-- 王昭
CREATE TABLE `write_off_details` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `case_number` varchar(64) DEFAULT NULL COMMENT '案件编号',
	`inaccount_date` date DEFAULT NULL COMMENT '入账日期',
	`personal_no` VARCHAR(50) DEFAULT NULL COMMENT '客户号',
  `unpaid_principal` decimal(18,4) DEFAULT NULL COMMENT '录入未偿还本金',
  `remain_principal` decimal(18,4) DEFAULT NULL COMMENT '录入剩余本金',
  `unpaid_interest` decimal(18,4) DEFAULT NULL COMMENT '录入未偿还利息',
  `verifi_nobill_interest` decimal(18,4) DEFAULT NULL COMMENT '录入未出账单利息',
  `other_interest` decimal(18,4) DEFAULT NULL COMMENT '录入其他累计利息',
  `pnlt_interest` decimal(18,4) DEFAULT NULL COMMENT '录入罚息',
	`in_fine` decimal(18,4) DEFAULT NULL COMMENT '录入滞纳金',
	`month_fee` decimal(18,4) DEFAULT NULL COMMENT '录入月服务费',
	`other_fee` decimal(18,4) DEFAULT NULL COMMENT '录入其他管理费',
	`has_total` decimal(18,4) DEFAULT NULL COMMENT '还款总额',
	`termination_ind` VARCHAR(10) DEFAULT NULL COMMENT '核销结清标识(Y-已结清，N-未结清)',
  `settle_date` date DEFAULT NULL COMMENT '结清日期',
	`request_date` date DEFAULT NULL COMMENT '核销请求日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='核销还款明细';