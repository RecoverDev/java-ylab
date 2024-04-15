package ru.list.recover.storages;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ru.list.recover.models.User;

public class UserRepositoryImpl implements UserRepository {

    private List<User> users = new ArrayList<>();

    /**
     * @param object
     * @return boolean
     */
    @Override
    public boolean insert(User object) {
        object.setId(Math.max(object.getId(), 1));
        if (users.stream().filter(u -> u.getId() == object.getId()).count() > 0) {
            int id = users.stream().map(u -> u.getId()).max((x, y) -> x.compareTo(y)).get() + 1;
            object.setId(id);
        }
        if (users.stream().filter(u -> u.getName().equals(object.getName())).count() > 0) {
            return false;
        }
        if (users.stream().filter(u -> u.getLogin().equals(object.getLogin())).count() > 0) {
            return false;
        }
        return users.add(object);
    }

    @Override
    public boolean delete(User object) {
        return users.remove(object);
    }

    @Override
    public User findById(int id) {
        Optional<User> result = users.stream().filter(u -> u.getId() == id).findFirst();
        return result.isPresent() ? result.get() : null;
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User findByLogin(String login) {
        Optional<User> user = users.stream().filter(u -> u.getLogin().equals(login)).findFirst();
        return user.isPresent() ? user.get() : null;
    }

    @Override
    public User findByName(String login) {
        Optional<User> user = users.stream().filter(u -> u.getName().equals(login)).findFirst();
        return user.isPresent() ? user.get() : null;
    }

    @Override
    public void update(User object) {
        User user = this.findById(object.getId());
        if (user == null) {
            this.insert(object);
        } else {
            int index = users.indexOf(user);
            users.set(index, object);
        }

    }

}
