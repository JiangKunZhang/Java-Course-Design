/**
 * @author PineappleSnow
 * @version 1.0
 * @date 2019/12/19 23:09
 */

import java.sql.*;
import java.util.ArrayList;

public class JDBC {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public JDBC() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Primary_School?useSSL=false&serverTimezone=Hongkong&characterEncoding=utf-8&autoReconnect=true";
            //jdbc:mysql://localhost:3306/Primary_School
            //jdbc:jdbc:mysql://localhost:3306/Primary_School/test?useUnicode=true&amp;characterEncoding=utf-8&amp;useSSL=false&amp;serverTimezone=GMT%2B8
            String username = "root";
            String password = "0331";
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean findUserPassword(String username, String password) {
        String sql = "SELECT * FROM Primary_School.user where username = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("password").equals(password)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insertSchoolInfo(SchoolInfo school) {
        String sql = "INSERT INTO Primary_School.school (name, address, phone, postcode, principalname) VALUES (?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, school.getName());
            preparedStatement.setString(2, school.getAddress());
            preparedStatement.setString(3, school.getPhone());
            preparedStatement.setString(4, school.getPostcode());
            preparedStatement.setString(5, school.getPrincipalname());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSchoolInfo(SchoolInfo school) {
        String sql = "UPDATE Primary_School.school SET name = ?, address = ?, phone = ?, postcode = ?, principalname = ? WHERE (id = ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, school.getName());
            preparedStatement.setString(2, school.getAddress());
            preparedStatement.setString(3, school.getPhone());
            preparedStatement.setString(4, school.getPostcode());
            preparedStatement.setString(5, school.getPrincipalname());
            preparedStatement.setInt(6, school.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSchoolInfo(SchoolInfo school) {
        String sql = "DELETE FROM Primary_School.school WHERE (id = ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, school.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<SchoolInfo> selectSchoolInfo(boolean isAll, String condition, String content) {
        String sql = "SELECT * FROM Primary_School.school";
        ArrayList<SchoolInfo> res = new ArrayList<>();
        try {
            if (isAll) {
                preparedStatement = connection.prepareStatement(sql);
            } else {
                switch (condition) {
                    case "id":
                        sql = sql + " WHERE id = ?";
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setInt(1, Integer.parseInt(content));
                        break;
                    case "name":
                        sql = sql + " WHERE name = ?";
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, content);
                        break;
                    case "address":
                        sql = sql + " WHERE address = ?";
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, content);
                        break;
                    case "phone":
                        sql = sql + " WHERE phone = ?";
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, content);
                        break;
                    case "postcode":
                        sql = sql + " WHERE postcode = ?";
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, content);
                        break;
                    case "principalname":
                        sql = sql + " WHERE principalname = ?";
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, content);
                        break;
                }
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                res.add(new SchoolInfo(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}

