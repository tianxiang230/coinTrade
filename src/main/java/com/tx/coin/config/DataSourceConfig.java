package com.tx.coin.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;

/**
 * Created by 你慧快乐 on 2018-1-9.
 */
@Configuration
public class DataSourceConfig {

    /**
     * 这里设置了该Bean的destroyMethod = ""是为了防止停止服务器时容器管理器两次销毁导致的异常，
     * name = "EmbeddeddataSource"用于在自动装配Bean时与其他dataSource加以区分。
     * @return
     */
    @Bean(destroyMethod = "", name = "EmbeddeddataSource")
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
        dataSourceBuilder.url("jdbc:sqlite:" + "coin_trade.db");
        dataSourceBuilder.type(SQLiteDataSource.class);
        return dataSourceBuilder.build();
    }
}
