package ru.list.recover.in;

import java.util.Scanner;
import static java.lang.System.out;

import java.time.LocalDate;

/**
 * класс содержит медоты получающие информацию от пользователя
 */
public class Response {
    private static Scanner sc = new Scanner(System.in);

    /**
     * получает от пользователя строку
     * @param message - поясняющее сообщение, выводимое пользователю
     * @return строку введенную пользователем
     */
    public static String getSrting(String message) {
        String result = "";
        out.print(message);
        if (sc.hasNextLine()) {
            result = sc.nextLine();
        }
        return result;
    }

    /**
     * получает от пользователя число
     * @param message - поясняющее сообщение, выводимое пользователю
     * @return число введенное пользователем
     */
    public static int getInt(String message) {
        int result = 0;
        out.print(message);
        if (sc.hasNextInt()) {
            result = sc.nextInt();
        }
        //добавлено дополнительное считывание строки
        //из-за особенностей работы nextInt (функция оставляет после себя 0Dh 0Ah)
        sc.nextLine();
        return result;
    }

    /**
     * получает от пользователя дату
     * @param message - поясняющее сообщение
     * @return - дата, введенная пользователем
     */
    public static LocalDate getDate(String message) {
        LocalDate result = LocalDate.of(2000, 1, 1);
        out.print(message);
        if (sc.hasNextLine()) {
            result = LocalDate.parse(sc.nextLine()) ;
        }
        return result;
    }

}
