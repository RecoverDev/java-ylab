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
    private String nameTable = "users";

    public UserRepositoryDB(Connection connection, String nameSchema) {
        this.connection = connection;
        if (nameSchema.length() > 0) {
            nameTable = nameSchema + "." + nameTable;
        }
    }

    @Override
    public boolean insert(User object) {
        boolean result;
        object.setId(this.generateId());
        String sql = "INSERT INTO " + nameTable + " (id, name, login, password, role) VALUES (?, ?, ?, ?, ?)";
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, object.getId());
            statement.setString(2, object.getName());
            statement.setString(3, object.getLogin());
            statement.setString(4, object.getPassword());
            statement.setInt(5, object.getRole());
            int count = statement.executeUpdate();
            result = (count == 1);
            if (result) {
                connection.commit();
            }
        } catch(SQLException e) {
            try {
                connection.rollback();
            } catch(SQLException ex) {

            }
            result = false;
        } 

        return result;
    }

    @Override
    public boolean delete(User object) {
        boolean result;
        String sql = "DELETE FROM " + nameTable + " WHERE id = ?";
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
        User result = null;
        String sql = "SELECT * FROM " + nameTable + " WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = new User(resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("login"),
                                resultSet.getString("password"),
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
        String sql = "SELECT * FROM " + nameTable;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("login"),
                                resultSet.getString("password"),
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
        String sql = "SELECT COUNT(*) AS count FROM " + nameTable;
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
        User result = null;
        String sql = "SELECT * FROM " + nameTable + " WHERE login = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = new User(resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("login"),
                                resultSet.getString("password"),
                                resultSet.getInt("role"));
            }
        } catch(SQLException e) {
            result = null;
        }

        return result;
    }

    @Override
    public void update(User object) {
        String sql = "UPDATE " + nameTable + " SET name = ?, login = ?, password = ? role = ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, object.getName());
            statement.setString(2, object.getLogin());
            statement.setString(3, object.getPassword());
            statement.setInt(4, object.getRole());
            statement.setInt(5, object.getId());
            statement.executeUpdate();
        } catch(SQLException e) {

        }
    }

    private int generateId() {
        int result = 1;
        while (this.findById(result) != null) {
            result ++;
        }
        return result;
    } 

}
