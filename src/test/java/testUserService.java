import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.list.recover.models.User;
import ru.list.recover.services.UserServiceImpl;
import ru.list.recover.storages.UserRepository;

public class testUserService {

    @Test
    public void testLogin(){
        UserServiceImpl service = new UserServiceImpl();
        service.setRepository(new UserRepository());

        service.getRepository().insert(new User(0, "user1", "login1", "111", 0));

        User result = service.login();

        Assertions.assertEquals(result.getLogin(), "login1");
    }

}
