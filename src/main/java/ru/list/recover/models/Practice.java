package ru.list.recover.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Practice - класс описывающий запись в журнале тренировок
 * id - идентификатор записи
 * user - спортсмен, сделавший запись
 * workout - тренировка (Workout)
 * countExercese - количество повторений
 * amount - продолжительность тренировки в минутах
 * calories - количество соженных калорий
 * description - дополнительные параметры или описание
 */
@Data
@AllArgsConstructor
public class Practice {

    private int id;
    private LocalDateTime date;
    private User user;
    private Workout workout;
    private int countExercise;
    private int amount;
    private double calories;
    private String description;

    
    /** 
     * переопределен метод toString
     * @return String - строковое представление объекта
     */
    @Override
    public String toString(){
        return String.format("%d %s %s %s минут", this.getId(), this.getDate().toLocalDate().toString(), this.getWorkout().getName(), this.getAmount());
    }

}
