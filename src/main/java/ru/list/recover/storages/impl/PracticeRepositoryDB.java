package ru.list.recover.storages.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import ru.list.recover.models.Practice;
import ru.list.recover.models.TypeWorkout;
import ru.list.recover.models.User;
import ru.list.recover.models.Workout;
import ru.list.recover.storages.PracticeRepository;

public class PracticeRepositoryDB implements PracticeRepository{

    private Connection connection;
    private String nameTable = "practices";

    public PracticeRepositoryDB(Connection connection, String nameSchema) {
        this.connection = connection;
        if (nameSchema.length() > 0) {
            nameTable = nameSchema + "." + nameTable;
        }
    }
    

    @Override
    public boolean insert(Practice object) {
        boolean result;
        object.setId(this.generateId());
        String sql = "INSERT INTO " + nameTable + " (id, date, user_id, workout_id, count_exercise, amount, calories, desc) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, object.getId());
            statement.setString(2, object.getDate().toString());
            statement.setInt(3, object.getUser().getId());
            statement.setInt(4, object.getWorkout().getId());
            statement.setInt(5, object.getCountExercise());
            statement.setInt(6, object.getAmount());
            statement.setDouble(7, object.getCalories());
            statement.setString(8, object.getDescription());
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
    public boolean delete(Practice object) {
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
    public void update(Practice object) {
        String sql = "UPDATE " + nameTable + " SET date = ?, count_exercise = ?, amount = ? calories = ? desc = ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, object.getDate().toString());
            statement.setInt(2, object.getCountExercise());
            statement.setInt(3, object.getAmount());
            statement.setDouble(4, object.getCalories());
            statement.setString(5, object.getDescription());
            statement.executeUpdate();
        } catch(SQLException e) {

        }
    }

    @Override
    public Practice findById(int id) {
        Practice result = null;
        String sql = "SELECT * FROM " + nameTable + " WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = new Practice(resultSet.getInt("id"),
                                LocalDateTime.parse(resultSet.getString("date")),
                                new User(1,"","","",0),
                                new Workout(1,"",new TypeWorkout(1, ""),1500),
                                resultSet.getInt("count_exercise"),
                                resultSet.getInt("amount"),
                                resultSet.getDouble("calories"),
                                resultSet.getString("desc"));
            }
        } catch(SQLException e) {
            result = null;
        }

        return result;
    }

    @Override
    public List<Practice> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
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
