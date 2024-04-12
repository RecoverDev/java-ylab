package ru.list.recover.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TypeWorkout - класс,  описывающий типы тренировок
 * id - идентификатор
 * name - ниаменование типа
 */
@Data
@AllArgsConstructor
public class TypeWorkout {
    private int id;
    private String name;

    @Override
    public String toString(){
        return String.format("%d %s", this.getId(), this.getName());
    }
}
