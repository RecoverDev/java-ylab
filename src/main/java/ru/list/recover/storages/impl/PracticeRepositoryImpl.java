package ru.list.recover.storages.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ru.list.recover.models.Practice;
import ru.list.recover.storages.PracticeRepository;

public class PracticeRepositoryImpl implements PracticeRepository {

    private List<Practice> practices = new ArrayList<>();

    /**
     * @param object
     * @return boolean
     */
    @Override
    public boolean insert(Practice object) {
        object.setId(Math.max(object.getId(), 1));
        if (practices.stream().filter(u -> u.getId() == object.getId()).count() > 0) {
            int id = practices.stream().map(u -> u.getId()).max((x, y) -> x.compareTo(y)).get() + 1;
            object.setId(id);
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
        Optional<Practice> result = practices.stream().filter(u -> u.getId() == id).findFirst();
        return result.isPresent() ? result.get() : null;
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
