package shelterhelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShelterHelperApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShelterHelperApplication.class, args);
    }

}
