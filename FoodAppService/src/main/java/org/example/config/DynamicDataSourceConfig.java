package org.example.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 刘文轩
 * @Date 2024/1/30 16:14
 */
//@Configuration
public class DynamicDataSourceConfig {

    /**
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个
     * @param statFilter
     * @return
     * @throws SQLException
     */
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.druid.food")
    @Bean(name = "food_datasource")
    public javax.sql.DataSource first() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        return dataSource;
    }
    @ConfigurationProperties(prefix="spring.datasource.druid.auth")
    @Bean(name = "auth_datasource")
    public javax.sql.DataSource second() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        return dataSource;
    }

    /**
     * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
     * @param dataSource1    数据源1
     * @param dataSource2   数据源2
     * @return
     */
    @Bean(name = "dynamicDataSource")
    public DynamicDataSource dataSource(@Qualifier("food_datasource") javax.sql.DataSource dataSource1,
                                        @Qualifier("auth_datasource") DataSource dataSource2){
        Map<Object, Object> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put(DatabaseContextHolder.FOOD_DS,dataSource1);
        dataSourceMap.put(DatabaseContextHolder.AUTH_DS,dataSource2);

        DynamicDataSource dynamicDataSource=new DynamicDataSource();
        //该方法是AbstractRoutingDataSource的方法
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        //根据实际需求，指定默认操作的库
        dynamicDataSource.setDefaultTargetDataSource(dataSource1);
        return dynamicDataSource;
    }

    /**
     * 根据数据源创建SqlSessionFactory
     * @param ds
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource ds) throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        //指定数据源(这个必须有，否则报错)
        fb.setDataSource(ds);
        return fb.getObject();
    }

}
