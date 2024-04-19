package ru.list.recover.storages;

import java.util.List;

import ru.list.recover.models.Workout;

public interface WorkoutRepository extends Repository<Workout> {

    /**
     * @param object
     * @return boolean
     */
    boolean insert(Workout object);

    boolean delete(Workout object);

    Workout findById(int id);

    List<Workout> findAll();

    int getCount();

    void update(Workout object);

}