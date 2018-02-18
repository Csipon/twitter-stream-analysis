package com.bobbbaich.fb.bot.config;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.postgresql.ds.PGPoolingDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;

@Slf4j
@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String HIBERNATE_BATCH_SIZE = "hibernate.batch-size";
    private static final String HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl-auto";
    private static final String HIBERNATE_CURRENT_SESSION_CONTEXT_CLASS = "hibernate.current-session-context-class";

    @Resource
    private Environment env;

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.bobbbaich.fb.bot");
        sessionFactory.setHibernateProperties(getHibernateProperties());
        return sessionFactory;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put(DIALECT, env.getRequiredProperty(HIBERNATE_DIALECT));
        properties.put(SHOW_SQL, env.getRequiredProperty(HIBERNATE_SHOW_SQL));
        properties.put(STATEMENT_BATCH_SIZE, env.getRequiredProperty(HIBERNATE_BATCH_SIZE));
        properties.put(HBM2DDL_AUTO, env.getRequiredProperty(HIBERNATE_HBM2DDL_AUTO));
        properties.put(CURRENT_SESSION_CONTEXT_CLASS, env.getRequiredProperty(HIBERNATE_CURRENT_SESSION_CONTEXT_CLASS));
        return properties;
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
        driverManagerDataSource.setUrl("jdbc:postgresql://localhost:5432/social");
        driverManagerDataSource.setUsername("postgres");
        driverManagerDataSource.setPassword("123123");
        return driverManagerDataSource;
    }
}