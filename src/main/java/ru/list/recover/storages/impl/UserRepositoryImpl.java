package ru.list.recover.storages.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ru.list.recover.models.User;
import ru.list.recover.storages.UserRepository;

public class UserRepositoryImpl implements UserRepository {

    private final Set<User> users = new HashSet<>();

    /**
     * @param object
     * @return boolean
     */
    @Override
    public boolean insert(User object) {
        object.setId(Math.max(object.getId(), 1));
        while (this.findById(object.getId()) != null) {
            object.setId(object.getId() + 1);
        }
        return users.add(object);
    }

    @Override
    public boolean delete(User object) {
        return users.remove(object);
    }

    @Override
    public User findById(int id) {
        List<User> list = users.stream().filter(u -> u.getId() == id).collect(Collectors.toList());
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return users.stream().collect(Collectors.toList());
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User findByLogin(String login) {
        return users.stream().filter(u -> u.getLogin().equals(login)).findFirst().get();
    }

    @Override
    public void update(User object) {
        User user = this.findById(object.getId());
        if (user != null) {
            this.delete(object);
        } 
        this.insert(object);
    }

}
