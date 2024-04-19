import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import ru.list.recover.in.Response;
import ru.list.recover.logger.Logger;
import ru.list.recover.models.User;
import ru.list.recover.services.UserService;
import ru.list.recover.services.impl.UserServiceImpl;
import ru.list.recover.storages.UserRepository;

public class UserServiceTest {

    @Mock
    UserRepository userRepositoryMockito;

    @Mock
    Response responseMockito;

    @Test
    @DisplayName("авторизация пользователя")
    public void LoginTest() {
        userRepositoryMockito = Mockito.mock(UserRepository.class);
        Mockito.when(userRepositoryMockito.findByLogin("user1")).thenReturn(new User(1,"Test User","user1","111",0));

        responseMockito = Mockito.mock(Response.class);
        Mockito.when(responseMockito.getSrting(any())).thenReturn("user1","111");

        UserService service = new UserServiceImpl(userRepositoryMockito, responseMockito, new Logger());
        User user = service.login();

        Assertions.assertEquals(user.getName(), "Test User");
    }

    @Test
    @DisplayName("регистрация пользователя")
    public void RegistrationTest() {
        userRepositoryMockito = Mockito.mock(UserRepository.class);

        responseMockito = Mockito.mock(Response.class);
        Mockito.when(responseMockito.getSrting(any())).thenReturn("Test User","testUser","321", "0");

        UserService service = new UserServiceImpl(userRepositoryMockito, responseMockito, new Logger());
        User user = service.registration();

        Assertions.assertEquals(user.getName(), "Test User");

    }

}
