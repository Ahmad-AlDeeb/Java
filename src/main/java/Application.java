import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
