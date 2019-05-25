create table refund_record
(
	id bigint auto_increment comment 'id'
		primary key,
	version int not null comment '版本号',
	scenic_id bigint null comment '景区id',
	order_no varchar(50) null comment '订单号',
	trx_no varchar(50) null comment '支付流水号',
	bank_order_no varchar(50) null comment '第三方订单号',
	merchant_id varchar(100) not null comment '商务号id',
	product_name varchar(60) null comment '商品名称',
	biz_type varchar(30) null comment '业务类型',
	pay_way varchar(30) null comment '支付方式类型',
	refund_amount int null comment '订单退款金额',
	total_amount int null comment '订单总金额',
	refund_trx_no varchar(50) not null comment '退款流水号',
	bank_refund_trx_no varchar(50) null comment '第三方退款流水号',
	refund_index int null comment '退款次数（索引 从0开始）',
	refund_request_time datetime null comment '退款请求时间',
	refund_complete_time datetime null comment '退款完成时间',
	request_apply_user_id bigint null comment '退款请求,申请人登录名',
	refund_reason varchar(500) null comment '退款原因',
	auditor bigint null comment '审核人',
	audit_reason varchar(500) null comment '审核信息',
	audit_time datetime null comment '审核时间
',
	state tinyint(2) null comment '退款状态',
	create_time datetime null comment '创建时间',
	editor bigint null comment '修改者',
	creator bigint null comment '创建者',
	edit_time datetime null comment '最后修改时间'
)
comment '退款记录表';

create table seq_table
(
	SEQ_NAME varchar(50) not null
		primary key,
	CURRENT_VALUE bigint default '1000000002' not null,
	INCREMENT smallint(6) default '1' not null,
	REMARK varchar(100) not null
);

create table trade_payment_order
(
	id bigint auto_increment comment 'id'
		primary key,
	version int default '0' not null comment '版本号',
	create_time datetime not null comment '创建时间',
	editor varchar(100) null comment '修改者',
	product_id bigint null,
	creator varchar(100) null comment '创建者',
	edit_time datetime null comment '最后修改时间',
	status varchar(50) null comment '状态(参考枚举:orderstatusenum)',
	trx_no varchar(100) null comment '流水号',
	bank_trx_no varchar(50) null comment '银行流水订单号',
	product_name varchar(300) null comment '商品名称',
	merchant_order_no varchar(30) not null comment '商户订单号',
	order_amount int default '0' null comment '订单金额 单位分',
	order_from varchar(30) null comment '订单来源',
	order_time datetime null comment '下单时间',
	order_ip varchar(50) null comment '下单ip(客户端ip,在网关页面获取)',
	order_referer_url varchar(100) null comment '从哪个页面链接过来的',
	cancel_reason varchar(600) null comment '订单撤销原因',
	order_period smallint(6) null comment '订单有效期(单位分钟)',
	expire_time datetime null comment '到期时间',
	pay_way_code varchar(50) null comment '支付方式编号',
	success_refund_amount int null comment '成功退款总金额',
	biz_code varchar(50) null,
	app_id varchar(50) null comment '下单应用appid',
	constraint ak_key_2
		unique (merchant_order_no)
)
comment '支付订单表';

create table trade_payment_record
(
	id bigint auto_increment comment 'id'
		primary key,
	version int default '0' not null comment '版本号',
	create_time datetime null comment '创建时间',
	status varchar(30) null comment '状态(参考枚举:paymentrecordstatusenum)',
	editor bigint null comment '修改者',
	creator bigint null comment '创建者',
	edit_time datetime null comment '最后修改时间',
	product_name varchar(50) null comment '商品名称',
	merchant_order_no varchar(50) not null comment '商户订单号',
	trx_no varchar(50) not null comment '支付流水号',
	bank_order_no varchar(50) null comment '银行订单号',
	bank_trx_no varchar(50) null comment '银行流水号',
	payer_user_id bigint null comment '付款人编号',
	order_ip varchar(30) null comment '下单ip(客户端ip,从网关中获取)',
	order_referer_url varchar(200) null comment '从哪个页面链接过来的(可用于防诈骗)',
	order_amount int default '0' null comment '订单金额',
	pay_way_code varchar(50) null comment '支付方式编号',
	pay_success_time datetime null comment '支付成功时间',
	complete_time datetime null comment '完成时间',
	is_refund varchar(30) default '101' null comment '是否退款(100:是,101:否,默认值为:101)',
	refund_times int default '0' null comment '退款次数(默认值为:0)',
	success_refund_amount int null comment '成功退款总金额',
	order_from varchar(30) null comment '订单来源',
	bank_return_msg varchar(2000) null comment '第三方返回信息',
	constraint ak_key_2
		unique (trx_no)
)
comment '支付记录表';

