package ru.list.recover.services;

import ru.list.recover.storages.PracticeRepository;
import ru.list.recover.storages.PracticeRepositoryImpl;
import ru.list.recover.storages.WorkoutRepository;

/**
 * интерфейс, описывающий работу сервиса спортсмена
 */
public interface FitnessService extends Service{

    /**
     * определяет хранище с проведенными тренировками
     * @param repository - хранилище с тренировками объект типа PracticeRepository
     */
    void setRepository(PracticeRepository repository);
    /**
     * возвращает хранилище проведенных тренировок, объект типа PracticeRepository
     * @return объект типа PracticeRepository
     */
    PracticeRepository getRepository();
    /**
     * возвращает хранилище списка возможных тренировок
     * @return объект типа WorkoutRepository
     */
    WorkoutRepository getWorkoutRepository();
    /**
     * определяет хранилище списка типов возможных тренировок, объект типа WorkoutRepository
     * @param workoutRepository объект типа WorkoutRepository
     */
    void setWorkoutRepository(WorkoutRepository workoutRepository);
    /**
     * выводит наэкран меню выьора возможных действий
     */
    void showMenu();
    /**
     * добавляет новую проведенную тренировку спортсмена
     * @return результат добавления (true - успешно)
     */
    boolean addPractice();
    /**
     * удаляет тренировку из списка проведенных тренировок
     * @return результат удаления (true - успешно)
     */
    boolean deletePractice();
    boolean editPractice();
    void showPractices();
    void statistic();
    void timeStatistic();
    void caloriesStatistic();
}
