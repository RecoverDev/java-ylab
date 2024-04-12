package ru.list.recover.services;

import ru.list.recover.models.User;

public interface Service  extends IObserve{

    void setUser(User user);

    void addListener(IObserve observe);
  
    void removeListener(IObserve observe);
}
