package ru.list.recover.storages;

import java.util.List;

import ru.list.recover.models.Practice;

public interface PracticeRepository extends Repository<Practice> {

    /**
     * @param object
     * @return boolean
     */
    boolean insert(Practice object);

    boolean delete(Practice object);

    void update(Practice object);

    Practice findById(int id);

    List<Practice> findAll();

    int getCount();

}