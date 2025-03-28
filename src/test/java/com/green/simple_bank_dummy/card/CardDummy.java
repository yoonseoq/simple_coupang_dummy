package com.green.simple_bank_dummy.card;

import com.green.simple_bank_dummy.Dummy;
import com.green.simple_bank_dummy.card.model.Card;
import com.green.simple_bank_dummy.customer.CustomerMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

class CardDummy extends Dummy {
    final int ADD_ROW_COUNT = 1_000;

    @Autowired
    CustomerMapper customerMapper;

    @Test
    void generate() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        CardMapper cardMapper = sqlSession.getMapper(CardMapper.class);

        List<Integer> customerIdList = customerMapper.findIdAll();

        System.out.printf("customerIdList size: %d\n", customerIdList.size());

        int maxId = cardMapper.findMaxId();
        int endRowCount = maxId + ADD_ROW_COUNT;
        for (int i = maxId + 1; i <= endRowCount; i++) {
            String cardNumber = String.format("%04d%04d%04d%04d", enFaker.random().nextInt(9999)
                    , enFaker.random().nextInt(9999)
                    , enFaker.random().nextInt(9999)
                    , enFaker.random().nextInt(9999));


            System.out.printf("cardNumber: %s\n", cardNumber);
            Card card = Card.builder()
                    .cardId(i)
                    .cardNumber(cardNumber)
                    .expirationDate(enFaker.date().future(enFaker.random().nextInt(100)+1, TimeUnit.HOURS, "YYYY-MM-dd"))
                    .customerId(customerIdList.get(enFaker.random().nextInt(customerIdList.size() - 1)))
                    .build();

            cardMapper.save(card);
            sqlSession.flushStatements();
        }

    }
}