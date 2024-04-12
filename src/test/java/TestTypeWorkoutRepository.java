import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.list.recover.models.TypeWorkout;
import ru.list.recover.storages.TypeWorkoutRepository;

public class TestTypeWorkoutRepository {

    @Test
    public void testInsert(){
        TypeWorkoutRepository repository = new TypeWorkoutRepository();
        
        Assertions.assertTrue(repository.insert(new TypeWorkout(1, "Силовая")));
    }

    @Test
    public void testIncrementID(){
        TypeWorkoutRepository repository = new TypeWorkoutRepository();
        
        repository.insert(new TypeWorkout(0, "Силовая"));

        Assertions.assertEquals(repository.findById(1).getId(), 1);

    }

    @Test
    public void testDelete(){
        TypeWorkoutRepository repository = new TypeWorkoutRepository();

        TypeWorkout typeWorkout = new TypeWorkout(1, "Пробная");
        
        repository.insert(typeWorkout);
        repository.insert(new TypeWorkout(1, "Силовая"));
        repository.insert(new TypeWorkout(1, "Аэробная"));
        repository.insert(new TypeWorkout(1, "Кардио"));
        repository.insert(new TypeWorkout(1, "Растяжка"));

        Assertions.assertTrue(repository.delete(typeWorkout));

    }

    @Test
    public void testFindById(){
        TypeWorkoutRepository repository = new TypeWorkoutRepository();

        TypeWorkout typeWorkout = new TypeWorkout(1, "Пробная");

        repository.insert(new TypeWorkout(1, "Силовая"));
        repository.insert(typeWorkout);
        repository.insert(new TypeWorkout(1, "Аэробная"));
        repository.insert(new TypeWorkout(1, "Кардио"));
        repository.insert(new TypeWorkout(1, "Растяжка"));

        Assertions.assertEquals(repository.findById(2), typeWorkout);

    }

    @Test
    public void testCount(){
        TypeWorkoutRepository repository = new TypeWorkoutRepository();

        repository.insert(new TypeWorkout(1, "Силовая"));
        repository.insert(new TypeWorkout(1, "Аэробная"));
        repository.insert(new TypeWorkout(1, "Кардио"));
        repository.insert(new TypeWorkout(1, "Растяжка"));

        Assertions.assertEquals(repository.getCount(), 4);

    }

}
