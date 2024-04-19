import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.list.recover.models.Practice;
import ru.list.recover.models.TypeWorkout;
import ru.list.recover.models.User;
import ru.list.recover.models.Workout;
import ru.list.recover.storages.impl.PracticeRepositoryImpl;

public class PracticeRepositoryTest {

    private static List<Practice> list;

    @BeforeAll
    private static void FillListPractice() {

        list = new ArrayList<>();

        User user = new User(1, "Test User", "user1", "111", 0);

        for (int i = 0; i < 10; i++) {
            list.add(new Practice(1,
                    LocalDateTime.now(),
                    user,
                    new Workout(i + 1, "workout" + i, new TypeWorkout(i, "type" + i), 100 * i),
                    12,
                    120, 1500, ""));
        }

    }

    @Test
    @DisplayName("Добавление тренировки")
    public void InsertTest() {
        PracticeRepositoryImpl repository = new PracticeRepositoryImpl();

        Assertions.assertTrue(repository.insert(list.get(0)));
    }

    @Test
    @DisplayName("Успешное удаление тренировки")
    public void DeleteTestTrue() {
        PracticeRepositoryImpl repository = new PracticeRepositoryImpl();

        repository.insert(list.get(0));
        repository.insert(list.get(1));
        repository.insert(list.get(2));
        repository.insert(list.get(3));

        Assertions.assertTrue(repository.delete(list.get(1)));
    }

    @Test
    @DisplayName("Не успешное удаление тренировки")
    public void DeleteTestFalse() {
        PracticeRepositoryImpl repository = new PracticeRepositoryImpl();

        repository.insert(list.get(0));
        repository.insert(list.get(1));
        repository.insert(list.get(2));
        repository.insert(list.get(3));

        Assertions.assertFalse(repository.delete(list.get(5)));
    }

    @Test
    @DisplayName("Поиск по ID")
    public void FindByIdTest() {
        PracticeRepositoryImpl repository = new PracticeRepositoryImpl();

        repository.insert(list.get(0));
        repository.insert(list.get(1));
        repository.insert(list.get(2));
        repository.insert(list.get(3));

        Assertions.assertEquals(repository.findById(3), list.get(2));

    }

    @Test
    @DisplayName("Получение количества тренировок")
    public void CountTest() {
        PracticeRepositoryImpl repository = new PracticeRepositoryImpl();

        for (Practice practice : list) {
            repository.insert(practice);
        }

        Assertions.assertEquals(repository.getCount(), 10);

    }
}
