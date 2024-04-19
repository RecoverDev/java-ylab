package ru.list.recover.storages;

import java.util.List;

import ru.list.recover.models.User;

/**
 * интерфейс описывает хранилище пользователей
 */
public interface UserRepository extends Repository<User> {

    /**
     * добавляет нового пользователя в хранилище
     * 
     * @param object объект класса User
     * @return boolean true - добавление прошло успешно
     */
    boolean insert(User object);

    /**
     * удаляет пользователя из хранилища
     * 
     * @return true - удаление прошло успешно
     */
    boolean delete(User object);

    /**
     * возвращает объект пользователя по ID
     */
    User findById(int id);
    
    /**
     * возвращает список всех пользователей из хранилища
     */
    List<User> findAll();

    /**
     * возвращает количество записей в хранилище
     */
    int getCount();

    /**
     * возвращает объект пользователя по логину
     * 
     * @param login -логин пользователя
     * @return - объект класса User
     */
    User findByLogin(String login);

    /**
     * обновляет запись пользователя
     */
    void update(User object);

}