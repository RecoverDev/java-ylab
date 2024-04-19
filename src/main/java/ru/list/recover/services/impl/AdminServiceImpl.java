package ru.list.recover.services.impl;

import java.util.ArrayList;
import java.util.List;

import ru.list.recover.in.Response;
import ru.list.recover.in.views.AdminView;
import ru.list.recover.logger.Logger;
import ru.list.recover.models.Practice;
import ru.list.recover.models.User;
import ru.list.recover.models.Workout;
import ru.list.recover.services.AdminService;
import ru.list.recover.services.IObserve;
import ru.list.recover.storages.PracticeRepository;
import ru.list.recover.storages.UserRepository;
import ru.list.recover.storages.WorkoutRepository;

/**
 * класс реализует функционал по работе администратора
 */
public class AdminServiceImpl implements AdminService {
    private User user;
    private Logger log;
    private List<IObserve> listeners = new ArrayList<>();
    private UserRepository userRepository;
    private WorkoutRepository workoutRepository;
    private PracticeRepository practiceRepository;
    private Response response;

    public AdminServiceImpl(UserRepository userRepository, WorkoutRepository workoutRepository, PracticeRepository practiceRepository, Response response, Logger logger) {
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
        this.practiceRepository = practiceRepository;
        this.response = response;
        this.log = logger;
    }

    /**
     * устанавливает текущего пользователя программы
     */
    @Override
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * добавляет наблюдателя в список (шаблон Наблюдатель)
     */
    @Override
    public void addListener(IObserve observe) {
        this.listeners.add(observe);
    }

    /**
     * удаляет наблюдателя из списка (шаблон Наблюдатель)
     */
    @Override
    public void removeListener(IObserve observe) {
        this.listeners.remove(observe);
    }

    /**
     * реализует шаблон Наблюдатель
     */
    @Override
    public void Observe(Object o) {
        for (IObserve obleve : listeners) {
            obleve.Observe(o);
        }
    }

    /**
     * показывает главное меню работы администратора
     */
    public void showMenu() {
        AdminView.showMenu();
        int answer = response.getInt("Введите номер пункта меню: ");

        switch (answer) {
            // список пользователей
            case 1:
                this.showList(this.getListUsers());
                break;
            // список доступных тренировок
            case 2:
                this.showList(this.getListWorkouts());
                break;
            // посещения пользователей
            case 3:
                this.showList(this.getListPractices());
                break;
            // просмотр журнала
            case 4:
                this.showList(this.getListLog());
                break;
            case 5:
                this.Observe(null);
                break;
            default:
                break;
        }

        this.Observe(answer);

    }

    /**
     * формирует список пользователей программы
     */
    public List<String> getListUsers() {
        List<User> listUsers = this.userRepository.findAll();
        List<String> strUsers = new ArrayList<>();

        listUsers.forEach(u -> strUsers.add(u.toString()));

        return strUsers;

    }

    /**
     * формирует список доступных вариантов тренировок
     */
    public List<String> getListWorkouts() {
        List<Workout> listWorkouts = workoutRepository.findAll();
        List<String> strWorkouts = new ArrayList<>();

        listWorkouts.forEach(w -> strWorkouts.add(w.toString()));

        return strWorkouts;

    }

    /**
     * формирует список проведенных тренировок
     */
    public List<String> getListPractices() {
        List<Practice> listPractices = practiceRepository.findAll();
        List<String> strPractice = new ArrayList<>();
        for (Practice p : listPractices) {
            String descPractice = String.format("%s %s %s(%s) %s минут", p.getDate().toString(), p.getUser().getName(),
                    p.getWorkout().getName(), p.getWorkout().getType().getName(), p.getAmount());
            strPractice.add(descPractice);
        }

        return strPractice;
    }

    /**
     * формирует список записей содержимого журнала
     */
    public List<String> getListLog() {
        List<String> strLog = new ArrayList<>();
        log.getLog().forEach(r -> strLog.add(r.toString()));

        return strLog;
    }

    private void showList(List<String> list) {
        AdminView.showList(list);
        response.getSrting("Для завершения просмотра нажмите ENTER ...");
    }

}
