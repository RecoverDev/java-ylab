package ru.list.recover.services;

/**
 * интерфейс, описывающий работу сервиса спортсмена
 */
public interface FitnessService extends Service {

    /**
     * выводит наэкран меню выьора возможных действий
     */
    void showMenu();

    /**
     * добавляет новую проведенную тренировку спортсмена
     * 
     * @return результат добавления (true - успешно)
     */
    boolean addPractice();

    /**
     * удаляет тренировку из списка проведенных тренировок
     * 
     * @return результат удаления (true - успешно)
     */
    boolean deletePractice();

    boolean editPractice();

    void showPractices();

    void statistic();

    void timeStatistic();

    void caloriesStatistic();
}
