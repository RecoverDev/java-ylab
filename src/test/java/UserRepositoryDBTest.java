import org.testcontainers.junit.jupiter.Testcontainers;

import ru.list.recover.db.FitnessProperties;
import ru.list.recover.logger.Logger;
import ru.list.recover.models.User;
import ru.list.recover.storages.UserRepository;
import ru.list.recover.storages.impl.UserRepositoryDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;


@Testcontainers
public class UserRepositoryDBTest {

    @Container
    private static PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres")
                    .withDatabaseName("postgres")
                    .withUsername("postgres")
                    .withPassword("password")
                    .withInitScript("data.sql");

    //private static Connection connection;
    
    @BeforeAll
    public static void Init() throws SQLException {
        FitnessProperties properties = new FitnessProperties("src/main/resources/fitness.properties", new Logger());
        properties.load();
        //connection = DriverManager.getConnection(properties.getUrl(), properties.getUser(), properties.getPassword());
    }
    
    
    @Test
    @DisplayName("Добавление пользователя")
    public void InsertUserTest() throws SQLException {
        FitnessProperties properties = new FitnessProperties("src/main/resources/fitness.properties", new Logger());
        properties.load();
        Connection connection = DriverManager.getConnection(properties.getUrl(), properties.getUser(), properties.getPassword());

        UserRepository repository = new UserRepositoryDB(connection,"fitness");
        boolean result = repository.insert(new User(1, "Test User", "user4", "123", 0));

        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("поиск пользователя по ID")
    public void findByIdTest() throws SQLException {
        FitnessProperties properties = new FitnessProperties("src/main/resources/fitness.properties", new Logger());
        properties.load();
        Connection connection = DriverManager.getConnection(properties.getUrl(), properties.getUser(), properties.getPassword());

        UserRepository repository = new UserRepositoryDB(connection,"fitness");
        User user = repository.findById(2);

        Assertions.assertEquals(user.getId(), 2);

    }

    @Test
    @DisplayName("Список всех пользователей")
    public void findAllTest() throws SQLException {
        FitnessProperties properties = new FitnessProperties("src/main/resources/fitness.properties", new Logger());
        properties.load();
        Connection connection = DriverManager.getConnection(properties.getUrl(), properties.getUser(), properties.getPassword());

        UserRepository repository = new UserRepositoryDB(connection,"fitness");
        List<User> users = repository.findAll();

        

    }



}
