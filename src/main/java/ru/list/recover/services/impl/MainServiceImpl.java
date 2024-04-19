package ru.list.recover.services.impl;

import java.util.ArrayList;
import java.util.List;

import ru.list.recover.in.views.MainView;
import ru.list.recover.models.User;
import ru.list.recover.services.IObserve;
import ru.list.recover.services.MainService;

/**
 * класс реализует функционал 
 */
public class MainServiceImpl implements MainService {
    private User user = null;
    private List<IObserve> listeners = new ArrayList<>();


    
    /** 
     * @param user
     */
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

    public void showTitle(){
        MainView.showTitle();
    }

    public void sayGoodBy(){
        MainView.showFinal();
    }

}
