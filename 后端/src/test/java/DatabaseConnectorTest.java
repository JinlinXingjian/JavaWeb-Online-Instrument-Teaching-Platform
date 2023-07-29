


import example.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnectorTest {
    public static void main(String[] args) throws SQLException {
        System.setProperty("file.encoding", "UTF-8");
        // 获取数据库连接
        Connection connection = DatabaseConnector.getConnection();
        connection.createStatement().execute("SET NAMES utf8mb4;");

        if (connection != null) {
            try {
                // 查询admins表的所有内容
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM admins");
                ResultSet resultSet = statement.executeQuery();

                // 输出结果
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String email = resultSet.getString("email");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String registrationDate = resultSet.getString("registration_date");

                    System.out.println("ID: " + id);
                    System.out.println("Username: " + username);
                    System.out.println("Password: " + password);
                    System.out.println("Email: " + email);
                    System.out.println("First Name: " + firstName);
                    System.out.println("Last Name: " + lastName);
                    System.out.println("Registration Date: " + registrationDate);
                    System.out.println("--------------------");
                }

                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
