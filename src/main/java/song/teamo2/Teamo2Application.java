package song.teamo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Teamo2Application {

	public static void main(String[] args) {
		SpringApplication.run(Teamo2Application.class, args);
	}

}
