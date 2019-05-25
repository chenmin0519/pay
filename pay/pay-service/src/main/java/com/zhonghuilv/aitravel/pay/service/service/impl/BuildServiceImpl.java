package com.zhonghuilv.aitravel.pay.service.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.zhonghuilv.aitravel.pay.service.exception.PayException;
import com.zhonghuilv.aitravel.pay.service.mapper.BuildMapper;
import com.zhonghuilv.aitravel.pay.service.service.BuildService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@Service
public class BuildServiceImpl implements BuildService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 对账批次号前缀
     **/
    private static final String RECONCILIATION_BATCH_NO = "5555";

    /**
     * 银行订单号
     **/
    private static final String BANK_ORDER_NO_PREFIX = "6666";
    /**
     * 支付流水号前缀
     **/
    private static final String TRX_NO_PREFIX = "7777";


    /**
     * 退款流水号前缀
     */
    private static final String REFUND_TRX_NO_PREFIX = "REFUND";

    @Autowired
    BuildMapper buildMapper;

    private static final DateTimeFormatter DEFAULT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 获取支付流水号
     **/
    @Override
    public String buildTrxNo() {

        String trxNoSeq = this.getSeqNextValue("TRX_NO_SEQ");
        // 20位的支付流水号规范：'8888' + yyyyMMdd(时间) + 序列的后8位
        String dateString = LocalDate.now().format(DEFAULT_DATE_FORMAT);
        return TRX_NO_PREFIX + dateString + trxNoSeq.substring(trxNoSeq.length() - 8, trxNoSeq.length());
    }

    /**
     * 获取退款流水号
     **/
    @Override
    public String buildRefundTrxNo() {
        String trxNoSeq = this.getSeqNextValue("REFUND_TRX_NO_SEQ");
        // 20位的支付流水号规范：'8888' + yyyyMMdd(时间) + 序列的后8位
        String substring = trxNoSeq.substring(trxNoSeq.length() - 8, trxNoSeq.length());
        String dateString = LocalDate.now().format(DEFAULT_DATE_FORMAT);
        return new StringBuilder(REFUND_TRX_NO_PREFIX)
                .append(dateString)
                .append(substring)
                .toString();
    }

    /**
     * 获取银行订单号
     **/
    @Override
    public String buildBankOrderNo() {

        String bankOrderNoSeq = this.getSeqNextValue("BANK_ORDER_NO_SEQ");
        // 20位的用户编号规范：'8888' + yyyyMMdd(时间) + 序列的后8位
        String dateString = LocalDate.now().format(DEFAULT_DATE_FORMAT);
        return BANK_ORDER_NO_PREFIX + dateString + bankOrderNoSeq.substring(bankOrderNoSeq.length() -
                8, bankOrderNoSeq.length());
    }

    /**
     * 获取对账批次号
     **/
    public String buildReconciliationNo() {
        String batchNoSeq = this.getSeqNextValue("RECONCILIATION_BATCH_NO_SEQ");
        String dateString = LocalDate.now().format(DEFAULT_DATE_FORMAT);
        return RECONCILIATION_BATCH_NO + dateString + batchNoSeq.substring(batchNoSeq.length() - 8,
                batchNoSeq.length());
    }

    /**
     * 根据序列名称,获取序列值
     */
    @Transactional(rollbackFor = Exception.class)
    public String getSeqNextValue(String seqName) {
        String seqNextValue = null;
        try {
            seqNextValue = buildMapper.getSeqNextValue(seqName);
        } catch (Exception e) {
            logger.error("生成序号异常：" + "seqName=" + seqName, e);
            throw PayException.DB_GET_SEQ_NEXT_VALUE_ERROR;
        }
        if (StringUtils.isEmpty(seqNextValue)) {
            throw PayException.DB_GET_SEQ_NEXT_VALUE_ERROR;
        }
        return seqNextValue;
    }
}
