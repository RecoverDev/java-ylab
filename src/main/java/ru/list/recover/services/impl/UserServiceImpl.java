package ru.list.recover.services.impl;

import java.util.ArrayList;
import java.util.List;

import ru.list.recover.in.Response;
import ru.list.recover.models.User;
import ru.list.recover.out.UserView;
import ru.list.recover.services.IObserve;
import ru.list.recover.services.UserService;
import ru.list.recover.storages.UserRepository;

public class UserServiceImpl implements UserService {
    private UserRepository repository;
    private User user = null;
    private List<IObserve> listeners = new ArrayList<>();

    /**
     * @return UserRepository
     */
    public UserRepository getRepository() {
        return repository;
    }

    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addListener(IObserve observe) {
        this.listeners.add(observe);
    }

    @Override
    public void removeListener(IObserve observe) {
        this.listeners.remove(observe);
    }

    @Override
    public User autorization() {
        User result = null;
        int answer = 0;

        UserView.autorizationMenu();
        answer = Response.getInt("Введите число (1 или 2): ");

        switch (answer) {
            case 1:
                result = this.login();
                break;
            case 2:
                User user = this.registration();
                if (user != null) {
                    repository.insert(user);
                }
            default:
                break;
        }
        this.Observe(answer);
        return result;
    }

    @Override
    public User login() {
        User result = null;

        String name = Response.getSrting("Введите логин: ");
        String pass = Response.getSrting("Введите пароль: ");

        User searchUser = repository.findByLogin(name);
        if (searchUser != null) {
            if (searchUser.getPassword().equals(pass)) {
                result = searchUser;
            }
        }

        if (result == null) {
            UserView.sayMessage("Неверный логин или пароль");
        }
        return result;
    }

    @Override
    public User registration() {
        User result = new User(1, "", "", "", 0);
        String value = "";

        value = Response.getSrting("Введите имя: ");
        if (repository.findByName(value) == null) {
            result.setName(value);
        } else {
            UserView.sayMessage("Пользователь с таким именем существует");
            return null;
        }
        value = Response.getSrting("Введите логин: ");
        if (repository.findByLogin(value) == null) {
            result.setLogin(value);
        } else {
            UserView.sayMessage("Пользователь с таким логином существует");
            return null;
        }
        value = Response.getSrting("Введите пароль: ");
        result.setPassword(value);

        if (user != null) {
            if (user.getRole() == 1) {
                int role = Response.getInt("Введите роль пользователя (0 - спортсмен, 1 - администратор)");
                if (role >= 0) {
                    result.setRole(role);
                }
            }
        }

        return result;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void Observe(Object o) {
        for (IObserve obleve : listeners) {
            obleve.Observe(o);
        }
    }

}
