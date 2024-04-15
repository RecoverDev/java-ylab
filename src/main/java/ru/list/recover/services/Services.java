package ru.list.recover.services;

/**
 * класс реализует фабричные методы дляполучения сервисов
 */
public class Services {

    /**
     * Фабричный метод получения сервиса по работе со списком пользователей
     * 
     * @param observe - ссылка на слушателя событий
     * @return сервис UserService
     */
    public static UserService FabricUserService(IObserve observe) {
        UserService userService = new UserServiceImpl();
        userService.addListener(observe);
        return userService;
    }

    /**
     * Фабричный метод получения главного сервиса
     * 
     * @param observe - ссылка на слушателя событий
     * @return сервис MainService
     */
    public static MainService FabricMainService(IObserve observe) {
        MainService mainService = new MainServiceImpl();
        mainService.addListener(observe);
        return mainService;
    }

    /**
     * Фабричный метод получения сервиса для работы пользователя (спортсмена)
     * 
     * @param observe - ссылка на слушателя событий
     * @return сервис FitnessService
     */
    public static FitnessService FabricFitnessService(IObserve observe) {
        // заменили объект на прокси для обеспечения журналирования
        FitnessService fitnessService = new proxyFitnessServiceImpl();
        fitnessService.addListener(observe);
        return fitnessService;
    }

    /**
     * Фабричный метод получения сервиса для работы администратора
     * 
     * @param observe - ссылка на слушателя событий
     * @return сервис AdminService
     */
    public static AdminService FabricAdminService(IObserve observe) {
        AdminService adminService = new AdminServiceImpl();
        adminService.addListener(observe);
        return adminService;
    }

}
