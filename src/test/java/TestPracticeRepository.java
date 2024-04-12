import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ru.list.recover.models.Practice;
import ru.list.recover.models.User;
import ru.list.recover.models.Workout;
import ru.list.recover.storages.PracticeRepository;

public class TestPracticeRepository {

    private static List<Practice> list;

    @BeforeAll
    private static void FillListPractice(){

        list = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            list.add(new Practice(1, 
                    LocalDateTime.now(), 
                    new User(0, null, null, null, 0), 
                    new Workout(0, null, null, 0), 
                    12, 
                    120, 1500, ""));
        }


    }

    @Test
    public void testInsert(){
        PracticeRepository repository = new PracticeRepository();

        Assertions.assertTrue(repository.insert(list.get(0)));
    }

    @Test
    public void testTrueDelete(){
        PracticeRepository repository = new PracticeRepository();

        repository.insert(list.get(0));
        repository.insert(list.get(1));
        repository.insert(list.get(2));
        repository.insert(list.get(3));

        Assertions.assertTrue(repository.delete(list.get(1)));
    }

    @Test
    public void testFalseDelete(){
        PracticeRepository repository = new PracticeRepository();

        repository.insert(list.get(0));
        repository.insert(list.get(1));
        repository.insert(list.get(2));
        repository.insert(list.get(3));

        Assertions.assertFalse(repository.delete(list.get(5)));
    }

    @Test
    public void testFindById(){
        PracticeRepository repository = new PracticeRepository();

        repository.insert(list.get(0));
        repository.insert(list.get(1));
        repository.insert(list.get(2));
        repository.insert(list.get(3));

        Assertions.assertEquals(repository.findById(3), list.get(2));

    }

    @Test
    public void testCount(){
        PracticeRepository repository = new PracticeRepository();

        for(Practice practice : list){
            repository.insert(practice);
        }

        Assertions.assertEquals(repository.getCount(), 10);

    }
}
