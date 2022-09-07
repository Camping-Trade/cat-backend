package CampingTrade.catbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@SpringBootApplication
public class  CatBackendApplication {

//	public static final String APPLICATION_LOCATIONS = "spring.config.location="
//			+ "classpath:/application.yml"
//			+ "classpath:/application-aws.yml";

	public static void main(String[] args) {

//		new SpringApplicationBuilder(CatBackendApplication.class)
//				.properties(APPLICATION_LOCATIONS)
//				.run(args);

		SpringApplication.run(CatBackendApplication.class, args);
	}

}
