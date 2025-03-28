package com.green.simple_coupang_dummy.account;

import com.green.simple_coupang_dummy.Dummy;
import com.green.simple_coupang_dummy.account.model.Acc;
import com.green.simple_coupang_dummy.customer.CustomerMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class AccountDummy extends Dummy {
    final int ADD_ROW_COUNT = 10;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    AccountMapper accountMapper;

    @Test
    void generate(){
        String[] accountTypeList = {"01","02","03","04"};
        List<Integer> customerIdList = customerMapper.findIdAll();
        int maxId = accountMapper.findMaxId();
        int endRowCount = maxId + ADD_ROW_COUNT;
        for (int i = maxId +1; i <= endRowCount; i++) {
            String accountType = accountTypeList[enFaker.random().nextInt(accountTypeList.length)];
             Acc acc = Acc.builder()
                     .accountId(i)
                     .accountType(accountType)
                     .balance(enFaker.random().nextDouble() + enFaker.random().nextInt(9999999))
                     .customerId(customerIdList.get(enFaker.random().nextInt(customerIdList.size()-1)))
                     .build();

             accountMapper.save(acc);
        }
    }

}