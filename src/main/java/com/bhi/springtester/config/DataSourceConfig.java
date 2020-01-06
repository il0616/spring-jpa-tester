package com.bhi.springtester.config;

import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.bhi.springtester")
public class DataSourceConfig {
    @Bean
    public DataSource getDataSource() {
        MariaDbDataSource mariaDbDataSource = new MariaDbDataSource("jdbc:mariadb://127.0.0.1:3306/test_db");
        try {
            mariaDbDataSource.setUser("root");
            mariaDbDataSource.setPassword("root123");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mariaDbDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(getDataSource());
        em.setPackagesToScan("com.bhi.springtester");

        // persistence 설정
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "validate");
        properties.setProperty("hibernate.show_sql", "true");

        // 각 구현체의 프로퍼티 확장 및 설정
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        // jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
        em.setJpaVendorAdapter(jpaVendorAdapter);
        em.setJpaProperties(properties);
        return em;
    }

    // Transaction 설정
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
