package ru.list.recover.storages.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ru.list.recover.models.Workout;
import ru.list.recover.storages.WorkoutRepository;

public class WorkoutRepositoryImpl implements WorkoutRepository {

    private final List<Workout> workouts = new ArrayList<>();

    /**
     * @param object
     * @return boolean
     */
    @Override
    public boolean insert(Workout object) {
        object.setId(Math.max(object.getId(), 1));
        while (this.findById(object.getId()) != null) {
            object.setId(object.getId() + 1);
        }

        return workouts.add(object);

    }

    @Override
    public boolean delete(Workout object) {
        return workouts.remove(object);
    }

    @Override
    public Workout findById(int id) {
        List<Workout> list = workouts.stream().filter(u -> u.getId() == id).collect(Collectors.toList());
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Workout> findAll() {
        return workouts;
    }

    @Override
    public int getCount() {
        return workouts.size();
    }

    @Override
    public void update(Workout object) {
        Workout workout = this.findById(object.getId());
        if (workout == null) {
            this.insert(object);
        } else {
            int index = workouts.indexOf(workout);
            workouts.set(index, object);
        }

    }

}
