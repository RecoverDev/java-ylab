package ru.list.recover.out;

import static java.lang.System.out;

import java.util.List;

/**
 * класс содержит методы выводящие сообщения при работе с сервисом FitnessService
 */
public class FitnessView {

    /**
     * выводит меню работы пользователя
     */
    public static void showMenu(){
        out.println("1. Добавить тренировку");
        out.println("2. Удалить тренировку");
        out.println("3. Изменить тренировку");
        out.println("4. Посмотреть свои тренировки");
        out.println("5. Статистика");
        out.println("6. Завершить сеанс работы");
    }

    /**
     * выводит произвольное сообщение
     * @param message - текст сообщения
     */
    public static void showMessage(String message){
        out.println(message);
    }

    /**
     * выводит список доступных видов тренировок
     * @param list - список видов тренировок
     */
    public static void showWorkouts(List<String> list){
        for (String string : list) {
            out.println("\t" + string);
        }
    }

    /**
     * выводит список проведенных тренировок пользователя
     * @param list - список проведенных тренировок
     */
    public static void showPractices(List<String> list){
        for (String string : list) {
            out.println("\t" + string);
        }
    }

    /**
     * выводит меню выбора вида статистики
     */
    public static void showMenuStatistic(){
        out.println("Выберите вид статистики:");
        out.println("1. статистика по общему времени тренировок в минутах за выбранный период");
        out.println("2. статистика по затраченным калориям за выбранный период");
    }

}
