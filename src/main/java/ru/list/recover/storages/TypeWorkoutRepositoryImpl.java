package ru.list.recover.storages;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ru.list.recover.models.TypeWorkout;

public class TypeWorkoutRepositoryImpl implements TypeWorkoutRepository{

    private List<TypeWorkout> typeWorkouts = new ArrayList<>();

    
    /** 
     * @param object
     * @return boolean
     */
    @Override
    public boolean insert(TypeWorkout object) {
        object.setId(Math.max(object.getId(), 1));
        if(typeWorkouts.stream().filter(u -> u.getId() == object.getId()).count() > 0){
            int id = typeWorkouts.stream().map(u -> u.getId()).max((x,y) -> x.compareTo(y)).get() + 1;
            object.setId(id);
        }
        return typeWorkouts.add(object);
    }

    @Override
    public boolean delete(TypeWorkout object) {
        return typeWorkouts.remove(object);    }

    @Override
    public void update(TypeWorkout object) {
        TypeWorkout typeWorkout = this.findById(object.getId());
        if(typeWorkout == null){
            this.insert(object);
        }else{
            int index = typeWorkouts.indexOf(typeWorkout);
            typeWorkouts.set(index,object);
        }
    }

    @Override
    public TypeWorkout findById(int id) {
        Optional<TypeWorkout> result = typeWorkouts.stream().filter(u -> u.getId() == id).findFirst();
        return result.isPresent() ? result.get() : null;
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
