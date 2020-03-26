package pl.sda.jdbc.procedure;

import java.sql.*;

public class JdbcProcedureExample {
    public static void main(String[] args) throws SQLException {
        final String driver = "com.mysql.cj.jdbc.Driver";
        final String sqlQuery = "CALL findAllCities(?)";
        ResultSet resultSet = null;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/world?serverTimezone=UTC", "root", "root");
             CallableStatement callableStatement = conn.prepareCall(sqlQuery)
        ) {
            Class.forName(driver);
            callableStatement.setString(1, "Poland");
            resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }
}

//    Procedura do wykonania na bazie danych world:
//
//    DELIMITER $$
//    CREATE PROCEDURE findAllCities(IN countryName CHAR(20))
//        BEGIN
//        SELECT name
//        FROM city
//        WHERE countrycode = (SELECT code FROM country WHERE name = countryName);
//        END;
//    $$ DELIMITER ;
