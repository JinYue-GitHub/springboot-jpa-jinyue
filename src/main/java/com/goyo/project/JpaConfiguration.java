/*package com.goyo.project;

import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

//@Configuration
//@EnableJpaRepositories(basePackages = "com.goyo.project",
//    entityManagerFactoryRef = "entityManagerFactory",
//    transactionManagerRef = "transactionManager",
//    repositoryBaseClass = MyRepositoryImpl.class)

//
//@EnableJpaRepositories(basePackages = "com.goyo.project",
//entityManagerFactoryRef = "entityManagerFactory",
//transactionManagerRef = "transactionManager")


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.goyo.project",
entityManagerFactoryRef = "entityManagerFactory",
transactionManagerRef = "transactionManager")
public class JpaConfiguration {
@Autowired
private Environment environment;

	@Bean(name = "datasource")
	public DataSource getDataSource() {
	    DruidDataSource dataSource = new DruidDataSource();
	    dataSource.setUrl(environment.getProperty("spring.datasource.url"));
	    dataSource.setUsername(environment.getProperty("spring.datasource.username"));
	    dataSource.setPassword(environment.getProperty("spring.datasource.password"));
	    dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
	    // 配置初始化大小、最小、最大 
	    dataSource.setInitialSize(1);
	    dataSource.setMinIdle(1);
	    dataSource.setMaxActive(10);
	    //配置获取连接等待超时的时间 
	    dataSource.setMaxWait(1200);
	    // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 
	    dataSource.setTimeBetweenEvictionRunsMillis(6000);
	    // 配置一个连接在池中最小生存的时间，单位是毫秒
	    dataSource.setMinEvictableIdleTimeMillis(6000);
	    dataSource.setTestWhileIdle(true);
	    //这里建议配置为TRUE，防止取到的连接不可用
	    dataSource.setTestOnBorrow(true);
	    dataSource.setTestOnReturn(false);
	    //打开PSCache，并且指定每个连接上PSCache的大小
	    dataSource.setPoolPreparedStatements(true);
	    dataSource.setMaxPoolPreparedStatementPerConnectionSize(500);
	    //这里配置提交方式，默认就是TRUE，可以不用配置
	    dataSource.setDefaultAutoCommit(true);
	    //验证连接有效与否的SQL，不同的数据配置不同
	    dataSource.setValidationQuery(" SELECT 1 FROM DUAL ");
	    
	    return dataSource;
	}  
	
	
	
//	 * Provider specific adapter.
	 
	
	
//	 * Entity Manager Factory setup.
//	
	
	@Bean(name = "entityManagerFactory")
	public EntityManagerFactory entityManagerFactory(@Qualifier("jpaVendorAdapter") JpaVendorAdapter jpavendoradapter,@Qualifier("datasource") DataSource datasource) {
	    LocalContainerEntityManagerFactoryBean factoryBean = new 
	    LocalContainerEntityManagerFactoryBean();
	    factoryBean.setDataSource(datasource);
//	    factoryBean.setPackagesToScan(new String[] { "com.goyo.project.core" });
	    factoryBean.setJpaVendorAdapter(jpavendoradapter);
	    factoryBean.setPackagesToScan("com.goyo.project.core");
	    factoryBean.setPersistenceUnitName("persistenceUnit");
//	    factoryBean.setJpaProperties(jpaProperties()); afterPropertiesSet
	    factoryBean.afterPropertiesSet();
	    
//	    lef.setDataSource(dataSource);
//		lef.setJpaVendorAdapter(jpaVendorAdapter);
//		lef.setPackagesToScan("cn.net.minu");
//		lef.setPersistenceUnitName("persistenceUnitCsms");
//		lef.afterPropertiesSet();
	    
	    
//	    EntityManagerFactory entityFactory = factoryBean.getObject();
//	    EntityManager createEntityManager = entityFactory.createEntityManager();
//	    System.out.println(createEntityManager);
	    return factoryBean.getObject();
	}
	
	@Bean(name="jpaVendorAdapter")
	public JpaVendorAdapter jpaVendorAdapter() {
	    HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
	    hibernateJpaVendorAdapter.setDatabase(Database.ORACLE);
	    hibernateJpaVendorAdapter.setGenerateDdl(true);
	    hibernateJpaVendorAdapter.setShowSql(true);
	    return hibernateJpaVendorAdapter;
	}
	
	@Bean(name = "entityManagerCsms")
	public EntityManager entityManager(
			@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return entityManagerFactory.createEntityManager();
	}

	
	
//	 * Here you can specify any provider specific properties.
	 
//	private Properties jpaProperties() {
//	    Properties properties = new Properties();
//	    properties.put("hibernate.dialect", environment.getRequiredProperty("spring.jpa.properties.hibernate.dialect"));
//	    return properties;
//	}
	@Bean(name="transactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory")EntityManagerFactory emf) {
	    JpaTransactionManager txManager = new JpaTransactionManager();
	    txManager.setEntityManagerFactory(emf);
	    return txManager;
	}
}*/