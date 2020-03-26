package pl.sda.jdbc.procedure;

import java.sql.*;

public class JdbcProcedureOutParametersExample {
    public static void main(String[] args) throws SQLException {
        final String driver = "com.mysql.cj.jdbc.Driver";
        final String sqlQuery = "CALL getPopulationByCountry(?, ?)";
        ResultSet resultSet = null;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/world?serverTimezone=UTC", "root", "root");
             CallableStatement callableStatement = conn.prepareCall(sqlQuery)
        ) {
            Class.forName(driver);
            callableStatement.setString(1, "Poland");
            callableStatement.registerOutParameter(2, Types.INTEGER);
            callableStatement.execute();

            System.out.println("Ludność danego kraju wynosi: " + callableStatement.getInt(2));

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
//DELIMITER $$
//    CREATE PROCEDURE getPopulationByCountry(countryName CHAR(20), OUT countryPopulation INT)
//        BEGIN
//        SELECT Population INTO countryPopulation FROM country WHERE name = countryName;
//        END;
//$$ DELIMITER ;
