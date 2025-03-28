package com.green.simple_bank_dummy.customer;

import com.green.simple_bank_dummy.Dummy;
import com.green.simple_bank_dummy.customer.model.Customer;
import net.datafaker.Faker;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)// 리플레이스 안함
@Rollback(false) //응 롤백 안해요
class CustomerDummy extends Dummy {

    @Autowired private SqlSessionFactory sqlSessionFactory; // 이거 필요함 factory 는 객체만드는 것


    final int addRowCount = 100_000;

    @Test
    void generate() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH); //db 연결해주는 친구. 객체화가된 오픈세션
        CustomerMapper customerMapper = sqlSession.getMapper(CustomerMapper.class); //이미 만들어진것 달라는 의미

        int maxId = customerMapper.findMaxId();// findMaxId 마지막 id값 +1
        int endRowCount = maxId + addRowCount;
        for (int i = maxId + 1; i <= endRowCount; i++) {
            Customer customer = Customer.builder()
                    .customerId(i)
                    .name(koFaker.name().lastName() + koFaker.name().firstName())
                    .email(i + enFaker.internet().emailAddress())
                    .build();


            customerMapper.save(customer);
            sqlSession.flushStatements();

        }
    }
}