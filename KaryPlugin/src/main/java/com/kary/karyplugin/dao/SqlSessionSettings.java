package com.kary.karyplugin.dao;

import java.io.IOException;
import java.io.InputStream;

import com.mysql.cj.log.Log;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

/**
 * @author:123
 */
public class SqlSessionSettings {
    private SqlSessionFactory sqlSessionFactory;
    public Configuration getConfiguration() {
        Configuration configuration = new Configuration();

        // 将下划线转换为驼峰命名
        configuration.setMapUnderscoreToCamelCase(true);

        // 设置数据源
        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setDriver("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://rm-cn-nwy3hi2n70005j8o.rwlb.rds.aliyuncs.com:3306/gameboys_666?userSSL=false;useUnicode=true;characterEncoding=UTF-8");
        dataSource.setUsername("gameboys");
        dataSource.setPassword("gameboys_666");

        // 设置事务管理器和环境
        Environment environment = new Environment("development", new JdbcTransactionFactory(), dataSource);
        configuration.setEnvironment(environment);

        // 加载Mapper配置文件
        Class<?>[] mappers = {RecordMapper.class, GamesMapper.class};
        for (Class<?> mapper : mappers) {
            configuration.addMapper(mapper);
        }

        return configuration;
    }
    public SqlSessionSettings() {
        //获取SqlSessionFactory对象
        sqlSessionFactory=new SqlSessionFactoryBuilder().build(getConfiguration());
    }
    public SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
}
