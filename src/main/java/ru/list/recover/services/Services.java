package ru.list.recover.services;

public class Services {

    public static UserService FabricUserService(IObserve observe){
        UserService userService = new UserServiceImpl();
        userService.addListener(observe);
        return userService;
    }

    public static MainService FabricMainService(IObserve observe){
        MainService mainService = new MainServiceImpl();
        mainService.addListener(observe);
        return mainService;
    }

    public static FitnessService FabricFitnessService(IObserve observe){
        FitnessService fitnessService = new FitnessServiceImpl();
        fitnessService.addListener(observe);
        return fitnessService;
    }

}
