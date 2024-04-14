package ru.list.recover.storages;

import java.util.List;

import ru.list.recover.models.User;

public interface UserRepository extends Repository<User>{

    /** 
     * @param object
     * @return boolean
     */
    boolean insert(User object);

    boolean delete(User object);

    User findById(int id);

    List<User> findAll();

    int getCount();

    User findByLogin(String login);

    User findByName(String login);

    void update(User object);

}