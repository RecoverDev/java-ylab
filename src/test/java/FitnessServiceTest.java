import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import ru.list.recover.in.Response;
import ru.list.recover.logger.Logger;
import ru.list.recover.models.Practice;
import ru.list.recover.models.TypeWorkout;
import ru.list.recover.models.User;
import ru.list.recover.models.Workout;
import ru.list.recover.services.FitnessService;
import ru.list.recover.services.impl.FitnessServiceImpl;
import ru.list.recover.storages.PracticeRepository;
import ru.list.recover.storages.WorkoutRepository;

public class FitnessServiceTest {

    @Mock
    PracticeRepository practiceRepositoryMockito;

    @Mock
    WorkoutRepository workoutRepositoryMockito;

    @Mock
    Response responseMockito;

    private User user = new User(1, "Test User", "user1", "111", 0);
    private List<Workout> workouts = List.of(
        new Workout(1, "Workout1", new TypeWorkout(1, "Type1"), 1500),
        new Workout(2, "Workout2", new TypeWorkout(1, "Type1"), 1500),
        new Workout(3, "Workout3", new TypeWorkout(2, "Type2"), 1500),
        new Workout(4, "Workout4", new TypeWorkout(2, "Type2"), 1500)
    );
    private List<Practice> practices = List.of(
        new Practice(1, LocalDateTime.of(2024, Month.APRIL, 9, 10, 0),
            user, workouts.get(0), 12, 60, 1500, ""),
        new Practice(2, LocalDateTime.of(2024, Month.APRIL, 10, 10, 0),
            user, workouts.get(1), 12, 60, 1500, ""),
        new Practice(3, LocalDateTime.of(2024, Month.APRIL, 11, 10, 0),
            user, workouts.get(2), 12, 60, 1500, ""),
            new Practice(4, LocalDateTime.of(2024, Month.APRIL, 12, 10, 0),
            user, workouts.get(3), 12, 60, 1500, "")
    );


    @Test
    @DisplayName("Вывод на экран списка проведенных тренировок")
    public void ShowPracticeTest() {
        workoutRepositoryMockito = Mockito.mock(WorkoutRepository.class);
        Mockito.when(workoutRepositoryMockito.findAll()).thenReturn(workouts);
        practiceRepositoryMockito = Mockito.mock(PracticeRepository.class);
        Mockito.when(practiceRepositoryMockito.findAll()).thenReturn(practices);
        responseMockito = Mockito.mock(Response.class);
        Mockito.when(responseMockito.getSrting("Для завершения просмотра нажмите ENTER ...")).thenReturn("\n");



        FitnessService service = new FitnessServiceImpl(practiceRepositoryMockito, workoutRepositoryMockito, responseMockito, new Logger());
        service.setUser(user);
        service.showPractices();

    }

    @Test
    @DisplayName("Добавление тренировки")
    public void AddPracticeTest() {
        workoutRepositoryMockito = Mockito.mock(WorkoutRepository.class);
        Mockito.when(workoutRepositoryMockito.findAll()).thenReturn(workouts);
        Mockito.when(workoutRepositoryMockito.findById(1)).thenReturn(workouts.get(0));

        practiceRepositoryMockito = Mockito.mock(PracticeRepository.class);
        Mockito.when(practiceRepositoryMockito.findAll()).thenReturn(practices);
        Mockito.when(practiceRepositoryMockito.insert(any(Practice.class))).thenReturn(true);


        responseMockito = Mockito.mock(Response.class);
        Mockito.when(responseMockito.getDate("Введите дату тренировки в формате ГГГГ-ММ-ДД: ")).thenReturn(LocalDate.now());
        Mockito.when(responseMockito.getTime("Введите время начала тренировки в формате ЧЧ:ММ:СС: ")).thenReturn(LocalTime.now());
        Mockito.when(responseMockito.getInt(any())).thenReturn(1, 12, 45, 1500);
        Mockito.when(responseMockito.getSrting(any())).thenReturn("коммертарий","\n");


        FitnessService service = new FitnessServiceImpl(practiceRepositoryMockito, workoutRepositoryMockito, responseMockito, new Logger());
        service.setUser(user);

        boolean result = service.addPractice();

        Assertions.assertEquals(result, true);

    }

    @Test
    @DisplayName("Добавление тренировки с плохой датой (НЕУСПЕХ)")
    public void AddPracticeBadDateTest() {
        workoutRepositoryMockito = Mockito.mock(WorkoutRepository.class);
        Mockito.when(workoutRepositoryMockito.findAll()).thenReturn(workouts);
        Mockito.when(workoutRepositoryMockito.findById(1)).thenReturn(workouts.get(0));

        practiceRepositoryMockito = Mockito.mock(PracticeRepository.class);
        Mockito.when(practiceRepositoryMockito.findAll()).thenReturn(practices);
        Mockito.when(practiceRepositoryMockito.insert(any(Practice.class))).thenReturn(true);


        responseMockito = Mockito.mock(Response.class);
        Mockito.when(responseMockito.getDate("Введите дату тренировки в формате ГГГГ-ММ-ДД: ")).thenReturn(null);
        Mockito.when(responseMockito.getTime("Введите время начала тренировки в формате ЧЧ:ММ:СС: ")).thenReturn(LocalTime.now());
        Mockito.when(responseMockito.getInt(any())).thenReturn(1, 12, 45, 1500);
        Mockito.when(responseMockito.getSrting(any())).thenReturn("коммертарий","\n");


        FitnessService service = new FitnessServiceImpl(practiceRepositoryMockito, workoutRepositoryMockito, responseMockito, new Logger());
        service.setUser(user);

        boolean result = service.addPractice();

        Assertions.assertEquals(result, false);

    }

    @Test
    @DisplayName("Добавление тренировки с некорректным временем")
    public void AddPracticeBadTimeTest() {
        workoutRepositoryMockito = Mockito.mock(WorkoutRepository.class);
        Mockito.when(workoutRepositoryMockito.findAll()).thenReturn(workouts);
        Mockito.when(workoutRepositoryMockito.findById(1)).thenReturn(workouts.get(0));

        practiceRepositoryMockito = Mockito.mock(PracticeRepository.class);
        Mockito.when(practiceRepositoryMockito.findAll()).thenReturn(practices);
        Mockito.when(practiceRepositoryMockito.insert(any(Practice.class))).thenReturn(true);


        responseMockito = Mockito.mock(Response.class);
        Mockito.when(responseMockito.getDate("Введите дату тренировки в формате ГГГГ-ММ-ДД: ")).thenReturn(LocalDate.now());
        Mockito.when(responseMockito.getTime("Введите время начала тренировки в формате ЧЧ:ММ:СС: ")).thenReturn(null);
        Mockito.when(responseMockito.getInt(any())).thenReturn(1, 12, 45, 1500);
        Mockito.when(responseMockito.getSrting(any())).thenReturn("коммертарий","\n");


        FitnessService service = new FitnessServiceImpl(practiceRepositoryMockito, workoutRepositoryMockito, responseMockito, new Logger());
        service.setUser(user);

        boolean result = service.addPractice();

        Assertions.assertEquals(result, false);

    }

}
