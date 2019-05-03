package com.juyoung.infjpa.springbootjpa.account;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    DataSource dataSource;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    AccountRepository accountRepository;

    @Test
    public void DI_의존성_빈_확인() throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            DatabaseMetaData metaData = connection.getMetaData();
            System.out.println(metaData.getDriverName());
            System.out.println(metaData.getURL());
            System.out.println(metaData.getUserName());
        }
    }

    @Test
    public void 계정생성() {
        Account account = new Account();
        account.setUsername("juyoung");
        account.setPassword("pass");

        Account newAccount = accountRepository.save(account);

        assertThat(newAccount).isNotNull();
        assertThat(newAccount).isEqualTo(account);
    }

    @Test
    public void 계정생성후_이름으로_찾기() {
        Account expected = new Account();
        expected.setUsername("juyoung");
        expected.setPassword("pass");

        accountRepository.save(expected);
        Optional<Account> existAccount =accountRepository.findByUsername("juyoung");
        Account account = existAccount.get();

        assertThat(existAccount).isNotEmpty();
        assertThat(account).isEqualTo(expected);
    }

    @Test
    public void 존재하지_않는_게정() {
        String nonExistName = "test";
        Optional<Account> nonExistedAccount = accountRepository.findByUsername(nonExistName);
        assertThat(nonExistedAccount).isEmpty();
    }
}