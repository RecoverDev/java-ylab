package ru.list.recover.services;

import java.util.List;

/**
 * интерфейс описывающий работу с AdminService (работа администратора)
 */
public interface AdminService extends Service {
    /**
     * показывает главное меню работы администратора
     */
    void showMenu();


    /**
     * формирует список пользователей программы
     */
    List<String> getListUsers();

    /**
     * формирует список доступных вариантов тренировок
     */
    List<String> getListWorkouts();

    /**
     * формирует список проведенных тренировок
     */
    List<String> getListPractices();

    /**
     * формирует список записей содержимого журнала
     */
    List<String> getListLog();

}
