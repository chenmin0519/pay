package com.zhonghuilv.aitravel.pay.service.service.impl;

import com.zhonghuilv.aitravel.common.excption.ParameterNotValidException;
import com.zhonghuilv.aitravel.pay.intf.pojo.IntegralAccount;
import com.zhonghuilv.aitravel.pay.service.mapper.IntegralAccountMapper;
import com.zhonghuilv.aitravel.pay.service.service.IntegralAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@Service
@Slf4j
public class IntegralAccountServiceImpl implements IntegralAccountService {

    @Autowired
    private IntegralAccountMapper integralAccountMapper;

    private IntegralAccount findByUserIdAndScenicId(Long userId, Long scenicId, boolean throwError) {
        IntegralAccount query = new IntegralAccount();
        query.setScenicId(scenicId);
        query.setUserId(userId);
        IntegralAccount integralAccount = integralAccountMapper.selectOne(query);
        if (throwError && integralAccount == null) {
            throw new ParameterNotValidException("用户" + userId + "景区" + scenicId + "积分账号不存在");
        }
        return integralAccount;
    }


    @Override
    public IntegralAccount freeze(Long userId, Long scenicId, Integer integral) {

        IntegralAccount integralAccount = findByUserIdAndScenicId(userId, scenicId, true);

        if (integralAccount.getBalance().compareTo(integral) < 0) {
            throw new ParameterNotValidException("积分不足！");
        }
        //设置修改对象
        IntegralAccount update = new IntegralAccount();
        update.setId(integralAccount.getId());
        update.setVersion(integralAccount.getVersion());
        update.setUnbalance(integralAccount.getUnbalance() + integral);
        update.setBalance(integralAccount.getBalance() - integral);
        // TODO 摘要算法实现
        updateByIdForOptimistic(update);
        return update;
    }

    @Transactional(rollbackFor = Exception.class)
    public IntegralAccount credit(Long userId, Long scenicId, Integer integral) {

        IntegralAccount integralAccount = findByUserIdAndScenicId(userId, scenicId, true);

        IntegralAccount update = new IntegralAccount();
        update.setId(integralAccount.getId());
        update.setVersion(integralAccount.getVersion());
        update.setTotalIncome(integralAccount.getTotalIncome() + integral);
        update.setBalance(integralAccount.getBalance() + integral);
        updateByIdForOptimistic(update);

        return update;
    }

    public IntegralAccount debit(Long userId, Long scenicId, Integer integral) {

        IntegralAccount integralAccount = findByUserIdAndScenicId(userId, scenicId, true);
        Integer balance = integralAccount.getBalance();
        if (balance.compareTo(integral) < 0) {
            throw new ParameterNotValidException("积分不足");
        }
        IntegralAccount update = new IntegralAccount();
        update.setId(integralAccount.getId());
        update.setVersion(integralAccount.getVersion());
        update.setTotalExpend(integralAccount.getTotalExpend() + integral);
        update.setBalance(balance - integral);
        updateByIdForOptimistic(update);
        return update;
    }


    /**
     * 通过主键乐观锁修改
     *
     * @param integralAccount
     * @return
     */
    private void updateByIdForOptimistic(IntegralAccount integralAccount) {
        Integer version = integralAccount.getVersion();
        integralAccount.setVersion(version + 1);

        Example example = new Example(IntegralAccount.class);
        example.createCriteria().andEqualTo("id", integralAccount.getId())
                .andEqualTo("version", version);
        int result = integralAccountMapper.updateByExampleSelective(integralAccount, example);
        if (result == 0) {
            while (result == 0) {
                try {
                    Thread.sleep(100L);
                    result = integralAccountMapper.updateByExampleSelective(integralAccount, example);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

    }
}
