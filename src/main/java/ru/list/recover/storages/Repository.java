package ru.list.recover.storages;

import java.util.List;

/**
 * 
 */
public interface Repository<T> {

    boolean insert(T object);
    boolean delete(T object);
    void update(T object);
    T findById(int id);
    List<T> findAll();
    int getCount();

}
