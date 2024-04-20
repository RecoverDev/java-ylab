package ru.list.recover.storages.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.list.recover.models.TypeWorkout;
import ru.list.recover.models.Workout;
import ru.list.recover.storages.WorkoutRepository;

public class WorkoutRepositoryDB implements WorkoutRepository{

    private Connection connection;
    private String nameTable = "workout";
    private String joinNameTable = "typeworkout";

    public WorkoutRepositoryDB(Connection connection, String nameSchema) {
        this.connection = connection;
        if (nameSchema.length() > 0) {
            nameTable = nameSchema + "." + nameTable;
        }
    }

    @Override
    public boolean insert(Workout object) {
        boolean result;
        object.setId(this.generateId());
        String sql = "INSERT INTO " + nameTable + " (id, name, type_id, calories) VALUES (?, ?, ?, ?)";
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, object.getId());
            statement.setString(2, object.getName());
            statement.setInt(3, object.getType().getId());
            statement.setDouble(4, object.getCalories());
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
    public boolean delete(Workout object) {
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
    public Workout findById(int id) {
        Workout result = null;
        String sql = "SELECT * FROM " + nameTable + " w JOIN " + joinNameTable + " t ON w.type_id = t.id WHERE w.id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TypeWorkout typeWorkout = new TypeWorkout(resultSet.getInt("t.id"), resultSet.getString("t.name"));
                result = new Workout(resultSet.getInt("w.id"), resultSet.getString("w.name"),typeWorkout,resultSet.getInt("w.calories"));
            }
        } catch(SQLException e) {
            result = null;
        }
        return result;
    }

    @Override
    public List<Workout> findAll() {
        List<Workout> list = new ArrayList<>();
        String sql = "SELECT * FROM " + nameTable + " w JOIN " + joinNameTable + " t ON w.type_id = t.id";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TypeWorkout typeWorkout = new TypeWorkout(resultSet.getInt("t.id"), resultSet.getString("t.name"));
                Workout workout = new Workout(resultSet.getInt("w.id"), resultSet.getString("w.name"),typeWorkout,resultSet.getInt("w.calories"));
                list.add(workout);
            }
        } catch (SQLException e) {
            list = new ArrayList<>();
        }
        return list;
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
    public void update(Workout object) {
        String sql = "UPDATE " + nameTable + " SET name = ?, type_id = ?, calories = ? WHERE id = ?";
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, object.getName());
            statement.setInt(2, object.getType().getId());
            statement.setDouble(3, object.getCalories());
            statement.setInt(4, object.getId());
            int count = statement.executeUpdate();
            if (count == 1) {
                connection.commit();
            }
        } catch(SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {

            }
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
