package com.apiWeb.ProjectRestAPI.admin;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class SqlConnection {

    public Connection connection;
    public SqlConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url ="jdbc:mysql://localhost:3306/apitest";
            String username = "root";
            String password = "";
            connection = DriverManager.getConnection(url,username,password);

        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public Connection getConnection(){
        return connection;
    }
    public void closeConnection(){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
