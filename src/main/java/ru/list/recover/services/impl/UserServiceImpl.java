package ru.list.recover.services.impl;

import java.util.ArrayList;
import java.util.List;

import ru.list.recover.in.Response;
import ru.list.recover.logger.Logger;
import ru.list.recover.models.User;
import ru.list.recover.out.UserView;
import ru.list.recover.services.IObserve;
import ru.list.recover.services.UserService;
import ru.list.recover.storages.UserRepository;

public class UserServiceImpl implements UserService {
    private UserRepository repository;
    private User user = null;
    private Logger logger;
    private List<IObserve> listeners = new ArrayList<>();
    private Response response;

    public UserServiceImpl(UserRepository userRepository, Response response, Logger logger) {
        this.repository = userRepository;
        this.response = response;
        this.logger = logger;
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
        answer = response.getInt("Введите число (1 или 2): ");

        switch (answer) {
            case 1:
                result = this.login();
                logger.addRecord("авторизация пользователя", result != null);
                break;
            case 2:
                User user = this.registration();
                boolean addUser = false;
                if (user != null) {
                    addUser = repository.insert(user);
                }
                logger.addRecord("Регистрация пользователя", addUser);
            default:
                break;
        }
        this.Observe(answer);
        return result;
    }

    @Override
    public User login() {
        User result = null;

        String name = response.getSrting("Введите логин: ");
        String pass = response.getSrting("Введите пароль: ");

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

        value = response.getSrting("Введите имя: ");
        result.setName(value);
        
        value = response.getSrting("Введите логин: ");
        if (repository.findByLogin(value) == null) {
            result.setLogin(value);
        } else {
            UserView.sayMessage("Пользователь с таким логином существует");
            return null;
        }
        value = response.getSrting("Введите пароль: ");
        result.setPassword(value);

        if (user != null) {
            if (user.getRole() == 1) {
                int role = response.getInt("Введите роль пользователя (0 - спортсмен, 1 - администратор)");
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
