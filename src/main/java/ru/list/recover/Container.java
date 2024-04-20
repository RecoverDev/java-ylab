package ru.list.recover;

import liquibase.exception.LiquibaseException;
import ru.list.recover.db.DBConnection;
import ru.list.recover.db.FitnessProperties;
import ru.list.recover.db.Migration;
import ru.list.recover.in.Response;
import ru.list.recover.logger.Logger;
import ru.list.recover.models.User;
import ru.list.recover.services.AdminService;
import ru.list.recover.services.FitnessService;
import ru.list.recover.services.IObserve;
import ru.list.recover.services.MainService;
import ru.list.recover.services.UserService;
import ru.list.recover.services.impl.AdminServiceImpl;
import ru.list.recover.services.impl.FitnessServiceImpl;
import ru.list.recover.services.impl.MainServiceImpl;
import ru.list.recover.services.impl.UserServiceImpl;
import ru.list.recover.storages.PracticeRepository;
import ru.list.recover.storages.TypeWorkoutRepository;
import ru.list.recover.storages.UserRepository;
import ru.list.recover.storages.WorkoutRepository;
import ru.list.recover.storages.impl.PracticeRepositoryDB;
import ru.list.recover.storages.impl.TypeWorkoutRepositoryDB;
import ru.list.recover.storages.impl.UserRepositoryDB;
import ru.list.recover.storages.impl.WorkoutRepositoryDB;

/**
 * класс создает и управляет вызовами сервисов и хранилищ,
 * а также содержит функцию, запускающую главный цикл программы
 */

public class Container implements IObserve {
    private UserService userService;
    private MainService mainService;
    private FitnessService fitnessService;
    private AdminService adminService;

    private UserRepository userRepository;
    private PracticeRepository practiceRepository;
    private WorkoutRepository workoutRepository;
    private TypeWorkoutRepository typeWorkoutRepository;

    private User currentUser = null;
    private boolean repeat = true;

    private Logger loger;

    public Container() {
        loger = new Logger();
        FitnessProperties properties = new FitnessProperties("src/main/resources/fitness.properties", loger);
        properties.load();

        DBConnection con = new DBConnection(properties, loger);
        con.connect();
        Migration migration = new Migration(con.getConnection(), loger,"fitness");
        try {
            migration.migrate();
        } catch (LiquibaseException e) {
            loger.addRecord("Ошибка миграции: " + e.getMessage(), false);
        }

        con.connect();

        userRepository = new UserRepositoryDB(con.getConnection(), "fitness");
        practiceRepository = new PracticeRepositoryDB(con.getConnection(), "fitness");
        typeWorkoutRepository = new TypeWorkoutRepositoryDB(con.getConnection(), "fitness");
        workoutRepository = new WorkoutRepositoryDB(con.getConnection(), "fitness");

        userService =  new UserServiceImpl(userRepository, new Response(), loger);
        userService.addListener(this);

        mainService = new MainServiceImpl();
        mainService.addListener(this);

        fitnessService = new FitnessServiceImpl(practiceRepository, workoutRepository, new Response(), loger);
        fitnessService.addListener(this); 

        adminService = new AdminServiceImpl(userRepository, workoutRepository, practiceRepository, new Response(), loger);
        adminService.addListener(this);

    }

    /**
     * метод содержит главный цикл программы
     */
    public void run() {

        loger.addRecord("Начало работы программы", true);
        do {

            mainService.showTitle();
            if (currentUser == null) {
                currentUser = userService.autorization();
            } else {
                userService.setUser(currentUser);
                mainService.setUser(currentUser);
                fitnessService.setUser(currentUser);
                if (currentUser.getRole() == 0) {
                    fitnessService.showMenu();
                } else if (currentUser.getRole() == 1) {
                    adminService.showMenu();
                }

            }

        } while (repeat);
        mainService.sayGoodBy();
        loger.addRecord("Работа программы завершена", true);

    }

    /**
     * реализуем шаблон Наблюдатель
     */
    @Override
    public void Observe(Object o) {
        if (o instanceof Integer res) {
            if (res == 0) {
                repeat = false;
            }
        } else {
            currentUser = (User) o;
        }
    }

}
