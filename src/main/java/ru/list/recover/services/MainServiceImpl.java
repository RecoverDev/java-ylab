package ru.list.recover.services;

import java.util.ArrayList;
import java.util.List;

import ru.list.recover.models.User;
import ru.list.recover.out.MainView;

public class MainServiceImpl implements MainService {
    private User user = null;
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

    public void title(){
        MainView.showTitle();
    }

    public void sayGoodBy(){
        MainView.showFinal();
    }

    public void showMenu(){
        if(user.getRole() == 0){
            this.showFitnessMenu();
        }else if (user.getRole() == 1){
            this.showAdminMenu();
        }
    }

    public void showFitnessMenu(){

    }

    public void showAdminMenu(){

    }

}