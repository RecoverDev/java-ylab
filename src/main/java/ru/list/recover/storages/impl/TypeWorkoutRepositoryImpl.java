package ru.list.recover.storages.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ru.list.recover.models.TypeWorkout;
import ru.list.recover.storages.TypeWorkoutRepository;

public class TypeWorkoutRepositoryImpl implements TypeWorkoutRepository {

    private final List<TypeWorkout> typeWorkouts = new ArrayList<>();

    /**
     * @param object
     * @return boolean
     */
    @Override
    public boolean insert(TypeWorkout object) {
        object.setId(Math.max(object.getId(), 1));
        while (this.findById(object.getId()) != null) {
            object.setId(object.getId() + 1);
        }

        return typeWorkouts.add(object);
    }

    @Override
    public boolean delete(TypeWorkout object) {
        return typeWorkouts.remove(object);
    }

    @Override
    public void update(TypeWorkout object) {
        TypeWorkout typeWorkout = this.findById(object.getId());
        if (typeWorkout == null) {
            this.insert(object);
        } else {
            int index = typeWorkouts.indexOf(typeWorkout);
            typeWorkouts.set(index, object);
        }
    }

    @Override
    public TypeWorkout findById(int id) {
        List<TypeWorkout> list = typeWorkouts.stream().filter(u -> u.getId() == id).collect(Collectors.toList());
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<TypeWorkout> findAll() {
        return typeWorkouts;
    }

    @Override
    public int getCount() {
        return typeWorkouts.size();
    }

}
