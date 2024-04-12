package ru.list.recover.services;

import ru.list.recover.in.Response;
import ru.list.recover.logger.Logger;
import ru.list.recover.models.User;
import ru.list.recover.out.FitnessView;
import ru.list.recover.storages.PracticeRepository;
import ru.list.recover.storages.WorkoutRepository;

/**
 * для обеспечения журналирования реализуем шаблон Посредник 
 * для класса FitnessServiceImpl
 */
public class proxyFitnessServiceImpl implements FitnessService{
    private FitnessServiceImpl service;
    private Logger loger;

    public proxyFitnessServiceImpl(){
        service = new FitnessServiceImpl();
        loger = Logger.getInstance();
    }

    @Override
    public void setUser(User user) {
        service.setUser(user);
    }

    @Override
    public void addListener(IObserve observe) {
        service.addListener(observe);
    }

    @Override
    public void removeListener(IObserve observe) {
        service.removeListener(observe);
    }

    @Override
    public void Observe(Object o) {
        service.Observe(o);
    }

    @Override
    public void setRepository(PracticeRepository repository) {
        service.setRepository(repository);
    }

    @Override
    public PracticeRepository getRepository() {
        return service.getRepository();
    }

    @Override
    public WorkoutRepository getWorkoutRepository() {
        return service.getWorkoutRepository();
    }

    @Override
    public void setWorkoutRepository(WorkoutRepository workoutRepository) {
        service.setWorkoutRepository(workoutRepository);
    }

    @Override
    public void showMenu() {
        FitnessView.showMenu();
        int answer = Response.getInt("Выберите режим работы: ");

        switch (answer) {
            //добавить тренировку
            case 1:
                this.addPractice();
                break;
            //удалить тренировку
            case 2:
                this.deletePractice();
                break;
            //редактировать тренировку
            case 3:
                this.editPractice();
                break;
            //посмотреть тренировки
            case 4:
                this.showPractices();
            //статистика
            case 5:
                this.statistic();
                break;
            case 6:
                this.Observe(null);
                break;
            default:
                break;
        }
        this.Observe(answer);
        
    }

    @Override
    public boolean addPractice() {
        boolean result = service.addPractice();
        loger.addRecord("Добавление тренировки пользователем", result);
        return result;
    }

    @Override
    public boolean deletePractice() {
        boolean result = service.deletePractice();
        loger.addRecord("Удаление тренировки пользователем", result);
        return result;
    }

    @Override
    public boolean editPractice() {
        boolean result = service.editPractice();
        loger.addRecord("Пользователь отредактировал тренировку", result);
        return result;
    }

    @Override
    public void showPractices() {
        service.showPractices();
        loger.addRecord("Пользователь посмотрел свои тренировки", true);
    }

    @Override
    public void statistic() {
        service.statistic();
    }

    @Override
    public void timeStatistic() {
        service.timeStatistic();
        loger.addRecord("Пользователь посмотрел статистику по затраченному времени", true);
    }

    @Override
    public void caloriesStatistic() {
        service.caloriesStatistic();
        loger.addRecord("Пользователь посмотрел статистику по соженным калориям", true);
    }

}
