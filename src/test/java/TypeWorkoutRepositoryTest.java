import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.list.recover.models.TypeWorkout;
import ru.list.recover.storages.TypeWorkoutRepository;
import ru.list.recover.storages.impl.TypeWorkoutRepositoryImpl;

public class TypeWorkoutRepositoryTest {

    @Test
    @DisplayName("Добавление записи")
    public void InsertTest() {
        TypeWorkoutRepository repository = new TypeWorkoutRepositoryImpl();

        Assertions.assertTrue(repository.insert(new TypeWorkout(1, "Силовая")));
    }

    @Test
    @DisplayName("Проверка обработки неверного ID")
    public void IncrementIDTest() {
        TypeWorkoutRepository repository = new TypeWorkoutRepositoryImpl();

        repository.insert(new TypeWorkout(0, "Силовая"));

        Assertions.assertEquals(repository.findById(1).getId(), 1);

    }

    @Test
    @DisplayName("Удаление элемента")
    public void DeleteTest() {
        TypeWorkoutRepository repository = new TypeWorkoutRepositoryImpl();

        TypeWorkout typeWorkout = new TypeWorkout(1, "Пробная");

        repository.insert(typeWorkout);
        repository.insert(new TypeWorkout(1, "Силовая"));
        repository.insert(new TypeWorkout(1, "Аэробная"));
        repository.insert(new TypeWorkout(1, "Кардио"));
        repository.insert(new TypeWorkout(1, "Растяжка"));

        Assertions.assertTrue(repository.delete(typeWorkout));

    }

    @Test
    @DisplayName("поиск элемента по ID")
    public void FindByIdTest() {
        TypeWorkoutRepository repository = new TypeWorkoutRepositoryImpl();

        TypeWorkout typeWorkout = new TypeWorkout(1, "Пробная");

        repository.insert(new TypeWorkout(1, "Силовая"));
        repository.insert(typeWorkout);
        repository.insert(new TypeWorkout(1, "Аэробная"));
        repository.insert(new TypeWorkout(1, "Кардио"));
        repository.insert(new TypeWorkout(1, "Растяжка"));

        Assertions.assertEquals(repository.findById(2), typeWorkout);

    }

    @Test
    @DisplayName("возвращает количество элементов в хранилище")
    public void CountTest() {
        TypeWorkoutRepository repository = new TypeWorkoutRepositoryImpl();

        repository.insert(new TypeWorkout(1, "Силовая"));
        repository.insert(new TypeWorkout(1, "Аэробная"));
        repository.insert(new TypeWorkout(1, "Кардио"));
        repository.insert(new TypeWorkout(1, "Растяжка"));

        Assertions.assertEquals(repository.getCount(), 4);

    }

}
