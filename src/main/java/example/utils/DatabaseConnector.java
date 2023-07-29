package example.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {
    private static ComboPooledDataSource dataSource;

    static {
        try {
            // 加载配置文件
            Properties properties = new Properties();
            InputStream inputStream = DatabaseConnector.class.getClassLoader().getResourceAsStream("config.properties");
            properties.load(inputStream);
            inputStream.close();

            // 创建 C3P0 数据源对象
            dataSource = new ComboPooledDataSource();
            dataSource.setDriverClass(properties.getProperty("db.driver")); // 设置数据库驱动程序类名
            dataSource.setJdbcUrl(properties.getProperty("db.url"));
            dataSource.setUser(properties.getProperty("db.username"));
            dataSource.setPassword(properties.getProperty("db.password"));
        } catch (IOException | PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
