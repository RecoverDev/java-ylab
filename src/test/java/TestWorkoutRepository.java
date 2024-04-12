import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.list.recover.models.TypeWorkout;
import ru.list.recover.models.Workout;
import ru.list.recover.storages.WorkoutRepository;

public class TestWorkoutRepository {

    @Test
    public void testInsert(){
        WorkoutRepository repository = new WorkoutRepository();

        Workout workout1 = new Workout(1, "Сайкл", new TypeWorkout(1,"Айробная"), 1500);

        Assertions.assertTrue(repository.insert(workout1));
    }

    @Test
    public void testDelete(){
        WorkoutRepository repository = new WorkoutRepository();

        Workout workout1 = new Workout(1, "Сайкл", new TypeWorkout(1,"Айробная"), 1500);
        Workout workout2 = new Workout(1, "Кроссфит", new TypeWorkout(1,"Айробная"), 1500);

        repository.insert(workout1);
        repository.insert(workout2);

        Assertions.assertTrue(repository.delete(workout1));

    }

    @Test
    public void testFindById(){
        WorkoutRepository repository = new WorkoutRepository();

        Workout workout1 = new Workout(1, "Сайкл", new TypeWorkout(1,"Кардио"), 1500);
        Workout workout2 = new Workout(1, "Кроссфит", new TypeWorkout(1,"Силовая"), 1500);
        Workout workout3 = new Workout(1, "Табата", new TypeWorkout(1,"Айробная"), 1500);
        Workout workout4 = new Workout(1, "Стрейчинг", new TypeWorkout(1,"Растяжка"), 1500);

        repository.insert(workout1);
        repository.insert(workout2);
        repository.insert(workout3);
        repository.insert(workout4);

        Assertions.assertEquals(repository.findById(3), workout3);

    }

    @Test
    public void testCount(){
        WorkoutRepository repository = new WorkoutRepository();

        Workout workout1 = new Workout(1, "Сайкл", new TypeWorkout(1,"Кардио"), 1500);
        Workout workout2 = new Workout(1, "Кроссфит", new TypeWorkout(1,"Силовая"), 1500);
        Workout workout3 = new Workout(1, "Табата", new TypeWorkout(1,"Айробная"), 1500);
        Workout workout4 = new Workout(1, "Стрейчинг", new TypeWorkout(1,"Растяжка"), 200);

        repository.insert(workout1);
        repository.insert(workout2);
        repository.insert(workout3);
        repository.insert(workout4);

        Assertions.assertEquals(repository.getCount(), 4);

    }

    @Test
    public void testUpdate(){
        WorkoutRepository repository = new WorkoutRepository();

        Workout workout1 = new Workout(1, "Сайкл", new TypeWorkout(1,"Кардио"), 1500);
        Workout workout2 = new Workout(1, "Кроссфит", new TypeWorkout(1,"Силовая"), 1500);
        Workout workout3 = new Workout(1, "Табата", new TypeWorkout(1,"Айробная"), 1500);
        Workout workout4 = new Workout(1, "Стрейчинг", new TypeWorkout(1,"Растяжка"), 200);

        repository.insert(workout1);
        repository.insert(workout2);
        repository.insert(workout3);
        repository.insert(workout4);

        Workout newWorkout = new Workout(3, "Табата", new TypeWorkout(1,"Айробная"), 2000);

        repository.update(newWorkout);

        Assertions.assertEquals(repository.findById(3).getCalories(), 2000);

    }

}
