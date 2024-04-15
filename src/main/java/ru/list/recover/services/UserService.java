package ru.list.recover.services;

import ru.list.recover.models.User;

public interface UserService extends Service {


    void setUser(User user);

    User autorization();

    User login();

    User registration();

    void addListener(IObserve observe);

    void removeListener(IObserve observe);

}
