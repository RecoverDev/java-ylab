import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.list.recover.db.FitnessProperties;
import ru.list.recover.logger.Logger;

public class FitnessPropertiesTest {

    @Test
    @DisplayName("Чтение имени пользователя из файла")
    public void readPropertyTest() throws IOException {
        Logger logger = new Logger();

        FitnessProperties properties = new FitnessProperties("src/main/resources/fitness.properties", logger);
        properties.load();
        Assertions.assertEquals(properties.getUser(), "fitnessUser");
    }

    @Test
    @DisplayName("Файл не найден")
    public void readPropertyBadTest() {
        Logger logger = new Logger();

        FitnessProperties properties = new FitnessProperties("BadFile", logger);
        boolean result = properties.load();
        Assertions.assertEquals(result, false);
    }

}
