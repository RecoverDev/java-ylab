package ru.list.recover.services;

import ru.list.recover.storages.PracticeRepository;
import ru.list.recover.storages.WorkoutRepository;

public interface FitnessService extends Service{

    void setRepository(PracticeRepository repository);
    PracticeRepository getRepository();
    WorkoutRepository getWorkoutRepository();
    void setWorkoutRepository(WorkoutRepository workoutRepository);
    void showMenu();
    
}
