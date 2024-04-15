import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.list.recover.models.User;
import ru.list.recover.storages.Repository;
import ru.list.recover.storages.UserRepositoryImpl;

public class TestUserRepository {

    @Test
    public void testInsert() {
        Repository<User> repository = new UserRepositoryImpl();

        Assertions.assertTrue(repository.insert(new User(1, "user1", "user1", "111", 0)));
    }

    @Test
    public void testInsertRepeatName() {
        UserRepositoryImpl repository = new UserRepositoryImpl();

        repository.insert(new User(1, "user1", "user1", "111", 0));
        repository.insert(new User(2, "user2", "user2", "222", 0));
        Assertions.assertFalse(repository.insert(new User(1, "user1", "user3", "111", 0)));

    }

    @Test
    public void testInsertRepeatLogin() {
        UserRepositoryImpl repository = new UserRepositoryImpl();

        repository.insert(new User(1, "user1", "user1", "111", 0));
        repository.insert(new User(2, "user2", "user2", "222", 0));
        Assertions.assertFalse(repository.insert(new User(1, "user3", "user1", "111", 0)));

    }

    @Test
    public void testDelete() {
        User user1 = new User(1, "user1", "user1", "111", 0);
        User user2 = new User(1, "user2", "user2", "111", 0);
        User user3 = new User(1, "user3", "user3", "111", 0);
        User user4 = new User(1, "user4", "user4", "111", 0);

        UserRepositoryImpl repository = new UserRepositoryImpl();
        repository.insert(user1);
        repository.insert(user2);
        repository.insert(user3);
        repository.insert(user4);

        Assertions.assertTrue(repository.delete(user3));
    }

    @Test
    public void testCount() {

        UserRepositoryImpl repository = new UserRepositoryImpl();

        repository.insert(new User(1, "user1", "user1", "111", 0));
        repository.insert(new User(2, "user2", "user2", "222", 0));

        Assertions.assertEquals(repository.getCount(), 2);
    }

    @Test
    public void testIdControl() {
        UserRepositoryImpl repository = new UserRepositoryImpl();

        repository.insert(new User(1, "user1", "user1", "111", 0));
        repository.insert(new User(1, "user2", "user2", "222", 0));

        Assertions.assertEquals(repository.findById(2).getName(), "user2");
    }

    @Test
    public void testFindByName() {
        User user1 = new User(1, "user1", "user1", "111", 0);
        User user2 = new User(1, "user2", "user2", "111", 0);
        User user3 = new User(1, "user3", "user3", "111", 0);
        User user4 = new User(1, "user4", "user4", "111", 0);

        UserRepositoryImpl repository = new UserRepositoryImpl();
        repository.insert(user1);
        repository.insert(user2);
        repository.insert(user3);
        repository.insert(user4);

        User findUser = repository.findByName("user2");

        Assertions.assertEquals(user2, findUser);
    }

    @Test
    public void testUpdate() {
        User user1 = new User(1, "user1", "user1", "111", 0);
        User user2 = new User(2, "user2", "user2", "111", 0);
        User user3 = new User(3, "user3", "user3", "111", 0);
        User user4 = new User(4, "user4", "user4", "111", 0);

        UserRepositoryImpl repository = new UserRepositoryImpl();
        repository.insert(user1);
        repository.insert(user2);
        repository.insert(user3);
        repository.insert(user4);

        User user5 = new User(3, "user5", "user3", "111", 0);

        repository.update(user5);

        User getUser = repository.findById(3);

        Assertions.assertEquals(getUser.getName(), "user5");

    }

}
