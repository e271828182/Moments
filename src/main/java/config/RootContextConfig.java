package config;


import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import security.SecurityConfiguration;

@Configuration
@MapperScan(basePackages={"mappers"})
@ComponentScan(basePackages={"service.impl"})
@EnableTransactionManagement
//@Import(SecurityConfiguration.class)
public class RootContextConfig {
	
	@Bean
	public DataSource dataSource(){
		  return new ComboPooledDataSource();
	}
	
	@Bean
	public SqlSessionFactory sqlSessonFactory(DataSource dataSource) throws Exception{
		 SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		 sessionFactory.setTypeAliasesPackage("pojo");
		 sessionFactory.setDataSource(dataSource);
		 return sessionFactory.getObject();
	}
	
	@Bean
	public SqlSession sqlSession(SqlSessionFactory sqlSessonFactory) {
		return sqlSessonFactory.openSession();
	}

	@Bean
	public DataSourceTransactionManager transactionManager(DataSource dataSource){		
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSource);
		return dataSourceTransactionManager;
		
	}
	
	

	

}
