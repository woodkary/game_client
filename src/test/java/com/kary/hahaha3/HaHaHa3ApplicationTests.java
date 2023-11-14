package com.kary.hahaha3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class HaHaHa3ApplicationTests {
    DriverManagerDataSource dataSource;
    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

}
