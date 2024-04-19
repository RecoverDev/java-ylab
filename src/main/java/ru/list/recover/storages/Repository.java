package ru.list.recover.storages;

import java.util.List;

/**
 * интерфейс описывает работу с хранилищем
 */
public interface Repository<T> {

    /**
     * добавление записи в хранилище
     * 
     * @param object - объект класса T для добавления в хранилище 
     * @return true - добавление прошло успешно
     */
    boolean insert(T object);

    /**
     * удаляет запись из хранилища
     * 
     * @param object - объект для удалания
     * @return true - удаление прошло успешно
     */
    boolean delete(T object);

    /**
     * обновляет запись в хранилище
     * 
     * @param object - объект для обновления
     */
    void update(T object);

    /**
     * возвращеет запись по ID
     * 
     * @param id - идентификатор записи/объекта
     * @return - объект класса T
     */
    T findById(int id);

    /**
     * возвращает полный список записей из хранилища
     * 
     * @return список объектов
     */
    List<T> findAll();

    /**
     * возвращает количество записей в хранилище
     * 
     * @return - количество записей
     */
    int getCount();

}
