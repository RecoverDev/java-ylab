package ru.list.recover.services;

import java.time.LocalDateTime;
import java.time.Month;

import ru.list.recover.logger.Logger;
import ru.list.recover.models.Practice;
import ru.list.recover.models.TypeWorkout;
import ru.list.recover.models.User;
import ru.list.recover.models.Workout;
import ru.list.recover.storages.PracticeRepository;
import ru.list.recover.storages.PracticeRepositoryImpl;
import ru.list.recover.storages.TypeWorkoutRepository;
import ru.list.recover.storages.TypeWorkoutRepositoryImpl;
import ru.list.recover.storages.UserRepository;
import ru.list.recover.storages.UserRepositoryImpl;
import ru.list.recover.storages.WorkoutRepository;
import ru.list.recover.storages.WorkoutRepositoryImpl;

/**
 * класс создает и управляет вызовами сервисов и хранилищ,
 * а также содержит функцию, запускающую главный цикл программы
 */

public class Container implements IObserve{
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

    public Container(){
        userRepository = new UserRepositoryImpl();
        practiceRepository = new PracticeRepositoryImpl();
        typeWorkoutRepository = new TypeWorkoutRepositoryImpl();
        workoutRepository = new WorkoutRepositoryImpl();

        userService = Services.FabricUserService(this);
        userService.setRepository(userRepository);
        mainService = Services.FabricMainService(this);

        fitnessService = Services.FabricFitnessService(this);
        fitnessService.setRepository(practiceRepository);
        fitnessService.setWorkoutRepository(workoutRepository);

        adminService = Services.FabricAdminService(this);
        adminService.setUserRepository(userRepository);
        adminService.setWorkoutRepository(workoutRepository);
        adminService.setPracticeRepository(practiceRepository);

        loger = Logger.getInstance();

        // для тестов
        this.fillTestData();


    }


    /**
     * метод содержит главный цикл программы
     */
    public void run(){

        loger.addRecord("Начало работы программы", true);
        do{

            mainService.title();
            if(currentUser == null){
                currentUser = userService.autorization();
            }else{
                userService.setUser(currentUser);
                mainService.setUser(currentUser);
                fitnessService.setUser(currentUser);
                if(currentUser.getRole() == 0){
                    fitnessService.showMenu();
                }else if(currentUser.getRole() == 1){
                    adminService.showMenu();
                }
               
            }

        }while(repeat);
        mainService.sayGoodBy();
        loger.addRecord("Работа программы завершена", true);

    }


   /**
    * реализуем шаблон Наблюдатель
    */
    @Override
    public void Observe(Object o) {
        if(o instanceof Integer res){
            if( res == 0){
                repeat = false;
            }
        }else {
            currentUser = (User)o;
        }
    }

    
    /**
     * тестовый метод
     * предназначен для начального заполнения репозиториев информацией
     * для проведения тестирования
     */
    private void fillTestData(){

        userRepository.insert(new User(1,"Вася Первый","user1","111",0));
        userRepository.insert(new User(2,"Иван Второй","user2","222",0));
        userRepository.insert(new User(3,"Админ Душнилкин","admin","1234",1));

        typeWorkoutRepository.insert(new TypeWorkout(1, "Кардио"));
        typeWorkoutRepository.insert(new TypeWorkout(2, "Силовая"));
        typeWorkoutRepository.insert(new TypeWorkout(3, "Аэробная"));
        typeWorkoutRepository.insert(new TypeWorkout(4, "Растяжка"));

        workoutRepository.insert(new Workout(1, "Сайкл", typeWorkoutRepository.findById(1), 1500));
        workoutRepository.insert(new Workout(1, "Кроссфит", typeWorkoutRepository.findById(2), 2500));
        workoutRepository.insert(new Workout(1, "Табата", typeWorkoutRepository.findById(2), 1500));
        workoutRepository.insert(new Workout(1, "Йога", typeWorkoutRepository.findById(4), 250));

        practiceRepository.insert(new Practice(1, LocalDateTime.of(2024, Month.APRIL, 10, 10, 0), userRepository.findById(1), workoutRepository.findById(1), 12, 60, 1500, ""));
        practiceRepository.insert(new Practice(2, LocalDateTime.of(2024, Month.APRIL, 9, 10, 0), userRepository.findById(1), workoutRepository.findById(2), 10, 45, 2500, ""));
        practiceRepository.insert(new Practice(3, LocalDateTime.of(2024, Month.APRIL, 10, 12, 0), userRepository.findById(1), workoutRepository.findById(3), 12, 50, 1500, ""));
        practiceRepository.insert(new Practice(4, LocalDateTime.of(2024, Month.APRIL, 8, 10, 0), userRepository.findById(2), workoutRepository.findById(1), 12, 60, 1500, ""));
        practiceRepository.insert(new Practice(5, LocalDateTime.of(2024, Month.APRIL, 9, 10, 0), userRepository.findById(2), workoutRepository.findById(2), 8, 45, 1500, ""));
        practiceRepository.insert(new Practice(6, LocalDateTime.of(2024, Month.APRIL, 10, 10, 0), userRepository.findById(2), workoutRepository.findById(3), 16, 50, 2500, ""));




    }

}
