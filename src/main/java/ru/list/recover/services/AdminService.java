package ru.list.recover.services;

/**
 * интерфейс описывающий работу с AdminService (работа администратора)
 */
public interface AdminService extends Service {
    /**
     * показывает главное меню работы администратора
     */
    void showMenu();


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
