package ru.list.recover.services;

import java.util.ArrayList;
import java.util.List;

import ru.list.recover.in.Response;
import ru.list.recover.logger.Logger;
import ru.list.recover.models.Practice;
import ru.list.recover.models.User;
import ru.list.recover.models.Workout;
import ru.list.recover.out.AdminView;
import ru.list.recover.storages.PracticeRepository;
import ru.list.recover.storages.UserRepository;
import ru.list.recover.storages.WorkoutRepository;

/**
 * класс реализует функционал по работе администратора
 */
public class AdminServiceImpl implements AdminService {
    private User user;
    private List<IObserve> listeners = new ArrayList<>();
    private UserRepository userRepository;
    private WorkoutRepository workoutRepository;
    private PracticeRepository practiceRepository;

    /**
     * возвращает список посещений пользователей
     * 
     * @return объект класса PracticeRepository
     */
    public PracticeRepository getPracticeRepository() {
        return practiceRepository;
    }

    /**
     * определяет в классе список посещений пользователей
     * 
     * @param practiceRepository объект класса PracticeRepository
     */
    public void setPracticeRepository(PracticeRepository practiceRepository) {
        this.practiceRepository = practiceRepository;
    }

    /**
     * возвращает хранилище списка возможных вариантов тренировок
     * 
     * @return объект класса WorkoutRepository
     */
    public WorkoutRepository getWorkoutRepository() {
        return workoutRepository;
    }

    /**
     * определяет в классе список возможных вариантов тренировок
     * 
     * @param workoutRepository объект класса WorkoutRepository
     */
    public void setWorkoutRepository(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    /**
     * возвращает хранилище списка пользователей программы
     * 
     * @return хранилище списка пользователей
     */
    public UserRepository getUserRepository() {
        return userRepository;
    }

    /**
     * определяет в классе хранилище списка пользователей
     * 
     * @param userRepository - Repository пользователей (класс User)
     */
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        int answer = Response.getInt("Введите номер пункта меню: ");

        switch (answer) {
            // список пользователей
            case 1:
                this.showUsers();
                break;
            // список доступных тренировок
            case 2:
                this.showWorkouts();
                break;
            // посещения пользователей
            case 3:
                this.showPractices();
                break;
            // просмотр журнала
            case 4:
                this.showLog();
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
     * выводит список пользователей программы
     */
    public void showUsers() {
        List<User> listUsers = this.getUserRepository().findAll();
        List<String> strUsers = new ArrayList<>();

        listUsers.forEach(u -> strUsers.add(u.toString()));

        AdminView.showList(strUsers);
        Response.getSrting("Для завершения просмотра нажмите ENTER ...");
    }

    /**
     * выводит список доступных вариантов тренировок
     */
    public void showWorkouts() {
        List<Workout> listWorkouts = workoutRepository.findAll();
        List<String> strWorkouts = new ArrayList<>();

        listWorkouts.forEach(w -> strWorkouts.add(w.toString()));

        AdminView.showList(strWorkouts);
        Response.getSrting("Для завершения просмотра нажмите ENTER ...");
    }

    /**
     * выводит список проведенных тренировок
     */
    public void showPractices() {
        List<Practice> listPractices = practiceRepository.findAll();
        List<String> strPractice = new ArrayList<>();
        for (Practice p : listPractices) {
            String descPractice = String.format("%s %s %s(%s) %s минут", p.getDate().toString(), p.getUser().getName(),
                    p.getWorkout().getName(), p.getWorkout().getType().getName(), p.getAmount());
            strPractice.add(descPractice);
        }
        AdminView.showList(strPractice);
        Response.getSrting("Для завершения просмотра нажмите ENTER ...");
    }

    /**
     * выводит содержимое журнала
     */
    public void showLog() {
        Logger log = Logger.getInstance();

        List<String> strLog = new ArrayList<>();
        log.getLog().forEach(r -> strLog.add(r.toString()));
        AdminView.showList(strLog);
        Response.getSrting("Для завершения просмотра нажмите ENTER ...");
    }

}