create table wechat_app_mch
(
	appid varchar(100) not null comment '小程序id',
	mch_id bigint not null comment '商务号ID',
	primary key (appid, mch_id)
)
comment '微信小程序和商务号的配置';



create table wechat_mch
(
	id bigint auto_increment
		primary key,
	mch_id varchar(100) not null comment '微信支付分配的商户号',
	pid bigint null comment '-1-服务商 0-普通商户  服务商ID-服务商特约商户',
	partner_key varchar(200) null comment '商户平台设置的密钥key',
	cert_path varchar(100) null comment 'api证书在oss（私有块）的路径( 没有上传不支持退款)',
	state tinyint(1) null comment '状态',
	create_time datetime null comment '创建时间',
	creator bigint null comment '创建人',
	edit_time datetime null comment '最后修改时间',
	editor bigint null comment '最后修改人'
)
comment '微信商户号配置';


INSERT INTO seq_table (SEQ_NAME, CURRENT_VALUE, INCREMENT, REMARK) VALUES ('ACCOUNT_NO_SEQ', 10000000, 1, '账户--账户编号');
INSERT INTO seq_table (SEQ_NAME, CURRENT_VALUE, INCREMENT, REMARK) VALUES ('ACTIVITY_NO_SEQ', 10000006, 1, '活动--活动编号');
INSERT INTO seq_table (SEQ_NAME, CURRENT_VALUE, INCREMENT, REMARK) VALUES ('USER_NO_SEQ', 10001113, 1, '用户--用户编号');
INSERT INTO seq_table (SEQ_NAME, CURRENT_VALUE, INCREMENT, REMARK) VALUES ('TRX_NO_SEQ', 10000000, 1, '交易—-支付流水号');
INSERT INTO seq_table (SEQ_NAME, CURRENT_VALUE, INCREMENT, REMARK) VALUES ('BANK_ORDER_NO_SEQ', 10000000, 1, '交易—-银行订单号');
INSERT INTO seq_table (SEQ_NAME, CURRENT_VALUE, INCREMENT, REMARK) VALUES ('RECONCILIATION_BATCH_NO_SEQ', 10000000, 1, '对账—-批次号');

/*==============================================================*/
/* create function                                              */
/*==============================================================*/
CREATE FUNCTION `FUN_SEQ`(SEQ VARCHAR(50)) RETURNS BIGINT(20)
BEGIN
     UPDATE SEQ_TABLE
     SET CURRENT_VALUE = CURRENT_VALUE + INCREMENT
     WHERE  SEQ_NAME=SEQ;
     RETURN FUN_SEQ_CURRENT_VALUE(SEQ);
END;


CREATE FUNCTION `FUN_SEQ_CURRENT_VALUE`(SEQ VARCHAR(50)) RETURNS BIGINT(20)
BEGIN
    DECLARE VALUE INTEGER;
    SET VALUE=0;
    SELECT CURRENT_VALUE INTO VALUE
    FROM SEQ_TABLE
    WHERE SEQ_NAME=SEQ;
    RETURN VALUE;
END;

CREATE FUNCTION `FUN_SEQ_SET_VALUE`(SEQ VARCHAR(50), VALUE INTEGER) RETURNS BIGINT(20)
BEGIN
     UPDATE SEQ_TABLE
     SET CURRENT_VALUE=VALUE
     WHERE SEQ_NAME=SEQ;
     RETURN FUN_SEQ_CURRENT_VALUE(SEQ);
END;

CREATE FUNCTION  FUN_NOW()
 RETURNS DATETIME
BEGIN
RETURN now();
END;


-- 时间函数

CREATE FUNCTION `FUN_DATE_ADD`(STR_DATE VARCHAR(10), STR_INTERVAL INTEGER) RETURNS DATE
BEGIN
     RETURN date_add(STR_DATE, INTERVAL STR_INTERVAL DAY);
END;





