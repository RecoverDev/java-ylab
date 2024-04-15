package ru.list.recover.storages;

import java.util.List;

import ru.list.recover.models.TypeWorkout;

public interface TypeWorkoutRepository extends Repository<TypeWorkout> {

    /**
     * @param object
     * @return boolean
     */
    boolean insert(TypeWorkout object);

    boolean delete(TypeWorkout object);

    void update(TypeWorkout object);

    TypeWorkout findById(int id);

    List<TypeWorkout> findAll();

    int getCount();

}