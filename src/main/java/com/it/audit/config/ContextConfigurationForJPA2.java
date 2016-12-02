package com.it.audit.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@Import(PropertyConfig.class)
@ComponentScan(basePackages = { "com.it.audit.domain" })
@EnableJpaRepositories(
		basePackages = { "com.it.audit.persistence.dao" },
		entityManagerFactoryRef = "defaultEntityManagerFactory",
		transactionManagerRef = "defaultJpaTransactionManager",
		includeFilters = {},
		excludeFilters = {})
@EnableTransactionManagement
@Slf4j
public class ContextConfigurationForJPA2 implements TransactionManagementConfigurer {

    @Value("${jdbc.driverclass}")
    private String driverClassName;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    @Value("${hibernate.sql.dialect}")
    String hibernameSqlDialect;
    @Value("${hibernate.sql.generateddl}")
    Boolean hibernateGenerateDdl;
    @Value("${hibernate.sql.show}")
    Boolean hibernateShowSql;

    private boolean isUsernameEmpty() {
        return this.username == null || this.username.isEmpty() || "${jdbc.username}".equals(this.username);
    }

    private boolean isPasswordEmpty() {
        return this.password == null || this.password.isEmpty() || "${jdbc.password}".equals(this.password);
    }

    @Bean(name = "defaultDataSource", destroyMethod = "close")
    public DataSource defaultDataSource() {
        if (log.isDebugEnabled()) {
            log.debug("driverClassName: {}, url: {}", new Object[] { this.driverClassName, this.url });
            log.debug("username empty: {}", this.isUsernameEmpty());
            log.debug("password empty: {}", this.isPasswordEmpty());
        }

        final org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setDriverClassName(this.driverClassName);
        dataSource.setUrl(this.url);
        dataSource.setUsername(!this.isUsernameEmpty() ? this.username : "");
        dataSource.setPassword(!this.isPasswordEmpty() ? this.password : "");
        dataSource.setInitialSize(10);
        dataSource.setMaxActive(10);
        dataSource.setMaxIdle(5);
        dataSource.setMinIdle(5);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(true);
        dataSource.setTestWhileIdle(true);
        //test pool miss
        dataSource.setLogAbandoned(true);
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(60);
        
        return dataSource;
    }

    @Bean(name = "jpaVendorAdapter")
    public JpaVendorAdapter jpaVendorAdapter() {
        if (log.isDebugEnabled()) {
            log.debug("sqlDialect: {}, generateDdl: {}, showSql: {}", new Object[] { // 
                    hibernameSqlDialect, hibernateGenerateDdl, hibernateShowSql });
        }

        final HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabasePlatform(hibernameSqlDialect);
        adapter.setGenerateDdl(hibernateGenerateDdl);
        adapter.setShowSql(hibernateShowSql);
        return adapter;
    }

    /**
     * JpaDialect不同于hibernateSqlDialect
     * 
     * @return
     */
    @Bean(name = "jpaDialect")
    public JpaDialect jpaDialect() {
        return new HibernateJpaDialect();
    }

    @Bean(name = "defaultEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPersistenceUnitName("default-persistence-unit");
        factoryBean.setJpaDialect(this.jpaDialect());
        factoryBean.setJpaVendorAdapter(this.jpaVendorAdapter());
        factoryBean.setDataSource(this.defaultDataSource());
        return factoryBean;
    }

    @Bean(name = "defaultJpaTransactionManager")
    public JpaTransactionManager defaultJpaTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        
        EntityManagerFactory factory = this.entityManagerFactoryBean().getObject();
        transactionManager.setEntityManagerFactory(factory);
        return transactionManager;
    }

    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return this.defaultJpaTransactionManager();
    }

    @Bean
    public org.springframework.jdbc.core.JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(this.defaultDataSource());
    }

    @Bean
    public org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(this.defaultDataSource());
    }

    @Bean
    public static PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        final PersistenceExceptionTranslationPostProcessor processor = new PersistenceExceptionTranslationPostProcessor();
        return processor;
    }

    @Bean
    public static PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor() {
        final PersistenceAnnotationBeanPostProcessor processor = new PersistenceAnnotationBeanPostProcessor();
        //processor.setPersistenceUnits(persistenceUnits);
        processor.setDefaultPersistenceUnitName("default-persistence-unit");
        return processor;
    }
}
