package cz.janvrska.fg;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:mysql://localhost:3306/authme");
        config.setUsername("fg");
        config.setPassword("xxxxx");
        config.setConnectionTimeout(5000);
        config.setMaxLifetime(60*30*1000);
        ds = new HikariDataSource(config);
    }

    private DataSource() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
