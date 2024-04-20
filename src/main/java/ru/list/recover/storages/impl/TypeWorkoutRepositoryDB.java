package ru.list.recover.storages.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.list.recover.models.TypeWorkout;
import ru.list.recover.storages.TypeWorkoutRepository;

public class TypeWorkoutRepositoryDB implements TypeWorkoutRepository{

    private Connection connection;
    private String nameTable = "typeworkout";

    public TypeWorkoutRepositoryDB(Connection connection, String nameSchema) {
        this.connection = connection;
        if (nameSchema.length() > 0) {
            nameTable = nameSchema + "." + nameTable;
        }
    }

    @Override
    public boolean insert(TypeWorkout object) {
        object.setId(this.generateId());
        boolean result;
        String sql = "INSERT INTO " + nameTable + " (id, name) VALUES (?, ?)";
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, object.getId());
            statement.setString(2, object.getName());
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
    public boolean delete(TypeWorkout object) {
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
    public void update(TypeWorkout object) {
        String sql = "UPDATE " + nameTable + " SET name = ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, object.getName());
            statement.setInt(2, object.getId());
            statement.executeUpdate();
        } catch(SQLException e) {

        }
    }

    @Override
    public TypeWorkout findById(int id) {
        TypeWorkout result = null;
        String sql = "SELECT * FROM " + nameTable + " WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                result = new TypeWorkout(resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch(SQLException e) {
            result = null;
        }
        return result;
    }

    @Override
    public List<TypeWorkout> findAll() {
        List<TypeWorkout> result = new ArrayList<>();
        String sql = "SELECT * FROM " + nameTable;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                TypeWorkout typeWorkout = new TypeWorkout(resultSet.getInt("id"), resultSet.getString("name"));
                result.add(typeWorkout);
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

    private int generateId() {
        int result = 1;
        while (this.findById(result) != null) {
            result ++;
        }
        return result;
    } 


}
