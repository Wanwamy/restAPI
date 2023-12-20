package com.apiWeb.ProjectRestAPI.admin.controller;

import com.apiWeb.ProjectRestAPI.admin.model.UserModel;
import org.springframework.web.bind.annotation.*;
import  com.apiWeb.ProjectRestAPI.admin.SqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Admin/User")
public class UserController {

    private final SqlConnection sqlConnection;

    public UserController(SqlConnection sqlConnection) {
        this.sqlConnection = sqlConnection;

    }

    @GetMapping("/{userID}")
    public UserModel getUser(@PathVariable String userID) {
        Connection connection = sqlConnection.getConnection();

        try {
            String sql = "SELECT * FROM users WHERE userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new UserModel(
                        resultSet.getString("userID"),
                        resultSet.getString("userName"),
                        resultSet.getString("userEmail"),
                        resultSet.getString("userPhoneNumber"),
                        resultSet.getString("userRole")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sqlConnection.closeConnection();
        }

        return null;
    }

    @GetMapping("/all")
    public List<UserModel> getAllUsers() {
        Connection connection = sqlConnection.getConnection();
        List<UserModel> userList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM users";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                userList.add(new UserModel(
                        resultSet.getString("userID"),
                        resultSet.getString("userName"),
                        resultSet.getString("userEmail"),
                        resultSet.getString("userPhoneNumber"),
                        resultSet.getString("userRole")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sqlConnection.closeConnection();
        }

        return userList;
    }

    @PostMapping("/add")
    public String addUser(@RequestBody UserModel userModel) {
        Connection connection = sqlConnection.getConnection();

        try {
            String sql = "INSERT INTO users (userID, userName, userEmail, userPhoneNumber, userRole) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userModel.getUserID());
            preparedStatement.setString(2, userModel.getUserName());
            preparedStatement.setString(3, userModel.getUserEmail());
            preparedStatement.setString(4, userModel.getUserPhoneNumber());
            preparedStatement.setString(5, userModel.getUserRole());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return "User added successfully";
            } else {
                return "Failed to add user";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error adding user";
        } finally {
            sqlConnection.closeConnection();
        }
    }

    @PutMapping("/update/{userID}")
    public String updateUser(@PathVariable String userID, @RequestBody UserModel updatedUser) {
        Connection connection = sqlConnection.getConnection();

        try {
            String sql = "UPDATE users SET userName=?, userEmail=?, userPhoneNumber=?, userRole=? WHERE userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, updatedUser.getUserName());
            preparedStatement.setString(2, updatedUser.getUserEmail());
            preparedStatement.setString(3, updatedUser.getUserPhoneNumber());
            preparedStatement.setString(4, updatedUser.getUserRole());
            preparedStatement.setString(5, userID);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return "User updated successfully";
            } else {
                return "Failed to update user";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error updating user";
        } finally {
            sqlConnection.closeConnection();
        }
    }

    @DeleteMapping("/delete/{userID}")
    public String deleteUser(@PathVariable String userID) {
        Connection connection = sqlConnection.getConnection();

        try {
            String sql = "DELETE FROM users WHERE userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userID);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return "User deleted successfully";
            } else {
                return "Failed to delete user";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error deleting user";
        } finally {
            sqlConnection.closeConnection();
        }

    }
}