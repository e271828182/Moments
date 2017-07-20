package config;


import java.util.Properties;
import java.util.concurrent.Executor;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.pagehelper.PageInterceptor;
import com.mchange.v2.c3p0.ComboPooledDataSource;


@Configuration
@MapperScan(basePackages={"mappers"})
@ComponentScan(basePackages={"service.impl"})
@EnableTransactionManagement
@EnableScheduling
@EnableAsync(proxyTargetClass = true)
public class RootContextConfig implements AsyncConfigurer, SchedulingConfigurer {
	
	@Bean
	public DataSource dataSource(){
		  return new ComboPooledDataSource();
	}
	
	@Bean
	public SqlSessionFactory sqlSessonFactory(DataSource dataSource) throws Exception{
		 SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		 sessionFactory.setTypeAliasesPackage("pojo");
		 sessionFactory.setDataSource(dataSource);
//		 sessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
		 PageInterceptor pageInterceptor = new PageInterceptor();
		 Properties properties = new Properties();
		 properties.put("helperDialect", "mysql");
		 properties.put("reasonable", "true");
		 properties.put("supportMethodsArguments", "true");
		 properties.put("params", "countSql");
		 properties.put("count", "countSql");
		 properties.put("autoRuntimeDialect", "true");
		 pageInterceptor.setProperties(properties);
		 sessionFactory.setPlugins(new Interceptor[]{pageInterceptor});
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
	
	@Bean
    public ThreadPoolTaskScheduler taskScheduler()
    {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(20);
        scheduler.setThreadNamePrefix("task-");
        scheduler.setAwaitTerminationSeconds(60);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        return scheduler;
    }

    @Override
    public Executor getAsyncExecutor()
    {
        Executor executor = this.taskScheduler();
        return executor;
    }
    

    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar)
    {
        TaskScheduler scheduler = this.taskScheduler();
        registrar.setTaskScheduler(scheduler);
            
    }

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
