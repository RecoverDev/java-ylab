package ru.list.recover.storages.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.list.recover.models.User;
import ru.list.recover.storages.UserRepository;

public class UserRepositoryDB implements UserRepository{

    private Connection connection;

    public UserRepositoryDB(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insert(User object) {
        boolean result;
        String sql = "INSERT INTO users (name, login, password, role) VALUE (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, object.getName());
            statement.setString(2, object.getLogin());
            statement.setString(3, object.getPassword());
            statement.setInt(4, object.getRole());
            int count = statement.executeUpdate();
            result = (count == 1);
        } catch(SQLException e) {
            result = false;
        } 

        return result;
    }

    @Override
    public boolean delete(User object) {
        boolean result;
        String sql = "DELETE FROM users WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, object.getId());
            result = statement.execute();
        } catch(SQLException e) {
            result = false;
        }

        return result;
    }

    @Override
    public User findById(int id) {
        User result;
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = new User(resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("login");
                                resultSet.getInt("role"));
            }
        } catch(SQLException e) {
            result = null;
        }

        return result;
    }

    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("login");
                                resultSet.getInt("role"));
                result.add(user);
            }
        } catch(SQLException e) {
        }

        return result;
    }

    @Override
    public int getCount() {
        int result = 0;
        String sql = "SELECT COUNT(id) AS count FROM users";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getInt("count");
            }
        } catch(SQLException e) {
            result = 0;
        }
        return result;
    }

    @Override
    public User findByLogin(String login) {
        User result;
        String sql = "SELECT * FROM users WHERE login = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = new User(resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("login");
                                resultSet.getInt("role"));
            }
        } catch(SQLException e) {
            result = null;
        }

        return result;
    }

    @Override
    public void update(User object) {
        String sql = "UPDATE users SET name = ?, login = ?, password = ? role = ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, object.getName());
            statement.setString(2, object.getLogin());
            statement.setString(3, object.getPassword());
            statement.setInt(4, object.getRole());
            statement.setInt(5, object.getId());
        } catch(SQLException e) {

        }


    }

}
