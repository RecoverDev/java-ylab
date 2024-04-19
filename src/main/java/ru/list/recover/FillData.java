package ru.list.recover;
import java.time.LocalDateTime;
import java.time.Month;

import ru.list.recover.models.Practice;
import ru.list.recover.models.TypeWorkout;
import ru.list.recover.models.User;
import ru.list.recover.models.Workout;
import ru.list.recover.storages.PracticeRepository;
import ru.list.recover.storages.TypeWorkoutRepository;
import ru.list.recover.storages.UserRepository;
import ru.list.recover.storages.WorkoutRepository;

/**
 * класс предназначен для начального заполнения репозиториев информацией
 * для проведения тестирования
 */
public class FillData {
    private UserRepository userRepository;
    private WorkoutRepository workoutRepository;
    private TypeWorkoutRepository typeWorkoutRepository;
    private PracticeRepository practiceRepository;


    public FillData(UserRepository userRepository, WorkoutRepository workoutRepository,
            TypeWorkoutRepository typeWorkoutRepository, PracticeRepository practiceRepository) {
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
        this.typeWorkoutRepository = typeWorkoutRepository;
        this.practiceRepository = practiceRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public WorkoutRepository getWorkoutRepository() {
        return workoutRepository;
    }

    public TypeWorkoutRepository getTypeWorkoutRepository() {
        return typeWorkoutRepository;
    }

    public PracticeRepository getPracticeRepository() {
        return practiceRepository;
    }

    public void fillTestData() {

        userRepository.insert(new User(1, "Вася Первый", "user1", "111", 0));
        userRepository.insert(new User(2, "Иван Второй", "user2", "222", 0));
        userRepository.insert(new User(3, "Админ Душнилкин", "admin", "1234", 1));

        typeWorkoutRepository.insert(new TypeWorkout(1, "Кардио"));
        typeWorkoutRepository.insert(new TypeWorkout(2, "Силовая"));
        typeWorkoutRepository.insert(new TypeWorkout(3, "Аэробная"));
        typeWorkoutRepository.insert(new TypeWorkout(4, "Растяжка"));

        workoutRepository.insert(new Workout(1, "Сайкл", typeWorkoutRepository.findById(1), 1500));
        workoutRepository.insert(new Workout(1, "Кроссфит", typeWorkoutRepository.findById(2), 2500));
        workoutRepository.insert(new Workout(1, "Табата", typeWorkoutRepository.findById(2), 1500));
        workoutRepository.insert(new Workout(1, "Йога", typeWorkoutRepository.findById(4), 250));

        practiceRepository.insert(new Practice(1, LocalDateTime.of(2024, Month.APRIL, 10, 10, 0),
                userRepository.findById(1), workoutRepository.findById(1), 12, 60, 1500, ""));
        practiceRepository.insert(new Practice(2, LocalDateTime.of(2024, Month.APRIL, 9, 10, 0),
                userRepository.findById(1), workoutRepository.findById(2), 10, 45, 2500, ""));
        practiceRepository.insert(new Practice(3, LocalDateTime.of(2024, Month.APRIL, 10, 12, 0),
                userRepository.findById(1), workoutRepository.findById(3), 12, 50, 1500, ""));
        practiceRepository.insert(new Practice(4, LocalDateTime.of(2024, Month.APRIL, 8, 10, 0),
                userRepository.findById(2), workoutRepository.findById(1), 12, 60, 1500, ""));
        practiceRepository.insert(new Practice(5, LocalDateTime.of(2024, Month.APRIL, 9, 10, 0),
                userRepository.findById(2), workoutRepository.findById(2), 8, 45, 1500, ""));
        practiceRepository.insert(new Practice(6, LocalDateTime.of(2024, Month.APRIL, 10, 10, 0),
                userRepository.findById(2), workoutRepository.findById(3), 16, 50, 2500, ""));

    }
        
}


