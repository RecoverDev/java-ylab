package ru.list.recover.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import ru.list.recover.in.Response;
import ru.list.recover.logger.Logger;
import ru.list.recover.models.Practice;
import ru.list.recover.models.TypeWorkout;
import ru.list.recover.models.User;
import ru.list.recover.models.Workout;
import ru.list.recover.out.FitnessView;
import ru.list.recover.services.FitnessService;
import ru.list.recover.services.IObserve;
import ru.list.recover.storages.PracticeRepository;
import ru.list.recover.storages.WorkoutRepository;

/**
 * класс реализует функционал работы пользователя (спортсмена)
 */
public class FitnessServiceImpl implements FitnessService {
    private User user;
    private Logger logger;
    private List<IObserve> listeners = new ArrayList<>();
    private PracticeRepository repository;
    private WorkoutRepository workouts;
    private Response response;

    public FitnessServiceImpl(PracticeRepository practiceRepository, WorkoutRepository workoutRepository, Response response, Logger logger) {
        this.repository = practiceRepository;
        this.workouts = workoutRepository;
        this.response = response;
        this.logger = logger;
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

    /**
     * выводит меню выбора возможных действия для пользователя
     */
    public void showMenu() {
        FitnessView.showMenu();
        int answer = response.getInt("Выберите режим работы: ");

        switch (answer) {
            // добавить тренировку
            case 1:
                logger.addRecord("Добавление тренировки", this.addPractice());
                break;
            // удалить тренировку
            case 2:
                logger.addRecord("Удаление тренировки", this.deletePractice());
                break;
            // редактировать тренировку
            case 3:
                logger.addRecord("Редактирование тренировки", this.editPractice());
                break;
            // посмотреть тренировки
            case 4:
                this.showPractices();
                logger.addRecord("Просмотр списка тренировок", true);
                break;
                // статистика
            case 5:
                this.statistic();
                break;
            case 6:
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
     * 
     * @return true - если тренировка успешно добавлена, иначе - false
     */
    public boolean addPractice() {
        int value = 0;
        Practice practice = new Practice(1, LocalDateTime.now(), user, null, 0, 0, 0, "");

        List<Workout> listWorkouts = workouts.findAll();
        List<String> strWorkouts = new ArrayList<>();
        for (Workout w : listWorkouts) {
            strWorkouts.add(w.toString());
        }

        FitnessView.showMessage("Выверите тренировку из списка:");
        FitnessView.showWorkouts(strWorkouts);
        value = response.getInt("Введите номер тренировки: (1 - " + workouts.getCount() + "): ");

        Workout workout = workouts.findById(value);
        if (workout == null) {
            FitnessView.showMessage("Такой тренировки не существует");
            return false;
        }

        if (presentTypeWorkoput(workout.getType())) {
            FitnessView.showMessage("Тренировка такого типа сегодня уже была");
            return false;
        }
        practice.setWorkout(workout);

        LocalDate date = response.getDate("Введите дату тренировки в формате ГГГГ-ММ-ДД: ");
        if (date == null) {
            FitnessView.showMessage("Не корректное значение даты");
            return false;
        }

        LocalTime time = response.getTime("Введите время начала тренировки в формате ЧЧ:ММ:СС: ");
        if (time == null) {
            FitnessView.showMessage("Не корректное значение времени");
            return false;
        }
        practice.setDate(LocalDateTime.of(date, time));

        value = response.getInt("Введите количество повторений: ");
        if (value < 1) {
            FitnessView.showMessage("Не корректное значение количества повторений (" +  value + ")");
            return false;
        }
        practice.setCountExercise(value);

        value = response.getInt("Введите длительность тренировки (минуты): ");
        if (value < 1) {
            FitnessView.showMessage("Не корректное значение продолжительности тренировки (" +  value + ")");
            return false;
        }
        practice.setAmount(value);

        value = response.getInt("Введите количесто потраченных калорий: ");
        if (value < 1) {
            FitnessView.showMessage("Не корректное значение количества потраченных калорий (" +  value + ")");
            return false;
        }
        practice.setCalories(value);

        String desc = response.getSrting("Введите дополнительные параметры: ");
        practice.setDescription(desc);

        boolean result = repository.insert(practice);
        if (result) {
            FitnessView.showMessage("Тренировка успешно добавлена");
        } else {
            FitnessView.showMessage("Тренировка не добавлена");
        }
        response.getSrting("Нажмите ENTER ...");

        return result;
    }

    /**
     * удаляет выбранную тренировку пользователя
     * 
     * @return true - если тренировка успешно удалена, иначе - false
     */
    public boolean deletePractice() {
        List<Practice> practices = getPracticeByUser();

        List<String> strPractices = new ArrayList<>();
        practices.forEach(p -> strPractices.add(p.toString()));

        FitnessView.showMessage("Выберите тренировку из списка:");
        FitnessView.showPractices(strPractices);
        int value = response.getInt("Введите номер удаляемой тренировки(1 - " + practices.size() + "): ");
        Practice practice = repository.findById(value);

        if (practice == null) {
            FitnessView.showMessage("Такой тренировки не существует");
            return false;
        }
        return repository.delete(practice);

    }

    /**
     * редактирует выбранную тренировку
     */
    public boolean editPractice() {
        List<Practice> practices = getPracticeByUser();
        List<String> strPractices = new ArrayList<>();
        practices.forEach(p -> strPractices.add(p.toString()));

        FitnessView.showMessage("Выберите тренировку для редактирования из списка:");
        FitnessView.showPractices(strPractices);
        int value = response.getInt("Введите номер редактируемой тренировки(1 - " + practices.size() + "): ");
        Practice practice = repository.findById(value);

        if (practice == null) {
            FitnessView.showMessage("Такой тренировки не существует");
            return false;
        }

        FitnessView.showMessage("Редактируется тренировка:");
        FitnessView.showMessage(practice.toString());

        value = response.getInt("Введите количество повторений (было - " + practice.getCountExercise() + "): ");
        if (value < 1) {
            FitnessView.showMessage("Не корректное значение количества повторений (" +  value + ")");
            return false;
        }
        practice.setCountExercise(value);

        value = response.getInt("Введите длительность тренировки (минуты) (было - " + practice.getAmount() + "): ");
        if (value < 1) {
            FitnessView.showMessage("Не корректное значение продолжительности тренировки (" +  value + ")");
            return false;
        }
        practice.setAmount(value);

        value = response.getInt("Введите количесто потраченных калорий (было - " + practice.getCalories() + "): ");
        if (value < 1) {
            FitnessView.showMessage("Не корректное значение количества потраченных калорий (" +  value + ")");
            return false;
        }
        practice.setCalories(value);

        String desc = response.getSrting("Введите дополнительные параметры: ");
        practice.setDescription(desc);

        repository.update(practice);
        return true;
    }

    /**
     * показывает список тренировок пользователя
     */
    public void showPractices() {
        List<Practice> practices = getPracticeByUser();

        List<String> strPractices = new ArrayList<>();
        practices.forEach(p -> strPractices.add(p.toString()));

        FitnessView.showMessage("Список пройденных тренировок:");
        FitnessView.showPractices(strPractices);
        response.getSrting("Для завершения просмотра нажмите ENTER ...");
    }

    /**
     * показывает статистику по пользователю
     */
    public void statistic() {
        FitnessView.showMenuStatistic();
        int answer = response.getInt("Введите номер варианта статистики: ");
        switch (answer) {
            case 1:
                this.timeStatistic();
                logger.addRecord("Просмотр статистики по использованию времени", true);
                break;
            case 2:
                this.caloriesStatistic();
                logger.addRecord("Просмотр статистики по соженным калориям", true);
                break;
            default:
                break;
        }

    }

    /**
     * показывает статистику по пользователю по общей продолжительности тренировок
     * за выбранный период
     */
    public void timeStatistic() {
        LocalDate date1 = response.getDate("Введите начало периода в формате ГГГГ-ММ-ДД: ");
        LocalDate date2 = response.getDate("Введите окончание периода в формате ГГГГ-ММ-ДД: ");
        List<Practice> list = this.getPracticeByUser();
        int fullTime = list.stream()
                .filter(p -> (p.getDate().toLocalDate().compareTo(date1) >= 0
                        && p.getDate().toLocalDate().compareTo(date2) <= 0))
                .mapToInt(p -> p.getAmount()).sum();

        FitnessView.showMessage("Общая продолжительность тренировок за выбранный период: " + fullTime);
        response.getSrting("Для завершения просмотра нажмите ENTER ...");

    }

    /**
     * показывает статистику по пользователю по количеству соженных калорий за
     * выбранный период
     */
    public void caloriesStatistic() {
        LocalDate date1 = response.getDate("Введите начало периода в формате ГГГГ-ММ-ДД: ");
        LocalDate date2 = response.getDate("Введите окончание периода в формате ГГГГ-ММ-ДД: ");
        List<Practice> list = this.getPracticeByUser();
        double fullCalories = list.stream()
                .filter(p -> (p.getDate().toLocalDate().compareTo(date1) >= 0
                        && p.getDate().toLocalDate().compareTo(date2) <= 0))
                .mapToDouble(p -> p.getCalories()).sum();

        FitnessView.showMessage("Количество соженных калорий за выбранный период: " + fullCalories);
        response.getSrting("Для завершения просмотра нажмите ENTER ...");

    }

    /**
     * возвращает список тренировок пользователя
     * 
     * @return - список тренировок пользователя
     */
    private List<Practice> getPracticeByUser() {
        return repository.findAll().stream().filter(p -> p.getUser() == user).toList();
    }

    /**
     * проверяет наличие тренировки указанного типа за сегодняшний день
     * 
     * @param type - искомый тип тренировки
     * @return true - если тренировки такого типа сегодня были
     */
    private boolean presentTypeWorkoput(TypeWorkout type) {
        if (repository.findAll().stream().filter(p -> p.getUser() == user)
                .filter(p -> p.getDate().toLocalDate().equals(LocalDate.now()))
                .filter(p -> p.getWorkout().getType() == type).count() > 0) {
            return true;
        }
        return false;
    }

}
