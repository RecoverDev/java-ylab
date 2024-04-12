package ru.list.recover.out;

import static java.lang.System.out;

/**
 * UserView - класс, выводящий сообщения необходимые для работы со списком пользователей
 */

public class UserView {

    /**
     * Выводит меню для авторизации пользователя
     * 1 - вход зарегистрированного пользователя
     * 2 - создание нового пользователя
     */
    public static void autorizationMenu(){
        out.println("1. Вход");
        out.println("2. Регистрация");
        out.println();
    }

    /**
     * Выводит на экран соощения
     * @param message - текст сообщения
     */
    public static void sayMessage(String message){
        out.println(message);
    }

}
