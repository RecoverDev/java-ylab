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
    private int id;
    private String name;
    private TypeWorkout type;
    private double calories;

    @Override
    public String toString(){
        return String.format("%d %s (%s)", this.getId(), this.getName(), this.getType().getName());
    }

}
