package ru.list.recover.services;


import ru.list.recover.models.User;
import ru.list.recover.storages.UserRepository;

public interface UserService extends Service{
    
    UserRepository getRepository();
    void setRepository(UserRepository repository);
    void setUser(User user);
    User autorization();
    User login();
    User registration();

    void addListener(IObserve observe);
    void removeListener(IObserve observe);


}
