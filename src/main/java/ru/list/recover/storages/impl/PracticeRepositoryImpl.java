package ru.list.recover.storages.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ru.list.recover.models.Practice;
import ru.list.recover.storages.PracticeRepository;

public class PracticeRepositoryImpl implements PracticeRepository {

    private final List<Practice> practices = new ArrayList<>();

    /**
     * @param object
     * @return boolean
     */
    @Override
    public boolean insert(Practice object) {
        object.setId(Math.max(object.getId(), 1));
        while (this.findById(object.getId()) != null) {
            object.setId(object.getId() + 1);
        }

        return practices.add(object);
    }

    @Override
    public boolean delete(Practice object) {
        return practices.remove(object);
    }

    @Override
    public void update(Practice object) {
        Practice practice = this.findById(object.getId());
        if (practice == null) {
            this.insert(object);
        } else {
            int index = practices.indexOf(practice);
            practices.set(index, object);
        }
    }

    @Override
    public Practice findById(int id) {
        List<Practice> list = practices.stream().filter(u -> u.getId() == id).collect(Collectors.toList());
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Practice> findAll() {
        return practices;
    }

    @Override
    public int getCount() {
        return practices.size();
    }

}
