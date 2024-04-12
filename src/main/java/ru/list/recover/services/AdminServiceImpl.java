package ru.list.recover.services;

import java.util.ArrayList;
import java.util.List;

import ru.list.recover.models.User;

public class AdminServiceImpl implements AdminService {
    private User user;
    private List<IObserve> listeners = new ArrayList<>();


    @Override
    public void setUser(User user) {
        this.user = user;
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
    public void Observe(Object o) {
        for (IObserve obleve : listeners) {
            obleve.Observe(o);
        }
    }

    public void showMenu(){

    }

}
