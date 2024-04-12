package ru.list.recover.out;

import static java.lang.System.out;

/**
 * класс содержит методы, выводящие сообщения при работе с сервисом MainService
 */
public class MainView {

    public static void showTitle(){
        out.print("\033[H\033[J");
        out.println("Программа \"МОЙ ФИТНЕС\"");
        out.println("для выхода нажмите \"0\"");
        out.println();
    }

    public static void showFinal(){
        out.println("Работа программы завершена");
    }

}
