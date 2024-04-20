package ru.list.recover.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Workout - класс, описывающий тренировку
 * id - идентификатор тренировки
 * name - наименование тренировки
 * type - тип тренировки (TypeWorkout)
 * calories - сжигаемые калории
 */

@Data
@AllArgsConstructor
public class Workout {
    /**
     * уникальный идентификатор трениковки
     */
    private int id;
    /**
     * наименование тренировки
     */
    private String name;

    /**
     * тип тренировки
     */
    private TypeWorkout type;
    /**
     * количество калорий, потраченных на тренировке
     */
    private double calories;

    /**
     * переопределен метод toString
     * 
     * @return String - строковое представление объекта
     */
    @Override
    public String toString() {
        return String.format("%d %s (%s)", this.getId(), this.getName(), this.getType().getName());
    }

}
