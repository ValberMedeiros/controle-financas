package br.com.dev.valber.medeiros.controleficancas.config;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import javax.sql.DataSource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class DAOConfigTest {

    @InjectMocks
    private DAOConfig daoConfig = new DAOConfig() {
        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        }
    };

    @Mock
    private DataSource dataSource;

    @Test
    void jdbcTemplate() {
        assertNotNull(ReflectionTestUtils.invokeMethod(daoConfig, "jdbcTemplate", dataSource));
    }
}