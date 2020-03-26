package pl.sda.jdbc.connection_pooling;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class DataBaseUtil {

    private static DataBaseUtil dbUtil;

    // obiekt C3P0 - zarządza połaczeniami do bazy danych
    private ComboPooledDataSource connectionPool;

    private DataBaseUtil() throws PropertyVetoException {
        connectionPool = new ComboPooledDataSource();

        // PODSTAWOWE INFORMACJE WYMAGANE DO POŁĄCZENIA Z BAZĄ DANYCH
        connectionPool.setDriverClass("com.mysql.cj.jdbc.Driver");
        connectionPool.setJdbcUrl("jdbc:mysql://localhost:3306/world?useSSL=false&serverTimezone=UTC");
        connectionPool.setUser("root");
        connectionPool.setPassword("root");

        // KONFIGURACJA PULI POŁĄCZEŃ:

        //początkowa liczba połaczeń
        connectionPool.setInitialPoolSize(5);

        // min. dostępna liczba podtrzymywanych połączeń
        connectionPool.setMinPoolSize(5);

        // max liczba podtrzymywanych połączeń
        connectionPool.setMaxPoolSize(20);

        // ilość dodatkowych połczeń, która ma zostać otworzona, gdy wszystkie są zajęte
        connectionPool.setAcquireIncrement(5);

        // maxymalny czas podtrzymywania połczenia w sekundach
        connectionPool.setMaxIdleTime(3600);
    }

    public Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

    public void close() {
        connectionPool.close();
    }

    // implementujemy Singleton
    public static DataBaseUtil getInstance() {
        if (dbUtil == null) {
            try {
                dbUtil = new DataBaseUtil();
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
        }
        return dbUtil;
    }
}
