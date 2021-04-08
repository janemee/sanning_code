package com.huimi.core.service.system;


import com.huimi.core.po.system.Bank;
import com.huimi.core.service.base.GenericService;

/**
 * 银行信息管理表业务相关Service接口<br>
 *
 * @author fengting
 * @date 2018-09-29 04:30:19
 */
public interface BankService extends GenericService<Integer, Bank> {
    Bank findById(Long id);

    void addBank(Bank bank);
}
