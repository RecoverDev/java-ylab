package ru.list.recover.storages.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ru.list.recover.models.Workout;
import ru.list.recover.storages.WorkoutRepository;

public class WorkoutRepositoryImpl implements WorkoutRepository {

    private List<Workout> workouts = new ArrayList<>();

    /**
     * @param object
     * @return boolean
     */
    @Override
    public boolean insert(Workout object) {
        object.setId(Math.max(object.getId(), 1));
        if (workouts.stream().filter(u -> u.getId() == object.getId()).count() > 0) {
            int id = workouts.stream().map(u -> u.getId()).max((x, y) -> x.compareTo(y)).get() + 1;
            object.setId(id);
        }

        return workouts.add(object);

    }

    @Override
    public boolean delete(Workout object) {
        return workouts.remove(object);
    }

    @Override
    public Workout findById(int id) {
        Optional<Workout> result = workouts.stream().filter(u -> u.getId() == id).findFirst();
        return result.isPresent() ? result.get() : null;
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
