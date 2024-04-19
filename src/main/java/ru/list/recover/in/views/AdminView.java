package ru.list.recover.in.views;

import static java.lang.System.out;

import java.util.List;

/**
 * класс содержит методы выводящие сообщения при работе с сервисом AdminService
 */
public class AdminView {

    /**
     * выводит меню работы администратора
     */
    public static void showMenu() {
        out.println("1. Просмотр списка пользователей");
        out.println("2. Просмотр списка вариантов тренировок");
        out.println("3. Просмотр посещений пользователей");
        out.println("4. Просмотр журнала");
        out.println("5. Завершение работы");
    }

    /**
     * выводит список строк на экран
     * 
     * @param list - список строк
     */
    public static void showList(List<String> list) {
        for (String string : list) {
            out.println(string);
        }
    }

}
