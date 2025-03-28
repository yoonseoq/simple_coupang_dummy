package com.green.simple_coupang_dummy;

import net.datafaker.Faker;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.util.Locale;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class Dummy {
    @Autowired
   protected SqlSessionFactory sqlSessionFactory;

   protected Faker koFaker = new Faker(new Locale("ko")); // 한국어로 설정
   protected Faker enFaker = new Faker(new Locale("en")); // 영어로 설정

}
