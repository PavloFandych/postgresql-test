package org.total.postgre.config;

import com.zaxxer.hikari.HikariDataSource;
import net.sf.ehcache.config.CacheConfiguration;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Pavlo.Fandych
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("org.total.postgre.repository")
@ComponentScan(value = { "org.total.postgre" }, lazyInit = true)
@EnableCaching
public class Config {

    @Bean
    DataSource getDataSource() {
        final HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/indexer");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgresqlpass");
        dataSource.setMaximumPoolSize(20);

        return dataSource;
    }

    @Bean(name = "entityManagerFactory")
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(getDataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan("org.total.postgre.entity");
        entityManagerFactoryBean.setJpaProperties(hibernateProperties());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean(destroyMethod = "shutdown")
    net.sf.ehcache.CacheManager ehCacheManager() {
        final CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setName("applicationCache");
        cacheConfiguration.setMaxEntriesLocalHeap(10000);
        cacheConfiguration.setMaxEntriesLocalDisk(1000000);
        cacheConfiguration.setMemoryStoreEvictionPolicy("LRU");

        /*
         * timeToIdle – The maximum number of seconds
         * an element can exist in the cache
         * without being accessed
         * */
        cacheConfiguration.setTimeToIdleSeconds(60);

        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        config.addCache(cacheConfiguration);

        return net.sf.ehcache.CacheManager.newInstance(config);
    }

    @Bean(name = "springCashManager")
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager());
    }

    private Properties hibernateProperties() {
        final Properties properties = new Properties();
        properties.setProperty("hibernate.order_updates", "true");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.globally_quoted_identifiers", "false");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.c3p0.min_size", "10");
        properties.setProperty("hibernate.c3p0.max_size", "20");
        properties.setProperty("hibernate.c3p0.acquire_increment", "1");
        properties.setProperty("hibernate.c3p0.idle_test_period", "3000");
        properties.setProperty("hibernate.c3p0.max_statements", "50");
        properties.setProperty("hibernate.c3p0.timeout", "1800");
        properties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
        properties.setProperty("hibernate.cache.use_second_level_cache", "true");

        properties
                .setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");

        return properties;
    }
}
