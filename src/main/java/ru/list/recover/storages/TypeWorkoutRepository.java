package ru.list.recover.storages;

import java.util.List;

import ru.list.recover.models.TypeWorkout;

/**
 * интерфнйс описывающий хранилище типов тренировок 
 */
public interface TypeWorkoutRepository extends Repository<TypeWorkout> {

    /**
     * добавление нового типа тренировки
     * 
     * @param object - объект класса TypeWorkout
     * @return boolean - true - добавление прошло успешно
     */
    boolean insert(TypeWorkout object);
    
    /**
     * удаление типа тренировки из списка
     * 
     * @return true - удаление прошло успешно
     */
    boolean delete(TypeWorkout object);

    /**
     * обновление типа тренировки в списке
     */
    void update(TypeWorkout object);

    /**
     * возвращает запись по ID
     */
    TypeWorkout findById(int id);

    
    /**
     * возвращает список всех записей из хранилища
     */
    List<TypeWorkout> findAll();

    /**
     * возвращает количество записей в хранилище
     */
    int getCount();

}