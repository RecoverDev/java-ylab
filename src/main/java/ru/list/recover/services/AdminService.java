package ru.list.recover.services;

import ru.list.recover.storages.PracticeRepository;
import ru.list.recover.storages.UserRepository;
import ru.list.recover.storages.WorkoutRepository;

/**
 * интерфейс описывающий работу с AdminService (работа администратора)
 */
public interface AdminService extends Service {
    /**
     * показывает главное меню работы администратора
     */
    void showMenu();

    /**
     * возвращает список посещений пользователей
     * @return объект класса PracticeRepository
     */
   PracticeRepository getPracticeRepository();

    /**
     * определяет в классе список посещений пользователей
     * @param practiceRepository объект класса PracticeRepository
     */
    void setPracticeRepository(PracticeRepository practiceRepository);


    /**
     * возвращает хранилище списка возможных вариантов тренировок
     * @return объект класса WorkoutRepository
     */
    WorkoutRepository getWorkoutRepository();

    /**
     * определяет в классе список возможных вариантов тренировок
     * @param workoutRepository объект класса WorkoutRepository
     */
    void setWorkoutRepository(WorkoutRepository workoutRepository);

    /**
     * возвращает хранилище списка пользователей программы
     * @return хранилище списка пользователей
     */
    UserRepository getUserRepository();

    /**
     * определяет в классе хранилище списка пользователей
     * @param userRepository - Repository пользователей (класс User)
     */
    void setUserRepository(UserRepository userRepository);

    /**
     * выводит список пользователей программы
     */
    void showUsers();

    /**
     * выводит список доступных вариантов тренировок
     */
    void showWorkouts();

/**
     * выводит список проведенных тренировок
     */
    void showPractices();

    /**
     * выводит содержимое журнала
     */
    void showLog();


}
