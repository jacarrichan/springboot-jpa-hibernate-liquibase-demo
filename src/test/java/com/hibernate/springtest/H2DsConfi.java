package com.hibernate.springtest;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author wanli zhou
 * @created 2017-10-09 2:11 PM.
 */
@Configurable
@EnableTransactionManagement
public class H2DsConfi {

    @Bean
    public SpringLiquibase springLiquibase(){
        SpringLiquibase ln = new SpringLiquibase();
        ln.setDataSource(localDataSource());
        ln.setChangeLog("classpath:db/changelog/master.xml");
        return ln;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(localDataSource());
        //https://stackoverflow.com/questions/4736301/is-persistence-xml-requied-when-working-with-spring-and-hibernate
        bean.setPersistenceXmlLocation("classpath*:META-INF/persistence-local.xml");
        bean.setPersistenceUnitName("test-hibernate-local");
        bean.setPackagesToScan("com.hibernate.demo.domain");
        bean.setPersistenceProviderClass(org.hibernate.ejb.HibernatePersistence.class);

        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        bean.setJpaVendorAdapter(adapter);
        return bean;
    }

    @Bean
    public DataSource localDataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        ds.setDriverClassName("org.h2.Driver");
        return ds;
    }
    @Bean
    public PlatformTransactionManager localTransactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory().getObject());
        txManager.setDataSource(localDataSource());
        return txManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslator() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
