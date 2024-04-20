package ru.list.recover;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import liquibase.exception.LiquibaseException;
import ru.list.recover.db.DBConnection;
import ru.list.recover.db.FitnessProperties;
import ru.list.recover.db.Migration;
import ru.list.recover.logger.Logger;
import ru.list.recover.models.TypeWorkout;
import ru.list.recover.models.User;
import ru.list.recover.storages.TypeWorkoutRepository;
import ru.list.recover.storages.UserRepository;
import ru.list.recover.storages.WorkoutRepository;
import ru.list.recover.storages.impl.TypeWorkoutRepositoryDB;
import ru.list.recover.storages.impl.UserRepositoryDB;

public class Main {

    /**
     * @param args
     * @throws LiquibaseException 
     * @throws SQLException 
     */
    public static void main(String[] args) throws LiquibaseException {

        Logger logger = new Logger();
        FitnessProperties properties = new FitnessProperties("src/main/resources/fitness.properties", logger);
        properties.load();

        DBConnection con = new DBConnection(properties, logger);
        con.connect();
        Migration migration = new Migration(con.getConnection(), logger,"fitness");
        migration.migrate();

        con.connect();
        UserRepository repository = new UserRepositoryDB(con.getConnection(), "fitness");

        boolean result = repository.insert(new User(2, "Test User", "testUser", "555", 0));
        List<User> users = repository.findAll();

        TypeWorkoutRepository typeWorkoutRepository = new TypeWorkoutRepositoryDB(con.getConnection(), "fitness");
        List<TypeWorkout> typeWorkouts = typeWorkoutRepository.findAll();

        WorkoutRepository workoutRepository =  WorkoutRepositoryDB(con.getConnection(),"fitness");
        //List<Workout> workouts = workoutRepository.findAll();

        users.forEach(u -> System.out.println(u));

        typeWorkouts.forEach(t -> System.out.println(t));

        //workouts.forEach(w -> System.out.println(w));

        logger.getLog().forEach(l -> System.out.println(l));

        // Container container = new Container();
        // container.run();
    }

}