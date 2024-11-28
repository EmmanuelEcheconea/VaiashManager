package com.vaiashmanager.db.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
@Configuration
@EnableJpaRepositories(
        basePackages = "com.vaiashmanager.db.repository",
        entityManagerFactoryRef = "testEntityManager",
        transactionManagerRef = "testTransactionManager",
        repositoryImplementationPostfix = "TestDBImpl" // Opcional, para diferenciar implementaciones

)
public class TestDBConfig {

    // Crear un DataSource específico para la primera base de datos
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.testdb")
    public DataSource testDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:test.db");
        return dataSource;    }

    @Bean(name = "testEntityManager")
    public LocalContainerEntityManagerFactoryBean testEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(testDataSource()); // Usamos el DataSource que configuramos
        em.setPackagesToScan(new String[] { "com.vaiashmanager.db.entity" }); // Paquete donde están las entidades

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update"); // Opcional: actualiza el esquema de la base de datos
        properties.put("hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect"); // Dialecto específico para SQLite
        properties.put("hibernate.show_sql", "true"); // Muestra el SQL en la consola

        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean(name = "testTransactionManager")
    public PlatformTransactionManager testTransactionManager(@Qualifier("testEntityManager") EntityManagerFactory testEntityManager) {
        return new JpaTransactionManager(testEntityManager);
    }
}
