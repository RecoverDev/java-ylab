package ru.list.recover.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ru.list.recover.in.Response;
import ru.list.recover.models.Practice;
import ru.list.recover.models.TypeWorkout;
import ru.list.recover.models.User;
import ru.list.recover.models.Workout;
import ru.list.recover.out.FitnessView;
import ru.list.recover.storages.PracticeRepository;
import ru.list.recover.storages.WorkoutRepository;

public class FitnessServiceImpl implements FitnessService{
    private User user;
    private List<IObserve> listeners = new ArrayList<>();
    private PracticeRepository repository;
    private WorkoutRepository workouts;

    @Override
    public WorkoutRepository getWorkoutRepository(){
        return workouts;
    }

    @Override
    public void setWorkoutRepository(WorkoutRepository workoutRepository){
        this.workouts = workoutRepository;
    }

    @Override
    public void setRepository(PracticeRepository repository) {
        this.repository = repository;
    }

    @Override
    public PracticeRepository getRepository() {
        return this.repository;
    }
    


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
            //статистика
            case 4:
                this.statistic();
                break;
            case 5:
                user = null;
                this.Observe(user);
                break;
            default:
                break;
        }
        this.Observe(answer);
        
    }

    /**
     * добавляет новую тренировку в журнал спортсмену
     */
    private void addPractice(){
        int value = 0;
        Practice practice = new Practice(1, LocalDateTime.now(), user, null, 0, 0, 0, "");

        List<Workout> listWorkouts = workouts.findAll();
        List<String> strWorkouts = new ArrayList<>();
        for (Workout w : listWorkouts) {
            strWorkouts.add(w.toString());
        }

        FitnessView.showMessage("Выверите тренировку из списка:");
        FitnessView.showWorkouts(strWorkouts);
        value = Response.getInt("Введите номер тренировки: (1 - " + workouts.getCount() + "): ");

        Workout workout = workouts.findById(value);
        if(workout == null){
            FitnessView.showMessage("Такой тренировки не существует");
            return;
        }

        if (presentTypeWorkoput(workout.getType())){
            FitnessView.showMessage("Тренировка такого типа сегодня уже была");
            return;
        }
        practice.setWorkout(workout);
       
        value = Response.getInt("Введите количество повторений: ");
        practice.setCountExercise(Math.max(value, 0));

        value = Response.getInt("Введите длительность тренировки (минуты): ");
        practice.setAmount(Math.max(value, 0));

        value = Response.getInt("Введите количесто потраченных калорий: ");
        practice.setCalories(Math.max(value, 0));

        String desc = Response.getSrting("Введите дополнительные параметры: ");
        practice.setDescription(desc);

        repository.insert(practice);
    }

    /**
     * удаляет выбранную тренировку пользователя
     */
    private void deletePractice(){
        List<Practice> practices = getPracticeByUser();

        List<String> strPractices = new ArrayList<>();
        practices.forEach(p -> strPractices.add(p.toString()));

        FitnessView.showMessage("Выберите тренировку из списка:");
        FitnessView.showPractices(strPractices);
        int value = Response.getInt("Введите номер удаляемой тренировки(1 - " + practices.size() + "): ");
        Practice practice = repository.findById(value);

        if(practice == null){
            FitnessView.showMessage("Такой тренировки не существует");
            return;
        }
        repository.delete(practice);

    }

    /**
     * редактирует выбранную тренировку
     */
    private void editPractice(){
        List<Practice> practices = getPracticeByUser();

        List<String> strPractices = new ArrayList<>();
        practices.forEach(p -> strPractices.add(p.toString()));

        FitnessView.showMessage("Выберите тренировку для редактирования из списка:");
        FitnessView.showPractices(strPractices);
        int value = Response.getInt("Введите номер редактируемой тренировки(1 - " + practices.size() + "): ");
        Practice practice = repository.findById(value);

        if(practice == null){
            FitnessView.showMessage("Такой тренировки не существует");
            return;
        }

        FitnessView.showMessage("Редактируется тренировка:");
        FitnessView.showMessage(practice.toString());

        value = Response.getInt("Введите количество повторений (было - " + practice.getCountExercise() + "): ");
        practice.setCountExercise(Math.max(value, 0));

        value = Response.getInt("Введите длительность тренировки (минуты) (было - " + practice.getAmount() + "): ");
        practice.setAmount(Math.max(value, 0));

        value = Response.getInt("Введите количесто потраченных калорий (было - " + practice.getCalories() + "): ");
        practice.setCalories(Math.max(value, 0));

        String desc = Response.getSrting("Введите дополнительные параметры: ");
        practice.setDescription(desc);

        repository.update(practice);


    }

    /**
     * показывает статистику по пользователю
     */
    private void statistic(){
        FitnessView.showMenuStatistic();
        int answer = Response.getInt("Введите номер варианта статистики: ");
        switch (answer) {
            case 1:
                this.timeStatistic();
                break;
            case 2:
                this.caloriesStatistic();
                break;
            default:
                break;
        }

    }

    /**
     * показывает статистику по пользователю по общей продолжительности тренировок
     * за выбранный период
     */
    private void timeStatistic(){
        LocalDate date1 = Response.getDate("Введите начало периода в формате ГГГГ-ММ-ДД: ");
        LocalDate date2 = Response.getDate("Введите окончание периода в формате ГГГГ-ММ-ДД: ");
        List<Practice> list = this.getPracticeByUser();
        int fullTime = list.stream().filter(p -> (p.getDate().toLocalDate().compareTo(date1) >= 0 && p.getDate().toLocalDate().compareTo(date2) <= 0))
                                    .mapToInt(p -> p.getAmount()).sum();
        
        FitnessView.showMessage("Общая продолжительность тренировок за выбранный период: " + fullTime);


    }

    /**
     * показывает статистику по пользователю по количеству соженных калорий за выбранный период
     */
    private void caloriesStatistic(){
        LocalDate date1 = Response.getDate("Введите начало периода в формате ГГГГ-ММ-ДД: ");
        LocalDate date2 = Response.getDate("Введите окончание периода в формате ГГГГ-ММ-ДД: ");
        List<Practice> list = this.getPracticeByUser();
        double fullCalories = list.stream().filter(p -> (p.getDate().toLocalDate().compareTo(date1) >= 0 && p.getDate().toLocalDate().compareTo(date2) <= 0))
                                    .mapToDouble(p -> p.getCalories()).sum();
        
        FitnessView.showMessage("Количество соженных калорий за выбранный период: " + fullCalories);

    }

    /**
     * возвращает список тренировок пользователя
     * @return - список тренировок пользователя
     */
    private List<Practice> getPracticeByUser(){
        return repository.findAll().stream().filter(p -> p.getUser() == user).toList();
    }

    /**
     * проверяет наличие тренировки указанного типа за сегодняшний день
     * @param type - искомый тип тренировки
     * @return true - если тренировки такого типа сегодня были
     */
    private boolean presentTypeWorkoput(TypeWorkout type){
        if(repository.findAll().stream().filter(p -> p.getUser() == user)
                                        .filter(p -> p.getDate().toLocalDate().equals(LocalDate.now()))
                                        .filter(p -> p.getWorkout().getType() == type).count() > 0){
            return true;
        }
        return false;
    }


}
